package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;

@Remote
public interface IACMInterfaceBean {

	public ACMInterfaceMid getNextMidSinProcesar();
	
	public void update(ACMInterfaceContrato acmInterfaceContrato);
	
	public void update(ACMInterfacePrepago acmInterfacePrepago);

	public void update(ACMInterfaceMid acmInterfaceMid);
}