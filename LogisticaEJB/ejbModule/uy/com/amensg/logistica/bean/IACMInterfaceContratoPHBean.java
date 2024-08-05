package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoContrato;

@Remote
public interface IACMInterfaceContratoPHBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public Long count(MetadataConsulta metadataConsulta);
	
	public String preprocesarAsignacion(MetadataConsulta metadataConsulta, Empresa empresa);
	
	public String asignar(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones);
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta);
	
	public void reprocesarPorMID(MetadataConsulta metadataConsulta, String observaciones);
	
	public void reprocesarPorNumeroContrato(MetadataConsulta metadataConsulta, String observaciones);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public Collection<TipoContrato> listTipoContratos();
	
	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta);
}