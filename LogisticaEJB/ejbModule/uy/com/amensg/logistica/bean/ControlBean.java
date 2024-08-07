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

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoControl;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.ProcesoImportacionLinea;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ControlBean implements IControlBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IEstadoControlBean iEstadoControlBean;

	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	@EJB
	private IProcesoImportacionLineaBean iProcesoImportacionLineaBean;
	
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
			
			return new QueryBuilder<Control>().list(entityManager, metadataConsulta, new Control());
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
			
			result = new QueryBuilder<Control>().count(entityManager, metadataConsulta, new Control());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String preprocesarArchivoEmpresa(String fileName, Long empresaId, Long loggedUsuarioId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			EstadoProcesoImportacion estadoProcesoImportacionInicio = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.Inicio"))
				);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.Controles"))
				);
			
			Usuario usuario =
				iUsuarioBean.getById(loggedUsuarioId, false);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
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
			
			boolean errorFatal = false;
			String line = null;
			Long numeroLinea = Long.valueOf(1);
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				try {
					Long.parseLong (fields[0].trim());
					
					ProcesoImportacionLinea procesoImportacionLinea = new ProcesoImportacionLinea();
					procesoImportacionLinea.setClave(fields[0].trim());
					procesoImportacionLinea.setLinea(line);
					procesoImportacionLinea.setNumero(numeroLinea);
					procesoImportacionLinea.setProcesoImportacion(procesoImportacionManaged);
					
					procesoImportacionLinea.setFact(hoy);
					procesoImportacionLinea.setFcre(hoy);
					procesoImportacionLinea.setTerm(Long.valueOf(1));
					procesoImportacionLinea.setUact(loggedUsuarioId);
					procesoImportacionLinea.setUcre(loggedUsuarioId);
					
					entityManager.persist(procesoImportacionLinea);
					
					numeroLinea++;
				} catch (Exception e) {
					errorFatal = true;
					
					e.printStackTrace();
					break;
				}
			}
			
			if (!errorFatal) {
				Map<Long, Integer> map = this.preprocesarConjunto(procesoImportacionManaged, empresaId);
				
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

	public Map<Long, Integer> preprocesarConjunto(ProcesoImportacion procesoImportacion, Long empresaId) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			for (ProcesoImportacionLinea procesoImportacionLinea : 
				iProcesoImportacionLineaBean.listByProcesoImportacion(procesoImportacion)) {
				String clave = procesoImportacionLinea.getClave();
				
				try {
					Long mid = Long.parseLong(clave);
					
					if (mid > 0) {
						result.put(procesoImportacionLinea.getId(), Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
					} else {
						result.put(procesoImportacionLinea.getId(), Constants.__COMPROBACION_IMPORTACION_OMITIR);
					}
				} catch (Exception e) {
					result.put(procesoImportacionLinea.getId(), Constants.__COMPROBACION_IMPORTACION_OMITIR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlId, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
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
			
			EstadoControl estado = 
				iEstadoControlBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoControl.PENDIENTE")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId, false);
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoOK = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoOK")));		
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoConErrores = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores")));
			
			ProcesoImportacion procesoImportacionManaged = 
				iProcesoImportacionBean.getByNombreArchivo(fileName);
			
			procesoImportacionManaged.setUact(loggedUsuarioId);
			procesoImportacionManaged.setFact(hoy);
			
			Query selectActivacion = entityManager.createNativeQuery(
				"SELECT asl.distribuidor_id, asl.fecha_asignacion_distribuidor, asl.punto_venta_id, asl.fecha_asignacion_punto_venta"
				+ " FROM activacion a"
				+ " INNER JOIN activacion_sublote_activacion asla ON asla.activacion_id = a.id"
				+ " INNER JOIN activacion_sublote asl ON asl.id = asla.activacion_sublote_id"
				+ " WHERE a.empresa_id = ?"
				+ " AND a.mid = ?"
				+ " AND a.chip = ?"
			);
			selectActivacion.setParameter(1, empresa.getId());
			
			Query insertControl = entityManager.createNativeQuery(
				"INSERT INTO control("
					+ " id,"
					+ " fecha_control,"
					+ " mes_control,"
					+ " fecha_importacion,"
					+ " fecha_activacion,"
					+ " carga_inicial,"
					+ " monto_cargar,"
					+ " fcre,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					+ " ucre,"
					
					+ " empresa_id,"
					+ " estado_control_id,"
					+ " tipo_control_id,"
					+ " mid,"
					+ " chip,"
					+ " distribuidor_id,"
					+ " fecha_asignacion_distribuidor,"
					+ " punto_venta_id,"
					+ " fecha_asignacion_punto_venta"
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
			
			insertControl.setParameter(1, fechaMin);
			insertControl.setParameter(2, fechaMin);
			insertControl.setParameter(3, hoy);
			insertControl.setParameter(4, fechaMin);
			insertControl.setParameter(5, Long.valueOf(0));
			insertControl.setParameter(6, Long.valueOf(0));
			insertControl.setParameter(7, hoy);
			insertControl.setParameter(8, hoy);
			insertControl.setParameter(9, Long.valueOf(1));
			insertControl.setParameter(10, loggedUsuarioId);
			insertControl.setParameter(11, loggedUsuarioId);
			insertControl.setParameter(12, empresa.getId());
			insertControl.setParameter(13, estado.getId());
			insertControl.setParameter(14, tipoControlId);
			
			Long successful = Long.valueOf(0);
			Long errors = Long.valueOf(0);
			for (ProcesoImportacionLinea procesoImportacionLinea : 
				iProcesoImportacionLineaBean.listByProcesoImportacion(procesoImportacionManaged)) {
				String[] fields = procesoImportacionLinea.getLinea().split(";", -1);
				Long lineNumber = procesoImportacionLinea.getNumero();
				
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
					
					String chip = null;
					try {
						chip = fields[1].trim();
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo chip incorrecto -> " + fields[1].trim());
						ok = false;
					}
					
					selectActivacion.setParameter(2, mid);
					selectActivacion.setParameter(3, chip);
					
					List<?> resultList = selectActivacion.getResultList();
					if (!resultList.isEmpty()) {
						Object[] tuple = (Object[])resultList.get(0);
						
						if (tuple.length == 4) {
							Long distribuidorId = null;
							if (tuple[0] != null) {
								distribuidorId = Long.valueOf((Integer) tuple[0]);
							}
							insertControl.setParameter(17, distribuidorId);
							
							Date fechaAsignacionDistribuidor = null;
							if (tuple[1] != null) {
								fechaAsignacionDistribuidor = (Date) tuple[1];
							}
							insertControl.setParameter(18, fechaAsignacionDistribuidor);
							
							Long puntoVentaId = null;
							if (tuple[2] != null) {
								puntoVentaId = Long.valueOf((Integer) tuple[2]);
							}
							insertControl.setParameter(19, puntoVentaId);
							
							Date fechaAsignacionPuntoVenta = null;
							if (tuple[3] != null) {
								fechaAsignacionPuntoVenta = (Date) tuple[3];
							}
							insertControl.setParameter(20, fechaAsignacionPuntoVenta);
						}
					} else {
						insertControl.setParameter(17, null);
						insertControl.setParameter(18, null);
						insertControl.setParameter(19, null);
						insertControl.setParameter(20, null);
					}
					
					if (!ok) {
						errors++;
					} else {
						insertControl.setParameter(15, mid);
						insertControl.setParameter(16, chip);
						
						insertControl.executeUpdate();

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

	public Control getSiguienteMidParaControlar() {
		Control result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<Control> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Control c"
					+ " WHERE c.estadoControl.id = :estadoPendienteId", 
					Control.class
				);
			query.setParameter("estadoPendienteId", Long.parseLong(Configuration.getInstance().getProperty("estadoControl.PENDIENTE")));
			query.setMaxResults(1);
			
			List<Control> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Control control = resultList.get(0);
				
				EstadoControl estadoControl = 
					iEstadoControlBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoControl.PROCESANDO")));
				
				control.setEstadoControl(estadoControl);
				
				control.setFact(hoy);
				control.setTerm(Long.valueOf(1));
				control.setUact(Long.valueOf(1));
				
				control = entityManager.merge(control);
				
				result = control;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void actualizarDatosControl(Control control) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<Control> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Control c"
					+ " WHERE c.empresa.id = :empresaId"
					+ " AND c.tipoControl.id = :tipoControlId"
					+ " AND c.mid = :mid"
					+ " AND c.estadoControl.id = :estadoControlProcesandoId",
					Control.class
				);
			query.setParameter("empresaId", control.getEmpresa().getId());
			query.setParameter("tipoControlId", control.getTipoControl().getId());
			query.setParameter("mid", control.getMid());
			query.setParameter(
				"estadoControlProcesandoId", 
				Long.parseLong(Configuration.getInstance().getProperty("estadoControl.PROCESANDO"))
			);
			query.setMaxResults(1);
			
			List<Control> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Control controlManaged = resultList.get(0);
				
				controlManaged.setCargaInicial(control.getCargaInicial());
				controlManaged.setEstadoControl(control.getEstadoControl());
				
				Long estadoControlId = control.getEstadoControl().getId();
				if (estadoControlId.equals(Long.parseLong(Configuration.getInstance().getProperty("estadoControl.OK")))
					|| estadoControlId.equals(Long.parseLong(Configuration.getInstance().getProperty("estadoControl.INVALIDO")))
					|| estadoControlId.equals(Long.parseLong(Configuration.getInstance().getProperty("estadoControl.CARGAR")))
				) {
					controlManaged.setFechaControl(hoy);
					controlManaged.setMesControl(hoy);
				}
				
				controlManaged.setFechaActivacion(control.getFechaActivacion());
				controlManaged.setFechaConexion(control.getFechaConexion());
				controlManaged.setFechaVencimiento(control.getFechaVencimiento());
				controlManaged.setMontoCargar(control.getMontoCargar());
				controlManaged.setMontoTotal(control.getMontoTotal());
				
				controlManaged.setFact(hoy);
				controlManaged.setTerm(Long.valueOf(1));
				controlManaged.setUact(Long.valueOf(1));
				
				control = entityManager.merge(controlManaged);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Control getById(Long id) {
		Control result = null;
		
		try {
			result = entityManager.find(Control.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(Control control) {
		try {
			Control controlManaged = entityManager.find(Control.class, control.getId());
			
			controlManaged.setCargaInicial(control.getCargaInicial());
			controlManaged.setFechaActivacion(control.getFechaActivacion());
			controlManaged.setFechaConexion(control.getFechaConexion());
			controlManaged.setFechaControl(control.getFechaControl());
			controlManaged.setFechaImportacion(control.getFechaImportacion());
			controlManaged.setFechaVencimiento(control.getFechaVencimiento());
			controlManaged.setMid(control.getMid());
			controlManaged.setMontoCargar(control.getMontoCargar());
			controlManaged.setMontoTotal(control.getMontoTotal());
			
			controlManaged.setEmpresa(control.getEmpresa());
			controlManaged.setEstadoControl(control.getEstadoControl());
			controlManaged.setTipoControl(control.getTipoControl());
			
			controlManaged.setFact(control.getFact());
			controlManaged.setTerm(control.getTerm());
			controlManaged.setUact(control.getUact());
			
			entityManager.merge(controlManaged);
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
				+ ";Empresa"
				+ ";Tipo de control"
				+ ";Estado de control"
				+ ";Fecha de importación"
				+ ";Fecha de activación"
				+ ";Fecha de conexión"
				+ ";Fecha de control"
				+ ";Fecha de vencimiento"
				+ ";Carga inicial"
				+ ";Monto cargar"
				+ ";Monto total"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Control control = (Control) object;
				
				String line = 
					control.getMid()
					+ ";" + (control.getEmpresa() != null ?
						control.getEmpresa().getNombre()
						: "")
					+ ";" + (control.getTipoControl() != null ?
						control.getTipoControl().getDescripcion()
						: "")
					+ ";" + (control.getEstadoControl() != null ?
						control.getEstadoControl().getNombre()
						: "")
					+ ";" + (control.getFechaImportacion() != null ?
						format.format(control.getFechaImportacion())
						: "")
					+ ";" + (control.getFechaActivacion() != null ?
						format.format(control.getFechaActivacion())
						: "")
					+ ";" + (control.getFechaConexion() != null ?
						format.format(control.getFechaConexion())
						: "")
					+ ";" + (control.getFechaControl() != null ?
						format.format(control.getFechaControl())
						: "")
					+ ";" + (control.getFechaVencimiento() != null ?
						format.format(control.getFechaVencimiento())
						: "")
					+ ";" + (control.getCargaInicial() != null ?
						control.getCargaInicial() 
						: "")
					+ ";" + (control.getMontoCargar() != null ?
						control.getMontoCargar() 
						: "")
					+ ";" + (control.getMontoTotal() != null ?
						control.getMontoTotal() 
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