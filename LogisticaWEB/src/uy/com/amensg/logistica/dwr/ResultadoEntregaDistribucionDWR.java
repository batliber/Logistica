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
		String EARName = "Logistica";
		String beanName = ResultadoEntregaDistribucionBean.class.getSimpleName();
		String remoteInterfaceName = IResultadoEntregaDistribucionBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
		ResultadoEntregaDistribucionTO resultadoEntregaDistribucionTO = new ResultadoEntregaDistribucionTO();
		
		resultadoEntregaDistribucionTO.setDescripcion(resultadoEntregaDistribucion.getDescripcion());
		
		resultadoEntregaDistribucionTO.setFact(resultadoEntregaDistribucion.getFact());
		resultadoEntregaDistribucionTO.setId(resultadoEntregaDistribucion.getId());
		resultadoEntregaDistribucionTO.setTerm(resultadoEntregaDistribucion.getTerm());
		resultadoEntregaDistribucionTO.setUact(resultadoEntregaDistribucion.getUact());
		
		return resultadoEntregaDistribucionTO;
	}
}