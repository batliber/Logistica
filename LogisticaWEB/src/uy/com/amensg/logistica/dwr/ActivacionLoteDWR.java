package uy.com.amensg.logistica.dwr;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.entities.ActivacionLote;
import uy.com.amensg.logistica.entities.ActivacionLoteTO;

@RemoteProxy
public class ActivacionLoteDWR {

	public static ActivacionLoteTO transform(ActivacionLote activacionLote) {
		ActivacionLoteTO result = new ActivacionLoteTO();
		
		result.setFechaImportacion(activacionLote.getFechaImportacion());
		result.setNombreArchivo(activacionLote.getNombreArchivo());
		result.setNumero(activacionLote.getNumero());
		
		result.setFact(activacionLote.getFact());
		result.setId(activacionLote.getId());
		result.setTerm(activacionLote.getTerm());
		result.setUact(activacionLote.getUact());
		
		return result;
	}
}