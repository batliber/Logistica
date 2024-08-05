package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceEstado;

@Remote
public interface IACMInterfaceEstadoBean {

	public Collection<ACMInterfaceEstado> list();
}