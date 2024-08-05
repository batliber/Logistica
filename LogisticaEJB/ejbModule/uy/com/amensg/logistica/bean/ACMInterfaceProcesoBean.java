package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadistica;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ACMInterfaceProcesoBean implements IACMInterfaceProcesoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;

	public MetadataConsultaResultado listEstadisticas(MetadataConsulta metadataConsulta) {
		return new QueryBuilder<ACMInterfaceProcesoEstadistica>()
			.list(
				entityManager, 
				metadataConsulta, 
				new ACMInterfaceProcesoEstadistica()
			);
	}
	
	public Long countEstadisticas(MetadataConsulta metadataConsulta) {
		return new QueryBuilder<ACMInterfaceProcesoEstadistica>()
			.count(
				entityManager, 
				metadataConsulta, 
				new ACMInterfaceProcesoEstadistica()
			);
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

	public void finalizarProcesos() {
		try {
			Query queryMidsEnProceso = entityManager.createQuery(
				"SELECT DISTINCT m.procesoId"
				+ " FROM ACMInterfaceMid m"
				+ " WHERE m.procesoId IS NOT NULL"
				+ " AND m.estado.id NOT IN ("
					+ " :estadoIdProcesado, :estadoIdListaVacia, :estadoIdListaNegra"
				+ " )"
			);
			queryMidsEnProceso.setParameter(
				"estadoIdProcesado", 
				Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"))
			);
			queryMidsEnProceso.setParameter(
				"estadoIdListaVacia", 
				Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaVacia"))
			);
			queryMidsEnProceso.setParameter(
				"estadoIdListaNegra", 
				Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra"))
			);
			
			Collection<Long> procesosConMidsSinTerminar = new LinkedList<Long>();
			for (Object object : queryMidsEnProceso.getResultList()) {
				procesosConMidsSinTerminar.add((Long) object);
			}
			
			Query queryUltimaFactProcesadaXProceso = entityManager.createQuery(
				"SELECT m.procesoId, max(m.fact)"
				+ " FROM ACMInterfaceMid m"
				+ " WHERE m.procesoId IS NOT NULL"
				+ " AND m.estado.id IN ("
					+ " :estadoIdProcesado, :estadoIdListaVacia"
				+ " )"
				+ " GROUP BY m.procesoId"
			);
			queryUltimaFactProcesadaXProceso.setParameter(
				"estadoIdProcesado", 
				Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"))
			);
			queryUltimaFactProcesadaXProceso.setParameter(
				"estadoIdListaVacia", 
				Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaVacia"))
			);
			
			Map<Long, Date> ultimaFactXProceso = new HashMap<Long, Date>();
			
			for (Object object : queryUltimaFactProcesadaXProceso.getResultList()) {
				Object[] queryResult = (Object[]) object;
				
				Long procesoId = (Long) queryResult[0];
				Date fact = (Date) queryResult[1];
				
				ultimaFactXProceso.put(procesoId, fact);
			}
			
			TypedQuery<ACMInterfaceProceso> queryProcesos =
				entityManager.createQuery(
					"SELECT p"
					+ " FROM ACMInterfaceProceso p"
					+ " WHERE p.id IN :procesosARevisar"
					+ " AND p.id NOT IN :procesosConMidsSinTerminar", 
					ACMInterfaceProceso.class
				);
			queryProcesos.setParameter("procesosARevisar", ultimaFactXProceso.keySet());
			queryProcesos.setParameter("procesosConMidsSinTerminar", procesosConMidsSinTerminar);
			
			for (ACMInterfaceProceso acmInterfaceProceso : queryProcesos.getResultList()) {
				if (acmInterfaceProceso.getFechaFin() == null) {
					acmInterfaceProceso.setFechaFin(ultimaFactXProceso.get(acmInterfaceProceso.getId()));
				
					entityManager.merge(acmInterfaceProceso);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}