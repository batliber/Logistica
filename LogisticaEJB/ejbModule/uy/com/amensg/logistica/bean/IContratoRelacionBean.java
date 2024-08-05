package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoRelacion;
import uy.com.amensg.logistica.entities.MetadataConsulta;

@Remote
public interface IContratoRelacionBean {

	public Collection<ContratoRelacion> listByContratoId(Long contratoId);
	
	public boolean chequearAsignacion(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ContratoRelacion save(ContratoRelacion contratoRelacion);
}