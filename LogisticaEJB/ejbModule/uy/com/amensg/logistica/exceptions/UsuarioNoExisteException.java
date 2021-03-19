package uy.com.amensg.logistica.exceptions;

public class UsuarioNoExisteException extends LogisticaException {

	private static final long serialVersionUID = 2918148275753566247L;
	
	public UsuarioNoExisteException(String message)	{
		super(message);
	}
}