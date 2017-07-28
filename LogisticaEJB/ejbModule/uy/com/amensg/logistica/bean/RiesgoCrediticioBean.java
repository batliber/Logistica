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
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class RiesgoCrediticioBean implements IRiesgoCrediticioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
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
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			Usuario usuario = iUsuarioBean.getById(usuarioId);
			
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
			Usuario usuario = iUsuarioBean.getById(usuarioId);
			
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
				iEstadoRiesgoCrediticioBean.getById(new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar")));
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioEnProceso = 
				iEstadoRiesgoCrediticioBean.getById(new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.EnProceso")));
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = entityManager.createQuery(
				"SELECT r"
				+ " FROM RiesgoCrediticio r"
				+ " WHERE r.estadoRiesgoCrediticio = :estadoRiesgoCrediticioParaProcesar",
				RiesgoCrediticio.class
			);
			query.setParameter("estadoRiesgoCrediticioParaProcesar", estadoRiesgoCrediticioParaProcesar);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioEnProceso);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(new Long(1));
				riesgoCrediticio.setUact(new Long(1));
				
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
					+ " ORDER BY r.fechaVigenciaDesde DESC",
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
					+ " ORDER BY r.fechaVigenciaDesde DESC",
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
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				String documento = fields[0].trim();
				
				documentos.add(documento);
			}
			
			Map<String, Integer> map = this.preprocesarConjunto(documentos, empresaId);
			
			Long importar = new Long(0);
			Long sobreescribir = new Long(0);
			Long omitir = new Long(0);
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
			
			result =
				"Se importarán " + importar + " documentos nuevos.|"
				+ "Se sobreescribirán " + sobreescribir + " documentos.|"
				+ "Se omitirán " + omitir + " documentos.";
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
				result.put(documento, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlRiesgoCrediticioId, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estado = 
				iEstadoRiesgoCrediticioBean.getById(new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId);
			
			CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel = 
				iCalificacionRiesgoCrediticioAntelBean.getById(new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK")));
			
			CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU = 
				iCalificacionRiesgoCrediticioBCUBean.getById(new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR")));
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			SQLQuery insertRiesgoCrediticio = hibernateSession.createSQLQuery(
				"INSERT INTO riesgo_crediticio("
					+ " id,"
					+ " fecha_vigencia_desde,"
					+ " fecha_importacion,"
					+ " fact,"
					+ " term,"
					+ " uact,"
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
					+ " ?"
				+ " )"
			);
			
			insertRiesgoCrediticio.setParameter(0, hoy, DateType.INSTANCE);
			insertRiesgoCrediticio.setParameter(1, hoy, DateType.INSTANCE);
			insertRiesgoCrediticio.setParameter(2, new Long(1), LongType.INSTANCE);
			insertRiesgoCrediticio.setParameter(3, loggedUsuarioId, LongType.INSTANCE);
			insertRiesgoCrediticio.setParameter(4, empresa.getId(), LongType.INSTANCE);
			insertRiesgoCrediticio.setParameter(5, estado.getId(), LongType.INSTANCE);
			insertRiesgoCrediticio.setParameter(6, tipoControlRiesgoCrediticioId, LongType.INSTANCE);
			insertRiesgoCrediticio.setParameter(7, calificacionRiesgoCrediticioAntel.getId(), LongType.INSTANCE);
			insertRiesgoCrediticio.setParameter(8, calificacionRiesgoCrediticioBCU.getId(), LongType.INSTANCE);
			
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
						insertRiesgoCrediticio.setParameter(9, documento, StringType.INSTANCE);
						
						insertRiesgoCrediticio.executeUpdate();

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
	
	public void save(RiesgoCrediticio riesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			riesgoCrediticio.setFact(date);
			riesgoCrediticio.setTerm(riesgoCrediticio.getTerm());
			riesgoCrediticio.setUact(riesgoCrediticio.getUact());
			
			if (riesgoCrediticio.getId() != null) {
				entityManager.merge(riesgoCrediticio);
			} else {
				entityManager.persist(riesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioACM(ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.empresa.id = :empresaId"
					+ " AND r.documento = :documento"
					+ " ORDER BY r.id DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("empresaId", acmInterfaceRiesgoCrediticio.getEmpresa().getId());
			query.setParameter("documento", acmInterfaceRiesgoCrediticio.getDocumento());
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel = null;
				if (!acmInterfaceRiesgoCrediticio.getDeudaCelular() &&
					!acmInterfaceRiesgoCrediticio.getRiesgoCrediticioCelular() &&
					acmInterfaceRiesgoCrediticio.getEstadoDeudaClienteFijo().equals("OK")) {
					calificacionRiesgoCrediticioAntel = 
						iCalificacionRiesgoCrediticioAntelBean.getById(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK"))
						);
				} else {
					calificacionRiesgoCrediticioAntel = 
						iCalificacionRiesgoCrediticioAntelBean.getById(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.TieneDeuda"))
						);
				}
				riesgoCrediticio.setCalificacionRiesgoCrediticioAntel(calificacionRiesgoCrediticioAntel);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado")));
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(new Long(1));
				riesgoCrediticio.setUact(new Long(1));
				
				entityManager.merge(riesgoCrediticio);
				
				acmInterfaceRiesgoCrediticio.setFechaAnalisis(date);
				
				iACMInterfaceRiesgoCrediticioBean.save(acmInterfaceRiesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioBCU(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.empresa.id = :empresaId"
					+ " AND r.documento = :documento"
					+ " ORDER BY r.id DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("empresaId", bcuInterfaceRiesgoCrediticio.getEmpresa().getId());
			query.setParameter("documento", bcuInterfaceRiesgoCrediticio.getDocumento());
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(
					iCalificacionRiesgoCrediticioBCUBean.getById(
						new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR"))
					)
				);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(
						new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(new Long(1));
				riesgoCrediticio.setUact(new Long(1));
				
				entityManager.merge(riesgoCrediticio);
				
				bcuInterfaceRiesgoCrediticio.setFechaAnalisis(date);
				
				iBCUInterfaceRiesgoCrediticioBean.save(bcuInterfaceRiesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCUSinDatos(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.empresa.id = :empresaId"
					+ " AND r.documento = :documento"
					+ " ORDER BY r.id DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("empresaId", bcuInterfaceRiesgoCrediticio.getEmpresa().getId());
			query.setParameter("documento", bcuInterfaceRiesgoCrediticio.getDocumento());
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(
					iCalificacionRiesgoCrediticioBCUBean.getById(
						new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
					)
				);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(
						new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
					);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(new Long(1));
				riesgoCrediticio.setUact(new Long(1));
				
				entityManager.merge(riesgoCrediticio);
				
				bcuInterfaceRiesgoCrediticio.setFechaAnalisis(date);
				
				iBCUInterfaceRiesgoCrediticioBean.save(bcuInterfaceRiesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera
	) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<RiesgoCrediticio> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticio r"
					+ " WHERE r.empresa.id = :empresaId"
					+ " AND r.documento = :documento"
					+ " ORDER BY r.id DESC",
					RiesgoCrediticio.class
				);
			query.setParameter("empresaId", bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getEmpresa().getId());
			query.setParameter("documento", 
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getDocumento()
			);
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticio riesgoCrediticio = resultList.iterator().next();
				
				CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU = 
					iCalificacionRiesgoCrediticioBCUBean.getByCalificacion(
						bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getCalificacion()
					);
				
				// Si la calificación de riesgo es SIN DETERMINAR o si es mayor que la peor registrada, actualizar.
				if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
					iCalificacionRiesgoCrediticioBCUBean.getById(
						new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR"))
					)) ||
					riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getOrden() < calificacionRiesgoCrediticioBCU.getOrden()
				) {
					riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(calificacionRiesgoCrediticioBCU);
				}
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
					iEstadoRiesgoCrediticioBean.getById(new Long(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado")));
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(new Long(1));
				riesgoCrediticio.setUact(new Long(1));
				
				entityManager.merge(riesgoCrediticio);
				
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setFechaAnalisis(date);
				
				BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticioManaged = iBCUInterfaceRiesgoCrediticioBean.getLastByEmpresaDocumento(
					riesgoCrediticio.getEmpresa(),
					riesgoCrediticio.getDocumento()
				);
				
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setBcuInterfaceRiesgoCrediticio(bcuInterfaceRiesgoCrediticioManaged);
				
				iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.save(bcuInterfaceRiesgoCrediticioInstitucionFinanciera);
			}
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
				"Documento"
				+ ";Empresa"
				+ ";Fecha de importación"
				+ ";Fecha de vigencia"
				+ ";Estado"
				+ ";Tipo de control"
				+ ";Calificación ANTEL"
				+ ";Calificación BCU"
			);
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
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
}