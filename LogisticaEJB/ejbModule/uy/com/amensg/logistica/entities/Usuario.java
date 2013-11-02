package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "seguridad_usuario")
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 7117721335308915684L;

	@Column(name = "login")
	private String login;

	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "nombre")
	private String nombre;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}