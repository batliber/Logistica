package uy.com.amensg.logistica.webservices.rest;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import uy.com.amensg.logistica.bean.BICSABean;
import uy.com.amensg.logistica.bean.IBICSABean;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioParaguayBICSABean;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioParaguayBICSABean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguay;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguayBICSA;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.webservices.external.bicsa.Persona;

@Path("/RiesgoCrediticioParaguayBICSAREST")
public class RiesgoCrediticioParaguayBICSAREST {

	@GET
	@Path("/listByRiesgoCrediticioParaguayId/{riesgoCrediticioParaguayId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<RiesgoCrediticioParaguayBICSA> listByRiesgoCrediticioParaguayId(
		@PathParam("riesgoCrediticioParaguayId") Long riesgoCrediticioParaguayId, @Context HttpServletRequest request
	) {
		Collection<RiesgoCrediticioParaguayBICSA> result = new LinkedList<RiesgoCrediticioParaguayBICSA>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRiesgoCrediticioParaguayBICSABean iRiesgoCrediticioParaguayBICSABean = lookupBean();
				
				result = 
					iRiesgoCrediticioParaguayBICSABean.listByRiesgoCrediticioParaguayId(
						riesgoCrediticioParaguayId
					);
				
				for (RiesgoCrediticioParaguayBICSA riesgoCrediticioParaguayBICSA : result) {
					riesgoCrediticioParaguayBICSA.setRiesgoCrediticioParaguay(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/testUpdate/{riesgoCrediticioParaguayId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public void testUpdate(
			@PathParam("riesgoCrediticioParaguayId") Long riesgoCrediticioParaguayId, @Context HttpServletRequest request
	) {
		try {
			IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean =
				lookupRiesgoCrediticioParaguayBean();
			
			RiesgoCrediticioParaguay riesgoCrediticioParaguay = 
				iRiesgoCrediticioParaguayBean.getById(riesgoCrediticioParaguayId);
			
			IBICSABean iBICSABean = lookupBICSABean();
			
			// Obtener los datos del servicio web de BICSA.
			Persona persona = iBICSABean.obtenerPersona(
				Integer.parseInt(Configuration.getInstance().getProperty("creditoAmigoPY.TipoDocumentoTodosBICSA")),
				Configuration.getInstance().getProperty("creditoAmigoPY.PaisTodosBICSA"), 
				riesgoCrediticioParaguay.getDocumento(), 
				Configuration.getInstance().getProperty("creditoAmigoPY.InstitucionBICSA")
			);
			
			StringWriter stringWriter = new StringWriter();
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(stringWriter, persona);
			
			// Grabar datos.
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RiesgoCrediticioParaguayBICSA riesgoCrediticioParaguayBICSA =
				new RiesgoCrediticioParaguayBICSA();
			
			riesgoCrediticioParaguayBICSA.setDatos(stringWriter.toString());
			riesgoCrediticioParaguayBICSA.setFact(hoy);
			riesgoCrediticioParaguayBICSA.setFcre(hoy);
			riesgoCrediticioParaguayBICSA.setRiesgoCrediticioParaguay(riesgoCrediticioParaguay);
			riesgoCrediticioParaguayBICSA.setTerm(Long.valueOf(1));
			riesgoCrediticioParaguayBICSA.setUact(Long.valueOf(1));
			riesgoCrediticioParaguayBICSA.setUcre(Long.valueOf(1));
			
			IRiesgoCrediticioParaguayBICSABean iRiesgoCrediticioParaguayBICSABean = lookupBean();
			
			iRiesgoCrediticioParaguayBICSABean.save(riesgoCrediticioParaguayBICSA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IRiesgoCrediticioParaguayBICSABean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioParaguayBICSABean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioParaguayBICSABean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IRiesgoCrediticioParaguayBICSABean) context.lookup(lookupName);
	}

	private IRiesgoCrediticioParaguayBean lookupRiesgoCrediticioParaguayBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioParaguayBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioParaguayBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IRiesgoCrediticioParaguayBean) context.lookup(lookupName);
	}
	
	private IBICSABean lookupBICSABean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = BICSABean.class.getSimpleName();
		String remoteInterfaceName = IBICSABean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IBICSABean) context.lookup(lookupName);
	}
}