package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import uy.com.amensg.logistica.bean.ANTELBean;
import uy.com.amensg.logistica.bean.IANTELBean;
import uy.com.amensg.logistica.web.entities.ANTELNotaTO;
import uy.com.amensg.logistica.web.entities.ResultadoNotificarAPIStockTO;
import uy.com.amensg.logistica.web.entities.ResultadoNotificarInstalacionTO;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.DataMask;

@Path("/ANTELREST")
public class ANTELREST {

	@GET
	@Path("/notificarInstalacion")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoNotificarInstalacionTO notificarInstalacion(@Context UriInfo uriInfo) {
		ResultadoNotificarInstalacionTO result = new ResultadoNotificarInstalacionTO();
		
		try {
			IANTELBean iANTELBean = lookupBean();
			
			String idTicket = null;
			String telefono = null;
			String serieONT = null;
			int idTypeData = 1;
			String info = "";
			
			MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
			
			if (queryParams.size() >= 3) {
				idTicket = queryParams.get("idTicket").get(0);
				telefono = queryParams.get("telefono").get(0);
				serieONT = queryParams.get("serieONT").get(0);
				idTypeData = Integer.parseInt(queryParams.get("idTypeData").get(0));
				info = queryParams.get("info").get(0);
				
				result.setResultado(iANTELBean.notificarInstalacion(idTicket, telefono, serieONT, idTypeData, info));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultado(Long.valueOf(-1));
		}
		
		return result;
	}
	
	@GET
	@Path("/obtenerDatosANTEL/{idTicket}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DataMask obtenerDatosANTEL(@PathParam("idTicket") String idTicket) {
		DataMask result = null;
		
		try {
			IANTELBean iANTELBean = lookupBean();
			
			result = iANTELBean.obtenerDatosANTEL(idTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/notificarAPIStock")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoNotificarAPIStockTO notificarAPIStock(@Context UriInfo uriInfo) {
		ResultadoNotificarAPIStockTO result = new ResultadoNotificarAPIStockTO();
		
		try {
			IANTELBean iANTELBean = lookupBean();
			
			MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
			
			String serie = null;
			
			if (queryParams.size() >= 2) {
				serie = queryParams.get("serie").get(0);
				
				Collection<String> series = new LinkedList<String>();
				series.add(serie);
				
				result.setResultado(iANTELBean.notificarAPIStock(series));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/publicarNota")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void publicarNota(ANTELNotaTO antelNotaTO, @Context HttpServletRequest request) {
		try {
			IANTELBean iANTELBean = lookupBean();
			
			iANTELBean.publicarNota(antelNotaTO.getIdTicket(), antelNotaTO.getNota());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/obtenerAccessTokent")
	@Produces({ MediaType.APPLICATION_JSON })
	public String obtenerAccessToken() {
		String result = null;
		
		try {
			IANTELBean iANTELBean = lookupBean();
			
			result = iANTELBean.obtenerAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IANTELBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ANTELBean.class.getSimpleName();
		String remoteInterfaceName = IANTELBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IANTELBean) context.lookup(lookupName);
	}
}