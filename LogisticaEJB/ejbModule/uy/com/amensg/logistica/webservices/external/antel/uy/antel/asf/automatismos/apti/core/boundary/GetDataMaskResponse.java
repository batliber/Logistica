package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "", 
	propOrder = {
		"_return"
	}
)
public class GetDataMaskResponse {

	@XmlElement(name = "return")
	protected DataMask _return;

	public DataMask getReturn() {
		return _return;
	}

	public void setReturn(DataMask value) {
		this._return = value;
	}
}