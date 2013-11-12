package uy.com.amensg.logistica.entities;

import java.util.Collection;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class MetadataConsultaTO {

	private Collection<MetadataCondicionTO> metadataCondiciones;
	private Collection<MetadataOrdenacionTO> metadataOrdenaciones;
	private Long tamanoMuestra;
	private Long tamanoSubconjunto;

	public Collection<MetadataCondicionTO> getMetadataCondiciones() {
		return metadataCondiciones;
	}

	public void setMetadataCondiciones(
			Collection<MetadataCondicionTO> metadataCondiciones) {
		this.metadataCondiciones = metadataCondiciones;
	}

	public Collection<MetadataOrdenacionTO> getMetadataOrdenaciones() {
		return metadataOrdenaciones;
	}

	public void setMetadataOrdenaciones(
			Collection<MetadataOrdenacionTO> metadataOrdenaciones) {
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
}