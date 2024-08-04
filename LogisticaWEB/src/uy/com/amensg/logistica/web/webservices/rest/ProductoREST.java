package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.IProductoBean;
import uy.com.amensg.logistica.bean.ProductoBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Producto;

@Path("/ProductoREST")
public class ProductoREST {

	@GET
	@Path("/listMinimal")
	@Produces("application/json")
	public Collection<Producto> listMinimal(@Context HttpServletRequest request) {
		Collection<Producto> result = new LinkedList<Producto>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IProductoBean iProductoBean = lookupBean();
			
				result = iProductoBean.listMinimal();
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
				
				IProductoBean iProductoBean = lookupBean();
				
				result = iProductoBean.list(metadataConsulta, usuarioId);
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
				
				IProductoBean iProductoBean = lookupBean();
				
				result = iProductoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByIMEI/{imei}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Producto getByIMEI(@PathParam("imei") String imei, @Context HttpServletRequest request) {
		Producto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IProductoBean iProductoBean = lookupBean();
				
				result = iProductoBean.getByIMEI(imei);
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
	public Producto add(Producto Producto, @Context HttpServletRequest request) {
		Producto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IProductoBean iProductoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Producto.setFact(hoy);
				Producto.setFcre(hoy);
				Producto.setTerm(Long.valueOf(1));
				Producto.setUact(loggedUsuarioId);
				Producto.setUcre(loggedUsuarioId);
				
				result = iProductoBean.save(Producto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void update(Producto Producto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IProductoBean iProductoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Producto.setFact(hoy);
				Producto.setTerm(Long.valueOf(1));
				Producto.setUact(loggedUsuarioId);
				
				iProductoBean.update(Producto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Producto Producto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IProductoBean iProductoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Producto.setFact(hoy);
				Producto.setTerm(Long.valueOf(1));
				Producto.setUact(loggedUsuarioId);
				
				iProductoBean.remove(Producto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IProductoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ProductoBean.class.getSimpleName();
		String remoteInterfaceName = IProductoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IProductoBean) context.lookup(lookupName);
	}
}