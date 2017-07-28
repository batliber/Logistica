package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Rol;

@Remote
public interface IRolBean {

	public Collection<Rol> list();
	
	public Rol getById(Long id);
	
	public void save(Rol rol);
	
	public void remove(Rol rol);
	
	public void update(Rol rol);
}