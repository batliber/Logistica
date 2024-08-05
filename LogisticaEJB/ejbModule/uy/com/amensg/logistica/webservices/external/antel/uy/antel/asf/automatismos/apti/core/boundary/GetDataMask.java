package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "", 
	propOrder = {
		"token"
	}
)
public class GetDataMask {

	protected String token;

	public String getToken() {
		return token;
	}

	public void setToken(String value) {
		this.token = value;
	}
}