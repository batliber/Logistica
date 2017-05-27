package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.PuntoVenta;

@Remote
public interface IPuntoVentaBean {

	public Collection<PuntoVenta> list();
	
	public Collection<PuntoVenta> listByDepartamento(Departamento departamento);
	
	public Collection<PuntoVenta> listByBarrio(Barrio barrio);
	
	public PuntoVenta getById(Long id);
	
	public void save(PuntoVenta puntoVenta);
	
	public void remove(PuntoVenta puntoVenta);
	
	public void update(PuntoVenta puntoVenta);
}