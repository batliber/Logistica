package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.AtencionClienteTipoContacto;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IAtencionClienteTipoContactoBean {

	public Collection<AtencionClienteTipoContacto> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public AtencionClienteTipoContacto getById(Long id);
	
	public AtencionClienteTipoContacto save(AtencionClienteTipoContacto atencionClienteTipoContacto);
	
	public void remove(AtencionClienteTipoContacto atencionClienteTipoContacto);
	
	public void update(AtencionClienteTipoContacto atencionClienteTipoContacto);
}