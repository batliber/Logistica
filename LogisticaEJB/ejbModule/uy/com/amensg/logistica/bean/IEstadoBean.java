package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.ProcesoNegocio;

@Remote
public interface IEstadoBean {

	public Collection<Estado> list();
	
	public Collection<Estado> listByProcesoNegocio(ProcesoNegocio procesoNegocio);
	
	public Estado getById(Long id);
}