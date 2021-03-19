package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Zona;

@Remote
public interface IZonaBean {

	public Collection<Zona> list();
	
	public Collection<Zona> listMinimal();
	
	public Collection<Zona> listMinimalByDepartamentoId(Long departamentoId);
	
	public Collection<Zona> listByDepartamentoId(Long departamentoId);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Zona getById(Long id);
	
	public Zona save(Zona zona);
	
	public void remove(Zona zona);
	
	public void update(Zona zona);
}