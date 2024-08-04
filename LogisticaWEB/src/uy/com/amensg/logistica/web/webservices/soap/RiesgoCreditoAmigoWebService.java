package uy.com.amensg.logistica.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;

@WebService
public class RiesgoCreditoAmigoWebService {

	@WebMethod
	public void controlarRiesgo(String documento) {
		try {
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			iRiesgoCrediticioBean.controlarRiesgoBCU(documento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IRiesgoCrediticioBean lookupRiesgoCrediticioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}
}