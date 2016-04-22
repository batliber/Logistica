package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Barrio;

@Remote
public interface IBarrioBean {

	public Collection<Barrio> list();
	
	public Collection<Barrio> listByDepartamentoId(Long departamentoId);
	
	public Barrio getById(Long id);
	
	public void save(Barrio barrio);
	
	public void remove(Barrio barrio);
	
	public void update(Barrio barrio);
}