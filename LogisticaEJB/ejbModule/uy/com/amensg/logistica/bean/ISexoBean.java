package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Sexo;

@Remote
public interface ISexoBean {

	public Collection<Sexo> list();
}