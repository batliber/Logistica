package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ModalidadVenta;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ModalidadVentaBean implements IModalidadVentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;

	public Collection<ModalidadVenta> list() {
		Collection<ModalidadVenta> result = new LinkedList<ModalidadVenta>();
		
		try {
			TypedQuery<ModalidadVenta> query = entityManager.createQuery(
				"SELECT m FROM ModalidadVenta m",
				ModalidadVenta.class
			);
			
			for (ModalidadVenta modalidadVenta : query.getResultList()) {
				result.add(modalidadVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<ModalidadVenta>().list(entityManager, metadataConsulta, new ModalidadVenta());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ModalidadVenta>().count(entityManager, metadataConsulta, new ModalidadVenta());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ModalidadVenta getById(Long id) {
		ModalidadVenta result = null;
		
		try {
			result = entityManager.find(ModalidadVenta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ModalidadVenta save(ModalidadVenta modalidadVenta) {
		ModalidadVenta result = null;
		
		try {
			modalidadVenta.setFcre(modalidadVenta.getFact());
			modalidadVenta.setUcre(modalidadVenta.getUact());
			
			entityManager.persist(modalidadVenta);
			
			result = modalidadVenta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(ModalidadVenta modalidadVenta) {
		try {
			ModalidadVenta managedModalidadVenta = entityManager.find(ModalidadVenta.class, modalidadVenta.getId());
			
//			Date date = GregorianCalendar.getInstance().getTime();
			
			managedModalidadVenta.setFact(modalidadVenta.getFact());
			managedModalidadVenta.setTerm(modalidadVenta.getTerm());
			managedModalidadVenta.setUact(modalidadVenta.getUact());
			
			entityManager.merge(managedModalidadVenta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(ModalidadVenta modalidadVenta) {
		try {
			ModalidadVenta modalidadVentaManaged = entityManager.find(ModalidadVenta.class, modalidadVenta.getId());
			
			modalidadVentaManaged.setDescripcion(modalidadVenta.getDescripcion());
			
			modalidadVentaManaged.setFact(modalidadVenta.getFact());
			modalidadVentaManaged.setTerm(modalidadVenta.getTerm());
			modalidadVentaManaged.setUact(modalidadVenta.getUact());
			
			entityManager.merge(modalidadVentaManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}