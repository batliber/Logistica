package uy.com.amensg.logistica.dwr;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.EstadoTO;

@RemoteProxy
public class EstadoDWR {

	public static EstadoTO transform(Estado estado) {
		EstadoTO estadoTO = new EstadoTO();
		
		estadoTO.setNombre(estado.getNombre());
		
		estadoTO.setFact(estado.getFact());
		estadoTO.setId(estado.getId());
		estadoTO.setTerm(estado.getTerm());
		estadoTO.setUact(estado.getUact());
		
		return estadoTO;
	}
	
	public static Estado transform(EstadoTO estadoTO) {
		Estado estado = new Estado();
		
		estado.setNombre(estadoTO.getNombre());
		
		estado.setFact(estadoTO.getFact());
		estado.setId(estadoTO.getId());
		estado.setTerm(estadoTO.getTerm());
		estado.setUact(estadoTO.getUact());
		
		return estado;
	}
}