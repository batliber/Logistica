package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;

@Remote
public interface ICalificacionRiesgoCrediticioBCUBean {

	public Collection<CalificacionRiesgoCrediticioBCU> list();
	
	public CalificacionRiesgoCrediticioBCU getById(Long id);
	
	public CalificacionRiesgoCrediticioBCU getByCalificacion(String calificacion);
}