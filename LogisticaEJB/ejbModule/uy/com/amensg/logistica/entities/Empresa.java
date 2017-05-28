package uy.com.amensg.logistica.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@OneToMany( cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	@JoinTable(name = "forma_pago_empresa", 
		joinColumns=@JoinColumn(name = "empresa_id"),
		inverseJoinColumns=@JoinColumn(name = "forma_pago_id")
	)
	private Collection<FormaPago> formaPagos;
	
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

	public Collection<FormaPago> getFormaPagos() {
		return formaPagos;
	}

	public void setFormaPagos(Collection<FormaPago> formaPagos) {
		this.formaPagos = formaPagos;
	}
}