package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "Documento", 
	propOrder = { 
		"idPaisDoc", "idTipoDoc", "tipoDoc", "nroDoc", "paisDoc", "fechaVencimiento", "versionDocumento" 
	}
)
public class Documento implements Serializable {

	private static final long serialVersionUID = -231312707208158111L;
	
	@XmlElement(name = "IdPaisDoc")
	protected String idPaisDoc;
	@XmlElement(name = "IdTipoDoc")
	protected int idTipoDoc;
	@XmlElement(name = "TipoDoc")
	protected String tipoDoc;
	@XmlElement(name = "NroDoc")
	protected String nroDoc;
	@XmlElement(name = "PaisDoc")
	protected String paisDoc;
	@XmlElement(name = "FechaVencimiento", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaVencimiento;
	@XmlElement(name = "VersionDocumento")
	protected String versionDocumento;

	public String getIdPaisDoc() {
		return idPaisDoc;
	}

	public void setIdPaisDoc(String idPaisDoc) {
		this.idPaisDoc = idPaisDoc;
	}

	public int getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(int idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String value) {
		this.tipoDoc = value;
	}

	public String getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(String value) {
		this.nroDoc = value;
	}

	public String getPaisDoc() {
		return paisDoc;
	}

	public void setPaisDoc(String value) {
		this.paisDoc = value;
	}

	public XMLGregorianCalendar getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(XMLGregorianCalendar value) {
		this.fechaVencimiento = value;
	}

	public String getVersionDocumento() {
		return versionDocumento;
	}

	public void setVersionDocumento(String value) {
		this.versionDocumento = value;
	}
}