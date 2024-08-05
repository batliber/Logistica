package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio_infraestructura_comprobacion")
public class ServicioInfraestructuraComprobacion extends BaseEntity {

	private static final long serialVersionUID = -4730844824563248324L;
	
	@Column(name = "fecha")
	private Date fecha;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "servicio_infraestructura_id")
	private ServicioInfraestructura servicioInfraestructura;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "estado_servicio_infraestructura_id")
	private EstadoServicioInfraestructura estadoServicioInfraestructura;
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public EstadoServicioInfraestructura getEstadoServicioInfraestructura() {
		return estadoServicioInfraestructura;
	}

	public void setEstadoServicioInfraestructura(EstadoServicioInfraestructura estadoServicioInfraestructura) {
		this.estadoServicioInfraestructura = estadoServicioInfraestructura;
	}

	public ServicioInfraestructura getServicioInfraestructura() {
		return servicioInfraestructura;
	}

	public void setServicioInfraestructura(ServicioInfraestructura servicioInfraestructura) {
		this.servicioInfraestructura = servicioInfraestructura;
	}
}