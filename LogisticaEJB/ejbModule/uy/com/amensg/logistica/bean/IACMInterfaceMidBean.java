package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IACMInterfaceMidBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones);
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta);
	
	public void update(ACMInterfaceMid acmInterfaceMid);
}