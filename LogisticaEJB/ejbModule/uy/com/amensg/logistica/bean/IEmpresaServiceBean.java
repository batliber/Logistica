package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IEmpresaServiceBean {

	public Collection<EmpresaService> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public EmpresaService getById(Long id);
	
	public EmpresaService save(EmpresaService empresaService);
	
	public void remove(EmpresaService empresaService);
	
	public void update(EmpresaService empresaService);
}