package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetalleTrazaBusqueda", propOrder = { "detalleTrazaBusqueda" })
public class ArrayOfDetalleTrazaBusqueda  implements Serializable {

	private static final long serialVersionUID = 3857607092240227219L;
	
	@XmlElement(name = "DetalleTrazaBusqueda", nillable = true)
	protected List<DetalleTrazaBusqueda> detalleTrazaBusqueda;

	public List<DetalleTrazaBusqueda> getDetalleTrazaBusqueda() {
		if (detalleTrazaBusqueda == null) {
			detalleTrazaBusqueda = new ArrayList<DetalleTrazaBusqueda>();
		}
		return this.detalleTrazaBusqueda;
	}
}