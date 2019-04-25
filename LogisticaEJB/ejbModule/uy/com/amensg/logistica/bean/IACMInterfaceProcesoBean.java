package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfaceProcesoBean {

	public MetadataConsultaResultado listEstadisticas(MetadataConsulta metadataConsulta);
	
	public Long countEstadisticas(MetadataConsulta metadataConsulta);
	
	public ACMInterfaceProceso save(ACMInterfaceProceso acmInterfaceProceso);
	
	public void finalizarProcesos();
}