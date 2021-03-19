package uy.com.amensg.logistica.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recarga_punto_venta_usuario")
public class RecargaPuntoVentaUsuario extends BaseEntity {

	private static final long serialVersionUID = -3886756588305715667L;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id")
	private PuntoVenta puntoVenta;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
}