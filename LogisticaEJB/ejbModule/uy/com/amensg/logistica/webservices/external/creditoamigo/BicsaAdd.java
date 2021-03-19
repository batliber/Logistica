package uy.com.amensg.logistica.webservices.external.creditoamigo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "",
	propOrder = {
		"documentoNro",
		"jSon"
	}
)
@XmlRootElement(
	name = "BicsaAdd"
)
public class BicsaAdd {

	@XmlElement(name = "DocumentoNro")
	protected int documentoNro;

	@XmlElement(name = "JSon")
	protected String jSon;

	public int getDocumentoNro() {
		return documentoNro;
	}

	public void setDocumentoNro(int value) {
		this.documentoNro = value;
	}

	public String getJSon() {
		return jSon;
	}

	public void setJSon(String value) {
		this.jSon = value;
	}
}