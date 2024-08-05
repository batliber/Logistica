package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.AtencionClienteRespuestaTecnicaComercial;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IAtencionClienteRespuestaTecnicaComercialBean {

	public Collection<AtencionClienteRespuestaTecnicaComercial> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public AtencionClienteRespuestaTecnicaComercial getById(Long id);
	
	public AtencionClienteRespuestaTecnicaComercial save(AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial);
	
	public void remove(AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial);
	
	public void update(AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial);
}