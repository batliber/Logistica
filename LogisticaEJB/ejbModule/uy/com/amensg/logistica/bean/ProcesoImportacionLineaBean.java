package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.ProcesoImportacionLinea;

@Stateless
public class ProcesoImportacionLineaBean implements IProcesoImportacionLineaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<ProcesoImportacionLinea> listByProcesoImportacion(ProcesoImportacion procesoImportacion) {
		Collection<ProcesoImportacionLinea> result = new LinkedList<ProcesoImportacionLinea>();
		
		try {
			TypedQuery<ProcesoImportacionLinea> query =
				entityManager.createQuery(
					"SELECT l"
					+ " FROM ProcesoImportacionLinea l"
					+ " WHERE l.procesoImportacion.id = :procesoImportacionId",
					ProcesoImportacionLinea.class
				);
			query.setParameter("procesoImportacionId", procesoImportacion.getId());
			
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}