package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Departamento;

@Remote
public interface IDepartamentoBean {

	public Collection<Departamento> list();
	
	public Departamento getByNombre(String nombre);
}