package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "vs_contrato_routing_history_antel")
public class ContratoRoutingHistoryANTEL extends BaseEntity {

	private static final long serialVersionUID = -1092269527976265181L;

	@Column(name = "fecha")
	private Date fecha;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "contrato_id", nullable = false)
	private ContratoANTEL contrato;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "rol_id", nullable = true)
	private Rol rol;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;

	@ManyToOne(optional = true)
	@JoinColumn(name = "estado_id")
	private EstadoANTEL estado;
	
	@Transient
	private Usuario usuarioAct;
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ContratoANTEL getContrato() {
		return contrato;
	}

	public void setContrato(ContratoANTEL contrato) {
		this.contrato = contrato;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public EstadoANTEL getEstado() {
		return estado;
	}

	public void setEstado(EstadoANTEL estado) {
		this.estado = estado;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioAct() {
		return usuarioAct;
	}

	public void setUsuarioAct(Usuario usuarioAct) {
		this.usuarioAct = usuarioAct;
	}
}