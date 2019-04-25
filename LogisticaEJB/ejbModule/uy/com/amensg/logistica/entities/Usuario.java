package uy.com.amensg.logistica.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "intentos_fallidos_login")
	private Long intentosFallidosLogin;
	
	@Column(name = "bloqueado")
	private Boolean bloqueado;
	
	@Column(name = "cambio_contrasena_proximo_login")
	private Boolean cambioContrasenaProximoLogin;
	
	@OneToMany(mappedBy = "usuario")
	private Collection<UsuarioRolEmpresa> usuarioRolEmpresas;
	
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
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getIntentosFallidosLogin() {
		return intentosFallidosLogin;
	}

	public void setIntentosFallidosLogin(Long intentosFallidosLogin) {
		this.intentosFallidosLogin = intentosFallidosLogin;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Boolean getCambioContrasenaProximoLogin() {
		return cambioContrasenaProximoLogin;
	}

	public void setCambioContrasenaProximoLogin(Boolean cambioContrasenaProximoLogin) {
		this.cambioContrasenaProximoLogin = cambioContrasenaProximoLogin;
	}

	public Collection<UsuarioRolEmpresa> getUsuarioRolEmpresas() {
		return usuarioRolEmpresas;
	}

	public void setUsuarioRolEmpresas(Collection<UsuarioRolEmpresa> usuarioRolEmpresas) {
		this.usuarioRolEmpresas = usuarioRolEmpresas;
	}
}