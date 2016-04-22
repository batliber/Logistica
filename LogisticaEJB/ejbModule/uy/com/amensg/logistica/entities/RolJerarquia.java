package uy.com.amensg.logistica.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seguridad_rol_jerarquia")
public class RolJerarquia implements Serializable {

	private static final long serialVersionUID = -7969516374237541090L;

	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "rol_id", nullable = false)
	private Rol rol;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "rol_subordinado_id", nullable = false)
	private Rol rolSubordinado;

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Rol getRolSubordinado() {
		return rolSubordinado;
	}

	public void setRolSubordinado(Rol rolSubordinado) {
		this.rolSubordinado = rolSubordinado;
	}
}