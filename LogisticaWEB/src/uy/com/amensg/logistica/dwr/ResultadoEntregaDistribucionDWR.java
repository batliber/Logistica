package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IResultadoEntregaDistribucionBean;
import uy.com.amensg.logistica.bean.ResultadoEntregaDistribucionBean;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucionTO;

@RemoteProxy
public class ResultadoEntregaDistribucionDWR {

	private IResultadoEntregaDistribucionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ResultadoEntregaDistribucionBean.class.getSimpleName();
		String remoteInterfaceName = IResultadoEntregaDistribucionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IResultadoEntregaDistribucionBean) context.lookup(lookupName);
	}
	
	public Collection<ResultadoEntregaDistribucionTO> list() {
		Collection<ResultadoEntregaDistribucionTO> result = new LinkedList<ResultadoEntregaDistribucionTO>();
		
		try {
			IResultadoEntregaDistribucionBean iResultadoEntregaDistribucionBean = lookupBean();
			
			for (ResultadoEntregaDistribucion resultadoEntregaDistribucion : iResultadoEntregaDistribucionBean.list()) {
				result.add(transform(resultadoEntregaDistribucion));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ResultadoEntregaDistribucionTO transform(ResultadoEntregaDistribucion resultadoEntregaDistribucion) {
		ResultadoEntregaDistribucionTO result = new ResultadoEntregaDistribucionTO();
		
		result.setDescripcion(resultadoEntregaDistribucion.getDescripcion());
		
		result.setFcre(resultadoEntregaDistribucion.getFcre());
		result.setFact(resultadoEntregaDistribucion.getFact());
		result.setId(resultadoEntregaDistribucion.getId());
		result.setTerm(resultadoEntregaDistribucion.getTerm());
		result.setUact(resultadoEntregaDistribucion.getUact());
		result.setUcre(resultadoEntregaDistribucion.getUcre());
		
		return result;
	}
}