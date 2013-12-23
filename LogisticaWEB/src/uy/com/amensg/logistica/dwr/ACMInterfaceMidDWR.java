package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceMidBean;
import uy.com.amensg.logistica.bean.IACMInterfaceMidBean;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceMidTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfaceMidDWR {

	private IACMInterfaceMidBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ACMInterfaceMidBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceMidBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceMidBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listEnProceso(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfaceMidBean.listEnProceso(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfaceMid : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfaceMid) acmInterfaceMid));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void reprocesarEnProceso(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
			
			iACMInterfaceMidBean.reprocesarEnProceso(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reprocesar(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfaceMidBean iACMInterfaceMidBean = lookupBean();
			
			iACMInterfaceMidBean.reprocesar(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ACMInterfaceMidTO transform(ACMInterfaceMid acmInterfaceMid) {
		ACMInterfaceMidTO acmInterfaceMidTO = new ACMInterfaceMidTO();
		
		acmInterfaceMidTO.setEstado(acmInterfaceMid.getEstado());
		acmInterfaceMidTO.setMid(acmInterfaceMid.getMid());
		acmInterfaceMidTO.setProcesoId(acmInterfaceMid.getProcesoId());
		
		acmInterfaceMidTO.setUact(acmInterfaceMid.getUact());
		acmInterfaceMidTO.setFact(acmInterfaceMid.getFact());
		acmInterfaceMidTO.setTerm(acmInterfaceMid.getTerm());
		
		return acmInterfaceMidTO;
	}
}