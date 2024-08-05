package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.UnidadIndexada;

@Remote
public interface IUnidadIndexadaBean {

	public Collection<UnidadIndexada> list();
	
	public UnidadIndexada getById(Long id);
	
	public UnidadIndexada save(UnidadIndexada unidadIndexada);
	
	public void remove(UnidadIndexada unidadIndexada);
	
	public UnidadIndexada getVigente();
}