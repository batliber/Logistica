package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoServicioInfraestructura;

@Remote
public interface IEstadoServicioInfraestructuraBean {

	public Collection<EstadoServicioInfraestructura> list();
	
	public EstadoServicioInfraestructura getById(Long id);
}