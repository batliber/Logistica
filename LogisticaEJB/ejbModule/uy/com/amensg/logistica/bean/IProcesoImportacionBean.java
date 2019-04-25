package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;

@Remote
public interface IProcesoImportacionBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ProcesoImportacion getByNombreArchivo(String fileName);
	
	public ProcesoImportacion save(ProcesoImportacion procesoImportacion);
	
	public void update(ProcesoImportacion procesoImportacion);
}