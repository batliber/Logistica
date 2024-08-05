package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRelacion;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ContratoRelacionBean implements IContratoRelacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean; 
	
	public Collection<ContratoRelacion> listByContratoId(Long id) {
		Collection<ContratoRelacion> result = new LinkedList<ContratoRelacion>();
		
		try {
			TypedQuery<ContratoRelacion> query =
				entityManager.createQuery(
					"SELECT cr"
					+ " FROM ContratoRelacion cr"
					+ " WHERE cr.contrato.id = :contratoId"
					+ " OR cr.contratoRelacionado.id = :contratoId",
					ContratoRelacion.class
				);
			query.setParameter("contratoId", id);
			
			Collection<ContratoRelacion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Consulta si dentro del filtro a asignar existe algún contrato con relaciones.
	 * 
	 * @param Criterios de la consulta.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return true sii la asignación se puede realizar.
	 */
	public boolean chequearAsignacion(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		boolean result = true;
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			
			Root<Contrato> root = criteriaQuery.from(Contrato.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<ContratoRelacion> subqueryContratoRelacionados = criteriaQuery.subquery(ContratoRelacion.class);
			Root<ContratoRelacion> subrootContratoRelacionados = subqueryContratoRelacionados.from(ContratoRelacion.class);
			subrootContratoRelacionados.alias("subrootContratoRelacionados");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryContratoRelacionados
						.select(subrootContratoRelacionados)
							.where(
								criteriaBuilder.and(
									criteriaBuilder.equal(
										subrootContratoRelacionados.get("contrato"), 
										root.get("id")
									),
									criteriaBuilder.isNull(
										subrootContratoRelacionados.get("contratoRelacionado").get("usuario")
									)
								)
							)
				)
			);
			
			criteriaQuery
				.select(criteriaBuilder.count(root.get("id")))
				.where(where);
	
			TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Contrato> field = root;
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
			
			result = query.getSingleResult() == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ContratoRelacion save(ContratoRelacion contratoRelacion) {
		ContratoRelacion result = null;
		
		try {
			contratoRelacion.setFcre(contratoRelacion.getFact());
			contratoRelacion.setUcre(contratoRelacion.getUact());
			
			entityManager.persist(contratoRelacion);
			
			result = contratoRelacion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}