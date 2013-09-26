package uy.com.amensg.logistica.robot.util;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Configuration instance = null;
	
	private Properties properties = null;
	
	private Configuration() {
		try {
			properties = new Properties();
			
			properties.load(getClass().getClassLoader().getResourceAsStream("robot.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
}