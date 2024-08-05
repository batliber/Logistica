package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ACMInterfaceMidPHBean;
import uy.com.amensg.logistica.bean.IACMInterfaceMidPHBean;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@Path("/ACMInterfaceMidPHREST")
public class ACMInterfaceMidPHREST {

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
				IACMInterfaceMidPHBean iACMInterfaceMidPHBean = lookupBean();
				
				// Condicion de estado no "Procesado"
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("estado.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				result = iACMInterfaceMidPHBean.list(metadataConsulta);
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
				IACMInterfaceMidPHBean iACMInterfaceMidPHBean = lookupBean();
				
				// Condicion de estado no "Procesado"
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("estado.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				result = iACMInterfaceMidPHBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IACMInterfaceMidPHBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceMidPHBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceMidPHBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceMidPHBean) context.lookup(lookupName);
	}
}