package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(
	XmlAccessType.FIELD
)
@XmlType(
	name = "", 
	propOrder = {
		"response"
	}
)
@XmlRootElement(
	name = "NotifyResponse"
)
public class NotifyResponse implements Serializable {
	
	private static final long serialVersionUID = -2685836203658178265L;
	
	protected String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String value) {
		this.response = value;
	}
}