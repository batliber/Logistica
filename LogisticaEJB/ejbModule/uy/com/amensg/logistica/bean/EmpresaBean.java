package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class EmpresaBean implements IEmpresaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean;
	
	@EJB
	private IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
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

	public Empresa getById(Long id, boolean initializeCollections) {
		Empresa result = null;
		
		try {
			TypedQuery<Empresa> query = 
				entityManager.createQuery(
					"SELECT e"
					+ " FROM Empresa e"
					+ " WHERE e.id = :id", 
					Empresa.class
				);
			query.setParameter("id", id);
			
			TypedQuery<FormaPago> queryFormaPagos = 
				entityManager.createQuery(
					"SELECT fs"
					+ " FROM Empresa e"
					+ " JOIN e.formaPagos fs"
					+ " WHERE e.id = :id",
					FormaPago.class
				);
			queryFormaPagos.setParameter("id", id);
			
			TypedQuery<Usuario> queryEmpresaUsuarioContratos = 
				entityManager.createQuery(
					"SELECT eucs"
					+ " FROM Empresa e"
					+ " JOIN e.empresaUsuarioContratos eucs"
					+ " WHERE e.id = :id",
					Usuario.class
				);
			queryEmpresaUsuarioContratos.setParameter("id", id);
			
			List<Empresa> resultList = query.getResultList();
			
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				entityManager.detach(result);
				
				if (initializeCollections) {
					Set<FormaPago> formaPagos = new HashSet<FormaPago>();
					for (FormaPago formaPago : queryFormaPagos.getResultList()) {
						formaPagos.add(formaPago);
					}
					
					result.setFormaPagos(formaPagos);
					
					Set<Usuario> empresaUsuarioContratos = new HashSet<Usuario>();
					for (Usuario usuario : queryEmpresaUsuarioContratos.getResultList()) {
						entityManager.detach(usuario);
						
						usuario.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
						
						empresaUsuarioContratos.add(usuario);
					}
					
					result.setEmpresaUsuarioContratos(empresaUsuarioContratos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Empresa getByIdAgente(Long idAgente, boolean initializeCollections) {
		Empresa result = null;
		
		try {
			TypedQuery<Empresa> query = 
				entityManager.createQuery(
					"SELECT e"
					+ " FROM Empresa e"
					+ " WHERE e.codigoPromotor = :idAgente", 
					Empresa.class
				);
			query.setParameter("idAgente", idAgente);
			
			TypedQuery<FormaPago> queryFormaPagos = 
				entityManager.createQuery(
					"SELECT fs"
					+ " FROM Empresa e"
					+ " JOIN e.formaPagos fs"
					+ " WHERE e.id = :id",
					FormaPago.class
				);
			
			List<Empresa> resultList = query.getResultList();
			
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				entityManager.detach(result);
				
				if (initializeCollections) {
					queryFormaPagos.setParameter("id", result.getId());
				
					Set<FormaPago> formaPagos = new HashSet<FormaPago>();
					for (FormaPago formaPago : queryFormaPagos.getResultList()) {
						formaPagos.add(formaPago);
					}
					
					result.setFormaPagos(formaPagos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<FormaPago> listFormasPagoById(Long id) {
		Collection<FormaPago> result = new LinkedList<FormaPago>();
		
		try {
			Empresa empresa = this.getById(id, true);
			
			for (FormaPago formaPago : empresa.getFormaPagos()) {
				result.add(formaPago);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Usuario> listEmpresaUsuarioContratosById(Long id) {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			Empresa empresa = this.getById(id, true);
			
			for (Usuario usuario : empresa.getEmpresaUsuarioContratos()) {
				result.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Empresa save(Empresa empresa) {
		Empresa result = null;
		
		try {
			empresa.setFcre(empresa.getFact());
			empresa.setUcre(empresa.getUact());
			
			entityManager.persist(empresa);
			
			iDisponibilidadEntregaEmpresaZonaTurnoBean.generarDisponibilidadParaEmpresa(empresa);
			
			Rol rol = iRolBean.getById(Long.parseLong(Configuration.getInstance().getProperty("rol.Administrador")), false);
			
			Usuario usuario = iUsuarioBean.getById(empresa.getUact(), false);
			
			UsuarioRolEmpresa usuarioRolEmpresa = new UsuarioRolEmpresa();
			usuarioRolEmpresa.setEmpresa(empresa);
			usuarioRolEmpresa.setRol(rol);
			usuarioRolEmpresa.setUsuario(usuario);
			
			usuarioRolEmpresa.setFact(empresa.getFact());
			usuarioRolEmpresa.setFcre(empresa.getFact());
			usuarioRolEmpresa.setTerm(Long.valueOf(1));
			usuarioRolEmpresa.setUact(empresa.getUact());
			usuarioRolEmpresa.setUcre(empresa.getUact());
			
			iUsuarioRolEmpresaBean.save(usuarioRolEmpresa);
			
			result = empresa;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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
			managedEmpresa.setDireccion(empresa.getDireccion());
			managedEmpresa.setLogoURL(empresa.getLogoURL());
			managedEmpresa.setNombre(empresa.getNombre());
			managedEmpresa.setNombreContrato(empresa.getNombreContrato());
			managedEmpresa.setNombreSucursal(empresa.getNombreSucursal());
			managedEmpresa.setOmitirControlVendidos(empresa.getOmitirControlVendidos());
			
			managedEmpresa.setFormaPagos(empresa.getFormaPagos());
			managedEmpresa.setEmpresaUsuarioContratos(empresa.getEmpresaUsuarioContratos());

			managedEmpresa.setFact(empresa.getFact());
			managedEmpresa.setTerm(empresa.getTerm());
			managedEmpresa.setUact(empresa.getUact());
			
			entityManager.merge(managedEmpresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}