package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@Stateless
public class ACMInterfacePrepagoBean implements IACMInterfacePrepagoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	@EJB
	private IACMInterfaceMidBean iACMInterfaceMidBean;
	
	public Collection<ACMInterfacePrepago> list() {
		Collection<ACMInterfacePrepago> result = new LinkedList<ACMInterfacePrepago>();
		
		try {
			Query query = entityManager.createQuery("SELECT p FROM ACMInterfacePrepago p");
			
			for (Object object : query.getResultList()) {
				result.add((ACMInterfacePrepago) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			TypedQuery<ACMInterfacePrepago> query = this.construirQuery(metadataConsulta);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			List<ACMInterfacePrepago> resultList = query.getResultList();
			
			for (ACMInterfacePrepago acmInterfacePrepago : resultList) {
				registrosMuestra.add(acmInterfacePrepago);
				
				if (registrosMuestra.size() == metadataConsulta.getTamanoMuestra()) {
					break;
				}
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			result.setCantidadRegistros(new Long(query.getResultList().size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String exportarAExcel(MetadataConsulta metadataConsulta) {
		String result = null;
		
		try {
			TypedQuery<ACMInterfacePrepago> query = this.construirQuery(metadataConsulta);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName =
				Configuration.getInstance().getProperty("exportacion.carpeta")
					+ gregorianCalendar.get(GregorianCalendar.YEAR)
					+ gregorianCalendar.get(GregorianCalendar.MONTH)
					+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH)
					+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY)
					+ gregorianCalendar.get(GregorianCalendar.MINUTE)
					+ gregorianCalendar.get(GregorianCalendar.SECOND)
					+ ".csv";
					
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (ACMInterfacePrepago acmInterfacePrepago : query.getResultList()) {
				acmInterfacePrepago.setFechaExportacion(gregorianCalendar.getTime());
				acmInterfacePrepago.setUact(new Long(1));
				acmInterfacePrepago.setFact(gregorianCalendar.getTime());
				acmInterfacePrepago.setTerm(new Long(1));
				
				acmInterfacePrepago = entityManager.merge(acmInterfacePrepago);
				
				printWriter.println(
					acmInterfacePrepago.getMid()
					+ ";" + acmInterfacePrepago.getMesAno()
					+ ";" + acmInterfacePrepago.getMontoMesActual()
					+ ";" + acmInterfacePrepago.getMontoMesAnterior1()
					+ ";" + acmInterfacePrepago.getMontoMesAnterior2()
					+ ";" + acmInterfacePrepago.getMontoPromedio()
					+ ";" + format.format(acmInterfacePrepago.getFechaExportacion())
				);
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private TypedQuery<ACMInterfacePrepago> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ACMInterfacePrepago> criteriaQuery = criteriaBuilder.createQuery(ACMInterfacePrepago.class);
		Root<ACMInterfacePrepago> root = criteriaQuery.from(ACMInterfacePrepago.class);
		
		List<Order> orders = new LinkedList<Order>();
		
		for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
			if (metadataOrdenacion.getAscendente()) {
				orders.add(criteriaBuilder.asc(root.get(metadataOrdenacion.getCampo())));
			} else {
				orders.add(criteriaBuilder.desc(root.get(metadataOrdenacion.getCampo())));
			}
		}
		
		Predicate predicate = criteriaBuilder.conjunction();
		
		criteriaQuery
			.select(root)
			.orderBy(orders);
		
		Map<String, Object> parameterValues = new HashMap<String, Object>();
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			Path<?> campo = root.get(metadataCondicion.getCampo());
			
			if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_IGUAL)) {
				ParameterExpression<?> parameterExpression = criteriaBuilder.parameter(campo.getJavaType(), "p" + i);
				
				predicate = criteriaBuilder.and(
					predicate, 
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
						predicate = criteriaBuilder.and(
							predicate, 
							criteriaBuilder.greaterThan(campo.as(Date.class), parameterExpression.as(Date.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							DateFormat.getInstance().parse(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						predicate = criteriaBuilder.and(
							predicate, 
							criteriaBuilder.greaterThan(campo.as(Long.class), parameterExpression.as(Long.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							new Long(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(String.class)) {
						predicate = criteriaBuilder.and(
							predicate, 
							criteriaBuilder.greaterThan(campo.as(String.class), parameterExpression.as(String.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							metadataCondicion.getValores().toArray(new String[]{})[0]
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						predicate = criteriaBuilder.and(
							predicate, 
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
						predicate = criteriaBuilder.and(
							predicate, 
							criteriaBuilder.lessThan(campo.as(Date.class), parameterExpression.as(Date.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							DateFormat.getInstance().parse(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						predicate = criteriaBuilder.and(
							predicate, 
							criteriaBuilder.lessThan(campo.as(Long.class), parameterExpression.as(Long.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							new Long(metadataCondicion.getValores().toArray(new String[]{})[0])
						);
					} else if (campo.getJavaType().equals(String.class)) {
						predicate = criteriaBuilder.and(
							predicate, 
							criteriaBuilder.lessThan(campo.as(String.class), parameterExpression.as(String.class))
						);
						
						parameterValues.put(
							parameterExpression.getName(), 
							metadataCondicion.getValores().toArray(new String[]{})[0]
						);
					} else if (campo.getJavaType().equals(Double.class)) {
						predicate = criteriaBuilder.and(
							predicate, 
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
				
				predicate = criteriaBuilder.and(
					predicate, 
					criteriaBuilder.like(campo.as(String.class), parameterExpression.as(String.class))
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
						predicate = criteriaBuilder.and(
							predicate, 
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
						predicate = criteriaBuilder.and(
							predicate, 
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
						predicate = criteriaBuilder.and(
							predicate, 
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
						predicate = criteriaBuilder.and(
							predicate, 
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
			}
			
			i++;
		}
		
		criteriaQuery.where(predicate);
		
		TypedQuery<ACMInterfacePrepago> query = entityManager.createQuery(criteriaQuery);
		
		for (String parameterName : parameterValues.keySet()) {
			query.setParameter(parameterName, parameterValues.get(parameterName));
		}
		
		return query;
	}
	
	public void reprocesar(MetadataConsulta metadataConsulta) {
		try {
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFact(new Date());
			acmInterfaceProceso.setFechaInicio(new Date());
			acmInterfaceProceso.setTerm(new Long(1));
			acmInterfaceProceso.setUact(new Long(1));
			
			acmInterfaceProceso = iACMInterfaceProcesoBean.save(acmInterfaceProceso);
			
			TypedQuery<ACMInterfacePrepago> query = this.construirQuery(metadataConsulta);
			
			for (ACMInterfacePrepago acmInterfacePrepago : query.getResultList()) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setEstado(
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
				acmInterfaceMid.setMid(acmInterfacePrepago.getMid());
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				iACMInterfaceMidBean.update(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}