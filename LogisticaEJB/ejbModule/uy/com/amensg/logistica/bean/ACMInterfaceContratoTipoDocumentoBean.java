package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceContratoTipoDocumento;

@Stateless
public class ACMInterfaceContratoTipoDocumentoBean implements IACMInterfaceContratoTipoDocumentoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<ACMInterfaceContratoTipoDocumento> list() {
		Collection<ACMInterfaceContratoTipoDocumento> result = new LinkedList<ACMInterfaceContratoTipoDocumento>();
		
		try {
			TypedQuery<ACMInterfaceContratoTipoDocumento> query = entityManager.createQuery(
				"SELECT t"
				+ " FROM ACMInterfaceContratoTipoDocumento t"
				+ " ORDER BY t.id", 
				ACMInterfaceContratoTipoDocumento.class
			);
			
			for (ACMInterfaceContratoTipoDocumento contratoTipoDocumento : query.getResultList()) {
				result.add(contratoTipoDocumento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}