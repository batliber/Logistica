package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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

import uy.com.amensg.logistica.bean.IPuntoVentaBean;
import uy.com.amensg.logistica.bean.PuntoVentaBean;
import uy.com.amensg.logistica.entities.CrearVisitaPuntoVentaDistribuidorTO;
import uy.com.amensg.logistica.entities.EstadoPuntoVenta;
import uy.com.amensg.logistica.entities.ListPuntoVentaByBarrioLocationAwareTO;
import uy.com.amensg.logistica.entities.ListPuntoVentaByDepartamentoLocationAwareTO;
import uy.com.amensg.logistica.entities.ListPuntoVentaCreatedORAssignedLocationAwareTO;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;

@Path("/PuntoVentaREST")
public class PuntoVentaREST {
	
	@GET
	@Path("/listMinimal")
	@Produces("application/json")
	public Collection<PuntoVenta> listMinimal(@Context HttpServletRequest request) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
				result = iPuntoVentaBean.listMinimal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@GET
	@Path("/listMinimalByDepartamentoId/{departamentoId}")
	@Produces("application/json")
	public Collection<PuntoVenta> listMinimalByDepartamentoId(
		@PathParam("departamentoId") Long departamentoId, @Context HttpServletRequest request
	) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				result = iPuntoVentaBean.listMinimalByDepartamentoId(departamentoId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listMinimalByDepartamentoIdLocationAware")
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<PuntoVenta> listMinimalByDepartamentoIdLocationAware(
		ListPuntoVentaByDepartamentoLocationAwareTO listPuntoVentaByDepartamentoLocationAwareTO, 
		@Context HttpServletRequest request
	) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				List<PuntoVenta> toOrder = new LinkedList<PuntoVenta>();
				
				toOrder.addAll(
					iPuntoVentaBean.listMinimalByDepartamentoId(
						listPuntoVentaByDepartamentoLocationAwareTO.getDepartamentoId()
					)
				);
				
				Collections.sort(
					toOrder, 
					new ComparatorLocation(
						listPuntoVentaByDepartamentoLocationAwareTO.getLatitud(),
						listPuntoVentaByDepartamentoLocationAwareTO.getLongitud()
					)
				);
				
				result = toOrder;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listMinimalByBarrioId/{barrioId}")
	@Produces("application/json")
	public Collection<PuntoVenta> listMinimalByBarrioId(
		@PathParam("barrioId") Long barrioId, @Context HttpServletRequest request
	) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				result = iPuntoVentaBean.listMinimalByBarrioId(barrioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listMinimalByBarrioIdLocationAware")
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<PuntoVenta> listMinimalByBarrioIdLocationAware(
		ListPuntoVentaByBarrioLocationAwareTO listPuntoVentaByBarrioLocationAwareTO, 
		@Context HttpServletRequest request
	) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				List<PuntoVenta> toOrder = new LinkedList<PuntoVenta>();
				
				toOrder.addAll(
					iPuntoVentaBean.listMinimalByBarrioId(
						listPuntoVentaByBarrioLocationAwareTO.getBarrioId()
					)
				);
				
				Collections.sort(
					toOrder, 
					new ComparatorLocation(
						listPuntoVentaByBarrioLocationAwareTO.getLatitud(),
						listPuntoVentaByBarrioLocationAwareTO.getLongitud()
					)
				);
				
				result = toOrder;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listMinimalCreatedORAssignedContextAndLocationAware")
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<PuntoVenta> listMinimalCreatedORAssignedContextAndLocationAware(
		ListPuntoVentaCreatedORAssignedLocationAwareTO listPuntoVentaCreatedORAssignedLocationAwareTO,
		@Context HttpServletRequest request
	) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				List<PuntoVenta> toOrder = new LinkedList<PuntoVenta>();
				
				toOrder.addAll(
					iPuntoVentaBean.listMinimalCreatedORAssignedByUsuarioId(
						listPuntoVentaCreatedORAssignedLocationAwareTO.getDepartamentoId(),
						listPuntoVentaCreatedORAssignedLocationAwareTO.getBarrioId(),
						listPuntoVentaCreatedORAssignedLocationAwareTO.getPuntoVentaId(),
						listPuntoVentaCreatedORAssignedLocationAwareTO.getEstadoVisitaPuntoVentaDistribuidorId(),
						loggedUsuarioId
					)
				);
				
				Collections.sort(
					toOrder, 
					new ComparatorLocation(
						listPuntoVentaCreatedORAssignedLocationAwareTO.getLatitud(),
						listPuntoVentaCreatedORAssignedLocationAwareTO.getLongitud()
					)
				);
				
				result = toOrder;
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
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				result = iPuntoVentaBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					PuntoVenta puntoVenta = (PuntoVenta) object;
					
					if (puntoVenta.getDistribuidor() != null) {
						puntoVenta.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (puntoVenta.getCreador() != null) {
						puntoVenta.getCreador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (puntoVenta.getRecargaPuntoVentaCota() != null) {
						puntoVenta.getRecargaPuntoVentaCota().setPuntoVenta(null);
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
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				result = iPuntoVentaBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/crearVisitas")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void crearVisitas(
		CrearVisitaPuntoVentaDistribuidorTO crearVisitaPuntoVentaDistribuidorTO, 
		@Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");

				Usuario distribuidor = crearVisitaPuntoVentaDistribuidorTO.getDistribuidor();
				String observaciones = crearVisitaPuntoVentaDistribuidorTO.getObservaciones();
				MetadataConsulta metadataConsulta = crearVisitaPuntoVentaDistribuidorTO.getMetadataConsulta();
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				iPuntoVentaBean.crearVisitas(
					distribuidor,
					observaciones,
					metadataConsulta,
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/crearVisitasPermanentes")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void crearVisitasPermanentes(
		CrearVisitaPuntoVentaDistribuidorTO crearVisitaPuntoVentaDistribuidorTO, 
		@Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario distribuidor = crearVisitaPuntoVentaDistribuidorTO.getDistribuidor();
				String observaciones = crearVisitaPuntoVentaDistribuidorTO.getObservaciones();
				MetadataConsulta metadataConsulta = crearVisitaPuntoVentaDistribuidorTO.getMetadataConsulta();
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				iPuntoVentaBean.crearVisitasPermanentes(
					distribuidor,
					observaciones,
					metadataConsulta,
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public PuntoVenta getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		PuntoVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				result = iPuntoVentaBean.getById(id);
				
				if (result.getDistribuidor() != null) {
					result.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getCreador() != null) {
					result.getCreador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getRecargaPuntoVentaCota() != null) {
					result.getRecargaPuntoVentaCota().setPuntoVenta(null);
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
	public PuntoVenta add(PuntoVenta puntoVenta, @Context HttpServletRequest request) {
		PuntoVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				puntoVenta.setFact(hoy);
				puntoVenta.setFcre(hoy);
				puntoVenta.setTerm(Long.valueOf(1));
				puntoVenta.setUact(loggedUsuarioId);
				puntoVenta.setUcre(loggedUsuarioId);
				
				result = iPuntoVentaBean.save(puntoVenta);
				
				if (result.getRecargaPuntoVentaCota() != null) {
					result.getRecargaPuntoVentaCota().setPuntoVenta(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/addMobile")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public PuntoVenta addMobile(PuntoVenta puntoVenta, @Context HttpServletRequest request) {
		PuntoVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				EstadoPuntoVenta estadoPuntoVenta = new EstadoPuntoVenta();
				estadoPuntoVenta.setId(
					Long.parseLong(Configuration.getInstance().getProperty("estadoPuntoVenta.Pendiente"))
				);
				
				puntoVenta.setEstadoPuntoVenta(estadoPuntoVenta);
				
				puntoVenta.setFact(hoy);
				puntoVenta.setFcre(hoy);
				puntoVenta.setTerm(Long.valueOf(1));
				puntoVenta.setUact(loggedUsuarioId);
				puntoVenta.setUcre(loggedUsuarioId);
				
				// Guarddar Punto de Venta.
				result = iPuntoVentaBean.save(puntoVenta);
				
				if (result.getRecargaPuntoVentaCota() != null) {
					result.getRecargaPuntoVentaCota().setPuntoVenta(null);
				}
				
				// Crear visita de autor para el usuario logueado.
				Usuario distribuidor = new Usuario();
				distribuidor.setId(loggedUsuarioId);
				
				iPuntoVentaBean.crearVisitaAutor(
					distribuidor, 
					"Visita de autor", 
					result, 
					loggedUsuarioId
				);
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
	public void update(PuntoVenta PuntoVenta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				PuntoVenta.setFact(hoy);
				PuntoVenta.setTerm(Long.valueOf(1));
				PuntoVenta.setUact(loggedUsuarioId);
				
				iPuntoVentaBean.update(PuntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(PuntoVenta PuntoVenta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				PuntoVenta.setFact(hoy);
				PuntoVenta.setTerm(Long.valueOf(1));
				PuntoVenta.setUact(loggedUsuarioId);
				
				iPuntoVentaBean.remove(PuntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IPuntoVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = PuntoVentaBean.class.getSimpleName();
		String remoteInterfaceName = IPuntoVentaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IPuntoVentaBean) context.lookup(lookupName);
	}

	class ComparatorLocation implements Comparator<PuntoVenta> {
		
		private final Double latitudFinal;
		private final Double longitudFinal;
		
		public ComparatorLocation(Double latitud, Double longitud) {
			this.latitudFinal = latitud;
			this.longitudFinal = longitud;
		}
		
		public int compare(PuntoVenta o1, PuntoVenta o2) {
			if (latitudFinal == null || longitudFinal == null) {
				// Si la ubicación con la cual comparar es null, son iguales.
				return 0;
			}
			
			if (o1.getLatitud() == null || o1.getLongitud() == null) {
				// Si la ubicación del punto de venta o1 es null
				if (o2.getLatitud() == null || o2.getLongitud() == null) {
					// Si la ubicación del punto de venta o2 es null, entonces son iguales.
					return 0;
				} else {
					// Si la ubicación del punto de venta o2 no es null, entonces la distancia al punto de venta o2 es menor.
					return 1;
				}
			} else if (o2.getLatitud() == null || o2.getLongitud() == null) {
				// Si la ubicación del punto de venta o1 no es null y la del punto de venta o2 es null, entnoces la distancia al punto de venta o1 es menor.
				return -1;
			} else {
				// Si todas las ubicaciones están definidas, entonces las comparamos:
				Double distanceO1 = 
					3959 
					* Math.acos(Math.cos(Math.toRadians(o1.getLatitud())) 
					* Math.cos(Math.toRadians(latitudFinal)) 
					* Math.cos(Math.toRadians(longitudFinal) - Math.toRadians(o1.getLongitud())) 
					+ Math.sin(Math.toRadians(o1.getLatitud())) * Math.sin(Math.toRadians(latitudFinal)));
				
				Double distanceO2 = 
					3959 
					* Math.acos(Math.cos(Math.toRadians(o2.getLatitud())) 
					* Math.cos(Math.toRadians(latitudFinal)) 
					* Math.cos(Math.toRadians(longitudFinal) - Math.toRadians(o2.getLongitud())) 
					+ Math.sin(Math.toRadians(o2.getLatitud())) * Math.sin(Math.toRadians(latitudFinal)));
				
				return distanceO1.compareTo(distanceO2);
			}
		}				
	}
}