package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.SituacionRiesgoCrediticioParaguay;

@Remote
public interface ISituacionRiesgoCrediticioParaguayBean {

	public Collection<SituacionRiesgoCrediticioParaguay> list();
	
	public SituacionRiesgoCrediticioParaguay getById(Long id);
}