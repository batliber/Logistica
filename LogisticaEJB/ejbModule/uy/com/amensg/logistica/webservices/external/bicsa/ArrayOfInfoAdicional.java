package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInfoAdicional", propOrder = { "infoAdicional" })
public class ArrayOfInfoAdicional implements Serializable {

	private static final long serialVersionUID = 4862919193799878676L;
	
	@XmlElement(name = "InfoAdicional", nillable = true)
	protected List<InfoAdicional> infoAdicional;

	public List<InfoAdicional> getInfoAdicional() {
		if (infoAdicional == null) {
			infoAdicional = new ArrayList<InfoAdicional>();
		}
		return this.infoAdicional;
	}
}