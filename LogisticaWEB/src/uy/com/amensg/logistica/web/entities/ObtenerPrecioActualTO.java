package uy.com.amensg.logistica.entities;

public class ObtenerPrecioActualTO {

	private Long empresaId;
	private Long tipoProductoId;
	private Long marcaId;
	private Long modeloId;
	private Long monedaId;
	private Long cuotas;

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getTipoProductoId() {
		return tipoProductoId;
	}

	public void setTipoProductoId(Long tipoProductoId) {
		this.tipoProductoId = tipoProductoId;
	}

	public Long getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}

	public Long getModeloId() {
		return modeloId;
	}

	public void setModeloId(Long modeloId) {
		this.modeloId = modeloId;
	}

	public Long getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}

	public Long getCuotas() {
		return cuotas;
	}

	public void setCuotas(Long cuotas) {
		this.cuotas = cuotas;
	}
}