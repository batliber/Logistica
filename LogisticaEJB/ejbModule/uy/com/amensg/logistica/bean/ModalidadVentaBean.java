package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ModalidadVenta;

@Stateless
public class ModalidadVentaBean implements IModalidadVentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	public Collection<ModalidadVenta> list() {
		Collection<ModalidadVenta> result = new LinkedList<ModalidadVenta>();
		
		try {
			TypedQuery<ModalidadVenta> query = entityManager.createQuery(
				"SELECT m FROM ModalidadVenta m",
				ModalidadVenta.class
			);
			
			for (ModalidadVenta modalidadVenta : query.getResultList()) {
				result.add(modalidadVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ModalidadVenta getById(Long id) {
		ModalidadVenta result = null;
		
		try {
			result = entityManager.find(ModalidadVenta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}