package uy.com.amensg.logistica.web.entities;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.Usuario;

public class CrearVisitaPuntoVentaDistribuidorTO {

	private Usuario distribuidor;
	private String observaciones;
	private MetadataConsulta metadataConsulta;

	public Usuario getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(Usuario distribuidor) {
		this.distribuidor = distribuidor;
	}

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