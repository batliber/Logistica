package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import uy.com.amensg.logistica.entities.ContratoANTEL;
import uy.com.amensg.logistica.entities.DatosEstadisticasResultadoEntregasANTEL;
import uy.com.amensg.logistica.entities.DatosEstadisticasVentasANTEL;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ContratoANTELBean implements IContratoANTELBean {
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	/**
	 * Lista los Contrato que cumplen los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 * @return MetadataConsultaResultado con los resultados de la consulta.
	 */
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<ContratoANTEL> criteriaQuery = criteriaBuilder.createQuery(ContratoANTEL.class);
			
			Root<ContratoANTEL> root = criteriaQuery.from(ContratoANTEL.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			boolean subordinados = false;
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!usuarioRolEmpresa.getRol().getSubordinados().isEmpty()) {
					subordinados = true;
					
					break;
				}
			}
			
			if (subordinados) {
				// Subquery con los roles subordinados del usuario
				Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
				Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
				subrootRolesSubordinados.alias("subrootRolesSubordinados");
				Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
				Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
				Predicate predicateSubordinados = criteriaBuilder.exists(
					subqueryRolesSubordinados
						.select(subrootRolesSubordinados)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
								criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), root.get("empresa").get("id")),
								criteriaBuilder.isMember(root.get("rol").as(Rol.class), expressionRolesSubordinados)
							)
						)
				);
				
				where = criteriaBuilder.and(
					where,
					criteriaBuilder.or(
						// Asignados al usuario.
						criteriaBuilder.equal(root.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
						// Asignados a algún rol subordinado dentro de la empresa
						predicateSubordinados
					)
				);
			} else {
				where = criteriaBuilder.and(
					where,
					// Asignados al usuario.
					criteriaBuilder.equal(root.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1"))
				);
			}
			
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

			TypedQuery<ContratoANTEL> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuarioId);

			if (subordinados) {
				query.setParameter("usuario2", usuarioId);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ContratoANTEL> field = root;
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
			for (ContratoANTEL contratoANTEL : query.getResultList()) {
				registrosMuestra.add(contratoANTEL);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Cuenta la cantidad de Contratos que cumplen los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 * @return Cantidad de registros que cumplen con los criterios.
	 */
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<ContratoANTEL> rootCount = criteriaQueryCount.from(ContratoANTEL.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			boolean subordinados = false;
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!usuarioRolEmpresa.getRol().getSubordinados().isEmpty()) {
					subordinados = true;
					
					break;
				}
			}
			
			if (subordinados) {
				// Subquery con los roles subordinados del usuario
				Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
				Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
				subrootRolesSubordinados.alias("subrootRolesSubordinados");
				Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
				Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
				Predicate predicateSubordinados = criteriaBuilder.exists(
					subqueryRolesSubordinados
						.select(subrootRolesSubordinados)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
								criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), rootCount.get("empresa").get("id")),
								criteriaBuilder.isMember(rootCount.get("rol").as(Rol.class), expressionRolesSubordinados)
							)
						)
				);
				
				where = criteriaBuilder.and(
					where,
					criteriaBuilder.or(
						// Asignados al usuario.
						criteriaBuilder.equal(rootCount.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
						// Asignados a algún rol subordinado dentro de la empresa
						predicateSubordinados
					)
				);
			} else {
				where = criteriaBuilder.and(
					where,
					// Asignados al usuario.
					criteriaBuilder.equal(rootCount.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1"))
				);
			}
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("usuario1", usuarioId);
			
			if (subordinados) {
				queryCount.setParameter("usuario2", usuarioId);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ContratoANTEL> field = rootCount;
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

	/**
	 * Retorna el Contrato para el número de trámite.
	 * 
	 * @param numeroTramite Número de trámite del Contrato.
	 * @return Contrato a retornar.
	 */
	public ContratoANTEL getByNumeroTramite(Long numeroTramite) {
		ContratoANTEL result = null;
		
		try {
			TypedQuery<ContratoANTEL> query = entityManager.createQuery(
				"SELECT c FROM"
				+ " ContratoANTEL c"
				+ " WHERE c.numeroTramite = :numeroTramite", 
				ContratoANTEL.class
			);
			query.setParameter("numeroTramite", numeroTramite);
			
			List<ContratoANTEL> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Retorna el Contrato para el número de orden.
	 * 
	 * @param numeroTramite Número de orden del Contrato.
	 * @return Contrato a retornar.
	 */
	public ContratoANTEL getByNumeroOrden(String numeroOrden) {
		ContratoANTEL result = null;
		
		try {
			TypedQuery<ContratoANTEL> query = entityManager.createQuery(
				"SELECT c FROM"
				+ " ContratoANTEL c"
				+ " WHERE c.antelNroTrn = :numeroOrden", 
				ContratoANTEL.class
			);
			query.setParameter("numeroOrden", numeroOrden);
			
			List<ContratoANTEL> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<DatosEstadisticasVentasANTEL> listEstadisticasVentasANTEL(
		Collection<Long> empresas, Date fechaDesde, Date fechaHasta
	) {
		Collection<DatosEstadisticasVentasANTEL> result = new LinkedList<DatosEstadisticasVentasANTEL>();
		
		try {
			TypedQuery<DatosEstadisticasVentasANTEL> query = 
				entityManager.createQuery(
					"SELECT d"
					+ " FROM DatosEstadisticasVentasANTEL d"
					+ " WHERE d.fecha BETWEEN :fechaDesde AND :fechaHasta"
					+ " AND d.empresaId IN (-1, :empresas)",
					DatosEstadisticasVentasANTEL.class
				);
			query.setParameter("fechaDesde", fechaDesde);
			query.setParameter("fechaHasta", fechaHasta);
			
			if (!empresas.isEmpty()) {
				query.setParameter("empresas", empresas);
			} else {
				query.setParameter("empresas", new LinkedList<Long>(Arrays.asList(new Long[]{ Long.valueOf(-1) })));
			}
			
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<DatosEstadisticasResultadoEntregasANTEL> listEstadisticasResultadoEntregasANTEL(
		Collection<Long> empresas, Date fechaDesde, Date fechaHasta
	) {
		Collection<DatosEstadisticasResultadoEntregasANTEL> result = new LinkedList<DatosEstadisticasResultadoEntregasANTEL>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT red.descripcion, COUNT(red) AS cantidad"
					+ " FROM ContratoANTEL ca"
					+ " JOIN ca.resultadoEntregaDistribucion red"
					+ " WHERE ca.fcre BETWEEN :fechaDesde AND :fechaHasta"
					+ " AND ca.empresa.id IN (:empresas)"
					+ " GROUP BY red.descripcion"
					+ " ORDER BY red.descripcion",
					Object[].class
				);
			query.setParameter("fechaDesde", fechaDesde);
			query.setParameter("fechaHasta", fechaHasta);
			
			if (!empresas.isEmpty()) {
				query.setParameter("empresas", empresas);
			} else {
				query.setParameter("empresas", new LinkedList<Long>(Arrays.asList(new Long[]{ Long.valueOf(-1) })));
			}
			
			for (Object[] object : query.getResultList()) {
				DatosEstadisticasResultadoEntregasANTEL datosEstadisticasResultadoEntregasANTEL =
					new DatosEstadisticasResultadoEntregasANTEL();
				datosEstadisticasResultadoEntregasANTEL.setCantidad((Long)object[1]);
				datosEstadisticasResultadoEntregasANTEL.setResultadoEntregaDistribucion((String)object[0]);
				
				result.add(datosEstadisticasResultadoEntregasANTEL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}