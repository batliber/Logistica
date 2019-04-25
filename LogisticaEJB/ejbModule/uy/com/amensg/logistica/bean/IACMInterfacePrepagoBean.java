package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;

@Remote
public interface IACMInterfacePrepagoBean {

	public Collection<ACMInterfacePrepago> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public Long count(MetadataConsulta metadataConsulta);

	public String preprocesarExportacion(MetadataConsulta metadataConsulta, Empresa empresa);

	public String exportarAExcel(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta);
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones);
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta);
	
	public void controlarRiesgoCrediticio(
		Empresa empresa, TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio, MetadataConsulta metadataConsulta
	);
}