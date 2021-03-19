package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTrabajoDeLaPersona", propOrder = { "trabajoDeLaPersona" })
public class ArrayOfTrabajoDeLaPersona implements Serializable {

	private static final long serialVersionUID = 544748783838918244L;
	
	@XmlElement(name = "TrabajoDeLaPersona", nillable = true)
	protected List<TrabajoDeLaPersona> trabajoDeLaPersona;

	public List<TrabajoDeLaPersona> getTrabajoDeLaPersona() {
		if (trabajoDeLaPersona == null) {
			trabajoDeLaPersona = new ArrayList<TrabajoDeLaPersona>();
		}
		return this.trabajoDeLaPersona;
	}
}