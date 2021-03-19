package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Usuario;

@Remote
public interface IUsuarioBean {

	public Collection<Usuario> list();
	
	public Collection<Usuario> listMinimal();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Usuario getById(Long id, boolean initializeCollections);
	
	public Usuario getByIdMinimal(Long id);
	
	public Usuario getByLogin(String login, boolean initializeCollections);
	
	public Usuario save(Usuario usuario);
	
	public void remove(Usuario usuario);
	
	public Usuario update(Usuario usuario);
}