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
import uy.com.amensg.logistica.bean.IRecargaPuntoVentaUsuarioBean;
import uy.com.amensg.logistica.bean.IRecargaSolicitudAcreditacionSaldoBean;
import uy.com.amensg.logistica.bean.RecargaPuntoVentaUsuarioBean;
import uy.com.amensg.logistica.bean.RecargaSolicitudAcreditacionSaldoBean;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldo;
import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldoArchivoAdjunto;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/RecargaSolicitudAcreditacionSaldoREST")
public class RecargaSolicitudAcreditacionSaldoREST {

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
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				result = iRecargaSolicitudAcreditacionSaldoBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo =
						(RecargaSolicitudAcreditacionSaldo) object;
					
					recargaSolicitudAcreditacionSaldo.setArchivosAdjuntos(
						new HashSet<RecargaSolicitudAcreditacionSaldoArchivoAdjunto>()
					);
					
					if (recargaSolicitudAcreditacionSaldo.getEmpresaRecargaBancoCuenta() != null) {
						recargaSolicitudAcreditacionSaldo.getEmpresaRecargaBancoCuenta()
							.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
						recargaSolicitudAcreditacionSaldo.getEmpresaRecargaBancoCuenta()
							.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					}
					
					if (recargaSolicitudAcreditacionSaldo.getPuntoVenta().getDistribuidor() != null) {
						recargaSolicitudAcreditacionSaldo.getPuntoVenta().getDistribuidor()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (recargaSolicitudAcreditacionSaldo.getPuntoVenta().getCreador() != null) {
						recargaSolicitudAcreditacionSaldo.getPuntoVenta().getCreador()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (recargaSolicitudAcreditacionSaldo.getPuntoVenta().getRecargaPuntoVentaCota() != null) {
						recargaSolicitudAcreditacionSaldo.getPuntoVenta().getRecargaPuntoVentaCota()
							.setPuntoVenta(null);
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				result = iRecargaSolicitudAcreditacionSaldoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				result =
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.getById(id, true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/solicitar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo solicitar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo, 
		@Context HttpServletRequest request
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaSolicitudAcreditacionSaldo.setFechaSolicitud(hoy);
				
				IRecargaPuntoVentaUsuarioBean iRecargaPuntoVentaUsuarioBean = lookupRecargaPuntoVentaUsuarioBean();
				
				Usuario usuario = new Usuario();
				usuario.setId(loggedUsuarioId);
				
				PuntoVenta puntoVenta = iRecargaPuntoVentaUsuarioBean.getPuntoVentaByUsuario(usuario);
				
				recargaSolicitudAcreditacionSaldo.setPuntoVenta(puntoVenta);
				
				recargaSolicitudAcreditacionSaldo.setFact(hoy);
				recargaSolicitudAcreditacionSaldo.setFcre(hoy);
				recargaSolicitudAcreditacionSaldo.setTerm(Long.valueOf(1));
				recargaSolicitudAcreditacionSaldo.setUact(loggedUsuarioId);
				recargaSolicitudAcreditacionSaldo.setUcre(loggedUsuarioId);
				
				result = 
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.solicitar(recargaSolicitudAcreditacionSaldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/preaprobar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo preaprobar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo, 
		@Context HttpServletRequest request
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaSolicitudAcreditacionSaldo.setFact(hoy);
				recargaSolicitudAcreditacionSaldo.setTerm(Long.valueOf(1));
				recargaSolicitudAcreditacionSaldo.setUact(loggedUsuarioId);
				
				result = 
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.preaprobar(recargaSolicitudAcreditacionSaldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/aprobar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo aprobar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo, @Context HttpServletRequest request
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaSolicitudAcreditacionSaldo.setFact(hoy);
				recargaSolicitudAcreditacionSaldo.setTerm(Long.valueOf(1));
				recargaSolicitudAcreditacionSaldo.setUact(loggedUsuarioId);
				
				result = 
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.aprobar(recargaSolicitudAcreditacionSaldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/denegar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo denegar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo, 
		@Context HttpServletRequest request
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaSolicitudAcreditacionSaldo.setFact(hoy);
				recargaSolicitudAcreditacionSaldo.setTerm(Long.valueOf(1));
				recargaSolicitudAcreditacionSaldo.setUact(loggedUsuarioId);
				
				result = 
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.denegar(recargaSolicitudAcreditacionSaldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/credito")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo credito(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo, 
		@Context HttpServletRequest request
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaSolicitudAcreditacionSaldo.setFact(hoy);
				recargaSolicitudAcreditacionSaldo.setTerm(Long.valueOf(1));
				recargaSolicitudAcreditacionSaldo.setUact(loggedUsuarioId);
				
				result = 
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.credito(recargaSolicitudAcreditacionSaldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/eliminar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public RecargaSolicitudAcreditacionSaldo eliminar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo, 
		@Context HttpServletRequest request
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaSolicitudAcreditacionSaldoBean iRecargaSolicitudAcreditacionSaldoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaSolicitudAcreditacionSaldo.setFact(hoy);
				recargaSolicitudAcreditacionSaldo.setTerm(Long.valueOf(1));
				recargaSolicitudAcreditacionSaldo.setUact(loggedUsuarioId);
				
				result = 
					prepareJSON(iRecargaSolicitudAcreditacionSaldoBean.eliminar(recargaSolicitudAcreditacionSaldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private RecargaSolicitudAcreditacionSaldo prepareJSON(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = recargaSolicitudAcreditacionSaldo;
		
		result.setArchivosAdjuntos(new HashSet<RecargaSolicitudAcreditacionSaldoArchivoAdjunto>());
		
		if (result.getEmpresaRecargaBancoCuenta() != null
			&& result.getEmpresaRecargaBancoCuenta().getEmpresa() != null) {
			result.getEmpresaRecargaBancoCuenta()
				.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getEmpresaRecargaBancoCuenta()
				.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		}
		
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
			result.getPuntoVenta().getRecargaPuntoVentaCota()
				.setPuntoVenta(null);
		}
		
		return result;
	}
	
	private IRecargaSolicitudAcreditacionSaldoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RecargaSolicitudAcreditacionSaldoBean.class.getSimpleName();
		String remoteInterfaceName = IRecargaSolicitudAcreditacionSaldoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRecargaSolicitudAcreditacionSaldoBean) context.lookup(lookupName);
	}
	
	private IRecargaPuntoVentaUsuarioBean lookupRecargaPuntoVentaUsuarioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RecargaPuntoVentaUsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IRecargaPuntoVentaUsuarioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRecargaPuntoVentaUsuarioBean) context.lookup(lookupName);
	}
}