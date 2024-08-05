package uy.com.amensg.logistica.web.test.controleswebservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "getSiguienteMidParaControlarResponse",
	namespace = "http://webservices.logistica.amensg.com.uy/message",
	propOrder = {
		"_return"
	}
)
public class GetSiguienteMidParaControlarResponse {

    @XmlElement(name = "return")
    protected String _return;

    public String getReturn() {
        return _return;
    }

    public void setReturn(String value) {
        this._return = value;
    }
}