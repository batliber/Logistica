package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Collection;

public class MetadataConsulta implements Serializable {

	private static final long serialVersionUID = -6422402083424469524L;

	private Collection<MetadataCondicion> metadataCondiciones;
	private Collection<MetadataOrdenacion> metadataOrdenaciones;
	private Long tamanoMuestra;

	public Collection<MetadataCondicion> getMetadataCondiciones() {
		return metadataCondiciones;
	}

	public void setMetadataCondiciones(
			Collection<MetadataCondicion> metadataCondiciones) {
		this.metadataCondiciones = metadataCondiciones;
	}

	public Collection<MetadataOrdenacion> getMetadataOrdenaciones() {
		return metadataOrdenaciones;
	}

	public void setMetadataOrdenaciones(
			Collection<MetadataOrdenacion> metadataOrdenaciones) {
		this.metadataOrdenaciones = metadataOrdenaciones;
	}

	public Long getTamanoMuestra() {
		return tamanoMuestra;
	}

	public void setTamanoMuestra(Long tamanoMuestra) {
		this.tamanoMuestra = tamanoMuestra;
	}
}