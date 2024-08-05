package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoArchivoAdjunto;

@Remote
public interface ITipoArchivoAdjuntoBean {

	public Collection<TipoArchivoAdjunto> list();
}