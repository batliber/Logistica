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
			TypedQuery<Empresa> query = entityManager.createQuery("SELECT e FROM Empresa e", Empresa.class);
			
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
}