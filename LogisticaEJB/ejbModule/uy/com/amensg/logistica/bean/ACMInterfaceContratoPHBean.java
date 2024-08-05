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
import java.util.Random;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Parameter;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
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
import uy.com.amensg.logistica.util.ConfigurationPH;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceContratoPHBean implements IACMInterfaceContratoPHBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitPHXA")
	private EntityManager entityManagerPH;
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogisticaXA")
	private EntityManager entityManagerELARED;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
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
			CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
			
			CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
			
			Root<ACMInterfaceContrato> root = criteriaQuery.from(ACMInterfaceContrato.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
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
			
			criteriaQuery.multiselect(
				root.get("tipoContratoDescripcion")
			).distinct(true)
			.where(where);
			
			TypedQuery<Object[]> query = entityManagerPH.createQuery(criteriaQuery);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ACMInterfaceContrato> field = root;
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
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
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
				} else {
					i++;
				}
			}
			
			for (Object object : query.getResultList()) {
				if (object != null) {
					TipoContrato tipoContrato = new TipoContrato();
					
					// tipoContrato.setTipoContratoCodigo((String) fields[0]);
					tipoContrato.setTipoContratoDescripcion((String) ((Object[]) object)[0]);
					
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
						ConfigurationPH.getInstance().getProperty("exportacion.carpeta") + fileName
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
			Long ursec = Long.valueOf(0);
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
					case Constants.__COMPROBACION_IMPORTACION_URSEC:
						ursec++;
						
						break;
				}
			}
			
			result =
				"Se asignarán " + importar + " MIDs nuevos.|"
				+ "Se sobreescribirán " + sobreescribir + " MIDs.|"
				+ "Se omitirán por URSEC " + ursec + " MIDs.|"
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
				ConfigurationPH.getInstance().getProperty("exportacion.carpeta")
					+ gregorianCalendar.get(GregorianCalendar.YEAR)
					+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1)
					+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH)
					+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY)
					+ gregorianCalendar.get(GregorianCalendar.MINUTE)
					+ gregorianCalendar.get(GregorianCalendar.SECOND)
					+ ".csv";
			
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
			
