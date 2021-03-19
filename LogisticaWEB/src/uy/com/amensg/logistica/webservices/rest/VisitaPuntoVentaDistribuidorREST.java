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

import uy.com.amensg.logistica.bean.IVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.VisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.entities.CrearVisitaPuntoVentaDistribuidorTO;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaLocationAware;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.entities.VisitaPorSubloteTO;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.util.Constants;

@Path("/VisitaPuntoVentaDistribuidorREST")
public class VisitaPuntoVentaDistribuidorREST {

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
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = 
						(VisitaPuntoVentaDistribuidor) object;
					
					visitaPuntoVentaDistribuidor.getDistribuidor()
						.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					
					if (visitaPuntoVentaDistribuidor.getPuntoVenta().getDistribuidor() != null) {
						visitaPuntoVentaDistribuidor.getPuntoVenta().getDistribuidor()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (visitaPuntoVentaDistribuidor.getPuntoVenta().getCreador() != null) {
						visitaPuntoVentaDistribuidor.getPuntoVenta().getCreador()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listMisVisitasContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listMisVisitasContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("distribuidor.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(usuarioId.toString());
				
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = 
						(VisitaPuntoVentaDistribuidor) object;
					
					visitaPuntoVentaDistribuidor.getDistribuidor()
						.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					
					if (visitaPuntoVentaDistribuidor.getPuntoVenta().getDistribuidor() != null) {
						visitaPuntoVentaDistribuidor.getPuntoVenta().getDistribuidor()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (visitaPuntoVentaDistribuidor.getPuntoVenta().getCreador() != null) {
						visitaPuntoVentaDistribuidor.getPuntoVenta().getCreador()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listMisVisitasContextAndLocationAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listMisVisitasContextAndLocationAware(
		MetadataConsultaLocationAware metadataConsultaLocationAware, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("distribuidor.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(usuarioId.toString());
				
				metadataCondicion.setValores(valores);
				
				metadataConsultaLocationAware.getMetadataConsulta().getMetadataCondiciones()
					.add(metadataCondicion);
				
//				metadataCondicion = new MetadataCondicion();
//				metadataCondicion.setCampo("estadoVisitaPuntoVentaDistribuidor.id");
//				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_NOT_IGUAL);
//				
//				valores = new LinkedList<String>();
//				valores.add(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Visitado"));
//				
//				metadataCondicion.setValores(valores);
//				
//				metadataConsultaLocationAware.getMetadataConsulta().getMetadataCondiciones()
//					.add(metadataCondicion);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.list(
					metadataConsultaLocationAware.getMetadataConsulta()
				);
				
				List<VisitaPuntoVentaDistribuidor> toOrder = new LinkedList<VisitaPuntoVentaDistribuidor>();
				
				for (Object object : result.getRegistrosMuestra()) {
					VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = 
						(VisitaPuntoVentaDistribuidor) object;
					
					visitaPuntoVentaDistribuidor.getDistribuidor()
						.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					
					if (visitaPuntoVentaDistribuidor.getPuntoVenta().getDistribuidor() != null) {
						visitaPuntoVentaDistribuidor.getPuntoVenta().getDistribuidor()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (visitaPuntoVentaDistribuidor.getPuntoVenta().getCreador() != null) {
						visitaPuntoVentaDistribuidor.getPuntoVenta().getCreador()
							.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					toOrder.add(visitaPuntoVentaDistribuidor);
				}
				
				Collections.sort(
					toOrder, 
					new ComparatorLocation(
						metadataConsultaLocationAware.getLatitud(), 
						metadataConsultaLocationAware.getLongitud()
					)
				);
				
				result.getRegistrosMuestra().clear();
				result.getRegistrosMuestra().addAll(toOrder);
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
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countMisVisitasContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countMisVisitasContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("distribuidor.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(usuarioId.toString());
				
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public VisitaPuntoVentaDistribuidor getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		VisitaPuntoVentaDistribuidor result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.getById(id);
				
				result.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				
				if (result.getPuntoVenta().getDistribuidor() != null) {
					result.getPuntoVenta().getDistribuidor()
						.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getPuntoVenta().getCreador() != null) {
					result.getPuntoVenta().getCreador()
						.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
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
	public VisitaPuntoVentaDistribuidor add(
		VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor, @Context HttpServletRequest request
	) {
		VisitaPuntoVentaDistribuidor result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				visitaPuntoVentaDistribuidor.setFact(hoy);
				visitaPuntoVentaDistribuidor.setFcre(hoy);
				visitaPuntoVentaDistribuidor.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
				visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
				
				result = iVisitaPuntoVentaDistribuidorBean.save(visitaPuntoVentaDistribuidor);
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
	public void update(
		VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				visitaPuntoVentaDistribuidor.setFact(hoy);
				visitaPuntoVentaDistribuidor.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
				
				iVisitaPuntoVentaDistribuidorBean.update(visitaPuntoVentaDistribuidor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(
		VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				visitaPuntoVentaDistribuidor.setFact(hoy);
				visitaPuntoVentaDistribuidor.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
				
				iVisitaPuntoVentaDistribuidorBean.remove(visitaPuntoVentaDistribuidor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/crearVisitasPorSubLotes")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void crearVisitasPorSubLotes(
		VisitaPorSubloteTO visitaPorSubloteTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				Usuario distribuidor = new Usuario();
				distribuidor.setId(visitaPorSubloteTO.getDistribuidorId());
				
				iVisitaPuntoVentaDistribuidorBean.crearVisitasPorSubLotes(
					distribuidor,
					visitaPorSubloteTO.getObservaciones(),
					visitaPorSubloteTO.getMetadataConsulta(),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				iVisitaPuntoVentaDistribuidorBean.crearVisitas(
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
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				iVisitaPuntoVentaDistribuidorBean.crearVisitasPermanentes(
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
	@Path("/exportarAExcel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcel(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = 
					lookupBean();
				
				String nombreArchivo = 
					iVisitaPuntoVentaDistribuidorBean.exportarAExcel(metadataConsulta, usuarioId);
				
				if (nombreArchivo != null) {
					result = new ResultadoExportacionArchivoTO();
					result.setNombreArchivo(nombreArchivo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IVisitaPuntoVentaDistribuidorBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = VisitaPuntoVentaDistribuidorBean.class.getSimpleName();
		String remoteInterfaceName = IVisitaPuntoVentaDistribuidorBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IVisitaPuntoVentaDistribuidorBean) context.lookup(lookupName);
	}

	class ComparatorLocation implements Comparator<VisitaPuntoVentaDistribuidor> {
		
		private final Double latitudFinal;
		private final Double longitudFinal;
		
		public ComparatorLocation(Double latitud, Double longitud) {
			this.latitudFinal = latitud;
			this.longitudFinal = longitud;
		}
		
		public int compare(VisitaPuntoVentaDistribuidor o1, VisitaPuntoVentaDistribuidor o2) {
			if (latitudFinal == null || longitudFinal == null) {
				// Si la ubicación con la cual comparar es null, son iguales.
				return 0;
			}
			
			if (o1.getPuntoVenta().getLatitud() == null || o1.getPuntoVenta().getLongitud() == null) {
				// Si la ubicación del punto de venta o1 es null
				if (o2.getPuntoVenta().getLatitud() == null || o2.getPuntoVenta().getLongitud() == null) {
					// Si la ubicación del punto de venta o2 es null, entonces son iguales.
					return 0;
				} else {
					// Si la ubicación del punto de venta o2 no es null, entonces la distancia al punto de venta o2 es menor.
					return 1;
				}
			} else if (o2.getPuntoVenta().getLatitud() == null || o2.getPuntoVenta().getLongitud() == null) {
				// Si la ubicación del punto de venta o1 no es null y la del punto de venta o2 es null, entnoces la distancia al punto de venta o1 es menor.
				return -1;
			} else {
				// Si todas las ubicaciones están definidas, entonces las comparamos:
				Double distanceO1 = 
					3959 
					* Math.acos(Math.cos(Math.toRadians(o1.getPuntoVenta().getLatitud())) 
					* Math.cos(Math.toRadians(latitudFinal)) 
					* Math.cos(Math.toRadians(longitudFinal) - Math.toRadians(o1.getPuntoVenta().getLongitud())) 
					+ Math.sin(Math.toRadians(o1.getPuntoVenta().getLatitud())) * Math.sin(Math.toRadians(latitudFinal)));
				
				Double distanceO2 = 
					3959 
					* Math.acos(Math.cos(Math.toRadians(o2.getPuntoVenta().getLatitud())) 
					* Math.cos(Math.toRadians(latitudFinal)) 
					* Math.cos(Math.toRadians(longitudFinal) - Math.toRadians(o2.getPuntoVenta().getLongitud())) 
					+ Math.sin(Math.toRadians(o2.getPuntoVenta().getLatitud())) * Math.sin(Math.toRadians(latitudFinal)));
				
				return distanceO1.compareTo(distanceO2);
			}
		}				
	}
}