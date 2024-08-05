package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TarjetaCredito;

@Remote
public interface ITarjetaCreditoBean {

	public Collection<TarjetaCredito> list();
	
	public TarjetaCredito getById(Long id);
}