package uy.com.amensg.logistica.bean;

public class MaxContratoRoutingHistoryResult {

	private Long id;
	private Long empresaId;
	private Long contratoId;
	
	public MaxContratoRoutingHistoryResult(Long id, Long empresaId, Long contratoId) {
		this.id = id;
		this.empresaId = empresaId;
		this.contratoId = contratoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getContratoId() {
		return contratoId;
	}

	public void setContratoId(Long contratoId) {
		this.contratoId = contratoId;
	}
}