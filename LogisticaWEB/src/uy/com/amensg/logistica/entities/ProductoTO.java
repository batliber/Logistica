package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ProductoTO extends BaseTO {

	private String imei;
	private String descripcion;
	private Date fechaBaja;
	private MarcaTO marca;
	private ModeloTO modelo;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public MarcaTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaTO marca) {
		this.marca = marca;
	}

	public ModeloTO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloTO modelo) {
		this.modelo = modelo;
	}

	public EmpresaServiceTO getEmpresaService() {
		return empresaService;
	}

	public void setEmpresaService(EmpresaServiceTO empresaService) {
		this.empresaService = empresaService;
	}
}