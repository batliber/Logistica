package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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