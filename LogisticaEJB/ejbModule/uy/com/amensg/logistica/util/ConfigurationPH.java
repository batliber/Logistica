package uy.com.amensg.logistica.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ConfiguracionPHBean;
import uy.com.amensg.logistica.bean.IConfiguracionPHBean;

public class ConfigurationPH {

	private static ConfigurationPH instance = null;
		
	private ConfigurationPH() {
		
	}
	
	public static ConfigurationPH getInstance() {
		if (instance == null) {
			instance = new ConfigurationPH();
		}
		return instance;
	}
	
	public String getProperty(String name) {
		String result = null;
		
		try {
			IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
			
			result = iConfiguracionPHBean.getProperty(name);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IConfiguracionPHBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IConfiguracionPHBean.class.getName();
		String beanName = ConfiguracionPHBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IConfiguracionPHBean) context.lookup(lookupName);
	}
}