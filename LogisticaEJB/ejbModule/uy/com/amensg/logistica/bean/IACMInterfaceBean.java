package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;

@Remote
public interface IACMInterfaceBean {

	public ACMInterfaceContrato getNextContratoSinProcesar();
	
	public ACMInterfacePrepago getNextPrepagoSinProcesar();
	
	public void update(ACMInterfaceContrato acmInterfaceContrato);
	
	public void update(ACMInterfacePrepago acmInterfacePrepago);
}