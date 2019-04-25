package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

@Remote
public interface IConfiguracionBean {

	public String getProperty(String clave);
}