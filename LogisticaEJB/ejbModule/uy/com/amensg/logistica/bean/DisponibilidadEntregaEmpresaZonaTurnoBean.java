package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Zona;

@Stateless
public class DisponibilidadEntregaEmpresaZonaTurnoBean implements IDisponibilidadEntregaEmpresaZonaTurnoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZonaTurno(Empresa empresa, Zona zona, Turno turno) {
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
		
		try {
			TypedQuery<DisponibilidadEntregaEmpresaZonaTurno> query = 
				entityManager.createQuery(
					"SELECT deezt FROM DisponibilidadEntregaEmpresaZonaTurno deezt"
					+ " WHERE deezt.empresa = :empresa"
					+ " AND deezt.zona = :zona"
					+ " AND deezt.turno = :turno", 
					DisponibilidadEntregaEmpresaZonaTurno.class
				);
			query.setParameter("empresa", empresa);
			query.setParameter("zona", zona);
			query.setParameter("turno", turno);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : query.getResultList()) {
				result.add(disponibilidadEntregaEmpresaZonaTurno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}