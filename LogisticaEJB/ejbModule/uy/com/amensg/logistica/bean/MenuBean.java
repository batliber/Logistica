package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import uy.com.amensg.logistica.entities.Menu;

@Stateless
public class MenuBean implements IMenuBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Menu> list() {
		Collection<Menu> result = new LinkedList<Menu>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT m"
				+ " FROM Menu m"
				+ " ORDER BY m.orden"
			);
			
			for (Object object : query.getResultList()) {
				result.add((Menu) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Menu getById(Long id) {
		Menu result = null;
		
		try {
			result = entityManager.find(Menu.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Menu menu) {
		try {
			menu.setFcre(menu.getFact());
			menu.setUcre(menu.getUact());
			
			entityManager.persist(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Menu menu) {
		try {
			Menu managedMenu = entityManager.find(Menu.class, menu.getId());
			
			managedMenu.setFact(menu.getFact());
			managedMenu.setTerm(menu.getTerm());
			managedMenu.setUact(menu.getUact());
			
			entityManager.merge(managedMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Menu menu) {
		try {
			Menu menuManaged = entityManager.find(Menu.class, menu.getId());
			
			menuManaged.setOrden(menu.getOrden());
			menuManaged.setPadre(menu.getPadre());
			menuManaged.setTitulo(menu.getTitulo());
			menuManaged.setUrl(menu.getUrl());
			
			menuManaged.setFact(menu.getFact());
			menuManaged.setTerm(menu.getTerm());
			menuManaged.setUact(menu.getUact());
			
			entityManager.merge(menuManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}