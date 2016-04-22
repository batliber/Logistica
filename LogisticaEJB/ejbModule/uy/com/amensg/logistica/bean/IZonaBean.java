package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Zona;

@Remote
public interface IZonaBean {

	public Collection<Zona> list();
	
	public Collection<Zona> listByDepartamentoId(Long departamentoId);
	
	public Zona getById(Long id);
	
	public void save(Zona zona);
	
	public void remove(Zona zona);
	
	public void update(Zona zona);
}