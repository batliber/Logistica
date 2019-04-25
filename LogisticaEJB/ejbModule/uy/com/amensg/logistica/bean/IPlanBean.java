package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Plan;

@Remote
public interface IPlanBean {

	public Collection<Plan> list();
	
	public Collection<Plan> listVigentes();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Plan getById(Long id);
	
	public void save(Plan plan);
	
	public void remove(Plan plan);
	
	public void update(Plan plan);
}