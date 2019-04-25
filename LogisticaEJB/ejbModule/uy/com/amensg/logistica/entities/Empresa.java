	package uy.com.amensg.logistica.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa extends BaseEntity {

	private static final long serialVersionUID = 9161726892635036606L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "logo_url")
	private String logoURL;

	@Column(name = "codigo_promotor")
	private Long codigoPromotor;
	
	@Column(name = "nombre_contrato")
	private String nombreContrato;
	
	@Column(name = "nombre_sucursal")
	private String nombreSucursal;
	
	@Column(name = "direccion")
	private String direccion;
	
	@OneToMany
	@JoinTable(name = "forma_pago_empresa", 
		joinColumns=@JoinColumn(name = "empresa_id"),
		inverseJoinColumns=@JoinColumn(name = "forma_pago_id")
	)
	private Set<FormaPago> formaPagos;
	
	@OneToMany
	@JoinTable(name = "empresa_usuario_contrato", 
		joinColumns=@JoinColumn(name = "empresa_id"),
		inverseJoinColumns=@JoinColumn(name = "usuario_id")
	)
	private Set<Usuario> empresaUsuarioContratos;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public Long getCodigoPromotor() {
		return codigoPromotor;
	}

	public void setCodigoPromotor(Long codigoPromotor) {
		this.codigoPromotor = codigoPromotor;
	}

	public String getNombreContrato() {
		return nombreContrato;
	}

	public void setNombreContrato(String nombreContrato) {
		this.nombreContrato = nombreContrato;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set<FormaPago> getFormaPagos() {
		return formaPagos;
	}

	public void setFormaPagos(Set<FormaPago> formaPagos) {
		this.formaPagos = formaPagos;
	}

	public Set<Usuario> getEmpresaUsuarioContratos() {
		return empresaUsuarioContratos;
	}

	public void setEmpresaUsuarioContratos(Set<Usuario> empresaUsuarioContratos) {
		this.empresaUsuarioContratos = empresaUsuarioContratos;
	}
}