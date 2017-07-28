package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class PuntoVentaTO extends BaseTO {

	private String nombre;
	private String direccion;
	private Date fechaBaja;
	private Double latitud;
	private Double longitud;
	private Double precision;
	private String telefono;
	private String documento;
	private String contacto;
	private DepartamentoTO departamento;
	private BarrioTO barrio;
	private EstadoPuntoVentaTO estadoPuntoVenta;

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

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public DepartamentoTO getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoTO departamento) {
		this.departamento = departamento;
	}

	public BarrioTO getBarrio() {
		return barrio;
	}

	public void setBarrio(BarrioTO barrio) {
		this.barrio = barrio;
	}

	public EstadoPuntoVentaTO getEstadoPuntoVenta() {
		return estadoPuntoVenta;
	}

	public void setEstadoPuntoVenta(EstadoPuntoVentaTO estadoPuntoVenta) {
		this.estadoPuntoVenta = estadoPuntoVenta;
	}
}