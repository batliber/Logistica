package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.util.QueryBuilder;

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
	
	public Collection<Rol> listMinimal() {
		Collection<Rol> result = new LinkedList<Rol>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT r.id, r.nombre"
					+ " FROM Rol r"
					+ " ORDER BY r.nombre ASC", 
					Object[].class
				);
			
			for (Object[] rol : query.getResultList()) {
				Rol rolMinimal = new Rol();
				rolMinimal.setId((Long)rol[0]);
				rolMinimal.setNombre((String)rol[1]);
				
				result.add(rolMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<Rol>().list(entityManager, metadataConsulta, new Rol());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Rol>().count(entityManager, metadataConsulta, new Rol());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Rol getById(Long id, boolean initializeCollections) {
		Rol result = null;
		
		try {
			TypedQuery<Rol> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM Rol r"
					+ " WHERE r.id = :id",
					Rol.class
				);
			query.setParameter("id", id);
			
			TypedQuery<Menu> queryMenus =
				entityManager.createQuery(
					"SELECT mr"
					+ " FROM Rol r"
					+ " JOIN r.menus mr"
					+ " WHERE r.id = :id",
					Menu.class
				);
			queryMenus.setParameter("id", id);
			
			TypedQuery<Rol> querySubordinados =
				entityManager.createQuery(
					"SELECT rs"
					+ " FROM Rol r"
					+ " JOIN r.subordinados rs"
					+ " WHERE r.id = :id",
					Rol.class
				);
			querySubordinados.setParameter("id", id);
			
			List<Rol> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				entityManager.detach(result);
			
				if (initializeCollections) {
					Set<Menu> menus = new HashSet<Menu>();
					for (Menu menu : queryMenus.getResultList()) {
						menus.add(menu);
					}
					result.setMenus(menus);
					
					Set<Rol> subordinados = new HashSet<Rol>();
					for (Rol rol : querySubordinados.getResultList()) {
						entityManager.detach(rol);
						
						rol.setMenus(new HashSet<Menu>());
						rol.setSubordinados(new HashSet<Rol>());
						
						subordinados.add(rol);
					}
					result.setSubordinados(subordinados);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Rol getByNombre(String nombre, boolean initializeCollections) {
		Rol result = null;
		
		try {
			TypedQuery<Rol> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM Rol r"
					+ " WHERE r.nombre = :nombre",
					Rol.class
				);
			query.setParameter("nombre", nombre);
			
			TypedQuery<Menu> queryMenus =
				entityManager.createQuery(
					"SELECT mr"
					+ " FROM Rol r"
					+ " JOIN r.menus mr"
					+ " WHERE r.id = :id",
					Menu.class
				);
			
			TypedQuery<Rol> querySubordinados =
				entityManager.createQuery(
					"SELECT rs"
					+ " FROM Rol r"
					+ " JOIN r.subordinados rs"
					+ " WHERE r.id = :id",
					Rol.class
				);
			
			List<Rol> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
				entityManager.detach(result);
				
				if (initializeCollections) {
					queryMenus.setParameter("id", result.getId());
					
					Set<Menu> menus = new HashSet<Menu>();
					for (Menu menu : queryMenus.getResultList()) {
						menus.add(menu);
					}
					result.setMenus(menus);
					
					querySubordinados.setParameter("id", result.getId());
					
					Set<Rol> subordinados = new HashSet<Rol>();
					for (Rol rol : querySubordinados.getResultList()) {
						subordinados.add(rol);
					}
					result.setSubordinados(subordinados);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Rol save(Rol rol) {
		Rol result = null;
		
		try {
			rol.setFcre(rol.getFact());
			rol.setUcre(rol.getUact());
			
			entityManager.persist(rol);
			
			result = rol;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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