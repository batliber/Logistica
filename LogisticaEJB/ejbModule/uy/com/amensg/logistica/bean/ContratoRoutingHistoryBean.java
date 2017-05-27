package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.ContratoRoutingHistoryCurrent;
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
public class ContratoRoutingHistoryBean implements IContratoRoutingHistoryBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	/**
	 * Lista los ContratoRoutingHistory que cumplen los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 */
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			Usuario usuario = iUsuarioBean.getById(usuarioId);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<ContratoRoutingHistoryCurrent> criteriaQuery = criteriaBuilder.createQuery(ContratoRoutingHistoryCurrent.class);
			
			Root<ContratoRoutingHistoryCurrent> root = criteriaQuery.from(ContratoRoutingHistoryCurrent.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryRoles = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootRoles = subqueryRoles.from(UsuarioRolEmpresa.class);
			subrootRoles.alias("subrootRoles");
			
			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
			subrootRolesSubordinados.alias("subrootRolesSubordinados");
			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.and(
					criteriaBuilder.or(
						criteriaBuilder.equal(root.get("contrato").get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario1")),
						criteriaBuilder.and(
							criteriaBuilder.isNull(root.get("contrato").get("usuario")),
							criteriaBuilder.exists(
								subqueryRoles
									.select(subrootRoles)
									.where(
										criteriaBuilder.and(
											criteriaBuilder.equal(subrootRoles.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario2")),
											criteriaBuilder.equal(subrootRoles.get("empresa"), root.get("contrato").get("empresa")),
											criteriaBuilder.equal(subrootRoles.get("rol"), root.get("contrato").get("rol"))
										)
									)
							)
						),
						criteriaBuilder.exists(
							subqueryRolesSubordinados
								.select(subrootRolesSubordinados)
								.where(
									criteriaBuilder.and(
										criteriaBuilder.equal(subrootRolesSubordinados.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario3")),
										criteriaBuilder.equal(subrootRolesSubordinados.get("empresa"), root.get("contrato").get("empresa")),
										criteriaBuilder.isMember(root.get("contrato").get("rol").as(Rol.class), expressionRolesSubordinados)
									)
								)
						)
					)
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

			TypedQuery<ContratoRoutingHistoryCurrent> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuario);
			query.setParameter("usuario2", usuario);
			query.setParameter("usuario3", usuario);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ContratoRoutingHistoryCurrent> field = root;
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
			for (ContratoRoutingHistoryCurrent contratoRoutingHistoryCurrent : query.getResultList()) {
				ContratoRoutingHistory contratoRoutingHistory = new ContratoRoutingHistory();
				contratoRoutingHistory.setContrato(contratoRoutingHistoryCurrent.getContrato());
				contratoRoutingHistory.setEmpresa(contratoRoutingHistoryCurrent.getEmpresa());
				contratoRoutingHistory.setFact(contratoRoutingHistoryCurrent.getFact());
				contratoRoutingHistory.setId(contratoRoutingHistoryCurrent.getId());
				contratoRoutingHistory.setRol(contratoRoutingHistoryCurrent.getRol());
				contratoRoutingHistory.setTerm(contratoRoutingHistoryCurrent.getTerm());
				contratoRoutingHistory.setUact(contratoRoutingHistoryCurrent.getUact());
				contratoRoutingHistory.setUsuario(contratoRoutingHistoryCurrent.getUsuario());
				
				registrosMuestra.add(contratoRoutingHistory);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			// -------------------------------------------
			// Query para obtener la cantidad de registros
			// -------------------------------------------
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<ContratoRoutingHistoryCurrent> rootCount = criteriaQueryCount.from(ContratoRoutingHistoryCurrent.class);
			rootCount.alias("root");
			
			where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			subqueryRoles = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			subrootRoles = subqueryRoles.from(UsuarioRolEmpresa.class);
			subrootRoles.alias("subrootRoles");
			
			subqueryRolesSubordinados = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
			subrootRolesSubordinados.alias("subrootRolesSubordinados");
			joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.or(
					criteriaBuilder.equal(rootCount.get("contrato").get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario1")),
					criteriaBuilder.and(
						criteriaBuilder.isNull(rootCount.get("contrato").get("usuario")),
						criteriaBuilder.exists(
							subqueryRoles
								.select(subrootRoles)
								.where(
									criteriaBuilder.and(
										criteriaBuilder.equal(subrootRoles.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario2")),
										criteriaBuilder.equal(subrootRoles.get("empresa"), rootCount.get("contrato").get("empresa")),
										criteriaBuilder.equal(subrootRoles.get("rol"), rootCount.get("contrato").get("rol"))
									)
								)
						)
					),
					criteriaBuilder.exists(
						subqueryRolesSubordinados
							.select(subrootRolesSubordinados)
							.where(
								criteriaBuilder.and(
									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario3")),
									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa"), rootCount.get("contrato").get("empresa")),
									criteriaBuilder.isMember(rootCount.get("contrato").get("rol").as(Rol.class), expressionRolesSubordinados)
								)
							)
					)
				)
			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("usuario1", usuario);
			queryCount.setParameter("usuario2", usuario);
			queryCount.setParameter("usuario3", usuario);
			
			// Setear los parámetros según las condiciones del filtro
			i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ContratoRoutingHistoryCurrent> field = rootCount;
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
			
			result.setCantidadRegistros(queryCount.getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Lista las asignaciones existentes para el contrato con ID contratoId.
	 * 
	 * @param contratoId ID del Contrato a consultar.
	 * @return La lista de asignaciones del Contrato especificado.
	 */
	public Collection<ContratoRoutingHistory> listByContratoId(Long contratoId) {
		Collection<ContratoRoutingHistory> result = new LinkedList<ContratoRoutingHistory>();
		
		try {
			TypedQuery<ContratoRoutingHistory> query = 
				entityManager.createQuery(
					"SELECT crh"
					+ " FROM ContratoRoutingHistory crh"
					+ " WHERE crh.contrato.id = :contratoId"
					+ " ORDER BY crh.id DESC",
					ContratoRoutingHistory.class
				);
			query.setParameter("contratoId", contratoId);
			
			for (ContratoRoutingHistory contratoRoutingHistory : query.getResultList()) {
				contratoRoutingHistory.setUsuarioAct(iUsuarioBean.getById(contratoRoutingHistory.getUact()));
				
				result.add(contratoRoutingHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}