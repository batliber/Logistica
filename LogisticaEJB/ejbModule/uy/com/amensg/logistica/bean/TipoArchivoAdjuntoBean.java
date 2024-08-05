package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoArchivoAdjunto;

@Stateless
public class TipoArchivoAdjuntoBean implements ITipoArchivoAdjuntoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<TipoArchivoAdjunto> list() {
		Collection<TipoArchivoAdjunto> result = new LinkedList<TipoArchivoAdjunto>();
		
		try {
			TypedQuery<TipoArchivoAdjunto> query = entityManager.createQuery(
				"SELECT t "
				+ " FROM TipoArchivoAdjunto t"
				+ " ORDER BY t.descripcion", 
				TipoArchivoAdjunto.class
			);
			
			for (TipoArchivoAdjunto tipoArchivoAdjunto : query.getResultList()) {
				result.add(tipoArchivoAdjunto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoArchivoAdjunto getById(Long id) {
		TipoArchivoAdjunto result = null;
		
		try {
			result = entityManager.find(TipoArchivoAdjunto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}