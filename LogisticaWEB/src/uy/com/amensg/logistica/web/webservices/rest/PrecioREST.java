package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

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
import uy.com.amensg.logistica.bean.IPrecioBean;
import uy.com.amensg.logistica.bean.PrecioBean;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.web.entities.ObtenerPrecioActualTO;

@Path("/PrecioREST")
public class PrecioREST {

	@POST
	@Path("/listPreciosActualesContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listPreciosActualesContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPrecioBean iPrecioBean = lookupBean();
				
				result = iPrecioBean.listPreciosActuales(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Precio precio = (Precio) object;
					
					precio.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					precio.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countPreciosActualesContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countPreciosActualesContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPrecioBean iPrecioBean = lookupBean();
				
				result = iPrecioBean.countPreciosActuales(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Precio getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Precio result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPrecioBean iPrecioBean = lookupBean();
				
				result = iPrecioBean.getById(id);
				
				result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
				result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Precio getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
		ObtenerPrecioActualTO obtenerPrecioActualTO, @Context HttpServletRequest request
	) {
		Precio result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPrecioBean iPrecioBean = lookupBean();
				
				Empresa empresa = new Empresa();
				empresa.setId(obtenerPrecioActualTO.getEmpresaId());
				
				TipoProducto tipoProducto = new TipoProducto();
				tipoProducto.setId(obtenerPrecioActualTO.getTipoProductoId());
				
				Marca marca = new Marca();
				marca.setId(obtenerPrecioActualTO.getMarcaId());
				
				Modelo modelo = new Modelo();
				modelo.setId(obtenerPrecioActualTO.getModeloId());
				
				Moneda moneda = new Moneda();
				moneda.setId(obtenerPrecioActualTO.getMonedaId());
				
				result = 
					iPrecioBean.getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
						empresa, 
						tipoProducto, 
						marca, 
						modelo, 
						moneda, 
						obtenerPrecioActualTO.getCuotas()
					);
				
				if (result != null) {
					result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
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
	public Precio add(Precio Precio, @Context HttpServletRequest request) {
		Precio result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPrecioBean iPrecioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Precio.setFact(hoy);
				Precio.setFcre(hoy);
				Precio.setTerm(Long.valueOf(1));
				Precio.setUact(loggedUsuarioId);
				Precio.setUcre(loggedUsuarioId);
				
				result = iPrecioBean.save(Precio);
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
	public void update(Precio Precio, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPrecioBean iPrecioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Precio.setFact(hoy);
				Precio.setTerm(Long.valueOf(1));
				Precio.setUact(loggedUsuarioId);
				
				iPrecioBean.update(Precio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IPrecioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = PrecioBean.class.getSimpleName();
		String remoteInterfaceName = IPrecioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IPrecioBean) context.lookup(lookupName);
	}
}