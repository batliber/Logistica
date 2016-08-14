package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceEstado;

@Stateless
public class ACMInterfaceEstadoBean implements IACMInterfaceEstadoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<ACMInterfaceEstado> list() {
		Collection<ACMInterfaceEstado> result = new LinkedList<ACMInterfaceEstado>();
		
		try {
			TypedQuery<ACMInterfaceEstado> query = entityManager.createQuery(
				"SELECT e"
				+ " FROM ACMInterfaceEstado e"
				+ " ORDER BY e.id", 
				ACMInterfaceEstado.class
			);
			
			for (ACMInterfaceEstado estado : query.getResultList()) {
				result.add(estado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}