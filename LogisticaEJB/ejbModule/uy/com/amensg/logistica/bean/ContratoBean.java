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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.StockTipoMovimiento;
import uy.com.amensg.logistica.entities.TipoContrato;
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
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<Contrato> query = entityManager.createQuery(criteriaQuery);
			
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
									"p" + i,
									new Boolean(valor)
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
			
			// Subquery con los roles subordinados del usuario
			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
			subrootRolesSubordinados.alias("subrootRolesSubordinados");
			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.or(
					// Asignados al usuario.
					criteriaBuilder.equal(rootCount.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
					// Asignados a algún rol subordinado dentro de la empresa
					criteriaBuilder.exists(
						subqueryRolesSubordinados
							.select(subrootRolesSubordinados)
							.where(
								criteriaBuilder.and(
									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), rootCount.get("empresa").get("id")),
									criteriaBuilder.isMember(rootCount.get("rol").as(Rol.class), expressionRolesSubordinados)
								)
							)
					)
				)
			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("usuario1", usuarioId);
			queryCount.setParameter("usuario2", usuarioId);
			
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryCount.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryCount.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								queryCount.setParameter(
									"p" + i,
									new Boolean(valor)
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									new Double(valor)
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
				
				Long mid = new Long (fields[0].trim());
				
				mids.add(mid);
			}
			
			Map<Long, Integer> map = this.preprocesarConjunto(mids, empresaId);
			
			Long importar = new Long(0);
			Long sobreescribir = new Long(0);
			Long omitir = new Long(0);
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
				new Long(Configuration.getInstance().getProperty("estado.LLAMAR"));
			Long estadoVendidoId = 
				new Long(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				new Long(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				new Long(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				new Long(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				new Long(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				new Long(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				new Long(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				new Long(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				new Long(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			
			TypedQuery<Long> queryVendidos = 
				entityManager.createQuery(
					"SELECT c.mid"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id IN ("
						+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
						+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId, :estadoACMId,"
						+ " :estadoReagendarId"
					+ " )"
					+ " AND c.mid IN :mids", 
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
			queryVendidos.setParameter("mids", mids);
			
			for (Long mid : queryVendidos.getResultList()) {
				result.put(mid, Constants.__COMPROBACION_IMPORTACION_OMITIR);
			}
			
			TypedQuery<Long> queryLlamar = 
				entityManagerXA.createQuery(
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
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
			
			Estado estado = 
				iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId);
			
			Long estadoVendidoId = 
				new Long(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				new Long(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				new Long(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				new Long(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				new Long(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				new Long(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				new Long(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				new Long(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				new Long(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			SQLQuery selectVendidos = hibernateSession.createSQLQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE estado_id IN ("
					+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
					+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId, :estadoACMId,"
					+ " :estadoReagendarId"
				+ " )"
				+ " AND mid = :mid"
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
			
			SQLQuery selectContratoExisteEmpresa = hibernateSession.createSQLQuery(
				"SELECT id"
				+ " FROM contrato"
				+ " WHERE mid = :mid"
				+ " AND empresa_id = :empresaId"
				+ " AND estado_id = :estadoLlamarId"
			);
			selectContratoExisteEmpresa.addScalar("id", LongType.INSTANCE);
			selectContratoExisteEmpresa.setParameter("empresaId", empresa.getId(), LongType.INSTANCE);
			selectContratoExisteEmpresa.setParameter("estadoLlamarId", estado.getId(), LongType.INSTANCE);
			
			SQLQuery insertContrato = hibernateSession.createSQLQuery(
				"INSERT INTO contrato("
					+ " id,"
					+ " numero_tramite,"
					+ " empresa_id,"
					+ " estado_id,"
					+ " rol_id,"
					+ " fact,"
					+ " term,"
					+ " uact,"
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
			
			insertContrato.setParameter(0, empresa.getId(), LongType.INSTANCE);
			insertContrato.setParameter(1, estado.getId(), LongType.INSTANCE);
			insertContrato.setParameter(2, rolSupervisorCallCenter.getId(), LongType.INSTANCE);
			
			insertContrato.setParameter(3, hoy, DateType.INSTANCE);
			insertContrato.setParameter(4, new Long(1), LongType.INSTANCE);
			insertContrato.setParameter(5, loggedUsuarioId, LongType.INSTANCE);
			
			SQLQuery updateContrato = hibernateSession.createSQLQuery(
				"UPDATE contrato"
				+ " SET fact = ?,"
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
			
			updateContrato.setParameter(0, hoy, DateType.INSTANCE);
			updateContrato.setParameter(1, new Long(1), LongType.INSTANCE);
			updateContrato.setParameter(2, loggedUsuarioId, LongType.INSTANCE);
			
			SQLQuery insertContratoRoutingHistory = hibernateSession.createSQLQuery(
				"INSERT INTO contrato_routing_history("
					+ " id,"
					+ " fecha,"
					+ " empresa_id,"
					+ " usuario_id,"
					+ " rol_id,"
					+ " uact,"
					+ " fact,"
					+ " term,"
					+ " contrato_id,"
					+ " estado_id"
				+ " ) SELECT nextval('hibernate_sequence'),"
					+ " c.fact,"
					+ " c.empresa_id,"
					+ " null,"
					+ " c.rol_id,"
					+ " c.uact,"
					+ " c.fact,"
					+ " c.term,"
					+ " c.id,"
					+ " c.estado_id"
				+ " FROM contrato c"
				+ " LEFT OUTER JOIN contrato_routing_history crh ON crh.contrato_id = c.id"
				+ " WHERE c.fact = ?"
				+ " AND crh.id IS NULL"
			);
			
			insertContratoRoutingHistory.setParameter(0, hoy, DateType.INSTANCE);
			
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
						mid = new Long(fields[0].trim());
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
							(fields[4] != null && !fields[4].equals("")) ? new Long(fields[4].trim()) : null;
					} catch (Exception e) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de línea " + lineNumber + " incompatible."
							+ " Campo documentoTipo incorrecto -> " + fields[4].trim()
						);
						ok = false;
					}
					
					String documento = 
						(fields[5] != null && !fields[5].equals("")) ? fields[5].trim() : null;
					String nombre = 
						(fields[6] != null && !fields[6].equals("")) ? fields[6].trim() : null;
					String direccion = 
						(fields[7] != null && !fields[7].equals("")) ? fields[7].trim() : null;
					
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
					
					String localidad = 
						(fields[9] != null && !fields[9].equals("")) ? fields[9].trim() : null;
					String equipo = 
						(fields[10] != null && !fields[10].equals("")) ? fields[10].trim() : null;
					String agente = 
						(fields[11] != null && !fields[11].equals("")) ? fields[11].trim() : null;
					
					Long numeroCliente = null;
					try {
						numeroCliente = 
							(fields[12] != null && !fields[12].equals("")) ? new Long(fields[12].trim()) : null;	
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
							(fields[13] != null && !fields[13].equals("")) ? new Long(fields[13].trim()) : null;	
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
							
							List<?> listContratoExisteEmpresa = selectContratoExisteEmpresa.list();
							if (listContratoExisteEmpresa.size() > 0) {
								// Si ya está en estado LLAMAR sobre-escribo.
								Long contratoId = (Long) listContratoExisteEmpresa.get(0);
								
								updateContrato.setParameter(3, agente, StringType.INSTANCE);
								updateContrato.setParameter(4, codigoPostal, StringType.INSTANCE);
								updateContrato.setParameter(5, direccion, StringType.INSTANCE);
								updateContrato.setParameter(6, documento, StringType.INSTANCE);
								updateContrato.setParameter(7, documentoTipo, LongType.INSTANCE);
								updateContrato.setParameter(8, equipo, StringType.INSTANCE);
								updateContrato.setParameter(9, fechaFinContrato, DateType.INSTANCE);
								updateContrato.setParameter(10, localidad, StringType.INSTANCE);
								updateContrato.setParameter(11, nombre, StringType.INSTANCE);
								updateContrato.setParameter(12, numeroCliente, LongType.INSTANCE);
								updateContrato.setParameter(13, numeroContrato, LongType.INSTANCE);
								updateContrato.setParameter(14, observaciones, StringType.INSTANCE);
								updateContrato.setParameter(15, tipoContratoCodigo, StringType.INSTANCE);
								updateContrato.setParameter(16, tipoContratoDescripcion, StringType.INSTANCE);
								updateContrato.setParameter(17, contratoId, LongType.INSTANCE);
								
								updateContrato.executeUpdate();
							} else {
								// Si no, creo el contrato.
								insertContrato.setParameter(6, agente, StringType.INSTANCE);
								insertContrato.setParameter(7, codigoPostal, StringType.INSTANCE);
								insertContrato.setParameter(8, direccion, StringType.INSTANCE);
								insertContrato.setParameter(9, documento, StringType.INSTANCE);
								insertContrato.setParameter(10, documentoTipo, LongType.INSTANCE);
								insertContrato.setParameter(11, equipo, StringType.INSTANCE);
								insertContrato.setParameter(12, fechaFinContrato, DateType.INSTANCE);
								insertContrato.setParameter(13, localidad, StringType.INSTANCE);
								insertContrato.setParameter(14, mid, LongType.INSTANCE);
								insertContrato.setParameter(15, nombre, StringType.INSTANCE);
								insertContrato.setParameter(16, numeroCliente, LongType.INSTANCE);
								insertContrato.setParameter(17, numeroContrato, LongType.INSTANCE);
								insertContrato.setParameter(18, observaciones, StringType.INSTANCE);
								insertContrato.setParameter(19, tipoContratoCodigo, StringType.INSTANCE);
								insertContrato.setParameter(20, tipoContratoDescripcion, StringType.INSTANCE);
								
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
			Empresa empresa = iEmpresaBean.getById(empresaId);
			
			Rol rol = iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
			
			Estado estado = iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Long estadoVendidoId = 
				new Long(Configuration.getInstance().getProperty("estado.VENDIDO"));
			Long estadoDistribuirId =
				new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR"));
			Long estadoActivarId =
				new Long(Configuration.getInstance().getProperty("estado.ACTIVAR"));
			Long estadoFaltaDocumentacionId =
				new Long(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			Long estadoRecoordinarId =
				new Long(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			Long estadoActDocVentaId =
				new Long(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"));
			Long estadoRedistribuirId =
				new Long(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"));
			Long estadoControlAntelId =
				new Long(Configuration.getInstance().getProperty("estado.CONTROLANTEL"));
			Long estadoACMId =
				new Long(Configuration.getInstance().getProperty("estado.ACM"));
			Long estadoReagendarId =
				new Long(Configuration.getInstance().getProperty("estado.REAGENDAR"));
			
			TypedQuery<Contrato> queryVendidos = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id IN ("
						+ " :estadoVendidoId, :estadoDistribuirId, :estadoActivarId, :estadoFaltaDocumentacionId,"
						+ " :estadoRecoordinarId, :estadoActDocVentaId, :estadoRedistribuirId, :estadoControlAntelId, :estadoACMId,"
						+ " :estadoReagendarId"
					+ " )"
					+ " AND c.mid = :mid", 
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
			queryVendidos.setParameter("mid", contrato.getMid());
			
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
			
			List<Contrato> resultList = queryVendidos.getResultList();
			if (resultList.size() == 0) {
				List<Contrato> resultListCallCenter = queryCallCenter.getResultList();
				
				if (resultListCallCenter.size() > 0) {
					Contrato contratoAnterior = resultListCallCenter.get(0);
					
					contrato.setId(contratoAnterior.getId());
					contrato.setNumeroTramite(contratoAnterior.getNumeroTramite());
				}
				
				contrato.setEmpresa(empresa);
				contrato.setEstado(estado);
				contrato.setRol(rol);
				
				Date date = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(date);
				contrato.setTerm(new Long(1));
				contrato.setUact(loggedUsuarioId);
				
				Contrato contratoManaged = this.update(contrato);
				
				ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
				contratoRoutingHistoryNew.setContrato(contratoManaged);
				contratoRoutingHistoryNew.setEmpresa(empresa);
				contratoRoutingHistoryNew.setEstado(contratoManaged.getEstado());
				contratoRoutingHistoryNew.setFecha(date);
				contratoRoutingHistoryNew.setRol(rol);
				
				contratoRoutingHistoryNew.setFact(date);
				contratoRoutingHistoryNew.setTerm(new Long(1));
				contratoRoutingHistoryNew.setUact(loggedUsuarioId);
				
				entityManager.persist(contratoRoutingHistoryNew);
				
				result = "Operación exitosa.";
			} else {
				result = "El MID ya fue vendido. No se puede asignar.";
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
			Usuario usuario = iUsuarioBean.getById(loggedUsuarioId);
			
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
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									new Double(valor)
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
				metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
				
				for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
					resultList.add((Contrato) object);
				}
			}
			
			Date currentDate = GregorianCalendar.getInstance().getTime();
			
			Long rolDistribuidorId = 
				new Long(Configuration.getInstance().getProperty("rol.Distribuidor"));
			
			Long estadoRecoordinarId = 
				new Long(Configuration.getInstance().getProperty("estado.RECOORDINAR"));
			
			Long estadoFaltaDocumentacionId =
				new Long(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"));
			
			Long estadoVendidoPorOtraEmpresaId =
				new Long(Configuration.getInstance().getProperty("estado.VENDIDOPOROTRAEMPRESA"));
			
			for (Contrato contrato : resultList) {
				Long diffInMilliseconds = 
					contrato.getFechaRechazo() != null ? (currentDate.getTime() - contrato.getFechaRechazo().getTime()) : 
						contrato.getFact() != null ? (currentDate.getTime() - contrato.getFact().getTime()) :
							null;
				
				Long cantidadDiasVendidoPorOtraEmpresa = 
					new Long(Configuration.getInstance().getProperty("cantidadDiasRetencionVendidoPorOtraEmpresa"));
				Long milisegundosSegundo = new Long(1000);
				Long segundosMinuto = new Long(60);
				Long minutosHora = new Long(60);
				Long horasDia = new Long(24);
				Long milisegundosDia = milisegundosSegundo * segundosMinuto * minutosHora * horasDia;
						
				if (estadoVendidoPorOtraEmpresaId.equals(contrato.getEstado().getId()) 
					&& diffInMilliseconds != null
					&& (diffInMilliseconds / milisegundosDia < cantidadDiasVendidoPorOtraEmpresa)) {
					// No asignar contratos vendidos por otra empresa en menos de 30 días.
				} else {
					if (!estadoFaltaDocumentacionId.equals(contrato.getEstado().getId())
						&& !estadoRecoordinarId.equals(contrato.getEstado().getId())) {
						contrato.setEstado(estado);
					}
					
					contrato.setRol(rol);
					contrato.setUsuario(usuario);
					
					contrato.setFact(currentDate);
					contrato.setTerm(new Long(1));
					contrato.setUact(loggedUsuarioId);
					
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
					contratoRoutingHistoryNew.setTerm(new Long(1));
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
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
			
			contratoRoutingHistoryNew.setFecha(date);
			contratoRoutingHistoryNew.setContrato(contratoManaged);
			contratoRoutingHistoryNew.setEmpresa(contratoManaged.getEmpresa());
			contratoRoutingHistoryNew.setEstado(contratoManaged.getEstado());
			contratoRoutingHistoryNew.setRol(rol);
			contratoRoutingHistoryNew.setUsuario(usuario);
			
			contratoRoutingHistoryNew.setFact(date);
			contratoRoutingHistoryNew.setTerm(new Long(1));
			contratoRoutingHistoryNew.setUact(loggedUsuarioId);
			
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
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Vendedor")));
		
		Estado estadoLlamar = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.LLAMAR")));
		
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
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Backoffice")));
		
		Estado estadoVendido = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.VENDIDO")));
		
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
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Distribuidor")));
		
		Estado estadoDistribuir = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR")));
		
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
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Activador")));
		
		Estado estadoActivar = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.ACTIVAR")));
		
		this.asignar(usuario, rolBackoffice, estadoActivar, metadataConsulta, loggedUsuarioId);
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
					new Long(Configuration.getInstance().getProperty("estado.VENDIDO"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			contrato.setFechaVenta(date);
			contrato.setVendedor(uact);
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
			Contrato contratoManaged = this.update(contrato);
			
			StockTipoMovimiento stockTipoMovimiento = iStockTipoMovimientoBean.getById(
				new Long(Configuration.getInstance().getProperty("stockTipoMovimiento.Venta"))
			);
			
			StockMovimiento stockMovimiento = new StockMovimiento();
			stockMovimiento.setCantidad(new Long(1) * stockTipoMovimiento.getSigno());
			stockMovimiento.setDocumentoId(contrato.getId());
			stockMovimiento.setFecha(date);
			
			stockMovimiento.setEmpresa(contrato.getEmpresa());
			stockMovimiento.setMarca(contrato.getMarca());
			stockMovimiento.setModelo(contrato.getModelo());
			stockMovimiento.setTipoProducto(contrato.getTipoProducto());
			stockMovimiento.setStockTipoMovimiento(stockTipoMovimiento);
			
			stockMovimiento.setFact(date);
			stockMovimiento.setTerm(new Long(1));
			stockMovimiento.setUact(uact.getId());
			
			iStockMovimientoBean.save(stockMovimiento);
			
			this.asignar(null, rol, contratoManaged, contrato.getUact());
			
			TypedQuery<Contrato> queryOtrasEmpresas = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado IN ( :estadoLlamar, :estadoRellamar, :estadoRechazado, :estadoRenovado )"
					+ " AND c.empresa <> :empresa"
					+ " AND c.mid = :mid", 
					Contrato.class);
			
			Estado estadoLlamar = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.LLAMAR"))
				);
			
			Estado estadoRellamar = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RELLAMAR"))
				);
			
			Estado estadoRechazado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RECHAZADO"))
				);
			
			Estado estadoRenovado =
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RENOVADO"))
				);
			
			queryOtrasEmpresas.setParameter("estadoLlamar", estadoLlamar);
			queryOtrasEmpresas.setParameter("estadoRellamar", estadoRellamar);
			queryOtrasEmpresas.setParameter("estadoRechazado", estadoRechazado);
			queryOtrasEmpresas.setParameter("estadoRenovado", estadoRenovado);
			queryOtrasEmpresas.setParameter("empresa", contrato.getEmpresa());
			queryOtrasEmpresas.setParameter("mid", contrato.getMid());
			
			Estado estadoVendidoPorOtraEmpresa = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.VENDIDOPOROTRAEMPRESA"))
				);
			
			Rol rolGerenteDeEmpresa =
				iRolBean.getById(
					new Long(Configuration.getInstance().getProperty("rol.GerenteDeEmpresa"))
				);
			
			for (Contrato contratoOtraEmpresa : queryOtrasEmpresas.getResultList()) {
				contratoOtraEmpresa.setEstado(estadoVendidoPorOtraEmpresa);
				contratoOtraEmpresa.setRol(rolGerenteDeEmpresa);
				contratoOtraEmpresa.setUsuario(null);
				contratoOtraEmpresa.setFechaRechazo(date);
				
				contratoOtraEmpresa.setFact(date);
				contratoOtraEmpresa.setTerm(new Long(1));
				contratoOtraEmpresa.setUact(uact.getId());
				
				Contrato contratoOtraEmpresaManaged = this.update(contratoOtraEmpresa);
				
				this.asignar(null, rolGerenteDeEmpresa, contratoOtraEmpresaManaged, uact.getId());
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
					new Long(Configuration.getInstance().getProperty("estado.RECHAZADO"))
				);
			
			Rol rolSupervisorCallCenter = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter"))
			);
			
			Rol rolSupervisorBackoffice = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			if (contrato.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Vendedor")))) {
				// Si estaba asignado a un Vendedor lo dejamos en la "bandeja" de Supervisores de Call-center.
				
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato.setVendedor(contrato.getUsuario());
			} else if (contrato.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Backoffice")))) {
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.RELLAMAR"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorDistribucion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			
			contrato.setFechaBackoffice(date);
			contrato.setBackoffice(uact);
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorDistribucion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.TELELINK"))
				);
			
			Rol rolSupervisorCallCenter = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter"))
			);
			
			Rol rolSupervisorBackoffice = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			if (contrato.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Vendedor")))) {
				// Si estaba asignado a un Vendedor lo dejamos en la "bandeja" de Supervisores de Call-center.
				
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato.setVendedor(uact);
			} else if (contrato.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Backoffice")))) {
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.RENOVADO"))
				);
			
			Rol rolSupervisorCallCenter = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter"))
			);
			
			Rol rolSupervisorBackoffice = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			if (contrato.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Vendedor")))) {
				// Si estaba asignado a un Vendedor lo dejamos en la "bandeja" de Supervisores de Call-center.
				
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato.setVendedor(uact);
			} else if (contrato.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Backoffice")))) {
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.REAGENDAR"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
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
			query.setParameter("rolId", new Long(Configuration.getInstance().getProperty("rol.Vendedor")));
			
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
				
				contratoRoutingHistoryReagendar.setFact(date);
				contratoRoutingHistoryReagendar.setTerm(new Long(1));
				contratoRoutingHistoryReagendar.setUact(contrato.getUact());
				
				entityManager.persist(contratoRoutingHistoryReagendar);
				
				contrato.setFechaVenta(contratoManaged.getFechaVenta());
				contrato.setVendedor(contratoManaged.getVendedor());
				contrato.setBackoffice(uact);
				contrato.setFechaBackoffice(date);
				
				contrato.setRol(contratoRoutingHistoryVendedor.getRol());
				contrato.setUsuario(contratoRoutingHistoryVendedor.getUsuario());
				
				contrato.setFact(date);
				contrato.setTerm(new Long(1));
				
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
					new Long(Configuration.getInstance().getProperty("estado.ACTIVAR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorActivacion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contratoManaged.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.NOFIRMA"))
				);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contratoManaged.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(contratoManaged.getRol());
			contrato.setUsuario(contrato.getUsuario());
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
			this.update(contrato);
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
					new Long(Configuration.getInstance().getProperty("estado.RECOORDINAR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Activador"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			
			contrato.setFechaEnvioAntel(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.CONTROLANTEL"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			
			contrato.setFechaEnvioAntel(date);
			contrato.setActivador(uact);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.ACM"))
				);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			
			contrato.setFechaActivacion(date);
			contrato.setActivador(uact);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.ACTIVAR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Activador"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setActivador(contratoManaged.getActivador());
			
			contrato.setCoordinador(uact);
			contrato.setEstado(estado);
			contrato.setFechaCoordinacion(date);
			contrato.setRol(rol);
			contrato.setUsuario(contratoManaged.getActivador());
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.NORECOORDINA"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorDistribucion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setActivador(contratoManaged.getActivador());
			
			contrato.setCoordinador(uact);
			contrato.setEstado(estado);
			contrato.setFechaCoordinacion(date);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.CERRADO"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorActivacion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setFechaEnvioAntel(contratoManaged.getFechaEnvioAntel());
			contrato.setActivador(contratoManaged.getActivador());
			contrato.setCoordinador(contratoManaged.getCoordinador());
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.GESTIONINTERNA"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorActivacion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			
			contrato.setActivador(null);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.GESTIONDISTRIBUCION"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Distribuidor"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
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
			contrato.setTerm(new Long(1));
			
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
					new Long(Configuration.getInstance().getProperty("estado.EQUIPOPERDIDO"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorActivacion"))
			);
			
			Usuario uact = iUsuarioBean.getById(contrato.getUact());
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			
			contrato.setActivador(null);
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			
			this.update(contrato);
			
			this.asignar(null, rol, contratoManaged, uact.getId());
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
				+ ";Fecha fin contrato"
				+ ";Código de plan"
				+ ";Descripción de plan"
				+ ";Tipo de documento"
				+ ";Documento"
				+ ";Nombre"
				+ ";Dirección"
				+ ";Código postal"
				+ ";Localidad"
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
				+ ";Forma de pago"
				+ ";Tarjeta de crédito"
				+ ";Número de serie"
				+ ";Observaciones"
				+ ";Fecha de venta"
				+ ";Fecha de back-office"
				+ ";Fecha de entrega a Distribuidor"
				+ ";Fecha de devuelto por Distribuidor"
				+ ";Fecha de envío a ANTEL"
//				+ ";Fecha de activación"
//				+ ";Fecha agendada de activación"
//				+ ";Fecha de coordinación"
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
			);
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				Contrato contrato = (Contrato) object;
				
				String line = 
					contrato.getMid()
					+ ";" + (contrato.getFechaFinContrato() != null ? 
						format.format(contrato.getFechaFinContrato())
						: "")
					+ ";" + (contrato.getTipoContratoCodigo() != null ?
						contrato.getTipoContratoCodigo() 
						: "")
					+ ";" + (contrato.getTipoContratoDescripcion() != null ?
						contrato.getTipoContratoDescripcion()
						: "")
					+ ";" + (contrato.getDocumentoTipo() != null ?
						contrato.getDocumentoTipo()
						: "")
					+ ";'" + (contrato.getDocumento() != null ?
						contrato.getDocumento()
						: "")
					+ ";" + (contrato.getNombre() != null ?
						contrato.getNombre()
						: "")
					+ ";" + (contrato.getDireccion() != null ?
						contrato.getDireccion()
						: "")
					+ ";" + (contrato.getCodigoPostal() != null ?
						contrato.getCodigoPostal()
						: "")
					+ ";" + (contrato.getLocalidad() != null ?
						contrato.getLocalidad()
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
					+ ";" + (contrato.getFormaPago() != null ?
						contrato.getFormaPago().getDescripcion()
						: "")
					+ ";" + (contrato.getTarjetaCredito() != null ?
						contrato.getTarjetaCredito().getNombre()
						: "")
					+ ";" + (contrato.getNumeroSerie() != null ?
						contrato.getNumeroSerie()
						: "")
					+ ";" + (contrato.getObservaciones() != null ?
						contrato.getObservaciones().replaceAll(";", ".").replaceAll("\"", "")
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
					+ ";" + (contrato.getFechaEnvioAntel() != null ?
						format.format(contrato.getFechaEnvioAntel())
						: "")
//					+ ";" + (contrato.getFechaActivacion() != null ?
//						format.format(contrato.getFechaActivacion())
//						: "")
//					+ ";" + (contrato.getFechaActivarEn() != null ?
//						format.format(contrato.getFechaActivarEn())
//						: "")
//					+ ";" + (contrato.getFechaCoordinacion() != null ?
//						format.format(contrato.getFechaCoordinacion())
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
						contrato.getProducto().getDescripcion()
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
						: "");
//					+ ";" + (contrato.getActivador() != null ?
//						contrato.getActivador().getNombre()
//						: "")
//					+ ";" + (contrato.getCoordinador() != null ?
//						contrato.getCoordinador().getNombre()
//						: "");
					
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
			codigosDepartamentos.put(new Long(1), new Long(11)); //"MONTEVIDEO"
			codigosDepartamentos.put(new Long(2), new Long(90)); //"CANELONES"
			codigosDepartamentos.put(new Long(3), new Long(55)); //"ARTIGAS"
			codigosDepartamentos.put(new Long(4), new Long(50)); //"SALTO"
			codigosDepartamentos.put(new Long(5), new Long(60)); //"PAYSANDU"
			codigosDepartamentos.put(new Long(6), new Long(65)); //"RIO NEGRO"
			codigosDepartamentos.put(new Long(7), new Long(75)); //"SORIANO"
			codigosDepartamentos.put(new Long(8), new Long(70)); //"COLONIA"
			codigosDepartamentos.put(new Long(9), new Long(85)); //"FLORES"
			codigosDepartamentos.put(new Long(10), new Long(94)); //"FLORIDA"
			codigosDepartamentos.put(new Long(11), new Long(97)); //"DURAZNO"
			codigosDepartamentos.put(new Long(12), new Long(30)); //"LAVALLEJA"
			codigosDepartamentos.put(new Long(13), new Long(20)); //"MALDONADO"
			codigosDepartamentos.put(new Long(14), new Long(27)); //"ROCHA"
			codigosDepartamentos.put(new Long(15), new Long(33)); //"TREINTA Y TRES"
			codigosDepartamentos.put(new Long(16), new Long(37)); //"CERRO LARGO"
			codigosDepartamentos.put(new Long(17), new Long(40)); //"RIVERA"
			codigosDepartamentos.put(new Long(18), new Long(45)); //"TACUAREMBO"
			codigosDepartamentos.put(new Long(19), new Long(80)); //"SAN JOSE"

			
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
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
			String valorNoDisponible = Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.formatoArchivoNucleo.valorNoDisponible");
			String producto = Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.formatoArchivoNucleo.producto");
			
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
					// TODO Ver si hay más productos
					+ ";" + producto
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
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
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
	 * Retorna el Contrato cuyo id coincide con el parámetro.
	 * 
	 * @param ID del contrato a retornar.
	 * @return Contrato a retornar.
	 */
	public Contrato getById(Long id) {
		Contrato result = null;
		
		try {
			result = entityManager.find(Contrato.class, id);
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
	public Contrato getByMidEmpresa(Long mid, Empresa empresa) {
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
			
			List<Contrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
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
	public Contrato getByNumeroTramite(Long numeroTramite) {
		Contrato result = null;
		
		try {
			TypedQuery<Contrato> query = entityManager.createQuery(
				"SELECT c FROM Contrato c WHERE c.numeroTramite = :numeroTramite", 
				Contrato.class
			);
			query.setParameter("numeroTramite", numeroTramite);
			
			List<Contrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
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
			if (contrato.getId() == null) {
				Query query = 
					entityManager.createNativeQuery(
						"SELECT nextval('numero_tramite_sequence')"
					);
				
				Long maxNumeroTramite = ((BigInteger) query.getSingleResult()).longValue();
				
				contrato.setNumeroTramite(maxNumeroTramite + 1);
				
				contrato.setFact(GregorianCalendar.getInstance().getTime());
				contrato.setTerm(new Long(1));
				
				entityManager.persist(contrato);
			} else {
				Long estadoVendidoId = 
					new Long(Configuration.getInstance().getProperty("estado.VENDIDO"));
				
				Long formaPagoNuestroCreditoId = 
					new Long(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
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
				
				entityManager.merge(contrato);
			}
			
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contrato;
	}
}