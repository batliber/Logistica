package uy.com.amensg.logistica.bean;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ContratoANTELTipoOperacionModelo;

@Stateless
public class ContratoANTELTipoOperacionModeloBean implements IContratoANTELTipoOperacionModeloBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public ContratoANTELTipoOperacionModelo getByTipoOperacion(Long tipoOperacion) {
		ContratoANTELTipoOperacionModelo result = null;
		
		try {
			TypedQuery<ContratoANTELTipoOperacionModelo> query =
				entityManager.createQuery(
					"SELECT catom"
					+ " FROM ContratoANTELTipoOperacionModelo catom"
					+ " WHERE catom.tipoOperacion = :tipoOperacion",
					ContratoANTELTipoOperacionModelo.class
				);
			query.setParameter("tipoOperacion", tipoOperacion);
			
			List<ContratoANTELTipoOperacionModelo> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}