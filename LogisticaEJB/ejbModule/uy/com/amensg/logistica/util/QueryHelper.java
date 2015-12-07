package uy.com.amensg.logistica.util;

import java.util.Date;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;

public class QueryHelper {

	public Predicate construirWhere(
		MetadataConsulta metadataConsulta, CriteriaBuilder criteriaBuilder, Root<?> root
	) {
		Predicate where = criteriaBuilder.conjunction();
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			String[] campos = metadataCondicion.getCampo().split("\\.");
			
			Path<?> campo = root;
			Join<?, ?> join = null;
			for (int j=0; j<campos.length - 1; j++) {
				if (join != null) {
					join = join.join(campos[j], JoinType.LEFT);
				} else {
					join = root.join(campos[j], JoinType.LEFT);
				}
			}
			
			if (join != null) {
				campo = join.get(campos[campos.length - 1]);
			} else {
				campo = root.get(campos[campos.length - 1]);
			}
			
			if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_IGUAL)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.equal(campo, parameterExpression)
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.notEqual(campo, parameterExpression)
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_MAYOR)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(
								campo.as(Date.class), 
								parameterExpression.as(Date.class)
							)
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(
								campo.as(Long.class), 
								parameterExpression.as(Long.class)
							)
						);
					} else if (campo.getJavaType().equals(String.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(
								campo.as(String.class), 
								parameterExpression.as(String.class)
							)
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.greaterThan(
								campo.as(Double.class), 
								parameterExpression.as(Double.class)
							)
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_MENOR)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(
								campo.as(Date.class), 
								parameterExpression.as(Date.class)
							)
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(
								campo.as(Long.class), 
								parameterExpression.as(Long.class)
							)
						);
					} else if (campo.getJavaType().equals(String.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(
								campo.as(String.class), 
								parameterExpression.as(String.class)
							)
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.lessThan(
								campo.as(Double.class), 
								parameterExpression.as(Double.class)
							)
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_LIKE)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.like(campo.as(String.class), parameterExpression.as(String.class))
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_NOT_LIKE)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.notLike(campo.as(String.class), parameterExpression.as(String.class))
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_ENTRE)) {
				ParameterExpression<?> parameterExpressionMin = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				i++;
				
				ParameterExpression<?> parameterExpressionMax = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
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
					} else if (campo.getJavaType().equals(Long.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.between(
								campo.as(Long.class), 
								parameterExpressionMin.as(Long.class),
								parameterExpressionMax.as(Long.class)
							)
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
					} else if (campo.getJavaType().equals(Double.class)) {
						where = criteriaBuilder.and(
							where, 
							criteriaBuilder.between(
								campo.as(Double.class), 
								parameterExpressionMin.as(Double.class),
								parameterExpressionMax.as(Double.class)
							)
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
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
				if (metadataCondicion.getValores().size() > 0) {
					where = criteriaBuilder.and(
						where,
						campo.in(metadataCondicion.getValores())
					);
				}
			}
			
			i++;
		}
		
		return where;
	}

	public Long generarMidAleatorio(Long minimo, Long maximo) {
		Random random = new Random();
		
		return minimo + random.nextInt(new Long(maximo - minimo).intValue() + 1); 
	}
}