package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;

@Stateless
public class EmpresaBean implements IEmpresaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Empresa> list() {
		Collection<Empresa> result = new LinkedList<Empresa>();
		
		try {
			TypedQuery<Empresa> query = entityManager.createQuery(
				"SELECT e"
				+ " FROM Empresa e"
				+ " ORDER BY e.nombre", 
				Empresa.class
			);
			
			for (Empresa empresa : query.getResultList()) {
				result.add(empresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Empresa getById(Long id) {
		Empresa result = null;
		
		try {
			result = entityManager.find(Empresa.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(Empresa empresa) {
		try {
			entityManager.persist(empresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Empresa empresa) {
		try {
			Empresa managedEmpresa = entityManager.find(Empresa.class, empresa.getId());
			
//			managedEmpresa.setFechaBaja(date);
			
			managedEmpresa.setFact(empresa.getFact());
			managedEmpresa.setTerm(empresa.getTerm());
			managedEmpresa.setUact(empresa.getUact());
			
			entityManager.merge(managedEmpresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Empresa empresa) {
		try {
			Empresa managedEmpresa = entityManager.find(Empresa.class, empresa.getId());
			
			managedEmpresa.setNombre(empresa.getNombre());
			
			managedEmpresa.setFact(empresa.getFact());
			managedEmpresa.setTerm(empresa.getTerm());
			managedEmpresa.setUact(empresa.getUact());
			
			entityManager.merge(managedEmpresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}