package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IBarrioBean {

	public Collection<Barrio> list();
	
	public Collection<Barrio> listMinimal();
	
	public Collection<Barrio> listByDepartamentoId(Long departamentoId);
	
	public Collection<Barrio> listMinimalByDepartamentoId(Long departamentoId);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Barrio getById(Long id);
	
	public void save(Barrio barrio);
	
	public void remove(Barrio barrio);
	
	public void update(Barrio barrio);
}