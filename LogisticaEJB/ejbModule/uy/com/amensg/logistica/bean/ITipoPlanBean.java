package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoPlan;

@Remote
public interface ITipoPlanBean {

	public Collection<TipoPlan> list();
	
	public TipoPlan getById(Long id);
}