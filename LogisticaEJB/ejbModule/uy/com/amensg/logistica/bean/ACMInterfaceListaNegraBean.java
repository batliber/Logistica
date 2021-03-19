package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ACMInterfaceListaNegraBean implements IACMInterfaceListaNegraBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public String exportarAExcel(MetadataConsulta metadataConsulta) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName =
				Configuration.getInstance().getProperty("exportacion.carpeta")
					+ gregorianCalendar.get(GregorianCalendar.YEAR)
					+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1)
					+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH)
					+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY)
					+ gregorianCalendar.get(GregorianCalendar.MINUTE)
					+ gregorianCalendar.get(GregorianCalendar.SECOND)
					+ ".csv";
					
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			for (Object object : this.list(metadataConsulta).getRegistrosMuestra()) {
				ACMInterfaceListaNegra acmInterfaceListaNegra = (ACMInterfaceListaNegra) object;
				
					printWriter.println(
					acmInterfaceListaNegra.getMid()
					+ ";" + (acmInterfaceListaNegra.getObservaciones() != null ?
						acmInterfaceListaNegra.getObservaciones()
						: "")
					+ ";" + (acmInterfaceListaNegra.getFact() != null ?
						format.format(acmInterfaceListaNegra.getFact())
						: "")
				);
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<ACMInterfaceListaNegra>().list(
				entityManager, metadataConsulta, new ACMInterfaceListaNegra()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ACMInterfaceListaNegra>().count(
				entityManager, metadataConsulta, new ACMInterfaceListaNegra()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}