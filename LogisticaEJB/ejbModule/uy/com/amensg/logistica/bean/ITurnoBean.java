package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Turno;

@Remote
public interface ITurnoBean {

	public Collection<Turno> list();
}