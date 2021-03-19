package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDocumento", propOrder = { "documento" })
public class ArrayOfDocumento implements Serializable {

	private static final long serialVersionUID = 1903932094277022085L;
	
	@XmlElement(name = "Documento", nillable = true)
	protected List<Documento> documento;

	public List<Documento> getDocumento() {
		if (documento == null) {
			documento = new ArrayList<Documento>();
		}
		return this.documento;
	}
}