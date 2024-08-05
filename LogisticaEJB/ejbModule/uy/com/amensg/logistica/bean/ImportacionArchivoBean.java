package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.EJB;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.FormatoImportacionArchivo;
import uy.com.amensg.logistica.entities.FormatoImportacionArchivoColumna;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@Stateless
public class ImportacionArchivoBean implements IImportacionArchivoBean, IImportacionArchivoBeanLocal {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@Resource
	private SessionContext sessionContext;
	
	@EJB
	private IFormatoImportacionArchivoBean iFormatoImportacionArchivoBean;
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	public String preprocesarArchivo(String fileName) {
		return null;
	}
	
	public Long procesarArchivo(String fileName, Long formatoImportacionArchivoId, Long loggedUsuarioId) {
		Long result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoProcesoImportacion estadoProcesoImportacionInicio = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.Inicio"))
				);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.Liquidaciones"))
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
			
			ProcesoImportacion procesoImportacionManaged = 
				iProcesoImportacionBean.save(procesoImportacion);
			
			result = procesoImportacionManaged.getId();
			
			sessionContext.getBusinessObject(IImportacionArchivoBeanLocal.class)
				.iniciarProcesamientoAsincrono(
					fileName, result, formatoImportacionArchivoId, loggedUsuarioId
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Asynchronous
	public void iniciarProcesamientoAsincrono(
		String fileName, Long procesoImportacionId, Long formatoImportacionArchivoId, Long loggedUsuarioId
	) {
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			FormatoImportacionArchivo formatoImportacionArchivo = 
				iFormatoImportacionArchivoBean.getById(formatoImportacionArchivoId);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoOK = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoOK"))
				);		
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoConErrores = 
				iEstadoProcesoImportacionBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores"))
				);
			
			ProcesoImportacion procesoImportacionManaged = 
				entityManager.find(ProcesoImportacion.class, procesoImportacionId);
			
			Query selectId = 
				entityManager.createNativeQuery(
					"SELECT nextval('hibernate_sequence')"
				);
			
			String insertQueryStringColumns = 
					" ucre,"
					+ " fcre,"
					+ " uact,"
					+ " fact,"
					+ " term,"
					
					+ " id";
			
			String insertQueryStringValues =
					" ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					
					+ " ?";
			
			List<FormatoImportacionArchivoColumna> toOrder = 
				new LinkedList<FormatoImportacionArchivoColumna>();
			toOrder.addAll(formatoImportacionArchivo.getFormatoImportacionArchivoColumnas());

			Collections.sort(toOrder, new Comparator<FormatoImportacionArchivoColumna>() {
				public int compare(
					FormatoImportacionArchivoColumna o1, FormatoImportacionArchivoColumna o2
				) {
					return o1.getOrden().compareTo(o2.getOrden());
				}							
			});
			
			for (FormatoImportacionArchivoColumna formatoImportacionArchivoColumna : toOrder) {
				insertQueryStringColumns +=
					", " + formatoImportacionArchivoColumna.getNombreColumnaDestino();
				
				insertQueryStringValues +=
					", ?";
			}
			
//			System.out.println(
//				"INSERT INTO " + formatoImportacionArchivo.getNombreTablaDestino() + " ("
//					+ insertQueryStringColumns
//				+ " ) VALUES ("
//					+ insertQueryStringValues
//				+ " )"
//			);
			
			Query insert = entityManager.createNativeQuery(
				"INSERT INTO " + formatoImportacionArchivo.getNombreTablaDestino() + " ("
					+ insertQueryStringColumns
				+ " ) VALUES ("
					+ insertQueryStringValues
				+ " )"
			);
			
			insert.setParameter(1, loggedUsuarioId);
			insert.setParameter(2, hoy);
			insert.setParameter(3, loggedUsuarioId);
			insert.setParameter(4, hoy);
			insert.setParameter(5, Long.valueOf(1));
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				if (lineNumber > formatoImportacionArchivo.getLineasEncabezado()) {
					String[] fields = line.split(formatoImportacionArchivo.getSeparadorColumnas(), -1);
					
					if (fields.length < formatoImportacionArchivo.getFormatoImportacionArchivoColumnas().size()) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Cantidad de columnas (" + fields.length + ") insuficientes."
						);
						errors++;
					} else {
						boolean ok = true;
						
						int fieldIndex = 0;
						for (FormatoImportacionArchivoColumna formatoImportacionArchivoColumna : toOrder) {
							// switch tipoDato
							
							fieldIndex++;
						}
						
						if (!ok) {
							errors++;
						} else {
//							select.setParameter(1, idField);
//							
//							List<?> list = select.list();
//							if (list.size() > 0) {
//								// Si el registro ya existe, omito.
//							} else {
								Long id = (Long) selectId.getResultList().get(0);
								
								insert.setParameter(6, id);
								
								int parameterNumber = 7;
								fieldIndex = 0;
								for (FormatoImportacionArchivoColumna formatoImportacionArchivoColumna : toOrder) {
									switch (formatoImportacionArchivoColumna.getTipoDato().getId().intValue()) {
										case Constants.__TIPO_DATO_INTEGER:
											insert.setParameter(
												parameterNumber,
												Long.valueOf(fields[fieldIndex])
											);
											
											break;
										case Constants.__TIPO_DATO_STRING:
											insert.setParameter(
												parameterNumber,
												fields[fieldIndex]
											);
											
											break;
										case Constants.__TIPO_DATO_DOUBLE_PRECISION:
											insert.setParameter(
												parameterNumber,
												Double.valueOf(fields[fieldIndex])
											);
											
											break;
										case Constants.__TIPO_DATO_TIMESTAMP:
											insert.setParameter(
												parameterNumber,
												simpleDateFormat.parse(fields[fieldIndex])
											);
											
											break;
									}
									
									parameterNumber++;
									fieldIndex++;
								}
								
								insert.executeUpdate();
//							}
							
							successful++;
							
//							transaction.commit();
							
							Thread.sleep(5000);
						}
					}
				}
			}
			
			if (errors > 0) {
				procesoImportacionManaged.setEstadoProcesoImportacion(
					estadoProcesoImportacionFinalizadoConErrores
				);
			} else {
				procesoImportacionManaged.setEstadoProcesoImportacion(
					estadoProcesoImportacionFinalizadoOK
				);
			}
			
			hoy = GregorianCalendar.getInstance().getTime();
			
			procesoImportacionManaged.setFact(hoy);
			procesoImportacionManaged.setFechaFin(hoy);
			procesoImportacionManaged.setObservaciones(
				"Líneas procesadas con éxito: " + successful + ".|"
				+ "Líneas con datos incorrectos: " + errors + "."
			);
			
			iProcesoImportacionBean.update(procesoImportacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String consultarEstadoImportacionArchivo(Long procesoImportacionId) {
		String result = null;
		
		try {
			ProcesoImportacion procesoImportacionManaged = 
				entityManager.find(ProcesoImportacion.class, procesoImportacionId);
			
			if (procesoImportacionManaged != null) {
				result = procesoImportacionManaged.getObservaciones();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}