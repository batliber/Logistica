package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Collection;

public class MetadataConsulta implements Serializable {

	private static final long serialVersionUID = -6422402083424469524L;

	private Collection<MetadataCondicion> metadataCondiciones;
	private Collection<MetadataOrdenacion> metadataOrdenaciones;
	private Long tamanoMuestra;
	private Long tamanoSubconjunto;

	public Collection<MetadataCondicion> getMetadataCondiciones() {
		return metadataCondiciones;
	}

	public void setMetadataCondiciones(
			Collection<MetadataCondicion> metadataCondiciones) {
		this.metadataCondiciones = metadataCondiciones;
	}

	public Collection<MetadataOrdenacion> getMetadataOrdenaciones() {
		return metadataOrdenaciones;
	}

	public void setMetadataOrdenaciones(
			Collection<MetadataOrdenacion> metadataOrdenaciones) {
		this.metadataOrdenaciones = metadataOrdenaciones;
	}

	public Long getTamanoMuestra() {
		return tamanoMuestra;
	}

	public void setTamanoMuestra(Long tamanoMuestra) {
		this.tamanoMuestra = tamanoMuestra;
	}

	public Long getTamanoSubconjunto() {
		return tamanoSubconjunto;
	}

	public void setTamanoSubconjunto(Long tamanoSubconjunto) {
		this.tamanoSubconjunto = tamanoSubconjunto;
	}

	public String asJSONString() {
		String result = 
			"{"
				+ " tamanoMuestra: " + this.tamanoMuestra + "," 
				+ " tamanoSubconjunto: " + this.tamanoSubconjunto + ","
				+ " metadataCondiciones: [";
		
		boolean firstCondicion = true;
		for (MetadataCondicion metadataCondicion : this.metadataCondiciones) {
			if (firstCondicion) {
				result += 
					" {";
				firstCondicion = false;
			} else {
				result +=
					", {";
			}
			
			result +=
						" campo: " + metadataCondicion.getCampo() + ","
						+ " operador: " + metadataCondicion.getOperador() + ","
						+ " valores: [";
			
			boolean firstValor = true;
			for (String valor : metadataCondicion.getValores()) {
				if (firstValor) {
					result +=
							" ";
					firstValor = false;
				} else {
					result +=
							", ";
				}
				
				result +=
							valor;
			}
			
			result +=
						" ]"
					+ " }";
		}
		
		result += 
				" ],"
				+ " metadataOrdenaciones: [";
		
		boolean firstOrdenacion = true;
		for (MetadataOrdenacion metadataOrdenacion : this.metadataOrdenaciones) {
			if (firstOrdenacion) {
				result += 
					" {";
				firstOrdenacion = false;
			} else {
				result +=
					", {";
			}
			
			result +=
						" campo: " + metadataOrdenacion.getCampo() + ","
						+ " ascendente: " + (metadataOrdenacion.getAscendente() ? "true" : "false")
					+ " }";
		}
		
		result += 
				" ]"
			+ " }";
		
		return result;
	}
}