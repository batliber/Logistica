package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Modelo;

@Remote
public interface IModeloBean {

	public Collection<Modelo> list();
	
	public Collection<Modelo> listVigentes();
	
	public Collection<Modelo> listByMarcaId(Long marcaId);
	
	public Collection<Modelo> listVigentesByMarcaId(Long marcaId);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Modelo getById(Long id);
	
	public void save(Modelo modelo);
	
	public void remove(Modelo modelo);
	
	public void update(Modelo modelo);
}