package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;

@Stateless
public class EmpresaBean implements IEmpresaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean;
	
	public Collection<Empresa> list() {
		Collection<Empresa> result = new LinkedList<Empresa>();
		
		try {
			TypedQuery<Empresa> query = entityManager.createQuery(
				"SELECT e"
				+ " FROM Empresa e"
				+ " ORDER BY e.nombre", 
				Empresa.class
			);
			
			for (Empresa empresa : query.getResultList()) {
				result.add(empresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Empresa getById(Long id) {
		Empresa result = null;
		
		try {
			result = entityManager.find(Empresa.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<FormaPago> listFormasPagoById(Long id) {
		Collection<FormaPago> result = new LinkedList<FormaPago>();
		
		try {
			Empresa empresa = this.getById(id);
			
			for (FormaPago formaPago : empresa.getFormaPagos()) {
				result.add(formaPago);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Empresa empresa) {
		try {
			entityManager.persist(empresa);
			
			iDisponibilidadEntregaEmpresaZonaTurnoBean.generarDisponibilidadParaEmpresa(empresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Empresa empresa) {
		try {
			Empresa managedEmpresa = entityManager.find(Empresa.class, empresa.getId());
			
//			managedEmpresa.setFechaBaja(date);
			
			managedEmpresa.setFact(empresa.getFact());
			managedEmpresa.setTerm(empresa.getTerm());
			managedEmpresa.setUact(empresa.getUact());
			
			entityManager.merge(managedEmpresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Empresa empresa) {
		try {
			Empresa managedEmpresa = entityManager.find(Empresa.class, empresa.getId());
			
			managedEmpresa.setCodigoPromotor(empresa.getCodigoPromotor());
			managedEmpresa.setLogoURL(empresa.getLogoURL());
			managedEmpresa.setNombre(empresa.getNombre());
			managedEmpresa.setNombreContrato(empresa.getNombreContrato());
			managedEmpresa.setNombreSucursal(empresa.getNombreSucursal());
<<<<<<< HEAD
			
			managedEmpresa.setFormaPagos(empresa.getFormaPagos());
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
			
			managedEmpresa.setFact(empresa.getFact());
			managedEmpresa.setTerm(empresa.getTerm());
			managedEmpresa.setUact(empresa.getUact());
			
			entityManager.merge(managedEmpresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}