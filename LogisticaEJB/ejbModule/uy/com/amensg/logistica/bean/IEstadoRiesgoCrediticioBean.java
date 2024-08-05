package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;

@Remote
public interface IEstadoRiesgoCrediticioBean {

	public Collection<EstadoRiesgoCrediticio> list();
	
	public EstadoRiesgoCrediticio getById(Long id);
}