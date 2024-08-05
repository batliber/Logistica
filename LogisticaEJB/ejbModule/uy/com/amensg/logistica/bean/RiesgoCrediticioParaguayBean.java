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
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguay;
import uy.com.amensg.logistica.entities.SituacionRiesgoCrediticioParaguay;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class RiesgoCrediticioParaguayBean implements IRiesgoCrediticioParaguayBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IEstadoRiesgoCrediticioBean iEstadoRiesgoCrediticioBean;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private ISituacionRiesgoCrediticioParaguayBean iSituacionRiesgoCrediticioParaguayBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<RiesgoCrediticioParaguay>().list(
				entityManager, metadataConsulta, new RiesgoCrediticioParaguay()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<RiesgoCrediticioParaguay>().count(
				entityManager, metadataConsulta, new RiesgoCrediticioParaguay()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public RiesgoCrediticioParaguay getById(Long id) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			result = entityManager.find(RiesgoCrediticioParaguay.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticioParaguay getLastByDocumentoFechaNacimientoSituacionRiesgoCrediticioParaguayId(
		String documento, Date fechaNacimiento, Long situacionRiesgoCrediticioParaguayId
	) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			TypedQuery<RiesgoCrediticioParaguay> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticioParaguay r"
					+ " WHERE r.documento = :documento"
					+ " AND r.fechaNacimiento = :fechaNacimiento"
					+ " AND r.situacionRiesgoCrediticioParaguay.id = :situacionRiesgoCrediticioParaguayId"
					+ " ORDER BY r.fact DESC",
					RiesgoCrediticioParaguay.class
				);
			query.setParameter("documento", documento);
			query.setParameter("fechaNacimiento", fechaNacimiento);
			query.setParameter("situacionRiesgoCrediticioParaguayId", situacionRiesgoCrediticioParaguayId);
			query.setMaxResults(1);
			
			Collection<RiesgoCrediticioParaguay> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticioParaguay getSiguienteDocumentoParaControlar() {
		RiesgoCrediticioParaguay result = null;
		
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
				
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date date = gregorianCalendar.getTime();
				
			Long maximoTiempoEnProcesoMinutos = 
				Long.parseLong(
					Configuration.getInstance().getProperty("riesgoCrediticio.maximoTiempoEnProcesoMinutos")
				);
			gregorianCalendar.add(
				GregorianCalendar.MINUTE, -1 * maximoTiempoEnProcesoMinutos.intValue()
			);
				
			Date fechaMaximaProceso = gregorianCalendar.getTime();
				
			TypedQuery<RiesgoCrediticioParaguay> query = entityManager.createQuery(
				"SELECT r"
				+ " FROM RiesgoCrediticioParaguay r"
				+ " WHERE ("
					+ "	r.estadoRiesgoCrediticio IN ("
						+ "	:estadoRiesgoCrediticioParaProcesar,"
						+ " :estadoRiesgoCrediticioParaProcesarPrioritario"
					+ " ) OR ("
						+ "	r.estadoRiesgoCrediticio = :estadoRiesgoCrediticioEnProceso"
						+ " AND r.fact < :fechaMaximaProceso"
					+ " )"
				+ " )"
				+ " ORDER BY r.estadoRiesgoCrediticio.id DESC",
				RiesgoCrediticioParaguay.class
			);
			query.setParameter("estadoRiesgoCrediticioParaProcesar", estadoRiesgoCrediticioParaProcesar);
			query.setParameter("estadoRiesgoCrediticioParaProcesarPrioritario", estadoRiesgoCrediticioParaProcesarPrioritario);
			query.setParameter("estadoRiesgoCrediticioEnProceso", estadoRiesgoCrediticioEnProceso);
			query.setParameter("fechaMaximaProceso", fechaMaximaProceso);
			query.setMaxResults(1);
				
			Collection<RiesgoCrediticioParaguay> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticioParaguay riesgoCrediticioParaguay = resultList.iterator().next();
				
				riesgoCrediticioParaguay.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioEnProceso);
				
				riesgoCrediticioParaguay.setFact(date);
				riesgoCrediticioParaguay.setTerm(Long.valueOf(1));
				riesgoCrediticioParaguay.setUact(Long.valueOf(1));
				
				result = riesgoCrediticioParaguay;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticioParaguay controlarRiesgo(
		String documento, Date fechaNacimiento, Long situacionRiesgoCrediticioParaguayId
	) {
		RiesgoCrediticioParaguay result = null;
		
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
			
			result = 
				this.getLastByDocumentoFechaNacimientoSituacionRiesgoCrediticioParaguayId(
					documento, fechaNacimiento, situacionRiesgoCrediticioParaguayId
				);
			
			boolean insert = true;
			
			if (result != null) {
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
					result.getEstadoRiesgoCrediticio().getId().equals(
						estadoRiesgoCrediticioParaProcesarPrioritario.getId()
					)
					|| (
						// El documento está en estado EnProceso desde hace menos de n minutos
						result.getEstadoRiesgoCrediticio().getId().equals(
							estadoRiesgoCrediticioEnProceso.getId()
						)
						&& result.getFact().after(fechaMaximaEnProceso)
					) || (
						// El documento está en estado Procesado desde hace menos de m días.
						result.getEstadoRiesgoCrediticio().getId().equals(
							estadoRiesgoCrediticioProcesado.getId()
						)
						&& result.getFact().after(fechaMaximaProcesado)
					)
				) {
					insert = false;
				}
				
				if (
					// El documento estuvo EnProceso más de n minutos.
					result.getEstadoRiesgoCrediticio().getId().equals(
						estadoRiesgoCrediticioEnProceso.getId()
					)
					&& result.getFact().before(fechaMaximaEnProceso)
				) {
					result.setEstadoRiesgoCrediticio(
						estadoRiesgoCrediticioTiempoEnProcesoAgotado
					);
					
					result.setFact(hoy);
					result.setTerm(Long.valueOf(1));
					result.setUact(Long.valueOf(1));
				}
			}
				
			if (insert) {
				RiesgoCrediticioParaguay riesgoCrediticioParaguay = new RiesgoCrediticioParaguay();
				riesgoCrediticioParaguay.setDocumento(documento);
				riesgoCrediticioParaguay.setEmpresa(empresa);
				riesgoCrediticioParaguay.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioParaProcesarPrioritario);
				riesgoCrediticioParaguay.setFechaNacimiento(fechaNacimiento);
				
				SituacionRiesgoCrediticioParaguay situacionRiesgoCrediticioParaguay = 
					iSituacionRiesgoCrediticioParaguayBean.getById(situacionRiesgoCrediticioParaguayId);
				
				riesgoCrediticioParaguay.setSituacionRiesgoCrediticioParaguay(situacionRiesgoCrediticioParaguay);
				
				riesgoCrediticioParaguay.setFact(hoy);
				riesgoCrediticioParaguay.setFcre(hoy);
				riesgoCrediticioParaguay.setTerm(Long.valueOf(1));
				riesgoCrediticioParaguay.setUact(Long.valueOf(1));
				riesgoCrediticioParaguay.setUcre(Long.valueOf(1));
				
				this.save(riesgoCrediticioParaguay);
				
				result = riesgoCrediticioParaguay;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	public RiesgoCrediticioParaguay save(RiesgoCrediticioParaguay riesgoCrediticioParaguay) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			riesgoCrediticioParaguay.setFact(date);
			
			if (riesgoCrediticioParaguay.getId() != null) {
				result = riesgoCrediticioParaguay = entityManager.merge(riesgoCrediticioParaguay);
			} else {
				riesgoCrediticioParaguay.setFcre(date);
				riesgoCrediticioParaguay.setUcre(riesgoCrediticioParaguay.getUact());
				
				entityManager.persist(riesgoCrediticioParaguay);
				
				result = riesgoCrediticioParaguay;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticioParaguay actualizarDatosRiesgoCrediticioParaguay(RiesgoCrediticioParaguay riesgoCrediticioParaguay) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioEnProceso = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.EnProceso"))
				);
			
			EstadoRiesgoCrediticio estadoRiesgoCrediticioProcesado = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.Procesado"))
				);
			
			TypedQuery<RiesgoCrediticioParaguay> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM RiesgoCrediticioParaguay r"
					+ " WHERE r.estadoRiesgoCrediticio.id = :estadoRiesgoCrediticioEnProcesoId"
					+ " AND r.documento = :documento"
					+ " AND r.fechaNacimiento = :fechaNacimiento"
					+ " AND r.situacionRiesgoCrediticioParaguay.id = :situacionRiesgoCrediticioParaguayId"
					+ " ORDER BY r.fact DESC",
					RiesgoCrediticioParaguay.class
				);
			query.setParameter(
				"estadoRiesgoCrediticioEnProcesoId", estadoRiesgoCrediticioEnProceso.getId()
			);
			query.setParameter("documento", riesgoCrediticioParaguay.getDocumento());
			query.setParameter("fechaNacimiento", riesgoCrediticioParaguay.getFechaNacimiento());
			query.setParameter(
				"situacionRiesgoCrediticioParaguayId", 
				riesgoCrediticioParaguay.getSituacionRiesgoCrediticioParaguay().getId()
			);
			query.setMaxResults(1);
			
			List<RiesgoCrediticioParaguay> resultList = query.getResultList();
			if (resultList.size() > 0) {
				RiesgoCrediticioParaguay riesgoCrediticioParaguayManaged = resultList.get(0);
				
				riesgoCrediticioParaguayManaged.setCondicion(riesgoCrediticioParaguay.getCondicion());
				riesgoCrediticioParaguayManaged.setMotivo(riesgoCrediticioParaguay.getMotivo());
				riesgoCrediticioParaguayManaged.setIpsDocumento(riesgoCrediticioParaguay.getIpsDocumento());
				riesgoCrediticioParaguayManaged.setIpsNombres(riesgoCrediticioParaguay.getIpsNombres());
				riesgoCrediticioParaguayManaged.setIpsApellidos(riesgoCrediticioParaguay.getIpsApellidos());
				riesgoCrediticioParaguayManaged.setIpsFnac(riesgoCrediticioParaguay.getIpsFnac());
				riesgoCrediticioParaguayManaged.setIpsSexo(riesgoCrediticioParaguay.getIpsSexo());
				riesgoCrediticioParaguayManaged.setIpsTipoAseg(riesgoCrediticioParaguay.getIpsTipoAseg());
				riesgoCrediticioParaguayManaged.setIpsEmpleador(riesgoCrediticioParaguay.getIpsEmpleador());
				riesgoCrediticioParaguayManaged.setIpsEstado(riesgoCrediticioParaguay.getIpsEstado());
				riesgoCrediticioParaguayManaged.setIpsMesesAporte(riesgoCrediticioParaguay.getIpsMesesAporte());
				riesgoCrediticioParaguayManaged.setIpsNuPatronal(riesgoCrediticioParaguay.getIpsNuPatronal());
				riesgoCrediticioParaguayManaged.setIpsUPA(riesgoCrediticioParaguay.getIpsUPA());
				riesgoCrediticioParaguayManaged.setRespuestaExterna(riesgoCrediticioParaguay.getRespuestaExterna());
				riesgoCrediticioParaguayManaged.setSetDocumento(riesgoCrediticioParaguay.getSetDocumento());
				riesgoCrediticioParaguayManaged.setSetDV(riesgoCrediticioParaguay.getSetDV());
				riesgoCrediticioParaguayManaged.setSetNombreCompleto(riesgoCrediticioParaguay.getSetNombreCompleto());
				riesgoCrediticioParaguayManaged.setSetEstado(riesgoCrediticioParaguay.getSetEstado());
				riesgoCrediticioParaguayManaged.setSetSituacion(riesgoCrediticioParaguay.getSetSituacion());
				riesgoCrediticioParaguayManaged.setSfpEntidad(riesgoCrediticioParaguay.getSfpEntidad());
				riesgoCrediticioParaguayManaged.setSfpCedula(riesgoCrediticioParaguay.getSfpCedula());
				riesgoCrediticioParaguayManaged.setSfpNombres(riesgoCrediticioParaguay.getSfpNombres());
				riesgoCrediticioParaguayManaged.setSfpApellidos(riesgoCrediticioParaguay.getSfpApellidos());
				riesgoCrediticioParaguayManaged.setSfpPresupuesto(riesgoCrediticioParaguay.getSfpPresupuesto());
				riesgoCrediticioParaguayManaged.setSfpFnac(riesgoCrediticioParaguay.getSfpFnac());
				
				riesgoCrediticioParaguayManaged.setEstadoRiesgoCrediticio(estadoRiesgoCrediticioProcesado);
				riesgoCrediticioParaguayManaged.setFechaVigenciaDesde(date);
				
				riesgoCrediticioParaguayManaged.setFact(date);
				riesgoCrediticioParaguayManaged.setTerm(Long.valueOf(1));
				riesgoCrediticioParaguayManaged.setUact(Long.valueOf(1));
				
				result = riesgoCrediticioParaguayManaged;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RiesgoCrediticioParaguay actualizarRespuestaExterna(Long id, String respuestaExterna) {
		RiesgoCrediticioParaguay result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			RiesgoCrediticioParaguay riesgoCrediticioParaguayManaged =
				entityManager.find(RiesgoCrediticioParaguay.class, id);
			
			riesgoCrediticioParaguayManaged.setRespuestaExterna(respuestaExterna);
			
			riesgoCrediticioParaguayManaged.setFact(date);
			riesgoCrediticioParaguayManaged.setTerm(Long.valueOf(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
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
				"Empresa"
				+ ";Documento"
				+ ";Fecha de Nacimiento"
				+ ";Situación"
				+ ";Estado"
				+ ";Vigencia"
				+ ";Creado"
				+ ";Modificado"
				+ ";condicion"
				+ ";motivo"
				+ ";ipsDocumento"
				+ ";ipsNombres"
				+ ";ipsApellidos"
				+ ";ipsFnac"
				+ ";ipsSexo"
				+ ";ipsTipoAseg"
				+ ";ipsEmpleador"
				+ ";ipsEstado"
				+ ";ipsMesesAporte"
				+ ";ipsNuPatronal"
				+ ";ipsUPA"
				+ ";setDocumento"
				+ ";setDV"
				+ ";setNombreCompleto"
				+ ";setEstado"
				+ ";setSituacion"
				+ ";sfpEntidad"
				+ ";sfpCedula"
				+ ";sfpNombres"
				+ ";sfpApellidos"
				+ ";sfpPresupuesto"
				+ ";sfpFnac"
);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				RiesgoCrediticioParaguay riesgoCrediticioParaguay = (RiesgoCrediticioParaguay) object;
				
				String line = 
					(riesgoCrediticioParaguay.getEmpresa() != null ?
						riesgoCrediticioParaguay.getEmpresa().getNombre()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getDocumento() != null ? 
						riesgoCrediticioParaguay.getDocumento()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getFechaNacimiento() != null ?
						format.format(riesgoCrediticioParaguay.getFechaNacimiento())
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSituacionRiesgoCrediticioParaguay() != null ? 
						riesgoCrediticioParaguay.getSituacionRiesgoCrediticioParaguay().getDescripcion()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getEstadoRiesgoCrediticio() != null ? 
						riesgoCrediticioParaguay.getEstadoRiesgoCrediticio().getNombre()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getFechaVigenciaDesde() != null ? 
						format.format(riesgoCrediticioParaguay.getFechaVigenciaDesde())
						: "")
					+ ";" + (riesgoCrediticioParaguay.getFcre() != null ? 
						format.format(riesgoCrediticioParaguay.getFcre())
						: "")
					+ ";" + (riesgoCrediticioParaguay.getFact() != null ? 
						format.format(riesgoCrediticioParaguay.getFact())
						: "")
					+ ";" + (riesgoCrediticioParaguay.getCondicion() != null ?
						riesgoCrediticioParaguay.getCondicion()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getMotivo() != null ?
						riesgoCrediticioParaguay.getMotivo()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsDocumento() != null ?
						riesgoCrediticioParaguay.getIpsDocumento()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsNombres() != null ?
						riesgoCrediticioParaguay.getIpsNombres()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsApellidos() != null ?
						riesgoCrediticioParaguay.getIpsApellidos()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsFnac() != null ?
						riesgoCrediticioParaguay.getIpsFnac()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsSexo() != null ?
						riesgoCrediticioParaguay.getIpsSexo()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsTipoAseg() != null ?
						riesgoCrediticioParaguay.getIpsTipoAseg()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsEmpleador() != null ?
						riesgoCrediticioParaguay.getIpsEmpleador()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsEstado() != null ?
						riesgoCrediticioParaguay.getIpsEstado()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsMesesAporte() != null ?
						riesgoCrediticioParaguay.getIpsMesesAporte()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsNuPatronal() != null ?
						riesgoCrediticioParaguay.getIpsNuPatronal()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getIpsUPA() != null ?
						riesgoCrediticioParaguay.getIpsUPA()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSetDocumento() != null ?
						riesgoCrediticioParaguay.getSetDocumento()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSetDV() != null ?
						riesgoCrediticioParaguay.getSetDV()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSetNombreCompleto() != null ?
						riesgoCrediticioParaguay.getSetNombreCompleto()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSetEstado() != null ?
						riesgoCrediticioParaguay.getSetEstado()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSetSituacion() != null ?
						riesgoCrediticioParaguay.getSetSituacion()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSfpEntidad() != null ?
						riesgoCrediticioParaguay.getSfpEntidad()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSfpCedula() != null ?
						riesgoCrediticioParaguay.getSfpCedula()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSfpNombres() != null ?
						riesgoCrediticioParaguay.getSfpNombres()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSfpApellidos() != null ?
						riesgoCrediticioParaguay.getSfpApellidos()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSfpPresupuesto() != null ?
						riesgoCrediticioParaguay.getSfpPresupuesto()
						: "")
					+ ";" + (riesgoCrediticioParaguay.getSfpFnac() != null ?
						riesgoCrediticioParaguay.getSfpFnac()
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
	
	public String preprocesarArchivoEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<Collection<String>> claves = new LinkedList<Collection<String>>();
			
			boolean errorFatal = false;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				try {
					Collection<String> clave = new LinkedList<String>();
					clave.add(fields[0].trim());
					clave.add(fields[1].trim());
					clave.add(fields[2].trim());
					
					claves.add(clave);
				} catch (Exception e) {
					errorFatal = true;
					
					e.printStackTrace();
					break;
				}
			}
			
			Map<Collection<String>, Integer> map = this.preprocesarConjunto(claves, empresaId);
			
			Long importar = Long.valueOf(0);
			Long sobreescribir = Long.valueOf(0);
			Long omitir = Long.valueOf(0);
			for (Entry<Collection<String>, Integer> entry : map.entrySet()) {
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
					"Se importarán " + importar + " registros nuevos.|"
					+ "Se sobreescribirán " + sobreescribir + " registros.|"
					+ "Se omitirán " + omitir + " registros.";
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
	
	public Map<Collection<String>, Integer> preprocesarConjunto(Collection<Collection<String>> claves, Long empresaId) {
		Map<Collection<String>, Integer> result = new HashMap<Collection<String>, Integer>();
		
		try {
			for (Collection<String> clave : claves) {
				if (clave.size() >= 3) {
					String[] arrayClave = clave.toArray(new String[] {});
					if (!arrayClave[0].isEmpty()
						&& !arrayClave[1].isEmpty()
						&& !arrayClave[2].isEmpty()) {
						result.put(clave, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
					} else {
						result.put(clave, Constants.__COMPROBACION_IMPORTACION_OMITIR);
					}
				} else {
					result.put(clave, Constants.__COMPROBACION_IMPORTACION_OMITIR);
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
		Long loggedUsuarioId
	) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoRiesgoCrediticio estado = 
				iEstadoRiesgoCrediticioBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar"))
				);
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId, false);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.RiesgoCrediticio"))
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
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores"))
				);
			
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
			
			Query insertRiesgoCrediticio = entityManager.createNativeQuery(
				"INSERT INTO riesgo_crediticio_paraguay("
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
					+ " documento,"
					+ " fecha_nacimiento,"
					+ " situacion_riesgo_crediticio_paraguay_id"
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
			
			SimpleDateFormat parser = new SimpleDateFormat("ddMMyyyy");
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				String[] fields = line.split(";", -1);
				
				if (fields.length < 3) {
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
					
					Date fechaNacimiento = null;
					try {
						fechaNacimiento = parser.parse(fields[1].trim());
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea" + lineNumber + " incompatible."
							+ " Campo fechaNacimiento incorrecto -> " + fields[1].trim());
						ok = false;
					}
					
					Long situacionRiesgoCrediticioParaguayId = null;
					try {
						situacionRiesgoCrediticioParaguayId = Long.decode(fields[2].trim());
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea" + lineNumber + " incompatible."
							+ " Campo situación incorrecto -> " + fields[2].trim());
						ok = false;
					}
					
					if (!ok) {
						errors++;
					} else {
						insertRiesgoCrediticio.setParameter(9, documento);
						insertRiesgoCrediticio.setParameter(10, fechaNacimiento);
						insertRiesgoCrediticio.setParameter(11, situacionRiesgoCrediticioParaguayId);
						
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
}