package uy.com.amensg.logistica.dwr;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.util.Configuration;

@RemoteProxy
public class ConfigurationDWR {

	public String getProperty(String name) {
		return Configuration.getInstance().getProperty(name);
	}
}