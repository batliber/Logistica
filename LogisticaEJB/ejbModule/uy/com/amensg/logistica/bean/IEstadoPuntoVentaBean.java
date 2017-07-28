package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoPuntoVenta;

@Remote
public interface IEstadoPuntoVentaBean {

	public Collection<EstadoPuntoVenta> list();
	
	public EstadoPuntoVenta getById(Long id);
}