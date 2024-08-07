package uy.com.amensg.riesgoCrediticio.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	namespace = "http://soap.webservices.web.logistica.amensg.com.uy/message",
	name = "getSiguienteDocumentoParaControlarRiesgoOnLineResponse", 
	propOrder = {
		"_return"
	}
)
public class GetSiguienteDocumentoParaControlarRiesgoOnLineResponse {

	@XmlElement(name = "return")
	protected String _return;

	public String getReturn() {
		return _return;
	}

	public void setReturn(String value) {
		this._return = value;
	}
}