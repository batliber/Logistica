package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Usuario;

@Stateless
public class UsuarioBean implements IUsuarioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Usuario> list() {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			Query query = entityManager.createQuery("SELECT u FROM Usuario u");
			
			for (Object object : query.getResultList()) {
				result.add((Usuario) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Usuario getById(Long id) {
		Usuario result = null;
		
		try {
			result = entityManager.find(Usuario.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario getByLogin(String login) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> typedQuery = 
				entityManager.createQuery("SELECT u FROM Usuario u WHERE login = :login", Usuario.class);
			typedQuery.setParameter("login", login);
			
			List<Usuario> resultList = typedQuery.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Usuario usuario) {
		try {
			entityManager.persist(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Usuario usuario) {
		try {
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			entityManager.remove(managedUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Usuario usuario) {
		try {
			entityManager.merge(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}