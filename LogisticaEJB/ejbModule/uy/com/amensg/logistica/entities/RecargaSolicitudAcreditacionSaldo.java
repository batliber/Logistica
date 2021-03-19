package uy.com.amensg.logistica.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recarga_solicitud_acreditacion_saldo")
public class RecargaSolicitudAcreditacionSaldo extends BaseEntity {

	private static final long serialVersionUID = 3863808652642601801L;

	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;
	
	@Column(name = "fecha_preaprobacion")
	private Date fechaPreaprobacion;
	
	@Column(name = "fecha_acreditacion")
	private Date fechaAcreditacion;
	
	@Column(name = "fecha_denegacion")
	private Date fechaDenegacion;
	
	@Column(name = "fecha_credito")
	private Date fechaCredito;
	
	@Column(name = "fecha_eliminacion")
	private Date fechaEliminacion;

	@Column(name = "monto")
	private Double monto;
	
	@Column(name = "observaciones")
	private String observaciones;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "recarga_empresa_id", nullable = false)
	private RecargaEmpresa recargaEmpresa;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "moneda_id", nullable = false)
	private Moneda moneda;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "recarga_forma_pago_id", nullable = false)
	private RecargaFormaPago recargaFormaPago;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "recarga_banco_id")
	private RecargaBanco recargaBanco;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_recarga_banco_cuenta_id")
	private EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta;
	
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "recarga_estado_acreditacion_saldo_id", nullable = false)
	private RecargaEstadoAcreditacionSaldo recargaEstadoAcreditacionSaldo;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id", nullable = false)
	private PuntoVenta puntoVenta;

	@OneToMany(mappedBy = "recargaSolicitudAcreditacionSaldo", cascade = CascadeType.ALL)
	private Set<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> archivosAdjuntos;

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fecha) {
		this.fechaSolicitud = fecha;
	}
	
	public Date getFechaPreaprobacion() {
		return fechaPreaprobacion;
	}

	public void setFechaPreaprobacion(Date fechaPreaprobacion) {
		this.fechaPreaprobacion = fechaPreaprobacion;
	}

	public Date getFechaAcreditacion() {
		return fechaAcreditacion;
	}

	public void setFechaAcreditacion(Date fechaAcreditacion) {
		this.fechaAcreditacion = fechaAcreditacion;
	}

	public Date getFechaDenegacion() {
		return fechaDenegacion;
	}

	public void setFechaDenegacion(Date fechaDenegacion) {
		this.fechaDenegacion = fechaDenegacion;
	}

	public Date getFechaCredito() {
		return fechaCredito;
	}

	public void setFechaCredito(Date fechaCredito) {
		this.fechaCredito = fechaCredito;
	}

	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public RecargaEmpresa getRecargaEmpresa() {
		return recargaEmpresa;
	}

	public void setRecargaEmpresa(RecargaEmpresa recargaEmpresa) {
		this.recargaEmpresa = recargaEmpresa;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public RecargaEstadoAcreditacionSaldo getRecargaEstadoAcreditacionSaldo() {
		return recargaEstadoAcreditacionSaldo;
	}

	public void setRecargaEstadoAcreditacionSaldo(RecargaEstadoAcreditacionSaldo recargaEstadoAcreditacionSaldo) {
		this.recargaEstadoAcreditacionSaldo = recargaEstadoAcreditacionSaldo;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public RecargaFormaPago getRecargaFormaPago() {
		return recargaFormaPago;
	}

	public void setRecargaFormaPago(RecargaFormaPago recargaFormaPago) {
		this.recargaFormaPago = recargaFormaPago;
	}

	public RecargaBanco getRecargaBanco() {
		return recargaBanco;
	}

	public void setRecargaBanco(RecargaBanco recargaBanco) {
		this.recargaBanco = recargaBanco;
	}

	public EmpresaRecargaBancoCuenta getEmpresaRecargaBancoCuenta() {
		return empresaRecargaBancoCuenta;
	}

	public void setEmpresaRecargaBancoCuenta(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta) {
		this.empresaRecargaBancoCuenta = empresaRecargaBancoCuenta;
	}

	public Set<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void setArchivosAdjuntos(Set<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}
}