//			String bucketName = "phonehouse-logistica";
//			
//			S3Client client = S3Client.builder().build();
//			
//			PutObjectRequest request = 
//				PutObjectRequest.builder()
//					.bucket(bucketName)
//					.key(fileName)
//					.build();
//			
//			client.putObject(
//				request, 
//				RequestBody.fromFile(new File(filePath))
//			);
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(ConfigurationPH.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(ConfigurationPH.getInstance().getProperty("estado.LLAMAR"))
				);
			
			Collection<ACMInterfaceContrato> subconjunto = this.listSubconjunto(metadataConsulta);
			
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfaceContrato acmInterfaceContrato : subconjunto) {
				mids.add(acmInterfaceContrato.getMid());
			}
			
			Map<Long, Integer> map = iContratoBean.preprocesarConjunto(mids, empresa.getId());
			
			Query selectContratoExisteEmpresa = entityManagerELARED.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE mid = :mid"
				+ " AND empresa_id = :empresaId"
				+ " AND estado_id = :estadoLlamarId"
			);
			selectContratoExisteEmpresa.setParameter("empresaId", empresa.getId());
			selectContratoExisteEmpresa.setParameter("estadoLlamarId", estado.getId());
			
			Query insertContrato = entityManagerELARED.createNativeQuery(
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
			
			insertContrato.setParameter(1, empresa.getId());
			insertContrato.setParameter(2, estado.getId());
			insertContrato.setParameter(3, rolSupervisorCallCenter.getId());
			
			insertContrato.setParameter(4, currentDate);
			insertContrato.setParameter(5, currentDate);
			insertContrato.setParameter(6, Long.valueOf(1));
			insertContrato.setParameter(7, Long.valueOf(1));
			insertContrato.setParameter(8, Long.valueOf(1));
			
			Query updateContrato = entityManagerELARED.createNativeQuery(
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
			
			updateContrato.setParameter(1, currentDate);
			updateContrato.setParameter(2, Long.valueOf(1));
			updateContrato.setParameter(3, Long.valueOf(1));
			
			Query insertContratoRoutingHistory = entityManagerELARED.createNativeQuery(
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
			
			insertContratoRoutingHistory.setParameter(1, currentDate);
			
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
						insertContrato.setParameter(9, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null);
						insertContrato.setParameter(10, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null);
						insertContrato.setParameter(11, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null);
						insertContrato.setParameter(12, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null);
						insertContrato.setParameter(13, acmInterfaceContrato.getDocumentoTipo());
						insertContrato.setParameter(14, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null);
						insertContrato.setParameter(15, acmInterfaceContrato.getFechaFinContrato());
						insertContrato.setParameter(16, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null);
						insertContrato.setParameter(17, acmInterfaceContrato.getMid());
						insertContrato.setParameter(18, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null);
						insertContrato.setParameter(19, acmInterfaceContrato.getNumeroCliente());
						insertContrato.setParameter(20, acmInterfaceContrato.getNumeroContrato().toString());
						insertContrato.setParameter(21, observaciones != "" ? observaciones : null);
						insertContrato.setParameter(22, acmInterfaceContrato.getTipoContratoCodigo() != "" ? acmInterfaceContrato.getTipoContratoCodigo() : null);
						insertContrato.setParameter(23, acmInterfaceContrato.getTipoContratoDescripcion() != "" ? acmInterfaceContrato.getTipoContratoDescripcion() : null);
						
						insertContrato.executeUpdate();
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						System.out.println("Se omite " + acmInterfaceContrato.getMid());
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						selectContratoExisteEmpresa.setParameter("mid", acmInterfaceContrato.getMid());
						
						Long contratoId = (Long) ((Integer) selectContratoExisteEmpresa.getResultList().get(0)).longValue();
						
						updateContrato.setParameter(4, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null);
						updateContrato.setParameter(5, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null);
						updateContrato.setParameter(6, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null);
						updateContrato.setParameter(7, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null);
						updateContrato.setParameter(8, acmInterfaceContrato.getDocumentoTipo());
						updateContrato.setParameter(9, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null);
						updateContrato.setParameter(10, acmInterfaceContrato.getFechaFinContrato());
						updateContrato.setParameter(11, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null);
						updateContrato.setParameter(12, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null);
						updateContrato.setParameter(13, acmInterfaceContrato.getNumeroCliente());
						updateContrato.setParameter(14, acmInterfaceContrato.getNumeroContrato().toString());
						updateContrato.setParameter(15, observaciones != "" ? observaciones : null);
						updateContrato.setParameter(16, acmInterfaceContrato.getTipoContratoCodigo() != "" ? acmInterfaceContrato.getTipoContratoCodigo() : null);
						updateContrato.setParameter(17, acmInterfaceContrato.getTipoContratoDescripcion() != "" ? acmInterfaceContrato.getTipoContratoDescripcion() : null);
						updateContrato.setParameter(18, contratoId);
						
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

	public void reprocesarPorMID(MetadataConsulta metadataConsulta, String observaciones) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFechaInicio(hoy);
			acmInterfaceProceso.setObservaciones(observaciones);
			
			acmInterfaceProceso.setUact(Long.valueOf(1));
			acmInterfaceProceso.setFact(hoy);
			acmInterfaceProceso.setTerm(Long.valueOf(1));
			
			entityManagerPH.persist(acmInterfaceProceso);
			
			ACMInterfaceEstado estado = 
				entityManagerPH.find(
					ACMInterfaceEstado.class, 
					Long.parseLong(ConfigurationPH.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
			
			Random random = new Random();
			
			for (ACMInterfaceContrato acmInterfaceContrato : this.listSubconjunto(metadataConsulta)) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setMid(acmInterfaceContrato.getMid());
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceMid.setEstado(estado);
				acmInterfaceMid.setRandom(Long.valueOf(random.nextInt()));
				
				acmInterfaceMid.setUact(Long.valueOf(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(Long.valueOf(1));
				
				entityManagerPH.merge(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reprocesarPorNumeroContrato(MetadataConsulta metadataConsulta, String observaciones) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFechaInicio(hoy);
			acmInterfaceProceso.setObservaciones(observaciones);
			
			acmInterfaceProceso.setUact(Long.valueOf(1));
			acmInterfaceProceso.setFact(hoy);
			acmInterfaceProceso.setTerm(Long.valueOf(1));
			
			entityManagerPH.persist(acmInterfaceProceso);
			
			ACMInterfaceEstado estado = 
				entityManagerPH.find(
					ACMInterfaceEstado.class, 
					Long.parseLong(ConfigurationPH.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
			
			for (ACMInterfaceContrato acmInterfaceContrato : this.listSubconjunto(metadataConsulta)) {
				ACMInterfaceNumeroContrato acmInterfaceNumeroContrato = new ACMInterfaceNumeroContrato();
				acmInterfaceNumeroContrato.setNumeroContrato(acmInterfaceContrato.getNumeroContrato());
				acmInterfaceNumeroContrato.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceNumeroContrato.setEstado(estado);
				
				acmInterfaceNumeroContrato.setUact(Long.valueOf(1));
				acmInterfaceNumeroContrato.setUcre(Long.valueOf(1));
				acmInterfaceNumeroContrato.setFact(hoy);
				acmInterfaceNumeroContrato.setFcre(hoy);
				acmInterfaceNumeroContrato.setTerm(Long.valueOf(1));
				
				entityManagerPH.merge(acmInterfaceNumeroContrato);
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