package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Plan;

@Remote
public interface IPlanBean {

	public Collection<Plan> list();
	
	public Collection<Plan> listVigentes();
	
	public Collection<Plan> listMinimal();
	
	public Collection<Plan> listVigentesMinimal();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Plan getById(Long id);
	
	public Plan save(Plan plan);
	
	public void remove(Plan plan);
	
	public void update(Plan plan);
}