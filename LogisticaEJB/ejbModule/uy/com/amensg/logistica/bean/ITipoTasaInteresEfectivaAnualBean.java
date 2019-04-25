package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;

@Remote
public interface ITipoTasaInteresEfectivaAnualBean {

	public Collection<TipoTasaInteresEfectivaAnual> list();
	
	public TipoTasaInteresEfectivaAnual getById(Long id);
}