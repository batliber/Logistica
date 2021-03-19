package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riesgo_crediticio_paraguay_bicsa")
public class RiesgoCrediticioParaguayBICSA extends BaseEntity {

	private static final long serialVersionUID = 281686691834363386L;

	@Column(name = "datos")
	private String datos;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "riesgo_crediticio_paraguay_id", nullable = false)
	private RiesgoCrediticioParaguay riesgoCrediticioParaguay;

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public RiesgoCrediticioParaguay getRiesgoCrediticioParaguay() {
		return riesgoCrediticioParaguay;
	}

	public void setRiesgoCrediticioParaguay(RiesgoCrediticioParaguay riesgoCrediticioParaguay) {
		this.riesgoCrediticioParaguay = riesgoCrediticioParaguay;
	}
}