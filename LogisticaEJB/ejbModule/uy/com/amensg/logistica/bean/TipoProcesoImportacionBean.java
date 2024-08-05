package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoProcesoImportacion;

@Stateless
public class TipoProcesoImportacionBean implements ITipoProcesoImportacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<TipoProcesoImportacion> list() {
		Collection<TipoProcesoImportacion> result = new LinkedList<TipoProcesoImportacion>();
		
		try {
			TypedQuery<TipoProcesoImportacion> query = entityManager.createQuery(
				"SELECT t"
				+ " FROM TipoProcesoImportacion t"
				+ " ORDER BY t.descripcion", 
				TipoProcesoImportacion.class
			);
			
			for (TipoProcesoImportacion tipoProcesoImportacion : query.getResultList()) {
				result.add(tipoProcesoImportacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoProcesoImportacion getById(Long id) {
		TipoProcesoImportacion result = null;
		
		try {
			result = entityManager.find(TipoProcesoImportacion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}