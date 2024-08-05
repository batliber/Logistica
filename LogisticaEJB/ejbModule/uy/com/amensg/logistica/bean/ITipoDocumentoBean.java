package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.TipoDocumento;

@Remote
public interface ITipoDocumentoBean {

	public Collection<TipoDocumento> list();
}