package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ModalidadVenta;

@Remote
public interface IModalidadVentaBean {

	public Collection<ModalidadVenta> list();
	
	public ModalidadVenta getById(Long id);
}