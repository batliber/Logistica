package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;

@Remote
public interface IEstadoVisitaPuntoVentaDistribuidorBean {

	public Collection<EstadoVisitaPuntoVentaDistribuidor> list();
	
	public EstadoVisitaPuntoVentaDistribuidor getById(Long id);
}