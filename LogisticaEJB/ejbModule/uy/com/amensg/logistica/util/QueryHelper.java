package uy.com.amensg.logistica.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;

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
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_IGUAL)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.equal(campo, parameterExpression)
				);
			} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_CLAVE_NOT_IGUAL)) {
				ParameterExpression<?> parameterExpression = 
					criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				where = criteriaBuilder.and(
					where, 
					criteriaBuilder.notEqual(campo, parameterExpression)
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

	public List<Order> construirOrderBy(
		MetadataConsulta metadataConsulta, CriteriaBuilder criteriaBuilder, Root<?> root
	) {
		List<Order> result = new LinkedList<Order>();
		
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
				result.add(
					criteriaBuilder.asc(
						join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
					)
				);
			} else {
				result.add(
					criteriaBuilder.desc(
						join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
					)
				);
			}
		}
		
		return result;
	}

	public TypedQuery cargarParameters(
		MetadataConsulta metadataConsulta, TypedQuery query, Root<?> root
	) {
		TypedQuery<?> result = query;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		// Setear los parámetros según las condiciones del filtro
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
				for (String valor : metadataCondicion.getValores()) {
					String[] campos = metadataCondicion.getCampo().split("\\.");
					
					Path<?> field = root;
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
					
					if (field.getJavaType().equals(Date.class)) {
						try {
							Date valorDate = format.parse(valor);
							
							result.setParameter(
								"p" + i,
								valorDate
							);
						} catch (Exception e) {
							System.err.println("Valor no válido en metadataConsulta: " 
								+ metadataCondicion.getCampo() + " " + metadataCondicion.getOperador()  + " "  + valor
							);
							
							Date valorDate = null;
							
							if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_IGUAL)) {
								GregorianCalendar gregorianCalendar = new GregorianCalendar();
								gregorianCalendar.set(GregorianCalendar.YEAR, 1900);
								
								valorDate = gregorianCalendar.getTime();
								
							} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL)) {
								GregorianCalendar gregorianCalendar = new GregorianCalendar();
								gregorianCalendar.set(GregorianCalendar.YEAR, 1900);
								
								valorDate = gregorianCalendar.getTime();
							} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_MAYOR)) {
								GregorianCalendar gregorianCalendar = new GregorianCalendar();
								gregorianCalendar.set(GregorianCalendar.YEAR, 3000);
								
								valorDate = gregorianCalendar.getTime();
							} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_MENOR)) {
								GregorianCalendar gregorianCalendar = new GregorianCalendar();
								gregorianCalendar.set(GregorianCalendar.YEAR, 1900);
								
								valorDate = gregorianCalendar.getTime();
							} else if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_ENTRE)) {
								
							}
							
							result.setParameter(
								"p" + i,
								valorDate
							);
						}
					} else if (field.getJavaType().equals(Long.class)) {
						try {
							Long valorLong = Long.parseLong(valor);
							
							result.setParameter(
								"p" + i,
								valorLong
							);
						} catch (Exception e) {
							System.err.println("Valor no válido en metadataConsulta: " 
								+ metadataCondicion.getCampo() + " " + metadataCondicion.getOperador()  + " "  + valor
							);
							
							Long valorLong = Long.valueOf(-1);
							
							result.setParameter(
								"p" + i,
								valorLong
							);
						}
					} else if (field.getJavaType().equals(String.class)) {
						result.setParameter(
							"p" + i,
							valor
						);
					} else if (field.getJavaType().equals(Double.class)) {
						try {
							Double valorDouble = Double.parseDouble(valor);
							
							result.setParameter(
								"p" + i,
								valorDouble
							);
						} catch (Exception e) {
							System.err.println("Valor no válido en metadataConsulta: " 
								+ metadataCondicion.getCampo() + " " + metadataCondicion.getOperador()  + " "  + valor
							);
							
							Double valorDouble = Double.valueOf(-1);
							
							result.setParameter(
								"p" + i,
								valorDouble
							);
						}
					} else if (field.getJavaType().equals(Boolean.class)) {
						try {
							Boolean valorBoolean = Boolean.parseBoolean(valor);
							
							result.setParameter(
								"p" + i,
								valorBoolean
							);
						} catch (Exception e) {
							System.err.println("Valor no válido en metadataConsulta: " 
								+ metadataCondicion.getCampo() + " " + metadataCondicion.getOperador()  + " "  + valor
							);
						}
					}
					
					i++;
				}
				
				if (metadataCondicion.getValores().size() == 0) {
					i++;
				}
			}
		}
		
		return query;
	}
}