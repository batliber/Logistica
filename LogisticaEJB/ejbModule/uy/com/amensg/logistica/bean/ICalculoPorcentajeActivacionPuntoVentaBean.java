package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface ICalculoPorcentajeActivacionPuntoVentaBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public Long count(MetadataConsulta metadataConsulta);

	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}