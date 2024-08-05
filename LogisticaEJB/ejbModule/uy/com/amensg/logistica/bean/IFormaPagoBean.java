package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.FormaPago;

@Remote
public interface IFormaPagoBean {

	public Collection<FormaPago> list();
	
	public FormaPago getById(Long id);
}