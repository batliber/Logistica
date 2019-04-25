package uy.com.amensg.logistica.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSiguienteMidSinProcesarResponse", propOrder = {
    "_return"
})
public class GetSiguienteMidSinProcesarResponse {
	
	@XmlElement(name = "return")
    protected String _return;

	public String getReturn() {
		return _return;
	}

	public void setReturn(String _return) {
		this._return = _return;
	}
}