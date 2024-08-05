package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entidad_vista_campo")
public class EntidadVistaCampo extends BaseEntity {

	private static final long serialVersionUID = 931308705509510809L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "visible")
	private Boolean visible;

	@Column(name = "editable")
	private Boolean editable;

	@Column(name = "obligatorio")
	private Boolean obligatorio;

	@ManyToOne(optional = false)
	@JoinColumn(name = "entidad_vista_id", nullable = false)
	private EntidadVista entidadVista;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public EntidadVista getEntidadVista() {
		return entidadVista;
	}

	public void setEntidadVista(EntidadVista entidadVista) {
		this.entidadVista = entidadVista;
	}
}