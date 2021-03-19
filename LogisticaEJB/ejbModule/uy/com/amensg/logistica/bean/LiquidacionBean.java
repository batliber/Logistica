package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.Liquidacion;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class LiquidacionBean implements ILiquidacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IMonedaBean iMonedaBean;
	
	@EJB
	private IActivacionBean iActivacionBean;
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
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
			
			return new QueryBuilder<Liquidacion>().list(entityManager, metadataConsulta, new Liquidacion());
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
			
			result = new QueryBuilder<Liquidacion>().count(entityManager, metadataConsulta, new Liquidacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String preprocesarArchivo(String fileName) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<Long> ids = new LinkedList<Long>();
			
			boolean errorFatal = false;
			String line = null;
			boolean first = false;
			while ((line = bufferedReader.readLine()) != null) {
				if (first) {
					String[] fields = line.split(";");
					
					try {
						ids.add(Long.parseLong(fields[0].trim()));
					} catch (Exception e) {
						errorFatal = true;
						
						e.printStackTrace();
						break;
					}
				} else {
					first = true;
				}
			}
			
			Map<Long, Integer> map = this.preprocesarConjunto(ids);
			
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
			
			if (!errorFatal) {
				result =
					"Se importarán " + importar + " liquidaciones nuevas.|"
					+ "Se sobreescribirán " + sobreescribir + " liquidaciones.|"
					+ "Se omitirán " + omitir + " liquidaciones.";
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
	
	public Map<Long, Integer> preprocesarConjunto(Collection<Long> ids) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			for (Long id : ids) {
				result.put(id, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
			}
			
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT l.idRegistro"
					+ " FROM Liquidacion l"
					+ " WHERE l.idRegistro IN :ids",
					Long.class
				);
			
			Collection<Long> chunk = new LinkedList<Long>();
			for (Long id: ids) {
				chunk.add(id);
				
				if (chunk.size() == 1000) {
					query.setParameter("ids", chunk);
					
					for (Long registroId : query.getResultList()) {
						result.put(registroId, Constants.__COMPROBACION_IMPORTACION_OMITIR);
					}
					
					chunk.clear();
				}
			}
			
			if (chunk.size() > 0 && chunk.size() < 1000) {
				query.setParameter("ids", chunk);
				
				for (Long registroId : query.getResultList()) {
					result.put(registroId, Constants.__COMPROBACION_IMPORTACION_OMITIR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String procesarArchivo(String fileName, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
//			SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy"); 
			SimpleDateFormat formatFechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.Liquidaciones"))
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
			
			Long idConceptoPrepagoNoKitActivado =
				Long.parseLong(Configuration.getInstance().getProperty("Liquidacion.idConceptoPrepagoNoKitActivado"));
			
			Long monedaPesosUruguayosId = 
				Long.parseLong(Configuration.getInstance().getProperty("moneda.PesosUruguayos"));
			
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
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<Tuple> selectId = 
				hibernateSession.createNativeQuery(
					"SELECT nextval('hibernate_sequence')", Tuple.class
				);
			
			NativeQuery<?> insertLiquidacion = hibernateSession.createNativeQuery(
				"INSERT INTO liquidacion("
					+ " ucre,"
					+ " fcre,"
					+ " uact,"
					+ " fact,"
					+ " term,"
					
					+ " id,"
//					+ " id_registro,"
					+ " empresa_id,"
//					+ " rlid,"
//					+ " numero_contrato,"
//					+ " fecha, "
					+ " plan,"
					+ " mid,"
//					+ " said,"
//					+ " serie,"
//					+ " dc,"
					+ " moneda_id,"
					+ " importe,"
//					+ " cant,"
					+ " fecha_liquidacion,"
					+ " id_concepto,"
					+ " nombre_concepto"
//					+ " modelo,"
//					+ " fabricante,"
//					+ " id_clase_concepto,"
//					+ " nom_clase_concepto"
				+ " ) VALUES ("
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					
					+ " ?,"
//					+ " ?,"
					+ " ?,"
//					+ " ?,"
//					+ " ?,"
//					+ " ?,"
					+ " ?,"
					+ " ?,"
//					+ " ?,"
//					+ " ?,"
//					+ " ?,"
					+ " ?,"
					+ " ?,"
//					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
//					+ " ?,"
//					+ " ?,"
//					+ " ?,"
//					+ " ?"
				+ " )"
			);
			
			insertLiquidacion.setParameter(1, loggedUsuarioId, LongType.INSTANCE);
			insertLiquidacion.setParameter(2, hoy, TimestampType.INSTANCE);
			insertLiquidacion.setParameter(3, loggedUsuarioId, LongType.INSTANCE);
			insertLiquidacion.setParameter(4, hoy, TimestampType.INSTANCE);
			insertLiquidacion.setParameter(5, Long.valueOf(1), LongType.INSTANCE);
			
			NativeQuery<Tuple> selectLiquidacion = hibernateSession.createNativeQuery(
//				"SELECT id"
//				+ " FROM liquidacion"
//				+ " WHERE id_registro = ?",
				"SELECT id"
				+ " FROM liquidacion"
				+ " WHERE empresa_id = ?"
				+ " AND mid = ?"
				+ " AND fecha_liquidacion = ?",
				Tuple.class
			);
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				if (lineNumber > 1) {
					String[] fields = line.split(";", -1);
					
					//if (fields.length < 22) {
					if (fields.length < 10) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Cantidad de columnas (" + fields.length + ") insuficientes."
						);
						errors++;
					} else {
						boolean ok = true;
						
						int field = 0;
						
						Long nroProveedor = null;
						try {
							nroProveedor = Long.parseLong(fields[field++].trim());
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo id_registro incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
//						Long idRegistro = null;
//						try {
//							idRegistro = Long.parseLong(fields[field++].trim());
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo id_registro incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
						Long idAgente = null;
						Long empresaId = null;
						try {
							idAgente = Long.parseLong(fields[field++].trim());
							empresaId = iEmpresaBean.getByIdAgente(idAgente, false).getId();
						} catch (Exception pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo id_agente incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						String nomAgente = null;
						try {
							nomAgente = fields[field++].trim();
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo nom_agente incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
//						Long rlid = null;
//						try {
//							if (fields[field].trim() != null && !fields[field].trim().isEmpty()) {
//								rlid = Long.parseLong(fields[field].trim());
//							}
//							field++;
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo rlid incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
//						Long contrato = null;
//						try {
//							contrato = Long.parseLong(fields[field++].trim());
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo contrato incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
//						Date fecha = null;
//						try {
//							fecha = formatFecha.parse(fields[field++].trim());
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo fecha incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
						String plan = null;
						try {
							plan = fields[field++].trim();
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo plan incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						String planOrigen = null;
						try {
							planOrigen = fields[field++].trim();
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo plan origen incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						Long mid = null;
						try {
							if (fields[field] != null && !fields[field].isEmpty()) {
								mid = Long.parseLong(fields[field].trim());
							}
							field++;
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo mid incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
//						Long said = null;
//						try {
//							if (fields[field] != null && !fields[field].isEmpty()) {
//								said = Long.parseLong(fields[field].trim());
//							}
//							field++;
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo said incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
//						String serie = null;
//						try {
//							serie = fields[field++].trim();
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo serie incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
//						String dc = null;
//						try {
//							dc = fields[field++].trim();
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo dc incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
						String moneda = null;
						Long monedaId = monedaPesosUruguayosId;
						try {
							if (fields[field] != null && !fields[field].isEmpty()) {
								moneda = fields[field].trim();
								Moneda monedaManaged = iMonedaBean.getBySimbolo(moneda);
								if (monedaManaged != null) {
									monedaId = monedaManaged.getId();
								}
							}
							field++;
						} catch (Exception pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo moneda incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						Double importe = null;
						try {
							if (fields[field] != null && !fields[field].trim().isEmpty()) {
								String importeString = fields[field].trim();
								importeString = importeString.replace(".", "");
								importeString = importeString.replace(",", ".");
								
								importe = Double.valueOf(importeString);
							}
							field++;
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo importe incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
//						Long cant = null;
//						try {
//							cant = Long.parseLong(fields[field++].trim());
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo cant incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
						String fechaLiquidacionString = null;
						try {
							fechaLiquidacionString = fields[field++].trim();
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fecha_liquidacion incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						String horaLiquidacionString = null;
						try {
							horaLiquidacionString = fields[field++].trim().replace(".", "");
						} catch (Exception pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo hora_liquidacion incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						Date fechaHoraLiquidacion = null;
						try {
							if (horaLiquidacionString != null && horaLiquidacionString.length() == 11) { 
								fechaHoraLiquidacion = formatFechaHora.parse(fechaLiquidacionString + " " + horaLiquidacionString);
							} else {
								fechaHoraLiquidacion = formatFechaHora.parse(fechaLiquidacionString + " 00:00:00");
							}
						} catch (ParseException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fecha_liquidacion/hora_liquidacion incorrectos -> " + fechaLiquidacionString + " " + horaLiquidacionString);
							ok = false;
						}
						
						Long idConcepto = null;
						try {
							idConcepto = Long.parseLong(fields[field++].trim());
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo id_concepto incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
						String nomConcepto = null;
						try {
							nomConcepto = fields[field++].trim();
						} catch (NumberFormatException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo nom_concepto incorrecto -> " + fields[field].trim());
							ok = false;
						}
						
//						String modelo = null;
//						try {
//							modelo = fields[field++].trim();
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo modelo incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
//						
//						String fabricante = null;
//						try {
//							fabricante = fields[field++].trim();
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo fabricante incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
//						
//						Long idClaseConcepto = null;
//						try {
//							idClaseConcepto = Long.parseLong(fields[20].trim());
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo id_clase_concepto incorrecto -> " + fields[20].trim());
//							ok = false;
//						}
//						
//						String nomClaseConcepto = null;
//						try {
//							nomClaseConcepto = fields[field++].trim();
//						} catch (NumberFormatException pe) {
//							System.err.println(
//								"Error al procesar archivo: " + fileName + "."
//								+ " Formato de línea " + lineNumber + " incompatible."
//								+ " Campo nom_clase_concepto incorrecto -> " + fields[field].trim());
//							ok = false;
//						}
						
						if (!ok) {
							errors++;
						} else {
//							selectLiquidacion.setParameter(1, idRegistro, LongType.INSTANCE);
							selectLiquidacion.setParameter(1, empresaId, LongType.INSTANCE);
							selectLiquidacion.setParameter(2, mid, LongType.INSTANCE);
							selectLiquidacion.setParameter(3, fechaHoraLiquidacion, TimestampType.INSTANCE);
							
							List<Tuple> listLiquidacion = selectLiquidacion.list();
							if (listLiquidacion.size() > 0) {
								// Si ya existe, omito.
							} else {
								Long id = ((BigInteger) selectId.list().get(0).get(0)).longValue();
								
								field = 6;
								insertLiquidacion.setParameter(field++, id, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, idRegistro, LongType.INSTANCE);
								insertLiquidacion.setParameter(field++, empresaId, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, rlid, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, contrato, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, fecha, TimestampType.INSTANCE);
								insertLiquidacion.setParameter(field++, plan, StringType.INSTANCE);
								insertLiquidacion.setParameter(field++, mid, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, said, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, serie, StringType.INSTANCE);
//								insertLiquidacion.setParameter(field++, dc, StringType.INSTANCE);
								insertLiquidacion.setParameter(field++, monedaId, LongType.INSTANCE);
								insertLiquidacion.setParameter(field++, importe, DoubleType.INSTANCE);
//								insertLiquidacion.setParameter(field++, cant, LongType.INSTANCE);
								insertLiquidacion.setParameter(field++, fechaHoraLiquidacion, TimestampType.INSTANCE);
								insertLiquidacion.setParameter(field++, idConcepto, LongType.INSTANCE);
								insertLiquidacion.setParameter(field++, nomConcepto, StringType.INSTANCE);
//								insertLiquidacion.setParameter(field++, modelo, StringType.INSTANCE);
//								insertLiquidacion.setParameter(field++, fabricante, StringType.INSTANCE);
//								insertLiquidacion.setParameter(field++, idClaseConcepto, LongType.INSTANCE);
//								insertLiquidacion.setParameter(field++, nomClaseConcepto, StringType.INSTANCE);
								
								insertLiquidacion.executeUpdate();
								
								if (idConcepto.equals(idConceptoPrepagoNoKitActivado)) {
									Activacion activacion = iActivacionBean.getLastByEmpresaIdMid(empresaId, mid);
									
									if (activacion != null) {
										Liquidacion liquidacion = new Liquidacion();
										liquidacion.setId(id);
										
										activacion.setLiquidacion(liquidacion);
										activacion.setUact(loggedUsuarioId);
										activacion.setFact(hoy);
										
										iActivacionBean.update(activacion);
									}
								}
							}
							
							successful++;
						}
					}
				}
			}
			
			result = 
				"Líneas procesadas con éxito: " + successful + ".|"
				+ "Líneas con datos incorrectos: " + errors + ".";
			
			this.calcularPorcentajeActivacionPuntoVentas(loggedUsuarioId);
			this.calcularPorcentajeActivacionSubLotes(loggedUsuarioId);
			
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
	
	public void calcularPorcentajeActivacionPuntoVentas(Long loggedUsuarioId) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<?> insertCalculoPorcentajeActivacionPuntoVenta = hibernateSession.createNativeQuery(
				"INSERT INTO calculo_porcentaje_activacion_punto_venta("
					+ " id,"
					+ " fecha_calculo,"
					+ " fecha_liquidacion,"
					+ " porcentaje_activacion,"
					+ " punto_venta_id,"
					+ " ucre,"
					+ " fcre,"
					+ " uact,"
					+ " fact,"
					+ " term)"
				+ "SELECT nextval('hibernate_sequence'),"
					+ " ?,"
					+ " l.fecha_liquidacion,"
					+ " (l.cantidad * 1.0) / t.cantidad,"
					+ " pv.id,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ " FROM punto_venta pv"
				+ " INNER JOIN ("
					+ " SELECT asl.punto_venta_id, COUNT(0) AS cantidad"
					+ " FROM activacion_sublote_activacion asla"
					+ " INNER JOIN activacion_sublote asl ON asl.id = asla.activacion_sublote_id"
					+ " GROUP BY asl.punto_venta_id"
				+ " ) t ON t.punto_venta_id = pv.id"
				+ " INNER JOIN ("
					+ " SELECT asl.punto_venta_id, l.fecha_liquidacion, COUNT(0) AS cantidad"
					+ " FROM activacion a"
					+ " INNER JOIN activacion_sublote_activacion asla ON asla.activacion_id = a.id"
					+ " INNER JOIN activacion_sublote asl ON asl.id = asla.activacion_sublote_id"
					+ " INNER JOIN liquidacion l ON l.id = a.liquidacion_id"
					+ " GROUP BY asl.punto_venta_id, l.fecha_liquidacion"
				+ " ) l ON l.punto_venta_id = pv.id"
			);
			
			insertCalculoPorcentajeActivacionPuntoVenta.setParameter(1, hoy, TimestampType.INSTANCE);
			insertCalculoPorcentajeActivacionPuntoVenta.setParameter(2, loggedUsuarioId, LongType.INSTANCE);
			insertCalculoPorcentajeActivacionPuntoVenta.setParameter(3, hoy, TimestampType.INSTANCE);
			insertCalculoPorcentajeActivacionPuntoVenta.setParameter(4, loggedUsuarioId, LongType.INSTANCE);
			insertCalculoPorcentajeActivacionPuntoVenta.setParameter(5, hoy, TimestampType.INSTANCE);
			insertCalculoPorcentajeActivacionPuntoVenta.setParameter(6, Long.valueOf(1), LongType.INSTANCE);
			
			insertCalculoPorcentajeActivacionPuntoVenta.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calcularPorcentajeActivacionSubLotes(Long loggedUsuarioId) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<?> updatePorcentajeActivacionSubLote = hibernateSession.createNativeQuery(
				"UPDATE activacion_sublote"
				+ " SET porcentaje_activacion = COALESCE(l.cantidad, 0) * 1.0 / t.cantidad,"
				+ " fact = ?,"
				+ " uact = ?"
				+ " FROM ("
					+ " SELECT asla.activacion_sublote_id, COUNT(0) AS cantidad"
					+ " FROM activacion_sublote_activacion asla"
					+ " INNER JOIN activacion_sublote asl ON asl.id = asla.activacion_sublote_id"
					+ " WHERE asl.punto_venta_id IS NOT NULL"
					+ " GROUP BY asla.activacion_sublote_id"
				+ " ) t"
				+ " LEFT OUTER JOIN ("
					+ " SELECT asla.activacion_sublote_id, COUNT(0) AS cantidad"
					+ " FROM activacion_sublote_activacion asla"
					+ " INNER JOIN activacion_sublote asl ON asl.id = asla.activacion_sublote_id"
					+ " INNER JOIN activacion a ON a.id = asla.activacion_id"
					+ " WHERE asl.punto_venta_id IS NOT NULL"
					+ " AND a.liquidacion_id IS NOT NULL"
					+ " GROUP BY asla.activacion_sublote_id"
				+ " ) l ON l.activacion_sublote_id = t.activacion_sublote_id"
				+ " WHERE activacion_sublote.id = t.activacion_sublote_id"
			);
			updatePorcentajeActivacionSubLote.setParameter(1, hoy, TimestampType.INSTANCE);
			updatePorcentajeActivacionSubLote.setParameter(2, loggedUsuarioId, LongType.INSTANCE);
			
			updatePorcentajeActivacionSubLote.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}