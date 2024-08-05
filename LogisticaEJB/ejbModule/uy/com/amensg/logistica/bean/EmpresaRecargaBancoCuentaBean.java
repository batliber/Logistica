package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaRecargaBancoCuenta;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.RecargaBanco;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class EmpresaRecargaBancoCuentaBean implements IEmpresaRecargaBancoCuentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<EmpresaRecargaBancoCuenta>().list(
				entityManager, metadataConsulta, new EmpresaRecargaBancoCuenta()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<EmpresaRecargaBancoCuenta>().count(
				entityManager, metadataConsulta, new EmpresaRecargaBancoCuenta()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<EmpresaRecargaBancoCuenta> listByEmpresaRecargaBancoMoneda(
		Empresa empresa, RecargaBanco recargaBanco, Moneda moneda
	) {
		Collection<EmpresaRecargaBancoCuenta> result = null;
		
		try {
			TypedQuery<EmpresaRecargaBancoCuenta> query =
				entityManager.createQuery(
					"SELECT e"
					+ " FROM EmpresaRecargaBancoCuenta e"
					+ " WHERE e.empresa.id = :empresaId"
					+ " AND e.recargaBanco.id = :recargaBancoId"
					+ " AND e.moneda.id = :monedaId",
					EmpresaRecargaBancoCuenta.class
				);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("recargaBancoId", recargaBanco.getId());
			query.setParameter("monedaId", moneda.getId());
			
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EmpresaRecargaBancoCuenta getById(Long id) {
		EmpresaRecargaBancoCuenta result = null;
		
		try {
			result = entityManager.find(EmpresaRecargaBancoCuenta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EmpresaRecargaBancoCuenta save(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta) {
		EmpresaRecargaBancoCuenta result = null;
		
		try {
			empresaRecargaBancoCuenta.setFcre(empresaRecargaBancoCuenta.getFact());
			empresaRecargaBancoCuenta.setUcre(empresaRecargaBancoCuenta.getUact());
			
			entityManager.persist(empresaRecargaBancoCuenta);
			
			result = empresaRecargaBancoCuenta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta) {
		try {
			EmpresaRecargaBancoCuenta empresaRecargaBancoCuentaManaged = 
				entityManager.find(EmpresaRecargaBancoCuenta.class, empresaRecargaBancoCuenta.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			empresaRecargaBancoCuentaManaged.setFechaBaja(date);
			
			empresaRecargaBancoCuentaManaged.setFact(empresaRecargaBancoCuenta.getFact());
			empresaRecargaBancoCuentaManaged.setTerm(empresaRecargaBancoCuenta.getTerm());
			empresaRecargaBancoCuentaManaged.setUact(empresaRecargaBancoCuenta.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta) {
		try {
			EmpresaRecargaBancoCuenta empresaRecargaBancoCuentaManaged = 
				entityManager.find(EmpresaRecargaBancoCuenta.class, empresaRecargaBancoCuenta.getId());
			
			empresaRecargaBancoCuentaManaged.setNumero(empresaRecargaBancoCuenta.getNumero());
			
			empresaRecargaBancoCuentaManaged.setEmpresa(empresaRecargaBancoCuenta.getEmpresa());
			empresaRecargaBancoCuentaManaged.setMoneda(empresaRecargaBancoCuenta.getMoneda());
			empresaRecargaBancoCuentaManaged.setRecargaBanco(empresaRecargaBancoCuenta.getRecargaBanco());
			
			empresaRecargaBancoCuentaManaged.setFact(empresaRecargaBancoCuenta.getFact());
			empresaRecargaBancoCuentaManaged.setFechaBaja(empresaRecargaBancoCuenta.getFechaBaja());
			empresaRecargaBancoCuentaManaged.setTerm(empresaRecargaBancoCuenta.getTerm());
			empresaRecargaBancoCuentaManaged.setUact(empresaRecargaBancoCuenta.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}