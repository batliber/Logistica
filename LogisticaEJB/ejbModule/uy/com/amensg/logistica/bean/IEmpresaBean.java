package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;

@Remote
public interface IEmpresaBean {

	public Collection<Empresa> list();

	public Empresa getById(Long id);
}