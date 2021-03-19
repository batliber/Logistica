package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ActivacionSubloteBean implements IActivacionSubloteBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean;
	
	@EJB
	private IEstadoVisitaPuntoVentaDistribuidorBean iEstadoVisitaPuntoVentaDistribuidorBean;
	
	public Collection<ActivacionSublote> list() {
		Collection<ActivacionSublote> result = new LinkedList<ActivacionSublote>();
		
		try {
			TypedQuery<ActivacionSublote> query = 
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ActivacionSublote a", 
					ActivacionSublote.class
				);
			
			for (ActivacionSublote activacionSublote : query.getResultList()) {
				result.add(activacionSublote);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<ActivacionSublote>().list(entityManager, metadataConsulta, new ActivacionSublote());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado listMisSublotes(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<ActivacionSublote> criteriaQuery = criteriaBuilder.createQuery(ActivacionSublote.class);
			
			Root<ActivacionSublote> root = criteriaQuery.from(ActivacionSublote.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.or(
					// Asignados al usuario.
					criteriaBuilder.equal(root.get("distribuidor").get("id"), criteriaBuilder.parameter(Long.class, "usuario1"))
				)
			);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = new LinkedList<Order>();
			for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
				String[] campos = metadataOrdenacion.getCampo().split("\\.");
				
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = root.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (metadataOrdenacion.getAscendente()) {
					orders.add(
						criteriaBuilder.asc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				} else {
					orders.add(
						criteriaBuilder.desc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				}
			}
			
			criteriaQuery
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<ActivacionSublote> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ActivacionSublote> field = root;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = root.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = root.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								query.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								query.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
									"p" + i,
									Boolean.parseBoolean(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			// Acotar al tamaño de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ActivacionSublote activacionSublote : query.getResultList()) {
				registrosMuestra.add(activacionSublote);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ActivacionSublote>().count(entityManager, metadataConsulta, new ActivacionSublote());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countMisSublotes(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// -------------------------------------------
			// Query para obtener la cantidad de registros
			// -------------------------------------------
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<ActivacionSublote> rootCount = criteriaQueryCount.from(ActivacionSublote.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.or(
					// Asignados al usuario.
					criteriaBuilder.equal(rootCount.get("distribuidor").get("id"), criteriaBuilder.parameter(Long.class, "usuario1"))
				)
			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("usuario1", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ActivacionSublote> field = rootCount;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = rootCount.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = rootCount.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								queryCount.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								queryCount.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryCount.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryCount.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								queryCount.setParameter(
									"p" + i,
									Boolean.parseBoolean(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			result = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ActivacionSublote getById(Long id, boolean initializeCollections) {
		ActivacionSublote result = null;
		
		try {
			result = entityManager.find(ActivacionSublote.class, id);
			
			TypedQuery<Activacion> queryActivaciones = 
				entityManager.createQuery(
					"SELECT a"
					+ " FROM Activacion a"
					+ " WHERE a.activacionSublote.id = :id",
					Activacion.class
				);
			queryActivaciones.setParameter("id", id);
			
			if (initializeCollections) {
				entityManager.detach(result);
				
				Set<Activacion> activaciones = new HashSet<Activacion>();
				
				for (Activacion activacion : queryActivaciones.getResultList()) {
					activaciones.add(activacion);
				}
				result.setActivaciones(activaciones);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ActivacionSublote getByNumero(Long numero, boolean initializeCollections) {
		ActivacionSublote result = null;
		
		try {
			TypedQuery<ActivacionSublote> query = 
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ActivacionSublote a"
					+ " WHERE a.numero = :numero", 
					ActivacionSublote.class
				);
			query.setParameter("numero", numero);
			
			TypedQuery<Activacion> queryActivaciones = 
				entityManager.createQuery(
					"SELECT ass"
					+ " FROM ActivacionSublote asl"
					+ " JOIN asl.activaciones ass"
					+ " WHERE asl.id = :id",
					Activacion.class
				);
			
			List<ActivacionSublote> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
				entityManager.detach(result);
				
				if (initializeCollections) {
					queryActivaciones.setParameter("id", result.getId());
					
					Set<Activacion> activaciones = new HashSet<Activacion>();
					
					for (Activacion activacion : queryActivaciones.getResultList()) {
						activaciones.add(activacion);
					}
					result.setActivaciones(activaciones);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ActivacionSublote getByNumeroUsuario(Long numero, Long usuarioId, boolean initializeCollections) {
		ActivacionSublote result = null;
		
		try {
			TypedQuery<ActivacionSublote> query = 
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ActivacionSublote a"
					+ " WHERE a.numero = :numero"
					+ " AND a.distribuidor.id = :usuarioId", 
					ActivacionSublote.class
				);
			query.setParameter("numero", numero);
			query.setParameter("usuarioId", usuarioId);
			
			TypedQuery<Activacion> queryActivaciones = 
				entityManager.createQuery(
					"SELECT ass"
					+ " FROM ActivacionSublote asl"
					+ " JOIN asl.activaciones ass"
					+ " WHERE asl.id = :id",
					Activacion.class
				);
			
			List<ActivacionSublote> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
				entityManager.detach(result);
				
				if (initializeCollections) {
					queryActivaciones.setParameter("id", result.getId());
					
					Set<Activacion> activaciones = new HashSet<Activacion>();
					
					for (Activacion activacion : queryActivaciones.getResultList()) {
						activaciones.add(activacion);
					}
					result.setActivaciones(activaciones);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ActivacionSublote save(ActivacionSublote activacionSublote) {
		ActivacionSublote result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT MAX(a.numero)"
					+ " FROM ActivacionSublote a", 
					Long.class
				);
			
			Long maxNumero = Long.valueOf(1);
			
			List<Long> resultList = query.getResultList();
			if (resultList.size() > 0 && resultList.get(0) != null) {
				maxNumero = resultList.get(0) + 1;
			}
			
			activacionSublote.setNumero(maxNumero);
			
			if (activacionSublote.getDistribuidor() != null) {
				activacionSublote.setFechaAsignacionDistribuidor(date);
			}
			
			Date fechaVencimientoChipMasViejo = null;
			Set<Activacion> activaciones = new HashSet<Activacion>();
			for (Activacion activacion : activacionSublote.getActivaciones()) {
				Activacion activacionManaged = entityManager.find(Activacion.class, activacion.getId()); 
				
				activacionManaged.setActivacionSublote(activacionSublote);
				
				if (fechaVencimientoChipMasViejo == null
					|| fechaVencimientoChipMasViejo.getTime() > 
					activacionManaged.getFechaVencimiento().getTime()
				) {
					fechaVencimientoChipMasViejo = activacionManaged.getFechaVencimiento();
				}
				
				activaciones.add(activacionManaged);
			}
			activacionSublote.setActivaciones(activaciones);
			
			activacionSublote.setFechaVencimientoChipMasViejo(fechaVencimientoChipMasViejo);
			
			if (activacionSublote.getPuntoVenta() != null) {
				activacionSublote.setFechaAsignacionPuntoVenta(date);
				
				PuntoVenta puntoVentaManaged = entityManager.find(PuntoVenta.class, activacionSublote.getPuntoVenta().getId());
				
				if (puntoVentaManaged.getFechaVencimientoChipMasViejo() == null
//					|| puntoVentaManaged.getFechaVencimientoChipMasViejo().getTime() > fechaVencimientoChipMasViejo.getTime()
//					Siempre que se asigna un Sublote a un Punto de venta se actualiza la fecha de vencimiento del chip más viejo.
				) {
					puntoVentaManaged.setFechaVencimientoChipMasViejo(fechaVencimientoChipMasViejo);
					
					puntoVentaManaged.setUact(activacionSublote.getUact());
					puntoVentaManaged.setFact(activacionSublote.getFact());
				}
			}
			
			activacionSublote.setFcre(activacionSublote.getFact());
			activacionSublote.setUcre(activacionSublote.getUact());
			
			entityManager.persist(activacionSublote);
			
			result = activacionSublote;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ActivacionSublote activacionSublote) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidorVisitaAutor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty(
						"estadoVisitaPuntoVentaDistribuidor.VisitaAutor")
					)
				);
				
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidorVisitaPermanente = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty(
						"estadoVisitaPuntoVentaDistribuidor.VisitaPermanente")
					)
				);
			
			ActivacionSublote activacionSubloteManaged = 
				entityManager.find(ActivacionSublote.class, activacionSublote.getId());
			
			activacionSubloteManaged.setDescripcion(activacionSublote.getDescripcion());
			
			if (activacionSubloteManaged.getDistribuidor() != null && 
				activacionSublote.getDistribuidor() != null) {
				// Si el sublote tenía distribuidor asignado y el sublote parámetro tiene distribuidor.
				if (!activacionSubloteManaged.getDistribuidor().getId().equals(
					activacionSublote.getDistribuidor().getId())
				) {
					// Si el distribuidor cambia.
					activacionSubloteManaged.setDistribuidor(activacionSublote.getDistribuidor());
					activacionSubloteManaged.setFechaAsignacionDistribuidor(date);
					
					if (activacionSublote.getPuntoVenta() != null
						&& !activacionSublote.getPuntoVenta().getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
							estadoVisitaPuntoVentaDistribuidorVisitaAutor.getId()
						)
						&& !activacionSublote.getPuntoVenta().getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
							estadoVisitaPuntoVentaDistribuidorVisitaPermanente.getId()
						)
					) {
						// Si el distribuidor no tiene visita de autor o permanente asignada.
						iVisitaPuntoVentaDistribuidorBean.crearVisita(
							activacionSublote.getDistribuidor(), 
							activacionSublote.getPuntoVenta(), 
							activacionSublote.getUact()
						);
					}
				}
			} else if (activacionSubloteManaged.getDistribuidor() != null) {
				// Si el sublote tenía distribuidor asignado y el sublote parámetro no tiene distribuidor.
				activacionSubloteManaged.setDistribuidor(null);
				activacionSubloteManaged.setFechaAsignacionDistribuidor(null);
			} else if (activacionSubloteManaged.getDistribuidor() == null && 
				activacionSublote.getDistribuidor() != null
			) {
				// Si el sublote no tenía distribuidor asignado y el sublote parámetro tiene distribuidor.
				activacionSubloteManaged.setDistribuidor(activacionSublote.getDistribuidor());
				activacionSubloteManaged.setFechaAsignacionDistribuidor(date);
				
				if (activacionSublote.getPuntoVenta() != null
					&& !activacionSublote.getPuntoVenta().getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
						estadoVisitaPuntoVentaDistribuidorVisitaAutor.getId()
					)
					&& !activacionSublote.getPuntoVenta().getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
						estadoVisitaPuntoVentaDistribuidorVisitaPermanente.getId()
					)
				) {
					iVisitaPuntoVentaDistribuidorBean.crearVisita(
						activacionSublote.getDistribuidor(), 
						activacionSublote.getPuntoVenta(), 
						activacionSublote.getUact()
					);
				}
			}
			
			if (activacionSubloteManaged.getPuntoVenta() != null && 
				activacionSublote.getPuntoVenta() != null
			) {
				// Si el sublote tenía punto de venta asignado y el sublote parámetro tiene punto de venta.
				if (!activacionSubloteManaged.getPuntoVenta().getId().equals(
					activacionSublote.getPuntoVenta().getId())
				) {
					// Si el punto de venta cambia.
					activacionSubloteManaged.setFechaAsignacionPuntoVenta(date);
					activacionSubloteManaged.setPuntoVenta(activacionSublote.getPuntoVenta());
				}
			} else if (activacionSubloteManaged.getPuntoVenta() != null) {
				// Si el sublote tenía punto de venta asignado y el sublote parámetro no tiene punto de venta.
				activacionSubloteManaged.setFechaAsignacionPuntoVenta(null);
				activacionSubloteManaged.setPuntoVenta(null);
			} else if (activacionSubloteManaged.getPuntoVenta() == null && 
				activacionSublote.getPuntoVenta() != null
			) {
				// Si el sublote no tenía punto de venta asignado y el sublote parámetro tiene punto de venta.
				activacionSubloteManaged.setFechaAsignacionPuntoVenta(date);
				activacionSubloteManaged.setPuntoVenta(activacionSublote.getPuntoVenta());
			}
			
			Map<Long, Activacion> activacionesManaged = new HashMap<Long, Activacion>();
			for (Activacion activacion : activacionSubloteManaged.getActivaciones()) {
				activacionesManaged.put(activacion.getId(), activacion);
			}
			
			Set<Activacion> activaciones = new HashSet<Activacion>();
			Date fechaVencimientoChipMasViejo = null;
			for (Activacion activacion : activacionSublote.getActivaciones()) {
				Activacion activacionManaged = entityManager.find(Activacion.class, activacion.getId());
				activacionManaged.setActivacionSublote(activacionSubloteManaged);
				
				activaciones.add(activacionManaged);
				
				if (fechaVencimientoChipMasViejo == null
					|| fechaVencimientoChipMasViejo.getTime() > 
						activacionManaged.getFechaVencimiento().getTime()
				) {
					fechaVencimientoChipMasViejo = activacionManaged.getFechaVencimiento();
				}
				
				activacionesManaged.remove(activacion.getId());
			}
			activacionSublote.setActivaciones(activaciones);
			
			for (Activacion activacion : activacionesManaged.values()) {
				activacion.setActivacionSublote(null);
				
				entityManager.merge(activacion);
			}
			
			activacionSubloteManaged.setFechaVencimientoChipMasViejo(fechaVencimientoChipMasViejo);
			
			if ((activacionSubloteManaged.getPuntoVenta() != null)
//				&& (activacionSubloteManaged.getPuntoVenta().getFechaVencimientoChipMasViejo() == null
//				|| activacionSubloteManaged.getPuntoVenta().getFechaVencimientoChipMasViejo().getTime() > fechaVencimientoChipMasViejo.getTime())
//				Siempre que se asigna un Sublote a un Punto de venta se actualiza la fecha de vencimiento del chip más viejo.
			) {
				activacionSubloteManaged.getPuntoVenta().setFechaVencimientoChipMasViejo(fechaVencimientoChipMasViejo);
				
				activacionSubloteManaged.getPuntoVenta().setUact(activacionSublote.getUact());
				activacionSubloteManaged.getPuntoVenta().setFact(activacionSublote.getFact());
			}
			
			activacionSubloteManaged.setFact(activacionSublote.getFact());
			activacionSubloteManaged.setUcre(activacionSublote.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void asignarAPuntoVenta(ActivacionSublote activacionSublote, PuntoVenta puntoVenta) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidorVisitado = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty(
						"estadoVisitaPuntoVentaDistribuidor.Visitado")
					)
				);
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidorVisitaAutor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty(
						"estadoVisitaPuntoVentaDistribuidor.VisitaAutor")
					)
				);
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidorVisitaPermanente = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty(
						"estadoVisitaPuntoVentaDistribuidor.VisitaPermanente")
					)
				);
			
			ActivacionSublote activacionSubloteManaged = 
				entityManager.find(ActivacionSublote.class, activacionSublote.getId());
			
			PuntoVenta puntoVentaManaged = 
				entityManager.find(PuntoVenta.class, puntoVenta.getId());
			
			activacionSubloteManaged.setPuntoVenta(puntoVentaManaged);
			activacionSubloteManaged.setFechaAsignacionPuntoVenta(date);
			
			activacionSubloteManaged.setFact(date);
			activacionSubloteManaged.setTerm(Long.valueOf(1));
			activacionSubloteManaged.setUact(activacionSublote.getUact());
			
			if (puntoVentaManaged.getEstadoVisitaPuntoVentaDistribuidor() == null
				|| (
					!puntoVentaManaged.getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
						estadoVisitaPuntoVentaDistribuidorVisitaPermanente.getId()
					)
					&& !puntoVentaManaged.getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
						estadoVisitaPuntoVentaDistribuidorVisitaAutor.getId()
					)
				)
			) {
				puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(
					estadoVisitaPuntoVentaDistribuidorVisitado
				);
				puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(date);
			}
			
//			Siempre que se asigna un Sublote a un Punto de venta se actualiza la fecha de vencimiento del chip más viejo.
			puntoVentaManaged.setFechaVencimientoChipMasViejo(
				activacionSubloteManaged.getFechaVencimientoChipMasViejo()
			);
			puntoVentaManaged.setFechaVisitaDistribuidor(date);
			puntoVentaManaged.setFact(date);
			puntoVentaManaged.setTerm(Long.valueOf(1));
			puntoVentaManaged.setUact(activacionSublote.getUact());
			
			VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidorManaged = 
				iVisitaPuntoVentaDistribuidorBean.getLastByPuntoVentaDistribuidor(
					activacionSubloteManaged.getDistribuidor(), 
					puntoVentaManaged
				);
			
			if (visitaPuntoVentaDistribuidorManaged != null) {
				// Si había una visita para el distribuidor y punto de venta.
				if (!visitaPuntoVentaDistribuidorManaged.getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
						estadoVisitaPuntoVentaDistribuidorVisitaAutor.getId()
					)
					&& !visitaPuntoVentaDistribuidorManaged.getEstadoVisitaPuntoVentaDistribuidor().getId().equals(
						estadoVisitaPuntoVentaDistribuidorVisitaPermanente.getId()
					)
				) {
					// Si la visita no era de autor ni era permanente.
					visitaPuntoVentaDistribuidorManaged.setEstadoVisitaPuntoVentaDistribuidor(
						estadoVisitaPuntoVentaDistribuidorVisitado
					);
				}
				
				visitaPuntoVentaDistribuidorManaged.setFechaVisita(date);
				
				visitaPuntoVentaDistribuidorManaged.setFact(date);
				visitaPuntoVentaDistribuidorManaged.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidorManaged.setUact(activacionSublote.getUact());
				
				entityManager.merge(visitaPuntoVentaDistribuidorManaged);
			} else {
				// Si no había una visita para el distribuidor y punto de venta.
				visitaPuntoVentaDistribuidorManaged = new VisitaPuntoVentaDistribuidor();
				visitaPuntoVentaDistribuidorManaged.setDistribuidor(activacionSubloteManaged.getDistribuidor());
				visitaPuntoVentaDistribuidorManaged.setEstadoVisitaPuntoVentaDistribuidor(
					estadoVisitaPuntoVentaDistribuidorVisitado
				);
				visitaPuntoVentaDistribuidorManaged.setFechaAsignacion(date);
				visitaPuntoVentaDistribuidorManaged.setFechaVisita(date);
				visitaPuntoVentaDistribuidorManaged.setObservaciones(
					"Visita por entrega de sub-lote: " + activacionSublote.getNumero()
				);
				visitaPuntoVentaDistribuidorManaged.setPuntoVenta(puntoVentaManaged);
				
				visitaPuntoVentaDistribuidorManaged.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidorManaged.setFact(date);
				visitaPuntoVentaDistribuidorManaged.setFcre(date);
				visitaPuntoVentaDistribuidorManaged.setUcre(activacionSublote.getUact());
				visitaPuntoVentaDistribuidorManaged.setUact(activacionSublote.getUact());
				
				entityManager.merge(visitaPuntoVentaDistribuidorManaged);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calcularFechasVencimientoChipMasViejo(Long loggedUsuarioId) {
		try {
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			Query updateFechasVencimientoChipMasViejo = 
				hibernateSession.createNativeQuery(
					"SELECT f_actualizar_vencimiento_chip_mas_viejo()"
				);
//			updateFechasVencimientoChipMasViejo.setParameter(1, loggedUsuarioId, LongType.INSTANCE);
			
			List<?> resultList = updateFechasVencimientoChipMasViejo.getResultList();
			for (Object row : resultList) {
				String srow =  (String) row;
				System.out.println(srow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}