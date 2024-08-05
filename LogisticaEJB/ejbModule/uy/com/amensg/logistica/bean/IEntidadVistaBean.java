package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;
import uy.com.amensg.logistica.entities.EntidadVista;

@Remote
public interface IEntidadVistaBean {

	public EntidadVista getById(Long id);
}