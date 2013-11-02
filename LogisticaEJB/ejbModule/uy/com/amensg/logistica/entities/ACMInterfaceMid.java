package uy.com.amensg.logistica.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "acm_interface_mid")
public class ACMInterfaceMid implements Serializable {

	private static final long serialVersionUID = -5665636107145717107L;

	@Id
	@Column(name = "mid")
	private String mid;

	@Column(name = "estado")
	private Long estado;

	@Column(name = "proceso_id")
	private Long procesoId;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(Long procesoId) {
		this.procesoId = procesoId;
	}
}