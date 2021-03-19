package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaPuntoVentaCota;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class PuntoVentaBean implements IPuntoVentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	@EJB
	private IEstadoVisitaPuntoVentaDistribuidorBean iEstadoVisitaPuntoVentaDistribuidorBean;
	
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
				Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
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
	
	public Collection<PuntoVenta> listMinimalCreatedORAssignedByUsuarioId(
		Long departamentoId,
		Long barrioId,
		Long puntoVentaId,
		Long estadoVisitaPuntoVentaDistribuidorId,
		Long usuarioId
	) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			Long estadoVisitaPuntoVentaDistribuidorPendienteId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente")
				);
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente")
				);
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaAutorId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaAutor")
				);
			
			String queryString = 
				"SELECT pv.id, pv.nombre,"
				+ " pv.departamento.id, pv.departamento.nombre,"
				+ " pv.barrio.id, pv.barrio.nombre,"
				+ " evpvd.id, evpvd.nombre,"
				+ " pv.latitud, pv.longitud, pv.precision"
				+ " FROM PuntoVenta pv"
				+ " LEFT JOIN pv.estadoVisitaPuntoVentaDistribuidor evpvd"
				+ " WHERE pv.fechaBaja IS NULL"
				+ " AND ("
					+ "	pv.ucre = :usuarioId"
					+ " OR EXISTS ("
						+ "	SELECT v.puntoVenta"
						+ " FROM VisitaPuntoVentaDistribuidor v"
						+ " WHERE v.puntoVenta = pv"
						+ " AND v.distribuidor.id = :usuarioId"
						+ " AND ("
							+ " v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorPendienteId"
							+ " OR v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId"
							+ " OR v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorVisitaAutorId"
						+ "	)"
					+ " )"
				+ " )";
			
			if (departamentoId != null) {
				queryString +=
					" AND pv.departamento.id = :departamentoId";
			}
			
			if (barrioId != null) {
				queryString +=
					" AND pv.barrio.id = :barrioId";
			}
			
			if (puntoVentaId != null) {
				queryString +=
					" AND pv.id = :puntoVentaId";
			}
			
			if (estadoVisitaPuntoVentaDistribuidorId != null) {
				queryString +=
					" AND pv.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaPuntoVentaDistribuidorId";
			}
			
			queryString +=
				" ORDER BY pv.nombre ASC";
			
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					queryString, 
					Object[].class
				);
			query.setParameter("usuarioId", usuarioId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorPendienteId", estadoVisitaPuntoVentaDistribuidorPendienteId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId", estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId);
			query.setParameter("estadoVisitaPuntoVentaDistribuidorVisitaAutorId", estadoVisitaPuntoVentaDistribuidorVisitaAutorId);
			
			if (departamentoId != null) {
				query.setParameter("departamentoId", departamentoId);
			}
			
			if (barrioId != null) {
				query.setParameter("barrioId", barrioId);
			}
			
			if (puntoVentaId != null) {
				query.setParameter("puntoVentaId", puntoVentaId);
			}
			if (estadoVisitaPuntoVentaDistribuidorId != null) {
				query.setParameter("estadoVisitaPuntoVentaDistribuidorId", estadoVisitaPuntoVentaDistribuidorId);
			}
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				puntoVentaMinimal.setLatitud((Double)puntoVenta[8]);
				puntoVentaMinimal.setLongitud((Double)puntoVenta[9]);
				puntoVentaMinimal.setPrecision((Double)puntoVenta[10]);

				if (puntoVenta[2] != null) {
					Departamento departamento = new Departamento();
					departamento.setId((Long) puntoVenta[2]);
					departamento.setNombre((String)puntoVenta[3]);
					
					puntoVentaMinimal.setDepartamento(departamento);
				}
				
				if (puntoVenta[3] != null) {
					Barrio barrio = new Barrio();
					barrio.setId((Long)puntoVenta[4]);
					barrio.setNombre((String)puntoVenta[5]);
					
					puntoVentaMinimal.setBarrio(barrio);
				}
				
				if (puntoVenta[6] != null) {
					EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = new EstadoVisitaPuntoVentaDistribuidor();
					estadoVisitaPuntoVentaDistribuidor.setId((Long)puntoVenta[6]);
					estadoVisitaPuntoVentaDistribuidor.setNombre((String)puntoVenta[7]);
					
					puntoVentaMinimal.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
				}
				
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
				Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
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
	
	public Collection<PuntoVenta> listMinimalByDepartamentoId(Long departamentoId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv.id, pv.nombre, pv.latitud, pv.longitud"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.departamento.id = :departamentoId"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			query.setParameter("departamentoId", departamentoId);
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				puntoVentaMinimal.setLatitud((Double)puntoVenta[2]);
				puntoVentaMinimal.setLongitud((Double)puntoVenta[3]);
				
				result.add(puntoVentaMinimal);
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
				Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"));
			Long estadoVisitaPuntoVentaDistribuidorVisitaPermanenteId = 
				Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"));
			
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
	
	public Collection<PuntoVenta> listMinimalByBarrioId(Long barrioId) {
		Collection<PuntoVenta> result = new LinkedList<PuntoVenta>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT pv.id, pv.nombre, pv.latitud, pv.longitud"
					+ " FROM PuntoVenta pv"
					+ " WHERE pv.fechaBaja IS NULL"
					+ " AND pv.barrio.id = :barrioId"
					+ " ORDER BY pv.nombre ASC", 
					Object[].class
				);
			query.setParameter("barrioId", barrioId);
			
			for (Object[] puntoVenta : query.getResultList()) {
				PuntoVenta puntoVentaMinimal = new PuntoVenta();
				puntoVentaMinimal.setId((Long)puntoVenta[0]);
				puntoVentaMinimal.setNombre((String)puntoVenta[1]);
				puntoVentaMinimal.setLatitud((Double)puntoVenta[2]);
				puntoVentaMinimal.setLongitud((Double)puntoVenta[3]);
				
				result.add(puntoVentaMinimal);
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
					"SELECT pv.id, pv.nombre"
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

	public PuntoVenta save(PuntoVenta puntoVenta) {
		PuntoVenta result = null;
		
		try {
			puntoVenta.setFcre(puntoVenta.getFact());
			puntoVenta.setUcre(puntoVenta.getUact());
			
			entityManager.persist(puntoVenta);
			
			result = puntoVenta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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
			puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(
				puntoVenta.getFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor()
			);
			puntoVentaManaged.setFechaVencimientoChipMasViejo(puntoVenta.getFechaVencimientoChipMasViejo());
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
				puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(
					puntoVenta.getEstadoVisitaPuntoVentaDistribuidor()
				);
			}
			
			if (puntoVenta.getDistribuidor() != null) {
				puntoVentaManaged.setDistribuidor(puntoVenta.getDistribuidor());
			}
			
			if (puntoVenta.getRecargaPuntoVentaCota() != null) {
				RecargaPuntoVentaCota recargaPuntoVentaCota = puntoVenta.getRecargaPuntoVentaCota();
				if (puntoVentaManaged.getRecargaPuntoVentaCota() != null) {
					RecargaPuntoVentaCota recargaPuntoVentaCotaManaged = puntoVentaManaged.getRecargaPuntoVentaCota();
					recargaPuntoVentaCotaManaged.setTopeAlarmaSaldo(
						recargaPuntoVentaCota.getTopeAlarmaSaldo()
					);
					recargaPuntoVentaCotaManaged.setTopePorcentajeDescuento(
						recargaPuntoVentaCota.getTopePorcentajeDescuento()
					);
					recargaPuntoVentaCotaManaged.setTopePorMid(
						recargaPuntoVentaCota.getTopePorMid()
					);
					recargaPuntoVentaCotaManaged.setTopeTotalPorDia(
						recargaPuntoVentaCota.getTopeTotalPorDia()
					);
					recargaPuntoVentaCotaManaged.setTopeTotalPorMes(
						recargaPuntoVentaCota.getTopeTotalPorMes()
					);
					
					recargaPuntoVentaCotaManaged.setFact(puntoVenta.getFact());
					recargaPuntoVentaCotaManaged.setTerm(puntoVenta.getTerm());
					recargaPuntoVentaCotaManaged.setUact(puntoVenta.getUact());
				} else {
					recargaPuntoVentaCota.setPuntoVenta(puntoVentaManaged);
					
					recargaPuntoVentaCota.setFact(puntoVenta.getFact());
					recargaPuntoVentaCota.setFact(puntoVenta.getFcre());
					recargaPuntoVentaCota.setTerm(puntoVenta.getTerm());
					recargaPuntoVentaCota.setUact(puntoVenta.getUcre());
					recargaPuntoVentaCota.setUact(puntoVenta.getUact());
					
					puntoVentaManaged.setRecargaPuntoVentaCota(recargaPuntoVentaCota);
				}
			} else {
				puntoVentaManaged.setRecargaPuntoVentaCota(null);
			}
			
			puntoVentaManaged.setFact(puntoVenta.getFact());
			puntoVentaManaged.setFechaBaja(puntoVenta.getFechaBaja());
			puntoVentaManaged.setTerm(puntoVenta.getTerm());
			puntoVentaManaged.setUact(puntoVenta.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearVisitas(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estadoVisitaPuntoVentaDistribuidor.Pendiente"))
				);
			
			MetadataConsultaResultado metadataConsultaResultado = list(metadataConsulta);
			
			Long i = metadataConsulta.getTamanoSubconjunto() != null ?
				metadataConsulta.getTamanoSubconjunto() : Long.MAX_VALUE;
			
			for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
				PuntoVenta puntoVenta = (PuntoVenta) object;
				
				VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = 
					new VisitaPuntoVentaDistribuidor();
				visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
				visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(
					estadoVisitaPuntoVentaDistribuidor
				);
				visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
				visitaPuntoVentaDistribuidor.setObservaciones(observaciones);
				visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVenta);
				
				visitaPuntoVentaDistribuidor.setFact(hoy);
				visitaPuntoVentaDistribuidor.setFcre(hoy);
				visitaPuntoVentaDistribuidor.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
				visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
				
				entityManager.persist(visitaPuntoVentaDistribuidor);
					
				puntoVenta.setDistribuidor(distribuidor);
				puntoVenta.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
				puntoVenta.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
				puntoVenta.setFechaAsignacionDistribuidor(hoy);
				
				puntoVenta.setFact(hoy);
				puntoVenta.setUact(loggedUsuarioId);
				puntoVenta.setTerm(Long.valueOf(1));
					
				i--;
					
				if (i == 0) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearVisitasPermanentes(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(
						Configuration.getInstance().getProperty(
							"estadoVisitaPuntoVentaDistribuidor.VisitaPermanente"
						)
					)
				);
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaAutorSuprimida = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(
						Configuration.getInstance().getProperty(
							"estadoVisitaPuntoVentaDistribuidor.VisitaAutorSuprimida"
						)
					)
				);
			
			Long estadoVisitaAutorId = 
				Long.parseLong(
					Configuration.getInstance().getProperty(
						"estadoVisitaPuntoVentaDistribuidor.VisitaAutor"
					)
				);
			
			MetadataConsultaResultado metadataConsultaResultado = list(metadataConsulta);
			
			Long i = metadataConsulta.getTamanoSubconjunto() != null ? 
				metadataConsulta.getTamanoSubconjunto() : Long.MAX_VALUE;
			
			TypedQuery<VisitaPuntoVentaDistribuidor> query = 
				entityManager.createQuery(
					"SELECT v"
					+ " FROM VisitaPuntoVentaDistribuidor v"
					+ " WHERE v.estadoVisitaPuntoVentaDistribuidor.id = :estadoVisitaAutorId"
					+ " AND v.puntoVenta.id = :puntoVentaId",
					VisitaPuntoVentaDistribuidor.class
				);
			query.setParameter("estadoVisitaAutorId", estadoVisitaAutorId);
			
			for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
				PuntoVenta puntoVenta = (PuntoVenta) object;
				
				// Suprimo las visitas de autor existentes.
				query.setParameter("puntoVentaId", puntoVenta.getId());
				for (VisitaPuntoVentaDistribuidor visitaAutor : query.getResultList()) {
					visitaAutor.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaAutorSuprimida);
					
					visitaAutor.setUact(loggedUsuarioId);
					visitaAutor.setFact(hoy);
					visitaAutor.setTerm(Long.valueOf(1));
				}
				
				// Creo las visitas permanente.
				VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = 
					new VisitaPuntoVentaDistribuidor();
				visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
				visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(
					estadoVisitaPuntoVentaDistribuidor
				);
				visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
				visitaPuntoVentaDistribuidor.setObservaciones(observaciones);
				visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVenta);
				
				visitaPuntoVentaDistribuidor.setFact(hoy);
				visitaPuntoVentaDistribuidor.setFcre(hoy);
				visitaPuntoVentaDistribuidor.setTerm(Long.valueOf(1));
				visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
				visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
				
				entityManager.persist(visitaPuntoVentaDistribuidor);
				
				// Actualizo los campos resumen del Punto de Venta.
				puntoVenta.setDistribuidor(distribuidor);
				puntoVenta.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
				puntoVenta.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
				puntoVenta.setFechaAsignacionDistribuidor(hoy);
				
				puntoVenta.setFact(hoy);
				puntoVenta.setUact(loggedUsuarioId);
				puntoVenta.setTerm(Long.valueOf(1));
				
				i--;
				
				if (i == 0) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearVisitaAutor(
		Usuario distribuidor, String observaciones, PuntoVenta puntoVenta, Long loggedUsuarioId
	) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor = 
				iEstadoVisitaPuntoVentaDistribuidorBean.getById(
					Long.parseLong(
						Configuration.getInstance().getProperty(
							"estadoVisitaPuntoVentaDistribuidor.VisitaAutor"
						)
					)
				);
			
			PuntoVenta puntoVentaManaged = this.getById(puntoVenta.getId());
			
			VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor = 
				new VisitaPuntoVentaDistribuidor();
			visitaPuntoVentaDistribuidor.setDistribuidor(distribuidor);
			visitaPuntoVentaDistribuidor.setEstadoVisitaPuntoVentaDistribuidor(
				estadoVisitaPuntoVentaDistribuidor
			);
			visitaPuntoVentaDistribuidor.setFechaAsignacion(hoy);
			visitaPuntoVentaDistribuidor.setObservaciones(observaciones);
			visitaPuntoVentaDistribuidor.setPuntoVenta(puntoVentaManaged);
			
			visitaPuntoVentaDistribuidor.setFact(hoy);
			visitaPuntoVentaDistribuidor.setFcre(hoy);
			visitaPuntoVentaDistribuidor.setTerm(Long.valueOf(1));
			visitaPuntoVentaDistribuidor.setUact(loggedUsuarioId);
			visitaPuntoVentaDistribuidor.setUcre(loggedUsuarioId);
			
			entityManager.persist(visitaPuntoVentaDistribuidor);
			
			puntoVentaManaged.setDistribuidor(distribuidor);
			puntoVentaManaged.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
			puntoVentaManaged.setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(hoy);
			puntoVentaManaged.setFechaAsignacionDistribuidor(hoy);
			
			puntoVentaManaged.setFact(hoy);
			puntoVentaManaged.setUact(loggedUsuarioId);
			puntoVentaManaged.setTerm(Long.valueOf(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}