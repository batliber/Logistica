package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldoArchivoAdjunto;

@Stateless
public class RecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean implements IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> listByRecargaSolicitudAcreditacionSaldoId(Long id) {
		Collection<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> result = new LinkedList<RecargaSolicitudAcreditacionSaldoArchivoAdjunto>();
		
		try {
			TypedQuery<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> query =
				entityManager.createQuery(
					"SELECT ra"
					+ " FROM RecargaSolicitudAcreditacionSaldoArchivoAdjunto ra"
					+ " WHERE ra.recargaSolicitudAcreditacionSaldo.id = :recargaSolicitudAcreditacionSaldoId",
					RecargaSolicitudAcreditacionSaldoArchivoAdjunto.class
				);
			query.setParameter("recargaSolicitudAcreditacionSaldoId", id);
			
			Collection<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}