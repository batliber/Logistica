package uy.com.amensg.logistica.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Configuracion;

@Stateless
public class ConfiguracionBean implements IConfiguracionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public String getProperty(String clave) {
		String result = null;
		
		try {
			TypedQuery<Configuracion> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Configuracion c"
					+ " WHERE c.clave = :clave",
					Configuracion.class
				);
			query.setParameter("clave", clave);
			
			List<Configuracion> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0).getValor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}