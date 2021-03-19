package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPais", propOrder = { "pais" })
public class ArrayOfPais implements Serializable {

	private static final long serialVersionUID = 4237687753655739974L;

	@XmlElement(name = "Pais", nillable = true)
	protected List<Pais> pais;

	public List<Pais> getPais() {
		if (pais == null) {
			pais = new ArrayList<Pais>();
		}
		return this.pais;
	}
}