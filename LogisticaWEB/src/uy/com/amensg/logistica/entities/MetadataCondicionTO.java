package uy.com.amensg.logistica.entities;

import java.util.Collection;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class MetadataCondicionTO {

	private String campo;
	private String operador;
	private Collection<String> valores;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Collection<String> getValores() {
		return valores;
	}

	public void setValores(Collection<String> valores) {
		this.valores = valores;
	}
}