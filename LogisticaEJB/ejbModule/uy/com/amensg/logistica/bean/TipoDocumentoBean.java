package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoDocumento;

@Stateless
public class TipoDocumentoBean implements ITipoDocumentoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<TipoDocumento> list() {
		Collection<TipoDocumento> result = new LinkedList<TipoDocumento>();
		
		try {
			TypedQuery<TipoDocumento> query = entityManager.createQuery(
				"SELECT t FROM TipoDocumento t",
				TipoDocumento.class
			);
			
			for (TipoDocumento tipoDocumento : query.getResultList()) {
				result.add(tipoDocumento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}