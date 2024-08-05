package uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(
	XmlAccessType.FIELD
)
@XmlType(
	name = "", 
	propOrder = {
		"xml"
	}
)
@XmlRootElement(name = "GTRACMG.ExecuteResponse")
public class GTRACMGExecuteResponse {

	@XmlElement(name = "Xml", required = true)
	protected String xml;

	public String getXml() {
		return xml;
	}

	public void setXml(String value) {
		this.xml = value;
	}
}