package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IActivacionSubloteBean {

	public Collection<ActivacionSublote> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ActivacionSublote getById(Long id);
	
	public ActivacionSublote getByNumero(Long numero);
	
	public Long save(ActivacionSublote activacionSublote);
	
	public void update(ActivacionSublote activacionSublote);
}