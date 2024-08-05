package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.AtencionClienteRespuestaTecnicaComercial;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class AtencionClienteRespuestaTecnicaComercialBean implements IAtencionClienteRespuestaTecnicaComercialBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<AtencionClienteRespuestaTecnicaComercial> list() {
		Collection<AtencionClienteRespuestaTecnicaComercial> result = new LinkedList<AtencionClienteRespuestaTecnicaComercial>();
		
		try {
			TypedQuery<AtencionClienteRespuestaTecnicaComercial> query = entityManager.createQuery(
				"SELECT a "
				+ " FROM AtencionClienteRespuestaTecnicaComercial a"
				+ " ORDER BY a.descripcion", 
				AtencionClienteRespuestaTecnicaComercial.class
			);
			
			for (AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial : query.getResultList()) {
				result.add(atencionClienteRespuestaTecnicaComercial);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<AtencionClienteRespuestaTecnicaComercial>().list(entityManager, metadataConsulta, new AtencionClienteRespuestaTecnicaComercial());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<AtencionClienteRespuestaTecnicaComercial>().count(entityManager, metadataConsulta, new AtencionClienteRespuestaTecnicaComercial());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AtencionClienteRespuestaTecnicaComercial getById(Long id) {
		AtencionClienteRespuestaTecnicaComercial result = null;
		
		try {
			result = entityManager.find(AtencionClienteRespuestaTecnicaComercial.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AtencionClienteRespuestaTecnicaComercial save(AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial) {
		AtencionClienteRespuestaTecnicaComercial result = null;
		
		try {
			atencionClienteRespuestaTecnicaComercial.setFcre(atencionClienteRespuestaTecnicaComercial.getFact());
			atencionClienteRespuestaTecnicaComercial.setUcre(atencionClienteRespuestaTecnicaComercial.getUact());
			
			entityManager.persist(atencionClienteRespuestaTecnicaComercial);
			
			result = atencionClienteRespuestaTecnicaComercial;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial) {
		try {
			AtencionClienteRespuestaTecnicaComercial managedAtencionClienteRespuestaTecnicaComercial = entityManager.find(AtencionClienteRespuestaTecnicaComercial.class, atencionClienteRespuestaTecnicaComercial.getId());
			
//			Date date = GregorianCalendar.getInstance().getTime();
			
			managedAtencionClienteRespuestaTecnicaComercial.setFact(atencionClienteRespuestaTecnicaComercial.getFact());
			managedAtencionClienteRespuestaTecnicaComercial.setTerm(atencionClienteRespuestaTecnicaComercial.getTerm());
			managedAtencionClienteRespuestaTecnicaComercial.setUact(atencionClienteRespuestaTecnicaComercial.getUact());
			
			entityManager.merge(managedAtencionClienteRespuestaTecnicaComercial);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial) {
		try {
			AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercialManaged = entityManager.find(AtencionClienteRespuestaTecnicaComercial.class, atencionClienteRespuestaTecnicaComercial.getId());
			
			atencionClienteRespuestaTecnicaComercialManaged.setDescripcion(atencionClienteRespuestaTecnicaComercial.getDescripcion());
			
			atencionClienteRespuestaTecnicaComercialManaged.setFact(atencionClienteRespuestaTecnicaComercial.getFact());
			atencionClienteRespuestaTecnicaComercialManaged.setTerm(atencionClienteRespuestaTecnicaComercial.getTerm());
			atencionClienteRespuestaTecnicaComercialManaged.setUact(atencionClienteRespuestaTecnicaComercial.getUact());
			
			entityManager.merge(atencionClienteRespuestaTecnicaComercialManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}