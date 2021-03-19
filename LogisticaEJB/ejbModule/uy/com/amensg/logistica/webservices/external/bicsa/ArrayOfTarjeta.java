package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTarjeta", propOrder = { "tarjeta" })
public class ArrayOfTarjeta implements Serializable {

	private static final long serialVersionUID = 7084933776853005070L;
	
	@XmlElement(name = "Tarjeta", nillable = true)
	protected List<Tarjeta> tarjeta;

	public List<Tarjeta> getTarjeta() {
		if (tarjeta == null) {
			tarjeta = new ArrayList<Tarjeta>();
		}
		return this.tarjeta;
	}
}