package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOperacionActivaDeLaPersona", propOrder = { "operacionActivaDeLaPersona" })
public class ArrayOfOperacionActivaDeLaPersona implements Serializable {

	private static final long serialVersionUID = -8188183099196271971L;
	
	@XmlElement(name = "OperacionActivaDeLaPersona", nillable = true)
	protected List<OperacionActivaDeLaPersona> operacionActivaDeLaPersona;

	public List<OperacionActivaDeLaPersona> getOperacionActivaDeLaPersona() {
		if (operacionActivaDeLaPersona == null) {
			operacionActivaDeLaPersona = new ArrayList<OperacionActivaDeLaPersona>();
		}
		return this.operacionActivaDeLaPersona;
	}
}