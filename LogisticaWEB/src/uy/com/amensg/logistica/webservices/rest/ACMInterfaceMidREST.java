package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
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

import uy.com.amensg.logistica.bean.ACMInterfaceMidBean;
import uy.com.amensg.logistica.bean.IACMInterfaceMidBean;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ReprocesarTO;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@Path("/ACMInterfaceMidREST")
public class ACMInterfaceMidREST {

	@POST
	@Path("/listSinDatosContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listSinDatosContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
				
				// Condicion de estado no "Procesado"
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("estado.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				result = iACMInterfaceMidBean.list(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countSinDatosContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countSinDatosContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
				
				// Condicion de estado no "Procesado"
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("estado.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				result = iACMInterfaceMidBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/reprocesarSinDatos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reprocesarSinDatos(ReprocesarTO reprocesarTO, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
				
				// Condicion de estado no "Procesado"
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("estado.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
				metadataCondicion.setValores(valores);
				
				reprocesarTO.getMetadataConsulta().getMetadataCondiciones().add(metadataCondicion);
				
				iACMInterfaceMidBean.reprocesar(
					reprocesarTO.getMetadataConsulta(),
					reprocesarTO.getObservaciones()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/agregarAListaNegraSinDatos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void agregarAListaNegraSinDatos(MetadataConsulta metadataConsulta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
				
				// Condicion de estado no "Procesado"
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("estado.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				iACMInterfaceMidBean.agregarAListaNegra(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceMidBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceMidBean.class.getName();
		String beanName = ACMInterfaceMidBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceMidBean) context.lookup(lookupName);
	}
}