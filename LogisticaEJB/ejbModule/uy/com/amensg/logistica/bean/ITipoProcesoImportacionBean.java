package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoProcesoImportacion;

@Remote
public interface ITipoProcesoImportacionBean {

	public Collection<TipoProcesoImportacion> list();
	
	public TipoProcesoImportacion getById(Long id);
}