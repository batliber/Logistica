package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfacePrepagoBean {

	public Collection<ACMInterfacePrepago> list();
	
	public String exportarAExcel(MetadataConsulta metadataConsulta);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones);
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones);
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta);
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
}