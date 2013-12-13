package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceListaNegraBean;
import uy.com.amensg.logistica.bean.IACMInterfaceListaNegraBean;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegraTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfaceListaNegraDWR {

	private IACMInterfaceListaNegraBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ACMInterfaceListaNegraBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceListaNegraBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceListaNegraBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfaceListaNegraBean iACMInterfaceListaNegraBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfaceListaNegraBean.list(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfaceListaNegra : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfaceListaNegra) acmInterfaceListaNegra));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ACMInterfaceListaNegraTO transform(ACMInterfaceListaNegra acmInterfaceListaNegra) {
		ACMInterfaceListaNegraTO acmInterfaceListaNegraTO = new ACMInterfaceListaNegraTO();
		
		acmInterfaceListaNegraTO.setMid(acmInterfaceListaNegra.getMid());
		acmInterfaceListaNegraTO.setObservaciones(acmInterfaceListaNegra.getObservaciones());
		
		acmInterfaceListaNegraTO.setFact(acmInterfaceListaNegra.getFact());
		acmInterfaceListaNegraTO.setTerm(acmInterfaceListaNegra.getTerm());
		acmInterfaceListaNegraTO.setUact(acmInterfaceListaNegra.getUact());
		
		return acmInterfaceListaNegraTO;
	}
}