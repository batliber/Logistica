package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class DisponibilidadEntregaEmpresaZonaTurnoTO extends BaseTO {

	private Long dia;
	private Long cantidad;
	private EmpresaTO empresa;
	private ZonaTO zona;
	private TurnoTO turno;

	public Long getDia() {
		return dia;
	}

	public void setDia(Long dia) {
		this.dia = dia;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public ZonaTO getZona() {
		return zona;
	}

	public void setZona(ZonaTO zona) {
		this.zona = zona;
	}

	public TurnoTO getTurno() {
		return turno;
	}

	public void setTurno(TurnoTO turno) {
		this.turno = turno;
	}
}