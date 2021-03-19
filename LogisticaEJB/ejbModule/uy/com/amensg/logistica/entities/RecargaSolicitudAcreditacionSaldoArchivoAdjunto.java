package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recarga_solicitud_acreditacion_saldo_archivo_adjunto")
public class RecargaSolicitudAcreditacionSaldoArchivoAdjunto extends BaseEntity {

	private static final long serialVersionUID = -9031955031573651044L;

	@Column(name = "url")
	private String url;
	
	@Column(name = "fecha_subida")
	private Date fechaSubida;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "recarga_solicitud_acreditacion_saldo_id", nullable = false)
	private RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo;
	
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

	public RecargaSolicitudAcreditacionSaldo getRecargaSolicitudAcreditacionSaldo() {
		return recargaSolicitudAcreditacionSaldo;
	}

	public void setRecargaSolicitudAcreditacionSaldo(RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo) {
		this.recargaSolicitudAcreditacionSaldo = recargaSolicitudAcreditacionSaldo;
	}
}