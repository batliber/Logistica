package uy.com.amensg.logistica.exceptions;

public class UsuarioContrasenaIncorrectaException extends LogisticaException {

	private static final long serialVersionUID = -1490281526715409671L;
	
	public UsuarioContrasenaIncorrectaException(String message) {
		super(message);
	}
}