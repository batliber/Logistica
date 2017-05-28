package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Producto;

@Remote
public interface IProductoBean {

	public Collection<Producto> list();
	
	public Producto getById(Long id);
	
<<<<<<< HEAD
	public Producto getByIMEI(String imei);
	
	public Boolean existeIMEI(String imei);
	
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	public void save(Producto producto);
	
	public void remove(Producto producto);
	
	public void update(Producto producto);
}