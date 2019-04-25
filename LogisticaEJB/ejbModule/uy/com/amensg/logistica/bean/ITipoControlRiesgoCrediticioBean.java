package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;

@Remote
public interface ITipoControlRiesgoCrediticioBean {

	public Collection<TipoControlRiesgoCrediticio> list();
	
	public TipoControlRiesgoCrediticio getById(Long id);
}