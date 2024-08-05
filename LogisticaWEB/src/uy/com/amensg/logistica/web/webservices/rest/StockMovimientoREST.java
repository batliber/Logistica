package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
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
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import uy.com.amensg.logistica.bean.IStockMovimientoBean;
import uy.com.amensg.logistica.bean.StockMovimientoBean;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.StockActual;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.web.entities.TransferirStockTO;

@Path("/StockMovimientoREST")
public class StockMovimientoREST {

	@POST
	@Path("/listStockActualContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				result = iStockMovimientoBean.listStockActual(metadataConsulta, loggedUsuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					StockActual stockActual = (StockActual) object;
					
					stockActual.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					stockActual.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countStockActualContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countStockActualContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				result = iStockMovimientoBean.countStockActual(metadataConsulta, loggedUsuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listByIMEI/{IMEI}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<StockMovimiento> listByIMEI(
		@PathParam("IMEI") String IMEI, @Context HttpServletRequest request
	) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				result = iStockMovimientoBean.listByIMEI(IMEI);
				
				for (StockMovimiento stockMovimiento : result) {
					stockMovimiento.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					stockMovimiento.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listStockByEmpresaId/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<StockMovimiento> listStockByEmpresaId(
		@PathParam("id") Long id, @Context HttpServletRequest request
	) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
				result = iStockMovimientoBean.listStockByEmpresaId(id);
				
				for (StockMovimiento stockMovimiento : result) {
					if (stockMovimiento.getEmpresa() != null) {
						stockMovimiento.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
						stockMovimiento.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listStockByEmpresaTipoProducto")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<StockMovimiento> listStockByEmpresaTipoProducto(
		@Context UriInfo uriInfo, @Context HttpServletRequest request
	) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
				
				if (queryParams.size() >= 2) {
					Empresa empresa = new Empresa();
					empresa.setId(Long.parseLong(queryParams.get("eid").get(0)));
					
					TipoProducto tipoProducto = new TipoProducto();
					tipoProducto.setId(Long.parseLong(queryParams.get("tid").get(0)));
					
					result = 
						iStockMovimientoBean.listStockByEmpresaTipoProducto(
							empresa, 
							tipoProducto
						);
					
					for (StockMovimiento stockMovimiento : result) {
						if (stockMovimiento.getEmpresa() != null) {
							stockMovimiento.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
							stockMovimiento.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getLastByIMEI/{IMEI}")
	@Produces({ MediaType.APPLICATION_JSON })
	public StockMovimiento getLastByIMEI(
		@PathParam("IMEI") String IMEI, @Context HttpServletRequest request
	) {
		StockMovimiento result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				result = iStockMovimientoBean.getLastByIMEI(IMEI);
				
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
	public StockMovimiento add(StockMovimiento stockMovimiento, @Context HttpServletRequest request) {
		StockMovimiento result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				stockMovimiento.setFecha(hoy);
				stockMovimiento.setCantidad(
					stockMovimiento.getCantidad() 
					* stockMovimiento.getStockTipoMovimiento().getSigno()
				);
				
				stockMovimiento.setFact(hoy);
				stockMovimiento.setFcre(hoy);
				stockMovimiento.setTerm(Long.valueOf(1));
				stockMovimiento.setUact(loggedUsuarioId);
				stockMovimiento.setUcre(loggedUsuarioId);
				
				result = iStockMovimientoBean.save(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/addByIMEI")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void add(Collection<StockMovimiento> stockMovimientos, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				for (StockMovimiento stockMovimiento : stockMovimientos) {
					stockMovimiento.setFecha(hoy);
					stockMovimiento.setCantidad(
						stockMovimiento.getCantidad() * 
						stockMovimiento.getStockTipoMovimiento().getSigno()
					);
					
					stockMovimiento.setFact(hoy);
					stockMovimiento.setFcre(hoy);
					stockMovimiento.setTerm(Long.valueOf(1));
					stockMovimiento.setUact(loggedUsuarioId);
					stockMovimiento.setUcre(loggedUsuarioId);
				}
				
				iStockMovimientoBean.save(stockMovimientos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/transferir")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void transferir(TransferirStockTO transferirStockTO, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IStockMovimientoBean iStockMovimientoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				for (StockMovimiento stockMovimiento : transferirStockTO.getStockMovimientos()) {
					stockMovimiento.setFecha(hoy);
					
					stockMovimiento.setFact(hoy);
					stockMovimiento.setFcre(hoy);
					stockMovimiento.setTerm(Long.valueOf(1));
					stockMovimiento.setUact(loggedUsuarioId);
					stockMovimiento.setUcre(loggedUsuarioId);
				}
				
				iStockMovimientoBean.transferir(
					transferirStockTO.getStockMovimientos(), 
					transferirStockTO.getEmpresaDestinoId()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IStockMovimientoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = StockMovimientoBean.class.getSimpleName();
		String remoteInterfaceName = IStockMovimientoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IStockMovimientoBean) context.lookup(lookupName);
	}
}