package uy.com.amensg.logistica.bean;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Constants;

@Stateless
public class ACMInterfaceListaNegraBean implements IACMInterfaceListaNegraBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	private Predicate where;
	private Map<String, Object> parameterValues = new HashMap<String, Object>();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			TypedQuery<ACMInterfaceListaNegra> query = this.construirQuery(metadataConsulta);
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			List<ACMInterfaceListaNegra> resultList = query.getResultList();
			
			for (ACMInterfaceListaNegra acmInterfaceListaNegra : resultList) {
				registrosMuestra.add(acmInterfaceListaNegra);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(ACMInterfaceListaNegra.class)));
			criteriaQuery.where(where);
			
			TypedQuery<Long> countQuery = entityManager.createQuery(criteriaQuery);
			
			for (String parameterName : parameterValues.keySet()) {
				countQuery.setParameter(parameterName, parameterValues.get(parameterName));
			}
			
			result.setCantidadRegistros(countQuery.getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private TypedQuery<ACMInterfaceListaNegra> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ACMInterfaceListaNegra> criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceListaNegra.class);
		Root<ACMInterfaceListaNegra> root = criteriaQuery.from(ACMInterfaceListaNegra.class);
		
		List<Order> orders = new LinkedList<Order>();
		
		for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
			if (metadataOrdenacion.getAscendente()) {
				orders.add(criteriaBuilder.asc(root.get(metadataOrdenacion.getCampo())));
			} else {
				orders.add(criteriaBuilder.desc(root.get(metadataOrdenacion.getCampo())));
			}
		}
		
		where = criteriaBuilder.conjunction();
		
		criteriaQuery
			.select(root)
			.orderBy(orders);
		
		parameterValues = new HashMap<String, Object>();
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			Path<?> campo = root.get(metadataCondicion.getCampo());
			
			if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_IGUAL)) {
				ParameterExpression<?> parameterExpression = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.equal(campo, parameterExpression)
				);
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						parameterValues.put(
							parameterExpression.getName(), 
							DateFormat.getInstance().parse(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						parameterValues.put(
							parameterExpression.getName(), 
							new Long(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(String.class)) {
						parameterValues.put(
							parameterExpression.getName(), 
							metadataCondicion.getValores().toArray(new String[]{})[0]
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						parameterValues.put(
							parameterExpression.getName(), 
							new Double(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_MAYOR)) {
				ParameterExpression<?> parameterExpression = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(campo.as(Date.class), parameterExpression.as(Date.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							DateFormat.getInstance().parse(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(campo.as(Long.class), parameterExpression.as(Long.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							new Long(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(String.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(campo.as(String.class), parameterExpression.as(String.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							metadataCondicion.getValores().toArray(new String[]{})[0]
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(campo.as(Double.class), parameterExpression.as(Double.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							new Double(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_MENOR)) {
				ParameterExpression<?> parameterExpression = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(campo.as(Date.class), parameterExpression.as(Date.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							DateFormat.getInstance().parse(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(campo.as(Long.class), parameterExpression.as(Long.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							new Long(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(String.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(campo.as(String.class), parameterExpression.as(String.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							metadataCondicion.getValores().toArray(new String[]{})[0]
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(campo.as(Double.class), parameterExpression.as(Double.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							new Double(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_LIKE)) {
				ParameterExpression<?> parameterExpression = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.like(campo.as(String.class), parameterExpression.as(String.class))
				);
				
				parameterValues.put(
					parameterExpression.getName(), 
					metadataCondicion.getValores().toArray(new String[]{})[0]
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_NOT_LIKE)) {
				ParameterExpression<?> parameterExpression = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.notLike(campo.as(String.class), parameterExpression.as(String.class))
				);
				
				parameterValues.put(
					parameterExpression.getName(), 
					metadataCondicion.getValores().toArray(new String[]{})[0]
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_ENTRE)) {
				ParameterExpression<?> parameterExpressionMin = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				i++;
				ParameterExpression<?> parameterExpressionMax = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				String[] valores = metadataCondicion.getValores().toArray(new String[]{});
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.between(
								campo.as(Date.class), 
								parameterExpressionMin.as(Date.class),
								parameterExpressionMax.as(Date.class)
							)
						);
						
						parameterValues.put(
							parameterExpressionMin.getName(), 
							DateFormat.getInstance().parse(valores[0])
						);
						
						parameterValues.put(
							parameterExpressionMax.getName(), 
							DateFormat.getInstance().parse(valores[1])
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.between(
								campo.as(Long.class), 
								parameterExpressionMin.as(Long.class),
								parameterExpressionMax.as(Long.class)
							)
						);
						
						parameterValues.put(
							parameterExpressionMin.getName(), 
							new Long(valores[0])
						);
						
						parameterValues.put(
							parameterExpressionMax.getName(), 
							new Long(valores[1])
						);
					} else if (campo.getJavaType().equals(String.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.between(
								campo.as(String.class), 
								parameterExpressionMin.as(String.class),
								parameterExpressionMax.as(String.class)
							)
						);
						
						parameterValues.put(
							parameterExpressionMin.getName(), 
							valores[0]
						);
						
						parameterValues.put(
							parameterExpressionMax.getName(), 
							valores[1]
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.between(
								campo.as(Double.class), 
								parameterExpressionMin.as(Double.class),
								parameterExpressionMax.as(Double.class)
							)
						);
						
						parameterValues.put(
							parameterExpressionMin.getName(), 
							new Double(valores[0])
						);
						
						parameterValues.put(
							parameterExpressionMax.getName(), 
							new Double(valores[1])
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_NULL)) {
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.isNull(campo)
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_NOT_NULL)) {
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.isNotNull(campo)
				);
			}
			
			i++;
		}
		
		criteriaQuery.where(where);
		
		TypedQuery<ACMInterfaceListaNegra> query = entityManager.createQuery(criteriaQuery);
		
		for (String parameterName : parameterValues.keySet()) {
			query.setParameter(parameterName, parameterValues.get(parameterName));
		}
		
		return query;
	}
}