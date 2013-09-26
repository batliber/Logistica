package uy.com.amensg.logistica.robot;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ACMInterfaceContratoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceContratoBean;
import uy.com.amensg.logistica.robot.util.Configuration;

public class LogisticaProxy {

	public LogisticaProxy() {
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			System.out.println(iACMInterfaceContratoBean.getSiguienteSinProcesar().getMid());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceContratoBean lookupBean() throws NamingException {
		String EARName = Configuration.getInstance().getProperty("EARName");
		String beanName = ACMInterfaceContratoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceContratoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		
		Properties properties = new Properties();
		properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + Configuration.getInstance().getProperty("providerURL"));

		Context context = new InitialContext(properties);
		
		return (IACMInterfaceContratoBean) context.lookup(lookupName);
	}
	
	public static void main(String[] args) {
		new LogisticaProxy();
	}
}