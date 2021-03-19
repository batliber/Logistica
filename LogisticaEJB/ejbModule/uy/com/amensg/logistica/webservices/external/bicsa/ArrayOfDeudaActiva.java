package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDeudaActiva", propOrder = { "deudaActiva" })
public class ArrayOfDeudaActiva implements Serializable {

	private static final long serialVersionUID = 8233472964852692585L;
	
	@XmlElement(name = "DeudaActiva", nillable = true)
	protected List<DeudaActiva> deudaActiva;

	public List<DeudaActiva> getDeudaActiva() {
		if (deudaActiva == null) {
			deudaActiva = new ArrayList<DeudaActiva>();
		}
		return this.deudaActiva;
	}
}