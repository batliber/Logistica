package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.exceptions.UsuarioBloqueadoException;
import uy.com.amensg.logistica.exceptions.UsuarioContrasenaIncorrectaException;
import uy.com.amensg.logistica.exceptions.UsuarioDebeCambiarContrasenaException;
import uy.com.amensg.logistica.exceptions.UsuarioNoExisteException;

@Remote
public interface ISeguridadBean {

	public SeguridadAuditoria login(String login, String contrasena)
		throws UsuarioNoExisteException, UsuarioDebeCambiarContrasenaException, 
			UsuarioContrasenaIncorrectaException, UsuarioBloqueadoException;
	
	public void logout(Long usuarioId);

	public void sessionTimeout(Long usuarioId);
}