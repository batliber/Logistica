package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Estado;

@Remote
public interface IEstadoBean {

	public Collection<Estado> list();
	
	public Estado getById(Long id);
}