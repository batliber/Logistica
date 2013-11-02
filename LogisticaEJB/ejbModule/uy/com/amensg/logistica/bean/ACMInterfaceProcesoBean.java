package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadistica;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class ACMInterfaceProcesoBean implements IACMInterfaceProcesoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	public Collection<ACMInterfaceProcesoEstadistica> listEstadisticas() {
		Collection<ACMInterfaceProcesoEstadistica> result = new LinkedList<ACMInterfaceProcesoEstadistica>();
		
		try {
			TypedQuery<ACMInterfaceProceso> queryProceso = entityManager.createQuery(
				"SELECT p FROM ACMInterfaceProceso p",
				ACMInterfaceProceso.class
			);
			
			Map<Long, ACMInterfaceProcesoEstadistica> estadisticas = new HashMap<Long, ACMInterfaceProcesoEstadistica>();
			for (ACMInterfaceProceso acmInterfaceProceso : queryProceso.getResultList()) {
				ACMInterfaceProcesoEstadistica acmInterfaceProcesoEstadistica = new ACMInterfaceProcesoEstadistica();
				acmInterfaceProcesoEstadistica.setFechaFin(acmInterfaceProceso.getFechaFin());
				acmInterfaceProcesoEstadistica.setFechaInicio(acmInterfaceProceso.getFechaInicio());
				acmInterfaceProcesoEstadistica.setId(acmInterfaceProceso.getId());
				
				estadisticas.put(acmInterfaceProceso.getId(), acmInterfaceProcesoEstadistica);
			}
			
			Query queryMid = entityManager.createQuery(
				"SELECT m.procesoId, m.estado, count(m) FROM ACMInterfaceMid m GROUP BY m.procesoId, m.estado"
			);
			
			for (Object object : queryMid.getResultList()) {
				Object[] queryResult = (Object[]) object;
				
				if (queryResult[0] != null) {
					ACMInterfaceProcesoEstadistica acmInterfaceProcesoEstadistica = estadisticas.get((Long)queryResult[0]);
					
					Long estado = (Long) queryResult[1];
					
					if (estado.equals(new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesar")))) {
						acmInterfaceProcesoEstadistica.setCantidadRegistrosParaProcesar((Long) queryResult[2]);
					} else if (estado.equals(new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario")))) {
						acmInterfaceProcesoEstadistica.setCantidadRegistrosParaProcesarPrioritario((Long) queryResult[2]);
					} else if (estado.equals(new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado")))) {
						acmInterfaceProcesoEstadistica.setCantidadRegistrosProcesado((Long) queryResult[2]);
					} else if (estado.equals(new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.EnProceso")))) {
						acmInterfaceProcesoEstadistica.setCantidadRegistrosEnProceso((Long) queryResult[2]);
					}
				}
			}
			
			List<ACMInterfaceProcesoEstadistica> toOrder = new LinkedList<ACMInterfaceProcesoEstadistica>();
			for (ACMInterfaceProcesoEstadistica acmInterfaceProcesoEstadistica : estadisticas.values()) {
				toOrder.add(acmInterfaceProcesoEstadistica);
			}
			
			Collections.sort(toOrder);
			
			result = toOrder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ACMInterfaceProceso save(ACMInterfaceProceso acmInterfaceProceso) {
		ACMInterfaceProceso result = null;
		
		try {
			result = entityManager.merge(acmInterfaceProceso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}