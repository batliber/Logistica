package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class UsuarioRolEmpresaBean implements IUsuarioRolEmpresaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IRolBean iRolBean;
	
	public Collection<UsuarioRolEmpresa> listByUsuario(Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		return result;
	}
	
	public Collection<UsuarioRolEmpresa> listByRolUsuario(Rol rol, Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		try {
			Usuario usuarioManaged = iUsuarioBean.getById(usuario.getId(), true);
			
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioManaged.getUsuarioRolEmpresas()) {
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			TypedQuery<UsuarioRolEmpresa> query = entityManager.createQuery(
				"SELECT ure"
				+ " FROM UsuarioRolEmpresa ure"
				+ " WHERE ure.rol = :rol"
				+ " AND ure.empresa IN :empresas"
				+ " AND ure.usuario.fechaBaja IS NULL"
				+ " ORDER BY ure.usuario.nombre ASC",
				UsuarioRolEmpresa.class
			);
			query.setParameter("rol", rol);
			query.setParameter("empresas", empresas);
			
			for (UsuarioRolEmpresa usuarioRolEmpresaResult : query.getResultList()) {
				result.add(usuarioRolEmpresaResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<UsuarioRolEmpresa> listByRolUsuarioMinimal(Rol rol, Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		try {
			Usuario usuarioManaged = iUsuarioBean.getById(usuario.getId(), true);
			
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioManaged.getUsuarioRolEmpresas()) {
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			TypedQuery<UsuarioRolEmpresa> query = entityManager.createQuery(
				"SELECT ure"
				+ " FROM UsuarioRolEmpresa ure"
				+ " WHERE ure.rol = :rol"
				+ " AND ure.empresa IN :empresas"
				+ " AND ure.usuario.fechaBaja IS NULL"
				+ " ORDER BY ure.usuario.nombre ASC",
				UsuarioRolEmpresa.class
			);
			query.setParameter("rol", rol);
			query.setParameter("empresas", empresas);
			
			for (UsuarioRolEmpresa usuarioRolEmpresaResult : query.getResultList()) {
				result.add(usuarioRolEmpresaResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<UsuarioRolEmpresa> listVendedoresByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")),
				false
			);
		
		return this.listByRolUsuario(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listBackofficesByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Backoffice")),
				false
			);
		
		return this.listByRolUsuario(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Distribuidor")),
				false
			);
			
		return this.listByRolUsuario(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresByUsuarioMinimal(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Distribuidor")),
				false
			);
			
		return this.listByRolUsuarioMinimal(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listActivadoresByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Activador")),
				false
			);
		
		return this.listByRolUsuario(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresChipsByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.DistribuidorChips")),
				false
			);
			
		return this.listByRolUsuario(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listAtencionClienteOperadoresByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.AtencionClienteOperador")),
				false
			);
			
		return this.listByRolUsuario(rol, usuario);
	}
	
	public Collection<UsuarioRolEmpresa> listAtencionClienteGestionadoresByUsuario(Usuario usuario) {
		Rol rol = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.AtencionClienteGestionador")),
				false
			);
			
		return this.listByRolUsuario(rol, usuario);
	}

	public void save(UsuarioRolEmpresa usuarioRolEmpresa) {
		try {
			usuarioRolEmpresa.setFcre(usuarioRolEmpresa.getFact());
			usuarioRolEmpresa.setUcre(usuarioRolEmpresa.getUact());
			
			entityManager.persist(usuarioRolEmpresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}