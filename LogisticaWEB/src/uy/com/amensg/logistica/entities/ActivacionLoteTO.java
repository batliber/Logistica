package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ActivacionLoteTO extends BaseTO {

	private Long numero;
	private String nombreArchivo;
	private Date fechaImportacion;
	private EmpresaTO empresa;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}
}