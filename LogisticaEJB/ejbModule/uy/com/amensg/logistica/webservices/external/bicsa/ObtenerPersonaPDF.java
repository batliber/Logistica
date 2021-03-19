package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "idTipoDoc", "idPaisDoc", "nroDoc", "codInstitucion" })
@XmlRootElement(name = "ObtenerPersona_PDF")
public class ObtenerPersonaPDF implements Serializable {

	private static final long serialVersionUID = 3082000272622826073L;
	
	@XmlElement(name = "IdTipoDoc")
	protected int idTipoDoc;
	@XmlElement(name = "IdPaisDoc")
	protected String idPaisDoc;
	@XmlElement(name = "NroDoc")
	protected String nroDoc;
	@XmlElement(name = "cod_institucion")
	protected String codInstitucion;

	public int getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(int value) {
		this.idTipoDoc = value;
	}

	public String getIdPaisDoc() {
		return idPaisDoc;
	}

	public void setIdPaisDoc(String value) {
		this.idPaisDoc = value;
	}

	public String getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(String value) {
		this.nroDoc = value;
	}

	public String getCodInstitucion() {
		return codInstitucion;
	}

	public void setCodInstitucion(String value) {
		this.codInstitucion = value;
	}
}