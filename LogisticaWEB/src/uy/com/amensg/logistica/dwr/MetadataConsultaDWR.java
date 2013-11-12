package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataCondicionTO;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.MetadataOrdenacionTO;

public class MetadataConsultaDWR {

	public static MetadataConsulta transform(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		for (MetadataCondicionTO metadataCondicionTO : metadataConsultaTO.getMetadataCondiciones()) {
			metadataCondiciones.add(transform(metadataCondicionTO));
		}
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		Collection<MetadataOrdenacion> metadataOrdenaciones = new LinkedList<MetadataOrdenacion>();
		
		for (MetadataOrdenacionTO metadataOrdenacionTO : metadataConsultaTO.getMetadataOrdenaciones()) {
			metadataOrdenaciones.add(transform(metadataOrdenacionTO));
		}
		
		metadataConsulta.setMetadataOrdenaciones(metadataOrdenaciones);
		
		metadataConsulta.setTamanoMuestra(metadataConsultaTO.getTamanoMuestra());
		metadataConsulta.setTamanoSubconjunto(metadataConsultaTO.getTamanoSubconjunto());
		
		return metadataConsulta;
	}

	public static MetadataCondicion transform(MetadataCondicionTO metadataCondicionTO) {
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		
		metadataCondicion.setCampo(metadataCondicionTO.getCampo());
		metadataCondicion.setOperador(metadataCondicionTO.getOperador());
		metadataCondicion.setValores(metadataCondicionTO.getValores());
		
		return metadataCondicion;
	}
	
	public static MetadataOrdenacion transform(MetadataOrdenacionTO metadataOrdenacionTO) {
		MetadataOrdenacion metadataOrdenacion = new MetadataOrdenacion();
		
		metadataOrdenacion.setAscendente(metadataOrdenacionTO.getAscendente());
		metadataOrdenacion.setCampo(metadataOrdenacionTO.getCampo());
		
		return metadataOrdenacion;
	}
	
	public static MetadataConsultaResultadoTO transform(MetadataConsultaResultado metadataConsultaResultado) {
		MetadataConsultaResultadoTO metadataConsultaResultadoTO = new MetadataConsultaResultadoTO();
		
		metadataConsultaResultadoTO.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		metadataConsultaResultadoTO.setRegistrosMuestra(metadataConsultaResultado.getRegistrosMuestra());
		
		return metadataConsultaResultadoTO;
	}
}