package uy.com.amensg.logistica.web.entities;

public class RegistrarAnalisisRiesgoManualTO {

	private Long empresaId;
	private String documento;

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
}