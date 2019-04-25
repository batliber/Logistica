package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfacePrepagoPHBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String preprocesarExportacion(MetadataConsulta metadataConsulta, Empresa empresa);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones);
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta);
}