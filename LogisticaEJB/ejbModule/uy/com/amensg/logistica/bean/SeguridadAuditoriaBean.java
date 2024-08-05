package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class SeguridadAuditoriaBean implements ISeguridadAuditoriaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<SeguridadAuditoria>().list(entityManager, metadataConsulta, new SeguridadAuditoria());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<SeguridadAuditoria>().count(entityManager, metadataConsulta, new SeguridadAuditoria());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<SeguridadAuditoria> list(Date fechaDesde, Date fechaHasta) {
		Collection<SeguridadAuditoria> result = new LinkedList<SeguridadAuditoria>();
		
		try {
			TypedQuery<SeguridadAuditoria> query = 
				entityManager.createQuery(
					"SELECT sa"
					+ " FROM SeguridadAuditoria sa"
					+ " WHERE sa.fecha >= :fechaDesde AND sa.fecha <= :fechaHasta"
					+ " ORDER BY sa.id DESC", 
					SeguridadAuditoria.class
				);
			query.setParameter("fechaDesde", fechaDesde);
			query.setParameter("fechaHasta", fechaHasta);
			
			for (SeguridadAuditoria seguridadAuditoria : query.getResultList()) {
				result.add(seguridadAuditoria);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(SeguridadAuditoria seguridadAuditoria) {
		try {
			seguridadAuditoria.setFcre(seguridadAuditoria.getFact());
			seguridadAuditoria.setUcre(seguridadAuditoria.getUact());
			
			entityManager.persist(seguridadAuditoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}