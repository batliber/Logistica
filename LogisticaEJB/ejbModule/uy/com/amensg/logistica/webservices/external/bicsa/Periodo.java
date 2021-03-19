package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Periodo", propOrder = { "idPeriodo", "descripcion", "valorEnDias" })
public class Periodo implements Serializable {

	private static final long serialVersionUID = 6840515383761404527L;
	
	@XmlElement(name = "IdPeriodo")
	protected int idPeriodo;
	@XmlElement(name = "Descripcion")
	protected String descripcion;
	@XmlElement(name = "ValorEnDias")
	protected int valorEnDias;

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int value) {
		this.idPeriodo = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}

	public int getValorEnDias() {
		return valorEnDias;
	}

	public void setValorEnDias(int value) {
		this.valorEnDias = value;
	}
}