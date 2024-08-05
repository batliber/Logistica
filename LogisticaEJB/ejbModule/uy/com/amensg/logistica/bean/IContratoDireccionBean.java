package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoDireccion;

@Remote
public interface IContratoDireccionBean {

	public Collection<ContratoDireccion> listByContratoId(Long id);
}