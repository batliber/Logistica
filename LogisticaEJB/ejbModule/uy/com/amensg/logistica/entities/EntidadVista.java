package uy.com.amensg.logistica.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "entidad_vista")
public class EntidadVista extends BaseEntity {

	private static final long serialVersionUID = 5263083728067676350L;

	@Column(name = "nombre")
	private String nombre;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "entidad_id", nullable = false)
	private Entidad entidad;

	@OneToMany(mappedBy = "entidadVista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<EntidadVistaCampo> entidadVistaCampos;

	@OneToMany(mappedBy = "entidadVista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<EntidadVistaAccion> entidadVistaAcciones;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public Set<EntidadVistaCampo> getEntidadVistaCampos() {
		return entidadVistaCampos;
	}

	public void setEntidadVistaCampos(Set<EntidadVistaCampo> entidadVistaCampos) {
		this.entidadVistaCampos = entidadVistaCampos;
	}

	public Set<EntidadVistaAccion> getEntidadVistaAcciones() {
		return entidadVistaAcciones;
	}

	public void setEntidadVistaAcciones(Set<EntidadVistaAccion> entidadVistaAcciones) {
		this.entidadVistaAcciones = entidadVistaAcciones;
	}
}