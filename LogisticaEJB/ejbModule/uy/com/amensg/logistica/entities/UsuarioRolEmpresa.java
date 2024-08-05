package uy.com.amensg.logistica.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seguridad_usuario_rol_empresa")
public class UsuarioRolEmpresa extends BaseEntity {

	private static final long serialVersionUID = 986970725319758062L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "rol_id", nullable = false)
	private Rol rol;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}