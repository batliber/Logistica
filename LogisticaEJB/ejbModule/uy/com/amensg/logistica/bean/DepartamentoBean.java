package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Departamento;

@Stateless
public class DepartamentoBean implements IDepartamentoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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
	
	public Departamento getByNombre(String nombre) {
		Departamento result = null;
		
		try {
			TypedQuery<Departamento> query = 
				entityManager.createQuery(
					"SELECT d"
					+ " FROM Departamento d"
					+ " WHERE d.nombre = :nombre", 
					Departamento.class
				);
			query.setParameter("nombre", nombre);
			
			List<Departamento> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}