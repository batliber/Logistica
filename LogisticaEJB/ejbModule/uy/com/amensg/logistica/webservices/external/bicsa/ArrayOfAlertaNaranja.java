package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAlertaNaranja", propOrder = { "alertaNaranja" })
public class ArrayOfAlertaNaranja  implements Serializable {

	private static final long serialVersionUID = -1089346402044848539L;
	
	@XmlElement(name = "AlertaNaranja", nillable = true)
	protected List<AlertaNaranja> alertaNaranja;

	public List<AlertaNaranja> getAlertaNaranja() {
		if (alertaNaranja == null) {
			alertaNaranja = new ArrayList<AlertaNaranja>();
		}
		return this.alertaNaranja;
	}
}