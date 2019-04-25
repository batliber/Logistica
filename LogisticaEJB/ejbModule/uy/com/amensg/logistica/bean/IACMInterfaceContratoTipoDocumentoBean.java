package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContratoTipoDocumento;

@Remote
public interface IACMInterfaceContratoTipoDocumentoBean {

	public Collection<ACMInterfaceContratoTipoDocumento> list();
}