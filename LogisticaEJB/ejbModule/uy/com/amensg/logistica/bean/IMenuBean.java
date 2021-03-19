package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Menu;

@Remote
public interface IMenuBean {

	public Collection<Menu> list();
	
	public Menu getById(Long id);
	
	public Menu save(Menu menu);
	
	public void remove(Menu menu);
	
	public void update(Menu menu);
}