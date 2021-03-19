package uy.com.amensg.logistica.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ConfiguracionBean;
import uy.com.amensg.logistica.bean.IConfiguracionBean;

public class Configuration {

	private static Configuration instance = null;
		
	private Configuration() {
		
	}
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	public String getProperty(String name) {
		String result = null;
		
		try {
			IConfiguracionBean iConfiguracionBean = lookupBean();
			
			result = iConfiguracionBean.getProperty(name);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IConfiguracionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IConfiguracionBean.class.getName();
		String beanName = ConfiguracionBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IConfiguracionBean) context.lookup(lookupName);
	}
}