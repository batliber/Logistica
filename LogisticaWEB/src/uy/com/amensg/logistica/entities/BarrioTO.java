package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class BarrioTO extends BaseTO {

	private String nombre;
	private DepartamentoTO departamento;
	private ZonaTO zona;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DepartamentoTO getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoTO departamento) {
		this.departamento = departamento;
	}

	public ZonaTO getZona() {
		return zona;
	}

	public void setZona(ZonaTO zona) {
		this.zona = zona;
	}
}