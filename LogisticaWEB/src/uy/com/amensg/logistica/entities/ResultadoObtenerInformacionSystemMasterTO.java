package uy.com.amensg.logistica.entities;

public class ResultadoObtenerInformacionSystemMasterTO {

	private String documento;
	private String fechaNacimiento;
	private String situacion;
	private String resultadoObtenerDatosLocales;
	private String resultadoObtenerDatosBICSA;
	private String resultadoEnviarDatosSystemMaster;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public String getResultadoObtenerDatosLocales() {
		return resultadoObtenerDatosLocales;
	}

	public void setResultadoObtenerDatosLocales(String resultadoObtenerDatosLocales) {
		this.resultadoObtenerDatosLocales = resultadoObtenerDatosLocales;
	}

	public String getResultadoObtenerDatosBICSA() {
		return resultadoObtenerDatosBICSA;
	}

	public void setResultadoObtenerDatosBICSA(String resultadoObtenerDatosBICSA) {
		this.resultadoObtenerDatosBICSA = resultadoObtenerDatosBICSA;
	}

	public String getResultadoEnviarDatosSystemMaster() {
		return resultadoEnviarDatosSystemMaster;
	}

	public void setResultadoEnviarDatosSystemMaster(String resultadoEnviarDatosSystemMaster) {
		this.resultadoEnviarDatosSystemMaster = resultadoEnviarDatosSystemMaster;
	}
}