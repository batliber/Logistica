package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;

@Remote
public interface ISeguridadAuditoriaBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);

	public Long count(MetadataConsulta metadataConsulta);
	
	public Collection<SeguridadAuditoria> list(Date fechaDesde, Date fechaHasta);
	
	public void save(SeguridadAuditoria seguridadAuditoria);
}