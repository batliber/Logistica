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

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.SeguridadTipoEvento;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

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

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			result = new QueryBuilder<Usuario>().list(entityManager, metadataConsulta, new Usuario());
			
			for (Object object : result.getRegistrosMuestra()) {
				Usuario usuario = (Usuario) object;
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
					usuarioRolEmpresa.getRol().getMenus().size();
					usuarioRolEmpresa.getRol().getSubordinados().size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Usuario>().count(entityManager, metadataConsulta, new Usuario());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario getById(Long id, boolean initializeCollections) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM Usuario u"
					+ " WHERE u.id = :usuarioId"
					+ " AND u.fechaBaja IS NULL",
					Usuario.class
				);
			query.setParameter("usuarioId", id);
			
			List<Usuario> resultList = query.getResultList();
			
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				if (initializeCollections) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : result.getUsuarioRolEmpresas()) {
						usuarioRolEmpresa.getRol().getMenus().size();
						usuarioRolEmpresa.getRol().getSubordinados().size();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Usuario getByLogin(String login, boolean initializeCollections) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM Usuario u"
					+ " WHERE u.login = :login"
					+ " AND u.fechaBaja IS NULL",
					Usuario.class
				);
			query.setParameter("login", login);
			
			List<Usuario> resultList = query.getResultList();

			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				if (initializeCollections) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : result.getUsuarioRolEmpresas()) {
						usuarioRolEmpresa.getRol().getMenus().size();
						usuarioRolEmpresa.getRol().getSubordinados().size();
					}
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
			
			usuario.setFcre(usuario.getFact());
			usuario.setUcre(usuario.getUact());
			
			entityManager.persist(usuario);
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioRolEmpresas) {
				usuarioRolEmpresa.setUsuario(usuario);
				
				usuarioRolEmpresa.setFcre(usuario.getFact());
				usuarioRolEmpresa.setUcre(usuario.getUact());
				
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
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : managedUsuario.getUsuarioRolEmpresas()) {
				entityManager.remove(usuarioRolEmpresa);
			}
			
			if (usuario.getContrasena() != null && !usuario.getContrasena().equals(managedUsuario.getContrasena())) {
				managedUsuario.setContrasena(usuario.getContrasena());
				
				SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
				seguridadAuditoria.setFecha(date);
				
				SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
				seguridadTipoEvento.setId(
					new Long(Configuration.getInstance().getProperty("seguridadTipoEvento.CambioPassword"))
				);
				
				seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
				
				seguridadAuditoria.setUsuario(managedUsuario);
				
				seguridadAuditoria.setFcre(date);
				seguridadAuditoria.setFact(date);
				seguridadAuditoria.setTerm(new Long(1));
				seguridadAuditoria.setUact(managedUsuario.getId());
				seguridadAuditoria.setUcre(managedUsuario.getId());
				
				entityManager.persist(seguridadAuditoria);
			}
			
			managedUsuario.setBloqueado(usuario.getBloqueado());
			managedUsuario.setCambioContrasenaProximoLogin(usuario.getCambioContrasenaProximoLogin());
			managedUsuario.setDocumento(usuario.getDocumento());
			managedUsuario.setIntentosFallidosLogin(usuario.getIntentosFallidosLogin());
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