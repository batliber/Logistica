package uy.com.amensg.logistica.dwr;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.entities.SeguridadTipoEvento;
import uy.com.amensg.logistica.entities.SeguridadTipoEventoTO;

@RemoteProxy
public class SeguridadTipoEventoDWR {

	public static SeguridadTipoEventoTO transform(SeguridadTipoEvento seguridadTipoEvento) {
		SeguridadTipoEventoTO result = new SeguridadTipoEventoTO();
		
		result.setDescripcion(seguridadTipoEvento.getDescripcion());
		
		result.setFact(seguridadTipoEvento.getFcre());
		result.setFact(seguridadTipoEvento.getFact());
		result.setId(seguridadTipoEvento.getId());
		result.setTerm(seguridadTipoEvento.getTerm());
		result.setUact(seguridadTipoEvento.getUact());
		result.setUcre(seguridadTipoEvento.getUcre());
		
		return result;
	}
}