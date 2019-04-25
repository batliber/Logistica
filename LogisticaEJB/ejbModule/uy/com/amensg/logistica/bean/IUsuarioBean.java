package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Usuario;

@Remote
public interface IUsuarioBean {

	public Collection<Usuario> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Usuario getById(Long id, boolean initializeCollections);
	
	public Usuario getByLogin(String login, boolean initializeCollections);
	
	public void save(Usuario usuario);
	
	public void remove(Usuario usuario);
	
	public void update(Usuario usuario);
}