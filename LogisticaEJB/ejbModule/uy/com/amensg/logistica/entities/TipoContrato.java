package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class TipoContrato implements Serializable {

	private static final long serialVersionUID = -8491393708369187518L;
	
	private String tipoContratoCodigo;
	private String tipoContratoDescripcion;

	public String getTipoContratoCodigo() {
		return tipoContratoCodigo;
	}

	public void setTipoContratoCodigo(String tipoContratoCodigo) {
		this.tipoContratoCodigo = tipoContratoCodigo;
	}

	public String getTipoContratoDescripcion() {
		return tipoContratoDescripcion;
	}

	public void setTipoContratoDescripcion(String tipoContratoDescripcion) {
		this.tipoContratoDescripcion = tipoContratoDescripcion;
	}
}