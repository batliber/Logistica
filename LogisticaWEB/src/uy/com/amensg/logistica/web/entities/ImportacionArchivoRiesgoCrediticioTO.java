package uy.com.amensg.logistica.entities;

public class ImportacionArchivoRiesgoCrediticioTO {

	private String nombre;
	private Long empresaId;
	private Long tipoControlRiesgoCrediticioId;

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

	public Long getTipoControlRiesgoCrediticioId() {
		return tipoControlRiesgoCrediticioId;
	}

	public void setTipoControlRiesgoCrediticioId(Long tipoControlRiesgoCrediticioId) {
		this.tipoControlRiesgoCrediticioId = tipoControlRiesgoCrediticioId;
	}
}