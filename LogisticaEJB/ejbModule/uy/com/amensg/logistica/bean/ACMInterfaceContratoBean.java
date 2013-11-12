package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@Stateless
public class ACMInterfaceContratoBean implements IACMInterfaceContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	private Predicate where;
	private Map<String, Object> parameterValues = new HashMap<String, Object>();
	
	public Collection<ACMInterfaceContrato> list() {
		Collection<ACMInterfaceContrato> result = new LinkedList<ACMInterfaceContrato>();
		
		try {
			Query query = entityManager.createQuery("SELECT c FROM ACMInterfaceContrato c");
			
			for (Object object : query.getResultList()) {
				result.add((ACMInterfaceContrato) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			List<ACMInterfaceContrato> resultList = query.getResultList();
			
			for (ACMInterfaceContrato acmInterfaceContrato : resultList) {
				registrosMuestra.add(acmInterfaceContrato);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(ACMInterfaceContrato.class)));
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

	public String exportarAExcel(MetadataConsulta metadataConsulta) {
		String result = null;
		
		try {
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			Collection<ACMInterfaceContrato> resultList = new LinkedList<ACMInterfaceContrato>();
			if (metadataConsulta.getTamanoSubconjunto() != null) {
				List<ACMInterfaceContrato> toOrder = query.getResultList();
				
				Collections.sort(toOrder, new Comparator<ACMInterfaceContrato>() {
					public int compare(ACMInterfaceContrato arg0, ACMInterfaceContrato arg1) {
						Random random = new Random();
						
						return random.nextBoolean() ? 1 : -1;
					}
				});
				
				int i = 0;
				for (ACMInterfaceContrato acmInterfaceContrato : toOrder) {
					resultList.add(acmInterfaceContrato);
					
					i++;
					if (i == metadataConsulta.getTamanoSubconjunto()) {
						break;
					}
				}
			} else {
				resultList = query.getResultList();
			}
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date currentDate = gregorianCalendar.getTime();
			
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
			
			for (ACMInterfaceContrato acmInterfaceContrato : resultList) {
				acmInterfaceContrato.setFechaExportacionAnterior(
					acmInterfaceContrato.getFechaExportacion()
				);
				acmInterfaceContrato.setFechaExportacion(currentDate);
				
				acmInterfaceContrato = entityManager.merge(acmInterfaceContrato);
				
				printWriter.println(
					acmInterfaceContrato.getMid()
					+ ";" + (acmInterfaceContrato.getFechaFinContrato() != null ? 
						format.format(acmInterfaceContrato.getFechaFinContrato())
						: "")
					+ ";" + acmInterfaceContrato.getTipoContratoCodigo()
					+ ";" + acmInterfaceContrato.getTipoContratoDescripcion()
					+ ";" + acmInterfaceContrato.getDocumentoTipo()
					+ ";'" + acmInterfaceContrato.getDocumento()
					+ ";" + acmInterfaceContrato.getNombre()
					+ ";" + acmInterfaceContrato.getDireccion()
					+ ";" + acmInterfaceContrato.getCodigoPostal()
					+ ";" + acmInterfaceContrato.getLocalidad()
					+ ";" + acmInterfaceContrato.getEquipo()
					+ ";" + acmInterfaceContrato.getAgente()
					+ ";" + (acmInterfaceContrato.getFechaExportacion() != null ?
						format.format(acmInterfaceContrato.getFechaExportacion())
						: "")
					+ ";" + format.format(acmInterfaceContrato.getFact())
				);
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta) {
		try {
			TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(
				"SELECT c FROM ACMInterfaceContrato c ORDER BY c.fechaExportacion DESC",
				ACMInterfaceContrato.class
			);
			
			Date fechaExportacion = null;
			for (ACMInterfaceContrato acmInterfaceContrato : query.getResultList()) {
				if (fechaExportacion == null 
					|| acmInterfaceContrato.getFechaExportacion()
						.equals(fechaExportacion)) {
					fechaExportacion = acmInterfaceContrato.getFechaExportacion();
					
					acmInterfaceContrato.setFechaExportacion(
						acmInterfaceContrato.getFechaExportacionAnterior()
					);
					acmInterfaceContrato.setFechaExportacionAnterior(
						fechaExportacion
					);
					
					entityManager.merge(acmInterfaceContrato);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reprocesar(MetadataConsulta metadataConsulta) {
		try {
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFact(new Date());
			acmInterfaceProceso.setFechaInicio(new Date());
			acmInterfaceProceso.setTerm(new Long(1));
			acmInterfaceProceso.setUact(new Long(1));
			
			acmInterfaceProceso = iACMInterfaceProcesoBean.save(acmInterfaceProceso);
			
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			for (ACMInterfaceContrato acmInterfaceContrato : query.getResultList()) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setEstado(
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
				acmInterfaceMid.setMid(acmInterfaceContrato.getMid());
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				entityManager.merge(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private TypedQuery<ACMInterfaceContrato> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ACMInterfaceContrato> criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceContrato.class);
		Root<ACMInterfaceContrato> root = criteriaQuery.from(ACMInterfaceContrato.class);
		
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
		
		TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(criteriaQuery);
		
		for (String parameterName : parameterValues.keySet()) {
			query.setParameter(parameterName, parameterValues.get(parameterName));
		}
		
		return query;
	}
}