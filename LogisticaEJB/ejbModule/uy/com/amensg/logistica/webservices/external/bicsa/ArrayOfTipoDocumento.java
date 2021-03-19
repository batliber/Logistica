package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTipoDocumento", propOrder = { "tipoDocumento" })
public class ArrayOfTipoDocumento implements Serializable {

	private static final long serialVersionUID = 1022207243027708055L;
	
	@XmlElement(name = "TipoDocumento", nillable = true)
	protected List<TipoDocumento> tipoDocumento;

	public List<TipoDocumento> getTipoDocumento() {
		if (tipoDocumento == null) {
			tipoDocumento = new ArrayList<TipoDocumento>();
		}
		return this.tipoDocumento;
	}
}