package uy.com.amensg.logistica.webservices.rest;

import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.CalculoPorcentajeActivacionPuntoVentaBean;
import uy.com.amensg.logistica.bean.ICalculoPorcentajeActivacionPuntoVentaBean;
import uy.com.amensg.logistica.entities.CalculoPorcentajeActivacionPuntoVenta;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/CalculoPorcentajeActivacionPuntoVentaREST")
public class CalculoPorcentajeActivacionPuntoVentaREST {

	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ICalculoPorcentajeActivacionPuntoVentaBean iCalculoPorcentajeActivacionPuntoVentaBean = lookupBean();
				
				result = iCalculoPorcentajeActivacionPuntoVentaBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					CalculoPorcentajeActivacionPuntoVenta calculoPorcentajeActivacionPuntoVenta = 
						(CalculoPorcentajeActivacionPuntoVenta) object;
					
					if (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getDistribuidor() != null) {
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getDistribuidor()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getCreador() != null) {
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getCreador()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ICalculoPorcentajeActivacionPuntoVentaBean iCalculoPorcentajeActivacionPuntoVentaBean = lookupBean();
				
				result = iCalculoPorcentajeActivacionPuntoVentaBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/exportarAExcel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcel(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ICalculoPorcentajeActivacionPuntoVentaBean iCalculoPorcentajeActivacionPuntoVentaBean = 
					lookupBean();
				
				String nombreArchivo = 
					iCalculoPorcentajeActivacionPuntoVentaBean.exportarAExcel(metadataConsulta, usuarioId);
				
				if (nombreArchivo != null) {
					result = new ResultadoExportacionArchivoTO();
					result.setNombreArchivo(nombreArchivo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ICalculoPorcentajeActivacionPuntoVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = CalculoPorcentajeActivacionPuntoVentaBean.class.getSimpleName();
		String remoteInterfaceName = ICalculoPorcentajeActivacionPuntoVentaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ICalculoPorcentajeActivacionPuntoVentaBean) context.lookup(lookupName);
	}
}