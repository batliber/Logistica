package uy.com.amensg.logistica.web.entities;

import uy.com.amensg.logistica.entities.MetadataConsulta;

public class VisitaPorSubloteTO {

	private Long distribuidorId;
	private String observaciones;
	private MetadataConsulta metadataConsulta;

	public Long getDistribuidorId() {
		return distribuidorId;
	}

	public void setDistribuidorId(Long distribuidorId) {
		this.distribuidorId = distribuidorId;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public MetadataConsulta getMetadataConsulta() {
		return metadataConsulta;
	}

	public void setMetadataConsulta(MetadataConsulta metadataConsulta) {
		this.metadataConsulta = metadataConsulta;
	}
}