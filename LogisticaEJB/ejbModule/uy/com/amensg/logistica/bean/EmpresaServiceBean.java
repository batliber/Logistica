package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class EmpresaServiceBean implements IEmpresaServiceBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<EmpresaService> list() {
		Collection<EmpresaService> result = new LinkedList<EmpresaService>();
		
		try {
			TypedQuery<EmpresaService> query = entityManager.createQuery(
				"SELECT es"
				+ " FROM EmpresaService es"
				+ " ORDER BY es.nombre", 
				EmpresaService.class
			);
			
			for (EmpresaService empresaService : query.getResultList()) {
				result.add(empresaService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<EmpresaService>().list(entityManager, metadataConsulta, new EmpresaService());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<EmpresaService>().count(entityManager, metadataConsulta, new EmpresaService());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EmpresaService getById(Long id) {
		EmpresaService result = null;
		
		try {
			result = entityManager.find(EmpresaService.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(EmpresaService empresaService) {
		try {
			empresaService.setFcre(empresaService.getFact());
			empresaService.setUcre(empresaService.getUact());
			
			entityManager.persist(empresaService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(EmpresaService empresaService) {
		try {
			EmpresaService managedEmpresaService = entityManager.find(EmpresaService.class, empresaService.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			managedEmpresaService.setFechaBaja(date);
			
			managedEmpresaService.setFact(empresaService.getFact());
			managedEmpresaService.setTerm(empresaService.getTerm());
			managedEmpresaService.setUact(empresaService.getUact());
			
			entityManager.merge(managedEmpresaService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(EmpresaService empresaService) {
		try {
			EmpresaService empresaServiceManaged = entityManager.find(EmpresaService.class, empresaService.getId());
			
			empresaServiceManaged.setDireccion(empresaService.getDireccion());
			empresaServiceManaged.setNombre(empresaService.getNombre());
			empresaServiceManaged.setTelefono(empresaService.getTelefono());
			
			empresaServiceManaged.setFact(empresaService.getFact());
			empresaServiceManaged.setFechaBaja(empresaService.getFechaBaja());
			empresaServiceManaged.setTerm(empresaService.getTerm());
			empresaServiceManaged.setUact(empresaService.getUact());
			
			entityManager.merge(empresaServiceManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}