package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOperacionCanceladaDeLaPersona", propOrder = { "operacionCanceladaDeLaPersona" })
public class ArrayOfOperacionCanceladaDeLaPersona implements Serializable {

	private static final long serialVersionUID = 5721698798586829327L;
	
	@XmlElement(name = "OperacionCanceladaDeLaPersona", nillable = true)
	protected List<OperacionCanceladaDeLaPersona> operacionCanceladaDeLaPersona;

	public List<OperacionCanceladaDeLaPersona> getOperacionCanceladaDeLaPersona() {
		if (operacionCanceladaDeLaPersona == null) {
			operacionCanceladaDeLaPersona = new ArrayList<OperacionCanceladaDeLaPersona>();
		}
		return this.operacionCanceladaDeLaPersona;
	}
}