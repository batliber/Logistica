package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moneda")
public class Moneda extends BaseEntity {

	private static final long serialVersionUID = 7480274473377862196L;

	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "simbolo")
	private String simbolo;

	@Column(name = "codigo_iso")
	private String codigoISO;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getCodigoISO() {
		return codigoISO;
	}

	public void setCodigoISO(String codigoISO) {
		this.codigoISO = codigoISO;
	}
}