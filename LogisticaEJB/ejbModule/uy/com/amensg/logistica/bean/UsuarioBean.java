package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Stateless
public class UsuarioBean implements IUsuarioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Usuario> list() {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM Usuario u"
					+ " WHERE u.fechaBaja IS NULL"
					+ " ORDER BY u.login ASC",
					Usuario.class
				);
			
			for (Usuario usuario : query.getResultList()) {
				result.add(usuario);
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
					usuarioRolEmpresa.getRol().getMenus().size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Usuario getById(Long id) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u FROM Usuario u WHERE u.id = :usuarioId AND u.fechaBaja IS NULL",
					Usuario.class
				);
			query.setParameter("usuarioId", id);
			
			List<Usuario> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : result.getUsuarioRolEmpresas()) {
					usuarioRolEmpresa.getRol().getMenus().size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario getByLogin(String login) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery("SELECT u FROM Usuario u WHERE login = :login", Usuario.class);
			query.setParameter("login", login);
			
			List<Usuario> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				for (UsuarioRolEmpresa usuarioRolEmprsa : result.getUsuarioRolEmpresas()) {
					usuarioRolEmprsa.getRol().getMenus().size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Usuario usuario) {
		try {
			Collection<UsuarioRolEmpresa> usuarioRolEmpresas = usuario.getUsuarioRolEmpresas();
			
			entityManager.persist(usuario);
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioRolEmpresas) {
				usuarioRolEmpresa.setUsuario(usuario);
				
				entityManager.persist(usuarioRolEmpresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Usuario usuario) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			managedUsuario.setFechaBaja(date);
			
			managedUsuario.setFact(usuario.getFact());
			managedUsuario.setTerm(usuario.getTerm());
			managedUsuario.setUact(usuario.getUact());
			
			entityManager.merge(managedUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Usuario usuario) {
		try {
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : managedUsuario.getUsuarioRolEmpresas()) {
				entityManager.remove(usuarioRolEmpresa);
			}
			
			if (usuario.getContrasena() != null) {
				managedUsuario.setContrasena(usuario.getContrasena());
			}
			
			managedUsuario.setLogin(usuario.getLogin());
			managedUsuario.setNombre(usuario.getNombre());
			
			managedUsuario.setFact(usuario.getFact());
			managedUsuario.setTerm(usuario.getTerm());
			managedUsuario.setUact(usuario.getUact());
			
			entityManager.merge(managedUsuario);
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				usuarioRolEmpresa.setUsuario(usuario);
				
				usuarioRolEmpresa.setFact(usuario.getFact());
				usuarioRolEmpresa.setTerm(usuario.getTerm());
				usuarioRolEmpresa.setUact(usuario.getUact());
				
				entityManager.merge(usuarioRolEmpresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}