package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDireccion", propOrder = { "direccion" })
public class ArrayOfDireccion implements Serializable {

	private static final long serialVersionUID = 7628018040545224529L;
	
	@XmlElement(name = "Direccion", nillable = true)
	protected List<Direccion> direccion;

	public List<Direccion> getDireccion() {
		if (direccion == null) {
			direccion = new ArrayList<Direccion>();
		}
		return this.direccion;
	}
}