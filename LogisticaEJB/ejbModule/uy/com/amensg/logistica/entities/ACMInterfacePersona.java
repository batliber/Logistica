package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "acm_interface_persona")
public class ACMInterfacePersona extends BaseEntity {

	private static final long serialVersionUID = -1766647339211548780L;

	@Column(name = "id_cliente")
	private String idCliente;
	
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "tipo_documento")
	private String tipoDocumento;
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "razon_social")
	private String razonSocial;
	
	@Column(name = "tipo_cliente")
	private String tipoCliente;
	
	@Column(name = "actividad")
	private String actividad;
	
	@Column(name = "fecha_nacimiento")
	private String fechaNacimiento;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "calle")
	private String calle;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "bis")
	private String bis;
	
	@Column(name = "apartamento")
	private String apartamento;
	
	@Column(name = "esquina")
	private String esquina;
	
	@Column(name = "block")
	private String block;
	
	@Column(name = "manzana")
	private String manzana;
	
	@Column(name = "solar")
	private String solar;
	
	@Column(name = "localidad")
	private String localidad;
	
	@Column(name = "codigo_postal")
	private String codigoPostal;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "distribucion")
	private String distribucion;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "email")
	private String email;

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBis() {
		return bis;
	}

	public void setBis(String bis) {
		this.bis = bis;
	}

	public String getApartamento() {
		return apartamento;
	}

	public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}

	public String getEsquina() {
		return esquina;
	}

	public void setEsquina(String esquina) {
		this.esquina = esquina;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getManzana() {
		return manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	public String getSolar() {
		return solar;
	}

	public void setSolar(String solar) {
		this.solar = solar;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDistribucion() {
		return distribucion;
	}

	public void setDistribucion(String distribucion) {
		this.distribucion = distribucion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}