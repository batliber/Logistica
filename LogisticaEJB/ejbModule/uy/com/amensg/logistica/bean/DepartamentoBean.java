package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Departamento;

@Stateless
public class DepartamentoBean implements IDepartamentoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Departamento> list() {
		Collection<Departamento> result = new LinkedList<Departamento>();
		
		try {
			TypedQuery<Departamento> query = 
				entityManager.createQuery(
					"SELECT d"
					+ " FROM Departamento d"
					+ " ORDER BY d.nombre", 
					Departamento.class
				);
			
			for (Departamento departamento : query.getResultList()) {
				result.add(departamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}