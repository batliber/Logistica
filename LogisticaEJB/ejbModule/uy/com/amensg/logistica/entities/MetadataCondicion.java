package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Collection;

public class MetadataCondicion implements Serializable {

	private static final long serialVersionUID = -5841349437964065460L;

	private String campo;
	private String operador;
	private Boolean fijo;
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

	public Boolean getFijo() {
		return fijo;
	}

	public void setFijo(Boolean fijo) {
		this.fijo = fijo;
	}

	public Collection<String> getValores() {
		return valores;
	}

	public void setValores(Collection<String> valores) {
		this.valores = valores;
	}
}