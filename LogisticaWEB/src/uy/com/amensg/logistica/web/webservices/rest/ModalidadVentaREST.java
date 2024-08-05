package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.IModalidadVentaBean;
import uy.com.amensg.logistica.bean.ModalidadVentaBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ModalidadVenta;

@Path("/ModalidadVentaREST")
public class ModalidadVentaREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<ModalidadVenta> list(@Context HttpServletRequest request) {
		Collection<ModalidadVenta> result = new LinkedList<ModalidadVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModalidadVentaBean iModalidadVentaBean = lookupBean();
				
				result = iModalidadVentaBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModalidadVentaBean iModalidadVentaBean = lookupBean();
				
				result = iModalidadVentaBean.list(metadataConsulta, usuarioId);
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModalidadVentaBean iModalidadVentaBean = lookupBean();
				
				result = iModalidadVentaBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ModalidadVenta getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		ModalidadVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModalidadVentaBean iModalidadVentaBean = lookupBean();
				
				result = iModalidadVentaBean.getById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ModalidadVenta add(ModalidadVenta atencionClienteConcepto, @Context HttpServletRequest request) {
		ModalidadVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModalidadVentaBean iModalidadVentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteConcepto.setFact(hoy);
				atencionClienteConcepto.setFcre(hoy);
				atencionClienteConcepto.setTerm(Long.valueOf(1));
				atencionClienteConcepto.setUact(loggedUsuarioId);
				atencionClienteConcepto.setUcre(loggedUsuarioId);
				
				result = iModalidadVentaBean.save(atencionClienteConcepto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	private IModalidadVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ModalidadVentaBean.class.getSimpleName();
		String remoteInterfaceName = IModalidadVentaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IModalidadVentaBean) context.lookup(lookupName);
	}
}