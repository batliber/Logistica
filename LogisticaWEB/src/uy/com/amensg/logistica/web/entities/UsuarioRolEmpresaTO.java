package uy.com.amensg.logistica.web.entities;

public class UsuarioRolEmpresaTO extends BaseTO {

	private UsuarioTO usuario;
	private RolTO rol;
	private EmpresaTO empresa;

	public UsuarioTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioTO usuario) {
		this.usuario = usuario;
	}

	public RolTO getRol() {
		return rol;
	}

	public void setRol(RolTO rol) {
		this.rol = rol;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}
}