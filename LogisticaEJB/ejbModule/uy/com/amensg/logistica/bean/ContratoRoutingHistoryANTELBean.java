package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ContratoRoutingHistoryANTEL;

@Stateless
public class ContratoRoutingHistoryANTELBean implements IContratoRoutingHistoryANTELBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	/**
	 * Lista las asignaciones existentes para el contrato con ID contratoId.
	 * 
	 * @param contratoId ID del Contrato a consultar.
	 * @return La lista de asignaciones del Contrato especificado.
	 */
	public Collection<ContratoRoutingHistoryANTEL> listByContratoId(Long contratoId) {
		Collection<ContratoRoutingHistoryANTEL> result = new LinkedList<ContratoRoutingHistoryANTEL>();
		
		try {
			TypedQuery<ContratoRoutingHistoryANTEL> query = 
				entityManager.createQuery(
					"SELECT crh"
					+ " FROM ContratoRoutingHistoryANTEL crh"
					+ " WHERE crh.contrato.id = :contratoId"
					+ " ORDER BY crh.id DESC",
					ContratoRoutingHistoryANTEL.class
				);
			query.setParameter("contratoId", contratoId);
			
			for (ContratoRoutingHistoryANTEL contratoRoutingHistoryANTEL : query.getResultList()) {
				contratoRoutingHistoryANTEL.setUsuarioAct(iUsuarioBean.getById(contratoRoutingHistoryANTEL.getUact(), false));
				
				result.add(contratoRoutingHistoryANTEL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Lista las asignaciones existentes para el contrato con número de trámite numroTramite.
	 * 
	 * @param numeroTramite Número de trámite del Contrato a consultar.
	 * @return La lista de asignaciones del Contrato especificado.
	 */
	public Collection<ContratoRoutingHistoryANTEL> listByNumeroTramite(Long numeroTramite) {
		Collection<ContratoRoutingHistoryANTEL> result = new LinkedList<ContratoRoutingHistoryANTEL>();
		
		try {
			TypedQuery<ContratoRoutingHistoryANTEL> query = 
				entityManager.createQuery(
					"SELECT crh"
					+ " FROM ContratoRoutingHistoryANTEL crh"
					+ " WHERE crh.contrato.numeroTramite = :numeroTramite"
					+ " ORDER BY crh.id DESC",
					ContratoRoutingHistoryANTEL.class
				);
			query.setParameter("numeroTramite", numeroTramite);
			
			for (ContratoRoutingHistoryANTEL contratoRoutingHistoryANTEL : query.getResultList()) {
				contratoRoutingHistoryANTEL.setUsuarioAct(iUsuarioBean.getById(contratoRoutingHistoryANTEL.getUact(), false));
				
				result.add(contratoRoutingHistoryANTEL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}