package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.ACMInterfaceMid;

@Stateless
public class ACMInterfaceMidBean implements IACMInterfaceMidBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public void update(ACMInterfaceMid acmInterfaceMid) {
		try {
			entityManager.merge(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}