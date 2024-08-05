package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceContratoBean implements IACMInterfaceContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	private CriteriaQuery<ACMInterfaceContrato> criteriaQuery;
	
	public Collection<ACMInterfaceContrato> list() {
		Collection<ACMInterfaceContrato> result = new LinkedList<ACMInterfaceContrato>();
		
		try {
			TypedQuery<ACMInterfaceContrato> query = 
				entityManager.createQuery(
					"SELECT c FROM ACMInterfaceContrato c",
					ACMInterfaceContrato.class);
			
			for (ACMInterfaceContrato acmInterfaceContrato : query.getResultList()) {
				result.add(acmInterfaceContrato);
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
				} else {
					i++;
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
				} else {
					i++;
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
			
			Collection<ACMInterfaceContrato> subconjunto = this.listSubconjunto(metadataConsulta);
			
			Collection<Long> mids = new LinkedList<Long>();
			for (ACMInterfaceContrato acmInterfaceContrato : subconjunto) {
				mids.add(acmInterfaceContrato.getMid());
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
						insertContrato.setParameter(9, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null);
						insertContrato.setParameter(10, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null);
						
						// 04/11/2018 - No se importan los datos personales.
//						insertContrato.setParameter(11, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null);
						insertContrato.setParameter(11, null);
//						insertContrato.setParameter(12, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null);
						insertContrato.setParameter(12, null);
						insertContrato.setParameter(13, acmInterfaceContrato.getDocumentoTipo());
						insertContrato.setParameter(14, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null);
						insertContrato.setParameter(15, acmInterfaceContrato.getFechaFinContrato());
//						insertContrato.setParameter(16, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null);
						insertContrato.setParameter(16, null);
						insertContrato.setParameter(17, acmInterfaceContrato.getMid());
//						insertContrato.setParameter(18, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null);
						insertContrato.setParameter(18, null);
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
						
						Long contratoId = (Long) ((Integer)selectContratoExisteEmpresa.getResultList().get(0)).longValue();
						
						// 04/11/2018 - No se importan los datos personales.
						updateContrato.setParameter(4, acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null);
						updateContrato.setParameter(5, acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null);
//						updateContrato.setParameter(6, acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null);
						updateContrato.setParameter(6, null);
//						updateContrato.setParameter(7, acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null);
						updateContrato.setParameter(7, null);
						updateContrato.setParameter(8, acmInterfaceContrato.getDocumentoTipo());
						updateContrato.setParameter(9, acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null);
						updateContrato.setParameter(10, acmInterfaceContrato.getFechaFinContrato());
//						updateContrato.setParameter(11, acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null);
						updateContrato.setParameter(11, null);
//						updateContrato.setParameter(12, acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null);
						updateContrato.setParameter(12, null);
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
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
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
			
			TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
			
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
			+ ";" + (acmInterfaceContrato.getFact() != null ?
				format.format(acmInterfaceContrato.getFact())
				: "")
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

	/**
	 * Procesa un conjunto de datos a importar.
	 * 
	 * @param mids Colección de mids a procesar.
	 * @return Map indicando para cada mid si se importará, sobreescribirá u omitirá.
	 */
	public Map<Long, Integer> preprocesarConjunto(Collection<Long> mids) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT c.mid"
					+ " FROM ACMInterfaceContrato c"
					+ " WHERE c.mid IN :mids",
					Long.class
				);
			
			long chunkSize = 10000;
			long totalSize = 0;
			Collection<Long> chunk = new LinkedList<Long>();
			for (Long mid : mids) {
				totalSize++;
				
				chunk.add(mid);
				
				if (chunk.size() == chunkSize || totalSize == mids.size()) {
					query.setParameter("mids", chunk);
					
					for (Long nro : query.getResultList()) {
						result.put(nro, Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR);
					}
					
					for (Long midFromChunk : chunk) {
						if (!result.containsKey(midFromChunk)) {
							result.put(midFromChunk, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
						}
					}
					
					chunk.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Procesa el archivo .csv de nombre fileName para los datos de ACM.
	 * 
	 * @param fileName El nombre del archivo a Importar.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String que informa cuántos registros se importarán, cuántos se sobreescribirán y cuántas se omitirán. 
	 */
	public String preprocesarArchivo(String fileName, Long loggedUsuarioId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<Long> mids = new LinkedList<Long>();
			
			String line = null;
			boolean first = true;
			while ((line = bufferedReader.readLine()) != null) {
				if (first) {
					String[] fields = line.split(";");
					
					Long mid = Long.parseLong(fields[0].trim());
					
					mids.add(mid);
				} else {
					first = true;
				}
			}
			
			Map<Long, Integer> map = this.preprocesarConjunto(mids);
			
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
				"Se importarán " + importar + " registros nuevos.|"
				+ "Se sobreescribirán " + sobreescribir + " registros.|"
				+ "Se omitirán " + omitir + " registros.";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

	/**
	 * Importa el archivo .csv de nombre fileName.
	 *  
	 * @param fileName El nombre del archivo a Importar.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String con el resultado de la operación.
	 */
	public String procesarArchivo(String fileName, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.ACM"))
				);
			
			EstadoProcesoImportacion estadoProcesoImportacionInicio = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.Inicio"))
				);
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoOK = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoOK"))
				);		
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoConErrores = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(
						Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores")
					)
				);
			
			Usuario usuario = iUsuarioBean.getById(loggedUsuarioId, false);
			
			ProcesoImportacion procesoImportacion = new ProcesoImportacion();
			procesoImportacion.setEstadoProcesoImportacion(estadoProcesoImportacionInicio);
			procesoImportacion.setFechaInicio(hoy);
			procesoImportacion.setNombreArchivo(fileName);
			procesoImportacion.setTipoProcesoImportacion(tipoProcesoImportacion);
			procesoImportacion.setUsuario(usuario);
			
			procesoImportacion.setFcre(hoy);
			procesoImportacion.setFact(hoy);
			procesoImportacion.setTerm(Long.valueOf(1));
			procesoImportacion.setUact(loggedUsuarioId);
			procesoImportacion.setUcre(loggedUsuarioId);
			
			ProcesoImportacion procesoImportacionManaged = iProcesoImportacionBean.save(procesoImportacion);
			
			Query deleteACMInterfaceContrato = entityManager.createNativeQuery(
				"DELETE FROM acm_interface_contrato WHERE mid = ?"
			);
			
			Query insertACMInterfaceContrato = entityManager.createNativeQuery(
				"INSERT INTO acm_interface_contrato ("
					+ " mid,"
					+ " fecha_fin_contrato,"
					+ " tipo_contrato_codigo,"
					+ " tipo_contrato_descripcion,"
					+ " documento_tipo,"
					+ " documento,"
					+ " nombre,"
					+ " direccion,"
					+ " codigo_postal,"
					+ " estado_contrato,"
					+ " equipo,"
					+ " agente,"
					+ " fecha_exportacion,"
					+ " fact,"
					+ " localidad,"
					+ " numero_contrato"
				+ " )"
				+ " VALUES ("
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
				+ ")"
			);
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				if (lineNumber > 0) {
					String[] fields = line.split(";", -1);
					
					if (fields.length < 1) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Cantidad de columnas (" + fields.length + ") insuficientes."
						);
						errors++;
					} else {
						boolean ok = true;
						
						Long mid = null;
						try {
							mid = Long.parseLong(fields[0].trim());
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo mid incorrecto -> " + fields[0].trim());
							ok = false;
						}
						
						Date fechaFinContrato = null;
						String fecha = fields[1].trim();
						try {
							if (fecha != null && !fecha.equals("")) {
								fechaFinContrato = simpleDateFormat.parse(fecha);
							}
						} catch (ParseException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fechaFinContrato incorrecto -> " + fecha);
							ok = false;
						}
						
						String tipoContratoCodigo = 
							(fields[2] != null && !fields[2].equals("")) ? fields[2].trim() : null;
						
						String tipoContratoDescripcion = 
							(fields[3] != null && !fields[3].equals("")) ? fields[3].trim() : null;
						
						Long documentoTipo = null;
						String numero = fields[4].trim();
						try {
							if (numero != null && !numero.equals("")) {
								documentoTipo = Long.parseLong(numero);
							}
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo documentoTipo incorrecto -> " + numero);
							ok = false;
						}
						
						String documento = 
							(fields[5] != null && !fields[5].equals("")) ? fields[5].trim() : null;
						
						String nombre = 
							(fields[6] != null && !fields[6].equals("")) ? fields[6].trim() : null;
						
						String direccion = 
							(fields[7] != null && !fields[7].equals("")) ? fields[7].trim() : null;
						
						String codigoPostal = 
							(fields[8] != null && !fields[8].equals("")) ? fields[8].trim() : null;
						
						String estadoContrato = 
							(fields[9] != null && !fields[9].equals("")) ? fields[9].trim() : null;
						
						String equipo = 
							(fields[10] != null && !fields[10].equals("")) ? fields[10].trim() : null;
						
						String agente = 
							(fields[11] != null && !fields[11].equals("")) ? fields[11].trim() : null;
						
						Date fechaExportacion = null;
						fecha = fields[12].trim();
						try {
							if (fecha != null && !fecha.equals("")) {
								fechaExportacion = simpleDateFormat.parse(fecha);
							}
						} catch (ParseException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fechaExportacion incorrecto -> " + fecha);
							ok = false;
						}
						
						Date fact = null;
						fecha = fields[13].trim();
						try {
							if (fecha != null && !fecha.equals("")) {
								fact = simpleDateFormat.parse(fecha);
							}
						} catch (ParseException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fact incorrecto -> " + fecha);
							ok = false;
						}
						
						String localidad = 
							(fields[14] != null && !fields[14].equals("")) ? fields[14].trim() : null;
						
						String numeroContrato = 
							(fields[15] != null && !fields[15].equals("")) ? fields[15].trim() : null;
						
						if (!ok) {
							errors++;
						} else {
							// Borro la información que haya para ese mid.
							deleteACMInterfaceContrato.setParameter(1, mid);
							
							deleteACMInterfaceContrato.executeUpdate();
							
							insertACMInterfaceContrato.setParameter(1, mid);
							insertACMInterfaceContrato.setParameter(2, fechaFinContrato);
							insertACMInterfaceContrato.setParameter(3, tipoContratoCodigo);
							insertACMInterfaceContrato.setParameter(4, tipoContratoDescripcion);
							insertACMInterfaceContrato.setParameter(5, documentoTipo);
							insertACMInterfaceContrato.setParameter(6, documento);
							insertACMInterfaceContrato.setParameter(7, nombre);
							insertACMInterfaceContrato.setParameter(8, direccion);
							insertACMInterfaceContrato.setParameter(9, codigoPostal);
							insertACMInterfaceContrato.setParameter(10, estadoContrato);
							insertACMInterfaceContrato.setParameter(11, equipo);
							insertACMInterfaceContrato.setParameter(12, agente);
							insertACMInterfaceContrato.setParameter(13, fechaExportacion);
							insertACMInterfaceContrato.setParameter(14, fact);
							insertACMInterfaceContrato.setParameter(15, localidad);
							insertACMInterfaceContrato.setParameter(16, numeroContrato);
							
							insertACMInterfaceContrato.executeUpdate();
							
							successful++;
						}
					}
				}
			}
			
			result = 
				"Líneas procesadas con éxito: " + successful + ".|"
				+ "Líneas con datos incorrectos: " + errors + ".";
			
			if (errors > 0) {
				procesoImportacionManaged.setEstadoProcesoImportacion(estadoProcesoImportacionFinalizadoConErrores);
			} else {
				procesoImportacionManaged.setEstadoProcesoImportacion(estadoProcesoImportacionFinalizadoOK);
			}
			
			hoy = GregorianCalendar.getInstance().getTime();
			
			procesoImportacionManaged.setFact(hoy);
			procesoImportacionManaged.setFechaFin(hoy);
			procesoImportacionManaged.setObservaciones(result);
			
			iProcesoImportacionBean.update(procesoImportacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}