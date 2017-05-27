package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;

@Stateless
public class SeguridadAuditoriaBean implements ISeguridadAuditoriaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
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
			entityManager.persist(seguridadAuditoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}