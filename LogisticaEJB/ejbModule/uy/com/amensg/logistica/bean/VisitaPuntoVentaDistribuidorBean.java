package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.CalculoPorcentajeActivacionPuntoVenta;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class VisitaPuntoVentaDistribuidorBean implements IVisitaPuntoVentaDistribuidorBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IEstadoVisitaPuntoVentaDistribuidorBean iEstadoVisitaPuntoVentaDistribuidorBean;
	
	@EJB
	private IPuntoVentaBean iPuntoVentaBean;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<VisitaPuntoVentaDistribuidor>().list(
				entityManager, metadataConsulta, new VisitaPuntoVentaDistribuidor()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<VisitaPuntoVentaDistribuidor>().count(
				entityManager, metadataConsulta, new VisitaPuntoVentaDistribuidor()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public VisitaPuntoVentaDistribuidor getById(Long id) {
		VisitaPuntoVentaDistribuidor result = null;
		
		try {
			result = entityManager.find(VisitaPuntoVentaDistribuidor.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public VisitaPuntoVentaDistribuidor getLastByPuntoVentaDistribuidor(Usuario distribuidor, PuntoVenta puntoVenta) {
		VisitaPuntoVentaDistribuidor result = null;
		
		try {
			TypedQuery<VisitaPuntoVentaDistribuidor> query =
				entityManager.createQuery(
					"SELECT v"
					+ " FROM VisitaPuntoVentaDistribuidor v"
					+ " WHERE v.puntoVenta = :puntoVenta"
					+ "	AND v.distribuidor = :distribuidor"
					+ " ORDER BY v.id DESC",
					VisitaPuntoVentaDistribuidor.class
				);
			query.setParameter("puntoVenta", puntoVenta);
			query.setParameter("distribuidor", distribuidor);
			
			List<VisitaPuntoVentaDistribuidor> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor) {
		try {
			visitaPuntoVentaDistribuidor.setFcre(visitaPuntoVentaDistribuidor.getFact());
			visitaPuntoVentaDistribuidor.setUcre(visitaPuntoVentaDistribuidor.getUact());
			
			entityManager.persist(visitaPuntoVentaDistribuidor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor) {
		try {
			VisitaPuntoVentaDistribuidor managedVisitaPuntoVentaDistribuidor = 
				entityManager.find(VisitaPuntoVentaDistribuidor.class, visitaPuntoVentaDistribuidor.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidorCancelado = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Cancelado"))
				);
			
			managedVisitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(
				estadoVisitaPuntoVentaDistribuidorCancelado
			);
			
			managedVisitaPuntoVentaDistribuidor.setFact(date);
			managedVisitaPuntoVentaDistribuidor.setTerm(visitaPuntoVentaDistribuidor.getTerm());
			managedVisitaPuntoVentaDistribuidor.setUact(visitaPuntoVentaDistribuidor.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor) {
		try {
			VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidorManaged = 
				entityManager.find(VisitaPuntoVentaDistribuidor.class, visitaPuntoVentaDistribuidor.getId());
			
			visitaPuntoVentaDistribuidorManaged.setFechaAsignacion(visitaPuntoVentaDistribuidor.getFechaAsignacion());
			visitaPuntoVentaDistribuidorManaged.setFechaVisita(visitaPuntoVentaDistribuidor.getFechaVisita());
			visitaPuntoVentaDistribuidorManaged.setObservaciones(visitaPuntoVentaDistribuidor.getObservaciones());
			
			visitaPuntoVentaDistribuidorManaged.setDistribuidor(visitaPuntoVentaDistribuidor.getDistribuidor());
			
			if (visitaPuntoVentaDistribuidorManaged.getEstadoVisitaPuntoVentaDistribuidor() != null) {
				if (visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor() != null) {
					if (!visitaPuntoVentaDistribuidorManaged.getEstadoVisitaPuntoVentaDistribuidor().equals(
						visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor())) {
						PuntoVenta puntoVentaManaged = visitaPuntoVentaDistribuidorManaged.getPuntoVenta();
						
						puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(
							visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor()
						);
						puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(
							visitaPuntoVentaDistribuidor.getFact()
						);
						
						puntoVentaManaged.setFact(visitaPuntoVentaDistribuidor.getFact());
						puntoVentaManaged.setUact(visitaPuntoVentaDistribuidor.getUact());
						puntoVentaManaged.setTerm(visitaPuntoVentaDistribuidor.getTerm());
					}
				}
			} else {
				if (visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor() != null) {
					PuntoVenta puntoVentaManaged = visitaPuntoVentaDistribuidorManaged.getPuntoVenta();
					
					puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(
						visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor()
					);
					puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(
						visitaPuntoVentaDistribuidor.getFact()
					);
					
					puntoVentaManaged.setFact(visitaPuntoVentaDistribuidor.getFact());
					puntoVentaManaged.setUact(visitaPuntoVentaDistribuidor.getUact());
					puntoVentaManaged.setTerm(visitaPuntoVentaDistribuidor.getTerm());
				}
			}
			
			visitaPuntoVentaDistribuidorManaged.setEstadoVisitaPuntoVentaDistribuidor(
				visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor()
			);
			visitaPuntoVentaDistribuidorManaged.setPuntoVenta(visitaPuntoVentaDistribuidor.getPuntoVenta());
			
			visitaPuntoVentaDistribuidorManaged.setFact(visitaPuntoVentaDistribuidor.getFact());
			visitaPuntoVentaDistribuidorManaged.setTerm(visitaPuntoVentaDistribuidor.getTerm());
			visitaPuntoVentaDistribuidorManaged.setUact(visitaPuntoVentaDistribuidor.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearVisita(Usuario distribuidor, PuntoVenta puntoVenta, Long loggedUsuarioId) {
		Date hoy = GregorianCalendar.getInstance().getTime();
		
		EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
			iEstadoVisitaPuntoVentaDistribuidorBean.getById(
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"))
			);
		
		PuntoVenta puntoVentaManaged = 
			iPuntoVentaBean.getById(puntoVenta.getId());
		
		VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = new VisitaPuntoVentaDistribuidor();
		visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
		visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
		visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
		visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVenta);
		
		visitaPuntoVentaDistribuidor.setFact(hoy);
		visitaPuntoVentaDistribuidor.setFcre(hoy);
		visitaPuntoVentaDistribuidor.setTerm(new Long(1));
		visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
		visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
		
		this.save(visitaPuntoVentaDistribuidor);
		
		puntoVentaManaged.setDistribuidor(distribuidor);
		puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
		puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
		puntoVentaManaged.setFechaAsignacionDistribuidor(hoy);
		
		puntoVentaManaged.setFact(hoy);
		puntoVentaManaged.setUact(loggedUsuarioId);
		puntoVentaManaged.setTerm(new Long(1));
		
		iPuntoVentaBean.update(puntoVentaManaged);
	}
	
	public void crearVisitas(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"))
				);
			
			MetadataConsultaResultado metadataConsultaResultado = 
				new QueryBuilder<CalculoPorcentajeActivacionPuntoVenta>().list(
					entityManager, metadataConsulta, new CalculoPorcentajeActivacionPuntoVenta()
				);
			
			Long i = metadataConsulta.getTamanoSubconjunto() != null ? 
				metadataConsulta.getTamanoSubconjunto() : Long.MAX_VALUE;
			
			Collection<Long> puntoVentaIds = new LinkedList<Long>();
			for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
				CalculoPorcentajeActivacionPuntoVenta calculoPorcentajeActivacionPuntoVenta = 
					(CalculoPorcentajeActivacionPuntoVenta) object;
				
				if (!puntoVentaIds.contains(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getId())) {
					PuntoVenta puntoVenta = calculoPorcentajeActivacionPuntoVenta.getPuntoVenta();
					
					puntoVentaIds.add(puntoVenta.getId());
					
					VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = new VisitaPuntoVentaDistribuidor();
					visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
					visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
					visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
					visitaPuntoVentaDistribuidor.setObservaciones(observaciones);
					visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVenta);
					
					visitaPuntoVentaDistribuidor.setFact(hoy);
					visitaPuntoVentaDistribuidor.setFcre(hoy);
					visitaPuntoVentaDistribuidor.setTerm(new Long(1));
					visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
					visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
					
					this.save(visitaPuntoVentaDistribuidor);
					
					puntoVenta.setDistribuidor(distribuidor);
					puntoVenta.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
					puntoVenta.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
					puntoVenta.setFechaAsignacionDistribuidor(hoy);
					
					puntoVenta.setFact(hoy);
					puntoVenta.setUact(loggedUsuarioId);
					puntoVenta.setTerm(new Long(1));
					
					i--;
					
					if (i == 0) {
						break;
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearVisitasPermanentes(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"))
				);
			
			MetadataConsultaResultado metadataConsultaResultado = 
				new QueryBuilder<CalculoPorcentajeActivacionPuntoVenta>().list(
					entityManager, metadataConsulta, new CalculoPorcentajeActivacionPuntoVenta()
				);
			
			Long i = metadataConsulta.getTamanoSubconjunto() != null ? 
				metadataConsulta.getTamanoSubconjunto() : Long.MAX_VALUE;
			
			Collection<Long> puntoVentaIds = new LinkedList<Long>();
			for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
				CalculoPorcentajeActivacionPuntoVenta calculoPorcentajeActivacionPuntoVenta = 
					(CalculoPorcentajeActivacionPuntoVenta) object;
				
				if (!puntoVentaIds.contains(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getId())) {
					PuntoVenta puntoVenta = calculoPorcentajeActivacionPuntoVenta.getPuntoVenta();
					
					puntoVentaIds.add(puntoVenta.getId());
					
					VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = new VisitaPuntoVentaDistribuidor();
					visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
					visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
					visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
					visitaPuntoVentaDistribuidor.setObservaciones(observaciones);
					visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVenta);
					
					visitaPuntoVentaDistribuidor.setFact(hoy);
					visitaPuntoVentaDistribuidor.setFcre(hoy);
					visitaPuntoVentaDistribuidor.setTerm(new Long(1));
					visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
					visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
					
					this.save(visitaPuntoVentaDistribuidor);
					
					puntoVenta.setDistribuidor(distribuidor);
					puntoVenta.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
					puntoVenta.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
					puntoVenta.setFechaAsignacionDistribuidor(hoy);
					
					puntoVenta.setFact(hoy);
					puntoVenta.setUact(loggedUsuarioId);
					puntoVenta.setTerm(new Long(1));
					
					i--;
					
					if (i == 0) {
						break;
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearVisitasPorSubLotes(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"))
				);
			
			MetadataConsultaResultado metadataConsultaResultado = 
				new QueryBuilder<ActivacionSublote>().list(
					entityManager, metadataConsulta, new ActivacionSublote()
				);
			
			Long i = metadataConsulta.getTamanoSubconjunto() != null ? 
				metadataConsulta.getTamanoSubconjunto() : Long.MAX_VALUE;
			
			Collection<Long> puntoVentaIds = new LinkedList<Long>();
			for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
				ActivacionSublote activacionSublote = (ActivacionSublote) object;
				
				if (!puntoVentaIds.contains(activacionSublote.getPuntoVenta().getId())) {
					PuntoVenta puntoVenta = activacionSublote.getPuntoVenta();
					
					puntoVentaIds.add(puntoVenta.getId());
					
					VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = new VisitaPuntoVentaDistribuidor();
					visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
					visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
					visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
					visitaPuntoVentaDistribuidor.setObservaciones(observaciones);
					visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVenta);
					
					visitaPuntoVentaDistribuidor.setFact(hoy);
					visitaPuntoVentaDistribuidor.setFcre(hoy);
					visitaPuntoVentaDistribuidor.setTerm(new Long(1));
					visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
					visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
					
					this.save(visitaPuntoVentaDistribuidor);
					
					puntoVenta.setDistribuidor(distribuidor);
					puntoVenta.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
					puntoVenta.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
					puntoVenta.setFechaAsignacionDistribuidor(hoy);
					
					puntoVenta.setFact(hoy);
					puntoVenta.setUact(loggedUsuarioId);
					puntoVenta.setTerm(new Long(1));
					
					i--;
					
					if (i == 0) {
						break;
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
			printWriter.println(
				"Punto de venta"
				+ ";Barrio"
				+ ";Distribuidor"
				+ ";Asignado"
				+ ";Estado"
				+ ";Fecha visita"
				+ ";Observaciones"
			);
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta).getRegistrosMuestra()) {
				VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = (VisitaPuntoVentaDistribuidor) object;
				
				String line = 
					(visitaPuntoVentaDistribuidor.getPuntoVenta() != null ? visitaPuntoVentaDistribuidor.getPuntoVenta().getNombre() : "")
					+ ";" + (visitaPuntoVentaDistribuidor.getPuntoVenta() != null && visitaPuntoVentaDistribuidor.getPuntoVenta().getBarrio() != null ? visitaPuntoVentaDistribuidor.getPuntoVenta().getBarrio().getNombre() : "")
					+ ";" + (visitaPuntoVentaDistribuidor.getDistribuidor() != null ? visitaPuntoVentaDistribuidor.getDistribuidor().getNombre() : "")
					+ ";" + (visitaPuntoVentaDistribuidor.getFechaAsignacion() != null ? dateFormat.format(visitaPuntoVentaDistribuidor.getFechaAsignacion()) : "")
					+ ";" + (visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor() != null ? visitaPuntoVentaDistribuidor.getEstadoVisitaPuntoVentaDistribuidor().getNombre() : "")
					+ ";" + (visitaPuntoVentaDistribuidor.getFechaVisita() != null ? dateFormat.format(visitaPuntoVentaDistribuidor.getFechaVisita()) : "")
					+ ";" + (visitaPuntoVentaDistribuidor.getObservaciones() != null ? visitaPuntoVentaDistribuidor.getObservaciones() : "");
				
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}