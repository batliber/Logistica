package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaEmpresa;
import uy.com.amensg.logistica.entities.RecargaMovimiento;

@Stateless
public class RecargaMovimientoBean implements IRecargaMovimientoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<RecargaMovimiento> listSaldoByPuntoVenta(PuntoVenta puntoVenta) {
		Collection<RecargaMovimiento> result = new LinkedList<RecargaMovimiento>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT r.recargaEmpresa.id, SUM(r.monto) AS saldo"
				+ " FROM RecargaMovimiento r"
				+ " WHERE r.puntoVenta = :puntoVenta"
				+ " GROUP BY r.recargaEmpresa.id"
			);
			query.setParameter("puntoVenta", puntoVenta);
			
			for (Object object : query.getResultList()) {
				Object[] values = (Object[]) object;
				
				RecargaMovimiento recargaMovimiento = new RecargaMovimiento();
				
				RecargaEmpresa recargaEmpresa = entityManager.find(RecargaEmpresa.class, (Long) values[0]);
				
				recargaMovimiento.setRecargaEmpresa(recargaEmpresa);
				
				recargaMovimiento.setMonto((Double) values[1]);
				
				result.add(recargaMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Double getSaldoByPuntoVentaRecargaEmpresa(PuntoVenta puntoVenta, RecargaEmpresa recargaEmpresa) {
		Double result = null;
		
		try {
			TypedQuery<Double> query = entityManager.createQuery(
				"SELECT SUM(r.monto) AS saldo"
				+ " FROM RecargaMovimiento r"
				+ " WHERE r.puntoVenta = :puntoVenta"
				+ " AND r.recargaEmpresa = :recargaEmpresa"
				+ " GROUP BY r.recargaEmpresa.id",
				Double.class
			);
			query.setParameter("puntoVenta", puntoVenta);
			query.setParameter("recargaEmpresa", recargaEmpresa);
			
			List<Double> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = (Double) resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RecargaMovimiento save(RecargaMovimiento recargaMovimiento) {
		RecargaMovimiento result = null;
		
		try {
			recargaMovimiento.setFcre(recargaMovimiento.getFact());
			recargaMovimiento.setUcre(recargaMovimiento.getUact());
			
			entityManager.persist(recargaMovimiento);
			
			result = recargaMovimiento;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}