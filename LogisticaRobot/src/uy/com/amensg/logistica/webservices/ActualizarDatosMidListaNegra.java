package uy.com.amensg.logistica.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "actualizarDatosMidListaNegra", 
	propOrder = { 
		"arg0"
	}
)
public class ActualizarDatosMidListaNegra {

	protected String arg0;

	public String getArg0() {
		return arg0;
	}

	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}
}