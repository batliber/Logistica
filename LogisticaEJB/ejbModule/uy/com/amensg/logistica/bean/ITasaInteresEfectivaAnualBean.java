package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;

@Remote
public interface ITasaInteresEfectivaAnualBean {

	public Collection<TasaInteresEfectivaAnual> list();
	
	public Collection<TasaInteresEfectivaAnual> listVigentes();
	
	public Collection<TasaInteresEfectivaAnual> listVigentesByTipo(TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual);
	
	public TasaInteresEfectivaAnual getById(Long id);
	
	public TasaInteresEfectivaAnual save(TasaInteresEfectivaAnual tasaInteresEfectivaAnual);
	
	public void remove(TasaInteresEfectivaAnual tasaInteresEfectivaAnual);
}