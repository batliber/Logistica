package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class BarrioBean implements IBarrioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Barrio> list() {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			TypedQuery<Barrio> query =
				entityManager.createQuery(
					"SELECT b"
					+ " FROM Barrio b"
					+ " ORDER BY b.nombre", 
					Barrio.class
				);
			
			for (Barrio barrio : query.getResultList()) {
				result.add(barrio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Barrio> listMinimal() {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			TypedQuery<Object[]> query =
				entityManager.createQuery(
					"SELECT b.id, b.nombre"
					+ " FROM Barrio b"
					+ " ORDER BY b.nombre", 
					Object[].class
				);
			
			for (Object[] barrio : query.getResultList()) {
				Barrio barrioMinimal = new Barrio();
				barrioMinimal.setId((Long)barrio[0]);
				barrioMinimal.setNombre((String)barrio[1]);
				
				result.add(barrioMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<Barrio> listByDepartamentoId(Long departamentoId) {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			TypedQuery<Barrio> query = entityManager.createQuery(
				"SELECT b"
				+ " FROM Barrio b"
				+ " WHERE b.departamento.id = :departamentoId"
				+ " ORDER BY b.nombre", 
				Barrio.class
			);
			query.setParameter("departamentoId", departamentoId);
			
			for (Barrio barrio : query.getResultList()) {
				result.add(barrio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Barrio> listMinimalByDepartamentoId(Long departamentoId) {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			TypedQuery<Object[]> query =
				entityManager.createQuery(
					"SELECT b.id, b.nombre"
					+ " FROM Barrio b"
					+ " WHERE b.departamento.id = :departamentoId"
					+ " ORDER BY b.nombre", 
					Object[].class
				);
			query.setParameter("departamentoId", departamentoId);
			
			for (Object[] barrio : query.getResultList()) {
				Barrio barrioMinimal = new Barrio();
				barrioMinimal.setId((Long)barrio[0]);
				barrioMinimal.setNombre((String)barrio[1]);
				
				result.add(barrioMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<Barrio>().list(entityManager, metadataConsulta, new Barrio());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Barrio>().count(entityManager, metadataConsulta, new Barrio());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Barrio getById(Long id) {
		Barrio result = null;
		
		try {
			result = entityManager.find(Barrio.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Barrio save(Barrio barrio) {
		Barrio result = null;
		
		try {
			barrio.setFcre(barrio.getFact());
			barrio.setUcre(barrio.getUact());
			
			entityManager.persist(barrio);
			
			result = barrio;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(Barrio barrio) {
		try {
			Barrio managedBarrio = entityManager.find(Barrio.class, barrio.getId());
			
//			managedBarrio.setFechaBaja(date);
			
			managedBarrio.setFact(barrio.getFact());
			managedBarrio.setTerm(barrio.getTerm());
			managedBarrio.setUact(barrio.getUact());
			
			entityManager.merge(managedBarrio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Barrio barrio) {
		try {
			Barrio managedBarrio = entityManager.find(Barrio.class, barrio.getId());
			
			managedBarrio.setNombre(barrio.getNombre());
			
			managedBarrio.setDepartamento(barrio.getDepartamento());
			managedBarrio.setZona(barrio.getZona());
			
			managedBarrio.setFact(barrio.getFact());
			managedBarrio.setTerm(barrio.getTerm());
			managedBarrio.setUact(barrio.getUact());
			
			entityManager.merge(managedBarrio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}