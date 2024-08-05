package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoControl;

@Remote
public interface IEstadoControlBean {

	public Collection<EstadoControl> list();
	
	public EstadoControl getById(Long id);
}