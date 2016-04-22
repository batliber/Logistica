package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;

@Stateless
public class ResultadoEntregaDistribucionBean implements IResultadoEntregaDistribucionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<ResultadoEntregaDistribucion> list() {
		Collection<ResultadoEntregaDistribucion> result = new LinkedList<ResultadoEntregaDistribucion>();
		
		try {
			TypedQuery<ResultadoEntregaDistribucion> query = 
				entityManager.createQuery(
					"SELECT r"
					+ " FROM ResultadoEntregaDistribucion r"
					+ " ORDER BY r.descripcion", 
					ResultadoEntregaDistribucion.class
				);
			
			for (ResultadoEntregaDistribucion resultadoEntregaDistribucion : query.getResultList()) {
				result.add(resultadoEntregaDistribucion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}