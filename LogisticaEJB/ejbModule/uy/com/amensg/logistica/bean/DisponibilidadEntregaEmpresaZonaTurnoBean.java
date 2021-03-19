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
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = 
			new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
		
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
			Empresa empresaDefault = 
				iEmpresaBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("empresa.ELARED")),
					false
				);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : this.listByEmpresa(empresaDefault)) {
				DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurnoNueva = new DisponibilidadEntregaEmpresaZonaTurno();
				disponibilidadEntregaEmpresaZonaTurnoNueva.setCantidad(disponibilidadEntregaEmpresaZonaTurno.getCantidad());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setDia(disponibilidadEntregaEmpresaZonaTurno.getDia());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setEmpresa(empresa);
				disponibilidadEntregaEmpresaZonaTurnoNueva.setTurno(disponibilidadEntregaEmpresaZonaTurno.getTurno());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setZona(disponibilidadEntregaEmpresaZonaTurno.getZona());
				
				disponibilidadEntregaEmpresaZonaTurnoNueva.setFcre(empresa.getFact());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setFact(empresa.getFact());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setTerm(Long.valueOf(1));
				disponibilidadEntregaEmpresaZonaTurnoNueva.setUact(empresa.getUact());
				disponibilidadEntregaEmpresaZonaTurnoNueva.setUcre(empresa.getUcre());
				
				entityManager.persist(disponibilidadEntregaEmpresaZonaTurnoNueva);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateDisponibilidadByZona(
		Zona zona,
		Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos
	) {
		try {
			TypedQuery<DisponibilidadEntregaEmpresaZonaTurno> query = 
				entityManager.createQuery(
					"SELECT d"
					+ " FROM DisponibilidadEntregaEmpresaZonaTurno d"
					+ " WHERE d.zona = :zona", 
					DisponibilidadEntregaEmpresaZonaTurno.class
				);
			query.setParameter("zona", zona);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurnoManaged 
				: query.getResultList()) {
				entityManager.remove(disponibilidadEntregaEmpresaZonaTurnoManaged);
			}
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno 
				: disponibilidadEntregaEmpresaZonaTurnos) {
				for (Empresa empresa : iEmpresaBean.list()) {
					DisponibilidadEntregaEmpresaZonaTurno toPersist = new DisponibilidadEntregaEmpresaZonaTurno();
					toPersist.setCantidad(disponibilidadEntregaEmpresaZonaTurno.getCantidad());
					toPersist.setDia(disponibilidadEntregaEmpresaZonaTurno.getDia());
					toPersist.setEmpresa(empresa);
					toPersist.setTurno(disponibilidadEntregaEmpresaZonaTurno.getTurno());
					toPersist.setZona(disponibilidadEntregaEmpresaZonaTurno.getZona());
					
					toPersist.setFact(disponibilidadEntregaEmpresaZonaTurno.getFact());
					toPersist.setFcre(disponibilidadEntregaEmpresaZonaTurno.getFact());
					toPersist.setTerm(disponibilidadEntregaEmpresaZonaTurno.getTerm());
					toPersist.setUact(disponibilidadEntregaEmpresaZonaTurno.getUact());
					toPersist.setUcre(disponibilidadEntregaEmpresaZonaTurno.getUact());
					
					entityManager.persist(toPersist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateDisponibilidadByEmpresaZona(
		Empresa empresa,
		Zona zona,
		Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos
	) {
		try {
			TypedQuery<DisponibilidadEntregaEmpresaZonaTurno> query = 
				entityManager.createQuery(
					"SELECT d"
					+ " FROM DisponibilidadEntregaEmpresaZonaTurno d"
					+ " WHERE d.empresa = :empresa"
					+ " AND d.zona = :zona", 
					DisponibilidadEntregaEmpresaZonaTurno.class
				);
			query.setParameter("empresa", empresa);
			query.setParameter("zona", zona);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurnoManaged 
				: query.getResultList()) {
				entityManager.remove(disponibilidadEntregaEmpresaZonaTurnoManaged);
			}
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno 
				: disponibilidadEntregaEmpresaZonaTurnos) {
				disponibilidadEntregaEmpresaZonaTurno.setUcre(disponibilidadEntregaEmpresaZonaTurno.getUact());
				disponibilidadEntregaEmpresaZonaTurno.setFcre(disponibilidadEntregaEmpresaZonaTurno.getFact());
				
				entityManager.persist(disponibilidadEntregaEmpresaZonaTurno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}