package uy.com.amensg.logistica.web.entities;

public class InstalarTO {

	private Long contratoId;
	private Long resultadoEntregaDistribucionId;
	private String documento;
	private String nombre;
	private String observaciones;

	public Long getContratoId() {
		return contratoId;
	}

	public void setContratoId(Long contratoId) {
		this.contratoId = contratoId;
	}

	public Long getResultadoEntregaDistribucionId() {
		return resultadoEntregaDistribucionId;
	}

	public void setResultadoEntregaDistribucionId(Long resultadoEntregaDistribucionId) {
		this.resultadoEntregaDistribucionId = resultadoEntregaDistribucionId;
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}