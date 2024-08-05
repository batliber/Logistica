package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class TipoProductoBean implements ITipoProductoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<TipoProducto> list() {
		Collection<TipoProducto> result = new LinkedList<TipoProducto>();
		
		try {
			TypedQuery<TipoProducto> query = entityManager.createQuery(
				"SELECT t FROM TipoProducto t",
				TipoProducto.class
			);
			
			for (TipoProducto tipoProducto : query.getResultList()) {
				result.add(tipoProducto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<TipoProducto>().list(entityManager, metadataConsulta, new TipoProducto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<TipoProducto>().count(entityManager, metadataConsulta, new TipoProducto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoProducto getById(Long id) {
		TipoProducto result = null;
		
		try {
			result = entityManager.find(TipoProducto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoProducto save(TipoProducto tipoProducto) {
		TipoProducto result = null;
		
		try {
			tipoProducto.setFcre(tipoProducto.getFact());
			tipoProducto.setUcre(tipoProducto.getUact());
			
			entityManager.persist(tipoProducto);
			
			result = tipoProducto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(TipoProducto tipoProducto) {
		try {
			TipoProducto managedTipoProducto = entityManager.find(TipoProducto.class, tipoProducto.getId());
			
//			Date date = GregorianCalendar.getInstance().getTime();
			
			managedTipoProducto.setFact(tipoProducto.getFact());
			managedTipoProducto.setTerm(tipoProducto.getTerm());
			managedTipoProducto.setUact(tipoProducto.getUact());
			
			entityManager.merge(managedTipoProducto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(TipoProducto tipoProducto) {
		try {
			TipoProducto tipoProductoManaged = entityManager.find(TipoProducto.class, tipoProducto.getId());
			
			tipoProductoManaged.setDescripcion(tipoProducto.getDescripcion());
			
			tipoProductoManaged.setFact(tipoProducto.getFact());
			tipoProductoManaged.setTerm(tipoProducto.getTerm());
			tipoProductoManaged.setUact(tipoProducto.getUact());
			
			entityManager.merge(tipoProductoManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}