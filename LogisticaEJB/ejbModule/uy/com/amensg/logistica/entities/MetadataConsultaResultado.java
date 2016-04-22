package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Collection;

public class MetadataConsultaResultado implements Serializable {

	private static final long serialVersionUID = -2756993869895704750L;
	
	private Long cantidadRegistros;
	private Collection<Object> registrosMuestra;

	public Long getCantidadRegistros() {
		return cantidadRegistros;
	}

	public void setCantidadRegistros(Long cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	public Collection<Object> getRegistrosMuestra() {
		return registrosMuestra;
	}

	public void setRegistrosMuestra(Collection<Object> registrosMuestra) {
		this.registrosMuestra = registrosMuestra;
	}
}