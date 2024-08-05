package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Configuracion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IConfiguracionPHBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);

	public Long count(MetadataConsulta metadataConsulta);
	
	public Configuracion getById(Long id);
	
	public String getProperty(String clave);
	
	public Configuracion getByClave(String clave);
	
	public Configuracion save(Configuracion configuracion);
	
	public void remove(Configuracion configuracion);
	
	public void update(Configuracion configuracion);
}