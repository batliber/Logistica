package uy.com.amensg.logistica.bean;

import java.util.Date;
import java.util.GregorianCalendar;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.SeguridadTipoEvento;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.exceptions.UsuarioBloqueadoException;
import uy.com.amensg.logistica.exceptions.UsuarioContrasenaIncorrectaException;
import uy.com.amensg.logistica.exceptions.UsuarioDebeCambiarContrasenaException;
import uy.com.amensg.logistica.exceptions.UsuarioNoExisteException;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.MD5Utils;

@Stateless
public class SeguridadBean implements ISeguridadBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private ISeguridadAuditoriaBean iSeguridadAuditoriaBean;
	
	public SeguridadAuditoria login(String login, String contrasena) 
		throws UsuarioNoExisteException, UsuarioDebeCambiarContrasenaException, 
			UsuarioContrasenaIncorrectaException, UsuarioBloqueadoException {
		Usuario usuario = null;
		
		try {
			usuario = iUsuarioBean.getByLogin(login, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		if (usuario == null) {
			throw new UsuarioNoExisteException("El usuario no existe.");
		} else if (usuario.getBloqueado() != null && usuario.getBloqueado()) {
			throw new UsuarioBloqueadoException("Usuario bloqueado.");
		} else if (usuario.getContrasena().equals(MD5Utils.stringToMD5(contrasena))) {
			if (usuario.getIntentosFallidosLogin() != null 
				&& !usuario.getIntentosFallidosLogin().equals(Long.valueOf(0))) {
				usuario.setIntentosFallidosLogin(Long.valueOf(0));
				
				usuario.setFact(date);
				usuario.setUact(Long.valueOf(1));
				usuario.setTerm(Long.valueOf(1));
				
				iUsuarioBean.updateIntentosFallidosLogin(usuario);
			}
			
			SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
			seguridadAuditoria.setFecha(date);
			
			SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
			seguridadTipoEvento.setId(
				Long.parseLong(Configuration.getInstance().getProperty("seguridadTipoEvento.Login"))
			);
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFcre(date);
			seguridadAuditoria.setFact(date);
			seguridadAuditoria.setTerm(Long.valueOf(1));
			seguridadAuditoria.setUact(usuario.getId());
			seguridadAuditoria.setUcre(usuario.getId());
			
			entityManager.persist(seguridadAuditoria);
			
			return seguridadAuditoria;
		} else {
			usuario.setFact(date);
			usuario.setUact(Long.valueOf(1));
			usuario.setTerm(Long.valueOf(1));
			
			if (usuario.getIntentosFallidosLogin() != null) {
				usuario.setIntentosFallidosLogin(usuario.getIntentosFallidosLogin() + 1);
			} else {
				usuario.setIntentosFallidosLogin(Long.valueOf(1));
			}
			
			iUsuarioBean.updateIntentosFallidosLogin(usuario);
			
			if (usuario.getIntentosFallidosLogin().equals(
				Long.parseLong(Configuration.getInstance().getProperty("seguridad.maximaCantidadIntentosFallidosLogin"))
			)) {
				usuario.setBloqueado(true);
				
				iUsuarioBean.updateBloqueado(usuario);
				
				SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
				seguridadAuditoria.setFecha(date);
				
				SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
				seguridadTipoEvento.setId(
					Long.parseLong(Configuration.getInstance().getProperty("seguridadTipoEvento.BloqueoUsuario"))
				);
				
				seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
				
				seguridadAuditoria.setUsuario(usuario);
				
				seguridadAuditoria.setFcre(date);
				seguridadAuditoria.setFact(date);
				seguridadAuditoria.setTerm(Long.valueOf(1));
				seguridadAuditoria.setUact(usuario.getId());
				seguridadAuditoria.setUcre(usuario.getId());
				
				entityManager.persist(seguridadAuditoria);
				
				throw new UsuarioBloqueadoException("Usuario bloqueado.");
			} else {
				throw new UsuarioContrasenaIncorrectaException("Usuario o contraseña incorrecta.");
			}
		}
	}
	
	public void logout(Long usuarioId) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario usuario = iUsuarioBean.getById(usuarioId, false);
			
			SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
			seguridadAuditoria.setFecha(date);
			
			SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
			seguridadTipoEvento.setId(
				Long.parseLong(Configuration.getInstance().getProperty("seguridadTipoEvento.Logout"))
			);
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFcre(date);
			seguridadAuditoria.setFact(date);
			seguridadAuditoria.setTerm(Long.valueOf(1));
			seguridadAuditoria.setUact(usuario.getId());
			seguridadAuditoria.setUcre(usuario.getId());
			
			entityManager.persist(seguridadAuditoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sessionTimeout(Long usuarioId) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario usuario = iUsuarioBean.getById(usuarioId, false);
			
			SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
			seguridadAuditoria.setFecha(date);
			
			SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
			seguridadTipoEvento.setId(
				Long.parseLong(Configuration.getInstance().getProperty("seguridadTipoEvento.Timeout"))
			);
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFcre(date);
			seguridadAuditoria.setFact(date);
			seguridadAuditoria.setTerm(Long.valueOf(1));
			seguridadAuditoria.setUact(usuario.getId());
			seguridadAuditoria.setUcre(usuario.getId());
			
			entityManager.persist(seguridadAuditoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}