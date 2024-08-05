package uy.com.amensg.logistica.web.entities;

public class ImportacionArchivoActivacionTO {

	private String nombre;
	private Long empresaId;
	private Long tipoActivacionId;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getTipoActivacionId() {
		return tipoActivacionId;
	}

	public void setTipoActivacionId(Long tipoActivacionId) {
		this.tipoActivacionId = tipoActivacionId;
	}
}