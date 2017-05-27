package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ModeloTO extends BaseTO {

	private String descripcion;
	private Date fechaBaja;
	private MarcaTO marca;
	private EmpresaServiceTO empresaService;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public MarcaTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaTO marca) {
		this.marca = marca;
	}

	public EmpresaServiceTO getEmpresaService() {
		return empresaService;
	}

	public void setEmpresaService(EmpresaServiceTO empresaService) {
		this.empresaService = empresaService;
	}
}