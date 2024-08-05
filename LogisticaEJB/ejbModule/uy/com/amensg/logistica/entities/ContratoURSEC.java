package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "contrato_ursec")
public class ContratoURSEC extends BaseEntity {

	private static final long serialVersionUID = -6729178003820544608L;

	@Column(name = "mid")
	private Long mid;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}
}