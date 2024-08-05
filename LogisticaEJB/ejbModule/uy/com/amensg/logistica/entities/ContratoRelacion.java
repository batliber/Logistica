package uy.com.amensg.logistica.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contrato_relacion")
public class ContratoRelacion extends BaseEntity {

	private static final long serialVersionUID = -3440600163489070640L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "contrato_id", nullable = false)
	private Contrato contrato;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "contrato_relacionado_id", nullable = false)
	private Contrato contratoRelacionado;

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Contrato getContratoRelacionado() {
		return contratoRelacionado;
	}

	public void setContratoRelacionado(Contrato contratoRelacionado) {
		this.contratoRelacionado = contratoRelacionado;
	}
}