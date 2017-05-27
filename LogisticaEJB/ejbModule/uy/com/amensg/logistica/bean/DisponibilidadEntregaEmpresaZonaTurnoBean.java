package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Zona;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class DisponibilidadEntregaEmpresaZonaTurnoBean implements IDisponibilidadEntregaEmpresaZonaTurnoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresa(Empresa empresa) {
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
		
		try {
			TypedQuery<DisponibilidadEntregaEmpresaZonaTurno> query = 
				entityManager.createQuery(
					"SELECT deezt"
					+ " FROM DisponibilidadEntregaEmpresaZonaTurno deezt"
					+ " WHERE deezt.empresa = :empresa", 
					DisponibilidadEntregaEmpresaZonaTurno.class
				);
			query.setParameter("empresa", empresa);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : query.getResultList()) {
				result.add(disponibilidadEntregaEmpresaZonaTurno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZona(Empresa empresa, Zona zona) {
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
		
		try {
			TypedQuery<DisponibilidadEntregaEmpresaZonaTurno> query = 
				entityManager.createQuery(
					"SELECT deezt"
					+ " FROM DisponibilidadEntregaEmpresaZonaTurno deezt"
					+ " WHERE deezt.empresa = :empresa"
					+ " AND deezt.zona = :zona", 
					DisponibilidadEntregaEmpresaZonaTurno.class
				);
			query.setParameter("empresa", empresa);
			query.setParameter("zona", zona);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : query.getResultList()) {
				result.add(disponibilidadEntregaEmpresaZonaTurno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZonaTurno(Empresa empresa, Zona zona, Turno turno) {
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
		
		try {
			TypedQuery<DisponibilidadEntregaEmpresaZonaTurno> query = 
				entityManager.createQuery(
					"SELECT deezt"
					+ " FROM DisponibilidadEntregaEmpresaZonaTurno deezt"
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

	public void generarDisponibilidadParaEmpresa(Empresa empresa) {
		try {
			Empresa empresaDefault = iEmpresaBean.getById(new Long(Configuration.getInstance().getProperty("empresa.ELARED")));
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : this.listByEmpresa(empresaDefault)) {
				DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurnoNueva = new DisponibilidadEntregaEmpresaZonaTurno();
				disponibilidadEntregaEmpresaZonaTurnoNueva.setCantidad(disponibilidadEntregaEmpresaZonaTurno.getCantidad());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setDia(disponibilidadEntregaEmpresaZonaTurno.getDia());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setEmpresa(empresa);
				disponibilidadEntregaEmpresaZonaTurnoNueva.setTurno(disponibilidadEntregaEmpresaZonaTurno.getTurno());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setZona(disponibilidadEntregaEmpresaZonaTurno.getZona());
				
				disponibilidadEntregaEmpresaZonaTurnoNueva.setFact(empresa.getFact());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setTerm(new Long(1));
				disponibilidadEntregaEmpresaZonaTurnoNueva.setUact(empresa.getUact());
				
				entityManager.persist(disponibilidadEntregaEmpresaZonaTurnoNueva);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}