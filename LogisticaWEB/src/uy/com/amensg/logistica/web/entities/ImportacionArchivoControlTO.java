package uy.com.amensg.logistica.web.entities;

public class ImportacionArchivoControlTO {

	private String nombre;
	private Long empresaId;
	private Long tipoControlId;

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

	public Long getTipoControlId() {
		return tipoControlId;
	}

	public void setTipoControlId(Long tipoControlId) {
		this.tipoControlId = tipoControlId;
	}
}