package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.CalculoPorcentajeActivacionPuntoVentaBean;
import uy.com.amensg.logistica.bean.ICalculoPorcentajeActivacionPuntoVentaBean;
import uy.com.amensg.logistica.entities.CalculoPorcentajeActivacionPuntoVenta;
import uy.com.amensg.logistica.entities.CalculoPorcentajeActivacionPuntoVentaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class CalculoPorcentajeActivacionPuntoVentaDWR {

	private ICalculoPorcentajeActivacionPuntoVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = CalculoPorcentajeActivacionPuntoVentaBean.class.getSimpleName();
		String remoteInterfaceName = ICalculoPorcentajeActivacionPuntoVentaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (ICalculoPorcentajeActivacionPuntoVentaBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ICalculoPorcentajeActivacionPuntoVentaBean iCalculoPorcentajeActivacionPuntoVentaBean = 
					lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iCalculoPorcentajeActivacionPuntoVentaBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object calculoPorcentajeActivacionPuntoVenta : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((CalculoPorcentajeActivacionPuntoVenta) calculoPorcentajeActivacionPuntoVenta));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ICalculoPorcentajeActivacionPuntoVentaBean iCalculoPorcentajeActivacionPuntoVentaBean = 
					lookupBean();
				
				result = 
					iCalculoPorcentajeActivacionPuntoVentaBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ICalculoPorcentajeActivacionPuntoVentaBean iCalculoPorcentajeActivacionPuntoVentaBean = lookupBean();
				
				result = 
					iCalculoPorcentajeActivacionPuntoVentaBean.exportarAExcel(
						MetadataConsultaDWR.transform(metadataConsultaTO), 
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static CalculoPorcentajeActivacionPuntoVentaTO transform(
		CalculoPorcentajeActivacionPuntoVenta calculoPorcentajeActivacionPuntoVenta
	) {
		CalculoPorcentajeActivacionPuntoVentaTO result = new CalculoPorcentajeActivacionPuntoVentaTO();
		
		result.setFechaCalculo(calculoPorcentajeActivacionPuntoVenta.getFechaCalculo());
		result.setFechaLiquidacion(calculoPorcentajeActivacionPuntoVenta.getFechaLiquidacion());
		result.setPorcentajeActivacion(calculoPorcentajeActivacionPuntoVenta.getPorcentajeActivacion());
		
		if (calculoPorcentajeActivacionPuntoVenta.getPuntoVenta() != null) {
			result.setPuntoVenta(PuntoVentaDWR.transform(calculoPorcentajeActivacionPuntoVenta.getPuntoVenta()));
		}
		
		result.setFcre(calculoPorcentajeActivacionPuntoVenta.getFcre());
		result.setFact(calculoPorcentajeActivacionPuntoVenta.getFact());
		result.setId(calculoPorcentajeActivacionPuntoVenta.getId());
		result.setTerm(calculoPorcentajeActivacionPuntoVenta.getTerm());
		result.setUact(calculoPorcentajeActivacionPuntoVenta.getUact());
		result.setUcre(calculoPorcentajeActivacionPuntoVenta.getUcre());
		
		return result;
	}
}