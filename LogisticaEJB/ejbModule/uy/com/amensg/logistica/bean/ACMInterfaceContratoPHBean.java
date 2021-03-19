package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
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
import javax.persistence.Query;
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

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceContratoPHBean implements IACMInterfaceContratoPHBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitPHXA")
	private EntityManager entityManagerPH;
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogisticaXA")
	private EntityManager entityManagerELARED;
	
	@EJB
	private IContratoBean iContratoBean;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IEstadoBean iEstadoBean;
	
	private CriteriaQuery<ACMInterfaceContrato> criteriaQuery;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Query para obtener los registros de muestra
			TypedQuery<ACMInterfaceContrato> queryMuestra = this.construirQuery(metadataConsulta);
			queryMuestra.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfaceContrato acmInterfaceContrato : queryMuestra.getResultList()) {
				registrosMuestra.add(acmInterfaceContrato);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
			
			// Query para obtener la cantidad de registros
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			criteriaQueryCount.select(
				criteriaBuilder.count(criteriaQueryCount.from(ACMInterfaceContrato.class))
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
			Root<ACMInterfaceContrato> rootCount = criteriaQueryCount.from(ACMInterfaceContrato.class);
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
						
						Path<ACMInterfaceContrato> field = rootCount;
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
	
	public Collection<TipoContrato> listTipoContratos() {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			Query query = entityManagerPH.createQuery(
				"SELECT c.tipoContratoDescripcion, COUNT(c)"
				+ " FROM ACMInterfaceContrato c"
				+ " WHERE c.tipoContratoDescripcion IS NOT NULL"
				+ " GROUP BY c.tipoContratoDescripcion"
				+ " ORDER BY COUNT(c) DESC"
			);
			
			for (Object object : query.getResultList()) {
				Object[] fields = (Object[]) object;
				
				TipoContrato tipoContrato = new TipoContrato();
				
				// tipoContrato.setTipoContratoCodigo((String) fields[0]);
				tipoContrato.setTipoContratoDescripcion((String) fields[0]);
				
				result.add(tipoContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta) {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			// Query con todos los filtros
			TypedQuery<ACMInterfaceContrato> queryACMInterfaceContrato = 
				this.construirQuery(metadataConsulta);
			
			CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
			
			// Criteria query para los tipos de contrato
			CriteriaQuery<Object[]> criteriaQueryTiposContrato = criteriaBuilder.createQuery(Object[].class);
			
			Root<ACMInterfaceContrato> root = criteriaQueryTiposContrato.from(ACMInterfaceContrato.class);
			
			criteriaQueryTiposContrato.multiselect(
				root.get("tipoContratoDescripcion")
			).distinct(true);
			
			// Mismo where que la primera Query
			criteriaQueryTiposContrato.where(criteriaQuery.getRestriction());
			
			TypedQuery<Object[]> queryTiposContrato = entityManagerPH.createQuery(criteriaQueryTiposContrato);
			
			for (Parameter<?> parameter : queryACMInterfaceContrato.getParameters()) {
				queryTiposContrato.setParameter(
					parameter.getName(), 
					queryACMInterfaceContrato.getParameterValue(parameter));
			}
			
			for (Object object : queryTiposContrato.getResultList()) {
				if (object != null) {
					TipoContrato tipoContrato = new TipoContrato();
					
					// tipoContrato.setTipoContratoCodigo((String) fields[0]);
					tipoContrato.setTipoContratoDescripcion((String) object);
					
					result.add(tipoContrato);
				}
			}
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
				ACMInterfaceContrato acmInterfaceContrato = (ACMInterfaceContrato) object;
				
				// Agregar línea al archivo.
				printWriter.println(this.buildCSVLine(acmInterfaceContrato, ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String preprocesarAsignacion(MetadataConsulta metadataConsulta, Empresa empresa) {
		String result = null;
		
		try {
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfaceContrato acmInterfaceContrato : this.listSubconjunto(metadataConsulta)) {
				mids.add(acmInterfaceContrato.getMid());
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
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR"))
				);
			
			Collection<ACMInterfaceContrato> subconjunto = this.listSubconjunto(metadataConsulta);
			
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfaceContrato acmInterfaceContrato : subconjunto) {
				mids.add(acmInterfaceContrato.getMid());
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
			
			for (ACMInterfaceContrato acmInterfaceContrato : subconjunto) {
				acmInterfaceContrato.setFechaExportacionAnterior(
					acmInterfaceContrato.getFechaExportacion()
				);
				acmInterfaceContrato.setFechaExportacion(currentDate);
				
				acmInterfaceContrato = entityManagerPH.merge(acmInterfaceContrato);
				
				// Agregar línea al archivo.
				printWriter.println(this.buildCSVLine(acmInterfaceContrato, observaciones));
				
				switch (map.get(acmInterfaceContrato.getMid())) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						insertContrato.setParameter(9, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null, StringType.INSTANCE);
						insertContrato.setParameter(10, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null, StringType.INSTANCE);
						insertContrato.setParameter(11, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null, StringType.INSTANCE);
						insertContrato.setParameter(12, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null, StringType.INSTANCE);
						insertContrato.setParameter(13, acmInterfaceContrato.getDocumentoTipo(), LongType.INSTANCE);
						insertContrato.setParameter(14, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null, StringType.INSTANCE);
						insertContrato.setParameter(15, acmInterfaceContrato.getFechaFinContrato(), DateType.INSTANCE);
						insertContrato.setParameter(16, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null, StringType.INSTANCE);
						insertContrato.setParameter(17, acmInterfaceContrato.getMid(), LongType.INSTANCE);
						insertContrato.setParameter(18, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null, StringType.INSTANCE);
						insertContrato.setParameter(19, acmInterfaceContrato.getNumeroCliente(), LongType.INSTANCE);
						insertContrato.setParameter(20, acmInterfaceContrato.getNumeroContrato(), LongType.INSTANCE);
						insertContrato.setParameter(21, observaciones != "" ? observaciones : null, StringType.INSTANCE);
						insertContrato.setParameter(22, acmInterfaceContrato.getTipoContratoCodigo() != "" ? acmInterfaceContrato.getTipoContratoCodigo() : null, StringType.INSTANCE);
						insertContrato.setParameter(23, acmInterfaceContrato.getTipoContratoDescripcion() != "" ? acmInterfaceContrato.getTipoContratoDescripcion() : null, StringType.INSTANCE);
						
						insertContrato.executeUpdate();
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						System.out.println("Se omite " + acmInterfaceContrato.getMid());
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						selectContratoExisteEmpresa.setParameter("mid", acmInterfaceContrato.getMid(), LongType.INSTANCE);
						
						Long contratoId = (Long) selectContratoExisteEmpresa.list().get(0).get(0);
						
						updateContrato.setParameter(4, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null, StringType.INSTANCE);
						updateContrato.setParameter(5, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null, StringType.INSTANCE);
						updateContrato.setParameter(6, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null, StringType.INSTANCE);
						updateContrato.setParameter(7, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null, StringType.INSTANCE);
						updateContrato.setParameter(8, acmInterfaceContrato.getDocumentoTipo(), LongType.INSTANCE);
						updateContrato.setParameter(9, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null, StringType.INSTANCE);
						updateContrato.setParameter(10, acmInterfaceContrato.getFechaFinContrato(), DateType.INSTANCE);
						updateContrato.setParameter(11, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null, StringType.INSTANCE);
						updateContrato.setParameter(12, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null, StringType.INSTANCE);
						updateContrato.setParameter(13, acmInterfaceContrato.getNumeroCliente(), LongType.INSTANCE);
						updateContrato.setParameter(14, acmInterfaceContrato.getNumeroContrato(), LongType.INSTANCE);
						updateContrato.setParameter(15, observaciones != "" ? observaciones : null, StringType.INSTANCE);
						updateContrato.setParameter(16, acmInterfaceContrato.getTipoContratoCodigo() != "" ? acmInterfaceContrato.getTipoContratoCodigo() : null, StringType.INSTANCE);
						updateContrato.setParameter(17, acmInterfaceContrato.getTipoContratoDescripcion() != "" ? acmInterfaceContrato.getTipoContratoDescripcion() : null, StringType.INSTANCE);
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
			TypedQuery<ACMInterfaceContrato> query = entityManagerPH.createQuery(
				"SELECT c FROM ACMInterfaceContrato c WHERE c.fechaExportacion IS NOT NULL ORDER BY c.fechaExportacion DESC",
				ACMInterfaceContrato.class
			);
			query.setMaxResults(1);
			
			Collection<ACMInterfaceContrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ACMInterfaceContrato acmInterfaceContrato = resultList.toArray(new ACMInterfaceContrato[]{})[0];
				
				if (acmInterfaceContrato.getFechaExportacion() != null) {
					TypedQuery<ACMInterfaceContrato> queryUltimaExportacion = entityManagerPH.createQuery(
						"SELECT c FROM ACMInterfaceContrato c WHERE c.fechaExportacion = :fechaExportacion"
						, ACMInterfaceContrato.class
					);
					queryUltimaExportacion.setParameter("fechaExportacion", acmInterfaceContrato.getFechaExportacion());
					
					TypedQuery<ContratoRoutingHistory> queryContratoRoutingHistory = entityManagerELARED.createQuery(
						"SELECT crh FROM ContratoRoutingHistory crh"
						+ " WHERE crh.fact = :fechaExportacion", 
						ContratoRoutingHistory.class
					);
					queryContratoRoutingHistory.setParameter("fechaExportacion", acmInterfaceContrato.getFechaExportacion());
					
					Collection<ACMInterfaceContrato> acmInterfaceContratosUltimaFechaExportacion = queryUltimaExportacion.getResultList();
					Collection<ContratoRoutingHistory> contratoRoutingHistoriesUltimaFechaExportacion = queryContratoRoutingHistory.getResultList();
					
					for (ACMInterfaceContrato acmInterfaceContratoUltimaExportacion : acmInterfaceContratosUltimaFechaExportacion) {
						acmInterfaceContratoUltimaExportacion.setFechaExportacion(
							acmInterfaceContratoUltimaExportacion.getFechaExportacionAnterior()
						);
						acmInterfaceContratoUltimaExportacion.setFechaExportacionAnterior(
							acmInterfaceContrato.getFechaExportacion()
						);
							
						entityManagerPH.merge(acmInterfaceContratoUltimaExportacion);
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

	private TypedQuery<ACMInterfaceContrato> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
		
		criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceContrato.class);
		
		Root<ACMInterfaceContrato> root = criteriaQuery.from(ACMInterfaceContrato.class);
		
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
		
		TypedQuery<ACMInterfaceContrato> query = entityManagerPH.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
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
		}
		
		return query;
	}

	private Collection<ACMInterfaceContrato> listSubconjunto(MetadataConsulta metadataConsulta) {
		Collection<ACMInterfaceContrato> resultList = new LinkedList<ACMInterfaceContrato>();
		
		TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
		
		if (metadataConsulta.getTamanoSubconjunto() != null) {
			List<ACMInterfaceContrato> toOrder = query.getResultList();
			
			Collections.shuffle(toOrder);
			
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
		
		return resultList;
	}

	private String buildCSVLine(ACMInterfaceContrato acmInterfaceContrato, String observaciones) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		String result = 
			acmInterfaceContrato.getMid()
			+ ";" + (acmInterfaceContrato.getFechaFinContrato() != null ? 
				format.format(acmInterfaceContrato.getFechaFinContrato())
				: "")
			+ ";" + (acmInterfaceContrato.getTipoContratoCodigo() != null ?
				acmInterfaceContrato.getTipoContratoCodigo() 
				: "")
			+ ";" + (acmInterfaceContrato.getTipoContratoDescripcion() != null ?
				acmInterfaceContrato.getTipoContratoDescripcion()
				: "")
			+ ";" + (acmInterfaceContrato.getDocumentoTipo() != null ?
				acmInterfaceContrato.getDocumentoTipo()
				: "")
			+ ";'" + (acmInterfaceContrato.getDocumento() != null ?
				acmInterfaceContrato.getDocumento()
				: "")
			+ ";" + (acmInterfaceContrato.getNombre() != null ?
				acmInterfaceContrato.getNombre()
				: "")
			+ ";" + (acmInterfaceContrato.getDireccion() != null ?
				acmInterfaceContrato.getDireccion()
				: "")
			+ ";" + (acmInterfaceContrato.getCodigoPostal() != null ?
				acmInterfaceContrato.getCodigoPostal()
				: "")
			+ ";" + (acmInterfaceContrato.getLocalidad() != null ?
				acmInterfaceContrato.getLocalidad()
				: "")
			+ ";" + (acmInterfaceContrato.getEquipo() != null ?
				acmInterfaceContrato.getEquipo()
				: "")
			+ ";" + (acmInterfaceContrato.getAgente() != null ?
				acmInterfaceContrato.getAgente()
				: "")
			+ ";" + (acmInterfaceContrato.getFechaExportacion() != null ?
				format.format(acmInterfaceContrato.getFechaExportacion())
				: "")
			+ ";" + format.format(acmInterfaceContrato.getFact())
			+ ";" + (acmInterfaceContrato.getNumeroCliente() != null ?
				acmInterfaceContrato.getNumeroCliente()
				: "")
			+ ";" + (acmInterfaceContrato.getNumeroContrato() != null ?
				acmInterfaceContrato.getNumeroContrato()
				: "")
			+ ";" + (observaciones != null ?
				observaciones
				: "");
		
		return result;
	}
}