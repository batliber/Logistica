package uy.com.amensg.logistica.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(RolJerarquiaCompositeKey.class)
@Table(name = "seguridad_rol_jerarquia")
public class RolJerarquia implements Serializable {

	private static final long serialVersionUID = -7969516374237541090L;

	@Id
	@Column(name = "rol_id")
	private Long rolId;
	
	@Id
	@Column(name = "rol_subordinado_id")
	private Long rolSubordinadoId;

	public Long getRolId() {
		return rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	public Long getRolSubordinadoId() {
		return rolSubordinadoId;
	}

	public void setRolSubordinadoId(Long rolSubordinadoId) {
		this.rolSubordinadoId = rolSubordinadoId;
	}
}