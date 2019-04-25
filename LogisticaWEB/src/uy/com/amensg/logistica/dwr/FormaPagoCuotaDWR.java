package uy.com.amensg.logistica.dwr;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.entities.FormaPagoCuota;
import uy.com.amensg.logistica.entities.FormaPagoCuotaTO;

@RemoteProxy
public class FormaPagoCuotaDWR {

	public static FormaPagoCuotaTO transform(FormaPagoCuota formaPagoCuota) {
		FormaPagoCuotaTO result = new FormaPagoCuotaTO();
		
		result.setDescripcion(formaPagoCuota.getDescripcion());
		
		result.setFcre(formaPagoCuota.getFcre());
		result.setFact(formaPagoCuota.getFact());
		result.setId(formaPagoCuota.getId());
		result.setTerm(formaPagoCuota.getTerm());
		result.setUact(formaPagoCuota.getUact());
		result.setUcre(formaPagoCuota.getUcre());
		
		return result;
	}
}