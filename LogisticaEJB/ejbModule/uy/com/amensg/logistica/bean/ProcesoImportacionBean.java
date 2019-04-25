package uy.com.amensg.logistica.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ProcesoImportacionBean implements IProcesoImportacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<ProcesoImportacion>().list(entityManager, metadataConsulta, new ProcesoImportacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ProcesoImportacion>().count(entityManager, metadataConsulta, new ProcesoImportacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ProcesoImportacion getByNombreArchivo(String nombreArchivo) {
		ProcesoImportacion result = null;
		
		try {
			TypedQuery<ProcesoImportacion> query = 
				entityManager.createQuery(
					"SELECT p"
					+ " FROM ProcesoImportacion p"
					+ " WHERE p.nombreArchivo = :nombreArchivo",
					ProcesoImportacion.class
				);
			query.setParameter("nombreArchivo", nombreArchivo);
			
			List<ProcesoImportacion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ProcesoImportacion save(ProcesoImportacion procesoImportacion) {
		ProcesoImportacion result = null;
		
		try {
			procesoImportacion.setFcre(procesoImportacion.getFact());
			procesoImportacion.setUcre(procesoImportacion.getUact());
			
			entityManager.persist(procesoImportacion);
			
			result = procesoImportacion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void update(ProcesoImportacion procesoImportacion) {
		try {
			ProcesoImportacion procesoImportacionManaged = entityManager.find(ProcesoImportacion.class, procesoImportacion.getId());
			
			procesoImportacionManaged.setEstadoProcesoImportacion(procesoImportacion.getEstadoProcesoImportacion());
			procesoImportacionManaged.setFechaFin(procesoImportacion.getFechaFin());
			procesoImportacionManaged.setFechaInicio(procesoImportacion.getFechaInicio());
			procesoImportacionManaged.setNombreArchivo(procesoImportacion.getNombreArchivo());
			procesoImportacionManaged.setObservaciones(procesoImportacion.getObservaciones());
			procesoImportacionManaged.setTipoProcesoImportacion(procesoImportacion.getTipoProcesoImportacion());
			procesoImportacionManaged.setUsuario(procesoImportacion.getUsuario());
			
			procesoImportacionManaged.setFact(procesoImportacion.getFact());
			procesoImportacionManaged.setTerm(procesoImportacion.getTerm());
			procesoImportacionManaged.setUact(procesoImportacion.getUact());
			
			entityManager.merge(procesoImportacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}