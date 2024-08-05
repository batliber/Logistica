package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "recarga_tipo_movimiento")
public class RecargaTipoMovimiento extends BaseEntity {

	private static final long serialVersionUID = -5620456130142863309L;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "signo")
	private Long signo;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getSigno() {
		return signo;
	}

	public void setSigno(Long signo) {
		this.signo = signo;
	}
}