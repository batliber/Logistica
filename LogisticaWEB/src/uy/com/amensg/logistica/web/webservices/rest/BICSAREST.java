package uy.com.amensg.logistica.web.webservices.rest;

import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import uy.com.amensg.logistica.bean.BICSABean;
import uy.com.amensg.logistica.bean.IBICSABean;
import uy.com.amensg.logistica.webservices.external.bicsa.Pais;
import uy.com.amensg.logistica.webservices.external.bicsa.Persona;
import uy.com.amensg.logistica.webservices.external.bicsa.TipoDocumento;
import uy.com.amensg.logistica.webservices.external.creditoamigo.WsBICSA;
import uy.com.amensg.logistica.webservices.external.creditoamigo.WsBICSASoap;

@Path("/BICSAREST")
public class BICSAREST {

	@GET
	@Path("/listPaises")
	@Produces("application/json")
	public Collection<Pais> listPaises(@Context HttpServletRequest request) {
		Collection<Pais> result = new LinkedList<Pais>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBICSABean iBICSABean = lookupBean();
				
				result = iBICSABean.listadoPaises();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listTipoDocumentos")
	@Produces("application/json")
	public Collection<TipoDocumento> listTipoDocumentos(@Context HttpServletRequest request) {
		Collection<TipoDocumento> result = new LinkedList<TipoDocumento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBICSABean iBICSABean = lookupBean();
				
				result = iBICSABean.listadoTipoDocumentos();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/obtenerPersona")
	@Produces("application/json")
	public Persona obtenerPersona(
		@Context UriInfo uriInfo, @Context HttpServletRequest request
	) {
		Persona result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBICSABean iBICSABean = lookupBean();
				
				MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
				
				if (queryParams.size() >= 4) {
					int idTipoDoc = Integer.parseInt(queryParams.get("idTipoDoc").get(0));
					String idPaisDoc = queryParams.get("idPaisDoc").get(0);
					String nroDoc = queryParams.get("nroDoc").get(0);
					String codInstitucion = queryParams.get("codInstitucion").get(0);
					
					result = iBICSABean.obtenerPersona(idTipoDoc, idPaisDoc, nroDoc, codInstitucion);
					
					StringWriter stringWriter = new StringWriter();
					
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.writeValue(stringWriter, result);
					
//					System.out.println(stringWriter.toString());
					
					WsBICSA wsBICSA = new WsBICSA();
					WsBICSASoap wsBICSASoap = wsBICSA.getWsBICSASoap12();
					
					wsBICSASoap.bicsaAdd(Integer.parseInt(nroDoc), stringWriter.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IBICSABean lookupBean() throws NamingException {
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