package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "listadoTipoDocumentosResult" })
@XmlRootElement(name = "ListadoTipoDocumentosResponse")
public class ListadoTipoDocumentosResponse implements Serializable {

	private static final long serialVersionUID = -2722824007338926779L;
	
	@XmlElement(name = "ListadoTipoDocumentosResult")
	protected ArrayOfTipoDocumento listadoTipoDocumentosResult;

	public ArrayOfTipoDocumento getListadoTipoDocumentosResult() {
		return listadoTipoDocumentosResult;
	}

	public void setListadoTipoDocumentosResult(ArrayOfTipoDocumento value) {
		this.listadoTipoDocumentosResult = value;
	}
}