package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfaceMidBean {

	public MetadataConsultaResultado listEnProceso(MetadataConsulta metadataConsulta);
	
	public void reprocesarEnProceso(MetadataConsulta metadataConsulta, String observaciones);
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones);
	
	public void update(ACMInterfaceMid acmInterfaceMid);
}