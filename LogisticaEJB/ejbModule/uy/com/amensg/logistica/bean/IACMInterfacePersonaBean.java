package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfacePersona;

@Remote
public interface IACMInterfacePersonaBean {

	public ACMInterfacePersona getById(Long id);
}