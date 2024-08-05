package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;
import uy.com.amensg.logistica.webservices.external.tablero.BCU;
import uy.com.amensg.logistica.webservices.external.tablero.BCUBCUItem;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigo;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoRIESGOBCU;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoSTATUSBCU;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoSTATUSBCUResponse;
import uy.com.amensg.logistica.webservices.external.tablero.RiesgoCreditoAmigoSoapPort;

@Stateless
public class RiesgoCrediticioBean implements IRiesgoCrediticioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IEstadoRiesgoCrediticioBean iEstadoRiesgoCrediticioBean;
	
	@EJB
	private IACMInterfaceRiesgoCrediticioBean iACMInterfaceRiesgoCrediticioBean;
	
	@EJB
	private ICalificacionRiesgoCrediticioAntelBean iCalificacionRiesgoCrediticioAntelBean;
	
	@EJB
	private IBCUInterfaceRiesgoCrediticioBean iBCUInterfaceRiesgoCrediticioBean;
	
	@EJB
	private ICalificacionRiesgoCrediticioBCUBean iCalificacionRiesgoCrediticioBCUBean;
	
	@EJB
	private IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	@EJB
	private ITipoControlRiesgoCrediticioBean iTipoControlRiesgoCrediticioBean;
	
	@EJB
	private IACMInterfacePersonaBean iACMInterfacePersonaBean;
	
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
			
			return new QueryBuilder<RiesgoCrediticio>().list(entityManager, metadataConsulta, new RiesgoCrediticio());
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
			
			result = new QueryBuilder<RiesgoCrediticio>().count(entityManager, metadataConsulta, new RiesgoCrediticio());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticio getSiguienteDocumentoParaControlar() {
		RiesgoCrediticio result = null;
		
		try {
			EstadoRiesgoCrediticio estadoRiesgoCrediticioParaProcesar = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioParaProcesarPrioritario = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesarPrioritario"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioEnProceso = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.EnProceso"))
				);
			
			TipoControlRiesgoCrediticio tipoControlRiesgoCrediticioBCU =
				iTipoControlRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoControlRiesgoCrediticio.BCU"))
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date date = gregorianCalendar.getTime();
			
			gregorianCalendar.add(GregorianCalendar.MINUTE, -1);
			
			Date fechaMaximaProceso = gregorianCalendar.getTime();
			
			TypedQuery<RiesgoCrediticio> query = entityManager.createQuery(
				"SELECT r"
				+ " FROM RiesgoCrediticio r"
				+ " WHERE r.estadoRiesgoCrediticio IN ("
					+ "	:estadoRiesgoCrediticioParaProcesar, :estadoRiesgoCrediticioParaProcesarPrioritario"
				+ " )"
				+ " OR ("
				+ "		r.estadoRiesgoCrediticio = :estadoRiesgoCrediticioEnProceso"
					+ " AND r.tipoControlRiesgoCrediticio = :tipoControlRiesgoCrediticioBCU"
					+ " AND r.fact < :fechaMaximaProceso"
				+ " )"
				+ " ORDER BY r.estadoRiesgoCrediticio.id DESC",
				RiesgoCrediticio.class
			);
			query.setParameter("estadoRiesgoCrediticioParaProcesar", estadoRiesgoCrediticioParaProcesar);
			query.setParameter("estadoRiesgoCrediticioParaProcesarPrioritario", estadoRiesgoCrediticioParaProcesarPrioritario);
			query.setParameter("estadoRiesgoCrediticioEnProceso", estadoRiesgoCrediticioEnProceso);
			query.setParameter("tipoControlRiesgoCrediticioBCU", tipoControlRiesgoCrediticioBCU);
			query.setParameter("fechaMaximaProceso", fechaMaximaProceso);
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioEnProceso);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(Long.valueOf(1));
				
				this.save(riesgoCrediticio);
				
				result = riesgoCrediticio;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticio getSiguienteDocumentoParaControlarRiesgoOnLine() {
		RiesgoCrediticio result = null;
		
		try {
			EstadoRiesgoCrediticio estadoRiesgoCrediticioParaProcesar = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioParaProcesarPrioritario = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesarPrioritario"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioEnProceso = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.EnProceso"))
				);
			
			TipoControlRiesgoCrediticio tipoControlRiesgoCrediticioRIESGOONLINE =
				iTipoControlRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoControlRiesgoCrediticio.RIESGOONLINE"))
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date date = gregorianCalendar.getTime();
			
