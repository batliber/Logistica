package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoContrato;

@Remote
public interface IACMInterfaceContratoPHBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public Collection<TipoContrato> listTipoContratos();
	
	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta);
	
	public String preprocesarExportacion(MetadataConsulta metadataConsulta, Empresa empresa);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones);
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta);
}