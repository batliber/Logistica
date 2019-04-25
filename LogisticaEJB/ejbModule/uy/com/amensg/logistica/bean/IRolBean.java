package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Rol;

@Remote
public interface IRolBean {

	public Collection<Rol> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Rol getById(Long id, boolean initializeCollections);
	
	public Rol getByNombre(String nombre, boolean initializeCollections);
	
	public void save(Rol rol);
	
	public void remove(Rol rol);
	
	public void update(Rol rol);
}