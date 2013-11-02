package uy.com.amensg.logistica.bean;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.SeguridadTipoEvento;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.MD5Utils;

@Stateless
public class SeguridadBean implements ISeguridadBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private ISeguridadAuditoriaBean iSeguridadAuditoriaBean;
	
	public SeguridadAuditoria login(String login, String contrasena) {
		SeguridadAuditoria result = null;
		
		try {
			Usuario usuario = iUsuarioBean.getByLogin(login);
			
			if ((usuario != null) && (usuario.getContrasena().equals(MD5Utils.stringToMD5(contrasena)))) {
				SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
				seguridadAuditoria.setFecha(new Date());
				
				SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
				seguridadTipoEvento.setId(new Long(1));
				
				seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
				
				seguridadAuditoria.setUsuario(usuario);
				
				seguridadAuditoria.setFact(new Date());
				seguridadAuditoria.setTerm(new Long(1));
				seguridadAuditoria.setUact(new Long(1));
				
				entityManager.persist(seguridadAuditoria);
				
				result = seguridadAuditoria;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void logout(Long usuarioId) {
		try {
			Usuario usuario = iUsuarioBean.getById(usuarioId);
			
			SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
			seguridadAuditoria.setFecha(new Date());
			
			SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
			seguridadTipoEvento.setId(new Long(2));
			
			seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
			
			seguridadAuditoria.setUsuario(usuario);
			
			seguridadAuditoria.setFact(new Date());
			seguridadAuditoria.setTerm(new Long(1));
			seguridadAuditoria.setUact(new Long(1));
			
			entityManager.persist(seguridadAuditoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}