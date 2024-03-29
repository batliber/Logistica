package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

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
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
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
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceContratoBean implements IACMInterfaceContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	@EJB
	private IACMInterfaceBean iACMInterfaceBean;
	
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
	
	private CriteriaQuery<ACMInterfaceContrato> criteriaQuery;
	
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
	
	/**
	 * Lista los ACMInterfaceContrato que cumplen los criterios encapsulados en metadataConsulta.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @return MetadataConsultaResultado con los resultados de la consulta.
	 */
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<ACMInterfaceContrato> criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceContrato.class);
			
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
			
			criteriaQuery
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(criteriaQuery);
			
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
				}
			}
			
			// Acotar al tamaño de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfaceContrato acmInterfaceContrato : query.getResultList()) {
				registrosMuestra.add(acmInterfaceContrato);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
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
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<ACMInterfaceContrato> rootCount = criteriaQueryCount.from(ACMInterfaceContrato.class);
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
			
			for (ACMInterfaceContrato acmInterfaceContrato : this.listSubconjunto(metadataConsulta)) {
				printWriter.println(this.buildCSVLine(acmInterfaceContrato, null));
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
	 * 	- Cantidad a asignar
	 *	- Cantidad a omitir
	 *	- Cantidad a reemplazar
	 * 
	 * @param filtro con la selección
	 * @param Empresa a asignar la selección
	 * @return String con las estadísticas de la asignación de la selección a la empresa.
	 */
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
			
			Collection<ACMInterfaceContrato> subconjunto = this.listSubconjunto(metadataConsulta);
			
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfaceContrato acmInterfaceContrato : subconjunto) {
				mids.add(acmInterfaceContrato.getMid());
			}
			
			Map<Long, Integer> map = iContratoBean.preprocesarConjunto(mids, empresa.getId());
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<Tuple> selectContratoExisteEmpresa = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE mid = :mid"
				+ " AND empresa_id = :empresaId"
				+ " AND estado_id = :estadoLlamarId", Tuple.class
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
			
			Random random = new Random();
			
			for (ACMInterfaceContrato acmInterfaceContrato : subconjunto) {
				acmInterfaceContrato.setFechaExportacionAnterior(
					acmInterfaceContrato.getFechaExportacion()
				);
				acmInterfaceContrato.setFechaExportacion(currentDate);
				acmInterfaceContrato.setRandom(Long.valueOf(random.nextInt()));
				
				acmInterfaceContrato = entityManager.merge(acmInterfaceContrato);
				
				// Agregar línea al archivo.
				printWriter.println(this.buildCSVLine(acmInterfaceContrato, observaciones));
				
				switch (map.get(acmInterfaceContrato.getMid())) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						insertContrato.setParameter(9, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null, StringType.INSTANCE);
						insertContrato.setParameter(10, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null, StringType.INSTANCE);
						
						// 04/11/2018 - No se importan los datos personales.
//						insertContrato.setParameter(11, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null, StringType.INSTANCE);
						insertContrato.setParameter(11, null, StringType.INSTANCE);
//						insertContrato.setParameter(12, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null, StringType.INSTANCE);
						insertContrato.setParameter(12, null, StringType.INSTANCE);
						insertContrato.setParameter(13, acmInterfaceContrato.getDocumentoTipo(), LongType.INSTANCE);
						insertContrato.setParameter(14, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null, StringType.INSTANCE);
						insertContrato.setParameter(15, acmInterfaceContrato.getFechaFinContrato(), DateType.INSTANCE);
//						insertContrato.setParameter(16, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null, StringType.INSTANCE);
						insertContrato.setParameter(16, null, StringType.INSTANCE);
						insertContrato.setParameter(17, acmInterfaceContrato.getMid(), LongType.INSTANCE);
//						insertContrato.setParameter(18, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null, StringType.INSTANCE);
						insertContrato.setParameter(18, null, StringType.INSTANCE);
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
						
						// 04/11/2018 - No se importan los datos personales.
						updateContrato.setParameter(4, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null, StringType.INSTANCE);
						updateContrato.setParameter(5, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null, StringType.INSTANCE);
//						updateContrato.setParameter(6, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null, StringType.INSTANCE);
						updateContrato.setParameter(6, null, StringType.INSTANCE);
//						updateContrato.setParameter(7, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null, StringType.INSTANCE);
						updateContrato.setParameter(7, null, StringType.INSTANCE);
						updateContrato.setParameter(8, acmInterfaceContrato.getDocumentoTipo(), LongType.INSTANCE);
						updateContrato.setParameter(9, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null, StringType.INSTANCE);
						updateContrato.setParameter(10, acmInterfaceContrato.getFechaFinContrato(), DateType.INSTANCE);
//						updateContrato.setParameter(11, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null, StringType.INSTANCE);
						updateContrato.setParameter(11, null, StringType.INSTANCE);
//						updateContrato.setParameter(12, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null, StringType.INSTANCE);
						updateContrato.setParameter(12, null, StringType.INSTANCE);
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
			TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(
				"SELECT c FROM ACMInterfaceContrato c WHERE c.fechaExportacion IS NOT NULL ORDER BY c.fechaExportacion DESC",
				ACMInterfaceContrato.class
			);
			query.setMaxResults(1);
			
			Collection<ACMInterfaceContrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ACMInterfaceContrato acmInterfaceContrato = resultList.toArray(new ACMInterfaceContrato[]{})[0];
				
				if (acmInterfaceContrato.getFechaExportacion() != null) {
					TypedQuery<ACMInterfaceContrato> queryUltimaExportacion = entityManager.createQuery(
						"SELECT c FROM ACMInterfaceContrato c WHERE c.fechaExportacion = :fechaExportacion"
						, ACMInterfaceContrato.class
					);
					queryUltimaExportacion.setParameter("fechaExportacion", acmInterfaceContrato.getFechaExportacion());
					
					TypedQuery<ContratoRoutingHistory> queryContratoRoutingHistory = entityManager.createQuery(
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
							
						entityManager.merge(acmInterfaceContratoUltimaExportacion);
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
	
	public void reprocesarPorMID(MetadataConsulta metadataConsulta, String observaciones) {
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
			
			for (ACMInterfaceContrato acmInterfaceContrato : this.listSubconjunto(metadataConsulta)) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setMid(acmInterfaceContrato.getMid());
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
	
	public void reprocesarPorNumeroContrato(MetadataConsulta metadataConsulta, String observaciones) {
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
				
				entityManager.merge(acmInterfaceNumeroContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta) {
		try {
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			ACMInterfaceEstado estado = 
				entityManager.find(ACMInterfaceEstado.class, Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra")));
			
			for (ACMInterfaceContrato acmInterfaceContrato : query.getResultList()) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setMid(acmInterfaceContrato.getMid());
				
				acmInterfaceMid.setEstado(estado);
				
				acmInterfaceMid.setUact(Long.valueOf(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(Long.valueOf(1));
				
				entityManager.merge(acmInterfaceMid);
				
				ACMInterfaceListaNegra acmInterfaceListaNegra = new ACMInterfaceListaNegra();
				
				acmInterfaceListaNegra.setMid(acmInterfaceContrato.getMid());
				acmInterfaceListaNegra.setObservaciones(
					(acmInterfaceContrato.getFechaFinContrato() != null ? 
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
					+ ";" + acmInterfaceContrato.getNumeroCliente()
					+ ";" + acmInterfaceContrato.getNumeroContrato()
				);
				
				acmInterfaceListaNegra.setTerm(Long.valueOf(1));
				acmInterfaceListaNegra.setFact(hoy);
				acmInterfaceListaNegra.setUact(Long.valueOf(1));
				
				entityManager.persist(acmInterfaceListaNegra);
				
				entityManager.remove(acmInterfaceContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			for (ACMInterfaceContrato acmInterfaceContrato : this.listSubconjunto(metadataConsulta)) {
				ACMInterfacePersona acmInterfacePersona = acmInterfaceContrato.getAcmInterfacePersona();
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
	
	public Collection<TipoContrato> listTipoContratos() {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			Query query = entityManager.createQuery(
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
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// Criteria query para los tipos de contrato
			CriteriaQuery<Object[]> criteriaQueryTiposContrato = criteriaBuilder.createQuery(Object[].class);
			
			Root<ACMInterfaceContrato> root = criteriaQueryTiposContrato.from(ACMInterfaceContrato.class);
			
			criteriaQueryTiposContrato.multiselect(
				root.get("tipoContratoDescripcion")
			).distinct(true);
			
			// Mismo where que la primera Query
			criteriaQueryTiposContrato.where(criteriaQuery.getRestriction());
			
			TypedQuery<Object[]> queryTiposContrato = entityManager.createQuery(criteriaQueryTiposContrato);
			
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
	
	private TypedQuery<ACMInterfaceContrato> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
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
		
		TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
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
		}
		
		return query;
	}

	private Collection<ACMInterfaceContrato> listSubconjunto(MetadataConsulta metadataConsulta) {
		Collection<ACMInterfaceContrato> result = new LinkedList<ACMInterfaceContrato>();
		
		Collection<MetadataOrdenacion> metadataOrdenaciones = new LinkedList<MetadataOrdenacion>();
		
		MetadataOrdenacion metadataOrdenacion = new MetadataOrdenacion();
		metadataOrdenacion.setAscendente(true);
		metadataOrdenacion.setCampo("random");
		
		metadataOrdenaciones.add(metadataOrdenacion);
		
		metadataConsulta.setMetadataOrdenaciones(metadataOrdenaciones);
		
		TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
		
		if (metadataConsulta.getTamanoSubconjunto() != null) {
			query.setMaxResults(metadataConsulta.getTamanoSubconjunto().intValue());
		}
		
		result = query.getResultList();
		
		return result;
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