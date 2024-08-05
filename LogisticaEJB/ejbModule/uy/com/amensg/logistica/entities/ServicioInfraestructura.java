package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio_infraestructura")
public class ServicioInfraestructura extends BaseEntity {

	private static final long serialVersionUID = -4730844824563248324L;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha_ultima_comprobacion")
	private Date fechaUltimaComprobacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "estado_servicio_infraestructura_id")
	private EstadoServicioInfraestructura estadoServicioInfraestructura;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaUltimaComprobacion() {
		return fechaUltimaComprobacion;
	}

	public void setFechaUltimaComprobacion(Date fechaUltimaComprobacion) {
		this.fechaUltimaComprobacion = fechaUltimaComprobacion;
	}

	public EstadoServicioInfraestructura getEstadoServicioInfraestructura() {
		return estadoServicioInfraestructura;
	}

	public void setEstadoServicioInfraestructura(EstadoServicioInfraestructura estadoServicioInfraestructura) {
		this.estadoServicioInfraestructura = estadoServicioInfraestructura;
	}
}