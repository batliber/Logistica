package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;

@Remote
public interface IACMInterfaceContratoBean {

	public Collection<ACMInterfaceContrato> list();
	
	public String preprocesarAsignacion(MetadataConsulta metadataConsulta, Empresa empresa);
	
	public String asignar(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones);
	
	public void reprocesarPorMID(MetadataConsulta metadataConsulta, String observaciones);
	
	public void reprocesarPorNumeroContrato(MetadataConsulta metadataConsulta, String observaciones);
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta);
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta);
	
	public void controlarRiesgoCrediticio(
		Empresa empresa, TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio, MetadataConsulta metadataConsulta
	);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public Long count(MetadataConsulta metadataConsulta);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public Collection<TipoContrato> listTipoContratos();
	
	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta);
}