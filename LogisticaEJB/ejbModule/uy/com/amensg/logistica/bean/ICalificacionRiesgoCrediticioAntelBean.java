package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;

@Remote
public interface ICalificacionRiesgoCrediticioAntelBean {

	public Collection<CalificacionRiesgoCrediticioAntel> list();
	
	public CalificacionRiesgoCrediticioAntel getById(Long id);
}