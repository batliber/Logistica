package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "unidad_indexada")
public class UnidadIndexada extends BaseEntity {

	private static final long serialVersionUID = 4239104475836280542L;

	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "fecha_vigencia_hasta")
	private Date fechaVigenciaHasta;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getFechaVigenciaHasta() {
		return fechaVigenciaHasta;
	}

	public void setFechaVigenciaHasta(Date fechaVigenciaHasta) {
		this.fechaVigenciaHasta = fechaVigenciaHasta;
	}
}