package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_producto")
public class TipoProducto extends BaseEntity {

	private static final long serialVersionUID = -1967815057960387102L;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "forma_pago_cuota_id", nullable = true)
	private FormaPagoCuota formaPagoCuota;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public FormaPagoCuota getFormaPagoCuota() {
		return formaPagoCuota;
	}

	public void setFormaPagoCuota(FormaPagoCuota formaPagoCuota) {
		this.formaPagoCuota = formaPagoCuota;
	}
}