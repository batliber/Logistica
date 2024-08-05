package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contrato_antel_tipo_operacion_modelo")
public class ContratoANTELTipoOperacionModelo extends BaseEntity {

	private static final long serialVersionUID = -7686241785746881742L;
	
	@Column(name = "tipo_operacion")
	private Long tipoOperacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "modelo_id", nullable = true)
	private Modelo modelo;

	public Long getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Long tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
}