package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfacePrepagoBean implements IACMInterfacePrepagoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IContratoBean iContratoBean;
	
	@EJB
	private IEstadoBean iEstadoBean;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IEstadoRiesgoCrediticioBean iEstadoRiesgoCrediticioBean;
	
	@EJB
	private ITipoControlRiesgoCrediticioBean iTipoControlRiesgoCrediticioBean;
	
	private CriteriaQuery<ACMInterfacePrepago> criteriaQuery;
	
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

	/**
	 * Lista los ACMInterfacePrepago que cumplen los criterios encapsulados en metadataConsulta.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @return MetadataConsultaResultado con los resultados de la consulta.
	 */
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<ACMInterfacePrepago> criteriaQuery = criteriaBuilder.createQuery(ACMInterfacePrepago.class);
			
			Root<ACMInterfacePrepago> root = criteriaQuery.from(ACMInterfacePrepago.class);
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
			
			criteriaQuery
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<ACMInterfacePrepago> query = entityManager.createQuery(criteriaQuery);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ACMInterfacePrepago> field = root;
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
				}
			}
			
			// Acotar al tamaño de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfacePrepago acmInterfacePrepago : query.getResultList()) {
				registrosMuestra.add(acmInterfacePrepago);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Cuenta la cantidad de ACMInterfacePrepago que cumplen los criterios encapsulados en metadataConsulta.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @return Cantidad de registros que cumplen con los criterios.
	 */
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<ACMInterfacePrepago> rootCount = criteriaQueryCount.from(ACMInterfacePrepago.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("mid")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
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
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long usuarioId) {
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
			
			for (ACMInterfacePrepago acmInterfacePrepago : this.listSubconjunto(metadataConsulta)) {
				printWriter.println(this.buildCSVLine(acmInterfacePrepago, null));
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
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR"))
				);
			
			Collection<ACMInterfacePrepago> subconjunto = this.listSubconjunto(metadataConsulta);
			
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfacePrepago acmInterfacePrepago : subconjunto) {
				mids.add(acmInterfacePrepago.getMid());
			}
			Map<Long, Integer> map = iContratoBean.preprocesarConjunto(mids, empresa.getId());
			
			Query selectContratoExisteEmpresa = entityManager.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE mid = :mid"
				+ " AND empresa_id = :empresaId"
				+ " AND estado_id = :estadoLlamarId"
			);
			selectContratoExisteEmpresa.setParameter("empresaId", empresa.getId());
			selectContratoExisteEmpresa.setParameter("estadoLlamarId", estado.getId());
			
			Query insertContrato = entityManager.createNativeQuery(
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
			
			Query updateContrato = entityManager.createNativeQuery(
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
			
			Query insertContratoRoutingHistory = entityManager.createNativeQuery(
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
			
			DecimalFormat formatMonto = new DecimalFormat("0.00");
			
			Random random = new Random();
			
			for (ACMInterfacePrepago acmInterfacePrepago : subconjunto) {
				acmInterfacePrepago.setFechaExportacionAnterior(
					acmInterfacePrepago.getFechaExportacion()
				);
				acmInterfacePrepago.setFechaExportacion(currentDate);
				acmInterfacePrepago.setRandom(Long.valueOf(random.nextInt()));
				
				acmInterfacePrepago = entityManager.merge(acmInterfacePrepago);
				
				// Agregar línea al archivo.
				printWriter.println(this.buildCSVLine(acmInterfacePrepago, observaciones));
				
				switch (map.get(acmInterfacePrepago.getMid())) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						insertContrato.setParameter(9, null);
						insertContrato.setParameter(10, null);
						insertContrato.setParameter(11, null);
						
						// 04/11/2018 - No se importan los datos personales.
						if (acmInterfacePrepago.getAcmInterfacePersona() != null) {
//							insertContrato.setParameter(
//								12, 
//								acmInterfacePrepago.getAcmInterfacePersona().getDocumento(), 
//								StringType.INSTANCE
//							);
							insertContrato.setParameter(12, null);
						} else {
							insertContrato.setParameter(12, null);
						}
						
						insertContrato.setParameter(13, null);
						insertContrato.setParameter(14, null);
						insertContrato.setParameter(15, null);
						insertContrato.setParameter(16, null);
						insertContrato.setParameter(17, acmInterfacePrepago.getMid());
						
						if (acmInterfacePrepago.getAcmInterfacePersona() != null) {
//							insertContrato.setParameter(
//								18, 
//								acmInterfacePrepago.getAcmInterfacePersona().getNombre() 
//									+ " " + acmInterfacePrepago.getAcmInterfacePersona().getApellido(), 
//								StringType.INSTANCE
//							);
							insertContrato.setParameter(18, null);
						} else {
							insertContrato.setParameter(18, null);
						}
						insertContrato.setParameter(19, null);
						insertContrato.setParameter(20, null);
						insertContrato.setParameter(21, 
							"Monto promedio: " 
								+ (acmInterfacePrepago.getMontoPromedio() != null ? formatMonto.format(acmInterfacePrepago.getMontoPromedio()) : "0") 
								+ ".\n"
							+ observaciones
						);
						insertContrato.setParameter(22, null);
						insertContrato.setParameter(23, null);
						
						insertContrato.executeUpdate();
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						System.out.println("Se omite " + acmInterfacePrepago.getMid());
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						selectContratoExisteEmpresa.setParameter("mid", acmInterfacePrepago.getMid());
						
						Long contratoId = (Long) ((Integer) selectContratoExisteEmpresa.getResultList().get(0)).longValue();
						
						updateContrato.setParameter(4, null);
						updateContrato.setParameter(5, null);
						updateContrato.setParameter(6, null);
						
						// 04/11/2018 - No se importan los datos personales.
						if (acmInterfacePrepago.getAcmInterfacePersona() != null) {
//							updateContrato.setParameter(
//								7, 
//								acmInterfacePrepago.getAcmInterfacePersona().getDocumento() 
//							);
							updateContrato.setParameter(7, null);
						} else {
							updateContrato.setParameter(7, null);
						}
						
						updateContrato.setParameter(8, null);
						updateContrato.setParameter(9, null);
						updateContrato.setParameter(10, null);
						updateContrato.setParameter(11, null);
						
						if (acmInterfacePrepago.getAcmInterfacePersona() != null) {
//							updateContrato.setParameter(
//								11,
//								acmInterfacePrepago.getAcmInterfacePersona().getNombre() 
//									+ " " + acmInterfacePrepago.getAcmInterfacePersona().getApellido()
//							);
							updateContrato.setParameter(12, null);
						} else {
							updateContrato.setParameter(12, null);
						}
						
						updateContrato.setParameter(13, null);
						updateContrato.setParameter(14, null);
						updateContrato.setParameter(15, 
							"Monto promedio: " 
								+ (acmInterfacePrepago.getMontoPromedio() != null ? formatMonto.format(acmInterfacePrepago.getMontoPromedio()) : "0") 
								+ ".\n"
							+ observaciones
						);
						updateContrato.setParameter(16, null);
						updateContrato.setParameter(17, null);
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
			TypedQuery<ACMInterfacePrepago> query = entityManager.createQuery(
				"SELECT p FROM ACMInterfacePrepago p WHERE p.fechaExportacion IS NOT NULL ORDER BY p.fechaExportacion DESC",
				ACMInterfacePrepago.class
			);
			query.setMaxResults(1);
			
			Collection<ACMInterfacePrepago> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ACMInterfacePrepago acmInterfacePrepago = resultList.toArray(new ACMInterfacePrepago[]{})[0];
				
				if (acmInterfacePrepago.getFechaExportacion() != null) {
					TypedQuery<ACMInterfacePrepago> queryUltimaExportacion = entityManager.createQuery(
						"SELECT p FROM ACMInterfacePrepago p WHERE p.fechaExportacion = :fechaExportacion"
						, ACMInterfacePrepago.class
					);
					queryUltimaExportacion.setParameter("fechaExportacion", acmInterfacePrepago.getFechaExportacion());
					
					TypedQuery<ContratoRoutingHistory> queryContratoRoutingHistory = entityManager.createQuery(
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
							
						entityManager.merge(acmInterfacePrepagoUltimaExportacion);
					}
					
					for (ContratoRoutingHistory contratoRoutingHistory : contratoRoutingHistoriesUltimaFechaExportacion) {
						Contrato contrato = contratoRoutingHistory.getContrato();
						
						entityManager.remove(contratoRoutingHistory);
						entityManager.remove(contrato);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFechaInicio(hoy);
			acmInterfaceProceso.setObservaciones(observaciones);
			
			acmInterfaceProceso.setUact(Long.valueOf(1));
			acmInterfaceProceso.setFact(hoy);
			acmInterfaceProceso.setTerm(Long.valueOf(1));
			
			acmInterfaceProceso = iACMInterfaceProcesoBean.save(acmInterfaceProceso);
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
			
			Random random = new Random();
			
			for (ACMInterfacePrepago acmInterfacePrepago : this.listSubconjunto(metadataConsulta)) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setMid(acmInterfacePrepago.getMid());
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceMid.setEstado(estado);
				acmInterfaceMid.setRandom(Long.valueOf(random.nextInt()));
				
				acmInterfaceMid.setUact(Long.valueOf(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(Long.valueOf(1));
				
				entityManager.merge(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta) {
		try {
			TypedQuery<ACMInterfacePrepago> query = this.construirQuery(metadataConsulta);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formatMesAno = new SimpleDateFormat("MM/yyyy");
			DecimalFormat formatMonto = new DecimalFormat("0.00");
			
			ACMInterfaceEstado estado = 
				entityManager.find(ACMInterfaceEstado.class, Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra")));
			
			for (ACMInterfacePrepago acmInterfacePrepago : query.getResultList()) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setEstado(estado);
				acmInterfaceMid.setMid(acmInterfacePrepago.getMid());
				
				acmInterfaceMid.setUact(Long.valueOf(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(Long.valueOf(1));
				
				entityManager.merge(acmInterfaceMid); 
				
				ACMInterfaceListaNegra acmInterfaceListaNegra = new ACMInterfaceListaNegra();
				
				acmInterfaceListaNegra.setMid(acmInterfacePrepago.getMid());
				acmInterfaceListaNegra.setObservaciones(
					(acmInterfacePrepago.getMesAno() != null ?
						formatMesAno.format(acmInterfacePrepago.getMesAno())
						: "")
					+ (acmInterfacePrepago.getMontoMesActual() != null ?
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
					+ ";" + (acmInterfacePrepago.getFechaActivacionKit() != null ?
						format.format(acmInterfacePrepago.getFechaActivacionKit())
						: "")
				);
				
				acmInterfaceListaNegra.setTerm(Long.valueOf(1));
				acmInterfaceListaNegra.setFact(hoy);
				acmInterfaceListaNegra.setUact(Long.valueOf(1));
				
				entityManager.persist(acmInterfaceListaNegra);
				
				entityManager.remove(acmInterfacePrepago);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private TypedQuery<ACMInterfacePrepago> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
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
		
		TypedQuery<ACMInterfacePrepago> query = entityManager.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			for (String valor : metadataCondicion.getValores()) {
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
		Collection<ACMInterfacePrepago> result = new LinkedList<ACMInterfacePrepago>();
		
		Collection<MetadataOrdenacion> metadataOrdenaciones = new LinkedList<MetadataOrdenacion>();
		
		MetadataOrdenacion metadataOrdenacion = new MetadataOrdenacion();
		metadataOrdenacion.setAscendente(true);
		metadataOrdenacion.setCampo("random");
		
		metadataOrdenaciones.add(metadataOrdenacion);
		
		metadataConsulta.setMetadataOrdenaciones(metadataOrdenaciones);
		
		TypedQuery<ACMInterfacePrepago> query = this.construirQuery(metadataConsulta);
		
		if (metadataConsulta.getTamanoSubconjunto() != null) {
			query.setMaxResults(metadataConsulta.getTamanoSubconjunto().intValue());
		}
		
		result = query.getResultList();
		
		return result;
	}
	
	public void controlarRiesgoCrediticio(
		Empresa empresa,
		TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio,
		MetadataConsulta metadataConsulta
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticio =
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar"))
				);
			
			for (Object object : this.listSubconjunto(metadataConsulta)) {
				ACMInterfacePrepago acmInterfacePrepago = (ACMInterfacePrepago) object;
				
				ACMInterfacePersona acmInterfacePersona = acmInterfacePrepago.getAcmInterfacePersona();
				if (acmInterfacePersona != null) {
					RiesgoCrediticio riesgoCrediticio = new RiesgoCrediticio();
					riesgoCrediticio.setDocumento(acmInterfacePersona.getDocumento());
					riesgoCrediticio.setFechaImportacion(hoy);
					
					riesgoCrediticio.setEmpresa(empresa);
					riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticio);
					riesgoCrediticio.setTipoControlRiesgoCrediticio(tipoControlRiesgoCrediticio);
					
					riesgoCrediticio.setFact(hoy);
					riesgoCrediticio.setFcre(hoy);
					riesgoCrediticio.setTerm(Long.valueOf(1));
					riesgoCrediticio.setUact(Long.valueOf(1));
					riesgoCrediticio.setUcre(Long.valueOf(1));
					
					entityManager.persist(riesgoCrediticio);
					
					acmInterfacePersona.setRiesgoCrediticio(riesgoCrediticio);
					
					acmInterfacePersona.setFact(hoy);
					acmInterfacePersona.setTerm(Long.valueOf(1));
					acmInterfacePersona.setUact(riesgoCrediticio.getUact());
					
					entityManager.merge(acmInterfacePersona);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			+ ";" + (acmInterfacePrepago.getAcmInterfacePersona() != null ?
				acmInterfacePrepago.getAcmInterfacePersona().getDocumento()
				: "")
			+ ";" + (
				"Monto promedio: " 
				+ (acmInterfacePrepago.getMontoPromedio() != null ? formatMonto.format(acmInterfacePrepago.getMontoPromedio()) : "0") 
				+ ". " + observaciones
			);
		
		return result;
	}
}