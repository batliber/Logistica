package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.ContratoRelacion;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.DatosFinanciacion;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.ModalidadVenta;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.MotivoCambioPlan;
import uy.com.amensg.logistica.entities.Plan;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.ProcesoExportacion;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Sexo;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.StockTipoMovimiento;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ContratoBean implements IContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogisticaXA")
	private EntityManager entityManagerXA;
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IEstadoBean iEstadoBean;
	
	@EJB
	private IStockTipoMovimientoBean iStockTipoMovimientoBean;
	
	@EJB
	private IStockMovimientoBean iStockMovimientoBean;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean;
	
	@EJB
	private ITipoProcesoImportacionBean iTipoProcesoImportacionBean;
	
	@EJB
	private IProcesoImportacionBean iProcesoImportacionBean;
	
	@EJB
	private IProcesoExportacionBean iProcesoExportacionBean;
	
	@EJB
	private IPrecioBean iPrecioBean;
	
	@EJB
	private IFinanciacionBean iFinanciacionBean;
	
	/**
	 * Lista los Contrato que cumplen los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 * @return MetadataConsultaResultado con los resultados de la consulta.
	 */
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Contrato> criteriaQuery = criteriaBuilder.createQuery(Contrato.class);
			
			Root<Contrato> root = criteriaQuery.from(Contrato.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			boolean subordinados = false;
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!usuarioRolEmpresa.getRol().getSubordinados().isEmpty()) {
					subordinados = true;
					
					break;
				}
			}
			
			if (subordinados) {
				// Subquery con los roles subordinados del usuario
				Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
				Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
				subrootRolesSubordinados.alias("subrootRolesSubordinados");
				Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
				Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
				Predicate predicateSubordinados = criteriaBuilder.exists(
					subqueryRolesSubordinados
						.select(subrootRolesSubordinados)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
								criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), root.get("empresa").get("id")),
								criteriaBuilder.isMember(root.get("rol").as(Rol.class), expressionRolesSubordinados)
							)
						)
				);
				
				where = criteriaBuilder.and(
					where,
					criteriaBuilder.or(
						// Asignados al usuario.
						criteriaBuilder.equal(root.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
						// Asignados a algún rol subordinado dentro de la empresa
						predicateSubordinados
					)
				);
			} else {
				where = criteriaBuilder.and(
					where,
					// Asignados al usuario.
					criteriaBuilder.equal(root.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1"))
				);
			}
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = new LinkedList<Order>();
			for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
				String[] campos = metadataOrdenacion.getCampo().split("\\.");
				
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = root.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (metadataOrdenacion.getAscendente()) {
					orders.add(
						criteriaBuilder.asc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				} else {
					orders.add(
						criteriaBuilder.desc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				}
			}
			
			criteriaQuery
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<Contrato> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuarioId);

			if (subordinados) {
				query.setParameter("usuario2", usuarioId);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Contrato> field = root;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = root.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = root.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								query.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								query.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
									"p" + i,
									Boolean.parseBoolean(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			// Acotar al tamaño de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (Contrato contrato : query.getResultList()) {
				registrosMuestra.add(contrato);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Lista los Contrato que cumplan los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * Los objetos retornados son desconectados del PersistentContext.
	 * @param metadataConsulta
	 * @param usuarioId
	 * @return
	 */
	public MetadataConsultaResultado listDetached(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = this.list(metadataConsulta, usuarioId);
		
		for (Object o : result.getRegistrosMuestra()) {
			Contrato c = (Contrato) o;
			
			entityManager.detach(c);
		}
		
		return result;
	}
	
	/**
	 * Cuenta la cantidad de Contratos que cumplen los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 * @return Cantidad de registros que cumplen con los criterios.
	 */
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Contrato> rootCount = criteriaQueryCount.from(Contrato.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			boolean subordinados = false;
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!usuarioRolEmpresa.getRol().getSubordinados().isEmpty()) {
					subordinados = true;
					
					break;
				}
			}
			
			if (subordinados) {
				// Subquery con los roles subordinados del usuario
				Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
				Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
				subrootRolesSubordinados.alias("subrootRolesSubordinados");
				Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
				Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
				Predicate predicateSubordinados = criteriaBuilder.exists(
					subqueryRolesSubordinados
						.select(subrootRolesSubordinados)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
								criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), rootCount.get("empresa").get("id")),
								criteriaBuilder.isMember(rootCount.get("rol").as(Rol.class), expressionRolesSubordinados)
							)
						)
				);
				
				where = criteriaBuilder.and(
					where,
					criteriaBuilder.or(
						// Asignados al usuario.
						criteriaBuilder.equal(rootCount.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
						// Asignados a algún rol subordinado dentro de la empresa
						predicateSubordinados
					)
				);
			} else {
				where = criteriaBuilder.and(
					where,
					// Asignados al usuario.
					criteriaBuilder.equal(rootCount.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1"))
				);
			}
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("usuario1", usuarioId);
			
			if (subordinados) {
				queryCount.setParameter("usuario2", usuarioId);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Contrato> field = rootCount;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = rootCount.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = rootCount.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								queryCount.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								queryCount.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryCount.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryCount.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								queryCount.setParameter(
									"p" + i,
									Boolean.parseBoolean(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			result = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Lista los tipos de contrato distintos.
	 * 
	 * @return Lista de tipos de contrato existentes.
	 */
	public Collection<TipoContrato> listTipoContratos() {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT c.tipoContratoDescripcion, COUNT(c)"
				+ " FROM Contrato c"
				+ " WHERE c.tipoContratoDescripcion IS NOT NULL"
				+ " GROUP BY c.tipoContratoDescripcion"
				+ " ORDER BY COUNT(c) DESC"
			);
			
			for (Object object : query.getResultList()) {
				Object[] fields = (Object[]) object;
				
				TipoContrato tipoContrato = new TipoContrato();
				
				// tipoContrato.setTipoContratoCodigo((String) fields[0]);
				tipoContrato.setTipoContratoDescripcion((String) fields[0]);
				
				result.add(tipoContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Lista los tipos de contrato que cumplen con los criterios encapsulados en metadataConsulta y que puede ver el usuarioId
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 * @return Lista de tipos de contrato que cumplen con los criterios.
	 */
	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta, Long usuarioId) {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
			
			Root<Contrato> root = criteriaQuery.from(Contrato.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			// Subquery con los roles subordinados del usuario
			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
			subrootRolesSubordinados.alias("subrootRolesSubordinados");
			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.or(
					// Asignados al usuario.
					criteriaBuilder.equal(root.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
					// Asignados a algún rol subordinado dentro de la empresa
					criteriaBuilder.exists(
						subqueryRolesSubordinados
							.select(subrootRolesSubordinados)
							.where(
								criteriaBuilder.and(
									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), root.get("empresa").get("id")),
									criteriaBuilder.isMember(root.get("rol").as(Rol.class), expressionRolesSubordinados)
								)
							)
					)
				)
			);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = new LinkedList<Order>();
			for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
				String[] campos = metadataOrdenacion.getCampo().split("\\.");
				
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = root.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (metadataOrdenacion.getAscendente()) {
					orders.add(
						criteriaBuilder.asc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				} else {
					orders.add(
						criteriaBuilder.desc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				}
			}
			
			criteriaQuery
				.multiselect(
					root.get("tipoContratoDescripcion")
				).distinct(true)
				.where(where)
				.orderBy(orders);
			
			TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuarioId);
			query.setParameter("usuario2", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Contrato> field = root;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = root.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = root.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								query.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								query.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			for (Object object : query.getResultList()) {
				if (object != null) {
					TipoContrato tipoContrato = new TipoContrato();
					
					// tipoContrato.setTipoContratoCodigo((String) fields[0]);
					tipoContrato.setTipoContratoDescripcion((String) object);
					
					result.add(tipoContrato);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Procesa el archivo .csv de nombre fileName para la Empresa empresaId.
	 * 
	 * @param fileName El nombre del archivo a Importar.
	 * @param emmpresaId ID de la empresa a la cual asignar los contratos importados.
	 * @return String que informa cuántos MIDs se importarán, cuántos se sobreescribirán y cuántos se omitirán. 
	 */
	public String preprocesarArchivoEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<Long> mids = new LinkedList<Long>();
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				Long mid = Long.parseLong(fields[0].trim());
				
				mids.add(mid);
			}
			
			if (mids.size() > 300) {
				result =
					"err: La cantidad de líneas del archivo supera el máximo permitido (300).";
			} else {
				Map<Long, Integer> map = this.preprocesarConjunto(mids, empresaId);
				
				Long importar = Long.valueOf(0);
				Long sobreescribir = Long.valueOf(0);
				Long omitir = Long.valueOf(0);
				for (Entry<Long, Integer> entry : map.entrySet()) {
					switch (entry.getValue()) {
						case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
							importar++;
							
							break;
						case Constants.__COMPROBACION_IMPORTACION_OMITIR:
							omitir++;
							
							break;
						case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
							sobreescribir++;
							
							break;
					}
				}
				
				result =
					"Se importarán " + importar + " MIDs nuevos.|"
					+ "Se sobreescribirán " + sobreescribir + " MIDs.|"
					+ "Se omitirán " + omitir + " MIDs.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Procesa el archivo .csv de nombre fileName para la Empresa empresaId.
	 * 
	 * @param fileName El nombre del archivo a Importar.
	 * @param emmpresaId ID de la empresa a la cual asignar los contratos importados.
	 * @return String que informa cuántas ventas se importarán, cuántas se sobreescribirán y cuántas se omitirán. 
	 */
	public String preprocesarArchivoVentasANTELEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<String> nros = new LinkedList<String>();
			
			String line = null;
			boolean first = false;
			while ((line = bufferedReader.readLine()) != null) {
				if (first) {
					String[] fields = line.split(";");
					
					String nroTrn = fields[0].trim();
					
					nros.add(nroTrn);
				} else {
					first = true;
				}
			}
			
			Map<String, Integer> map = this.preprocesarConjuntoANTEL(nros, empresaId);
			
			Long importar = Long.valueOf(0);
			Long sobreescribir = Long.valueOf(0);
			Long omitir = Long.valueOf(0);
			for (Entry<String, Integer> entry : map.entrySet()) {
				switch (entry.getValue()) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						importar++;
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						omitir++;
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						sobreescribir++;
						
						break;
				}
			}
			
			result =
				"Se importarán " + importar + " ventas nuevas.|"
				+ "Se sobreescribirán " + sobreescribir + " ventas.|"
				+ "Se omitirán " + omitir + " ventas.";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Procesa un conjunto de MIDs para la empresa empresaId.
	 * 
	 * @param empresaId ID de la empresa a la cual asignar los MIDs.
	 * @param mids Colección de MIDs a procesar.
	 * @return Map indicando para cada MID si se importará, sobreescribirá u omitirá.
	 */
	public Map<Long, Integer> preprocesarConjunto(Collection<Long> mids, Long empresaId) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			Long estadoLlamarId = 
				Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR"));
			Long estadoVendidoId = 
				Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			Long estadoNoFirmaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.NOFIRMA"));
			Long estadoFacturaImpagaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FACTURAIMPAGA"));
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			gregorianCalendar.add(
				GregorianCalendar.MONTH, 
				-1 * Integer.parseInt(Configuration.getInstance().getProperty("cantidadMesesRetencionVenta"))
			);
			
			// Se admite volver a vender contratos transcurridos 6 meses de la última venta.
			Date fechaVentaLimite = gregorianCalendar.getTime();
			
			TypedQuery<Long> queryVendidos = 
				entityManager.createQuery(
					"SELECT c.mid"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id IN ("
						+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
						+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId, :estadoACMId,"
						+ " :estadoReagendarId, :estadoNoFirmaId, :estadoFacturaImpagaId"
					+ " )"
					+ " AND c.mid IN :mids"
					+ " AND c.fechaVenta > :fechaVentaLimite", 
					Long.class
				);
			queryVendidos.setParameter("estadoVendidoId", estadoVendidoId);
			queryVendidos.setParameter("estadoDistribuirId", estadoDistribuirId);
			queryVendidos.setParameter("estadoActivarId", estadoActivarId);
			queryVendidos.setParameter("estadoFaltaDocumentacionId", estadoFaltaDocumentacionId);
			queryVendidos.setParameter("estadoRecoordinarId", estadoRecoordinarId);
			queryVendidos.setParameter("estadoActDocVentaId", estadoActDocVentaId);
			queryVendidos.setParameter("estadoRedistribuirId", estadoRedistribuirId);
			queryVendidos.setParameter("estadoControlAntelId", estadoControlAntelId);
			queryVendidos.setParameter("estadoACMId", estadoACMId);
			queryVendidos.setParameter("estadoReagendarId", estadoReagendarId);
			queryVendidos.setParameter("estadoNoFirmaId", estadoNoFirmaId);
			queryVendidos.setParameter("estadoFacturaImpagaId", estadoFacturaImpagaId);
			queryVendidos.setParameter("mids", mids);
			queryVendidos.setParameter("fechaVentaLimite", fechaVentaLimite);
			
			for (Long mid : queryVendidos.getResultList()) {
				result.put(mid, Constants.__COMPROBACION_IMPORTACION_OMITIR);
			}
			
			TypedQuery<Long> queryLlamar = 
				entityManager.createQuery(
					"SELECT mid"
					+ " FROM Contrato c"
					+ " WHERE c.empresa.id = :empresaId"
					+ " AND c.estado.id = :estadoLlamarId"
					+ " AND c.mid IN :mids",
					Long.class
				);
			queryLlamar.setParameter("empresaId", empresaId);
			queryLlamar.setParameter("estadoLlamarId", estadoLlamarId);
			queryLlamar.setParameter("mids", mids);
			
			for (Long mid : queryLlamar.getResultList()) {
				result.put(mid, Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR);
			}
			
			for (Long mid : mids) {
				if (!result.containsKey(mid)) {
					result.put(mid, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Procesa un conjunto de ventas de ANTEL para la empresa empresaId.
	 * 
	 * @param empresaId ID de la empresa a la cual asignar los contratos.
	 * @param mids Colección de números de trámite a procesar.
	 * @return Map indicando para cada número de trámite si se importará, sobreescribirá u omitirá.
	 */
	public Map<String, Integer> preprocesarConjuntoANTEL(Collection<String> nros, Long empresaId) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		try {
			TypedQuery<String> query = 
				entityManager.createQuery(
					"SELECT c.antelNroTrn"
					+ " FROM Contrato c"
					+ " WHERE c.empresa.id = :empresaId"
					+ " AND c.antelNroTrn IN :nros",
					String.class
				);
			query.setParameter("empresaId", empresaId);
			query.setParameter("nros", nros);
			
			for (String nro : query.getResultList()) {
				result.put(nro, Constants.__COMPROBACION_IMPORTACION_OMITIR);
			}
			
			for (String nro : nros) {
				if (!result.containsKey(nro)) {
					result.put(nro, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Importa el archivo .csv de nombre fileName para la Empresa empresaId.
	 * Genera un Contrato (con estado "LLAMAR") por cada línea del archivo y los asigna a la "bandeja" de Supervisores de Call-Center de la Empresa empresaId.
	 *  
	 * @param fileName El nombre del archivo a Importar.
	 * @param emmpresaId ID de la empresa a la cual asignar los contratos importados.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String con el resultado de la operación.
	 */
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			gregorianCalendar.add(GregorianCalendar.MONTH, -1 * Integer.parseInt(Configuration.getInstance().getProperty("cantidadMesesRetencionVenta")));
			
			// Se admite volver a vender contratos transcurridos 6 meses de la última venta. 
			Date fechaVentaLimite = gregorianCalendar.getTime();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Estado estado = 
				iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId, false);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.ContratosParaLlamar")));
			
			EstadoProcesoImportacion estadoProcesoImportacionInicio = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.Inicio")));
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoOK = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoOK")));		
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoConErrores = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores")));
			
			Usuario usuario =
				iUsuarioBean.getById(loggedUsuarioId, false);
			
			ProcesoImportacion procesoImportacion = new ProcesoImportacion();
			procesoImportacion.setEstadoProcesoImportacion(estadoProcesoImportacionInicio);
			procesoImportacion.setFechaInicio(hoy);
			procesoImportacion.setNombreArchivo(fileName);
			procesoImportacion.setTipoProcesoImportacion(tipoProcesoImportacion);
			procesoImportacion.setUsuario(usuario);
			
			procesoImportacion.setFcre(hoy);
			procesoImportacion.setFact(hoy);
			procesoImportacion.setTerm(Long.valueOf(1));
			procesoImportacion.setUact(loggedUsuarioId);
			procesoImportacion.setUcre(loggedUsuarioId);
			
			ProcesoImportacion procesoImportacionManaged = iProcesoImportacionBean.save(procesoImportacion);
			
			Long estadoVendidoId = 
				Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			Long estadoNoFirmaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.NOFIRMA"));
			Long estadoFacturaImpagaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FACTURAIMPAGA"));
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<Tuple> selectVendidos = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE estado_id IN ("
					+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
					+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId,"
					+ " :estadoACMId, :estadoReagendarId, :estadoNoFirmaId, :estadoFacturaImpagaId"
				+ " )"
				+ " AND mid = :mid"
				+ " AND fecha_venta > :fechaVentaLimite",
				Tuple.class
			);
			selectVendidos.setParameter("estadoVendidoId", estadoVendidoId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoDistribuirId", estadoDistribuirId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoActivarId", estadoActivarId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoFaltaDocumentacionId", estadoFaltaDocumentacionId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoRecoordinarId", estadoRecoordinarId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoActDocVentaId", estadoActDocVentaId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoRedistribuirId", estadoRedistribuirId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoControlAntelId", estadoControlAntelId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoACMId", estadoACMId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoReagendarId", estadoReagendarId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoNoFirmaId", estadoNoFirmaId, LongType.INSTANCE);
			selectVendidos.setParameter("estadoFacturaImpagaId", estadoFacturaImpagaId, LongType.INSTANCE);
			selectVendidos.setParameter("fechaVentaLimite", fechaVentaLimite, TimestampType.INSTANCE);
			
			NativeQuery<Tuple> selectContratoExisteEmpresa = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE mid = :mid"
				+ " AND empresa_id = :empresaId"
				+ " AND estado_id = :estadoLlamarId",
				Tuple.class
			);
			selectContratoExisteEmpresa.addScalar("id", LongType.INSTANCE);
			selectContratoExisteEmpresa.setParameter("empresaId", empresa.getId(), LongType.INSTANCE);
			selectContratoExisteEmpresa.setParameter("estadoLlamarId", estado.getId(), LongType.INSTANCE);
			
			NativeQuery<?> insertContrato = hibernateSession.createNativeQuery(
				"INSERT INTO contrato("
					+ " id,"
					+ " numero_tramite,"
					+ " random,"
					+ " empresa_id,"
					+ " estado_id,"
					+ " rol_id,"
					+ " fcre,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					+ " ucre,"
					+ " agente,"
					+ " codigo_postal,"
					+ " direccion,"
					+ " documento,"
					+ " documento_tipo,"
					+ " equipo,"
					+ " fecha_fin_contrato,"
					+ " localidad,"
					+ " mid,"
					+ " nombre,"
					+ " numero_cliente,"
					+ " numero_contrato,"
					+ " observaciones,"
					+ " tipo_contrato_codigo,"
					+ " tipo_contrato_descripcion"
				+ " ) VALUES ("
					+ " nextval('hibernate_sequence'),"
					+ " nextval('numero_tramite_sequence'),"
					+ " CAST(random() * 1000000 AS integer),"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ " )"
			);
			
			insertContrato.setParameter(1, empresa.getId(), LongType.INSTANCE);
			insertContrato.setParameter(2, estado.getId(), LongType.INSTANCE);
			insertContrato.setParameter(3, rolSupervisorCallCenter.getId(), LongType.INSTANCE);
			
			insertContrato.setParameter(4, hoy, TimestampType.INSTANCE);
			insertContrato.setParameter(5, hoy, TimestampType.INSTANCE);
			insertContrato.setParameter(6, Long.valueOf(1), LongType.INSTANCE);
			insertContrato.setParameter(7, loggedUsuarioId, LongType.INSTANCE);
			insertContrato.setParameter(8, loggedUsuarioId, LongType.INSTANCE);
			
			NativeQuery<?> updateContrato = hibernateSession.createNativeQuery(
				"UPDATE contrato"
				+ " SET random = CAST(random() * 1000000 AS integer),"
					+ " fact = ?,"
					+ " term = ?,"
					+ " uact = ?,"
					+ " agente = ?,"
					+ " codigo_postal = ?,"
					+ " direccion = ?,"
					+ " documento = ?,"
					+ " documento_tipo = ?,"
					+ " equipo = ?,"
					+ " fecha_fin_contrato = ?,"
					+ " localidad = ?,"
					+ " nombre = ?,"
					+ " numero_cliente = ?,"
					+ " numero_contrato = ?,"
					+ " observaciones = ?,"
					+ " tipo_contrato_codigo = ?,"
					+ " tipo_contrato_descripcion = ?"
				+ " WHERE id = ?"
			);
			
			updateContrato.setParameter(1, hoy, TimestampType.INSTANCE);
			updateContrato.setParameter(2, Long.valueOf(1), LongType.INSTANCE);
			updateContrato.setParameter(3, loggedUsuarioId, LongType.INSTANCE);
			
			NativeQuery<?> insertContratoRoutingHistory = hibernateSession.createNativeQuery(
				"INSERT INTO contrato_routing_history("
					+ " id,"
					+ " fecha,"
					+ " empresa_id,"
					+ " usuario_id,"
					+ " rol_id,"
					+ " ucre,"
					+ " uact,"
					+ " fact,"
					+ " fcre,"
					+ " term,"
					+ " contrato_id,"
					+ " estado_id"
				+ " ) SELECT nextval('hibernate_sequence'),"
					+ " c.fact,"
					+ " c.empresa_id,"
					+ " null,"
					+ " c.rol_id,"
					+ " c.ucre,"
					+ " c.uact,"
					+ " c.fact,"
					+ " c.fcre,"
					+ " c.term,"
					+ " c.id,"
					+ " c.estado_id"
				+ " FROM contrato c"
				+ " LEFT OUTER JOIN contrato_routing_history crh ON crh.contrato_id = c.id"
				+ " WHERE c.fact = ?"
				+ " AND crh.id IS NULL"
			);
			
			insertContratoRoutingHistory.setParameter(1, hoy, TimestampType.INSTANCE);
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				String[] fields = line.split(";", -1);
				
				if (fields.length < 15) {
					System.err.println(
						"Error al procesar archivo: " + fileName + "."
						+ " Formato de línea " + lineNumber + " incompatible."
						+ " Cantidad de columnas (" + fields.length + ") insuficientes."
					);
					errors++;
				} else {
					boolean ok = true;
					
					Long mid = null;
					try {
						mid = Long.parseLong(fields[0].trim());
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo mid incorrecto -> " + fields[0].trim());
						ok = false;
					}
					
					Date fechaFinContrato = null;
					try {
						fechaFinContrato = 
							(fields[1] != null && !fields[1].equals("")) ? simpleDateFormat.parse(fields[1].trim()) : null;	
					} catch (ParseException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo fechaFinContrato incorrecto -> " + fields[1].trim()
						);
						ok = false;
					}
					
					String tipoContratoCodigo = 
						(fields[2] != null && !fields[2].equals("")) ? fields[2].trim() : null;
					String tipoContratoDescripcion = 
						(fields[3] != null && !fields[3].equals("")) ? fields[3].trim() : null;
					
					Long documentoTipo = null;
					try {
						documentoTipo =
							(fields[4] != null && !fields[4].equals("")) ? Long.parseLong(fields[4].trim()) : null;
					} catch (Exception e) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo documentoTipo incorrecto -> " + fields[4].trim()
						);
						ok = false;
					}
					
					// 04/11/2018 - No se importan los datos personales.
					String documento = null;
//						(fields[5] != null && !fields[5].equals("")) ? fields[5].trim() : null;
					String nombre = null; 
//						(fields[6] != null && !fields[6].equals("")) ? fields[6].trim() : null;
					String direccion = null;
//						(fields[7] != null && !fields[7].equals("")) ? fields[7].trim() : null;
					
					String codigoPostal = 
						(fields[8] != null && !fields[8].equals("")) ? fields[8].trim() : null;
					if (codigoPostal != null && codigoPostal.length() > 16) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo codigoPostal incorrecto -> " + fields[8].trim()
						);
						ok = false;
					}
					
					// 04/11/2018 - No se importan los datos personales.
					String localidad = null;
//						(fields[9] != null && !fields[9].equals("")) ? fields[9].trim() : null;
					String equipo = 
						(fields[10] != null && !fields[10].equals("")) ? fields[10].trim() : null;
					String agente = 
						(fields[11] != null && !fields[11].equals("")) ? fields[11].trim() : null;
					
					Long numeroCliente = null;
					try {
						numeroCliente = 
							(fields[12] != null && !fields[12].equals("")) ? Long.parseLong(fields[12].trim()) : null;	
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo numeroCliente incorrecto -> " + fields[12].trim()
						);
						ok = false;
					}
					
					Long numeroContrato = null;
					try {
						numeroContrato = 
							(fields[13] != null && !fields[13].equals("")) ? Long.parseLong(fields[13].trim()) : null;	
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo numeroContrato incorrecto -> " + fields[13].trim()
						);
						ok = false;
					}
					
					String observaciones =
						(fields[14] != null && !fields[14].equals("")) ? fields[14].trim() : null;
					
					if (!ok) {
						errors++;
					} else {
						// Busco si el mid está en estado VENDIDO o superior
						selectVendidos.setParameter("mid", mid, LongType.INSTANCE);
						
						if (selectVendidos.list().size() == 0) {
							// Busco si el mid ya está asignado en estado LLAMAR para la empresa.
							selectContratoExisteEmpresa.setParameter("mid", mid, LongType.INSTANCE);
							
							List<Tuple> listContratoExisteEmpresa = selectContratoExisteEmpresa.list();
							if (listContratoExisteEmpresa.size() > 0) {
								// Si ya está en estado LLAMAR sobre-escribo.
								Long contratoId = (Long) listContratoExisteEmpresa.get(0).get(0);
								
								updateContrato.setParameter(4, agente, StringType.INSTANCE);
								updateContrato.setParameter(5, codigoPostal, StringType.INSTANCE);
								updateContrato.setParameter(6, direccion, StringType.INSTANCE);
								updateContrato.setParameter(7, documento, StringType.INSTANCE);
								updateContrato.setParameter(8, documentoTipo, LongType.INSTANCE);
								updateContrato.setParameter(9, equipo, StringType.INSTANCE);
								updateContrato.setParameter(10, fechaFinContrato, DateType.INSTANCE);
								updateContrato.setParameter(11, localidad, StringType.INSTANCE);
								updateContrato.setParameter(12, nombre, StringType.INSTANCE);
								updateContrato.setParameter(13, numeroCliente, LongType.INSTANCE);
								updateContrato.setParameter(14, numeroContrato, LongType.INSTANCE);
								updateContrato.setParameter(15, observaciones, StringType.INSTANCE);
								updateContrato.setParameter(16, tipoContratoCodigo, StringType.INSTANCE);
								updateContrato.setParameter(17, tipoContratoDescripcion, StringType.INSTANCE);
								updateContrato.setParameter(18, contratoId, LongType.INSTANCE);
								
								updateContrato.executeUpdate();
							} else {
								// Si no, creo el contrato.
								insertContrato.setParameter(9, agente, StringType.INSTANCE);
								insertContrato.setParameter(10, codigoPostal, StringType.INSTANCE);
								insertContrato.setParameter(11, direccion, StringType.INSTANCE);
								insertContrato.setParameter(12, documento, StringType.INSTANCE);
								insertContrato.setParameter(13, documentoTipo, LongType.INSTANCE);
								insertContrato.setParameter(14, equipo, StringType.INSTANCE);
								insertContrato.setParameter(15, fechaFinContrato, DateType.INSTANCE);
								insertContrato.setParameter(16, localidad, StringType.INSTANCE);
								insertContrato.setParameter(17, mid, LongType.INSTANCE);
								insertContrato.setParameter(18, nombre, StringType.INSTANCE);
								insertContrato.setParameter(19, numeroCliente, LongType.INSTANCE);
								insertContrato.setParameter(20, numeroContrato, LongType.INSTANCE);
								insertContrato.setParameter(21, observaciones, StringType.INSTANCE);
								insertContrato.setParameter(22, tipoContratoCodigo, StringType.INSTANCE);
								insertContrato.setParameter(23, tipoContratoDescripcion, StringType.INSTANCE);
								
								insertContrato.executeUpdate();
							}
						}
					
						successful++;
					}
				}
			}
			
			// Ruteo los contratos recién creados.
			insertContratoRoutingHistory.executeUpdate();
			
			result = 
				"Líneas procesadas con éxito: " + successful + ".|"
				+ "Líneas con datos incorrectos: " + errors + ".";
			
			if (errors > 0) {
				procesoImportacionManaged.setEstadoProcesoImportacion(estadoProcesoImportacionFinalizadoConErrores);
			} else {
				procesoImportacionManaged.setEstadoProcesoImportacion(estadoProcesoImportacionFinalizadoOK);
			}
			
			hoy = GregorianCalendar.getInstance().getTime();
			
			procesoImportacionManaged.setFact(hoy);
			procesoImportacionManaged.setFechaFin(hoy);
			procesoImportacionManaged.setObservaciones(result);
			
			iProcesoImportacionBean.update(procesoImportacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Importa el archivo .csv de nombre fileName para la Empresa empresaId.
	 * Genera un Contrato (con estado "VENDIDO") por cada línea del archivo y los asigna a la "bandeja" de Supervisores de Back-office de la Empresa empresaId.
	 *  
	 * @param fileName El nombre del archivo a Importar.
	 * @param emmpresaId ID de la empresa a la cual asignar los contratos importados.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String con el resultado de la operación.
	 */
	public String procesarArchivoVentasANTELEmpresa(String fileName, Long empresaId, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Estado estado = 
				iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId, false);
			
			TipoProcesoImportacion tipoProcesoImportacion =
				iTipoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("tipoProcesoImportacion.VentasANTEL")));
			
			EstadoProcesoImportacion estadoProcesoImportacionInicio = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.Inicio")));
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoOK = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoOK")));		
			
			EstadoProcesoImportacion estadoProcesoImportacionFinalizadoConErrores = 
				iEstadoProcesoImportacionBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estadoProcesoImportacion.FinalizadoConErrores")));
			
			Usuario usuario = iUsuarioBean.getById(loggedUsuarioId, false);
			
			ProcesoImportacion procesoImportacion = new ProcesoImportacion();
			procesoImportacion.setEstadoProcesoImportacion(estadoProcesoImportacionInicio);
			procesoImportacion.setFechaInicio(hoy);
			procesoImportacion.setNombreArchivo(fileName);
			procesoImportacion.setTipoProcesoImportacion(tipoProcesoImportacion);
			procesoImportacion.setUsuario(usuario);
			
			procesoImportacion.setFcre(hoy);
			procesoImportacion.setFact(hoy);
			procesoImportacion.setTerm(Long.valueOf(1));
			procesoImportacion.setUact(loggedUsuarioId);
			procesoImportacion.setUcre(loggedUsuarioId);
			
			ProcesoImportacion procesoImportacionManaged = iProcesoImportacionBean.save(procesoImportacion);
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			NativeQuery<Tuple> selectId = 
				hibernateSession.createNativeQuery(
					"SELECT nextval('hibernate_sequence')", Tuple.class
				);
			
			NativeQuery<?> insertContrato = hibernateSession.createNativeQuery(
				"INSERT INTO contrato("
					+ " numero_tramite,"
					+ " random,"
					+ " empresa_id,"
					+ " estado_id,"
					+ " rol_id,"
					+ " tipo_producto_id,"
					+ " nuevo_plan_id,"
					+ " forma_pago_id,"
					+ " tipo_tasa_interes_efectiva_anual_id,"
					+ " fcre,"
					+ " ucre,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					
					+ " id,"
					+ " numero_factura,"
					+ " tipo_documento_id,"
					+ " documento,"
					+ " nombre,"
					+ " apellido,"
					+ " telefono_contacto,"
					+ " direccion_entrega_calle,"
					+ " direccion_entrega_observaciones,"
					+ " marca_id,"
					+ " modelo_id,"
					+ " moneda_id,"
					+ " precio,"
					+ " cuotas,"
					+ " intereses,"
					+ " gastos_administrativos,"
					+ " valor_cuota,"
					+ " valor_unidad_indexada,"
					+ " gastos_concesion,"
					+ " gastos_administrativos_totales,"
					+ " valor_tasa_interes_efectiva_anual,"
					+ " antel_nro_trn,"
					+ " antel_forma_pago,"
					+ " antel_nro_servicio_cuenta,"
					+ " antel_importe,"
					+ " fecha_venta,"
					+ " observaciones"
				+ " ) VALUES ("
					+ " nextval('numero_tramite_sequence'),"
					+ " CAST(random() * 1000000 AS integer),"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ " )"
			);
			
			insertContrato.setParameter(1, empresa.getId(), LongType.INSTANCE);
			insertContrato.setParameter(2, estado.getId(), LongType.INSTANCE);
			insertContrato.setParameter(3, rolSupervisorCallCenter.getId(), LongType.INSTANCE);
			
			Long tipoProductoId = Long.parseLong(Configuration.getInstance().getProperty("tipoProducto.ANTEL"));
			insertContrato.setParameter(4, tipoProductoId, LongType.INSTANCE);
			
			Long nuevoPlanId = Long.parseLong(Configuration.getInstance().getProperty("plan.SinPlan"));
			insertContrato.setParameter(5, nuevoPlanId, LongType.INSTANCE);
			
			Long formaPagoNuestroCreditoId = Long.parseLong(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
			insertContrato.setParameter(6, formaPagoNuestroCreditoId, LongType.INSTANCE);
			
			Long tipoTasaInteresEfectivaAnualANTELId = Long.parseLong(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.tipoTasaInteresEfectivaAnualANTEL"));
			insertContrato.setParameter(7, tipoTasaInteresEfectivaAnualANTELId, LongType.INSTANCE);
			
			insertContrato.setParameter(8, hoy, TimestampType.INSTANCE);
			insertContrato.setParameter(9, Long.valueOf(1), LongType.INSTANCE);
			insertContrato.setParameter(10, hoy, TimestampType.INSTANCE);
			insertContrato.setParameter(11, Long.valueOf(1), LongType.INSTANCE);
			insertContrato.setParameter(12, loggedUsuarioId, LongType.INSTANCE);
			
			NativeQuery<Tuple> selectModelo = hibernateSession.createNativeQuery(
				"SELECT id, marca_id"
				+ " FROM modelo"
				+ " WHERE descripcion = ?"
				+ " AND fecha_baja IS NULL",
				Tuple.class
			);
			
			NativeQuery<Tuple> selectMoneda = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM moneda"
				+ " WHERE codigo_iso = ?",
				Tuple.class
			);
			
			NativeQuery<Tuple> selectTipoDocumento = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM tipo_documento"
				+ " WHERE abreviacion = ?",
				Tuple.class
			);
			
			NativeQuery<Tuple> selectContrato = hibernateSession.createNativeQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE empresa_id = ?"
				+ " AND antel_nro_trn = ?",
				Tuple.class
			);
			
			NativeQuery<?> insertContratoRoutingHistory = hibernateSession.createNativeQuery(
				"INSERT INTO contrato_routing_history("
					+ " id,"
					+ " fecha,"
					+ " empresa_id,"
					+ " usuario_id,"
					+ " rol_id,"
					+ " ucre,"
					+ " uact,"
					+ " fact,"
					+ " fcre,"
					+ " term,"
					+ " contrato_id,"
					+ " estado_id"
				+ " ) SELECT nextval('hibernate_sequence'),"
					+ " c.fact,"
					+ " c.empresa_id,"
					+ " null,"
					+ " c.rol_id,"
					+ " c.ucre,"
					+ " c.uact,"
					+ " c.fact,"
					+ " c.fcre,"
					+ " c.term,"
					+ " c.id,"
					+ " c.estado_id"
				+ " FROM contrato c"
				+ " LEFT OUTER JOIN contrato_routing_history crh ON crh.contrato_id = c.id"
				+ " WHERE c.fact = ?"
				+ " AND crh.id IS NULL"
			);
			
			insertContratoRoutingHistory.setParameter(1, hoy, TimestampType.INSTANCE);
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				if (lineNumber > 1) {
					String[] fields = line.split(";", -1);
					
					if (fields.length < 24) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Cantidad de columnas (" + fields.length + ") insuficientes."
						);
						errors++;
					} else {
						boolean ok = true;
						
	//					Nro Trn
						String numeroTrn = (fields[0] != null && !fields[0].equals("")) ? fields[0].trim() : null;
						
	//					Nro Trn Ref
	//					String nroTrnRef = (fields[1] != null && !fields[1].equals("")) ? fields[1].trim() : null;
						
	//					Nro Remito
	//					String nroRemito = (fields[2] != null && !fields[2].equals("")) ? fields[2].trim() : null;
						
	//					Fecha de Entrega
						Date fechaEntrega = null;
						try {
							fechaEntrega = 
								(fields[3] != null && !fields[3].equals("")) ? simpleDateFormat.parse(fields[3].trim()) : null;	
						} catch (ParseException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fechaEntrega incorrecto -> " + fields[3].trim()
							);
							ok = false;
						}
						
	//					ACCION
	//					String accion = (fields[4] != null && !fields[4].equals("")) ? fields[4].trim() : null;
						
	//					Concepto - Código
						String conceptoCodigo = (fields[5] != null && !fields[5].equals("")) ? fields[5].trim() : null;
						
						Long modeloId = null;
						Long marcaId = null;
						if (conceptoCodigo != null) {
							selectModelo.setParameter(1, conceptoCodigo, StringType.INSTANCE);
							
							List<Tuple> resultSelectModelo = selectModelo.list();
							if (resultSelectModelo.size() > 0) {
								Tuple tuple = (Tuple) resultSelectModelo.get(0);
								
								modeloId = Long.valueOf((Integer) tuple.get(0));
								marcaId = Long.valueOf((Integer) tuple.get(1));
							}
						}
						
	//					Concepto  - Descripción
	//					String conceptoDescripcion = (fields[6] != null && !fields[6].equals("")) ? fields[6].trim() : null;
						
	//					Concepto - Cant Uni
	//					Long conceptoCantidadUni = null;
	//					try {
	//						conceptoCantidadUni =
	//							(fields[7] != null && !fields[7].equals("")) ? Long.parseLong(fields[7].trim()) : null;
	//					} catch (Exception e) {
	//						System.err.println(
	//							"Error al procesar archivo: " + fileName + "."
	//							+ " Formato de línea " + lineNumber + " incompatible."
	//							+ " Campo conceptoCantidadUni incorrecto -> " + fields[7].trim()
	//						);
	//						ok = false;
	//					}
						
	//					Entrega - Contacto - Doc
						String entregaContactoDoc = (fields[8] != null && !fields[8].equals("")) ? fields[8].trim() : null;
						
						String tipoDocumentoAbreviacion = null;
						String documento = null;
						if (entregaContactoDoc != null) {
							String[] tokens = entregaContactoDoc.split(" ");
							if (tokens.length >= 2) {
								tipoDocumentoAbreviacion = tokens[0];
								documento = tokens[1];
							}
						}
						
						Long tipoDocumentoId = null;
						if (tipoDocumentoAbreviacion != null) {
							selectTipoDocumento.setParameter(1, tipoDocumentoAbreviacion, StringType.INSTANCE);
							
							List<Tuple> resultSelectTipoDocumento = selectTipoDocumento.list();
							if (resultSelectTipoDocumento.size() > 0) {
								tipoDocumentoId = Long.valueOf((Integer) resultSelectTipoDocumento.get(0).get(0));
							}
						}
						
	//					Entrega - Contacto - Nombre
						String entregaContactoNombre = (fields[9] != null && !fields[9].equals("")) ? fields[9].trim() : null;
						
	//					Entrega - Dirección
						String entregaDireccion = (fields[10] != null && !fields[10].equals("")) ? fields[10].trim() : null;
						
	//					Entrega - Telefono
						String entregaTelefono = (fields[11] != null && !fields[11].equals("")) ? fields[11].trim() : null;
						
	//					Entrega - Horario
						String entregaHorario = (fields[12] != null && !fields[12].equals("")) ? fields[12].trim() : null;
						
	//					Entrega - Obs
						String entregaObs = (fields[13] != null && !fields[13].equals("")) ? fields[13].trim() : null;
						
	//					Doc - Fecha Emisión
						Date fechaEmision = null;
						try {
							fechaEmision = 
								(fields[14] != null && !fields[14].equals("")) ? simpleDateFormat.parse(fields[14].trim()) : null;	
						} catch (ParseException pe) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo fechaEmision incorrecto -> " + fields[14].trim()
							);
							ok = false;
						}
						
	//					Doc - Tipo Doc
	//					String docTipoDoc = (fields[15] != null && !fields[15].equals("")) ? fields[15].trim() : null;
						
	//					Doc - Serie
						String docSerie = (fields[16] != null && !fields[16].equals("")) ? fields[16].trim() : null;
						
	//					Doc - Numero
						String docNumero = (fields[17] != null && !fields[17].equals("")) ? fields[17].trim() : null;
						
	//					Doc - Forma de Pago
						String docFormaPago = (fields[18] != null && !fields[18].equals("")) ? fields[18].trim() : null;
						
	//					NC - Factura Referencia
	//					String ncFacturaReferencia = (fields[19] != null && !fields[19].equals("")) ? fields[19].trim() : null;
						
	//					Cargo - Nro Servicio/Cuenta
						String cargoNroServicioCuenta = (fields[20] != null && !fields[20].equals("")) ? fields[20].trim() : null;
						
	//					Cargo - Cant. Cuotas
						Long cargoCantCuotas = null;
						try {
							String cantCuotasString = (fields[21] != null && !fields[21].equals("")) ? fields[21].trim() : null;
							if (cantCuotasString != null) {
								String[] tokens = cantCuotasString.split(" ");
								if (tokens.length > 0) {
									cargoCantCuotas = Long.parseLong(tokens[0]);
								}
							}
						} catch (Exception e) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo cargoCantCuotas incorrecto -> " + fields[21].trim()
							);
							ok = false;
						}
						
	//					Cargo - Importe Total
						Double cargoImporteTotal = null;
						try {
							cargoImporteTotal =
								(fields[22] != null && !fields[22].equals("")) ? Double.valueOf(fields[22].trim()) : null;
						} catch (Exception e) {
							System.err.println(
								"Error al procesar archivo: " + fileName + "."
								+ " Formato de línea " + lineNumber + " incompatible."
								+ " Campo cargoImporteTotal incorrecto -> " + fields[22].trim()
							);
							ok = false;
						}
						
	//					Cargo - Moneda
						String cargoMoneda = (fields[23] != null && !fields[23].equals("")) ? fields[23].trim() : null;
						
						Long monedaId = null;
						if (cargoMoneda != null) {
							selectMoneda.setParameter(1, cargoMoneda, StringType.INSTANCE);
							
							List<Tuple> resultSelectMoneda = selectMoneda.list();
							if (resultSelectMoneda.size() > 0) {
								monedaId = Long.valueOf((Integer) resultSelectMoneda.get(0).get(0));
							}
						}
						
						String observaciones = 
							"Emitido: " + (fechaEmision != null ? simpleDateFormat.format(fechaEmision) : "N/D") + ", Fecha de entrega: " + (fechaEntrega != null ? simpleDateFormat.format(fechaEntrega) : "N/D") + ", Horario: " + entregaHorario;
						
						TipoProducto tipoProducto = new TipoProducto();
						tipoProducto.setId(tipoProductoId);
						
						Marca marca = new Marca();
						marca.setId(marcaId);
						
						Modelo modelo = new Modelo();
						modelo.setId(modeloId);
						
						Moneda moneda = new Moneda();
						moneda.setId(monedaId);
						
						Precio precio = iPrecioBean.getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
							empresa, 
							tipoProducto, 
							marca, 
							modelo, 
							moneda,
							cargoCantCuotas
						);
						
						TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual = new TipoTasaInteresEfectivaAnual();
						tipoTasaInteresEfectivaAnual.setId(
							Long.parseLong(Configuration.getInstance().getProperty(
								"financiacion.creditoDeLaCasa.tipoTasaInteresEfectivaAnualANTEL"
							))
						);
						
						DatosFinanciacion datosFinanciacion = null;
						if (precio != null) {
							datosFinanciacion = 
								iFinanciacionBean.calcularFinanciacion(
									moneda,
									tipoTasaInteresEfectivaAnual,
									// Se calcula el importe en base al precio definido y no según el dato del archivo.
									// cargoImporteTotal,
									precio.getPrecio(),
									cargoCantCuotas
								);
						}
						
						if (!ok) {
							errors++;
						} else {
							// Busco si el numeroTRN ya existe.
							selectContrato.setParameter(1, empresaId, LongType.INSTANCE);
							selectContrato.setParameter(2, numeroTrn, StringType.INSTANCE);
							
							List<Tuple> listContrato = selectContrato.list();
							if (listContrato.size() > 0) {
								// Si el antelNroTrn existe, omito.
							} else {
								Long id = Long.valueOf(((BigInteger) selectId.list().get(0).get(0)).longValue());
								
								// Si no, creo el contrato.
								
								insertContrato.setParameter(13, id, LongType.INSTANCE);
								insertContrato.setParameter(14, docSerie + "-" + docNumero, StringType.INSTANCE);
								insertContrato.setParameter(15, tipoDocumentoId, LongType.INSTANCE);
								insertContrato.setParameter(16, documento, StringType.INSTANCE);
								insertContrato.setParameter(17, entregaContactoNombre, StringType.INSTANCE);
								insertContrato.setParameter(18, entregaContactoNombre, StringType.INSTANCE);
								insertContrato.setParameter(19, entregaTelefono, StringType.INSTANCE);
								insertContrato.setParameter(20, entregaDireccion, StringType.INSTANCE);
								insertContrato.setParameter(21, entregaObs, StringType.INSTANCE);
								insertContrato.setParameter(22, marcaId, LongType.INSTANCE);
								insertContrato.setParameter(23, modeloId, LongType.INSTANCE);
								insertContrato.setParameter(24, monedaId, LongType.INSTANCE);
								if (precio != null) {
									insertContrato.setParameter(25, precio.getPrecio(), DoubleType.INSTANCE);
								} else {
									insertContrato.setParameter(25, null, DoubleType.INSTANCE);
								}
								insertContrato.setParameter(26, cargoCantCuotas, LongType.INSTANCE);
								if (datosFinanciacion != null) {
									insertContrato.setParameter(27, datosFinanciacion.getIntereses(), DoubleType.INSTANCE);
									insertContrato.setParameter(28, datosFinanciacion.getGastosAdministrativos(), DoubleType.INSTANCE);
									insertContrato.setParameter(29, datosFinanciacion.getMontoCuota(), DoubleType.INSTANCE);
									insertContrato.setParameter(30, datosFinanciacion.getValorUnidadIndexada(), DoubleType.INSTANCE);
									insertContrato.setParameter(31, datosFinanciacion.getGastosConcesion(), DoubleType.INSTANCE);
									insertContrato.setParameter(32, datosFinanciacion.getGastosAdministrativosTotales(), DoubleType.INSTANCE);
									insertContrato.setParameter(33, datosFinanciacion.getValorTasaInteresEfectivaAnual(), DoubleType.INSTANCE);
								} else {
									insertContrato.setParameter(27, null, DoubleType.INSTANCE);
									insertContrato.setParameter(28, null, DoubleType.INSTANCE);
									insertContrato.setParameter(29, null, DoubleType.INSTANCE);
									insertContrato.setParameter(30, null, DoubleType.INSTANCE);
									insertContrato.setParameter(31, null, DoubleType.INSTANCE);
									insertContrato.setParameter(32, null, DoubleType.INSTANCE);
									insertContrato.setParameter(33, null, DoubleType.INSTANCE);
								}
								insertContrato.setParameter(34, numeroTrn, StringType.INSTANCE);
								insertContrato.setParameter(35, docFormaPago, StringType.INSTANCE);
								insertContrato.setParameter(36, cargoNroServicioCuenta, StringType.INSTANCE);
								insertContrato.setParameter(37, cargoImporteTotal, DoubleType.INSTANCE);
								insertContrato.setParameter(38, fechaEmision, DateType.INSTANCE);
								insertContrato.setParameter(39, observaciones, StringType.INSTANCE);
								
								insertContrato.executeUpdate();
								
								StockTipoMovimiento stockTipoMovimiento = iStockTipoMovimientoBean.getById(
									Long.parseLong(Configuration.getInstance().getProperty("stockTipoMovimiento.Venta"))
								);
								
								StockMovimiento stockMovimiento = new StockMovimiento();
								stockMovimiento.setCantidad(Long.valueOf(1) * stockTipoMovimiento.getSigno());
								stockMovimiento.setDocumentoId(id);
								stockMovimiento.setFecha(hoy);
								
								stockMovimiento.setEmpresa(empresa);
								stockMovimiento.setMarca(marca);
								stockMovimiento.setModelo(modelo);
								stockMovimiento.setTipoProducto(tipoProducto);
								stockMovimiento.setStockTipoMovimiento(stockTipoMovimiento);
								
								stockMovimiento.setFcre(hoy);
								stockMovimiento.setFact(hoy);
								stockMovimiento.setTerm(Long.valueOf(1));
								stockMovimiento.setUact(loggedUsuarioId);
								stockMovimiento.setUcre(loggedUsuarioId);
								
								iStockMovimientoBean.save(stockMovimiento);
							}
							
							successful++;
						}
					}
				}
			}
			
			// Ruteo los contratos recién creados.
			insertContratoRoutingHistory.executeUpdate();
			
			result = 
				"Líneas procesadas con éxito: " + successful + ".|"
				+ "Líneas con datos incorrectos: " + errors + ".";
			
			if (errors > 0) {
				procesoImportacionManaged.setEstadoProcesoImportacion(estadoProcesoImportacionFinalizadoConErrores);
			} else {
				procesoImportacionManaged.setEstadoProcesoImportacion(estadoProcesoImportacionFinalizadoOK);
			}
			
			hoy = GregorianCalendar.getInstance().getTime();
			
			procesoImportacionManaged.setFact(hoy);
			procesoImportacionManaged.setFechaFin(hoy);
			procesoImportacionManaged.setObservaciones(result);
			
			iProcesoImportacionBean.update(procesoImportacionManaged);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Asigna un Contrato (con estado "LLAMAR") a la "bandeja" de Supervisores de Call-Center de la Empresa empresaId.
	 * 
	 * @param empresaId ID de la empresa a la cual asignar el Contrato.
	 * @param contrato El Contrato a asignar.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return String con el resultado de la operación.
	 */
	public String addAsignacionManual(Long empresaId, Contrato contrato, Long loggedUsuarioId) {
		String result = null;
		
		try {
			Long empresaANBELId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANBEL")
				);
			
			Long empresaANTELPolo1Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo1")
				);
			Long empresaANTELPolo2Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo2")
				);
			
			Long empresaROCHEId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ROCHE")
				);
			
			Long empresaXiaomiId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.Xiaomi")
				);
			
			Empresa empresa = iEmpresaBean.getById(empresaId, false);
			
			Rol rolVendedor = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")),
					false
				);
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Usuario uact =
				iUsuarioBean.getById(loggedUsuarioId, false);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			gregorianCalendar.add(
				GregorianCalendar.MONTH, 
				-1 * Integer.parseInt(Configuration.getInstance().getProperty("cantidadMesesRetencionVenta"))
			);
			
			// Se admite volver a vender contratos transcurridos 6 meses de la última venta. 
			Date fechaVentaLimite = gregorianCalendar.getTime();
			
			Estado estado = iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Long estadoVendidoId = 
				Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			Long estadoNoFirmaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.NOFIRMA"));
			Long estadoFacturaImpagaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FACTURAIMPAGA"));
			
			TypedQuery<Contrato> queryVendidos = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id IN ("
						+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
						+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId, :estadoACMId,"
						+ " :estadoReagendarId, :estadoNoFirmaId, :estadoFacturaImpagaId"
					+ " )"
					+ " AND c.mid = :mid"
					+ " AND c.fechaVenta > :fechaVentaLimite", 
					Contrato.class
				);
			queryVendidos.setParameter("estadoVendidoId", estadoVendidoId);
			queryVendidos.setParameter("estadoDistribuirId", estadoDistribuirId);
			queryVendidos.setParameter("estadoActivarId", estadoActivarId);
			queryVendidos.setParameter("estadoFaltaDocumentacionId", estadoFaltaDocumentacionId);
			queryVendidos.setParameter("estadoRecoordinarId", estadoRecoordinarId);
			queryVendidos.setParameter("estadoActDocVentaId", estadoActDocVentaId);
			queryVendidos.setParameter("estadoRedistribuirId", estadoRedistribuirId);
			queryVendidos.setParameter("estadoControlAntelId", estadoControlAntelId);
			queryVendidos.setParameter("estadoACMId", estadoACMId);
			queryVendidos.setParameter("estadoReagendarId", estadoReagendarId);
			queryVendidos.setParameter("estadoNoFirmaId", estadoNoFirmaId);
			queryVendidos.setParameter("estadoFacturaImpagaId", estadoFacturaImpagaId);
			queryVendidos.setParameter("mid", contrato.getMid());
			queryVendidos.setParameter("fechaVentaLimite", fechaVentaLimite);
			
			TypedQuery<Contrato> queryCallCenter =
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id = :estadoLlamarId"
					+ " AND c.mid = :mid"
					+ " AND c.empresa.id = :empresaId", 
					Contrato.class
				);
			queryCallCenter.setParameter("estadoLlamarId", estado.getId());
			queryCallCenter.setParameter("mid", contrato.getMid());
			queryCallCenter.setParameter("empresaId", empresaId);
			
			boolean omitirControlVendidos = 
				empresaId.equals(empresaANTELPolo1Id) 
				|| empresaId.equals(empresaANTELPolo2Id)
				|| empresaId.equals(empresaROCHEId)
				|| empresaId.equals(empresaXiaomiId);
			
			List<Contrato> resultList = queryVendidos.getResultList();
			if (!omitirControlVendidos && resultList.size() > 0) {
				result = ";El MID ya fue vendido. No se puede asignar.";
			} else {
				contrato.setEmpresa(empresa);
				contrato.setEstado(estado);
				contrato.setRol(rolSupervisorCallCenter);
				
				if (empresaId.equals(empresaANBELId) || omitirControlVendidos) {
					contrato.setRol(rolVendedor);
					contrato.setUsuario(uact);
					
					if (omitirControlVendidos) {
						ModalidadVenta modalidadVentaTelemarketing = new ModalidadVenta();
						modalidadVentaTelemarketing.setId(
							Long.decode(Configuration.getInstance().getProperty("modalidadVenta.Telemarketing"))
						);
						
						Plan planSinPlan = new Plan();
						planSinPlan.setId(
							Long.decode(Configuration.getInstance().getProperty("plan.SinPlan"))
						);
						
						TipoProducto tipoProductoANTEL = new TipoProducto();
						tipoProductoANTEL.setId(
							Long.decode(Configuration.getInstance().getProperty("tipoProducto.ANTEL"))
						);
						
						Marca marcaSinMarca = new Marca();
						marcaSinMarca.setId(
							Long.decode(Configuration.getInstance().getProperty("marca.SinMarca"))
						);
						
						Modelo modeloSinModelo = new Modelo();
						modeloSinModelo.setId(
							Long.decode(Configuration.getInstance().getProperty("modelo.SinEquipo"))
						);
						
						Producto productoEquipoX = new Producto();
						productoEquipoX.setId(
							Long.decode(Configuration.getInstance().getProperty("producto.SinEquipo"))
						);
						
						Moneda monedaPesosUruguayos = new Moneda();
						monedaPesosUruguayos.setId(
							Long.decode(Configuration.getInstance().getProperty("moneda.PesosUruguayos"))
						);
						
						MotivoCambioPlan motivoCambioPlanRenovacion = new MotivoCambioPlan();
						motivoCambioPlanRenovacion.setId(
							Long.decode(Configuration.getInstance().getProperty("motivoCambioPlan.Renovacion"))
						);
						
						FormaPago formaPagoSinPago = new FormaPago();
						formaPagoSinPago.setId(
							Long.decode(Configuration.getInstance().getProperty("formaPago.SinPago"))
						);
						
						GregorianCalendar gregorianCalendarDefault = new GregorianCalendar();
						gregorianCalendarDefault.set(GregorianCalendar.YEAR, 1950);
						gregorianCalendarDefault.set(GregorianCalendar.MONTH, 1);
						gregorianCalendarDefault.set(GregorianCalendar.DAY_OF_MONTH, 1);
						
						Date fechaNacimientoDefault = gregorianCalendarDefault.getTime();
						
						Sexo sexoMasculino = new Sexo();
						sexoMasculino.setId(
							Long.decode(Configuration.getInstance().getProperty("sexo.Masculino"))
						);
						
						contrato.setModalidadVenta(modalidadVentaTelemarketing);
						contrato.setNuevoPlan(planSinPlan);
						contrato.setTipoProducto(tipoProductoANTEL);
						contrato.setMarca(marcaSinMarca);
						contrato.setModelo(modeloSinModelo);
						contrato.setProducto(productoEquipoX);
						contrato.setCostoEnvio(Double.valueOf(0));
						contrato.setMoneda(monedaPesosUruguayos);
						contrato.setMotivoCambioPlan(motivoCambioPlanRenovacion);
						contrato.setFormaPago(formaPagoSinPago);
						contrato.setFechaNacimiento(fechaNacimientoDefault);
						contrato.setSexo(sexoMasculino);
						contrato.setEmail("-");
						contrato.setNumeroFactura("1");
						contrato.setNumeroSerie("1");
						contrato.setNumeroChip("1");
					}
				}
				
				if (!omitirControlVendidos) {
					List<Contrato> resultListCallCenter = queryCallCenter.getResultList();
				
					if (resultListCallCenter.size() > 0) {
						Contrato contratoAnterior = resultListCallCenter.get(0);
						
						contrato.setId(contratoAnterior.getId());
						contrato.setNumeroTramite(contratoAnterior.getNumeroTramite());
					}
				}
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				Contrato contratoManaged = this.update(contrato);
				
				ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
				contratoRoutingHistoryNew.setContrato(contratoManaged);
				contratoRoutingHistoryNew.setEmpresa(empresa);
				contratoRoutingHistoryNew.setEstado(contratoManaged.getEstado());
				contratoRoutingHistoryNew.setFecha(hoy);
				contratoRoutingHistoryNew.setRol(rolSupervisorCallCenter);
				
				if (empresaId.equals(empresaANBELId) 
					|| empresaId.equals(empresaANTELPolo1Id) 
					|| empresaId.equals(empresaANTELPolo2Id)
					|| empresaId.equals(empresaROCHEId)) {
					contratoRoutingHistoryNew.setRol(rolVendedor);
					contratoRoutingHistoryNew.setUsuario(uact);
				}
				
				contratoRoutingHistoryNew.setFcre(hoy);
				contratoRoutingHistoryNew.setFact(hoy);
				contratoRoutingHistoryNew.setTerm(Long.valueOf(1));
				contratoRoutingHistoryNew.setUact(loggedUsuarioId);
				contratoRoutingHistoryNew.setUcre(loggedUsuarioId);
				
				entityManager.persist(contratoRoutingHistoryNew);
				
				result = contratoManaged.getId() + ";Operación exitosa.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Consulta si dentro del filtro a asignar existe algún registro ya asignado.
	 * 
	 * @param Criterios de la consulta.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return true sii la asignación se puede realizar.
	 */
	public boolean chequearAsignacion(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		boolean result = true;
		
		try {
			// Obtener el usuario para el cual se consulta
			Usuario usuario = iUsuarioBean.getById(loggedUsuarioId, true);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			
			Root<Contrato> root = criteriaQuery.from(Contrato.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
			subrootRolesSubordinados.alias("subrootRolesSubordinados");
			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.isNotNull(root.get("usuario")),
				criteriaBuilder.or(
					// Asignados al usuario.
					criteriaBuilder.equal(root.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario1")),
					// Asignados a algún rol subordinado dentro de la empresa
					criteriaBuilder.exists(
						subqueryRolesSubordinados
							.select(subrootRolesSubordinados)
							.where(
								criteriaBuilder.and(
									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario2")),
									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa"), root.get("empresa")),
									criteriaBuilder.isMember(root.get("rol").as(Rol.class), expressionRolesSubordinados)
								)
							)
					)
				)
			);
			
			criteriaQuery
				.select(criteriaBuilder.count(root.get("id")))
				.where(where);
	
			TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuario);
			query.setParameter("usuario2", usuario);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Contrato> field = root;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = root.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = root.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								query.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								query.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			result = query.getSingleResult() == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Consulta si dentro del filtro a asignar existen contratos relacionados.
	 * 
	 * @param Criterios de la consulta.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 * @return true sii la asignación se puede realizar.
	 */
	public boolean chequearRelacionesAsignacion(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		boolean result = true;
		
		try {
			// Obtener el usuario para el cual se consulta
			Usuario usuario = iUsuarioBean.getById(loggedUsuarioId, true);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			
			Root<Contrato> root = criteriaQuery.from(Contrato.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
			subrootRolesSubordinados.alias("subrootRolesSubordinados");
			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
			
			Subquery<ContratoRelacion> subqueryContratoRelacion = criteriaQuery.subquery(ContratoRelacion.class);
			Root<ContratoRelacion> subrootContratoRelacion = subqueryContratoRelacion.from(ContratoRelacion.class);
			subrootContratoRelacion.alias("subrootContratoRelacion");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryContratoRelacion
						.select(subrootContratoRelacion)
						.where(
							criteriaBuilder.equal(
								subrootContratoRelacion.get("contrato").get("id"), root.get("id")
							)
						)
				),
				criteriaBuilder.or(
					// Asignados al usuario.
					criteriaBuilder.equal(root.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario1")),
					// Asignados a algún rol subordinado dentro de la empresa
					criteriaBuilder.exists(
						subqueryRolesSubordinados
							.select(subrootRolesSubordinados)
							.where(
								criteriaBuilder.and(
									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario"), criteriaBuilder.parameter(Usuario.class, "usuario2")),
									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa"), root.get("empresa")),
									criteriaBuilder.isMember(root.get("rol").as(Rol.class), expressionRolesSubordinados)
								)
							)
					)
				)
			);
			
			criteriaQuery
				.select(criteriaBuilder.count(root.get("id")))
				.where(where);
	
			TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("usuario1", usuario);
			query.setParameter("usuario2", usuario);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Contrato> field = root;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = root.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = root.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								query.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								query.setParameter(
									"p" + i,
									Long.parseLong(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									Double.parseDouble(valor)
								);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					if (metadataCondicion.getValores().size() == 0) {
						i++;
					}
				}
			}
			
			result = query.getSingleResult() == 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Asigna los Contratos que cumplen con los criterios especificados al Usuario y Rol especificados y actualiza su Estado según parámetro.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param rol Rol a asignar los Contratos.
	 * @param estado Estado a actualizar en los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public void asignar(Usuario usuario, Rol rol, Estado estado, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		try {
			Collection<Contrato> resultList = new LinkedList<Contrato>();
			if (metadataConsulta.getTamanoSubconjunto() != null) {
				metadataConsulta.setTamanoMuestra(metadataConsulta.getTamanoSubconjunto());
				
				List<Contrato> toOrder = new LinkedList<Contrato>();
				for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
					toOrder.add((Contrato) object);
				}
				
				Collections.sort(toOrder, new Comparator<Contrato>() {
					public int compare(Contrato arg0, Contrato arg1) {
						Random random = new Random();
						
						return random.nextBoolean() ? 1 : -1;
					}
				});
				
				int i = 0;
				for (Contrato contrato : toOrder) {
					resultList.add(contrato);
					
					i++;
					if (i == metadataConsulta.getTamanoSubconjunto()) {
						break;
					}
				}
			} else {
				metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
				
				for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
					resultList.add((Contrato) object);
				}
			}
			
			Date currentDate = GregorianCalendar.getInstance().getTime();
			
			Long rolDistribuidorId = 
				Long.parseLong(Configuration.getInstance().getProperty("rol.Distribuidor"));
			
			Long estadoRecoordinarId = 
				Long.parseLong(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			
			Long estadoFaltaDocumentacionId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			
			Long estadoVendidoPorOtraEmpresaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDOPOROTRAEMPRESA"));
			
			Long empresaANTELPolo1Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo1")
				);
			Long empresaANTELPolo2Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo2")
				);
			
			Long empresaXiaomiId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.Xiaomi")
				);
				
			Random random = new Random();
			
			for (Contrato contrato : resultList) {
				boolean omitirControlVendidos = 
					contrato.getEmpresa().getId().equals(empresaANTELPolo1Id) 
					|| contrato.getEmpresa().getId().equals(empresaANTELPolo2Id)
					|| contrato.getEmpresa().getId().equals(empresaXiaomiId);
				
				Long diffInMilliseconds = 
					contrato.getFechaRechazo() != null ? (currentDate.getTime() - contrato.getFechaRechazo().getTime()) : 
						contrato.getFact() != null ? (currentDate.getTime() - contrato.getFact().getTime()) :
							null;
				
				Long cantidadDiasVendidoPorOtraEmpresa = 
					Long.parseLong(Configuration.getInstance().getProperty("cantidadDiasRetencionVendidoPorOtraEmpresa"));
				Long milisegundosSegundo = Long.valueOf(1000);
				Long segundosMinuto = Long.valueOf(60);
				Long minutosHora = Long.valueOf(60);
				Long horasDia = Long.valueOf(24);
				Long milisegundosDia = milisegundosSegundo * segundosMinuto * minutosHora * horasDia;
						
				if (!omitirControlVendidos
					&& (estadoVendidoPorOtraEmpresaId.equals(contrato.getEstado().getId()) 
						&& diffInMilliseconds != null
						&& (diffInMilliseconds / milisegundosDia < cantidadDiasVendidoPorOtraEmpresa)
					)
				) {
					// No asignar contratos vendidos por otra empresa en menos de 30 días.
				} else {
					if (!estadoFaltaDocumentacionId.equals(contrato.getEstado().getId())
						&& !estadoRecoordinarId.equals(contrato.getEstado().getId())) {
						contrato.setEstado(estado);
					}
					
					contrato.setRol(rol);
					contrato.setUsuario(usuario);
					
					contrato.setFact(currentDate);
					contrato.setTerm(Long.valueOf(1));
					contrato.setUact(loggedUsuarioId);
					contrato.setRandom(Long.valueOf(random.nextInt(1000000)));
					
					if (rol.getId().equals(rolDistribuidorId)) {
						contrato.setFechaEntregaDistribuidor(currentDate);
					}
					
					Contrato mergedContrato = entityManager.merge(contrato);
					
					ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
					contratoRoutingHistoryNew.setContrato(mergedContrato);
					contratoRoutingHistoryNew.setEmpresa(contrato.getEmpresa());
					contratoRoutingHistoryNew.setEstado(mergedContrato.getEstado());
					contratoRoutingHistoryNew.setFecha(currentDate);
					contratoRoutingHistoryNew.setRol(rol);
					contratoRoutingHistoryNew.setUsuario(usuario);
					
					contratoRoutingHistoryNew.setFact(currentDate);
					contratoRoutingHistoryNew.setTerm(Long.valueOf(1));
					contratoRoutingHistoryNew.setUact(loggedUsuarioId);
					
					entityManager.persist(contratoRoutingHistoryNew);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna el Contrato al Usuario y Rol especificados.
	 * 
	 * @param usuario Usuario a asignar el Contrato.
	 * @param rol Rol a asignar el Contrato.
	 * @param contrato Contrato a asignar.
	 * @param loggedUsuarioId ID del usuario que ejecuta la acción.
	 */
	public void asignar(Usuario usuario, Rol rol, Contrato contrato, Long loggedUsuarioId) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Random random = new Random();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			contratoManaged.setRandom(Long.valueOf(random.nextInt(1000000)));
			
			ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
			
			contratoRoutingHistoryNew.setFecha(date);
			contratoRoutingHistoryNew.setContrato(contratoManaged);
			contratoRoutingHistoryNew.setEmpresa(contratoManaged.getEmpresa());
			contratoRoutingHistoryNew.setEstado(contratoManaged.getEstado());
			contratoRoutingHistoryNew.setRol(rol);
			contratoRoutingHistoryNew.setUsuario(usuario);
			
			contratoRoutingHistoryNew.setFcre(date);
			contratoRoutingHistoryNew.setFact(date);
			contratoRoutingHistoryNew.setTerm(Long.valueOf(1));
			contratoRoutingHistoryNew.setUact(loggedUsuarioId);
			contratoRoutingHistoryNew.setUcre(loggedUsuarioId);
			
			entityManager.persist(contratoRoutingHistoryNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Vendedor) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarVentas(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioid) {
		Rol rolVendedor = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")),
				false
			);
		
		Estado estadoLlamar = 
			iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR")));
		
		this.asignar(usuario, rolVendedor, estadoLlamar, metadataConsulta, loggedUsuarioid);
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Back-office) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarBackoffice(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Rol rolBackoffice = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Backoffice")),
				false
			);
		
		Estado estadoVendido = 
			iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO")));
		
		this.asignar(usuario, rolBackoffice, estadoVendido, metadataConsulta, loggedUsuarioId);
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Distribuidor) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarDistribuidor(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Rol rolBackoffice = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Distribuidor")),
				false
			);
		
		Estado estadoDistribuir = 
			iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.DISTRIBUIR")));
		
		this.asignar(usuario, rolBackoffice, estadoDistribuir, metadataConsulta, loggedUsuarioId);
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Activador) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarActivador(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Rol rolBackoffice = 
			iRolBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("rol.Activador")),
				false
			);
		
		Estado estadoActivar = 
			iEstadoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR")));
		
		this.asignar(usuario, rolBackoffice, estadoActivar, metadataConsulta, loggedUsuarioId);
	}
	
	/**
	 * Consulta si la venta a realizar no se encuentra ya en estado VENDIDO.
	 * 
	 * @param Contrato a verificar.
	 * @return true sii la venta se puede realizar.
	 */
	public boolean validarVenta(Contrato contrato) {
		boolean result = false;
		
		try {
			Long empresaANTELPolo1Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo1")
				);
			Long empresaANTELPolo2Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo2")
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			gregorianCalendar.add(GregorianCalendar.MONTH, -1 * Integer.parseInt(Configuration.getInstance().getProperty("cantidadMesesRetencionVenta")));
			
			// Se admite volver a vender contratos transcurridos 6 meses de la última venta. 
			Date fechaVentaLimite = gregorianCalendar.getTime();
			
			Long estadoVendidoId = 
				Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			Long estadoNoFirmaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.NOFIRMA"));
			Long estadoFacturaImpagaId =
				Long.parseLong(Configuration.getInstance().getProperty("estado.FACTURAIMPAGA"));
			
			TypedQuery<Contrato> queryVendidos = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id IN ("
						+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
						+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId,"
						+ " :estadoACMId, :estadoReagendarId, :estadoNoFirmaId, :estadoFacturaImpagaId"
					+ " )"
					+ " AND c.mid = :mid"
					+ " AND c.fechaVenta > :fechaVentaLimite"
					+ " AND c.id <> :id",
					Contrato.class
				);
			queryVendidos.setParameter("estadoVendidoId", estadoVendidoId);
			queryVendidos.setParameter("estadoDistribuirId", estadoDistribuirId);
			queryVendidos.setParameter("estadoActivarId", estadoActivarId);
			queryVendidos.setParameter("estadoFaltaDocumentacionId", estadoFaltaDocumentacionId);
			queryVendidos.setParameter("estadoRecoordinarId", estadoRecoordinarId);
			queryVendidos.setParameter("estadoActDocVentaId", estadoActDocVentaId);
			queryVendidos.setParameter("estadoRedistribuirId", estadoRedistribuirId);
			queryVendidos.setParameter("estadoControlAntelId", estadoControlAntelId);
			queryVendidos.setParameter("estadoACMId", estadoACMId);
			queryVendidos.setParameter("estadoReagendarId", estadoReagendarId);
			queryVendidos.setParameter("estadoNoFirmaId", estadoNoFirmaId);
			queryVendidos.setParameter("estadoFacturaImpagaId", estadoFacturaImpagaId);
			queryVendidos.setParameter("mid", contrato.getMid());
			queryVendidos.setParameter("fechaVentaLimite", fechaVentaLimite);
			queryVendidos.setParameter("id", contrato.getId());
			
			result = contrato.getEmpresa().getId().equals(empresaANTELPolo1Id) 
				|| contrato.getEmpresa().getId().equals(empresaANTELPolo2Id)
				|| queryVendidos.getResultList().isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Actualiza el Contrato a estado "VENDIDO" y lo asigna a la "bandeja" de Supervisores de Back-office.
	 * Actualiza el Stock del producto e impide la venta del mismo número por otra empresa, pasando los demás Contratos a estado "VENDIDO POR OTRA EMPRESA".
	 * 
	 * @param contrato a agendar.
	 */
	public void agendar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorBackOffice")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Long empresaANTELPolo1Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo1")
				);
			Long empresaANTELPolo2Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo2")
				);
			
			Long empresaXiaomiId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.Xiaomi")
				);
			
			boolean omitirControlVendidos = 
				contrato.getEmpresa().getId().equals(empresaANTELPolo1Id) 
				|| contrato.getEmpresa().getId().equals(empresaANTELPolo2Id)
				|| contrato.getEmpresa().getId().equals(empresaXiaomiId);
			
			contrato.setFechaVenta(date);
			contrato.setVendedor(uact);
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			Contrato contratoManaged = this.update(contrato);
			
			StockTipoMovimiento stockTipoMovimiento = iStockTipoMovimientoBean.getById(
				Long.parseLong(Configuration.getInstance().getProperty("stockTipoMovimiento.Venta"))
			);
			
			StockMovimiento stockMovimiento = new StockMovimiento();
			stockMovimiento.setCantidad(Long.valueOf(1) * stockTipoMovimiento.getSigno());
			stockMovimiento.setDocumentoId(contrato.getId());
			stockMovimiento.setFecha(date);
			
			stockMovimiento.setEmpresa(contrato.getEmpresa());
			stockMovimiento.setMarca(contrato.getMarca());
			stockMovimiento.setModelo(contrato.getModelo());
			stockMovimiento.setTipoProducto(contrato.getTipoProducto());
			stockMovimiento.setStockTipoMovimiento(stockTipoMovimiento);
			
			stockMovimiento.setFcre(date);
			stockMovimiento.setFact(date);
			stockMovimiento.setTerm(Long.valueOf(1));
			stockMovimiento.setUact(uact.getId());
			stockMovimiento.setUcre(uact.getId());
			
			iStockMovimientoBean.save(stockMovimiento);
			
			this.asignar(null, rol, contratoManaged, contrato.getUact());
			
			TypedQuery<Contrato> queryOtrasEmpresas = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado IN ( :estadoLlamar, :estadoRellamar, :estadoRechazado, :estadoRenovado )"
					+ " AND c.empresa <> :empresa"
					+ " AND c.empresa.id <> :empresaANTELPolo1Id"
					+ " AND c.empresa.id <> :empresaANTELPolo2Id"
					+ " AND c.mid = :mid", 
					Contrato.class);
			
			Estado estadoLlamar = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.LLAMAR"))
				);
			
			Estado estadoRellamar = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RELLAMAR"))
				);
			
			Estado estadoRechazado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RECHAZADO"))
				);
			
			Estado estadoRenovado =
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RENOVADO"))
				);
			
			queryOtrasEmpresas.setParameter("estadoLlamar", estadoLlamar);
			queryOtrasEmpresas.setParameter("estadoRellamar", estadoRellamar);
			queryOtrasEmpresas.setParameter("estadoRechazado", estadoRechazado);
			queryOtrasEmpresas.setParameter("estadoRenovado", estadoRenovado);
			queryOtrasEmpresas.setParameter("empresa", contrato.getEmpresa());
			queryOtrasEmpresas.setParameter("mid", contrato.getMid());
			queryOtrasEmpresas.setParameter("empresaANTELPolo1Id", empresaANTELPolo1Id);
			queryOtrasEmpresas.setParameter("empresaANTELPolo2Id", empresaANTELPolo2Id);
			
			if (!omitirControlVendidos) {
				Estado estadoVendidoPorOtraEmpresa = 
					iEstadoBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDOPOROTRAEMPRESA"))
					);
				
				Rol rolGerenteDeEmpresa =
					iRolBean.getById(
						Long.parseLong(Configuration.getInstance().getProperty("rol.GerenteDeEmpresa")),
						false
					);
				
				for (Contrato contratoOtraEmpresa : queryOtrasEmpresas.getResultList()) {
					contratoOtraEmpresa.setEstado(estadoVendidoPorOtraEmpresa);
					contratoOtraEmpresa.setRol(rolGerenteDeEmpresa);
					contratoOtraEmpresa.setUsuario(null);
					contratoOtraEmpresa.setFechaRechazo(date);
					
					contratoOtraEmpresa.setFact(date);
					contratoOtraEmpresa.setTerm(Long.valueOf(1));
					contratoOtraEmpresa.setUact(uact.getId());
					
					Contrato contratoOtraEmpresaManaged = this.update(contratoOtraEmpresa);
					
					this.asignar(null, rolGerenteDeEmpresa, contratoOtraEmpresaManaged, uact.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "RECHAZADO" y lo asigna a la "bandeja" de Supervisores de Call-center.
	 * 
	 * @param contrato a rechazar.
	 */
	public void rechazar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RECHAZADO"))
				);
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Rol rolSupervisorBackoffice = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorBackOffice")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			if (contratoManaged.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")))) {
				// Si estaba asignado a un Vendedor lo dejamos en la "bandeja" de Supervisores de Call-center.
				
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato.setVendedor(contrato.getUsuario());
			} else if (contratoManaged.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Backoffice")))) {
				// Si estaba asignado a un Back-office lo dejamos en la "bandeja" de Supervisores de Back-office.
				
				contrato.setRol(rolSupervisorBackoffice);
				
				contrato.setFechaVenta(contratoManaged.getFechaVenta());
				contrato.setVendedor(contratoManaged.getVendedor());
				contrato.setFechaBackoffice(date);
				contrato.setBackoffice(contratoManaged.getUsuario());
			}
			
			contrato.setFormaPago(null);
			contrato.setCuotas(null);
			contrato.setGastosAdministrativos(null);
			contrato.setGastosAdministrativosTotales(null);
			contrato.setGastosConcesion(null);
			contrato.setIntereses(null);
			contrato.setValorCuota(null);
			contrato.setValorTasaInteresEfectivaAnual(null);
			contrato.setValorUnidadIndexada(null);
			
			contrato.setEstado(estado);
			contrato.setFechaRechazo(date);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, contrato.getRol(), contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "RELLAMAR" y lo asigna a la "bandeja" de Supervisores de Call-center.
	 * 
	 * @param contrato a posponer.
	 */
	public void posponer(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RELLAMAR"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			Contrato contratoManaged = this.update(contrato);
			
			this.asignar(null, contratoManaged.getRol(), contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "DISTRIBUIR" y lo asigna a la "bandeja" de Supervisores de Distribución.
	 * 
	 * @param contrato contrato a distribuir.
	 */
	public void distribuir(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.DISTRIBUIR"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorDistribucion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			
			contrato.setFechaBackoffice(date);
			contrato.setBackoffice(uact);
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "RE-DISTRIBUIR" y lo asigna a la "bandeja" de Supervisores de Distribución.
	 * 
	 * @param contrato a redistribuir.
	 */
	public void redistribuir(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorDistribucion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setDistribuidor(null);
			contrato.setFechaEntregaDistribuidor(null);
			contrato.setFechaDevolucionDistribuidor(null);
			contrato.setActivador(null);
			contrato.setFechaActivacion(null);
			contrato.setFechaActivarEn(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "TELELINK".
	 * 
	 * @param contrato a actualizar.
	 */
	public void telelink(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.TELELINK"))
				);
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Rol rolSupervisorBackoffice = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorBackOffice")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			if (contrato.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")))) {
				// Si estaba asignado a un Vendedor lo dejamos en la "bandeja" de Supervisores de Call-center.
				
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato.setVendedor(uact);
			} else if (contrato.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Backoffice")))) {
				// Si estaba asignado a un Back-office lo dejamos en la "bandeja" de Supervisores de Back-office.
				
				contrato.setRol(rolSupervisorBackoffice);
				
				contrato.setFechaVenta(contratoManaged.getFechaVenta());
				contrato.setVendedor(contratoManaged.getVendedor());
				contrato.setFechaBackoffice(date);
				contrato.setBackoffice(uact);
			}
			
			contrato.setEstado(estado);
			contrato.setFechaRechazo(date);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, contrato.getRol(), contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "RENOVADO" y lo asigna a la "bandeja" de Supervisores de Back-office.
	 * 
	 * @param contrato a actualizar.
	 */
	public void renovo(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RENOVADO"))
				);
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Rol rolSupervisorBackoffice = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorBackOffice")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			if (contrato.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")))) {
				// Si estaba asignado a un Vendedor lo dejamos en la "bandeja" de Supervisores de Call-center.
				
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato.setVendedor(uact);
			} else if (contrato.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Backoffice")))) {
				// Si estaba asignado a un Back-office lo dejamos en la "bandeja" de Supervisores de Back-office.
				
				contrato.setRol(rolSupervisorBackoffice);
				
				contrato.setFechaVenta(contratoManaged.getFechaVenta());
				contrato.setVendedor(contratoManaged.getVendedor());
				contrato.setFechaBackoffice(date);
				contrato.setBackoffice(uact);
			}
			
			contrato.setFormaPago(null);
			contrato.setCuotas(null);
			contrato.setGastosAdministrativos(null);
			contrato.setGastosAdministrativosTotales(null);
			contrato.setGastosConcesion(null);
			contrato.setIntereses(null);
			contrato.setValorCuota(null);
			contrato.setValorTasaInteresEfectivaAnual(null);
			contrato.setValorUnidadIndexada(null);
			
			contrato.setEstado(estado);
			contrato.setFechaRechazo(date);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			asignar(null, contrato.getRol(), contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "REAGENDAR" y lo asigna a la "bandeja" del último Vendedor que realizó la venta.
	 * 
	 * @param contrato a reagendar.
	 */
	public void reagendar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.REAGENDAR"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			contrato.setEstado(estado);
			
			TypedQuery<ContratoRoutingHistory> query = 
				entityManager.createQuery(
					"SELECT crh"
					+ " FROM ContratoRoutingHistory crh"
					+ " WHERE crh.contrato.id = :contratoId"
					+ " AND crh.rol.id = :rolId"
					+ " ORDER BY crh.id DESC",
					ContratoRoutingHistory.class
				);
			query.setParameter("contratoId", contrato.getId());
			query.setParameter("rolId", Long.parseLong(Configuration.getInstance().getProperty("rol.Vendedor")));
			
			query.setMaxResults(1);
			
			Collection<ContratoRoutingHistory> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ContratoRoutingHistory contratoRoutingHistoryVendedor = resultList.toArray(new ContratoRoutingHistory[]{})[0];
				
				ContratoRoutingHistory contratoRoutingHistoryReagendar = new ContratoRoutingHistory();
				
				contratoRoutingHistoryReagendar.setContrato(contratoRoutingHistoryVendedor.getContrato());
				contratoRoutingHistoryReagendar.setEmpresa(contratoRoutingHistoryVendedor.getEmpresa());
				contratoRoutingHistoryReagendar.setEstado(estado);
				contratoRoutingHistoryReagendar.setFecha(date);
				contratoRoutingHistoryReagendar.setRol(contratoRoutingHistoryVendedor.getRol());
				contratoRoutingHistoryReagendar.setUsuario(contratoRoutingHistoryVendedor.getUsuario());
				
				contratoRoutingHistoryReagendar.setFcre(date);
				contratoRoutingHistoryReagendar.setFact(date);
				contratoRoutingHistoryReagendar.setTerm(Long.valueOf(1));
				contratoRoutingHistoryReagendar.setUact(contrato.getUact());
				contratoRoutingHistoryReagendar.setUcre(contrato.getUact());
				
				entityManager.persist(contratoRoutingHistoryReagendar);
				
				contrato.setFechaVenta(contratoManaged.getFechaVenta());
				contrato.setVendedor(contratoManaged.getVendedor());
				contrato.setBackoffice(uact);
				contrato.setFechaBackoffice(date);
				
				contrato.setRol(contratoRoutingHistoryVendedor.getRol());
				contrato.setUsuario(contratoRoutingHistoryVendedor.getUsuario());
				
				contrato.setFact(date);
				contrato.setTerm(Long.valueOf(1));
				
				this.update(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "ACTIVAR" y lo asigna a la "bandeja" de Supervisores de Activación.
	 * 
	 * @param contrato a activar.
	 */
	public void activar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorActivacion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contratoManaged.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "NO FIRMA".
	 * Mantiene el Distribuidor al que estaba asignado.
	 * 
	 * @param contrato a actualizar.
	 */
	public void noFirma(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.NOFIRMA"))
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contratoManaged.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(contratoManaged.getRol());
			contrato.setUsuario(contrato.getUsuario());
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
			
			asignar(contratoManaged.getUsuario(), contratoManaged.getRol(), contratoManaged, contrato.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "RE-COORDINAR" y lo asigna a la "bandeja" de Supervisores de Call-center.
	 * 
	 * @param contrato a recoordinar.
	 */
	public void recoordinar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.RECOORDINAR"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(null);
			contrato.setFechaDevolucionDistribuidor(null);
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "ACT. DOC. VENTA" y lo asigna a la "bandeja" del Activador que agendó la activación.
	 * 
	 * @param contrato a agendar.
	 */
	public void agendarActivacion(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.Activador")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setFechaEnvioAntel(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contrato.setEstado(estado);
			
			contratoManaged = this.update(contrato);
			
			asignar(contratoManaged.getUsuario(), rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "CONTROL ANTEL".
	 * 
	 * @param contrato a enviar a ANTEL.
	 */
	public void enviarAAntel(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.CONTROLANTEL"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setFechaEnvioAntel(date);
			contrato.setActivador(uact);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "ACM".
	 * 
	 * @param contrato a actualizar.
	 */
	public void terminar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.ACM"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			
			contrato.setFechaActivacion(date);
			contrato.setActivador(uact);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "FALTA DOCUMENTACION" y lo asigna a la "bandeja" de Supervisores de Call-center.
	 * 
	 * @param contrato a actualizar.
	 */
	public void faltaDocumentacion(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			
			contrato.setDistribuidor(null);
			contrato.setFechaEntregaDistribuidor(null);
			contrato.setFechaDevolucionDistribuidor(null);
			
			contrato.setActivador(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "ACTIVAR" y lo asigna a la "bandeja" del Activador que intervino.
	 * 
	 * @param contrato a actualizar.
	 */
	public void reActivar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.ACTIVAR"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.Activador")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setActivador(contratoManaged.getActivador());
			
			contrato.setCoordinador(uact);
			contrato.setEstado(estado);
			contrato.setFechaCoordinacion(date);
			contrato.setRol(rol);
			contrato.setUsuario(contratoManaged.getActivador());
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(contratoManaged.getActivador(), rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "NO RECOORDINA" y lo asigna a la "bandeja" de Supervisores de Distribución.
	 * 
	 * @param contrato a actualizar.
	 */
	public void noRecoordina(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.NORECOORDINA"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorDistribucion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setActivador(contratoManaged.getActivador());
			
			contrato.setCoordinador(uact);
			contrato.setEstado(estado);
			contrato.setFechaCoordinacion(date);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "CERRADO" y lo asigna a la "bandeja" de Supervisores de Activación.
	 * 
	 * @param contrato a actualizar.
	 */
	public void cerrar(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.CERRADO"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorActivacion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			contrato.setActivador(contratoManaged.getActivador());
			contrato.setCoordinador(contratoManaged.getCoordinador());
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "GESTION INTERNA" y lo asigna a la "bandeja" de Supervisores de Activación.
	 * 
	 * @param contrato a actualizar.
	 */
	public void gestionInterna(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.GESTIONINTERNA"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorActivacion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setActivador(null);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "GESTION DISTRIBUCION" y lo asigna a la "bandeja" del Distribuidor.
	 * 
	 * @param contrato a actualizar.
	 */
	public void gestionDistribucion(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.GESTIONDISTRIBUCION"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.Distribuidor")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			
			contrato.setFechaEntregaDistribuidor(date);
			contrato.setFechaDevolucionDistribuidor(null);
			contrato.setDistribuidor(null);
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(contratoManaged.getUsuario());
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			contratoManaged = this.update(contrato);
			
			this.asignar(contrato.getUsuario(), rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza el Contrato a estado "EQUIPO PERDIDO".
	 * 
	 * @param contrato a actualizar.
	 */
	public void equipoPerdido(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.EQUIPOPERDIDO"))
				);
			
			Rol rol = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorActivacion")),
					false
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setActivador(null);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato a estado "FACTURA IMPAGA".
	 * 
	 * @param contrato a actualizar.
	 */
	public void facturaImpaga(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.FACTURAIMPAGA"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
			
			this.asignar(contrato.getUsuario(), contrato.getRol(), contratoManaged, uact.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Marca el contrato como Enviado a Núcleo
	 * 
	 * @param contrato a actualizar.
	 */
	public void enviadoANucleo(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact(), false);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contratoManaged.setFechaEnvioANucleo(date);
			
			contratoManaged.setUact(uact.getId());
			contratoManaged.setFact(date);
			contratoManaged.setTerm(Long.valueOf(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Marca el contrato como Cancelado por el Cliente.
	 * 
	 * @param contrato a actualizar.
	 */
	public void canceladoPorCliente(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.CANCELADOPORCLIENTE"))
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			
			contrato.setFechaRechazo(date);
			contrato.setEstado(estado);
			
			contrato.setFcre(contratoManaged.getFcre());
			contrato.setUcre(contratoManaged.getUcre());
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Marca el contrato como Equipos Pagos.
	 * 
	 * @param contrato a actualizar.
	 */
	public void equiposPagos(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.EQUIPOSPAGOS"))
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			
			contrato.setEstado(estado);
			
			contrato.setFcre(contratoManaged.getFcre());
			contrato.setUcre(contratoManaged.getUcre());
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Marca el contrato como Equipo Devuelto.
	 * 
	 * @param contrato a actualizar.
	 */
	public void equipoDevuelto(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.EQUIPODEVUELTO"))
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			
			contrato.setEstado(estado);
			
			contrato.setFcre(contratoManaged.getFcre());
			contrato.setUcre(contratoManaged.getUcre());
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Marca el contrato como No Recuperado.
	 * 
	 * @param contrato a actualizar.
	 */
	public void noRecuperado(Contrato contrato) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.NORECUPERADO"))
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaPickUp(contratoManaged.getFechaPickUp());
			contrato.setResultadoEntregaDistribucionFecha(contratoManaged.getResultadoEntregaDistribucionFecha());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			
			contrato.setEstado(estado);
			
			contrato.setFcre(contratoManaged.getFcre());
			contrato.setUcre(contratoManaged.getUcre());
			contrato.setFact(date);
			contrato.setTerm(Long.valueOf(1));
			
			this.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Registra el evento de Pick-Up del contrato.
	 * 
	 * @param id a actualizar.
	 * @param observaciones a registrar.
	 * @param loggedUsuarioId identificador del usuario con sesión iniciada.
	 */
	public void pickUp(Long id, String observaciones, Long loggedUsuarioId) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, id);
			
			contratoManaged.setFechaPickUp(date);
			contratoManaged.setFact(date);
			contratoManaged.setUact(loggedUsuarioId);
			contratoManaged.setTerm(Long.valueOf(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cancela el trámite.
	 * 
	 * @param id a actualizar.
	 * @param loggedUsuarioId identificador del usuario con sesión iniciada.
	 */
	public void cancelarTramite(Long id, Long loggedUsuarioId) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("estado.TRAMITECANCELADO"))
				);
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(
					Long.parseLong(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")),
					false
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, id);
			
			contratoManaged.setEstado(estado);
			contratoManaged.setFechaRechazo(date);
			contratoManaged.setRol(rolSupervisorCallCenter);
			contratoManaged.setUsuario(null);
			
			contratoManaged.setFact(date);
			contratoManaged.setUact(loggedUsuarioId);
			contratoManaged.setTerm(Long.valueOf(1));
			
			this.asignar(null, contratoManaged.getRol(), contratoManaged, loggedUsuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date date = gregorianCalendar.getTime();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
			ProcesoExportacion procesoExportacion = new ProcesoExportacion();
			procesoExportacion.setFact(date);
			procesoExportacion.setFcre(date);
			procesoExportacion.setFechaInicio(date);
			procesoExportacion.setNombreArchivo(fileName);
			procesoExportacion.setObservaciones(metadataConsulta.asJSONString());
			procesoExportacion.setTerm(Long.valueOf(1));
			procesoExportacion.setUact(loggedUsuarioId);
			procesoExportacion.setUcre(loggedUsuarioId);
			procesoExportacion.setUrlOrigen("");
			
			Usuario usuario = new Usuario();
			usuario.setId(loggedUsuarioId);
			
			procesoExportacion.setUsuario(usuario);
			
			ProcesoExportacion procesoExportacionManaged = iProcesoExportacionBean.save(procesoExportacion);
			
			printWriter.println(
				"MID"
				+ ";Fecha fin contrato"
				+ ";Código de plan"
				+ ";Descripción de plan"
				+ ";Tipo de documento"
				+ ";Documento"
				+ ";Nombre"
				+ ";Apellido"
				+ ";Dirección"
				+ ";Código postal"
				+ ";Localidad"
				+ ";Modalidad de venta"
//				+ ";Equipo"
//				+ ";Agente"
//				+ ";Número de contrato"
				+ ";Número de trámite"
//				+ ";Número de cliente"
//				+ ";Fecha de nacimiento"
				+ ";Fecha de entrega"
//				+ ";Dirección de entrega"
//				+ ";Dirección de factura"
//				+ ";Teléfono de contacto"
				+ ";E-mail"
				+ ";Número de factura"
				+ ";Número de factura River Green"
				+ ";Precio"
				+ ";Nuevo plan"
				+ ";Motivo de cambio de plan"
				+ ";Forma de pago"
				+ ";Tarjeta de crédito"
				+ ";Costo de envío"
				+ ";Número de serie"
				+ ";Observaciones"
				+ ";Fecha de creación"
				+ ";Fecha de pick-up"
				+ ";Fecha de venta"
				+ ";Fecha de back-office"
				+ ";Fecha de entrega a Distribuidor"
				+ ";Fecha de devuelto por Distribuidor"
				+ ";Fecha de coordinación"
				+ ";Fecha de envío a ANTEL"
//				+ ";Fecha de activación"
//				+ ";Fecha agendada de activación"
//				+ ";Fecha de rechazo"
				+ ";Departamento"
				+ ";Barrio"
				+ ";Zona"
//				+ ";Turno"
				+ ";Nuevo equipo"
				+ ";Estado"
				+ ";Empresa"
//				+ ";Rol"
				+ ";Usuario"
				+ ";Vendedor"
				+ ";Back-office"
				+ ";Distribuidor"
//				+ ";Activador"
//				+ ";Coordinador"
				+ ";Resultado entrega"
				+ ";Observaciones entrega"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Contrato contrato = (Contrato) object;
				
				String line = 
					(contrato.getMid() != null ?
						contrato.getMid()
						: "")
					+ ";" + (contrato.getFechaFinContrato() != null ? 
						format.format(contrato.getFechaFinContrato())
						: "")
					+ ";" + (contrato.getTipoContratoCodigo() != null ?
						contrato.getTipoContratoCodigo()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getTipoContratoDescripcion() != null ?
						contrato.getTipoContratoDescripcion()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getDocumentoTipo() != null ?
						contrato.getDocumentoTipo()
						: "")
					+ ";'" + (contrato.getDocumento() != null ?
						contrato.getDocumento()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getNombre() != null ?
						contrato.getNombre()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getApellido() != null ?
						contrato.getApellido()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getDireccion() != null ?
						contrato.getDireccion()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getCodigoPostal() != null ?
						contrato.getCodigoPostal()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getLocalidad() != null ?
						contrato.getLocalidad()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getModalidadVenta() != null ?
						contrato.getModalidadVenta().getDescripcion()
						: "")
//					+ ";" + (contrato.getEquipo() != null ?
//						contrato.getEquipo()
//						: "")
//					+ ";" + (contrato.getAgente() != null ?
//						contrato.getAgente()
//						: "")
//					+ ";" + (contrato.getNumeroContrato() != null ?
//						contrato.getNumeroContrato()
//						: "")
					+ ";" + (contrato.getNumeroTramite() != null ?
						contrato.getNumeroTramite()
						: "")
//					+ ";" + (contrato.getNumeroCliente() != null ?
//						contrato.getNumeroCliente()
//						: "")
//					+ ";" + (contrato.getFechaNacimiento() != null ?
//						format.format(contrato.getFechaNacimiento())
//						: "")
					+ ";" + (contrato.getFechaEntrega() != null ?
						format.format(contrato.getFechaEntrega())
						: "")
//					+ ";" + (contrato.getDireccionEntrega() != null ?
//						contrato.getDireccionEntrega()
//						: "")
//					+ ";" + (contrato.getDireccionFactura() != null ?
//						contrato.getDireccionFactura()
//						: "")
//					+ ";" + (contrato.getTelefonoContacto() != null ?
//						contrato.getTelefonoContacto()
//						: "")
					+ ";" + (contrato.getEmail() != null ?
						contrato.getEmail()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getNumeroFactura() != null ?
						contrato.getNumeroFactura()
						: "")
					+ ";" + (contrato.getNumeroFacturaRiverGreen() != null ?
						contrato.getNumeroFacturaRiverGreen()
						: "")
					+ ";" + (contrato.getPrecio() != null ?
						contrato.getPrecio()
						: "")
					+ ";" + (contrato.getNuevoPlan() != null ?
						contrato.getNuevoPlan().getAbreviacion()
						: "")
					+ ";" + (contrato.getMotivoCambioPlan() != null ?
						contrato.getMotivoCambioPlan().getDescripcion()
						: "")
					+ ";" + (contrato.getFormaPago() != null ?
						contrato.getFormaPago().getDescripcion()
						: "")
					+ ";" + (contrato.getTarjetaCredito() != null ?
						contrato.getTarjetaCredito().getNombre()
						: "")
					+ ";" + (contrato.getCostoEnvio() != null ?
						contrato.getCostoEnvio()
						: "")
					+ ";" + (contrato.getNumeroSerie() != null ?
						contrato.getNumeroSerie()
						: "")
					+ ";" + (contrato.getObservaciones() != null ?
						contrato.getObservaciones()
							.replaceAll(";", ".")
							.replaceAll("\"", "")
						: "")
					+ ";" + (contrato.getFcre() != null ?
						format.format(contrato.getFcre())
						: "")
					+ ";" + (contrato.getFechaPickUp() != null ?
						format.format(contrato.getFechaPickUp())
						: "")
					+ ";" + (contrato.getFechaVenta() != null ?
						format.format(contrato.getFechaVenta())
						: "")
					+ ";" + (contrato.getFechaBackoffice() != null ?
						format.format(contrato.getFechaBackoffice())
						: "")
					+ ";" + (contrato.getFechaEntregaDistribuidor() != null ?
						format.format(contrato.getFechaEntregaDistribuidor())
						: "")
					+ ";" + (contrato.getFechaDevolucionDistribuidor() != null ?
						format.format(contrato.getFechaDevolucionDistribuidor())
						: "")
					+ ";" + (contrato.getFechaCoordinacion() != null ?
						format.format(contrato.getFechaCoordinacion())
						: "")					
					+ ";" + (contrato.getFechaEnvioAntel() != null ?
						format.format(contrato.getFechaEnvioAntel())
						: "")
//					+ ";" + (contrato.getFechaActivacion() != null ?
//						format.format(contrato.getFechaActivacion())
//						: "")
//					+ ";" + (contrato.getFechaActivarEn() != null ?
//						format.format(contrato.getFechaActivarEn())
//						: "")
//					+ ";" + (contrato.getFechaRechazo() != null ?
//						format.format(contrato.getFechaRechazo())
//						: "")
					+ ";" + (contrato.getBarrio() != null ?
						contrato.getBarrio().getDepartamento().getNombre()
						: "")
					+ ";" + (contrato.getBarrio() != null ?
						contrato.getBarrio().getNombre()
						: "")
					+ ";" + (contrato.getZona() != null ?
						contrato.getZona().getNombre()
						: "")
//					+ ";" + (contrato.getTurno() != null ?
//						contrato.getTurno().getNombre()
//						: "")
					+ ";" + (contrato.getProducto() != null ?
						contrato.getProducto().getModelo().getDescripcion()
						: (contrato.getModelo() != null ? 
							contrato.getModelo().getDescripcion() 
							: ""
						)
					)
					+ ";" + (contrato.getEstado() != null ?
						contrato.getEstado().getNombre()
						: "")
					+ ";" + (contrato.getEmpresa() != null ?
						contrato.getEmpresa().getNombre()
						: "")
//					+ ";" + (contrato.getRol() != null ?
//						contrato.getRol().getNombre()
//						: "")
					+ ";" + (contrato.getUsuario() != null ?
						contrato.getUsuario().getNombre()
						: "")
					+ ";" + (contrato.getVendedor() != null ?
						contrato.getVendedor().getNombre()
						: "")
					+ ";" + (contrato.getBackoffice() != null ?
						contrato.getBackoffice().getNombre()
						: "")
					+ ";" + (contrato.getDistribuidor() != null ? 
						contrato.getDistribuidor().getNombre() 
						: "")
//					+ ";" + (contrato.getActivador() != null ?
//						contrato.getActivador().getNombre()
//						: "")
//					+ ";" + (contrato.getCoordinador() != null ?
//						contrato.getCoordinador().getNombre()
//						: "");
					+ ";" + (contrato.getResultadoEntregaDistribucion() != null ?
						contrato.getResultadoEntregaDistribucion().getDescripcion()
						: "")
					+ ";" + (contrato.getResultadoEntregaDistribucionObservaciones() != null ?
						contrato.getResultadoEntregaDistribucionObservaciones()
							.replaceAll(";", "")
							.replaceAll("\"", "")
						: "");
					
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			date = gregorianCalendar.getTime();
			
			procesoExportacionManaged.setFact(date);
			procesoExportacionManaged.setFechaFin(date);
			procesoExportacionManaged.setUact(loggedUsuarioId);
			
			iProcesoExportacionBean.update(procesoExportacionManaged);
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * El archivo generado tendrá el formato especificado para enviar a Núcleo.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcelNucleo(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			Map<Long, Long> codigosDepartamentos = new HashMap<Long, Long>();
			codigosDepartamentos.put(Long.valueOf(1), Long.valueOf(11)); //"MONTEVIDEO"
			codigosDepartamentos.put(Long.valueOf(2), Long.valueOf(90)); //"CANELONES"
			codigosDepartamentos.put(Long.valueOf(3), Long.valueOf(55)); //"ARTIGAS"
			codigosDepartamentos.put(Long.valueOf(4), Long.valueOf(50)); //"SALTO"
			codigosDepartamentos.put(Long.valueOf(5), Long.valueOf(60)); //"PAYSANDU"
			codigosDepartamentos.put(Long.valueOf(6), Long.valueOf(65)); //"RIO NEGRO"
			codigosDepartamentos.put(Long.valueOf(7), Long.valueOf(75)); //"SORIANO"
			codigosDepartamentos.put(Long.valueOf(8), Long.valueOf(70)); //"COLONIA"
			codigosDepartamentos.put(Long.valueOf(9), Long.valueOf(85)); //"FLORES"
			codigosDepartamentos.put(Long.valueOf(10), Long.valueOf(94)); //"FLORIDA"
			codigosDepartamentos.put(Long.valueOf(11), Long.valueOf(97)); //"DURAZNO"
			codigosDepartamentos.put(Long.valueOf(12), Long.valueOf(30)); //"LAVALLEJA"
			codigosDepartamentos.put(Long.valueOf(13), Long.valueOf(20)); //"MALDONADO"
			codigosDepartamentos.put(Long.valueOf(14), Long.valueOf(27)); //"ROCHA"
			codigosDepartamentos.put(Long.valueOf(15), Long.valueOf(33)); //"TREINTA Y TRES"
			codigosDepartamentos.put(Long.valueOf(16), Long.valueOf(37)); //"CERRO LARGO"
			codigosDepartamentos.put(Long.valueOf(17), Long.valueOf(40)); //"RIVERA"
			codigosDepartamentos.put(Long.valueOf(18), Long.valueOf(45)); //"TACUAREMBO"
			codigosDepartamentos.put(Long.valueOf(19), Long.valueOf(80)); //"SAN JOSE"

			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
//			printWriter.println(
//				"Tipo de documento"
//				+ ";Documento"
//				+ ";Nombre primario"
//				+ ";Nombre secundario"
//				+ ";Apellido primario"
//				+ ";Apellido secundario"
//				+ ";Fecha de nacimiento"
//				+ ";Sexo"
//				+ ";Estado civil"
//				+ ";Nacionalidad"
//				+ ";Fecha de vencimiento de documento"
//				+ ";Email"
//				+ ";Calle"
//				+ ";Número de casa"
//				+ ";Número de apartamento"
//				+ ";Esquina"
//				+ ";Solar,Senda,Block,MZ"
//				+ ";Código postal"
//				+ ";Departamento"
//				+ ";Teléfono"
//				+ ";Celular"
//				+ ";Teléfono especial"
//				+ ";Ocupación"
//				+ ";Teléfono trabajo"
//				+ ";Fecha ingreso"
//				+ ";Departamento"
//				+ ";Barrio"
//				+ ";Calle"
//				+ ";Número"
//				+ ";Apt/esq/block/solar/manzana"
//				+ ";Código postal"
//				+ ";Nombre empresa"
//				+ ";Comprobante"
//				+ ";Salario nominal"
//				+ ";Salario líquido"
//				+ ";Tarjeta de crédito"
//				+ ";Límite"
//				+ ";Otra tarjeta de crédito"
//				+ ";Límite"
//				+ ";Clearing"
//				+ ";Producto"
//				+ ";Monto solicitado"
//				+ ";Cuotas"
//				+ ";Intereses"
//				+ ";Gastos administrativos"
//				+ ";Gastos de concesión"
//				+ ";Valor cuota"
//				+ ";Fecha venta"
// Se modifica la fecha de venta por la fecha de activación.
//				+ ";Fecha activación"
//				+ ";Unidad Indexada"
//				+ ";Número de trámite"
//				+ ";Número de vale"
//				+ ";Empresa"
//			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			String valorNoDisponible = Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.formatoArchivoNucleo.valorNoDisponible");
			String productoComun = Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.formatoArchivoNucleo.productoComun");
			String productoANTEL = Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.formatoArchivoNucleo.productoANTEL");
			String productoANTELTasaInteresEspecial = Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.formatoArchivoNucleo.productoANTELTasaInteresEspecial");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Contrato contrato = (Contrato) object;
				
				String line = 
					// Para Núcleo, 1 - Cédula de identidad, 2 - RUT. Por ahora son iguales a Logística.
					(contrato.getTipoDocumento() != null ? contrato.getTipoDocumento().getId() : valorNoDisponible)
					+ ";" + (contrato.getDocumento() != null ? contrato.getDocumento().replace("-", "") : valorNoDisponible)
					+ ";" + (contrato.getNombre() != null ? contrato.getNombre() : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + (contrato.getApellido() != null ? contrato.getApellido() : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + (contrato.getFechaNacimiento() != null ? dateFormat.format(contrato.getFechaNacimiento()) : valorNoDisponible)
					+ ";" + (contrato.getSexo() != null ? contrato.getSexo().getDescripcion().substring(0, 1) : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + (contrato.getEmail() != null ? contrato.getEmail() : valorNoDisponible)
					+ ";" + (contrato.getDireccionFacturaCalle() != null ? contrato.getDireccionFacturaCalle() : valorNoDisponible)
					+ ";" + (contrato.getDireccionFacturaNumero() != null ? contrato.getDireccionFacturaNumero() : valorNoDisponible)
					+ ";" + (contrato.getDireccionFacturaApto() != null ? contrato.getDireccionFacturaApto() : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + (contrato.getDireccionFacturaSolar() != null ? contrato.getDireccionFacturaSolar() : valorNoDisponible) //+ " - " + contrato.getDireccionFacturaBlock() + " - " + contrato.getDireccionFacturaManzana()
					+ ";" + (contrato.getDireccionFacturaCodigoPostal() != null ? contrato.getDireccionFacturaCodigoPostal() : valorNoDisponible)
					+ ";" + (contrato.getDireccionFacturaDepartamento() != null ? codigosDepartamentos.get(contrato.getDireccionFacturaDepartamento().getId()) : valorNoDisponible)
					+ ";" + (contrato.getTelefonoContacto() != null ? contrato.getTelefonoContacto() : valorNoDisponible)
					+ ";" + (contrato.getTelefonoContacto() != null ? contrato.getTelefonoContacto() : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + (contrato.getDireccionEntregaDepartamento() != null ? codigosDepartamentos.get(contrato.getDireccionEntregaDepartamento().getId()) : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + (contrato.getDireccionEntregaCalle() != null ? contrato.getDireccionEntregaCalle() : valorNoDisponible)
					+ ";" + (contrato.getDireccionEntregaNumero() != null ? contrato.getDireccionEntregaNumero() : valorNoDisponible)
					+ ";" + (contrato.getDireccionEntregaApto() != null ? contrato.getDireccionEntregaApto() : valorNoDisponible)
					+ ";" + (contrato.getDireccionEntregaCodigoPostal() != null ? contrato.getDireccionEntregaCodigoPostal() : valorNoDisponible)
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + valorNoDisponible
					+ ";" + (
						contrato.getAntelNroTrn() != null && !contrato.getAntelNroTrn().isEmpty() ? 
							(contrato.getCuotas() != null 
								&& contrato.getCuotas() > 18 
								&& contrato.getValorTasaInteresEfectivaAnual() != null
								&& contrato.getValorTasaInteresEfectivaAnual().equals(0.9) ? 
								productoANTELTasaInteresEspecial
								: productoANTEL
							)
							: productoComun
						)
					+ ";" + (contrato.getPrecio() != null ? decimalFormat.format(Math.round(contrato.getPrecio())) : valorNoDisponible)
					+ ";" + (contrato.getCuotas() != null ? decimalFormat.format(contrato.getCuotas()) : valorNoDisponible)
					+ ";" + (contrato.getIntereses() != null ? decimalFormat.format(Math.round(contrato.getIntereses())) : valorNoDisponible)
					+ ";" + (contrato.getGastosAdministrativosTotales() != null ? decimalFormat.format(Math.round(contrato.getGastosAdministrativosTotales())) : valorNoDisponible)
//					+ ";" + (contrato.getGastosConcesion() != null ? decimalFormat.format(Math.round(contrato.getGastosConcesion())) : valorNoDisponible)
					+ ";" + (contrato.getValorCuota() != null ? decimalFormat.format(Math.round(contrato.getValorCuota())) : valorNoDisponible)
//					+ ";" + (contrato.getFechaVenta() != null ? dateFormat.format(contrato.getFechaVenta()) : valorNoDisponible)
					+ ";" + (contrato.getFechaActivarEn() != null ? dateFormat.format(contrato.getFechaActivarEn()) : valorNoDisponible)
					+ ";" + (contrato.getValorUnidadIndexada() != null ? decimalFormat.format(contrato.getValorUnidadIndexada()) : valorNoDisponible)
					+ ";" + contrato.getNumeroTramite()
					+ ";" + (contrato.getNumeroVale() != null ? contrato.getNumeroVale() : valorNoDisponible)
					+ ";" + contrato.getEmpresa().getId();
				
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcelVentasNuestroCredito(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
			printWriter.println(
				"MID"
				+ ";Empresa"
				+ ";Fecha de venta"
				+ ";Número de vale"
				+ ";Tasa Efectiva Anual"
				+ ";Unidad Indexada"
				+ ";Precio"
				+ ";Cuotas"
				+ ";Valor cuota"
				+ ";Intereses"
				+ ";Estado"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			DecimalFormat decimalFormatCuatroCifras = new DecimalFormat("#.####");
			
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Contrato contrato = (Contrato) object;
				
				String line = 
					contrato.getMid()
					+ ";" + (contrato.getEmpresa() != null ?
						contrato.getEmpresa().getNombre()
						: "")
					+ ";" + (contrato.getFechaVenta() != null ?
						format.format(contrato.getFechaVenta())
						: "")
					+ ";" + (contrato.getNumeroVale() != null ?
						contrato.getNumeroVale() 
						: "")
					+ ";" + (contrato.getValorTasaInteresEfectivaAnual() != null ?
						decimalFormat.format(contrato.getValorTasaInteresEfectivaAnual())
						: "")
					+ ";" + (contrato.getValorUnidadIndexada() != null ?
						decimalFormatCuatroCifras.format(contrato.getValorUnidadIndexada())
						: "")
					+ ";" + (contrato.getPrecio() != null ?
						decimalFormat.format(contrato.getPrecio())
						: "")
					+ ";" + (contrato.getCuotas() != null ?
						contrato.getCuotas()
						: "")
					+ ";" + (contrato.getValorCuota() != null ?
						decimalFormat.format(contrato.getValorCuota())
						: "")
					+ ";" + (contrato.getIntereses() != null ?
						decimalFormat.format(contrato.getIntereses())
						: "")
					+ ";" + (contrato.getEstado() != null ?
						contrato.getEstado().getNombre()
						: "");
				
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcelVentasCuentaAjena(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
			printWriter.println(
				"Número de trámite"
				+ ";Nro trn"
				+ ";Equipo"
				+ ";Documento"
				+ ";Fecha de venta"
				+ ";Número de factura"
				+ ";Forma de pago"
				+ ";Nro servicio cuenta"
				+ ";Cuotas"
				+ ";Importe"
				+ ";Estado"
				+ ";Observaciones"
			);
			
			metadataConsulta.setTamanoMuestra(Long.valueOf(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Contrato contrato = (Contrato) object;
				
				String line = 
					contrato.getNumeroTramite()
					+ ";" + (contrato.getAntelNroTrn() != null ?
						contrato.getAntelNroTrn()
						: "")
					+ ";" + (contrato.getModelo() != null ?
						contrato.getMarca().getNombre() + " " + contrato.getModelo().getDescripcion()
						: "")
					+ ";" + (contrato.getDocumento() != null ?
						contrato.getDocumento()
						: "")
					+ ";" + (contrato.getFechaVenta() != null ?
						format.format(contrato.getFechaVenta())
						: "")
					+ ";" + (contrato.getNumeroFactura() != null ?
						contrato.getNumeroFactura()
						: "")
					+ ";" + (contrato.getAntelFormaPago() != null ?
						contrato.getAntelFormaPago()
						: "")
					+ ";" + (contrato.getAntelNroServicioCuenta() != null ?
						contrato.getAntelNroServicioCuenta()
						: "")
					+ ";" + (contrato.getCuotas() != null ?
						contrato.getCuotas()
						: "")
					+ ";" + (contrato.getAntelImporte() != null ?
						decimalFormat.format(contrato.getAntelImporte())
						: "")
					+ ";" + (contrato.getEstado() != null ?
						contrato.getEstado().getNombre()
						: "")
					+ ";" + (contrato.getObservaciones() != null ?
						contrato.getObservaciones()
						: "");
				
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Retorna el Contrato cuyo id coincide con el parámetro.
	 * 
	 * @param ID del contrato a retornar.
	 * @return Contrato a retornar.
	 */
	public Contrato getById(Long id, boolean initializeCollections) {
		Contrato result = null;
		
		try {
			result = entityManager.find(Contrato.class, id);
			
			TypedQuery<ContratoArchivoAdjunto> queryArchivosAdjuntos =
				entityManager.createQuery(
					"SELECT caa"
					+ " FROM ContratoArchivoAdjunto caa"
					+ " WHERE caa.contrato.id = :contratoId",
					ContratoArchivoAdjunto.class
				);
			queryArchivosAdjuntos.setParameter("contratoId", id);
			
			if (initializeCollections) {
				entityManager.detach(result);
				
				Set<ContratoArchivoAdjunto> contratoArchivoAdjuntos = new HashSet<ContratoArchivoAdjunto>();
				
				for (ContratoArchivoAdjunto contratoArchivoAdjunto : queryArchivosAdjuntos.getResultList()) {
					contratoArchivoAdjuntos.add(contratoArchivoAdjunto);
				}
				
				result.setArchivosAdjuntos(contratoArchivoAdjuntos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Retorna el Contrato para la combinación <mid, empresa>.
	 * 
	 * @param mid MID del Contrato.
	 * @param empresa Empresa del Contrato.
	 * @return Contrato que cumple con los parámetros especificados.
	 */
	public Contrato getByMidEmpresa(Long mid, Empresa empresa, boolean initializeCollections) {
		Contrato result = null;
		
		try {
			TypedQuery<Contrato> query = 
				entityManager.createQuery(
					"SELECT c FROM Contrato c"
					+ " WHERE c.mid = :mid"
					+ " AND c.empresa = :empresa", 
					Contrato.class
				);
			query.setParameter("mid", mid);
			query.setParameter("empresa", empresa);
			
			TypedQuery<ContratoArchivoAdjunto> queryArchivosAdjuntos =
				entityManager.createQuery(
					"SELECT caa"
					+ " FROM ContratoArchivoAdjunto caa"
					+ " WHERE caa.contrato.id = :contratoId",
					ContratoArchivoAdjunto.class
				);
				
			List<Contrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
				entityManager.detach(result);
				
				if (initializeCollections) {
					queryArchivosAdjuntos.setParameter("contratoId", result.getId());
					
					Set<ContratoArchivoAdjunto> contratoArchivoAdjuntos = new HashSet<ContratoArchivoAdjunto>();
					
					for (ContratoArchivoAdjunto contratoArchivoAdjunto : queryArchivosAdjuntos.getResultList()) {
						contratoArchivoAdjuntos.add(contratoArchivoAdjunto);
					}
					
					result.setArchivosAdjuntos(contratoArchivoAdjuntos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Retorna el Contrato para el número de trámite.
	 * 
	 * @param numeroTramite Número de trámite del Contrato.
	 * @return Contrato a retornar.
	 */
	public Contrato getByNumeroTramite(Long numeroTramite, boolean initializeCollections) {
		Contrato result = null;
		
		try {
			TypedQuery<Contrato> query = entityManager.createQuery(
				"SELECT c FROM"
				+ " Contrato c"
				+ " WHERE c.numeroTramite = :numeroTramite", 
				Contrato.class
			);
			query.setParameter("numeroTramite", numeroTramite);
			
			TypedQuery<ContratoArchivoAdjunto> queryArchivosAdjuntos =
				entityManager.createQuery(
					"SELECT caa"
					+ " FROM ContratoArchivoAdjunto caa"
					+ " WHERE caa.contrato.id = :contratoId",
					ContratoArchivoAdjunto.class
				);
			
			List<Contrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
				entityManager.detach(result);
				
				if (initializeCollections) {
					queryArchivosAdjuntos.setParameter("contratoId", result.getId());
					
					Set<ContratoArchivoAdjunto> contratoArchivoAdjuntos = new HashSet<ContratoArchivoAdjunto>();
					
					for (ContratoArchivoAdjunto contratoArchivoAdjunto : queryArchivosAdjuntos.getResultList()) {
						contratoArchivoAdjuntos.add(contratoArchivoAdjunto);
					}
					
					result.setArchivosAdjuntos(contratoArchivoAdjuntos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Actualiza el contrato y retorna la instancia actualizada.
	 * Si es una instancia nueva, auto-genera el número de trámite.
	 * Si la forma de pago es "Nuestro crédito", auto-genera el número de vale. 
	 * 
	 * @param contrato Contrato a actualizar.
	 * @return Contrato actualizado.
	 */
	public Contrato update(Contrato contrato) {
		try {
			Random random = new Random();
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			if (contrato.getId() == null) {
				Query query = 
					entityManager.createNativeQuery(
						"SELECT nextval('numero_tramite_sequence')"
					);
				
				Long maxNumeroTramite = ((BigInteger) query.getSingleResult()).longValue();
				
				contrato.setNumeroTramite(maxNumeroTramite);
				contrato.setRandom(Long.valueOf(random.nextInt(1000000)));
				
				contrato.setFcre(date);
				contrato.setFact(date);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUcre(contrato.getUact());
				
				entityManager.persist(contrato);
			} else {
				Long estadoVendidoId = 
					Long.parseLong(Configuration.getInstance().getProperty("estado.VENDIDO"));
				
				Long formaPagoNuestroCreditoId = 
					Long.parseLong(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				if (contrato.getEstado().getId().equals(estadoVendidoId) &&
					contrato.getFormaPago() != null && 
					contrato.getFormaPago().getId().equals(formaPagoNuestroCreditoId) &&
					contrato.getNumeroVale() == null) {
					
					Query query = 
						entityManager.createNativeQuery(
							"SELECT nextval('numero_vale_sequence')"
						);
						
					Long maxNumeroVale = ((BigInteger) query.getSingleResult()).longValue();
					
					contrato.setNumeroVale(maxNumeroVale);
				}
				
				Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
				contrato.setFcre(contratoManaged.getFcre());
				contrato.setUcre(contratoManaged.getUcre());
				
				contrato.setRandom(Long.valueOf(random.nextInt(1000000)));
				
				if (contratoManaged.getResultadoEntregaDistribucion() == null
					&& contratoManaged.getResultadoEntregaDistribucionFecha() == null
					&& contrato.getResultadoEntregaDistribucion() != null) {
					contrato.setResultadoEntregaDistribucionFecha(date);
				}
				
				entityManager.merge(contrato);
			}
			
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contrato;
	}
}