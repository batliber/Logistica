package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RolJerarquia;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.SeguridadTipoEvento;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class UsuarioBean implements IUsuarioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<Usuario> list() {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM Usuario u"
					+ " WHERE u.fechaBaja IS NULL"
					+ " ORDER BY u.login ASC",
					Usuario.class
				);
			
			for (Usuario usuario : query.getResultList()) {
				result.add(usuario);
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
					usuarioRolEmpresa.getRol().getMenus().size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Usuario> listMinimal() {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT u.id, u.nombre"
					+ " FROM Usuario u"
					+ " WHERE u.fechaBaja IS NULL"
					+ " ORDER BY u.nombre ASC", 
					Object[].class
				);
				
			for (Object[] usuario : query.getResultList()) {
				Usuario usuarioMinimal = new Usuario();
				usuarioMinimal.setId((Long)usuario[0]);
				usuarioMinimal.setNombre((String)usuario[1]);
				
				result.add(usuarioMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			result = new QueryBuilder<Usuario>().list(entityManager, metadataConsulta, new Usuario());
			
			for (Object object : result.getRegistrosMuestra()) {
				Usuario usuario = (Usuario) object;
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
					usuarioRolEmpresa.getRol().getMenus().size();
					usuarioRolEmpresa.getRol().getSubordinados().size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado listSubordinados(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
			
			Root<Usuario> root = criteriaQuery.from(Usuario.class);
			root.alias("susub");
			
			QueryHelper queryHelper = new QueryHelper();
			
			Predicate where = queryHelper.construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryUsuarioRolEmpresaSubordinado = 
				criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootUsuarioRolEmpresaSubordinado = 
				subqueryUsuarioRolEmpresaSubordinado.from(UsuarioRolEmpresa.class);
			subrootUsuarioRolEmpresaSubordinado.alias("suresub");
						
			Subquery<RolJerarquia> subqueryRolJerarquia = criteriaQuery.subquery(RolJerarquia.class);
			Root<RolJerarquia> subrootRolJerarquia = 
				subqueryRolJerarquia.from(RolJerarquia.class);
			subrootRolJerarquia.alias("srj");
			
			Subquery<UsuarioRolEmpresa> subqueryUsuarioRolEmpresaSuperior = 
				criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootUsuarioRolEmpresaSuperior = 
				subqueryUsuarioRolEmpresaSuperior.from(UsuarioRolEmpresa.class);
			subrootUsuarioRolEmpresaSuperior.alias("sure");
			
			Predicate predicateUsuarioRolEmpresas = 
				criteriaBuilder.exists(
					subqueryUsuarioRolEmpresaSubordinado
						.select(subrootUsuarioRolEmpresaSubordinado)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(
									subrootUsuarioRolEmpresaSubordinado.get("usuario").get("id"), 
									root.get("id")
								),
								criteriaBuilder.exists(
									subqueryRolJerarquia
										.select(subrootRolJerarquia)
										.where(
											criteriaBuilder.and(
												criteriaBuilder.equal(
													subrootRolJerarquia.get("rolSubordinadoId"),
													subrootUsuarioRolEmpresaSubordinado.get("rol").get("id")
												),
												criteriaBuilder.exists(
													subqueryUsuarioRolEmpresaSuperior
														.select(subrootUsuarioRolEmpresaSuperior)
														.where(
															criteriaBuilder.and(
																criteriaBuilder.equal(
																	subrootUsuarioRolEmpresaSuperior.get("rol").get("id"),
																	subrootRolJerarquia.get("rolId")
																),
																criteriaBuilder.equal(
																	subrootUsuarioRolEmpresaSuperior.get("empresa").get("id"),
																	subrootUsuarioRolEmpresaSubordinado.get("empresa").get("id")
																),
																criteriaBuilder.equal(
																	subrootUsuarioRolEmpresaSuperior.get("usuario").get("id"),
																	criteriaBuilder.parameter(Long.class, "usuario")
																)
															)
														)
												)
											)
										)
								)
							)
						)
				);
			
			where = 
				criteriaBuilder.and(
					where,
					predicateUsuarioRolEmpresas
				);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = queryHelper.construirOrderBy(metadataConsulta, criteriaBuilder, root);
			
			criteriaQuery
				.select(root)
				.distinct(true)
				.where(where)
				.orderBy(orders);

			TypedQuery<Usuario> query = entityManager.createQuery(criteriaQuery);
			
			query = queryHelper.cargarParameters(metadataConsulta, query, root);
			
			query.setParameter("usuario", usuarioId);
			
			// Acotar al tamaño de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (Usuario usuario : query.getResultList()) {
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
					usuarioRolEmpresa.getRol().getMenus().size();
					usuarioRolEmpresa.getRol().getSubordinados().size();
				}
				
				registrosMuestra.add(usuario);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Usuario>().count(entityManager, metadataConsulta, new Usuario());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countSubordinados(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			Root<Usuario> rootCount = criteriaQueryCount.from(Usuario.class);
			rootCount.alias("susub");
			
			QueryHelper queryHelper = new QueryHelper();
			
			Predicate where = queryHelper.construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Subquery<UsuarioRolEmpresa> subqueryUsuarioRolEmpresaSubordinado = 
				criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootUsuarioRolEmpresaSubordinado = 
				subqueryUsuarioRolEmpresaSubordinado.from(UsuarioRolEmpresa.class);
			subrootUsuarioRolEmpresaSubordinado.alias("suresub");
						
			Subquery<RolJerarquia> subqueryRolJerarquia = criteriaQueryCount.subquery(RolJerarquia.class);
			Root<RolJerarquia> subrootRolJerarquia = 
				subqueryRolJerarquia.from(RolJerarquia.class);
			subrootRolJerarquia.alias("srj");
			
			Subquery<UsuarioRolEmpresa> subqueryUsuarioRolEmpresaSuperior = 
				criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootUsuarioRolEmpresaSuperior = 
				subqueryUsuarioRolEmpresaSuperior.from(UsuarioRolEmpresa.class);
			subrootUsuarioRolEmpresaSuperior.alias("sure");
			
			Predicate predicateUsuarioRolEmpresas = 
				criteriaBuilder.exists(
					subqueryUsuarioRolEmpresaSubordinado
						.select(subrootUsuarioRolEmpresaSubordinado)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(
									subrootUsuarioRolEmpresaSubordinado.get("usuario").get("id"), 
									rootCount.get("id")
								),
								criteriaBuilder.exists(
									subqueryRolJerarquia
										.select(subrootRolJerarquia)
										.where(
											criteriaBuilder.and(
												criteriaBuilder.equal(
													subrootRolJerarquia.get("rolSubordinadoId"),
													subrootUsuarioRolEmpresaSubordinado.get("rol").get("id")
												),
												criteriaBuilder.exists(
													subqueryUsuarioRolEmpresaSuperior
														.select(subrootUsuarioRolEmpresaSuperior)
														.where(
															criteriaBuilder.and(
																criteriaBuilder.equal(
																	subrootUsuarioRolEmpresaSuperior.get("rol").get("id"),
																	subrootRolJerarquia.get("rolId")
																),
																criteriaBuilder.equal(
																	subrootUsuarioRolEmpresaSuperior.get("empresa").get("id"),
																	subrootUsuarioRolEmpresaSubordinado.get("empresa").get("id")
																),
																criteriaBuilder.equal(
																	subrootUsuarioRolEmpresaSuperior.get("usuario").get("id"),
																	criteriaBuilder.parameter(Long.class, "usuario")
																)
															)
														)
												)
											)
										)
								)
							)
						)
				);
			
			where = 
				criteriaBuilder.and(
					where,
					predicateUsuarioRolEmpresas
				);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount))
				.distinct(true)
				.where(where);

			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount = queryHelper.cargarParameters(metadataConsulta, queryCount, rootCount);
			
			queryCount.setParameter("usuario", usuarioId);
			
			result = (Long) queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario getById(Long id, boolean initializeCollections) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM Usuario u"
					+ " WHERE u.id = :usuarioId"
					+ " AND u.fechaBaja IS NULL",
					Usuario.class
				);
			query.setParameter("usuarioId", id);
			
			List<Usuario> resultList = query.getResultList();
			
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				if (initializeCollections) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : result.getUsuarioRolEmpresas()) {
						usuarioRolEmpresa.getRol().getMenus().size();
						usuarioRolEmpresa.getRol().getSubordinados().size();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario getByIdMinimal(Long id) {
		Usuario result = null;
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT u.id, u.nombre"
					+ " FROM Usuario u"
					+ " WHERE u.id = :usuarioId",
					Object[].class
				);
			query.setParameter("usuarioId", id);
			
			List<Object[]> resultList = query.getResultList();
			
			if (!resultList.isEmpty()) {
				Object[] object = resultList.get(0);
				
				result = new Usuario();
				result.setId((Long) object[0]);
				result.setNombre((String) object[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Usuario getByLogin(String login, boolean initializeCollections) {
		Usuario result = null;
		
		try {
			TypedQuery<Usuario> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM Usuario u"
					+ " WHERE u.login = :login"
					+ " AND u.fechaBaja IS NULL",
					Usuario.class
				);
			query.setParameter("login", login);
			
			List<Usuario> resultList = query.getResultList();

			if (!resultList.isEmpty()) {
				result = resultList.get(0);
				
				if (initializeCollections) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : result.getUsuarioRolEmpresas()) {
						usuarioRolEmpresa.getRol().getMenus().size();
						usuarioRolEmpresa.getRol().getSubordinados().size();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario getByEmailMinimal(String email) {
		Usuario result = null;
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT u.id, u.nombre"
					+ " FROM Usuario u"
					+ " WHERE u.email = :email",
					Object[].class
				);
			query.setParameter("email", email);
			
			List<Object[]> resultList = query.getResultList();
			
			if (!resultList.isEmpty()) {
				Object[] object = resultList.get(0);
				
				result = new Usuario();
				result.setId((Long) object[0]);
				result.setNombre((String) object[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario save(Usuario usuario) {
		Usuario result = null;
		
		try {
			Collection<UsuarioRolEmpresa> usuarioRolEmpresas = usuario.getUsuarioRolEmpresas();
			
			usuario.setFcre(usuario.getFact());
			usuario.setUcre(usuario.getUact());
			
			entityManager.persist(usuario);
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioRolEmpresas) {
				usuarioRolEmpresa.setUsuario(usuario);
				
				usuarioRolEmpresa.setFcre(usuario.getFact());
				usuarioRolEmpresa.setUcre(usuario.getUact());
				
				entityManager.persist(usuarioRolEmpresa);
			}
			
			result = usuario;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(Usuario usuario) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			managedUsuario.setFechaBaja(date);
			
			managedUsuario.setFact(usuario.getFact());
			managedUsuario.setTerm(usuario.getTerm());
			managedUsuario.setUact(usuario.getUact());
			
			entityManager.merge(managedUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario update(Usuario usuario) {
		Usuario result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : managedUsuario.getUsuarioRolEmpresas()) {
				entityManager.remove(usuarioRolEmpresa);
			}
			
			entityManager.flush();
			
			if (usuario.getContrasena() != null && !usuario.getContrasena().equals(managedUsuario.getContrasena())) {
				managedUsuario.setContrasena(usuario.getContrasena());
				
				SeguridadAuditoria seguridadAuditoria = new SeguridadAuditoria();
				seguridadAuditoria.setFecha(date);
				
				SeguridadTipoEvento seguridadTipoEvento = new SeguridadTipoEvento();
				seguridadTipoEvento.setId(
					Long.parseLong(Configuration.getInstance().getProperty("seguridadTipoEvento.CambioPassword"))
				);
				
				seguridadAuditoria.setSeguridadTipoEvento(seguridadTipoEvento);
				
				seguridadAuditoria.setUsuario(managedUsuario);
				
				seguridadAuditoria.setFcre(date);
				seguridadAuditoria.setFact(date);
				seguridadAuditoria.setTerm(Long.valueOf(1));
				seguridadAuditoria.setUact(managedUsuario.getId());
				seguridadAuditoria.setUcre(managedUsuario.getId());
				
				entityManager.persist(seguridadAuditoria);
			}
			
			managedUsuario.setBloqueado(usuario.getBloqueado());
			managedUsuario.setCambioContrasenaProximoLogin(usuario.getCambioContrasenaProximoLogin());
			managedUsuario.setDocumento(usuario.getDocumento());
			managedUsuario.setIntentosFallidosLogin(usuario.getIntentosFallidosLogin());
			managedUsuario.setLogin(usuario.getLogin());
			managedUsuario.setNombre(usuario.getNombre());
			
			managedUsuario.setFact(usuario.getFact());
			managedUsuario.setTerm(usuario.getTerm());
			managedUsuario.setUact(usuario.getUact());
			
			managedUsuario.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			
			entityManager.merge(managedUsuario);
			
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				usuarioRolEmpresa.setUsuario(usuario);
				
				usuarioRolEmpresa.setFact(usuario.getFact());
				usuarioRolEmpresa.setTerm(usuario.getTerm());
				usuarioRolEmpresa.setUact(usuario.getUact());
				
				entityManager.merge(usuarioRolEmpresa);
			}
			
			result = managedUsuario;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario updateIntentosFallidosLogin(Usuario usuario) {
		Usuario result = null;
		
		try {
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			managedUsuario.setIntentosFallidosLogin(usuario.getIntentosFallidosLogin());
			
			managedUsuario.setFact(usuario.getFact());
			managedUsuario.setTerm(usuario.getTerm());
			managedUsuario.setUact(usuario.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Usuario updateBloqueado(Usuario usuario) {
		Usuario result = null;
		
		try {
			Usuario managedUsuario = entityManager.find(Usuario.class, usuario.getId());
			
			managedUsuario.setBloqueado(usuario.getBloqueado());
			
			managedUsuario.setFact(usuario.getFact());
			managedUsuario.setTerm(usuario.getTerm());
			managedUsuario.setUact(usuario.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}