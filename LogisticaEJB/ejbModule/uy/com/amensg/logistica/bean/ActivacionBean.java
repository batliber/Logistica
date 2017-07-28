package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionLote;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoActivacion;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ActivacionBean implements IActivacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IEstadoActivacionBean iEstadoActivacionBean;
	
	@EJB
	private IActivacionLoteBean iActivacionLoteBean;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Activacion> criteriaQuery = criteriaBuilder.createQuery(Activacion.class);
			
			Root<Activacion> root = criteriaQuery.from(Activacion.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryEmpresasUsuario = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootEmpresasUsuario = subqueryEmpresasUsuario.from(UsuarioRolEmpresa.class);
			subrootEmpresasUsuario.alias("subrootEmpresasUsuario");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryEmpresasUsuario
						.select(subrootEmpresasUsuario)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootEmpresasUsuario.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "loggedUsuarioId")),
								criteriaBuilder.equal(subrootEmpresasUsuario.get("empresa"), root.get("empresa"))
							)
						)
				)
			);
			
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

			TypedQuery<Activacion> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("loggedUsuarioId", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Activacion> field = root;
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
									"p" + i,
									new Boolean(valor)
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
			for (Activacion activacion : query.getResultList()) {
				registrosMuestra.add(activacion);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// -------------------------------------------
			// Query para obtener la cantidad de registros
			// -------------------------------------------
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Activacion> rootCount = criteriaQueryCount.from(Activacion.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Subquery<UsuarioRolEmpresa> subqueryEmpresasUsuario = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootEmpresasUsuario = subqueryEmpresasUsuario.from(UsuarioRolEmpresa.class);
			subrootEmpresasUsuario.alias("subrootEmpresasUsuario");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryEmpresasUsuario
						.select(subrootEmpresasUsuario)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootEmpresasUsuario.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "loggedUsuarioId")),
								criteriaBuilder.equal(subrootEmpresasUsuario.get("empresa"), rootCount.get("empresa"))
							)
						)
				)
			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("loggedUsuarioId", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Activacion> field = rootCount;
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryCount.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryCount.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								queryCount.setParameter(
									"p" + i,
									new Boolean(valor)
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

	public String preprocesarArchivoEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<Long> mids = new LinkedList<Long>();
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				Long mid = new Long (fields[0].trim());
				
				mids.add(mid);
			}
			
			Map<Long, Integer> map = this.preprocesarConjunto(mids, empresaId);
			
			Long importar = new Long(0);
			Long sobreescribir = new Long(0);
			Long omitir = new Long(0);
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
				"Se importarán " + importar + " MIDs nuevos.|"
				+ "Se sobreescribirán " + sobreescribir + " MIDs.|"
				+ "Se omitirán " + omitir + " MIDs.";
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
	
	public Map<Long, Integer> preprocesarConjunto(Collection<Long> mids, Long empresaId) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			for (Long mid : mids) {
				result.put(mid, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoActivacionId, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			gregorianCalendar.set(GregorianCalendar.YEAR, 1900);
			gregorianCalendar.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
			gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
			gregorianCalendar.set(GregorianCalendar.HOUR, 0);
			gregorianCalendar.set(GregorianCalendar.MINUTE, 0);
			gregorianCalendar.set(GregorianCalendar.SECOND, 0);
			gregorianCalendar.set(GregorianCalendar.MILLISECOND, 0);
			
			Date fechaMin = gregorianCalendar.getTime();
			
			EstadoActivacion estado = 
				iEstadoActivacionBean.getById(new Long(Configuration.getInstance().getProperty("estadoActivacion.SINPROCESAR")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId);
			
			ActivacionLote activacionLote = new ActivacionLote();
			activacionLote.setEmpresa(empresa);
			activacionLote.setFechaImportacion(hoy);
			activacionLote.setNombreArchivo(fileName);
			
			activacionLote.setFact(hoy);
			activacionLote.setTerm(new Long(1));
			activacionLote.setUact(loggedUsuarioId);
			
			ActivacionLote activacionLoteManaged = iActivacionLoteBean.save(activacionLote);
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			SQLQuery insertActivacion = hibernateSession.createSQLQuery(
				"INSERT INTO activacion("
					+ " id,"
					+ " fecha_activacion,"
					+ " fecha_importacion,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					+ " empresa_id,"
					+ " estado_activacion_id,"
					+ " tipo_activacion_id,"
					+ " activacion_lote_id,"
					+ " mid,"
					+ " chip"
				+ " ) VALUES ("
					+ " nextval('hibernate_sequence'),"
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
			
			insertActivacion.setParameter(0, fechaMin, DateType.INSTANCE);
			insertActivacion.setParameter(1, hoy, DateType.INSTANCE);
			insertActivacion.setParameter(2, hoy, DateType.INSTANCE);
			insertActivacion.setParameter(3, new Long(1), LongType.INSTANCE);
			insertActivacion.setParameter(4, loggedUsuarioId, LongType.INSTANCE);
			insertActivacion.setParameter(5, empresa.getId(), LongType.INSTANCE);
			insertActivacion.setParameter(6, estado.getId(), LongType.INSTANCE);
			insertActivacion.setParameter(7, tipoActivacionId, LongType.INSTANCE);
			insertActivacion.setParameter(8, activacionLoteManaged.getId(), LongType.INSTANCE);
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				String[] fields = line.split(";", -1);
				
				if (fields.length < 2) {
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
						mid = new Long(fields[0].trim());
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo mid incorrecto -> " + fields[0].trim());
						ok = false;
					}
					
					String chip = 
						(fields[1] != null && !fields[1].equals("")) ? fields[1].trim() : null;
					
					if (!ok) {
						errors++;
					} else {
						insertActivacion.setParameter(9, mid, LongType.INSTANCE);
						insertActivacion.setParameter(10, chip, StringType.INSTANCE);

						insertActivacion.executeUpdate();

						successful++;
					}
				}
			}
			
			result = 
				"Líneas procesadas con éxito: " + successful + ".|"
				+ "Líneas con datos incorrectos: " + errors + ".";
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

	public Activacion getSiguienteMidParaActivar() {
		Activacion result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<Activacion> query = 
				entityManager.createQuery(
					"SELECT a"
					+ " FROM Activacion a"
					+ " WHERE a.estadoActivacion.id = :estadoSinProcesarId", 
					Activacion.class
				);
			query.setParameter("estadoSinProcesarId", new Long(Configuration.getInstance().getProperty("estadoActivacion.SINPROCESAR")));
			query.setMaxResults(1);
			
			List<Activacion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Activacion activacion = resultList.get(0);
				
				EstadoActivacion estadoActivacion = 
					iEstadoActivacionBean.getById(new Long(Configuration.getInstance().getProperty("estadoActivacion.PROCESANDO")));
				
				activacion.setEstadoActivacion(estadoActivacion);
				
				activacion.setFact(hoy);
				activacion.setTerm(new Long(1));
				activacion.setUact(new Long(1));
				
				activacion = entityManager.merge(activacion);
				
				result = activacion;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void actualizarDatosActivacion(Long mid, String chip, Long estadoActivacionId) {
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			TypedQuery<Activacion> query = 
				entityManager.createQuery(
					"SELECT a"
					+ " FROM Activacion a"
					+ " WHERE a.mid = :mid"
					+ " AND a.chip = :chip", 
					Activacion.class
				);
			query.setParameter("mid", mid);
			query.setParameter("chip", chip);
			query.setMaxResults(1);
			
			List<Activacion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Activacion activacion = resultList.get(0);
				
				EstadoActivacion estadoActivacion = 
					iEstadoActivacionBean.getById(estadoActivacionId);
				
				if (estadoActivacionId.equals(new Long(Configuration.getInstance().getProperty("estadoActivacion.OK")))) {
					activacion.setFechaActivacion(hoy);
					
					gregorianCalendar.add(GregorianCalendar.MONTH, 6);
					
					activacion.setFechaVencimiento(gregorianCalendar.getTime());
				}
				
				activacion.setEstadoActivacion(estadoActivacion);
				
				activacion.setFact(hoy);
				activacion.setTerm(new Long(1));
				activacion.setUact(new Long(1));
				
				activacion = entityManager.merge(activacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Activacion getById(Long id) {
		Activacion result = null;
		
		try {
			result = entityManager.find(Activacion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Activacion getLastByChip(String chip) {
		Activacion result = null;
		
		try {
			TypedQuery<Activacion> query =
				entityManager.createQuery(
					"SELECT a"
					+ " FROM Activacion a"
					+ " WHERE a.chip = :chip"
					+ " ORDER BY id DESC", 
					Activacion.class
				);
			query.setParameter("chip", chip);
			
			List<Activacion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void update(Activacion activacion) {
		try {
			Activacion activacionManaged = entityManager.find(Activacion.class, activacion.getId());
			
			activacionManaged.setChip(activacion.getChip());
			activacionManaged.setFechaActivacion(activacion.getFechaActivacion());
			activacionManaged.setFechaImportacion(activacion.getFechaImportacion());
			activacionManaged.setFechaVencimiento(activacion.getFechaVencimiento());
			activacionManaged.setMid(activacion.getMid());
			activacionManaged.setActivacionLote(activacion.getActivacionLote());
			activacionManaged.setEmpresa(activacion.getEmpresa());
			activacionManaged.setEstadoActivacion(activacion.getEstadoActivacion());
			activacionManaged.setTipoActivacion(activacion.getTipoActivacion());
			
			activacionManaged.setFact(activacion.getFact());
			activacionManaged.setTerm(activacion.getTerm());
			activacionManaged.setUact(activacion.getUact());
			
			entityManager.merge(activacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			printWriter.println(
				"MID"
				+ ";Chip"
				+ ";Empresa"
				+ ";Tipo de activación"
				+ ";Lote"
				+ ";Estado de activación"
				+ ";Fecha de importación"
				+ ";Fecha de activación"
				+ ";Fecha de vencimiento"
				+ ";Sub-lote"
				+ ";Distribuidor"
				+ ";Fecha asignación distribuidor"
				+ ";Punto de venta"
				+ ";Fecha asignación punto venta"
			);
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Activacion activacion = (Activacion) object;
				
				String line = 
					activacion.getMid()
					+ ";=\"" + activacion.getChip()
					+ "\";" + (activacion.getEmpresa() != null ?
						activacion.getEmpresa().getNombre()
						: "")
					+ ";" + (activacion.getTipoActivacion() != null ?
						activacion.getTipoActivacion().getDescripcion()
						: "")
					+ ";" + (activacion.getActivacionLote() != null ?
						activacion.getActivacionLote().getNumero()
						: "")
					+ ";" + (activacion.getEstadoActivacion() != null ?
						activacion.getEstadoActivacion().getNombre()
						: "")
					+ ";" + (activacion.getFechaImportacion() != null ?
						format.format(activacion.getFechaImportacion())
						: "")
					+ ";" + (activacion.getFechaActivacion() != null ?
						format.format(activacion.getFechaActivacion())
						: "")
					+ ";" + (activacion.getFechaVencimiento() != null ?
						format.format(activacion.getFechaVencimiento())
						: "")
					+ ";" + (activacion.getActivacionSublote() != null ? 
						activacion.getActivacionSublote().getNumero() : "")
					+ ";" + (activacion.getActivacionSublote() != null && activacion.getActivacionSublote().getDistribuidor() != null ?
						activacion.getActivacionSublote().getDistribuidor().getNombre()
						: "")
					+ ";" + (activacion.getActivacionSublote() != null && activacion.getActivacionSublote().getFechaAsignacionDistribuidor() != null ?
						format.format(activacion.getActivacionSublote().getFechaAsignacionDistribuidor())
						: "")
					+ ";" + (activacion.getActivacionSublote() != null && activacion.getActivacionSublote().getPuntoVenta() != null ?
						activacion.getActivacionSublote().getPuntoVenta().getNombre()
						: "")
					+ ";" + (activacion.getActivacionSublote() != null && activacion.getActivacionSublote().getFechaAsignacionPuntoVenta() != null ?
						format.format(activacion.getActivacionSublote().getFechaAsignacionPuntoVenta())
						: "");
				
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}