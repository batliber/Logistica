package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ActivacionTO extends BaseTO {

	private Long mid;
	private Date fechaActivacion;
	private Date fechaVencimiento;
	private String chip;
	private Date fechaImportacion;
	private Long idTipoActivacion;
	private EmpresaTO empresa;
	private TipoActivacionTO tipoActivacion;
	private EstadoActivacionTO estadoActivacion;
	private ActivacionLoteTO activacionLote;
	private ActivacionSubloteTO activacionSublote;
	private LiquidacionTO liquidacion;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getChip() {
		return chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public Long getIdTipoActivacion() {
		return idTipoActivacion;
	}

	public void setIdTipoActivacion(Long idTipoActivacion) {
		this.idTipoActivacion = idTipoActivacion;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public TipoActivacionTO getTipoActivacion() {
		return tipoActivacion;
	}

	public void setTipoActivacion(TipoActivacionTO tipoActivacion) {
		this.tipoActivacion = tipoActivacion;
	}

	public EstadoActivacionTO getEstadoActivacion() {
		return estadoActivacion;
	}

	public void setEstadoActivacion(EstadoActivacionTO estadoActivacion) {
		this.estadoActivacion = estadoActivacion;
	}

	public ActivacionLoteTO getActivacionLote() {
		return activacionLote;
	}
	
	public void setActivacionLote(ActivacionLoteTO activacionLote) {
		this.activacionLote = activacionLote;
	}

	public ActivacionSubloteTO getActivacionSublote() {
		return activacionSublote;
	}

	public void setActivacionSublote(ActivacionSubloteTO activacionSublote) {
		this.activacionSublote = activacionSublote;
	}

	public LiquidacionTO getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(LiquidacionTO liquidacion) {
		this.liquidacion = liquidacion;
	}
}