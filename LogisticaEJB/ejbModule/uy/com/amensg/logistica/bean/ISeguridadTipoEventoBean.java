package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.SeguridadTipoEvento;

@Remote
public interface ISeguridadTipoEventoBean {

	public Collection<SeguridadTipoEvento> list();
}