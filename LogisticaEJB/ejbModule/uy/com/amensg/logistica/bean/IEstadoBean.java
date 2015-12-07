package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Estado;

@Remote
public interface IEstadoBean {

	public Estado getById(Long id);
}