package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class MarcaBean implements IMarcaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<Marca> list() {
		Collection<Marca> result = new LinkedList<Marca>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT m"
				+ " FROM Marca m"
				+ " ORDER BY m.nombre"
			);
			
			for (Object object : query.getResultList()) {
				result.add((Marca) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<Marca>().list(entityManager, metadataConsulta, new Marca());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Marca>().count(entityManager, metadataConsulta, new Marca());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Marca getById(Long id) {
		Marca result = null;
		
		try {
			result = entityManager.find(Marca.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Marca save(Marca marca) {
		Marca result = null;
		
		try {
			marca.setFcre(marca.getFact());
			marca.setUcre(marca.getUact());
			
			entityManager.persist(marca);
			
			result = marca;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(Marca marca) {
		try {
			Marca managedMarca = entityManager.find(Marca.class, marca.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			managedMarca.setFechaBaja(date);
			
			managedMarca.setFact(marca.getFact());
			managedMarca.setTerm(marca.getTerm());
			managedMarca.setUact(marca.getUact());
			
			entityManager.merge(managedMarca);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Marca marca) {
		try {
			Marca marcaManaged = entityManager.find(Marca.class, marca.getId());
			
			marcaManaged.setNombre(marca.getNombre());
			
			marcaManaged.setFact(marca.getFact());
			marcaManaged.setFechaBaja(marca.getFechaBaja());
			marcaManaged.setTerm(marca.getTerm());
			marcaManaged.setUact(marca.getUact());
			
			entityManager.merge(marcaManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}