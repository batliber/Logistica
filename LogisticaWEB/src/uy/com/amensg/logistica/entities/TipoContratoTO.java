package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class TipoContratoTO {

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