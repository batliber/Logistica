package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.ProcesoExportacion;

@Stateless
public class ProcesoExportacionBean implements IProcesoExportacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
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