package uy.com.amensg.logistica.web.entities;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.Usuario;

public class AsignacionContratosUsuarioTO {

	private Usuario usuario;
	private MetadataConsulta metadataConsulta;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MetadataConsulta getMetadataConsulta() {
		return metadataConsulta;
	}

	public void setMetadataConsulta(MetadataConsulta metadataConsulta) {
		this.metadataConsulta = metadataConsulta;
	}
}