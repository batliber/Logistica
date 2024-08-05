package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.ProcesoNegocio;

@Stateless
public class EstadoBean implements IEstadoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<Estado> list() {
		Collection<Estado> result = new LinkedList<Estado>();
		
		try {
			TypedQuery<Estado> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM Estado e"
				+ " ORDER BY e.nombre", 
				Estado.class
			);
			
			for (Estado estado : query.getResultList()) {
				result.add(estado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Estado> listByProcesoNegocio(ProcesoNegocio procesoNegocio) {
		Collection<Estado> result = new LinkedList<Estado>();
		
		try {
			Query query = 
				entityManager.createNativeQuery(
					"SELECT e.id,"
					+ " e.nombre,"
					+ " e.fcre,"
					+ " e.ucre,"
					+ " e.fact,"
					+ " e.uact,"
					+ " e.term"
					+ " FROM estado e"
					+ " INNER JOIN proceso_negocio_estado pne ON pne.estado_id = e.id"
					+ " WHERE pne.proceso_negocio_id = :procesoNegocioId"
				);
			query.setParameter("procesoNegocioId", procesoNegocio.getId());
			
			for (Object object : query.getResultList()) {
				Object[] tuple = (Object[]) object;
				
				Estado estado = new Estado();
				estado.setId((Long) ((Integer) tuple[0]).longValue());
				
				estado.setNombre((String) tuple[1]);
				
				estado.setFcre(null);
				estado.setUcre(null);
				estado.setFact(null);
				estado.setUact(null);
				estado.setTerm(null);
				
				result.add(estado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Estado getById(Long id) {
		Estado result = null;
		
		try {
			result = entityManager.find(Estado.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}