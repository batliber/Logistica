package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoControl;

@Remote
public interface ITipoControlBean {

	public Collection<TipoControl> list();
}