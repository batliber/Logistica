package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
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
import uy.com.amensg.logistica.entities.ContratoURSEC;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ContratoURSECBean implements IContratoURSECBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IEstadoBean iEstadoBean;
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<ContratoURSEC>().list(entityManager, metadataConsulta, new ContratoURSEC());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ContratoURSEC>().count(entityManager, metadataConsulta, new ContratoURSEC());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Procesa un conjunto de mids como URSEC.
	 * 
	 * @param mids Colección de números de trámite a procesar.
	 * @return Map indicando para cada número de trámite si se importará, sobreescribirá u omitirá.
	 */
	public Map<Long, Integer> preprocesarConjuntoNoLlamar(Collection<Long> mids) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT cu.mid"
					+ " FROM ContratoURSEC cu"
					+ " WHERE cu.mid IN :mids",
					Long.class
				);
			query.setParameter("mids", mids);
			
			for (Long nro : query.getResultList()) {
				result.put(nro, Constants.__COMPROBACION_IMPORTACION_OMITIR);
			}
			
			for (Long mid : mids) {
				if (!result.containsKey(mid)) {
					result.put(mid, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Procesa el archivo .csv de nombre fileName para los MIDs a marcar como URSEC.
	 * 
	 * @param fileName El nombre del archivo a Importar.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String que informa cuántas ventas se importarán, cuántas se sobreescribirán y cuántas se omitirán. 
	 */
	public String preprocesarArchivoURSEC(String fileName, Long loggedUsuarioId) {
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
			
			Map<Long, Integer> map = this.preprocesarConjuntoNoLlamar(mids);
			
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
	 * Actualiza todas las ventas para los MIDs del listado de todas las empresas como URSEC.
	 *  
	 * @param fileName El nombre del archivo a Importar.
	 * @param observaciones Observaciones a registrar.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String con el resultado de la operación.
	 */
	public String procesarArchivoURSEC(String fileName, String observaciones, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Rol rolGerenteEmpresa = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.GerenteDeEmpresa")),
					false
				);
			
			Estado estado = 
				iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.URSEC")));
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.URSEC"))
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
			
			Query selectContratoURSEC = entityManager.createNativeQuery(
				"SELECT *"
				+ " FROM contrato_ursec"
				+ " WHERE mid = ?"
			);
			
			Query insertContratoURSEC = entityManager.createNativeQuery(
				"INSERT INTO contrato_ursec ("
					+ " id,"
					+ " mid,"
					+ " ucre,"
					+ " fcre,"
					+ " uact,"
					+ " fact,"
					+ " term"
				+ " )"
				+ " VALUES ("
					+ " nextval('hibernate_sequence'),"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ ")"
			);
			insertContratoURSEC.setParameter(2, loggedUsuarioId);
			insertContratoURSEC.setParameter(3, hoy);
			insertContratoURSEC.setParameter(4, loggedUsuarioId);
			insertContratoURSEC.setParameter(5, hoy);
			insertContratoURSEC.setParameter(6, Long.valueOf(1));
			
			Query updateContrato = entityManager.createNativeQuery(
				"UPDATE contrato"
				+ " SET estado_id = ?, uact = ?, fact = ?, term = ?, observaciones = ?, rol_id = ?, usuario_id = null"
				+ " WHERE mid = ?"
				+ " AND fecha_venta IS NULL"
			);
			
			updateContrato.setParameter(1, estado.getId());
			updateContrato.setParameter(2, loggedUsuarioId);
			updateContrato.setParameter(3, hoy);
			updateContrato.setParameter(4, Long.valueOf(1));
			updateContrato.setParameter(5, observaciones);
			updateContrato.setParameter(6, rolGerenteEmpresa.getId());
			
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
				+ " LEFT OUTER JOIN contrato_routing_history crh ON crh.contrato_id = c.id"
				+ " WHERE c.fact = ?"
				+ " AND crh.id IS NULL"
			);
			insertContratoRoutingHistory.setParameter(1, hoy);
			
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
						
						Long mid = (fields[0] != null && !fields[0].equals("")) ? Long.parseLong(fields[0].trim()) : null;
						
						if (!ok) {
							errors++;
						} else {
							// Busco si el mid ya existe.
							selectContratoURSEC.setParameter(1, mid);
							
							List<?> listContratoURSEC = selectContratoURSEC.getResultList();
							if (listContratoURSEC.size() > 0) {
								// Si el mid existe, omito.
							} else {
//								Long id = (Long) ((Integer) selectId.getResultList().get(0)).longValue();
								
								// Si no, inserto el contrato_no_llamar.
								
								insertContratoURSEC.setParameter(1, mid);
								insertContratoURSEC.executeUpdate();
							}
							
							successful++;
						}
					}
				}
			}
			
			// Ruteo los contratos recién creados.
			insertContratoRoutingHistory.executeUpdate();
			
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