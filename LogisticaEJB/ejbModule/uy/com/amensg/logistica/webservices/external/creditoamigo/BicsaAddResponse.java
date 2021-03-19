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
		"bicsaAddResult"
	}
)
@XmlRootElement(name = "BicsaAddResponse")
public class BicsaAddResponse {

	@XmlElement(name = "BicsaAddResult")
	protected String bicsaAddResult;

	public String getBicsaAddResult() {
		return bicsaAddResult;
	}

	public void setBicsaAddResult(String value) {
		this.bicsaAddResult = value;
	}
}