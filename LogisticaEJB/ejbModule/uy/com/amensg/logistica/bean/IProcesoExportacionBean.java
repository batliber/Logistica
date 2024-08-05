package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoExportacion;

@Remote
public interface IProcesoExportacionBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ProcesoExportacion save(ProcesoExportacion procesoExportacion);
	
	public void update(ProcesoExportacion procesoExportacion);
}