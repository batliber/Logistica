package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfacePrepagoPHBean implements IACMInterfacePrepagoPHBean {
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitPHXA")
	private EntityManager entityManagerPH;
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogisticaXA")
	private EntityManager entityManagerELARED;
	
	@EJB
	private IContratoBean iContratoBean;
	
	private CriteriaQuery<ACMInterfacePrepago> criteriaQuery;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Query para obtener los registros de muestra
			TypedQuery<ACMInterfacePrepago> queryMuestra = this.construirQuery(metadataConsulta);
			queryMuestra.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfacePrepago acmInterfacePrepago : queryMuestra.getResultList()) {
				registrosMuestra.add(acmInterfacePrepago);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
			
			// Query para obtener la cantidad de registros
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			criteriaQueryCount.select(
				criteriaBuilder.count(criteriaQueryCount.from(ACMInterfacePrepago.class))
			);
			
			criteriaQueryCount.where(criteriaQuery.getRestriction());
			
			TypedQuery<Long> queryCount = entityManagerPH.createQuery(criteriaQueryCount);
			
			for (Parameter<?> parameter : queryMuestra.getParameters()) {
				queryCount.setParameter(parameter.getName(), queryMuestra.getParameterValue(parameter));
			}
			
			result.setCantidadRegistros(queryCount.getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Cuenta la cantidad de ACMInterfaceContrato que cumplen los criterios encapsulados en metadataConsulta.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @return Cantidad de registros que cumplen con los criterios.
	 */
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<ACMInterfacePrepago> rootCount = criteriaQueryCount.from(ACMInterfacePrepago.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("mid")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManagerPH.createQuery(criteriaQueryCount);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ACMInterfacePrepago> field = rootCount;
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
	 * Exporta los datos que cumplen con los criterios especificados a un archivo .csv 
	 * de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			for (Object object : this.list(metadataConsulta).getRegistrosMuestra()) {
				ACMInterfacePrepago acmInterfacePrepago = (ACMInterfacePrepago) object;
				
				// Agregar línea al archivo.
				printWriter.println(this.buildCSVLine(acmInterfacePrepago, ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Calcula estadístcas para la asignación de MIDs con los criterios seleccionados.
	 * Devuelve un String con 3 tipos de caso:
	 *     - Cantidad a asignar
	 *     - Cantidad a omitir
	 *     - Cantidad a reemplazar
	 * 
	 * @param filtro con la selección
	 * @param Empresa a asignar la selección
	 * @return String con las estadísticas de la asignación de la selección a la empresa.
	 */
	public String preprocesarAsignacion(MetadataConsulta metadataConsulta, Empresa empresa) {
		String result = null;
		
		try {
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfacePrepago acmInterfacePrepago: this.listSubconjunto(metadataConsulta)) {
				mids.add(acmInterfacePrepago.getMid());
			}
			
			Map<Long, Integer> map = iContratoBean.preprocesarConjunto(mids, empresa.getId());
			
			Long importar = Long.valueOf(0);
			Long sobreescribir = Long.valueOf(0);
			Long omitir = Long.valueOf(0);
			for (Entry<Long, Integer> entry : map.entrySet()) {
				switch (entry.getValue()) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						importar++;
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						omitir++;
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						sobreescribir++;
						
						break;
				}
			}
			
			result =
				"Se asignarán " + importar + " MIDs nuevos.|"
				+ "Se sobreescribirán " + sobreescribir + " MIDs.|"
				+ "Se omitirán " + omitir + " MIDs.";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Asigna los MIDs que cumplen con los criterios a la Empresa.
	 * 
	 * @param filtro con la selección
	 * @param Empresa a asignar la selección
	 * @param observaciones a incluír en la asignación.
	 */
	public String asignar(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date currentDate = gregorianCalendar.getTime();
			
			String fileName =
				Configuration.getInstance().getProperty("exportacion.carpeta")
					+ gregorianCalendar.get(GregorianCalendar.YEAR)
					+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1)
					+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH)
					+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY)
					+ gregorianCalendar.get(GregorianCalendar.MINUTE)
					+ gregorianCalendar.get(GregorianCalendar.SECOND)
					+ ".csv";
					
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
			
			Rol rolSupervisorCallCenter = 
				entityManagerELARED.find(Rol.class, Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
			
			Estado estado = 
				entityManagerELARED.find(Estado.class, Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Collection<ACMInterfacePrepago> subconjunto = this.listSubconjunto(metadataConsulta);
			
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfacePrepago acmInterfacePrepago : subconjunto) {
				mids.add(acmInterfacePrepago.getMid());
			}
			Map<Long, Integer> map = iContratoBean.preprocesarConjunto(mids, empresa.getId());
			
			Session hibernateSession = entityManagerELARED.unwrap(Session.class);
			
			NativeQuery<Tuple> selectContratoExisteEmpresa = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE mid = :mid"
				+ " AND empresa_id = :empresaId"
				+ " AND estado_id = :estadoLlamarId",
				Tuple.class
			);
			selectContratoExisteEmpresa.addScalar("id", LongType.INSTANCE);
			selectContratoExisteEmpresa.setParameter("empresaId", empresa.getId(), LongType.INSTANCE);
			selectContratoExisteEmpresa.setParameter("estadoLlamarId", estado.getId(), LongType.INSTANCE);
			
			NativeQuery<?> insertContrato = hibernateSession.createNativeQuery(
				"INSERT INTO contrato("
					+ " id,"
					+ " numero_tramite,"
					+ " random,"
					+ " empresa_id,"
					+ " estado_id,"
					+ " rol_id,"
					+ " fcre,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					+ " ucre,"
					+ " agente,"
					+ " codigo_postal,"
					+ " direccion,"
					+ " documento,"
					+ " documento_tipo,"
					+ " equipo,"
					+ " fecha_fin_contrato,"
					+ " localidad,"
					+ " mid,"
					+ " nombre,"
					+ " numero_cliente,"
					+ " numero_contrato,"
					+ " observaciones,"
					+ " tipo_contrato_codigo,"
					+ " tipo_contrato_descripcion"
				+ " ) VALUES ("
					+ " nextval('hibernate_sequence'),"
					+ " nextval('numero_tramite_sequence'),"
					+ " CAST(random() * 1000000 AS integer),"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ " )"
			);
			
			insertContrato.setParameter(1, empresa.getId(), LongType.INSTANCE);
			insertContrato.setParameter(2, estado.getId(), LongType.INSTANCE);
			insertContrato.setParameter(3, rolSupervisorCallCenter.getId(), LongType.INSTANCE);
			
			insertContrato.setParameter(4, currentDate, TimestampType.INSTANCE);
			insertContrato.setParameter(5, currentDate, TimestampType.INSTANCE);
			insertContrato.setParameter(6, Long.valueOf(1), LongType.INSTANCE);
			insertContrato.setParameter(7, Long.valueOf(1), LongType.INSTANCE);
			insertContrato.setParameter(8, Long.valueOf(1), LongType.INSTANCE);
			
			NativeQuery<?> updateContrato = hibernateSession.createNativeQuery(
				"UPDATE contrato"
				+ " SET random = CAST(random() * 1000000 AS integer),"
					+ " fact = ?,"
					+ " term = ?,"
					+ " uact = ?,"
					+ " agente = ?,"
					+ " codigo_postal = ?,"
					+ " direccion = ?,"
					+ " documento = ?,"
					+ " documento_tipo = ?,"
					+ " equipo = ?,"
					+ " fecha_fin_contrato = ?,"
					+ " localidad = ?,"
					+ " nombre = ?,"
					+ " numero_cliente = ?,"
					+ " numero_contrato = ?,"
					+ " observaciones = ?,"
					+ " tipo_contrato_codigo = ?,"
					+ " tipo_contrato_descripcion = ?"
				+ " WHERE id = ?"
			);
			
			updateContrato.setParameter(1, currentDate, TimestampType.INSTANCE);
			updateContrato.setParameter(2, Long.valueOf(1), LongType.INSTANCE);
			updateContrato.setParameter(3, Long.valueOf(1), LongType.INSTANCE);
			
			NativeQuery<?> insertContratoRoutingHistory = hibernateSession.createNativeQuery(
				"INSERT INTO contrato_routing_history("
					+ " id,"
					+ " fecha,"
					+ " empresa_id,"
					+ " usuario_id,"
					+ " rol_id,"
					+ " ucre,"
					+ " uact,"
					+ " fact,"
					+ " fcre,"
					+ " term,"
					+ " contrato_id,"
					+ " estado_id"
				+ " ) SELECT nextval('hibernate_sequence'),"
					+ " c.fact,"
					+ " c.empresa_id,"
					+ " null,"
					+ " c.rol_id,"
					+ " c.ucre,"
					+ " c.uact,"
					+ " c.fact,"
					+ " c.fcre,"
					+ " c.term,"
					+ " c.id,"
					+ " c.estado_id"
				+ " FROM contrato c"
				+ " WHERE c.fact = ?"
				+ " AND NOT EXISTS ("
					+ " SELECT 1 FROM contrato_routing_history WHERE contrato_id = c.id"
				+ " )"
			);
			
			insertContratoRoutingHistory.setParameter(1, currentDate, TimestampType.INSTANCE);
			
			DecimalFormat formatMonto = new DecimalFormat("0.00");
			
			for (ACMInterfacePrepago acmInterfacePrepago : subconjunto) {
				acmInterfacePrepago.setFechaExportacionAnterior(
					acmInterfacePrepago.getFechaExportacion()
				);
				acmInterfacePrepago.setFechaExportacion(currentDate);
				
				acmInterfacePrepago = entityManagerPH.merge(acmInterfacePrepago);
				
				// Agregar línea al archivo.
				printWriter.println(this.buildCSVLine(acmInterfacePrepago, observaciones));
				
				switch (map.get(acmInterfacePrepago.getMid())) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						insertContrato.setParameter(9, null, StringType.INSTANCE);
						insertContrato.setParameter(10, null, StringType.INSTANCE);
						insertContrato.setParameter(11, null, StringType.INSTANCE);
						insertContrato.setParameter(12, null, StringType.INSTANCE);
						insertContrato.setParameter(13, null, LongType.INSTANCE);
						insertContrato.setParameter(14, null, StringType.INSTANCE);
						insertContrato.setParameter(15, null, DateType.INSTANCE);
						insertContrato.setParameter(16, null, StringType.INSTANCE);
						insertContrato.setParameter(17, acmInterfacePrepago.getMid(), LongType.INSTANCE);
						insertContrato.setParameter(18, null, StringType.INSTANCE);
						insertContrato.setParameter(19, null, LongType.INSTANCE);
						insertContrato.setParameter(20, null, LongType.INSTANCE);
						insertContrato.setParameter(21, 
							"Monto promedio: " 
								+ (acmInterfacePrepago.getMontoPromedio() != null ? formatMonto.format(acmInterfacePrepago.getMontoPromedio()) : "0") 
								+ ".\n"
							+ observaciones,
							StringType.INSTANCE
						);
						insertContrato.setParameter(22, null, StringType.INSTANCE);
						insertContrato.setParameter(23, null, StringType.INSTANCE);
						
						insertContrato.executeUpdate();
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						System.out.println("Se omite " + acmInterfacePrepago.getMid());
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						selectContratoExisteEmpresa.setParameter("mid", acmInterfacePrepago.getMid(), LongType.INSTANCE);
						
						Long contratoId = (Long) selectContratoExisteEmpresa.list().get(0).get(0);
						
						updateContrato.setParameter(4, null, StringType.INSTANCE);
						updateContrato.setParameter(5, null, StringType.INSTANCE);
						updateContrato.setParameter(6, null, StringType.INSTANCE);
						updateContrato.setParameter(7, null, StringType.INSTANCE);
						updateContrato.setParameter(8, null, LongType.INSTANCE);
						updateContrato.setParameter(9, null, StringType.INSTANCE);
						updateContrato.setParameter(10, null, DateType.INSTANCE);
						updateContrato.setParameter(11, null, StringType.INSTANCE);
						updateContrato.setParameter(12, null, StringType.INSTANCE);
						updateContrato.setParameter(13, null, LongType.INSTANCE);
						updateContrato.setParameter(14, null, LongType.INSTANCE);
						updateContrato.setParameter(15, 
							"Monto promedio: " 
								+ (acmInterfacePrepago.getMontoPromedio() != null ? formatMonto.format(acmInterfacePrepago.getMontoPromedio()) : "0") 
								+ ".\n"
							+ observaciones,
							StringType.INSTANCE
						);
						updateContrato.setParameter(16, null, StringType.INSTANCE);
						updateContrato.setParameter(17, null, StringType.INSTANCE);
						updateContrato.setParameter(18, contratoId, LongType.INSTANCE);
						
						updateContrato.executeUpdate();
						
						break;
				}
			}
			
			insertContratoRoutingHistory.executeUpdate();
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void deshacerAsignacion(MetadataConsulta metadataConsulta) {
		try {
			TypedQuery<ACMInterfacePrepago> query = entityManagerPH.createQuery(
				"SELECT p FROM ACMInterfacePrepago p WHERE p.fechaExportacion IS NOT NULL ORDER BY p.fechaExportacion DESC",
				ACMInterfacePrepago.class
			);
			query.setMaxResults(1);
			
			Collection<ACMInterfacePrepago> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ACMInterfacePrepago acmInterfacePrepago = resultList.toArray(new ACMInterfacePrepago[]{})[0];
				
				if (acmInterfacePrepago.getFechaExportacion() != null) {
					TypedQuery<ACMInterfacePrepago> queryUltimaExportacion = entityManagerPH.createQuery(
						"SELECT p FROM ACMInterfacePrepago p WHERE p.fechaExportacion = :fechaExportacion"
						, ACMInterfacePrepago.class
					);
					queryUltimaExportacion.setParameter("fechaExportacion", acmInterfacePrepago.getFechaExportacion());
					
					TypedQuery<ContratoRoutingHistory> queryContratoRoutingHistory = entityManagerELARED.createQuery(
						"SELECT crh FROM ContratoRoutingHistory crh"
						+ " WHERE crh.fact = :fechaExportacion", 
						ContratoRoutingHistory.class
					);
					queryContratoRoutingHistory.setParameter("fechaExportacion", acmInterfacePrepago.getFechaExportacion());
					
					Collection<ACMInterfacePrepago> acmInterfacePrepagosUltimaFechaExportacion = queryUltimaExportacion.getResultList();
					Collection<ContratoRoutingHistory> contratoRoutingHistoriesUltimaFechaExportacion = queryContratoRoutingHistory.getResultList();
					
					for (ACMInterfacePrepago acmInterfacePrepagoUltimaExportacion : acmInterfacePrepagosUltimaFechaExportacion) {
						acmInterfacePrepagoUltimaExportacion.setFechaExportacion(
							acmInterfacePrepagoUltimaExportacion.getFechaExportacionAnterior()
						);
						acmInterfacePrepagoUltimaExportacion.setFechaExportacionAnterior(
							acmInterfacePrepago.getFechaExportacion()
						);
							
						entityManagerPH.merge(acmInterfacePrepagoUltimaExportacion);
					}
					
					for (ContratoRoutingHistory contratoRoutingHistory : contratoRoutingHistoriesUltimaFechaExportacion) {
						Contrato contrato = contratoRoutingHistory.getContrato();
						
						entityManagerELARED.remove(contratoRoutingHistory);
						entityManagerELARED.remove(contrato);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private TypedQuery<ACMInterfacePrepago> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
		
		criteriaQuery = criteriaBuilder.createQuery(ACMInterfacePrepago.class);
		
		Root<ACMInterfacePrepago> root = criteriaQuery.from(ACMInterfacePrepago.class);
		
		List<Order> orders = new LinkedList<Order>();
		
		for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
			if (metadataOrdenacion.getAscendente()) {
				orders.add(criteriaBuilder.asc(root.get(metadataOrdenacion.getCampo())));
			} else {
				orders.add(criteriaBuilder.desc(root.get(metadataOrdenacion.getCampo())));
			}
		}
		
		criteriaQuery
			.select(root)
			.orderBy(orders)
			.where(new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root));
		
		TypedQuery<ACMInterfacePrepago> query = entityManagerPH.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			for (String valor : metadataCondicion.getValores()) {
				Path<?> campo = root.get(metadataCondicion.getCampo());
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						query.setParameter(
							"p" + i,
							format.parse(valor)
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						query.setParameter(
							"p" + i,
							Long.parseLong(valor)
						);
					} else if (campo.getJavaType().equals(String.class)) {
						query.setParameter(
							"p" + i,
							valor
						);
					} else if (campo.getJavaType().equals(Double.class)) {
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
		
		return query;
	}

	private Collection<ACMInterfacePrepago> listSubconjunto(MetadataConsulta metadataConsulta) {
		Collection<ACMInterfacePrepago> resultList = new LinkedList<ACMInterfacePrepago>();
		
		TypedQuery<ACMInterfacePrepago> query = this.construirQuery(metadataConsulta);
		
		if (metadataConsulta.getTamanoSubconjunto() != null) {
			List<ACMInterfacePrepago> toOrder = query.getResultList();
			
			Collections.shuffle(toOrder);
			
			int i = 0;
			for (ACMInterfacePrepago acmInterfacePrepago : toOrder) {
				resultList.add(acmInterfacePrepago);
				
				i++;
				if (i == metadataConsulta.getTamanoSubconjunto()) {
					break;
				}
			}
		} else {
			resultList = query.getResultList();
		}
		
		return resultList;
	}
	
	private String buildCSVLine(ACMInterfacePrepago acmInterfacePrepago, String observaciones) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatMesAno = new SimpleDateFormat("MM/yyyy");
		DecimalFormat formatMonto = new DecimalFormat("0.00");
		
		String result = 
			acmInterfacePrepago.getMid()
			+ ";" + (acmInterfacePrepago.getMesAno() != null ?
				formatMesAno.format(acmInterfacePrepago.getMesAno())
				: "")
			+ ";" + (acmInterfacePrepago.getMontoMesActual() != null ?
				formatMonto.format(acmInterfacePrepago.getMontoMesActual())
				: "")
			+ ";" + (acmInterfacePrepago.getMontoMesAnterior1() != null ?
				formatMonto.format(acmInterfacePrepago.getMontoMesAnterior1())
				: "")
			+ ";" + (acmInterfacePrepago.getMontoMesAnterior2() != null ? 
				formatMonto.format(acmInterfacePrepago.getMontoMesAnterior2())
				: "")
			+ ";" + (acmInterfacePrepago.getMontoPromedio() != null ?
				formatMonto.format(acmInterfacePrepago.getMontoPromedio())
				: "")
			+ ";" + (acmInterfacePrepago.getFechaExportacion() != null ?
				format.format(acmInterfacePrepago.getFechaExportacion())
				: "")
			+ ";" + (acmInterfacePrepago.getFechaActivacionKit() != null ?
				format.format(acmInterfacePrepago.getFechaActivacionKit())
				: "")
			+ ";" + (
				"Monto promedio: " 
				+ (acmInterfacePrepago.getMontoPromedio() != null ? formatMonto.format(acmInterfacePrepago.getMontoPromedio()) : "0") 
				+ ". " + observaciones
			);
		
		return result;
	}
}