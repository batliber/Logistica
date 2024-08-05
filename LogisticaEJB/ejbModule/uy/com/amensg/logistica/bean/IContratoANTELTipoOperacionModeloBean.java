package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoANTELTipoOperacionModelo;

@Remote
public interface IContratoANTELTipoOperacionModeloBean {

	public ContratoANTELTipoOperacionModelo getByTipoOperacion(Long tipoOperacion);
}