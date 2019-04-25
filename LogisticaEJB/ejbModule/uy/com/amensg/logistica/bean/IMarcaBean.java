package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IMarcaBean {

	public Collection<Marca> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Marca getById(Long id);
	
	public void save(Marca marca);
	
	public void remove(Marca marca);
	
	public void update(Marca marca);
}