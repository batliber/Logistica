package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contrato_archivo_adjunto")
public class ContratoArchivoAdjunto extends BaseEntity {

	private static final long serialVersionUID = -6085878202412524832L;

	@Column(name = "url")
	private String url;
	
	@Column(name = "fecha_subida")
	private Date fechaSubida;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "contrato_id", nullable = false)
	private Contrato contrato;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_archivo_adjunto_id")
	private TipoArchivoAdjunto tipoArchivoAdjunto;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public TipoArchivoAdjunto getTipoArchivoAdjunto() {
		return tipoArchivoAdjunto;
	}

	public void setTipoArchivoAdjunto(TipoArchivoAdjunto tipoArchivoAdjunto) {
		this.tipoArchivoAdjunto = tipoArchivoAdjunto;
	}
}