package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoANTEL;

@Remote
public interface IEstadoANTELBean {

	public Collection<EstadoANTEL> list();
		
	public EstadoANTEL getById(Long id);
}