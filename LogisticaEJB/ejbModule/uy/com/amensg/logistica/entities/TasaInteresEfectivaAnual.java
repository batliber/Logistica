package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tasa_interes_efectiva_anual")
public class TasaInteresEfectivaAnual extends BaseEntity {

	private static final long serialVersionUID = 6342366217301384115L;
	
	@Column(name = "cuotas_desde")
	private Long cuotasDesde;
	
	@Column(name = "cuotas_hasta")
	private Long cuotasHasta;
	
	@Column(name = "monto_desde")
	private Double montoDesde;
	
	@Column(name = "monto_hasta")
	private Double montoHasta;
	
	@Column(name = "fecha_vigencia_hasta")
	private Date fechaVigenciaHasta;
	
	@Column(name = "valor")
	private Double valor;

	public Long getCuotasDesde() {
		return cuotasDesde;
	}

	public void setCuotasDesde(Long cuotasDesde) {
		this.cuotasDesde = cuotasDesde;
	}

	public Long getCuotasHasta() {
		return cuotasHasta;
	}

	public void setCuotasHasta(Long cuotasHasta) {
		this.cuotasHasta = cuotasHasta;
	}

	public Double getMontoDesde() {
		return montoDesde;
	}

	public void setMontoDesde(Double montoDesde) {
		this.montoDesde = montoDesde;
	}

	public Double getMontoHasta() {
		return montoHasta;
	}

	public void setMontoHasta(Double montoHasta) {
		this.montoHasta = montoHasta;
	}

	public Date getFechaVigenciaHasta() {
		return fechaVigenciaHasta;
	}

	public void setFechaVigenciaHasta(Date fechaVigenciaHasta) {
		this.fechaVigenciaHasta = fechaVigenciaHasta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}