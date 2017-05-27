package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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
import javax.persistence.criteria.Subquery;

import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class BCUInterfaceRiesgoCrediticioBean implements IBCUInterfaceRiesgoCrediticioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<BCUInterfaceRiesgoCrediticio> criteriaQuery = criteriaBuilder.createQuery(BCUInterfaceRiesgoCrediticio.class);
			
			Root<BCUInterfaceRiesgoCrediticio> root = criteriaQuery.from(BCUInterfaceRiesgoCrediticio.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryEmpresasUsuario = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootEmpresasUsuario = subqueryEmpresasUsuario.from(UsuarioRolEmpresa.class);
			subrootEmpresasUsuario.alias("subrootEmpresasUsuario");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryEmpresasUsuario
						.select(subrootEmpresasUsuario)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootEmpresasUsuario.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "loggedUsuarioId")),
								criteriaBuilder.equal(subrootEmpresasUsuario.get("empresa"), root.get("empresa"))
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

			TypedQuery<BCUInterfaceRiesgoCrediticio> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("loggedUsuarioId", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<BCUInterfaceRiesgoCrediticio> field = root;
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
			for (BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio : query.getResultList()) {
				registrosMuestra.add(bcuInterfaceRiesgoCrediticio);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// -------------------------------------------
			// Query para obtener la cantidad de registros
			// -------------------------------------------
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<BCUInterfaceRiesgoCrediticio> rootCount = criteriaQueryCount.from(BCUInterfaceRiesgoCrediticio.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Subquery<UsuarioRolEmpresa> subqueryEmpresasUsuario = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootEmpresasUsuario = subqueryEmpresasUsuario.from(UsuarioRolEmpresa.class);
			subrootEmpresasUsuario.alias("subrootEmpresasUsuario");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryEmpresasUsuario
						.select(subrootEmpresasUsuario)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootEmpresasUsuario.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "loggedUsuarioId")),
								criteriaBuilder.equal(subrootEmpresasUsuario.get("empresa"), rootCount.get("empresa"))
							)
						)
				)
			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("documento")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("loggedUsuarioId", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<BCUInterfaceRiesgoCrediticio> field = rootCount;
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

	public BCUInterfaceRiesgoCrediticio getLastByEmpresaDocumento(Empresa empresa, String documento) {
		BCUInterfaceRiesgoCrediticio result = null;
		
		try {
			TypedQuery<BCUInterfaceRiesgoCrediticio> query = entityManager.createQuery(
				"SELECT b"
				+ " FROM BCUInterfaceRiesgoCrediticio b"
				+ " WHERE b.empresa.id = :empresaId"
				+ " AND b.documento = :documento"
				+ " ORDER BY b.id DESC",
				BCUInterfaceRiesgoCrediticio.class
			);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("documento", documento);
			query.setMaxResults(1);
			
			Collection<BCUInterfaceRiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			bcuInterfaceRiesgoCrediticio.setFact(date);
			bcuInterfaceRiesgoCrediticio.setTerm(new Long(1));
			bcuInterfaceRiesgoCrediticio.setUact(new Long(1));
			
			entityManager.persist(bcuInterfaceRiesgoCrediticio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}