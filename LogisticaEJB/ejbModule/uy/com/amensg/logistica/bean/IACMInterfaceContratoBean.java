package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;

@Remote
public interface IACMInterfaceContratoBean {

	public ACMInterfaceContrato getSiguienteSinProcesar();
}