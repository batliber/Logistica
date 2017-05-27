package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;

@Remote
public interface IContratoArchivoAdjuntoBean {

	public Collection<ContratoArchivoAdjunto> listByContratoId(Long id);
}