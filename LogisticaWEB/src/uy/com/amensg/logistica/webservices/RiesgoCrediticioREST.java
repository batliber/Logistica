package uy.com.amensg.logistica.webservices;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.util.Configuration;

@Path("/RiesgoCrediticioREST")
public class RiesgoCrediticioREST {

	@GET
	@Path("/getInformacionRiesgoCrediticio/{documento}")
	@Produces("application/json")
	public String getInformacionRiesgoCrediticio(@PathParam("documento") String documento) {
		String result = "";
		
		try {
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Integer maximoTiempoProcesadoDias = Integer.parseInt(
				Configuration.getInstance().getProperty("riesgoCrediticio.maximoTiempoProcesadoDias")
			);
			
			gregorianCalendar.add(
				GregorianCalendar.DATE, 
				-1 * maximoTiempoProcesadoDias
			);
			
			Date fechaMaximaVigencia = gregorianCalendar.getTime(); 
			RiesgoCrediticio riesgoCrediticio = iRiesgoCrediticioBean.getLastByDocumento(documento);
			if (riesgoCrediticio != null) {
				if (riesgoCrediticio.getEstadoRiesgoCrediticio().getId().equals(
						Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					)
					&& !riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
						Long.parseLong(
							Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR")
						)
					)
					&& (
						riesgoCrediticio.getFechaVigenciaDesde() == null
						|| riesgoCrediticio.getFechaVigenciaDesde().after(fechaMaximaVigencia)
					)
				) {
					result = 
						"{"
							+ " \"documento\": \"" + documento + "\","
							+ " \"calificacion\": \"" 
								+ riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getDescripcion() 
							+ "\""
						+ " }";
				} else {
					iRiesgoCrediticioBean.controlarRiesgoBCUOnline(documento);
					
					result = 
						"{"
							+ " \"documento\": \"" + documento + "\","
							+ " \"calificacion\": \"PENDIENTE\""
						+ " }";
				}
			} else {
				iRiesgoCrediticioBean.controlarRiesgoBCUOnline(documento);
				
				result = 
					"{"
						+ " \"documento\": \"" + documento + "\","
						+ " \"calificacion\": \"PENDIENTE\""
					+ " }";
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			result =
				"{"
					+ " \"error\": \"No se puede completar la operación.\""
				+ " }";
		}
		
		return result;
	}
	
	private IRiesgoCrediticioBean lookupRiesgoCrediticioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}
}