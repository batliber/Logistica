package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoActivacion;

@Remote
public interface IEstadoActivacionBean {

	public Collection<EstadoActivacion> list();
	
	public EstadoActivacion getById(Long id);
}