package uy.com.amensg.logistica.bean;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoExportacion;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ProcesoExportacionBean implements IProcesoExportacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<ProcesoExportacion>().list(entityManager, metadataConsulta, new ProcesoExportacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ProcesoExportacion>().count(entityManager, metadataConsulta, new ProcesoExportacion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProcesoExportacion save(ProcesoExportacion procesoExportacion) {
		ProcesoExportacion result = null;
		
		try {
			procesoExportacion.setFcre(procesoExportacion.getFact());
			procesoExportacion.setUcre(procesoExportacion.getUact());
			
			entityManager.persist(procesoExportacion);
			
			result = procesoExportacion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(ProcesoExportacion procesoExportacion) {
		try {
			ProcesoExportacion procesoExportacionManaged = entityManager.find(ProcesoExportacion.class, procesoExportacion.getId());
			
			procesoExportacionManaged.setFechaFin(procesoExportacion.getFechaFin());
			procesoExportacionManaged.setFechaInicio(procesoExportacion.getFechaInicio());
			procesoExportacionManaged.setNombreArchivo(procesoExportacion.getNombreArchivo());
			procesoExportacionManaged.setObservaciones(procesoExportacion.getObservaciones());
			procesoExportacionManaged.setUrlOrigen(procesoExportacion.getUrlOrigen());
			procesoExportacionManaged.setUsuario(procesoExportacion.getUsuario());
			
			procesoExportacionManaged.setFact(procesoExportacion.getFact());
			procesoExportacionManaged.setTerm(procesoExportacion.getTerm());
			procesoExportacionManaged.setUact(procesoExportacion.getUact());
			
			entityManager.merge(procesoExportacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}