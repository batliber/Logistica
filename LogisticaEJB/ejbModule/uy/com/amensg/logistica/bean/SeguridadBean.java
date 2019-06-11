package uy.com.amensg.logistica.bean;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
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
			throw new UsuarioNoExisteException();
		} else if (usuario.getBloqueado() != null && usuario.getBloqueado()) {
			throw new UsuarioBloqueadoException();
		} else if (usuario.getContrasena().equals(MD5Utils.stringToMD5(contrasena))) {
			if (usuario.getIntentosFallidosLogin() != null 
				&& !usuario.getIntentosFallidosLogin().equals(new Long(0))) {
				usuario.setIntentosFallidosLogin(new Long(0));
				
				usuario.setFact(date);
				usuario.setUact(new Long(1));
				usuario.setTerm(new Long(1));
				
				iUsuarioBean.update(usuario);
			}
			
			SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
			seguridadAuditoria.setFecha(date);
			
			SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
			seguridadTipoEvento.setId(
				new Long(Configuration.getInstance().getProperty("seguridadTipoEvento.Login"))
			);
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFcre(date);
			seguridadAuditoria.setFact(date);
			seguridadAuditoria.setTerm(new Long(1));
			seguridadAuditoria.setUact(usuario.getId());
			seguridadAuditoria.setUcre(usuario.getId());
			
			entityManager.persist(seguridadAuditoria);
			
			return seguridadAuditoria;
		} else {
			if (usuario.getIntentosFallidosLogin() != null) {
				usuario.setIntentosFallidosLogin(usuario.getIntentosFallidosLogin() + 1);
			} else {
				usuario.setIntentosFallidosLogin(new Long(1));
			}
			
			usuario.setFact(date);
			usuario.setUact(new Long(1));
			usuario.setTerm(new Long(1));
			
			if (usuario.getIntentosFallidosLogin().equals(
				new Long(Configuration.getInstance().getProperty("seguridad.maximaCantidadIntentosFallidosLogin"))
			)) {
				usuario.setBloqueado(true);
				
				iUsuarioBean.update(usuario);
				
				SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
				seguridadAuditoria.setFecha(date);
				
				SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
				seguridadTipoEvento.setId(
					new Long(Configuration.getInstance().getProperty("seguridadTipoEvento.BloqueoUsuario"))
				);
				
				seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
				
				seguridadAuditoria.setUsuario(usuario);
				
				seguridadAuditoria.setFcre(date);
				seguridadAuditoria.setFact(date);
				seguridadAuditoria.setTerm(new Long(1));
				seguridadAuditoria.setUact(usuario.getId());
				seguridadAuditoria.setUcre(usuario.getId());
				
				entityManager.persist(seguridadAuditoria);
				
				throw new UsuarioBloqueadoException();
			} else {
				iUsuarioBean.update(usuario);
				
				throw new UsuarioContrasenaIncorrectaException();
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
				new Long(Configuration.getInstance().getProperty("seguridadTipoEvento.Logout"))
			);
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFcre(date);
			seguridadAuditoria.setFact(date);
			seguridadAuditoria.setTerm(new Long(1));
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
				new Long(Configuration.getInstance().getProperty("seguridadTipoEvento.Timeout"))
			);
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFcre(date);
			seguridadAuditoria.setFact(date);
			seguridadAuditoria.setTerm(new Long(1));
			seguridadAuditoria.setUact(usuario.getId());
			seguridadAuditoria.setUcre(usuario.getId());
			
			entityManager.persist(seguridadAuditoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}