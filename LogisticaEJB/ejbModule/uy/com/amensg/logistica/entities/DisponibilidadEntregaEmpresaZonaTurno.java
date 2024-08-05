package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "disponibilidad_entrega_empresa_zona_turno")
public class DisponibilidadEntregaEmpresaZonaTurno extends BaseEntity {

	private static final long serialVersionUID = -898529265819089291L;

	@Column(name = "dia")
	private Long dia;
	
	@Column(name = "cantidad")
	private Long cantidad;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "zona_id", nullable = false)
	private Zona zona;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "turno_id", nullable = false)
	private Turno turno;

	public Long getDia() {
		return dia;
	}

	public void setDia(Long dia) {
		this.dia = dia;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
}