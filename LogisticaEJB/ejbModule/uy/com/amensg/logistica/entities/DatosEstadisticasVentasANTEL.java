package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vs_estadisticas_antel_01")
public class DatosEstadisticasVentasANTEL implements Serializable {

	private static final long serialVersionUID = -4737182779045934060L;

	@Id
	@Column(name = "empresa_id")
	private Long empresaId;
	
	@Id
	@Column(name = "fecha")
	private Date fecha;
	
	@Id
	@Column(name = "status")
	private String status;
	
	@Column(name = "cantidad")
	private Long cantidad;

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}