package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfaceRiesgoCrediticioBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public void save(ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}