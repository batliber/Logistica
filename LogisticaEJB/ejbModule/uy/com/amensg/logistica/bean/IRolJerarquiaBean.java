package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.RolJerarquia;

@Remote
public interface IRolJerarquiaBean {

	public Collection<RolJerarquia> list();
}