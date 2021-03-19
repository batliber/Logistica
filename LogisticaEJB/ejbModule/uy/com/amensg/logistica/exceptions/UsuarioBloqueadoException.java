package uy.com.amensg.logistica.exceptions;

public class UsuarioBloqueadoException extends LogisticaException {

	private static final long serialVersionUID = -3970402328021124057L;
	
	public UsuarioBloqueadoException(String message) {
		super(message);
	}
}