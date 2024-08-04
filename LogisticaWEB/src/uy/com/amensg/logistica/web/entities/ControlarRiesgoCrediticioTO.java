package uy.com.amensg.logistica.entities;

public class ControlarRiesgoCrediticioTO {

	private MetadataConsulta metadataConsulta;
	private Long empresaId;
	private Long tipoControlRiesgoCrediticioId;

	public MetadataConsulta getMetadataConsulta() {
		return metadataConsulta;
	}

	public void setMetadataConsulta(MetadataConsulta metadataConsulta) {
		this.metadataConsulta = metadataConsulta;
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