package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Moneda;

@Remote
public interface IMonedaBean {

	public Collection<Moneda> list();
	
	public Moneda getById(Long id);
	
	public Moneda getBySimbolo(String simbolo);
}