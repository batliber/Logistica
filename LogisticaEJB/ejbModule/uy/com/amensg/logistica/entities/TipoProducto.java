package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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