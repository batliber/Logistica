package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.AtencionClienteConcepto;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class AtencionClienteConceptoBean implements IAtencionClienteConceptoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<AtencionClienteConcepto> list() {
		Collection<AtencionClienteConcepto> result = new LinkedList<AtencionClienteConcepto>();
		
		try {
			TypedQuery<AtencionClienteConcepto> query = entityManager.createQuery(
				"SELECT a "
				+ " FROM AtencionClienteConcepto a"
				+ " ORDER BY a.descripcion", 
				AtencionClienteConcepto.class
			);
			
			for (AtencionClienteConcepto atencionClienteConcepto : query.getResultList()) {
				result.add(atencionClienteConcepto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<AtencionClienteConcepto>().list(entityManager, metadataConsulta, new AtencionClienteConcepto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<AtencionClienteConcepto>().count(entityManager, metadataConsulta, new AtencionClienteConcepto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AtencionClienteConcepto getById(Long id) {
		AtencionClienteConcepto result = null;
		
		try {
			result = entityManager.find(AtencionClienteConcepto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AtencionClienteConcepto save(AtencionClienteConcepto atencionClienteConcepto) {
		AtencionClienteConcepto result = null;
		
		try {
			atencionClienteConcepto.setFcre(atencionClienteConcepto.getFact());
			atencionClienteConcepto.setUcre(atencionClienteConcepto.getUact());
			
			entityManager.persist(atencionClienteConcepto);
			
			result = atencionClienteConcepto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(AtencionClienteConcepto atencionClienteConcepto) {
		try {
			AtencionClienteConcepto managedAtencionClienteConcepto = entityManager.find(AtencionClienteConcepto.class, atencionClienteConcepto.getId());
			
//			Date date = GregorianCalendar.getInstance().getTime();
			
			managedAtencionClienteConcepto.setFact(atencionClienteConcepto.getFact());
			managedAtencionClienteConcepto.setTerm(atencionClienteConcepto.getTerm());
			managedAtencionClienteConcepto.setUact(atencionClienteConcepto.getUact());
			
			entityManager.merge(managedAtencionClienteConcepto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(AtencionClienteConcepto atencionClienteConcepto) {
		try {
			AtencionClienteConcepto atencionClienteConceptoManaged = entityManager.find(AtencionClienteConcepto.class, atencionClienteConcepto.getId());
			
			atencionClienteConceptoManaged.setDescripcion(atencionClienteConcepto.getDescripcion());
			
			atencionClienteConceptoManaged.setFact(atencionClienteConcepto.getFact());
			atencionClienteConceptoManaged.setTerm(atencionClienteConcepto.getTerm());
			atencionClienteConceptoManaged.setUact(atencionClienteConcepto.getUact());
			
			entityManager.merge(atencionClienteConceptoManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}