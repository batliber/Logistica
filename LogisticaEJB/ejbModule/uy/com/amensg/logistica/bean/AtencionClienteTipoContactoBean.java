package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.AtencionClienteTipoContacto;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class AtencionClienteTipoContactoBean implements IAtencionClienteTipoContactoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<AtencionClienteTipoContacto> list() {
		Collection<AtencionClienteTipoContacto> result = new LinkedList<AtencionClienteTipoContacto>();
		
		try {
			TypedQuery<AtencionClienteTipoContacto> query = entityManager.createQuery(
				"SELECT a "
				+ " FROM AtencionClienteTipoContacto a"
				+ " ORDER BY a.descripcion", 
				AtencionClienteTipoContacto.class
			);
			
			for (AtencionClienteTipoContacto atencionClienteTipoContacto : query.getResultList()) {
				result.add(atencionClienteTipoContacto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<AtencionClienteTipoContacto>().list(entityManager, metadataConsulta, new AtencionClienteTipoContacto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<AtencionClienteTipoContacto>().count(entityManager, metadataConsulta, new AtencionClienteTipoContacto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AtencionClienteTipoContacto getById(Long id) {
		AtencionClienteTipoContacto result = null;
		
		try {
			result = entityManager.find(AtencionClienteTipoContacto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AtencionClienteTipoContacto save(AtencionClienteTipoContacto atencionClienteTipoContacto) {
		AtencionClienteTipoContacto result = null;
		
		try {
			atencionClienteTipoContacto.setFcre(atencionClienteTipoContacto.getFact());
			atencionClienteTipoContacto.setUcre(atencionClienteTipoContacto.getUact());
			
			entityManager.persist(atencionClienteTipoContacto);
			
			result = atencionClienteTipoContacto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(AtencionClienteTipoContacto atencionClienteTipoContacto) {
		try {
			AtencionClienteTipoContacto managedAtencionClienteTipoContacto = entityManager.find(AtencionClienteTipoContacto.class, atencionClienteTipoContacto.getId());
			
//			Date date = GregorianCalendar.getInstance().getTime();
			
			managedAtencionClienteTipoContacto.setFact(atencionClienteTipoContacto.getFact());
			managedAtencionClienteTipoContacto.setTerm(atencionClienteTipoContacto.getTerm());
			managedAtencionClienteTipoContacto.setUact(atencionClienteTipoContacto.getUact());
			
			entityManager.merge(managedAtencionClienteTipoContacto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(AtencionClienteTipoContacto atencionClienteTipoContacto) {
		try {
			AtencionClienteTipoContacto atencionClienteTipoContactoManaged = entityManager.find(AtencionClienteTipoContacto.class, atencionClienteTipoContacto.getId());
			
			atencionClienteTipoContactoManaged.setDescripcion(atencionClienteTipoContacto.getDescripcion());
			
			atencionClienteTipoContactoManaged.setFact(atencionClienteTipoContacto.getFact());
			atencionClienteTipoContactoManaged.setTerm(atencionClienteTipoContacto.getTerm());
			atencionClienteTipoContactoManaged.setUact(atencionClienteTipoContacto.getUact());
			
			entityManager.merge(atencionClienteTipoContactoManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}