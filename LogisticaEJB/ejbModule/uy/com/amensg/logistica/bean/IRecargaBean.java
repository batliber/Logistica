package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Recarga;

@Remote
public interface IRecargaBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Recarga getById(Long id);
	
	public Recarga recargar(Recarga recarga);
	
	public Recarga aprobar(Recarga recarga);
	
	public Recarga timeout(Recarga recarga);
}