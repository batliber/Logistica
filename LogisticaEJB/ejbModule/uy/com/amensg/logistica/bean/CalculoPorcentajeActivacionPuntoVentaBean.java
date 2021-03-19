package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.CalculoPorcentajeActivacionPuntoVenta;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class CalculoPorcentajeActivacionPuntoVentaBean implements ICalculoPorcentajeActivacionPuntoVentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			result = 
				new QueryBuilder<CalculoPorcentajeActivacionPuntoVenta>().list(
					entityManager, 
					metadataConsulta, 
					new CalculoPorcentajeActivacionPuntoVenta()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = 
				new QueryBuilder<CalculoPorcentajeActivacionPuntoVenta>().count(
					entityManager, 
					metadataConsulta, 
					new CalculoPorcentajeActivacionPuntoVenta()
				);
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
				"Punto de venta"
				+ ";Departamento"
				+ ";Barrio"
				+ ";Estado"
				+ ";Porcentaje de activación"
				+ ";Fecha de cálculo de porcentaje de activación"
				+ ";Fecha de liquidación"
				+ ";Distribuidor"
				+ ";Fecha de asignación"
				+ ";Fecha de visita"
				+ ";Estado de la visita"
				+ ";Fecha de último cambio de estado de la visita"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta).getRegistrosMuestra()) {
				CalculoPorcentajeActivacionPuntoVenta calculoPorcentajeActivacionPuntoVenta = 
					(CalculoPorcentajeActivacionPuntoVenta) object;
				
				String line = 
					(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null ? 
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getNombre() 
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null 
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getDepartamento() != null ? 
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getDepartamento().getNombre() 
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null 
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getBarrio() != null ?
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getBarrio().getNombre()
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getEstadoPuntoVenta() != null ?
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getEstadoPuntoVenta().getNombre()
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPorcentajeActivacion() != null ?
						calculoPorcentajeActivacionPuntoVenta.getPorcentajeActivacion()
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getFechaCalculo() != null ?
						dateFormat.format(calculoPorcentajeActivacionPuntoVenta.getFechaCalculo())
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getFechaLiquidacion() != null ?
						dateFormat.format(calculoPorcentajeActivacionPuntoVenta.getFechaLiquidacion())
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getDistribuidor() != null ?
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getDistribuidor().getNombre()
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getFechaAsignacionDistribuidor() != null ?
						dateFormat.format(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getFechaAsignacionDistribuidor())
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getFechaVisitaDistribuidor() != null ?
						dateFormat.format(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getFechaVisitaDistribuidor())
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getEstadoVisitaPuntoVentaDistribuidor() != null ? 
						calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getEstadoVisitaPuntoVentaDistribuidor().getNombre()
						: "")
					+ ";" + (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null
						&& calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor() != null ? 
						dateFormat.format(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta().getFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor()) 
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