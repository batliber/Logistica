package uy.com.amensg.logistica.web.entities;

import java.util.Collection;
import java.util.Date;

public class UsuarioTO extends BaseTO {

	private String login;
	private String contrasena;
	private String nombre;
	private String documento;
	private Date fechaBaja;
	private Long intentosFallidosLogin;
	private Boolean bloqueado;
	private Boolean cambioContrasenaProximoLogin;
	private Collection<UsuarioRolEmpresaTO> usuarioRolEmpresas;
	private Collection<MenuTO> menus;

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

	public Collection<UsuarioRolEmpresaTO> getUsuarioRolEmpresas() {
		return usuarioRolEmpresas;
	}

	public void setUsuarioRolEmpresas(
			Collection<UsuarioRolEmpresaTO> usuarioRolEmpresas) {
		this.usuarioRolEmpresas = usuarioRolEmpresas;
	}

	public Collection<MenuTO> getMenus() {
		return menus;
	}

	public void setMenus(Collection<MenuTO> menus) {
		this.menus = menus;
	}
}