package uy.com.amensg.logistica.web.entities;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;

public class AsignacionTO {

	private MetadataConsulta metadataConsulta;
	private Empresa empresa;
	private String observaciones;

	public MetadataConsulta getMetadataConsulta() {
		return metadataConsulta;
	}

	public void setMetadataConsulta(MetadataConsulta metadataConsulta) {
		this.metadataConsulta = metadataConsulta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}