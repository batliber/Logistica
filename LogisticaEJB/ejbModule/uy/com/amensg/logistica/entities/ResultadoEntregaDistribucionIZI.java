package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultado_entrega_distribucion_izi")
public class ResultadoEntregaDistribucionIZI extends BaseEntity {

	private static final long serialVersionUID = -3651190077223537037L;

	@Column(name = "completion_code")
	private String completionCode;
	
	@Column(name = "completion_code_description")
	private String completionCodeDescription;
	
	@Column(name = "completion_sub_code")
	private String completionSubCode;
	
	@Column(name = "completion_sub_code_description")
	private String completionSubCodeDescription;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_id", nullable = true)
	private Estado estado;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "resultado_entrega_distribucion_id", nullable = true)
	private ResultadoEntregaDistribucion resultadoEntregaDistribucion;

	public String getCompletionCode() {
		return completionCode;
	}

	public void setCompletionCode(String completionCode) {
		this.completionCode = completionCode;
	}

	public String getCompletionCodeDescription() {
		return completionCodeDescription;
	}

	public void setCompletionCodeDescription(String completionCodeDescription) {
		this.completionCodeDescription = completionCodeDescription;
	}

	public String getCompletionSubCode() {
		return completionSubCode;
	}

	public void setCompletionSubCode(String completionSubCode) {
		this.completionSubCode = completionSubCode;
	}

	public String getCompletionSubCodeDescription() {
		return completionSubCodeDescription;
	}

	public void setCompletionSubCodeDescription(String completionSubCodeDescription) {
		this.completionSubCodeDescription = completionSubCodeDescription;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public ResultadoEntregaDistribucion getResultadoEntregaDistribucion() {
		return resultadoEntregaDistribucion;
	}

	public void setResultadoEntregaDistribucion(ResultadoEntregaDistribucion resultadoEntregaDistribucion) {
		this.resultadoEntregaDistribucion = resultadoEntregaDistribucion;
	}
}