package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoProducto;

@Stateless
public class TipoProductoBean implements ITipoProductoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
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
	
	public TipoProducto getById(Long id) {
		TipoProducto result = null;
		
		try {
			result = entityManager.find(TipoProducto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}