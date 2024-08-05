package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IContratoURSECBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public String preprocesarArchivoURSEC(String fileName, Long loggedUsuarioId);

	public String procesarArchivoURSEC(String fileName, String observaciones, Long loggedUsuarioId);
}