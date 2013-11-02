package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class ACMInterfaceBean implements IACMInterfaceBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public ACMInterfaceMid getNextMidSinProcesar() {
		ACMInterfaceMid result = null;
		
		try {
			TypedQuery<ACMInterfaceMid> query = entityManager.createQuery(
				"SELECT a FROM ACMInterfaceMid a"
				+ " WHERE a.estado in ("
						+ " :paraProcesar, :paraProcesarPrioritario"
				+ " )"
				+ " ORDER BY a.estado DESC"
				, ACMInterfaceMid.class);
			query.setParameter("paraProcesar", 
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesar")));
			query.setParameter("paraProcesarPrioritario", 
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario")));
			query.setMaxResults(1);
			
			result =  query.getResultList().get(0);
			
			result.setEstado(new Long(1));
			
			entityManager.merge(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ACMInterfaceContrato acmInterfaceContrato) {
		try {
			entityManager.merge(acmInterfaceContrato);
			
			ACMInterfaceMid acmInterfaceMid = entityManager.find(ACMInterfaceMid.class, acmInterfaceContrato.getMid());
			acmInterfaceMid.setEstado(
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"))
			);
			
			entityManager.merge(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfacePrepago acmInterfacePrepago) {
		try {
			entityManager.merge(acmInterfacePrepago);
			
			ACMInterfaceMid acmInterfaceMid = entityManager.find(ACMInterfaceMid.class, acmInterfacePrepago.getMid());
			acmInterfaceMid.setEstado(
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"))
			);
			
			entityManager.merge(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfaceMid acmInterfaceMid) {
		try {
			ACMInterfaceMid acmInterfaceMidManaged = 
				entityManager.find(ACMInterfaceMid.class, acmInterfaceMid.getMid());
			
			acmInterfaceMidManaged.setEstado(acmInterfaceMid.getEstado());
			
			entityManager.merge(acmInterfaceMidManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}