package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class SeguridadAuditoriaTO extends BaseTO {

	private Date fecha;
	private SeguridadTipoEventoTO seguridadTipoEvento;
	private UsuarioTO usuario;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public SeguridadTipoEventoTO getSeguridadTipoEvento() {
		return seguridadTipoEvento;
	}

	public void setSeguridadTipoEvento(SeguridadTipoEventoTO seguridadTipoEvento) {
		this.seguridadTipoEvento = seguridadTipoEvento;
	}

	public UsuarioTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioTO usuario) {
		this.usuario = usuario;
	}
}