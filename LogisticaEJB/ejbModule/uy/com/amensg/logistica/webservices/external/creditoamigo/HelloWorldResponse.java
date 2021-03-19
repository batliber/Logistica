
package uy.com.amensg.logistica.webservices.external.creditoamigo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "", 
	propOrder = {
		"helloWorldResult"
	}
)
@XmlRootElement(
	name = "HelloWorldResponse"
)
public class HelloWorldResponse {

	@XmlElement(name = "HelloWorldResult")
	protected String helloWorldResult;

	public String getHelloWorldResult() {
		return helloWorldResult;
	}

	public void setHelloWorldResult(String value) {
		this.helloWorldResult = value;
	}
}