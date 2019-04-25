package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;

@Remote
public interface IEstadoProcesoImportacionBean {

	public Collection<EstadoProcesoImportacion> list();
	
	public EstadoProcesoImportacion getById(Long id);
}