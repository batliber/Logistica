package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceNumeroContratoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceNumeroContratoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContratoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfaceNumeroContratoDWR {

	private IACMInterfaceNumeroContratoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceNumeroContratoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceNumeroContratoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceNumeroContratoBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfaceNumeroContratoBean iACMInterfaceNumeroContratoBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfaceNumeroContratoBean.list(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfaceNumeroContrato : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfaceNumeroContrato) acmInterfaceNumeroContrato));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			IACMInterfaceNumeroContratoBean iACMInterfaceNumeroContratoBean = lookupBean();
			
			result = 
				iACMInterfaceNumeroContratoBean.count(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void reprocesar(MetadataConsultaTO metadataConsultaTO, String observaciones) {
		try {
			IACMInterfaceNumeroContratoBean iACMInterfaceNumeroContratoBean = lookupBean();
			
			iACMInterfaceNumeroContratoBean.reprocesar(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				observaciones
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ACMInterfaceNumeroContratoTO transform(ACMInterfaceNumeroContrato acmInterfaceNumeroContrato) {
		ACMInterfaceNumeroContratoTO acmInterfaceNumeroContratoTO = new ACMInterfaceNumeroContratoTO();
		
		if (acmInterfaceNumeroContrato.getEstado() != null) {
			acmInterfaceNumeroContratoTO.setEstado(ACMInterfaceEstadoDWR.transform(acmInterfaceNumeroContrato.getEstado()));
		}
		
		acmInterfaceNumeroContratoTO.setNumeroContrato(acmInterfaceNumeroContrato.getNumeroContrato());
		acmInterfaceNumeroContratoTO.setProcesoId(acmInterfaceNumeroContrato.getProcesoId());
		
		acmInterfaceNumeroContratoTO.setUact(acmInterfaceNumeroContrato.getUact());
		acmInterfaceNumeroContratoTO.setUcre(acmInterfaceNumeroContrato.getUcre());
		acmInterfaceNumeroContratoTO.setFact(acmInterfaceNumeroContrato.getFact());
		acmInterfaceNumeroContratoTO.setFcre(acmInterfaceNumeroContrato.getFcre());
		acmInterfaceNumeroContratoTO.setTerm(acmInterfaceNumeroContrato.getTerm());
		
		return acmInterfaceNumeroContratoTO;
	}
}