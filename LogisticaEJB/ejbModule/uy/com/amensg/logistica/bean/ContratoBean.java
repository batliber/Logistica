package uy.com.amensg.logistica.bean;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.Empresa;

@Stateless
public class ContratoBean implements IContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	public Contrato getById(Long id) {
		Contrato result = null;
		
		try {
			result = entityManager.find(Contrato.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Contrato getByMidEmpresa(Long mid, Empresa empresa) {
		Contrato result = null;
		
		try {
			TypedQuery<Contrato> query = 
				entityManager.createQuery(
					"SELECT c FROM Contrato c"
					+ " WHERE c.mid = :mid"
					+ " AND c.empresa = :empresa", 
					Contrato.class
				);
			query.setParameter("mid", mid);
			query.setParameter("empresa", empresa);
			
			List<Contrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Contrato update(Contrato contrato) {
		try {
			contrato.setUact(new Long(1));
			contrato.setFact(GregorianCalendar.getInstance().getTime());
			contrato.setTerm(new Long(1));
			
			if (contrato.getId() == null) {
				TypedQuery<Long> query = 
					entityManager.createQuery(
						"SELECT MAX(c.numeroTramite)"
						+ " FROM Contrato c",
						Long.class
					);
				
				Long maxNumeroTramite = query.getSingleResult();
				
				if (maxNumeroTramite != null) {
					contrato.setNumeroTramite(maxNumeroTramite + 1);
				} else {
					contrato.setNumeroTramite(new Long(1));
				}
				
				entityManager.persist(contrato);
			} else {
				entityManager.merge(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contrato;
	}
}