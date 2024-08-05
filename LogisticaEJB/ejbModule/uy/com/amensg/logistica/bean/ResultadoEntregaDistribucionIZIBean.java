package uy.com.amensg.logistica.bean;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucionIZI;

@Stateless
public class ResultadoEntregaDistribucionIZIBean implements IResultadoEntregaDistribucionIZIBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public ResultadoEntregaDistribucionIZI getByResultadoEntregaDistribucionEstado(
			ResultadoEntregaDistribucion resultadoEntregaDistribucion, Estado estado) {
		ResultadoEntregaDistribucionIZI result = null;
		
		try {
			TypedQuery<ResultadoEntregaDistribucionIZI> query =
				entityManager.createQuery(
					"SELECT r"
					+ " FROM ResultadoEntregaDistribucionIZI r"
					+ " WHERE r.resultadoEntregaDistribucion = :resultadoEntregaDistribucion"
					+ " AND r.estado = :estado", 
					ResultadoEntregaDistribucionIZI.class
				);
			query.setParameter("resultadoEntregaDistribucion", resultadoEntregaDistribucion);
			query.setParameter("estado", estado);
			
			List<ResultadoEntregaDistribucionIZI> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}