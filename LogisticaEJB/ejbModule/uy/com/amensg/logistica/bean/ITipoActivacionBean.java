package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoActivacion;

@Remote
public interface ITipoActivacionBean {

	public Collection<TipoActivacion> list();
}