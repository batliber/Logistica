package uy.com.amensg.logistica.web.dwr;

import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.web.entities.FormaPagoTO;

public class FormaPagoDWR {

	public static FormaPagoTO transform(FormaPago formaPago) {
		FormaPagoTO result = new FormaPagoTO();
		
		result.setDescripcion(formaPago.getDescripcion());
		result.setOrden(formaPago.getOrden());
		
		result.setFcre(formaPago.getFcre());
		result.setFact(formaPago.getFact());
		result.setId(formaPago.getId());
		result.setTerm(formaPago.getTerm());
		result.setUact(formaPago.getUact());
		result.setUcre(formaPago.getUcre());
		
		return result;
	}
}