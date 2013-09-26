package uy.com.amensg.logistica.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "acm_interface_contrato")
public class ACMInterfaceContrato implements Serializable {

	private static final long serialVersionUID = 5858491604040285536L;

	@Id
	@Column(name = "mid")
	private String mid;

	@Column(name = "fecha_fin_contrato")
	private String fechaFinContrato;
	
	@Column(name = "tipo_contrato")
	private String tipoContrato;
	
	@Column(name = "documento")
	private String documento;

	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "localidad")
	private String localidad;
	
	@Column(name = "proceso_id")
	private Long procesoId;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(String fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Long getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(Long procesoId) {
		this.procesoId = procesoId;
	}
}