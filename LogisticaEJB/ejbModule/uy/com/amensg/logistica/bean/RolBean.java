package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Rol;

@Stateless
public class RolBean implements IRolBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Rol> list() {
		Collection<Rol> result = new LinkedList<Rol>();
		
		try {
			TypedQuery<Rol> query = 
				entityManager.createQuery(
					"SELECT r FROM Rol r"
					+ " ORDER BY r.id",
					Rol.class
				);
			
			for (Rol rol : query.getResultList()) {
				result.add(rol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Rol getById(Long id) {
		Rol result = null;
		
		try {
			result = entityManager.find(Rol.class, id);
			result.getSubordinados();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(Rol rol) {
		try {
			entityManager.persist(rol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Rol rol) {
		try {
			Rol managedRol = entityManager.find(Rol.class, rol.getId());
			
			managedRol.setFact(rol.getFact());
			managedRol.setTerm(rol.getTerm());
			managedRol.setUact(rol.getUact());
			
			entityManager.merge(managedRol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Rol rol) {
		try {
			Rol rolManaged = entityManager.find(Rol.class, rol.getId());
			
			rolManaged.setNombre(rol.getNombre());
			
			rolManaged.setMenus(rol.getMenus());
			rolManaged.setSubordinados(rol.getSubordinados());
			
			rolManaged.setFact(rol.getFact());
			rolManaged.setTerm(rol.getTerm());
			rolManaged.setUact(rol.getUact());
			
			entityManager.merge(rolManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}