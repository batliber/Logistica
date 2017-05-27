package uy.com.amensg.logistica.bean;

import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ActivacionConexion;

@Stateless
public class ActivacionConexionBean implements IActivacionConexionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	public ActivacionConexion getRandomByEmpresaId(Long empresaId) {
		ActivacionConexion result = null;
		
		try {
			TypedQuery<ActivacionConexion> query =
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ActivacionConexion a"
					+ " WHERE a.empresa.id = :empresaId",
					ActivacionConexion.class
				);
			query.setParameter("empresaId", empresaId);
			
			Random random = new Random();
			
			List<ActivacionConexion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				int randomIndex = random.nextInt(resultList.size());
				
				result = resultList.get(randomIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}