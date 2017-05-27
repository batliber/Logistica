package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.BCUInterfaceRiesgoCrediticioInstitucionFinancieraBean;
import uy.com.amensg.logistica.bean.IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO;

@RemoteProxy
public class BCUInterfaceRiesgoCrediticioInstitucionFinancieraDWR {

	private IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.class.getName();
		String beanName = BCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean) context.lookup(lookupName);
	}
	
	public Collection<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO> listByBCUInterfaceRiesgoCrediticioId(Long id) {
		Collection<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO> result = new LinkedList<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO>();
		
		try {
			IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean = lookupBean();
			
			for (BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera : 
				iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.listByBCUInterfaceRiesgoCrediticioId(id)) {
				result.add(transform((BCUInterfaceRiesgoCrediticioInstitucionFinanciera) bcuInterfaceRiesgoCrediticioInstitucionFinanciera));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO transform(
		BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera
	) {
		BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO result = new BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO();
		
		result.setCalificacion(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getCalificacion());
		result.setContingencias(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getContingencias());
		result.setDocumento(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getDocumento());
		result.setFechaAnalisis(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getFechaAnalisis());
		result.setInstitucionFinanciera(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getInstitucionFinanciera());
		result.setPrevisionesTotales(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getPrevisionesTotales());
		result.setVigente(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getVigente());
		result.setVigenteNoAutoliquidable(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getVigenteNoAutoliquidable());
		
		if (bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getEmpresa()));
		}
		
		result.setFact(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getFact());
		result.setId(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getId());
		result.setTerm(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getTerm());
		result.setUact(bcuInterfaceRiesgoCrediticioInstitucionFinanciera.getUact());
		
		return result;
	}
}