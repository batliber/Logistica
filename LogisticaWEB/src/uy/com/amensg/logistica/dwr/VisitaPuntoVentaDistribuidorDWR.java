package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.VisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidorTO;
import uy.com.amensg.logistica.util.Constants;

@RemoteProxy
public class VisitaPuntoVentaDistribuidorDWR {

	private IVisitaPuntoVentaDistribuidorBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = VisitaPuntoVentaDistribuidorBean.class.getSimpleName();
		String remoteInterfaceName = IVisitaPuntoVentaDistribuidorBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IVisitaPuntoVentaDistribuidorBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataConsulta metadataConsulta = MetadataConsultaDWR.transform(metadataConsultaTO);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iVisitaPuntoVentaDistribuidorBean.list(metadataConsulta);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object visitaPuntoVentaDistribuidor : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((VisitaPuntoVentaDistribuidor) visitaPuntoVentaDistribuidor));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listMisVisitasContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("distribuidor.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(usuarioId.toString());
				
				metadataCondicion.setValores(valores);
				
				MetadataConsulta metadataConsulta = MetadataConsultaDWR.transform(metadataConsultaTO);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iVisitaPuntoVentaDistribuidorBean.list(metadataConsulta);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object visitaPuntoVentaDistribuidor : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((VisitaPuntoVentaDistribuidor) visitaPuntoVentaDistribuidor));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listMisVisitasContextAndLocationAware(
		MetadataConsultaTO metadataConsultaTO, Double latitud, Double longitud
	) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("distribuidor.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(usuarioId.toString());
				
				metadataCondicion.setValores(valores);
				
				MetadataConsulta metadataConsulta = MetadataConsultaDWR.transform(metadataConsultaTO);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iVisitaPuntoVentaDistribuidorBean.list(metadataConsulta);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				List<VisitaPuntoVentaDistribuidorTO> toOrder = new LinkedList<VisitaPuntoVentaDistribuidorTO>();
				for (Object visitaPuntoVentaDistribuidor : metadataConsultaResultado.getRegistrosMuestra()) {
					VisitaPuntoVentaDistribuidorTO visitaPuntoVentaDistribuidorTO = 
						transform((VisitaPuntoVentaDistribuidor) visitaPuntoVentaDistribuidor);
					
					toOrder.add(visitaPuntoVentaDistribuidorTO);
				}
				
				
				Collections.sort(toOrder, new ComparatorLocation(latitud, longitud));
				
				registrosMuestra.addAll(toOrder);
				
				result.setRegistrosMuestra(registrosMuestra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = 
					iVisitaPuntoVentaDistribuidorBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countMisVisitasContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("distribuidor.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(usuarioId.toString());
				
				metadataCondicion.setValores(valores);
				
				MetadataConsulta metadataConsulta = MetadataConsultaDWR.transform(metadataConsultaTO);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iVisitaPuntoVentaDistribuidorBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public VisitaPuntoVentaDistribuidorTO getById(Long id) {
		VisitaPuntoVentaDistribuidorTO result = null;
		
		try {
			IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
			
			VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = iVisitaPuntoVentaDistribuidorBean.getById(id);
			if (visitaPuntoVentaDistribuidor != null) {
				result = transform(visitaPuntoVentaDistribuidor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(VisitaPuntoVentaDistribuidorTO visitaPuntoVentaDistribuidorTO) {
		try {
			IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
			
			iVisitaPuntoVentaDistribuidorBean.save(transform(visitaPuntoVentaDistribuidorTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void remove(VisitaPuntoVentaDistribuidorTO visitaPuntoVentaDistribuidorTO) {
		try {
			IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
			
			iVisitaPuntoVentaDistribuidorBean.remove(transform(visitaPuntoVentaDistribuidorTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(VisitaPuntoVentaDistribuidorTO visitaPuntoVentaDistribuidorTO) {
		try {
			IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
			
			iVisitaPuntoVentaDistribuidorBean.update(transform(visitaPuntoVentaDistribuidorTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearVisitas(
		UsuarioTO distribuidorTO, String observaciones, MetadataConsultaTO metadataConsultaTO
	) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				iVisitaPuntoVentaDistribuidorBean.crearVisitas(
					UsuarioDWR.transform(distribuidorTO),
					observaciones,
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearVisitasPermanentes(
		UsuarioTO distribuidorTO, String observaciones, MetadataConsultaTO metadataConsultaTO
	) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				iVisitaPuntoVentaDistribuidorBean.crearVisitasPermanentes(
					UsuarioDWR.transform(distribuidorTO),
					observaciones,
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearVisitasPorSubLotes(
		UsuarioTO distribuidorTO, String observaciones, MetadataConsultaTO metadataConsultaTO
	) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				iVisitaPuntoVentaDistribuidorBean.crearVisitasPorSubLotes(
					UsuarioDWR.transform(distribuidorTO),
					observaciones,
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = 
					iVisitaPuntoVentaDistribuidorBean.exportarAExcel(
						MetadataConsultaDWR.transform(metadataConsultaTO), 
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static VisitaPuntoVentaDistribuidorTO transform(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor) {
		VisitaPuntoVentaDistribuidorTO result = new VisitaPuntoVentaDistribuidorTO();
		
		result.setFechaAsignacion(visitaPuntoVentaDistribuidor.getFechaAsignacion());
		result.setFechaVisita(visitaPuntoVentaDistribuidor.getFechaVisita());
		result.setObservaciones(visitaPuntoVentaDistribuidor.getObservaciones());
		
		if (visitaPuntoVentaDistribuidor.getDistribuidor() != null) {
			result.setDistribuidor(UsuarioDWR.transform(visitaPuntoVentaDistribuidor.getDistribuidor(), false));
		}
		
		if (visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor() != null) {
			result.setEstadoVisitaPuntoVentaDistribuidor(
				EstadoVisitaPuntoVentaDistribuidorDWR.transform(
					visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor()
				)
			);
		}
		
		if (visitaPuntoVentaDistribuidor.getPuntoVenta() != null) {
			result.setPuntoVenta(PuntoVentaDWR.transform(visitaPuntoVentaDistribuidor.getPuntoVenta()));
		}
		
		result.setFcre(visitaPuntoVentaDistribuidor.getFcre());
		result.setFact(visitaPuntoVentaDistribuidor.getFact());
		result.setId(visitaPuntoVentaDistribuidor.getId());
		result.setTerm(visitaPuntoVentaDistribuidor.getTerm());
		result.setUact(visitaPuntoVentaDistribuidor.getUact());
		result.setUact(visitaPuntoVentaDistribuidor.getUcre());
		
		return result;
	}
	
	public static VisitaPuntoVentaDistribuidor transform(VisitaPuntoVentaDistribuidorTO visitaPuntoVentaDistribuidorTO) {
		VisitaPuntoVentaDistribuidor result = new VisitaPuntoVentaDistribuidor();
		
		result.setFechaAsignacion(visitaPuntoVentaDistribuidorTO.getFechaAsignacion());
		result.setFechaVisita(visitaPuntoVentaDistribuidorTO.getFechaVisita());
		result.setObservaciones(visitaPuntoVentaDistribuidorTO.getObservaciones());
		
		if (visitaPuntoVentaDistribuidorTO.getDistribuidor() != null) {
			Usuario distribuidor = new Usuario();
			distribuidor.setId(visitaPuntoVentaDistribuidorTO.getDistribuidor().getId());
			
			result.setDistribuidor(distribuidor);
		}
		
		if (visitaPuntoVentaDistribuidorTO.getEstadoVisitaPuntoVentaDistribuidor() != null) {
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				new EstadoVisitaPuntoVentaDistribuidor();
			estadoVisitaPuntoVentaDistribuidor.setId(
				visitaPuntoVentaDistribuidorTO.getEstadoVisitaPuntoVentaDistribuidor().getId()
			);
			
			result.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
		}
		
		if (visitaPuntoVentaDistribuidorTO.getPuntoVenta() != null) {
			PuntoVenta puntoVenta = new PuntoVenta();
			puntoVenta.setId(visitaPuntoVentaDistribuidorTO.getPuntoVenta().getId());
			
			result.setPuntoVenta(puntoVenta);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(visitaPuntoVentaDistribuidorTO.getFcre());
		result.setFact(date);
		result.setId(visitaPuntoVentaDistribuidorTO.getId());
		result.setTerm(visitaPuntoVentaDistribuidorTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(visitaPuntoVentaDistribuidorTO.getUcre());
		
		return result;
	}

	class ComparatorLocation implements Comparator<VisitaPuntoVentaDistribuidorTO> {
		
		private final Double latitudFinal;
		private final Double longitudFinal;
		
		public ComparatorLocation(Double latitud, Double longitud) {
			this.latitudFinal = latitud;
			this.longitudFinal = longitud;
		}
		
		public int compare(VisitaPuntoVentaDistribuidorTO o1, VisitaPuntoVentaDistribuidorTO o2) {
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