//			Long maximoTiempoEnProcesoMinutos = 
//				Long.parseLong(Configuration.getInstance().getProperty("riesgoCrediticio.maximoTiempoEnProcesoMinutos"));
//			gregorianCalendar.add(
//				GregorianCalendar.MINUTE, -1 * maximoTiempoEnProcesoMinutos.intValue()
//			);
//			
//			Date fechaMaximaProceso = gregorianCalendar.getTime();
			
			TypedQuery<RiesgoCrediticio> query = entityManager.createQuery(
				"SELECT r"
				+ " FROM RiesgoCrediticio r"
				+ " WHERE r.tipoControlRiesgoCrediticio = :tipoControlRiesgoCrediticioRIESGOONLINE"
				+ " AND ("
					+ "	r.estadoRiesgoCrediticio IN ("
						+ "	:estadoRiesgoCrediticioParaProcesar, :estadoRiesgoCrediticioParaProcesarPrioritario"
					+ " )"
//						OR ("
//						+ "	r.estadoRiesgoCrediticio = :estadoRiesgoCrediticioEnProceso"
//						+ " AND r.fact < :fechaMaximaProceso"
//					+ " )"
				+ " )"
				+ " ORDER BY r.estadoRiesgoCrediticio.id DESC",
				RiesgoCrediticio.class
			);
			query.setParameter("tipoControlRiesgoCrediticioRIESGOONLINE", tipoControlRiesgoCrediticioRIESGOONLINE);
			query.setParameter("estadoRiesgoCrediticioParaProcesar", estadoRiesgoCrediticioParaProcesar);
			query.setParameter("estadoRiesgoCrediticioParaProcesarPrioritario", estadoRiesgoCrediticioParaProcesarPrioritario);
