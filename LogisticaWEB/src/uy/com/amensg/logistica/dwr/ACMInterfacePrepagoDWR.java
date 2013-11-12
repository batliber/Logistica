package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfacePrepagoBean;
import uy.com.amensg.logistica.bean.IACMInterfacePrepagoBean;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.ACMInterfacePrepagoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfacePrepagoDWR {

	private IACMInterfacePrepagoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ACMInterfacePrepagoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfacePrepagoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfacePrepagoBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfacePrepagoBean.list(
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
		String result = null;
		
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			result = iACMInterfacePrepagoBean.exportarAExcel(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void reprocesar(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			iACMInterfacePrepagoBean.reprocesar(
				MetadataConsultaDWR.transform(
						metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deshacerAsignacion(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			iACMInterfacePrepagoBean.deshacerAsignacion(
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
		
		acmInterfacePrepagoTO.setFact(acmInterfacePrepago.getFact());
		acmInterfacePrepagoTO.setUact(acmInterfacePrepago.getUact());
		acmInterfacePrepagoTO.setTerm(acmInterfacePrepago.getTerm());
		
		return acmInterfacePrepagoTO;
	}
}