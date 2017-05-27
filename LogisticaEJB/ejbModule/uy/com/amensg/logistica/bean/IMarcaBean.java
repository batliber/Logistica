package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Marca;

@Remote
public interface IMarcaBean {

	public Collection<Marca> list();
	
	public Marca getById(Long id);
	
	public void save(Marca marca);
	
	public void remove(Marca marca);
	
	public void update(Marca marca);
}