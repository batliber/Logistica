package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Rol;

@Remote
public interface IRolBean {

	public Collection<Rol> list();
	
	public Rol getById(Long id);
}