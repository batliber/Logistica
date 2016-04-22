package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ContratoRoutingHistoryTO extends BaseTO {

	private Date fecha;
	private ContratoTO contrato;
	private EmpresaTO empresa;
	private RolTO rol;
	private UsuarioTO usuario;
	private EstadoTO estado;
	private UsuarioTO usuarioAct;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ContratoTO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoTO contrato) {
		this.contrato = contrato;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public RolTO getRol() {
		return rol;
	}

	public void setRol(RolTO rol) {
		this.rol = rol;
	}

	public UsuarioTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioTO usuario) {
		this.usuario = usuario;
	}
	
	public EstadoTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoTO estado) {
		this.estado = estado;
	}

	public UsuarioTO getUsuarioAct() {
		return usuarioAct;
	}

	public void setUsuarioAct(UsuarioTO usuarioAct) {
		this.usuarioAct = usuarioAct;
	}
}