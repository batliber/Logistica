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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ActivacionSubloteBean implements IActivacionSubloteBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
									"p" + i,
									new Boolean(valor)
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryCount.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryCount.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								queryCount.setParameter(
									"p" + i,
									new Boolean(valor)
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
	
	public ActivacionSublote getById(Long id) {
		ActivacionSublote result = null;
		
		try {
			result = entityManager.find(ActivacionSublote.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ActivacionSublote getByNumero(Long numero) {
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
			
			List<ActivacionSublote> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long save(ActivacionSublote activacionSublote) {
		Long result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT MAX(a.numero)"
					+ " FROM ActivacionSublote a", 
					Long.class
				);
			
			Long maxNumero = new Long(1);
			
			List<Long> resultList = query.getResultList();
			if (resultList.size() > 0 && resultList.get(0) != null) {
				maxNumero = resultList.get(0) + 1;
			}
			
			activacionSublote.setNumero(maxNumero);
			
			if (activacionSublote.getDistribuidor() != null) {
				activacionSublote.setFechaAsignacionDistribuidor(date);
			}
			
			if (activacionSublote.getPuntoVenta() != null) {
				activacionSublote.setFechaAsignacionPuntoVenta(date);
			}
			
			Set<Activacion> activaciones = new HashSet<Activacion>();
			for (Activacion activacion : activacionSublote.getActivaciones()) {
				Activacion activacionManaged = entityManager.find(Activacion.class, activacion.getId()); 
				
				activacionManaged.setActivacionSublote(activacionSublote);
				
				activaciones.add(activacionManaged);
			}
			activacionSublote.setActivaciones(activaciones);
			
			entityManager.persist(activacionSublote);
			
			result = maxNumero;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ActivacionSublote activacionSublote) {
		try {
			ActivacionSublote activacionSubloteManaged = entityManager.find(ActivacionSublote.class, activacionSublote.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			if (activacionSubloteManaged.getDistribuidor() != null && activacionSublote.getDistribuidor() != null) {
				if (!activacionSubloteManaged.getDistribuidor().getId().equals(activacionSublote.getDistribuidor().getId())) {
					activacionSublote.setFechaAsignacionDistribuidor(date);
				}
			} else if (activacionSubloteManaged.getDistribuidor() != null) {
				activacionSublote.setFechaAsignacionDistribuidor(null);
			} else if (activacionSubloteManaged.getDistribuidor() == null && activacionSublote.getDistribuidor() != null) {
				activacionSublote.setFechaAsignacionDistribuidor(date);
			}
			
			if (activacionSubloteManaged.getPuntoVenta() != null && activacionSublote.getPuntoVenta() != null) {
				if (!activacionSubloteManaged.getPuntoVenta().getId().equals(activacionSublote.getPuntoVenta().getId())) {
					activacionSublote.setFechaAsignacionPuntoVenta(date);
				}
			} else if (activacionSubloteManaged.getPuntoVenta() != null) {
				activacionSublote.setFechaAsignacionPuntoVenta(null);
			} else if (activacionSubloteManaged.getPuntoVenta() == null && activacionSublote.getPuntoVenta() != null) {
				activacionSublote.setFechaAsignacionPuntoVenta(date);
			}
			
			Map<Long, Activacion> activacionesManaged = new HashMap<Long, Activacion>();
			for (Activacion activacion : activacionSubloteManaged.getActivaciones()) {
				activacionesManaged.put(activacion.getId(), activacion);
			}
			
			Set<Activacion> activaciones = new HashSet<Activacion>();
			for (Activacion activacion : activacionSublote.getActivaciones()) {
				Activacion activacionManaged = entityManager.find(Activacion.class, activacion.getId());
				activacionManaged.setActivacionSublote(activacionSubloteManaged);
				
				activaciones.add(activacionManaged);
				
				activacionesManaged.remove(activacion.getId());
			}
			activacionSublote.setActivaciones(activaciones);
			
			for (Activacion activacion : activacionesManaged.values()) {
				activacion.setActivacionSublote(null);
				
				entityManager.merge(activacion);
			}
			
			entityManager.merge(activacionSublote);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void asignarAPuntoVenta(ActivacionSublote activacionSublote, PuntoVenta puntoVenta) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			ActivacionSublote activacionSubloteManaged = entityManager.find(ActivacionSublote.class, activacionSublote.getId());
			
			PuntoVenta puntoVentaManaged = entityManager.find(PuntoVenta.class, puntoVenta.getId());
			
			activacionSubloteManaged.setPuntoVenta(puntoVentaManaged);
			activacionSubloteManaged.setFechaAsignacionPuntoVenta(date);
			
			activacionSubloteManaged.setFact(date);
			activacionSubloteManaged.setTerm(new Long(1));
			activacionSubloteManaged.setUact(activacionSubloteManaged.getUact());
			
			entityManager.merge(activacionSubloteManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}