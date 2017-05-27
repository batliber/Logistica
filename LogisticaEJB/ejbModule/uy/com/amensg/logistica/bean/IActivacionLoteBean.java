package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ActivacionLote;

@Remote
public interface IActivacionLoteBean {

	public ActivacionLote save(ActivacionLote activacionLote);
}