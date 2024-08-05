package uy.com.amensg.logistica.web.entities;

import uy.com.amensg.logistica.entities.MetadataConsulta;

public class ReprocesarTO {

	private MetadataConsulta metadataConsulta;
	private String observaciones;

	public MetadataConsulta getMetadataConsulta() {
		return metadataConsulta;
	}

	public void setMetadataConsulta(MetadataConsulta metadataConsulta) {
		this.metadataConsulta = metadataConsulta;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}