package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
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
import uy.com.amensg.logistica.bean.ActivacionSubloteBean;
import uy.com.amensg.logistica.bean.IActivacionSubloteBean;
import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/ActivacionSubloteREST")
public class ActivacionSubloteREST {

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
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = iActivacionSubloteBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					ActivacionSublote activacionSublote = (ActivacionSublote) object;
					
					prepareJSON(activacionSublote, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listMisSublotesContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listMisSublotesContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = iActivacionSubloteBean.listMisSublotes(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					ActivacionSublote activacionSublote = (ActivacionSublote) object;
					
					prepareJSON(activacionSublote, false);
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
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = iActivacionSubloteBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countMisSublotesContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countMisSublotesContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = iActivacionSubloteBean.countMisSublotes(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ActivacionSublote getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		ActivacionSublote result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = prepareJSON(iActivacionSubloteBean.getById(id, true), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNumero/{numero}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ActivacionSublote getByNumero(@PathParam("numero") Long numero, @Context HttpServletRequest request) {
		ActivacionSublote result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = 
					prepareJSON(iActivacionSubloteBean.getByNumero(numero, true), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNumeroContextAware/{numero}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ActivacionSublote getByNumeroContextAware(
		@PathParam("numero") Long numero, @Context HttpServletRequest request
	) {
		ActivacionSublote result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = 
					prepareJSON(iActivacionSubloteBean.getByNumeroUsuario(numero, usuarioId, true), true);
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
	public ActivacionSublote add(ActivacionSublote activacionSublote, @Context HttpServletRequest request) {
		ActivacionSublote result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				activacionSublote.setFact(hoy);
				activacionSublote.setFcre(hoy);
				activacionSublote.setTerm(Long.valueOf(1));
				activacionSublote.setUact(loggedUsuarioId);
				activacionSublote.setUcre(loggedUsuarioId);
				
				result = prepareJSON(iActivacionSubloteBean.save(activacionSublote), true);
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
	public void update(ActivacionSublote activacionSublote, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				activacionSublote.setFact(hoy);
				activacionSublote.setTerm(Long.valueOf(1));
				activacionSublote.setUact(loggedUsuarioId);
				
				iActivacionSubloteBean.update(activacionSublote);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/asignarAPuntoVenta")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void asignarAPuntoVenta(ActivacionSublote activacionSublote, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				activacionSublote.setFact(hoy);
				activacionSublote.setTerm(Long.valueOf(1));
				activacionSublote.setUact(loggedUsuarioId);
				
				iActivacionSubloteBean.asignarAPuntoVenta(
					activacionSublote,
					activacionSublote.getPuntoVenta()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/calcularFechasVencimientoChipMasViejo")
	@Produces({ MediaType.APPLICATION_JSON })
	public void calcularFechasVencimientoChipMasViejo(@Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				iActivacionSubloteBean.calcularFechasVencimientoChipMasViejo(usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ActivacionSublote prepareJSON(ActivacionSublote activacionSublote, boolean includeCollections) {
		ActivacionSublote result = activacionSublote;
		
		if (result != null) {
			if (includeCollections) {
				for (Activacion activacion : result.getActivaciones()) {
					prepareJSON(activacion);
				}
			} else {
				result.setActivaciones(new HashSet<Activacion>());
			}
			
			if (result.getDistribuidor() != null) {
				result.getDistribuidor()
					.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			}
			
			result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
			
			if (result.getPuntoVenta() != null 
				&& result.getPuntoVenta().getDistribuidor() != null) {
				result.getPuntoVenta().getDistribuidor()
					.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			}
			
			if (result.getPuntoVenta() != null 
				&& result.getPuntoVenta().getCreador() != null) {
				result.getPuntoVenta().getCreador()
					.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			}
			
			if (result.getPuntoVenta() != null 
				&& result.getPuntoVenta().getRecargaPuntoVentaCota() != null) {
				result.getPuntoVenta().getRecargaPuntoVentaCota().setPuntoVenta(null);
			}
		}
		
		return result;
	}
	
	private Activacion prepareJSON(Activacion activacion) {
		Activacion result = activacion;
		
		if (result.getActivacionLote() != null) {
			result.getActivacionLote().getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getActivacionLote().getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		}
		
//		if (result.getActivacionSublote() != null) {
//			result.getActivacionSublote().setActivaciones(new HashSet<Activacion>());
//			
//			if (result.getActivacionSublote().getDistribuidor() != null) {
//				result.getActivacionSublote().getDistribuidor()
//					.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
//			}
//			
//			result.getActivacionSublote().getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
//			result.getActivacionSublote().getEmpresa().setFormaPagos(new HashSet<FormaPago>());
//			
//			if (result.getActivacionSublote().getPuntoVenta() != null
//				&& result.getActivacionSublote().getPuntoVenta().getDistribuidor() != null) {
//				result.getActivacionSublote().getPuntoVenta().getDistribuidor()
//					.setUsuarioRolEmpresas(new HashSet<UsuarioRolEmpresa>());
//			}
//			
//			if (result.getActivacionSublote().getPuntoVenta() != null
//				&& result.getActivacionSublote().getPuntoVenta().getCreador() != null) {
//				result.getActivacionSublote().getPuntoVenta().getCreador()
//					.setUsuarioRolEmpresas(new HashSet<UsuarioRolEmpresa>());
//			}
//		}
		result.setActivacionSublote(null);
		
		result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
		result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		
		if (result.getLiquidacion() != null) {
			result.getLiquidacion().getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getLiquidacion().getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		}
		
		return result;
	}
	
	private IActivacionSubloteBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionSubloteBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionSubloteBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IActivacionSubloteBean) context.lookup(lookupName);
	}
}