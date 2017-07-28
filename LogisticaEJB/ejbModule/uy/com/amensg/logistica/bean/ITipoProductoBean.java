package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoProducto;

@Remote
public interface ITipoProductoBean {

	public Collection<TipoProducto> list();
	
	public TipoProducto getById(Long id);
}