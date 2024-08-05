package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="recarga_estado_acreditacion_saldo")
public class RecargaEstadoAcreditacionSaldo extends BaseEntity {

	private static final long serialVersionUID = 2097571938023965819L;

	@Column (name = "nombre")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}