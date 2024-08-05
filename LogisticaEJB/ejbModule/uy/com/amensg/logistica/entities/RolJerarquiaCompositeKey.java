package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class RolJerarquiaCompositeKey implements Serializable {

	private static final long serialVersionUID = -3481517630767119280L;

	private Long rolId;
	
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

	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}