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
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

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
	
	public Collection<PuntoVenta> list(Long usuarioId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			Long estadoVisitaPuntoVentaDistribuidorPendienteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
			TypedQuery<PuntoVenta> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND ("
						+ " pv.ucre = :usuarioId"
						+ " OR EXISTS ("
							+ "	SELECT v.puntoVenta"
							+ " FROM VisitaPuntoVentaDistribuidor v"
							+ " WHERE v.puntoVenta = pv"
							+ " AND v.distribuidor.id = :usuarioId"
							+ " AND ("
								+ " v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorPendienteId"
								+ " OR v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId"
							+ ")"
						+ " )"
					+ " )"
					+ " ORDER BY pv.nombre ASC", 
					PuntoVenta.class
				);
			query.setParameter("usuarioId", usuarioId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorPendienteId", estadoVisitaPuntoVentaDistribuidorPendienteId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId", estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId);
			
			for (PuntoVenta puntoVenta : query.getResultList()) {
				result.add(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVenta> listMinimal() {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv.id, pv.nombre"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				
				result.add(puntoVentaMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVenta> listMinimalCreatedByUsuarioId(Long usuarioId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv.id, pv.nombre"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.ucre = :usuarioId"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			query.setParameter("usuarioId", usuarioId);
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				
				result.add(puntoVentaMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVenta> listMinimalCreatedORAssignedByUsuarioId(Long usuarioId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			Long estadoVisitaPuntoVentaDistribuidorPendienteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv.id, pv.nombre"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND ("
						+ "	pv.ucre = :usuarioId"
						+ " OR EXISTS ("
							+ "	SELECT v.puntoVenta"
							+ " FROM VisitaPuntoVentaDistribuidor v"
							+ " WHERE v.puntoVenta = pv"
							+ " AND v.distribuidor = pv.distribuidor"
							+ " AND ("
								+ " v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorPendienteId"
								+ " OR v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId"
							+ "	)"
						+ " )"
					+ " )"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			query.setParameter("usuarioId", usuarioId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorPendienteId", estadoVisitaPuntoVentaDistribuidorPendienteId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId", estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId);
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				
				result.add(puntoVentaMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<PuntoVenta>().list(entityManager, metadataConsulta, new PuntoVenta());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<PuntoVenta>().count(entityManager, metadataConsulta, new PuntoVenta());
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
	
	public Collection<PuntoVenta> listByDepartamento(Departamento departamento, Long usuarioId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			Long estadoVisitaPuntoVentaDistribuidorPendienteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
			TypedQuery<PuntoVenta> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.departamento.id = :departamentoId"
					+ " AND ("
						+ " pv.ucre = :usuarioId"
						+ " OR EXISTS ("
							+ "	SELECT v.puntoVenta"
							+ " FROM VisitaPuntoVentaDistribuidor v"
							+ " WHERE v.puntoVenta = pv"
							+ " AND v.distribuidor.id = :usuarioId"
							+ " AND ("
								+ " v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorPendienteId"
								+ " OR v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId"
							+ " )"
						+ " )"
					+ " )"
					+ " ORDER BY pv.nombre ASC", 
					PuntoVenta.class
				);
			query.setParameter("departamentoId", departamento.getId());
			query.setParameter("usuarioId", usuarioId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorPendienteId", estadoVisitaPuntoVentaDistribuidorPendienteId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId", estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId);
			
			for (PuntoVenta puntoVenta : query.getResultList()) {
				result.add(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVenta> listMinimalByDepartamento(Departamento departamento) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv.id, pv.nombre"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.departamento.id = :departamentoId"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			query.setParameter("departamentoId", departamento.getId());
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				
				result.add(puntoVentaMinimal);
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
	
	public Collection<PuntoVenta> listByBarrio(Barrio barrio, Long usuarioId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			Long estadoVisitaPuntoVentaDistribuidorPendienteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				new Long(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
			TypedQuery<PuntoVenta> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.barrio.id = :barrioId"
					+ " AND ("
						+ " pv.ucre = :usuarioId"
						+ " OR EXISTS ("
							+ "	SELECT v.puntoVenta"
							+ " FROM VisitaPuntoVentaDistribuidor v"
							+ " WHERE v.puntoVenta = pv"
							+ " AND v.distribuidor.id = :usuarioId"
							+ " AND ("
								+ " v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorPendienteId"
								+ " OR v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId"
							+ " )"
						+ " )"
					+ " )"
					+ " ORDER BY pv.nombre ASC", 
					PuntoVenta.class
				);
			query.setParameter("barrioId", barrio.getId());
			query.setParameter("usuarioId", usuarioId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorPendienteId", estadoVisitaPuntoVentaDistribuidorPendienteId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId", estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId);
			
			for (PuntoVenta puntoVenta : query.getResultList()) {
				result.add(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVenta> listMinimalByBarrio(Barrio barrio) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.barrio.id = :barrioId"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			query.setParameter("barrioId", barrio.getId());
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				
				result.add(puntoVentaMinimal);
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
			puntoVenta.setFcre(puntoVenta.getFact());
			puntoVenta.setUcre(puntoVenta.getUact());
			
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
			puntoVentaManaged.setFechaAsignacionDistribuidor(puntoVenta.getFechaAsignacionDistribuidor());
			puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(puntoVenta.getFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor());
			puntoVentaManaged.setFechaVisitaDistribuidor(puntoVenta.getFechaVisitaDistribuidor());
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
			
			if (puntoVenta.getEstadoPuntoVenta() != null) {
				puntoVentaManaged.setEstadoPuntoVenta(puntoVenta.getEstadoPuntoVenta());
			}
			
			if (puntoVenta.getEstadoVisitaPuntoVentaDistribuidor() != null) {
				puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(puntoVenta.getEstadoVisitaPuntoVentaDistribuidor());
			}
			
			if (puntoVenta.getDistribuidor() != null) {
				puntoVentaManaged.setDistribuidor(puntoVenta.getDistribuidor());
			}
			
			puntoVentaManaged.setFact(puntoVenta.getFact());
			puntoVentaManaged.setFechaBaja(puntoVenta.getFechaBaja());
			puntoVentaManaged.setTerm(puntoVenta.getTerm());
			puntoVentaManaged.setUact(puntoVenta.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}