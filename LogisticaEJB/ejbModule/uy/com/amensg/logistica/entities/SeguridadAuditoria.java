package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seguridad_auditoria")
public class SeguridadAuditoria extends BaseEntity {

	private static final long serialVersionUID = 5396119873019765312L;

	@Column(name = "fecha")
	private Date fecha;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "seguridad_tipo_evento_id", nullable = false)
	private SeguridadTipoEvento seguridadTipoEvento;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "seguridad_usuario_id", nullable = false)
	private Usuario usuario;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public SeguridadTipoEvento getSeguridadTipoEvento() {
		return seguridadTipoEvento;
	}

	public void setSeguridadTipoEvento(SeguridadTipoEvento seguridadTipoEvento) {
		this.seguridadTipoEvento = seguridadTipoEvento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}