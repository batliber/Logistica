package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "configuracion")
public class Configuracion extends BaseEntity {

	private static final long serialVersionUID = 7195537147856011800L;

	@Column(name = "clave")
	private String clave;
	
	@Column(name = "valor")
	private String valor;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}