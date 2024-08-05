package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entidad_vista_accion")
public class EntidadVistaAccion extends BaseEntity {

	private static final long serialVersionUID = 4838792701929219757L;

	@Column(name = "visible")
	private Boolean visible;

	@ManyToOne(optional = false)
	@JoinColumn(name = "entidad_vista_id", nullable = false)
	private EntidadVista entidadVista;

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "entidad_accion_id", nullable = false)
	private EntidadAccion entidadAccion;

	public EntidadVista getEntidadVista() {
		return entidadVista;
	}

	public void setEntidadVista(EntidadVista entidadVista) {
		this.entidadVista = entidadVista;
	}

	public EntidadAccion getEntidadAccion() {
		return entidadAccion;
	}

	public void setEntidadAccion(EntidadAccion entidadAccion) {
		this.entidadAccion = entidadAccion;
	}
}