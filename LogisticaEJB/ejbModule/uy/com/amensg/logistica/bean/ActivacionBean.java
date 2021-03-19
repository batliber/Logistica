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

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionLote;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoActivacion;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

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
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			Collection<String> empresas = new LinkedList<String>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!empresas.contains(usuarioRolEmpresa.getEmpresa().getId().toString())) {
					empresas.add(usuarioRolEmpresa.getEmpresa().getId().toString());
				}
			}
			
			MetadataCondicion metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("empresa.id");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO);
			metadataCondicion.setValores(empresas);
			
			metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
			
			return new QueryBuilder<Activacion>().list(entityManager, metadataConsulta, new Activacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			Collection<String> empresas = new LinkedList<String>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!empresas.contains(usuarioRolEmpresa.getEmpresa().getId().toString())) {
					empresas.add(usuarioRolEmpresa.getEmpresa().getId().toString());
				}
			}
			
			MetadataCondicion metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("empresa.id");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO);
			metadataCondicion.setValores(empresas);
			
			metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
			
			result = new QueryBuilder<Activacion>().count(entityManager, metadataConsulta, new Activacion());
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
			
			boolean errorFatal = false;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				try {
					mids.add(Long.parseLong(fields[0].trim()));
				} catch (Exception e) {
					errorFatal = true;
					
					e.printStackTrace();
					break;
				}
			}
			
			if (!errorFatal) {
				Map<Long, Integer> map = this.preprocesarConjunto(mids, empresaId);
				
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
					"Se importarán " + importar + " MIDs nuevos.|"
					+ "Se sobreescribirán " + sobreescribir + " MIDs.|"
					+ "Se omitirán " + omitir + " MIDs.";
			} else {
				result = "Formato incompatible. Operación abortada";
			}
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
				if (mid > 0) {
					result.put(mid, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
				} else {
					result.put(mid, Constants.__COMPROBACION_IMPORTACION_OMITIR);
				}
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
				iEstadoActivacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoActivacion.SINPROCESAR")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId, false);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.Activaciones")));
			
			EstadoProcesoImportacion estadoProcesoImportacionInicio = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.Inicio")));
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoOK = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoOK")));		
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoConErrores = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores")));
			
			Usuario usuario =
				iUsuarioBean.getById(loggedUsuarioId, false);
			
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
			
			ActivacionLote activacionLote = new ActivacionLote();
			activacionLote.setEmpresa(empresa);
			activacionLote.setFechaImportacion(hoy);
			activacionLote.setNombreArchivo(fileName);
			
			activacionLote.setFcre(hoy);
			activacionLote.setFact(hoy);
			activacionLote.setTerm(Long.valueOf(1));
			activacionLote.setUact(loggedUsuarioId);
			activacionLote.setUcre(loggedUsuarioId);
			
			ActivacionLote activacionLoteManaged = iActivacionLoteBean.save(activacionLote);
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<?> insertActivacion = hibernateSession.createNativeQuery(
				"INSERT INTO activacion("
					+ " id,"
					+ " fecha_activacion,"
					+ " fecha_importacion,"
					+ " fcre,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					+ " ucre,"
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
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ " )"
			);
			
			insertActivacion.setParameter(1, fechaMin, DateType.INSTANCE);
			insertActivacion.setParameter(2, hoy, DateType.INSTANCE);
			insertActivacion.setParameter(3, hoy, DateType.INSTANCE);
			insertActivacion.setParameter(4, hoy, DateType.INSTANCE);
			insertActivacion.setParameter(5, Long.valueOf(1), LongType.INSTANCE);
			insertActivacion.setParameter(6, loggedUsuarioId, LongType.INSTANCE);
			insertActivacion.setParameter(7, loggedUsuarioId, LongType.INSTANCE);
			insertActivacion.setParameter(8, empresa.getId(), LongType.INSTANCE);
			insertActivacion.setParameter(9, estado.getId(), LongType.INSTANCE);
			insertActivacion.setParameter(10, tipoActivacionId, LongType.INSTANCE);
			insertActivacion.setParameter(11, activacionLoteManaged.getId(), LongType.INSTANCE);
			
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
						mid = Long.parseLong(fields[0].trim());
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
						insertActivacion.setParameter(12, mid, LongType.INSTANCE);
						insertActivacion.setParameter(13, chip, StringType.INSTANCE);

						insertActivacion.executeUpdate();

						successful++;
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
			query.setParameter("estadoSinProcesarId", Long.parseLong(Configuration.getInstance().getProperty("estadoActivacion.SINPROCESAR")));
			query.setMaxResults(1);
			
			List<Activacion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Activacion activacion = resultList.get(0);
				
				EstadoActivacion estadoActivacion = 
					iEstadoActivacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoActivacion.PROCESANDO")));
				
				activacion.setEstadoActivacion(estadoActivacion);
				
				activacion.setFact(hoy);
				activacion.setTerm(Long.valueOf(1));
				activacion.setUact(Long.valueOf(1));
				
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
				
				if (estadoActivacionId.equals(Long.parseLong(Configuration.getInstance().getProperty("estadoActivacion.OK")))) {
					activacion.setFechaActivacion(hoy);
					
					gregorianCalendar.add(GregorianCalendar.MONTH, 6);
					
					activacion.setFechaVencimiento(gregorianCalendar.getTime());
				}
				
				activacion.setEstadoActivacion(estadoActivacion);
				
				activacion.setFact(hoy);
				activacion.setTerm(Long.valueOf(1));
				activacion.setUact(Long.valueOf(1));
				
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
	
	public Activacion getLastByEmpresaIdMid(Long empresaId, Long mid) {
		Activacion result = null;
		
		try {
			TypedQuery<Activacion> query =
				entityManager.createQuery(
					"SELECT a"
					+ " FROM Activacion a"
					+ " WHERE a.empresa.id = :empresaId"
					+ " AND a.mid = :mid"
					+ " ORDER BY id DESC", 
					Activacion.class
				);
			query.setParameter("empresaId", empresaId);
			query.setParameter("mid", mid);
			
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
			activacionManaged.setLiquidacion(activacion.getLiquidacion());
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
//				+ ";Punto de venta"
//				+ ";Fecha asignación punto venta"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Activacion activacion = (Activacion) object;
				
				String line = 
					activacion.getMid()
					+ ";'" + activacion.getChip()
					+ ";" + (activacion.getEmpresa() != null ?
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
						: "");
//					+ ";" + (activacion.getActivacionSublote() != null && activacion.getActivacionSublote().getPuntoVenta() != null ?
//						activacion.getActivacionSublote().getPuntoVenta().getNombre()
//						: "")
//					+ ";" + (activacion.getActivacionSublote() != null && activacion.getActivacionSublote().getFechaAsignacionPuntoVenta() != null ?
//						format.format(activacion.getActivacionSublote().getFechaAsignacionPuntoVenta())
//						: "");
				
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Exporta los datos que cumplen con los criterios especificados a un archivo .csv 
	 * de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema
	 * para el rol Supervisor de Distribución de Chips.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcelSupervisorDistribucionChips(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
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
				+ ";Empresa"
				+ ";Chip"
				+ ";Fecha de activación"
				+ ";Fecha de importación"
				+ ";Estado de activación"
				+ ";Tipo de activación"
				+ ";Lote"
				+ ";Sub-lote"
				+ ";Distribuidor"
				+ ";Fecha asignación distribuidor"
				+ ";Punto de venta"
				+ ";Fecha asignación punto venta"
				+ ";Fecha de liquidación"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Activacion activacion = (Activacion) object;
				
				String line = 
					activacion.getMid()
					+ "\";" + (activacion.getEmpresa() != null ?
						activacion.getEmpresa().getNombre()
						: "")
					+ ";=\"" + activacion.getChip()
					+ ";" + (activacion.getFechaActivacion() != null ?
						format.format(activacion.getFechaActivacion())
						: "")
					+ ";" + (activacion.getFechaImportacion() != null ?
						format.format(activacion.getFechaImportacion())
						: "")
					+ ";" + (activacion.getEstadoActivacion() != null ?
						activacion.getEstadoActivacion().getNombre()
						: "")
					+ ";" + (activacion.getTipoActivacion() != null ?
						activacion.getTipoActivacion().getDescripcion()
						: "")
					+ ";" + (activacion.getActivacionLote() != null ?
						activacion.getActivacionLote().getNumero()
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
						: "")
					+ ";" + (activacion.getLiquidacion() != null ?
						format.format(activacion.getLiquidacion().getFechaLiquidacion())
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
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados a un archivo .csv 
	 * de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema
	 * para el rol Encargado de Activaciones.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcelEncargadoActivaciones(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
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
				+ ";Empresa"
				+ ";Chip"
				+ ";Fecha de activación"
				+ ";Fecha de importación"
				+ ";Estado de activación"
				+ ";Tipo de activación"
				+ ";Lote"
				+ ";Sub-lote"
				+ ";Distribuidor"
				+ ";Fecha asignación distribuidor"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Activacion activacion = (Activacion) object;
				
				String line = 
					activacion.getMid()
					+ "\";" + (activacion.getEmpresa() != null ?
						activacion.getEmpresa().getNombre()
						: "")
					+ ";=\"" + activacion.getChip()
					+ ";" + (activacion.getFechaActivacion() != null ?
						format.format(activacion.getFechaActivacion())
						: "")
					+ ";" + (activacion.getFechaImportacion() != null ?
						format.format(activacion.getFechaImportacion())
						: "")
					+ ";" + (activacion.getEstadoActivacion() != null ?
						activacion.getEstadoActivacion().getNombre()
						: "")
					+ ";" + (activacion.getTipoActivacion() != null ?
						activacion.getTipoActivacion().getDescripcion()
						: "")
					+ ";" + (activacion.getActivacionLote() != null ?
						activacion.getActivacionLote().getNumero()
						: "")
					+ ";" + (activacion.getActivacionSublote() != null ? 
						activacion.getActivacionSublote().getNumero() : "")
					+ ";" + (activacion.getActivacionSublote() != null 
						&& activacion.getActivacionSublote().getDistribuidor() != null ?
						activacion.getActivacionSublote().getDistribuidor().getNombre()
						: "")
					+ ";" + (activacion.getActivacionSublote() != null 
						&& activacion.getActivacionSublote().getFechaAsignacionDistribuidor() != null ?
						format.format(activacion.getActivacionSublote().getFechaAsignacionDistribuidor())
						: "")
					+ ";" + (activacion.getActivacionSublote() != null 
						&& activacion.getActivacionSublote().getPuntoVenta() != null 
						&& activacion.getActivacionSublote().getPuntoVenta().getDepartamento() != null ?
						activacion.getActivacionSublote().getPuntoVenta().getDepartamento().getNombre()
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
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados a un archivo .csv 
	 * de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema
	 * para el rol Encargado de Activaciones sin Distribución.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcelEncargadoActivacionesSinDistribucion(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
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
				+ ";Empresa"
				+ ";Chip"
				+ ";Fecha de activación"
				+ ";Fecha de importación"
				+ ";Estado de activación"
				+ ";Tipo de activación"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Activacion activacion = (Activacion) object;
				
				String line = 
					activacion.getMid()
					+ "\";" + (activacion.getEmpresa() != null ?
						activacion.getEmpresa().getNombre()
						: "")
					+ ";=\"" + activacion.getChip()
					+ ";" + (activacion.getFechaActivacion() != null ?
						format.format(activacion.getFechaActivacion())
						: "")
					+ ";" + (activacion.getFechaImportacion() != null ?
						format.format(activacion.getFechaImportacion())
						: "")
					+ ";" + (activacion.getEstadoActivacion() != null ?
						activacion.getEstadoActivacion().getNombre()
						: "")
					+ ";" + (activacion.getTipoActivacion() != null ?
						activacion.getTipoActivacion().getDescripcion()
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