//			query.setParameter("estadoRiesgoCrediticioEnProceso", estadoRiesgoCrediticioEnProceso);
//			query.setParameter("fechaMaximaProceso", fechaMaximaProceso);
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioEnProceso);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(Long.valueOf(1));
				
				this.save(riesgoCrediticio);
				
				result = riesgoCrediticio;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticio getLastByDocumento(String documento) {
		RiesgoCrediticio result = null;
		
		try {
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.documento = :documento"
					+ " ORDER BY r.id DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("documento", documento);
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticio getLastByEmpresaDocumento(Empresa empresa, String documento) {
		RiesgoCrediticio result = null;
		
		try {
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.empresa.id = :empresaId"
					+ " AND r.documento = :documento"
					+ " ORDER BY r.fact DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("documento", documento);
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.iterator().next();
			}
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
			
			Collection<String> documentos = new LinkedList<String>();
			
			boolean errorFatal = false;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				try {
					documentos.add(fields[0].trim());
				} catch (Exception e) {
					errorFatal = true;
					
					e.printStackTrace();
					break;
				}
			}
			
			Map<String, Integer> map = this.preprocesarConjunto(documentos, empresaId);
			
			Long importar = Long.valueOf(0);
			Long sobreescribir = Long.valueOf(0);
			Long omitir = Long.valueOf(0);
			for (Entry<String, Integer> entry : map.entrySet()) {
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
			
			if (!errorFatal) {
				result =
					"Se importarán " + importar + " documentos nuevos.|"
					+ "Se sobreescribirán " + sobreescribir + " documentos.|"
					+ "Se omitirán " + omitir + " documentos.";
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
	
	public Map<String, Integer> preprocesarConjunto(Collection<String> documentos, Long empresaId) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		try {
			for (String documento : documentos) {
				if (!documento.isEmpty()) {
					result.put(documento, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
				} else {
					result.put(documento, Constants.__COMPROBACION_IMPORTACION_OMITIR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String procesarArchivoEmpresa(
		String fileName, 
		Long empresaId, 
		Long tipoControlRiesgoCrediticioId, 
		Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estado = 
				iEstadoRiesgoCrediticioBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId, false);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.RiesgoCrediticio")));
			
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
			
			CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel = 
				iCalificacionRiesgoCrediticioAntelBean.getById(Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.SINDETERMINAR")));
			
			CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU = 
				iCalificacionRiesgoCrediticioBCUBean.getById(Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR")));
			
			Query insertRiesgoCrediticio = entityManager.createNativeQuery(
				"INSERT INTO riesgo_crediticio("
					+ " id,"
					+ " fecha_vigencia_desde,"
					+ " fecha_importacion,"
					+ " fcre,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					+ " ucre,"
					+ " empresa_id,"
					+ " estado_riesgo_crediticio_id,"
					+ " tipo_control_riesgo_crediticio_id,"
					+ " calificacion_riesgo_crediticio_antel_id,"
					+ " calificacion_riesgo_crediticio_bcu_id,"
					+ " documento"
				+ " ) VALUES ("
					+ " nextval('hibernate_sequence'),"
					+ " null,"
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
			
			insertRiesgoCrediticio.setParameter(1, hoy);
			insertRiesgoCrediticio.setParameter(2, hoy);
			insertRiesgoCrediticio.setParameter(3, hoy);
			insertRiesgoCrediticio.setParameter(4, Long.valueOf(1));
			insertRiesgoCrediticio.setParameter(5, loggedUsuarioId);
			insertRiesgoCrediticio.setParameter(6, loggedUsuarioId);
			insertRiesgoCrediticio.setParameter(7, empresa.getId());
			insertRiesgoCrediticio.setParameter(8, estado.getId());
			insertRiesgoCrediticio.setParameter(9, tipoControlRiesgoCrediticioId);
			insertRiesgoCrediticio.setParameter(10, calificacionRiesgoCrediticioAntel.getId());
			insertRiesgoCrediticio.setParameter(11, calificacionRiesgoCrediticioBCU.getId());
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
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
					
					String documento = null;
					try {
						documento = fields[0].trim();
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo documento incorrecto -> " + fields[0].trim());
						ok = false;
					}
					
					if (!ok) {
						errors++;
					} else {
						insertRiesgoCrediticio.setParameter(12, documento);
						
						insertRiesgoCrediticio.executeUpdate();

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
	
	public void save(RiesgoCrediticio riesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			riesgoCrediticio.setFact(date);
			
			if (riesgoCrediticio.getId() != null) {
				riesgoCrediticio = entityManager.merge(riesgoCrediticio);
			} else {
				riesgoCrediticio.setFcre(date);
				riesgoCrediticio.setUcre(riesgoCrediticio.getUact());
				
				entityManager.persist(riesgoCrediticio);
			}
			
			ACMInterfacePersona acmInterfacePersonaManaged = 
				iACMInterfacePersonaBean.getByDocumento(riesgoCrediticio.getDocumento());
			
			if (acmInterfacePersonaManaged != null) {
				acmInterfacePersonaManaged.setRiesgoCrediticio(riesgoCrediticio);
				
				acmInterfacePersonaManaged.setFact(date);
				acmInterfacePersonaManaged.setTerm(Long.valueOf(1));
				acmInterfacePersonaManaged.setUact(riesgoCrediticio.getUact());
				
				entityManager.merge(acmInterfacePersonaManaged);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioACM(
		Long riesgoCrediticioId,
		ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio
	) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.id = :id",
					RiesgoCrediticio.class
				);
			query.setParameter("id", riesgoCrediticioId);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel = null;
				if (!(acmInterfaceRiesgoCrediticio.getDeudaCelular().equals("SI"))
					&& !(acmInterfaceRiesgoCrediticio.getRiesgoCrediticioCelular().equals("SI"))
					&& !(acmInterfaceRiesgoCrediticio.getEstadoDeudaClienteFijo().toUpperCase().equals("TIENE DEUDA"))
				) {
					calificacionRiesgoCrediticioAntel = 
						iCalificacionRiesgoCrediticioAntelBean.getById(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK"))
						);
				} else {
					calificacionRiesgoCrediticioAntel = 
						iCalificacionRiesgoCrediticioAntelBean.getById(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.TieneDeuda"))
						);
				}
				riesgoCrediticio.setCalificacionRiesgoCrediticioAntel(calificacionRiesgoCrediticioAntel);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(Long.valueOf(1));
				
				entityManager.merge(riesgoCrediticio);
				
				acmInterfaceRiesgoCrediticio.setFechaAnalisis(date);
				
				iACMInterfaceRiesgoCrediticioBean.save(acmInterfaceRiesgoCrediticio);
			}
		} catch (Exception e) {
			System.err.println("Error para el riesgo con id: " + riesgoCrediticioId);
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioBCU(
		Long riesgoCrediticioId,
		BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio
	) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.id = :id",
					RiesgoCrediticio.class
				);
			query.setParameter("id", riesgoCrediticioId);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(
					iCalificacionRiesgoCrediticioBCUBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR"))
					)
				);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				riesgoCrediticio.setFechaVigenciaDesde(date);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(Long.valueOf(1));
				
				entityManager.merge(riesgoCrediticio);
				
				bcuInterfaceRiesgoCrediticio.setFechaAnalisis(date);
				
				iBCUInterfaceRiesgoCrediticioBean.save(bcuInterfaceRiesgoCrediticio);
			}
		} catch (Exception e) {
			System.err.println("Error para el riesgo con id: " + riesgoCrediticioId);
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCUSinDatos(
		Long riesgoCrediticioId,
		BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio
	) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.id = :id",
					RiesgoCrediticio.class
				);
			query.setParameter("id", riesgoCrediticioId);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(
					iCalificacionRiesgoCrediticioBCUBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
					)
				);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				riesgoCrediticio.setFechaVigenciaDesde(date);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(Long.valueOf(1));
				
				entityManager.merge(riesgoCrediticio);
				
				bcuInterfaceRiesgoCrediticio.setFechaAnalisis(date);
				
				iBCUInterfaceRiesgoCrediticioBean.save(bcuInterfaceRiesgoCrediticio);
			}
		} catch (Exception e) {
			System.err.println("Error para el riesgo con id: " + riesgoCrediticioId);
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		Long riesgoCrediticioId,
		BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera
	) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Long calificacionRiesgoCrediticioBCUSinDeterminarId =
				Long.parseLong(
					Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR")
				);
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.id = :id",
					RiesgoCrediticio.class
				);
			query.setParameter("id", riesgoCrediticioId);
			
			List<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.get(0);
				
				CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU = 
					iCalificacionRiesgoCrediticioBCUBean.getByCalificacion(
						bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getCalificacion().trim()
					);
				
				if (calificacionRiesgoCrediticioBCU == null) {
					throw new Exception("Se recibió un código de calificación no registrado: " 
						+ bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getCalificacion().trim());
				} else {
					// Si la calificación de riesgo es null, SIN DETERMINAR o si es mayor que la peor registrada, 
					// actualizar.
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU() == null
						|| riesgoCrediticio.getCalificacionRiesgoCrediticioBCU()
							.getId().equals(calificacionRiesgoCrediticioBCUSinDeterminarId) 
						|| riesgoCrediticio.getCalificacionRiesgoCrediticioBCU()
							.getOrden() < calificacionRiesgoCrediticioBCU.getOrden()
					) {
						riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(calificacionRiesgoCrediticioBCU);
					}
					
					EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
						iEstadoRiesgoCrediticioBean.getById(
							Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
						);
					
					riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
					riesgoCrediticio.setFechaVigenciaDesde(date);
					
					riesgoCrediticio.setFact(date);
					riesgoCrediticio.setTerm(Long.valueOf(1));
					riesgoCrediticio.setUact(Long.valueOf(1));
					
					entityManager.merge(riesgoCrediticio);
					
					bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setFechaAnalisis(date);
					
					BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticioManaged = 
						iBCUInterfaceRiesgoCrediticioBean.getLastByEmpresaDocumento(
							riesgoCrediticio.getEmpresa(),
							riesgoCrediticio.getDocumento()
						);
					
					bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setBcuInterfaceRiesgoCrediticio(
						bcuInterfaceRiesgoCrediticioManaged
					);
					
					iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.save(
						bcuInterfaceRiesgoCrediticioInstitucionFinanciera
					);
					
					if (bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getInstitucionFinanciera()
						.equals(
							Configuration.getInstance().getProperty(
								"calificacionRiesgoCrediticioBCU.institucionFinanciera.peorCalificacion"
							)
						)
					) {
						String urlString = 
							Configuration.getInstance().getProperty("creditoAmigo.URL")
								+ "?o=" + Configuration.getInstance().getProperty("creditoAmigo.operation.RemoteUpdate")
								+ "&d=" + riesgoCrediticio.getDocumento() 
								+ "&c=" + URLEncoder.encode(riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getDescripcion(), "UTF-8");
						URL url = new URL(urlString);
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						urlConnection.setRequestMethod("GET");
						
						url.getContent();
						
						urlConnection.disconnect();
						
						if (Boolean.valueOf(Configuration.getInstance().getProperty("creditoAmigo.operation.NotifyGISA"))) {
							RiesgoCreditoAmigoSoapPort riesgoCreditoAmigo = 
								new RiesgoCreditoAmigo().getRiesgoCreditoAmigoSoapPort();
							
							RiesgoCreditoAmigoRIESGOBCU riesgoCreditoAmigoRIESGOBCU = 
								new RiesgoCreditoAmigoRIESGOBCU();
							riesgoCreditoAmigoRIESGOBCU.setWdocu(riesgoCrediticio.getDocumento());
							riesgoCreditoAmigoRIESGOBCU.setWip("");
							riesgoCreditoAmigoRIESGOBCU.setWpass("N3s70r-3$eV");
							riesgoCreditoAmigoRIESGOBCU.setWriesgo(riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getDescripcion());
							riesgoCreditoAmigoRIESGOBCU.setWusu("NestorBCU");
							
							riesgoCreditoAmigo.riesgobcu(riesgoCreditoAmigoRIESGOBCU);
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error para el riesgo con id: " + riesgoCrediticioId);
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
				"Documento"
				+ ";Empresa"
				+ ";Fecha de importación"
				+ ";Fecha de vigencia"
				+ ";Estado"
				+ ";Tipo de control"
				+ ";Calificación ANTEL"
				+ ";Calificación BCU"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				RiesgoCrediticio riesgoCrediticio = (RiesgoCrediticio) object;
				
				String line = 
					riesgoCrediticio.getDocumento()
					+ ";" + (riesgoCrediticio.getEmpresa() != null ?
						riesgoCrediticio.getEmpresa().getNombre()
						: "")
					+ ";" + (riesgoCrediticio.getFechaImportacion() != null ?
						format.format(riesgoCrediticio.getFechaImportacion())
						: "")
					+ ";" + (riesgoCrediticio.getFechaVigenciaDesde() != null ?
						format.format(riesgoCrediticio.getFechaVigenciaDesde())
						: "")
					+ ";" + (riesgoCrediticio.getEstadoRiesgoCrediticio() != null ?
						riesgoCrediticio.getEstadoRiesgoCrediticio().getNombre()
						: "")
					+ ";" + (riesgoCrediticio.getTipoControlRiesgoCrediticio() != null ?
						riesgoCrediticio.getTipoControlRiesgoCrediticio().getDescripcion()
						: "")
					+ ";" + (riesgoCrediticio.getCalificacionRiesgoCrediticioAntel() != null ?
						riesgoCrediticio.getCalificacionRiesgoCrediticioAntel().getDescripcion()
						: "")
					+ ";" + (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU() != null ?
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getDescripcion()
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

	public void controlarRiesgoBCU(String documento) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticio = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesarPrioritario"))
				);
			
			Empresa empresa = 
				iEmpresaBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("empresa.GazalerSA")), 
					false
				);
			
			TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio =
				iTipoControlRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoControlRiesgoCrediticio.BCU"))
				);
			
			RiesgoCrediticio riesgoCrediticio = new RiesgoCrediticio();
			riesgoCrediticio.setDocumento(documento);
			riesgoCrediticio.setEmpresa(empresa);
			riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticio);
			riesgoCrediticio.setTipoControlRiesgoCrediticio(tipoControlRiesgoCrediticio);
			
			riesgoCrediticio.setFact(hoy);
			riesgoCrediticio.setFcre(hoy);
			riesgoCrediticio.setTerm(Long.valueOf(1));
			riesgoCrediticio.setUact(Long.valueOf(1));
			riesgoCrediticio.setUcre(Long.valueOf(1));
			
			this.save(riesgoCrediticio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void controlarRiesgoBCUOnline(String documento) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioParaProcesarPrioritario = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(
						Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesarPrioritario")
					)
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioEnProceso = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.EnProceso"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioTiempoEnProcesoAgotado = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.TiempoEnProcesoAgotado"))
				);
			
			Empresa empresa = 
				iEmpresaBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("empresa.GazalerSA")), 
					false
				);
			
			TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio =
				iTipoControlRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoControlRiesgoCrediticio.RIESGOONLINE"))
				);
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.documento = :documento"
					+ " AND r.tipoControlRiesgoCrediticio.id = :tipoControlRiesgoCrediticioId"
					+ " AND r.empresa.id = :empresaId"
					+ " ORDER BY r.id DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("documento", documento);
			query.setParameter("tipoControlRiesgoCrediticioId", tipoControlRiesgoCrediticio.getId());
			query.setParameter("empresaId", empresa.getId());
			query.setMaxResults(1);
			
			boolean insert = true;
			
			List<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticioManaged = resultList.get(0);
				
				Long maximoTiempoEnProcesoMinutos = 
					Long.parseLong(Configuration.getInstance().getProperty(
						"riesgoCrediticio.maximoTiempoEnProcesoMinutos"
					));
				
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.add(
					GregorianCalendar.MINUTE, -1 * maximoTiempoEnProcesoMinutos.intValue()
				);
					
				Date fechaMaximaEnProceso = gregorianCalendar.getTime();
				
				Long maximoTiempoProcesadoDias = 
					Long.parseLong(Configuration.getInstance().getProperty(
						"riesgoCrediticio.maximoTiempoProcesadoDias"
					));
				
				gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.add(
					GregorianCalendar.DATE, -1 * maximoTiempoProcesadoDias.intValue()
				);
				
				Date fechaMaximaProcesado = gregorianCalendar.getTime();
				
				if (
						// El documento está en estado ParaProcesarPrioritario
						riesgoCrediticioManaged.getEstadoRiesgoCrediticio().getId().equals(
							estadoRiesgoCrediticioParaProcesarPrioritario.getId()
						)
					|| (
						// El documento está en estado EnProceso desde hace menos de n minutos.
						riesgoCrediticioManaged.getEstadoRiesgoCrediticio().getId().equals(
							estadoRiesgoCrediticioEnProceso.getId()
						)
						&& riesgoCrediticioManaged.getFact().after(fechaMaximaEnProceso)
					) || (
						// El documento está en estado Procesado desde hace menos de m días.
						riesgoCrediticioManaged.getEstadoRiesgoCrediticio().getId().equals(
							estadoRiesgoCrediticioProcesado.getId()
						)
						&& riesgoCrediticioManaged.getFact().after(fechaMaximaProcesado)
					)
				) {
					insert = false;
				}
				
				if (
					// El documento estuvo EnProceso más de n minutos.
					riesgoCrediticioManaged.getEstadoRiesgoCrediticio().getId().equals(
						estadoRiesgoCrediticioEnProceso.getId()
					)
					&& riesgoCrediticioManaged.getFact().before(fechaMaximaEnProceso)
				) {
					riesgoCrediticioManaged.setEstadoRiesgoCrediticio(
						estadoRiesgoCrediticioTiempoEnProcesoAgotado
					);
					
					riesgoCrediticioManaged.setFact(hoy);
					riesgoCrediticioManaged.setTerm(Long.valueOf(1));
					riesgoCrediticioManaged.setUact(Long.valueOf(1));
				}
			}
			
			if (insert) {
				RiesgoCrediticio riesgoCrediticio = new RiesgoCrediticio();
				riesgoCrediticio.setDocumento(documento);
				riesgoCrediticio.setEmpresa(empresa);
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioParaProcesarPrioritario);
				riesgoCrediticio.setTipoControlRiesgoCrediticio(tipoControlRiesgoCrediticio);
				
				riesgoCrediticio.setFact(hoy);
				riesgoCrediticio.setFcre(hoy);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(Long.valueOf(1));
				riesgoCrediticio.setUcre(Long.valueOf(1));
				
				this.save(riesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BCUBCUItem> listPendientesRiesgoOnLine() {
		List<BCUBCUItem> result = new LinkedList<BCUBCUItem>();
		
		try {
			RiesgoCreditoAmigoSoapPort service = new RiesgoCreditoAmigo().getRiesgoCreditoAmigoSoapPort();
			
			RiesgoCreditoAmigoSTATUSBCU riesgoCreditoAmigoSTATUSBCU = new RiesgoCreditoAmigoSTATUSBCU();
			riesgoCreditoAmigoSTATUSBCU.setWip("");
			riesgoCreditoAmigoSTATUSBCU.setWpass("N3s70r-3$eV");
			riesgoCreditoAmigoSTATUSBCU.setWusu("NestorBCU");
				
			RiesgoCreditoAmigoSTATUSBCUResponse riesgoCreditoAmigoSTATUSBCUResponse =
				service.statusbcu(riesgoCreditoAmigoSTATUSBCU);
			
			BCU bcu = riesgoCreditoAmigoSTATUSBCUResponse.getWbcu();
			
			result = bcu.getBCUBCUItem();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}