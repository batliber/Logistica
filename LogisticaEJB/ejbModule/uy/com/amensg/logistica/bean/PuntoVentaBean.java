package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.PuntoVenta;

@Stateless
public class PuntoVentaBean implements IPuntoVentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	public Collection<PuntoVenta> list() {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<PuntoVenta> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " ORDER BY pv.nombre ASC", 
					PuntoVenta.class
				);
			
			for (PuntoVenta puntoVenta : query.getResultList()) {
				result.add(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<PuntoVenta> listByDepartamento(Departamento departamento) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<PuntoVenta> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.departamento.id = :departamentoId"
					+ " ORDER BY pv.nombre ASC", 
					PuntoVenta.class
				);
			query.setParameter("departamentoId", departamento.getId());
			
			for (PuntoVenta puntoVenta : query.getResultList()) {
				result.add(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVenta> listByBarrio(Barrio barrio) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<PuntoVenta> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.barrio.id = :barrioId"
					+ " ORDER BY pv.nombre ASC", 
					PuntoVenta.class
				);
			query.setParameter("barrioId", barrio.getId());
			
			for (PuntoVenta puntoVenta : query.getResultList()) {
				result.add(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public PuntoVenta getById(Long id) {
		PuntoVenta result = null;
		
		try {
			result = entityManager.find(PuntoVenta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(PuntoVenta puntoVenta) {
		try {
			entityManager.persist(puntoVenta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(PuntoVenta puntoVenta) {
		try {
			PuntoVenta managedPuntoVenta = entityManager.find(PuntoVenta.class, puntoVenta.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			managedPuntoVenta.setFechaBaja(date);
			
			managedPuntoVenta.setFact(puntoVenta.getFact());
			managedPuntoVenta.setTerm(puntoVenta.getTerm());
			managedPuntoVenta.setUact(puntoVenta.getUact());
			
			entityManager.merge(managedPuntoVenta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(PuntoVenta puntoVenta) {
		try {
			PuntoVenta puntoVentaManaged = entityManager.find(PuntoVenta.class, puntoVenta.getId());
			
			puntoVentaManaged.setContacto(puntoVenta.getContacto());
			puntoVentaManaged.setDireccion(puntoVenta.getDireccion());
			puntoVentaManaged.setDocumento(puntoVenta.getDocumento());
			puntoVentaManaged.setLatitud(puntoVenta.getLatitud());
			puntoVentaManaged.setLongitud(puntoVenta.getLongitud());
			puntoVentaManaged.setNombre(puntoVenta.getNombre());
			puntoVentaManaged.setPrecision(puntoVenta.getPrecision());
			puntoVentaManaged.setTelefono(puntoVenta.getTelefono());
			
			if (puntoVenta.getBarrio() != null) {
				puntoVentaManaged.setBarrio(puntoVenta.getBarrio());
			}
			
			if (puntoVenta.getDepartamento() != null) {
				puntoVentaManaged.setDepartamento(puntoVenta.getDepartamento());
			}
			
			puntoVentaManaged.setFact(puntoVenta.getFact());
			puntoVentaManaged.setFechaBaja(puntoVenta.getFechaBaja());
			puntoVentaManaged.setTerm(puntoVenta.getTerm());
			puntoVentaManaged.setUact(puntoVenta.getUact());
			
			entityManager.merge(puntoVentaManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}