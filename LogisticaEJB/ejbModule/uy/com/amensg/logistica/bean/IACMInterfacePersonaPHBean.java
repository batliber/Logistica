package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfacePersona;

@Remote
public interface IACMInterfacePersonaPHBean {

	public ACMInterfacePersona getById(Long id);
	
	public ACMInterfacePersona getByDocumento(String documento);
	
	public ACMInterfacePersona getByIdCliente(String idCliente);
}