package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfacePrepagoPHBean;
import uy.com.amensg.logistica.bean.IACMInterfacePrepagoPHBean;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.ACMInterfacePrepagoTO;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfacePrepagoPHDWR {

	private IACMInterfacePrepagoPHBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfacePrepagoPHBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfacePrepagoPHBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfacePrepagoPHBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfacePrepagoPHBean iACMInterfacePrepagoPHBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfacePrepagoPHBean.list(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfacePrepago : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfacePrepago) acmInterfacePrepago));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
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
				
				IACMInterfacePrepagoPHBean iACMInterfacePrepagoPHBean = lookupBean();
				
				result = iACMInterfacePrepagoPHBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String preprocesarExportacionByEmpresa(MetadataConsultaTO metadataConsultaTO, EmpresaTO empresaTO) {
		String result = null;
		
		try {
			IACMInterfacePrepagoPHBean iACMInterfacePrepagoPHBean = lookupBean();
			
			result = iACMInterfacePrepagoPHBean.preprocesarExportacion(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				EmpresaDWR.transform(empresaTO)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelByEmpresa(MetadataConsultaTO metadataConsultaTO, EmpresaTO empresaTO, String observaciones) {
		String result = null;
		
		try {
			IACMInterfacePrepagoPHBean iACMInterfacePrepagoPHBean = lookupBean();
			
			result = iACMInterfacePrepagoPHBean.exportarAExcel(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				EmpresaDWR.transform(empresaTO),
				observaciones
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void deshacerAsignacion(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfacePrepagoPHBean iACMInterfacePrepagoPHBean = lookupBean();
			
			iACMInterfacePrepagoPHBean.deshacerAsignacion(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ACMInterfacePrepagoTO transform(ACMInterfacePrepago acmInterfacePrepago) {
		ACMInterfacePrepagoTO acmInterfacePrepagoTO = new ACMInterfacePrepagoTO();
		
		acmInterfacePrepagoTO.setMesAno(acmInterfacePrepago.getMesAno());
		acmInterfacePrepagoTO.setMid(acmInterfacePrepago.getMid());
		acmInterfacePrepagoTO.setMontoMesActual(acmInterfacePrepago.getMontoMesActual());
		acmInterfacePrepagoTO.setMontoMesAnterior1(acmInterfacePrepago.getMontoMesAnterior1());
		acmInterfacePrepagoTO.setMontoMesAnterior2(acmInterfacePrepago.getMontoMesAnterior2());
		acmInterfacePrepagoTO.setMontoPromedio(acmInterfacePrepago.getMontoPromedio());
		acmInterfacePrepagoTO.setFechaExportacion(acmInterfacePrepago.getFechaExportacion());
		acmInterfacePrepagoTO.setFechaActivacionKit(acmInterfacePrepago.getFechaActivacionKit());
		
		acmInterfacePrepagoTO.setFact(acmInterfacePrepago.getFact());
		acmInterfacePrepagoTO.setUact(acmInterfacePrepago.getUact());
		acmInterfacePrepagoTO.setTerm(acmInterfacePrepago.getTerm());
		
		return acmInterfacePrepagoTO;
	}
}