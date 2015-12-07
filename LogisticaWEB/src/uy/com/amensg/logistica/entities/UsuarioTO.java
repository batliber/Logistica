package uy.com.amensg.logistica.entities;

import java.util.Collection;
import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class UsuarioTO extends BaseTO {

	private String login;
	private String contrasena;
	private String nombre;
	private Date fechaBaja;
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

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
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