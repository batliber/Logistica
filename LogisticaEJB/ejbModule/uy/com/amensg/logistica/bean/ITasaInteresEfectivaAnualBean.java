package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;

@Remote
public interface ITasaInteresEfectivaAnualBean {

	public Collection<TasaInteresEfectivaAnual> listVigentes();
}