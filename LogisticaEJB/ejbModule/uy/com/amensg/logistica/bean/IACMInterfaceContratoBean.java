package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfaceContratoBean {

	public Collection<ACMInterfaceContrato> list();
	
	public String exportarAExcel(MetadataConsulta metadataConsulta);
	
	public void reprocesar(MetadataConsulta metadataConsulta);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
}