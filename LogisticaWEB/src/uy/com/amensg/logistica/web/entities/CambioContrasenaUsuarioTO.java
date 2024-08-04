package uy.com.amensg.logistica.entities;

public class CambioContrasenaUsuarioTO extends BaseTO {

	private String contrasenaActual;
	private String contrasenaNueva;
	private String contrasenaConfirma;

	public String getContrasenaActual() {
		return contrasenaActual;
	}

	public void setContrasenaActual(String contrasenaActual) {
		this.contrasenaActual = contrasenaActual;
	}

	public String getContrasenaNueva() {
		return contrasenaNueva;
	}

	public void setContrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}

	public String getContrasenaConfirma() {
		return contrasenaConfirma;
	}

	public void setContrasenaConfirma(String contrasenaConfirma) {
		this.contrasenaConfirma = contrasenaConfirma;
	}
}