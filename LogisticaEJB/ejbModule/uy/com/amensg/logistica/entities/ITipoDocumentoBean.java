package uy.com.amensg.logistica.entities;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface ITipoDocumentoBean {

	public Collection<TipoDocumento> list();
}