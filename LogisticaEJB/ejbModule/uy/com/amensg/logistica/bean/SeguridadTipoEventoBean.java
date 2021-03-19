package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.SeguridadTipoEvento;

@Stateless
public class SeguridadTipoEventoBean implements ISeguridadTipoEventoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<SeguridadTipoEvento> list() {
		Collection<SeguridadTipoEvento> result = new LinkedList<SeguridadTipoEvento>();
		
		try {
			TypedQuery<SeguridadTipoEvento> query = 
				entityManager.createQuery(
					"SELECT s"
					+ " FROM SeguridadTipoEvento s"
					+ " ORDER BY s.descripcion", 
					SeguridadTipoEvento.class
				);
			
			for (SeguridadTipoEvento seguridadTipoEvento : query.getResultList()) {
				result.add(seguridadTipoEvento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}