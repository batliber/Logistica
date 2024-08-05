package uy.com.amensg.logistica.bean;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Configuracion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ConfiguracionPHBean implements IConfiguracionPHBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitPHXA")
	private EntityManager entityManagerPH;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<Configuracion>().list(entityManagerPH, metadataConsulta, new Configuracion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Configuracion>().count(entityManagerPH, metadataConsulta, new Configuracion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Configuracion getById(Long id) {
		Configuracion result = null;
		
		try {
			result = entityManagerPH.find(Configuracion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String getProperty(String clave) {
		String result = null;
		
		try {
			TypedQuery<Configuracion> query = 
				entityManagerPH.createQuery(
					"SELECT c"
					+ " FROM Configuracion c"
					+ " WHERE c.clave = :clave",
					Configuracion.class
				);
			query.setParameter("clave", clave);
			
			List<Configuracion> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0).getValor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Configuracion getByClave(String clave) {
		Configuracion result = null;
		
		try {
			TypedQuery<Configuracion> query = 
				entityManagerPH.createQuery(
					"SELECT c"
					+ " FROM Configuracion c"
					+ " WHERE c.clave = :clave",
					Configuracion.class
				);
			query.setParameter("clave", clave);
			
			List<Configuracion> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Configuracion save(Configuracion configuracion) {
		Configuracion result = null;
		
		try {
			configuracion.setFcre(configuracion.getFact());
			configuracion.setUcre(configuracion.getUact());
			
			entityManagerPH.persist(configuracion);
			
			result = configuracion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(Configuracion configuracion) {
		try {
			Configuracion managedConfiguracion = entityManagerPH.find(Configuracion.class, configuracion.getId());
			
			entityManagerPH.remove(managedConfiguracion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Configuracion configuracion) {
		try {
			Configuracion configuracionManaged = entityManagerPH.find(Configuracion.class, configuracion.getId());
			
			configuracionManaged.setClave(configuracion.getClave());
			configuracionManaged.setValor(configuracion.getValor());
			
			configuracionManaged.setFact(configuracion.getFact());
			configuracionManaged.setTerm(configuracion.getTerm());
			configuracionManaged.setUact(configuracion.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}