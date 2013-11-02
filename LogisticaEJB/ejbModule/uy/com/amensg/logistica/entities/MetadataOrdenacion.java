package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class MetadataOrdenacion implements Serializable {

	private static final long serialVersionUID = -6707903210551529472L;

	private String campo;
	private Boolean ascendente;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Boolean getAscendente() {
		return ascendente;
	}

	public void setAscendente(Boolean ascendente) {
		this.ascendente = ascendente;
	}
}