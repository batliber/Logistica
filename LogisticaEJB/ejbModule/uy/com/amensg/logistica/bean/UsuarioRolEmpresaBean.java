package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class UsuarioRolEmpresaBean implements IUsuarioRolEmpresaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IRolBean iRolBean;
	
	public Collection<UsuarioRolEmpresa> listByUsuario(Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		return result;
	}

	public Collection<UsuarioRolEmpresa> listVendedoresByUsuario(Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		try {
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Vendedor"))
			);
			
			Usuario usuarioManaged = iUsuarioBean.getById(usuario.getId());
			
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioManaged.getUsuarioRolEmpresas()) {
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			TypedQuery<UsuarioRolEmpresa> query = entityManager.createQuery(
				"SELECT ure"
				+ " FROM UsuarioRolEmpresa ure"
				+ " WHERE ure.rol = :rol"
				+ " AND ure.empresa IN :empresas"
				+ " AND ure.usuario.fechaBaja IS NULL",
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
	
	public Collection<UsuarioRolEmpresa> listBackofficesByUsuario(Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		try {
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Backoffice"))
			);
			
			Usuario usuarioManaged = iUsuarioBean.getById(usuario.getId());
			
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioManaged.getUsuarioRolEmpresas()) {
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			TypedQuery<UsuarioRolEmpresa> query = entityManager.createQuery(
				"SELECT ure"
				+ " FROM UsuarioRolEmpresa ure"
				+ " WHERE ure.rol = :rol"
				+ " AND ure.empresa IN :empresas"
				+ " AND ure.usuario.fechaBaja IS NULL",
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
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresByUsuario(Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		try {
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Distribuidor"))
			);
			
			Usuario usuarioManaged = iUsuarioBean.getById(usuario.getId());
			
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioManaged.getUsuarioRolEmpresas()) {
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			TypedQuery<UsuarioRolEmpresa> query = entityManager.createQuery(
				"SELECT ure"
				+ " FROM UsuarioRolEmpresa ure"
				+ " WHERE ure.rol = :rol"
				+ " AND ure.empresa IN :empresas"
				+ " AND ure.usuario.fechaBaja IS NULL",
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
	
	public Collection<UsuarioRolEmpresa> listActivadoresByUsuario(Usuario usuario) {
		Collection<UsuarioRolEmpresa> result = new LinkedList<UsuarioRolEmpresa>();
		
		try {
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Activador"))
			);
			
			Usuario usuarioManaged = iUsuarioBean.getById(usuario.getId());
			
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioManaged.getUsuarioRolEmpresas()) {
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			TypedQuery<UsuarioRolEmpresa> query = entityManager.createQuery(
				"SELECT ure"
				+ " FROM UsuarioRolEmpresa ure"
				+ " WHERE ure.rol = :rol"
				+ " AND ure.empresa IN :empresas"
				+ " AND ure.usuario.fechaBaja IS NULL",
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
}