package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoRoutingHistoryANTEL;

@Remote
public interface IContratoRoutingHistoryANTELBean {
	
	public Collection<ContratoRoutingHistoryANTEL> listByContratoId(Long contratoId);
	
	public Collection<ContratoRoutingHistoryANTEL> listByNumeroTramite(Long numeroTramite);
}