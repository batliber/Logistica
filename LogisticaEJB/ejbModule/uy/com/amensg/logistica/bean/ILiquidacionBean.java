package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface ILiquidacionBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public String preprocesarArchivo(String fileName);
	
	public String procesarArchivo(String fileName, Long loggedUsuarioId);

	public void calcularPorcentajeActivacionPuntoVentas(Long loggedUsuarioId);
	
	public void calcularPorcentajeActivacionSubLotes(Long loggedUsuarioId);
}