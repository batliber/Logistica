package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.EJB;
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
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.StockActual;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.StockTipoMovimiento;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class StockMovimientoBean implements IStockMovimientoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	@EJB
	IProductoBean iProductoBean;
	
	@EJB
	IStockTipoMovimientoBean iStockTipoMovimientoBean;
	
	@EJB
	IEmpresaBean iEmpresaBean;
	
	public Collection<StockMovimiento> listStockActual() {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT s.empresa.id, s.marca.id, s.modelo.id, s.tipoProducto.id, SUM(s.cantidad) AS cantidad"
				+ " FROM StockMovimiento s"
				+ " GROUP BY s.empresa.id, s.marca.id, s.modelo.id, s.tipoProducto.id"
			);
			
			for (Object object : query.getResultList()) {
				Object[] values = (Object[]) object;
				
				StockMovimiento stockMovimiento = new StockMovimiento();
				
				Empresa empresa = entityManager.find(Empresa.class, (Long) values[0]);
				
				stockMovimiento.setEmpresa(empresa);
				
				Marca marca = entityManager.find(Marca.class, (Long) values[1]);
				
				stockMovimiento.setMarca(marca);
				
				Modelo modelo = entityManager.find(Modelo.class, (Long) values[2]);
				
				stockMovimiento.setModelo(modelo);
				
				TipoProducto tipoProducto = entityManager.find(TipoProducto.class, (Long) values[3]);
				
				stockMovimiento.setTipoProducto(tipoProducto);
				
				stockMovimiento.setCantidad((Long) values[4]);
				
				result.add(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado listStockActual(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<StockActual>().list(entityManager, metadataConsulta, new StockActual());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long countStockActual(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<StockActual>().count(entityManager, metadataConsulta, new StockActual());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado listStockActual(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<StockActual> criteriaQuery = criteriaBuilder.createQuery(StockActual.class);
			
			Root<StockActual> root = criteriaQuery.from(StockActual.class);
			root.alias("root");
			
			QueryHelper queryHelper = new QueryHelper();
			
			Predicate where = queryHelper.construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryUsuarioRolEmpresa = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootUsuarioRolEmpresa = subqueryUsuarioRolEmpresa.from(UsuarioRolEmpresa.class);
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryUsuarioRolEmpresa
						.select(subrootUsuarioRolEmpresa)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(
									subrootUsuarioRolEmpresa.get("usuario").get("id"), 
									criteriaBuilder.parameter(Long.class, "usuario")
								),
								criteriaBuilder.equal(
									subrootUsuarioRolEmpresa.get("empresa").get("id"), 
									root.get("empresa").get("id")
								)
							)
						)
				)
			);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = queryHelper.construirOrderBy(metadataConsulta, criteriaBuilder, root);
			
			criteriaQuery
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<StockActual> query = entityManager.createQuery(criteriaQuery);
			
			query = queryHelper.cargarParameters(metadataConsulta, query, root);
			
			query.setParameter("usuario", usuarioId);
			
			// Acotar al tamaño de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (StockActual stockActual: query.getResultList()) {
				registrosMuestra.add(stockActual);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long countStockActual(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			Root<StockActual> rootCount = criteriaQueryCount.from(StockActual.class);
			rootCount.alias("root");
			
			QueryHelper queryHelper = new QueryHelper();
			
			Predicate where = queryHelper.construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Subquery<UsuarioRolEmpresa> subqueryUsuarioRolEmpresa = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootUsuarioRolEmpresa = subqueryUsuarioRolEmpresa.from(UsuarioRolEmpresa.class);
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryUsuarioRolEmpresa
						.select(subrootUsuarioRolEmpresa)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(
									subrootUsuarioRolEmpresa.get("usuario").get("id"), 
									criteriaBuilder.parameter(Long.class, "usuario")
								),
								criteriaBuilder.equal(
									subrootUsuarioRolEmpresa.get("empresa").get("id"), 
									rootCount.get("empresa").get("id")
								)
							)
						)
				)
			);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = queryHelper.construirOrderBy(metadataConsulta, criteriaBuilder, rootCount);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);

			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount = queryHelper.cargarParameters(metadataConsulta, queryCount, rootCount);
			
			queryCount.setParameter("usuario", usuarioId);
			
			result = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<StockMovimiento> listStockByEmpresaId(Long id) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT s.marca.id, s.modelo.id, s.tipoProducto.id, SUM(s.cantidad) AS cantidad"
				+ " FROM StockMovimiento s"
				+ " WHERE s.empresa.id = :empresaId"
				+ " AND s.modelo.fechaBaja IS NULL"
				+ " GROUP BY s.marca.id, s.modelo.id, s.tipoProducto.id"
			);
			query.setParameter("empresaId", id);
			
			for (Object object : query.getResultList()) {
				Object[] values = (Object[]) object;
				
				StockMovimiento stockMovimiento = new StockMovimiento();
				
				Marca marca = entityManager.find(Marca.class, (Long) values[0]);
				
				stockMovimiento.setMarca(marca);
				
				Modelo modelo = entityManager.find(Modelo.class, (Long) values[1]);
				
				stockMovimiento.setModelo(modelo);
				
				TipoProducto tipoProducto = entityManager.find(TipoProducto.class, (Long) values[2]);
				
				stockMovimiento.setTipoProducto(tipoProducto);
				
				stockMovimiento.setCantidad((Long) values[3]);
				
				result.add(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<StockMovimiento> listStockByEmpresaTipoProducto(Empresa empresa, TipoProducto tipoProducto) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT s.marca.id, s.modelo.id, s.tipoProducto.id, SUM(s.cantidad) AS cantidad"
				+ " FROM StockMovimiento s"
				+ " WHERE s.empresa.id = :empresaId"
				+ " AND s.tipoProducto.id = :tipoProductoId"
				+ " AND s.modelo.fechaBaja IS NULL"
				+ " GROUP BY s.marca.id, s.modelo.id, s.tipoProducto.id"
			);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("tipoProductoId", tipoProducto.getId());
			
			for (Object object : query.getResultList()) {
				Object[] values = (Object[]) object;
				
				StockMovimiento stockMovimiento = new StockMovimiento();
				
				Marca marcaManaged = entityManager.find(Marca.class, (Long) values[0]);
				
				stockMovimiento.setMarca(marcaManaged);
				
				Modelo modeloManaged = entityManager.find(Modelo.class, (Long) values[1]);
				
				stockMovimiento.setModelo(modeloManaged);
				
				TipoProducto tipoProductoManaged = entityManager.find(TipoProducto.class, (Long) values[2]);
				
				stockMovimiento.setTipoProducto(tipoProductoManaged);
				
				stockMovimiento.setCantidad((Long) values[3]);
				
				result.add(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<StockMovimiento> listByIMEI(String imei) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			Producto producto = iProductoBean.getByIMEI(imei);
			
			TypedQuery<StockMovimiento> query = 
				entityManager.createQuery(
					"SELECT sm"
					+ " FROM StockMovimiento sm"
					+ " WHERE sm.producto = :producto"
					+ " ORDER BY sm.id DESC", 
					StockMovimiento.class
				);
			query.setParameter("producto", producto);
			
			for (StockMovimiento stockMovimiento : query.getResultList()) {
				result.add(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public StockMovimiento getLastByIMEI(String imei) {
		StockMovimiento result = null;
		
		try {
			TypedQuery<StockMovimiento> query =
				entityManager.createQuery(
					"SELECT sm"
					+ " FROM StockMovimiento sm"
					+ " WHERE sm.producto.imei = :imei"
					+ " ORDER BY sm.id DESC",
					StockMovimiento.class
				);
			query.setParameter("imei", imei);
			query.setMaxResults(1);
			
			List<StockMovimiento> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public StockMovimiento save(StockMovimiento stockMovimiento) {
		StockMovimiento result = null;
		
		try {
			stockMovimiento.setFcre(stockMovimiento.getFact());
			stockMovimiento.setUcre(stockMovimiento.getUact());
			
			entityManager.persist(stockMovimiento);
			
			result = stockMovimiento;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(Collection<StockMovimiento> stockMovimientos) {
		try {
			for (StockMovimiento stockMovimiento : stockMovimientos) {
				if (stockMovimiento.getProducto().getId() == null) {
					stockMovimiento.getProducto().setFcre(stockMovimiento.getFact());
					stockMovimiento.getProducto().setUcre(stockMovimiento.getUact());
					
					entityManager.persist(stockMovimiento.getProducto());
				}
				
				stockMovimiento.setFcre(stockMovimiento.getFact());
				stockMovimiento.setUcre(stockMovimiento.getUact());
				
				entityManager.persist(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void transferir(Collection<StockMovimiento> stockMovimientos, Long empresaDestinoId) {
		try {
			StockTipoMovimiento stockTipoMovimientoBaja =
				iStockTipoMovimientoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("stockTipoMovimiento.BajaPorTransferencia")));
			
			StockTipoMovimiento stockTipoMovimientoAlta =
				iStockTipoMovimientoBean.getById(Long.parseLong(Configuration.getInstance().getProperty("stockTipoMovimiento.AltaPorTransferencia")));
			
			Empresa empresaDestino = 
				iEmpresaBean.getById(empresaDestinoId, false);
			
			for (StockMovimiento stockMovimiento : stockMovimientos) {
				StockMovimiento stockMovimientoBaja = new StockMovimiento();
				stockMovimientoBaja.setCantidad(Long.valueOf(1) * stockTipoMovimientoBaja.getSigno());
				stockMovimientoBaja.setDocumentoId(Long.valueOf(-1));
				stockMovimientoBaja.setEmpresa(stockMovimiento.getEmpresa());
				stockMovimientoBaja.setFecha(stockMovimiento.getFact());
				stockMovimientoBaja.setMarca(stockMovimiento.getMarca());
				stockMovimientoBaja.setModelo(stockMovimiento.getModelo());
				stockMovimientoBaja.setTipoProducto(stockMovimiento.getTipoProducto());
				stockMovimientoBaja.setProducto(stockMovimiento.getProducto());
				stockMovimientoBaja.setStockTipoMovimiento(stockTipoMovimientoBaja);
				
				stockMovimientoBaja.setFcre(stockMovimiento.getFact());
				stockMovimientoBaja.setFact(stockMovimiento.getFact());
				stockMovimientoBaja.setTerm(stockMovimiento.getTerm());
				stockMovimientoBaja.setUact(stockMovimiento.getUact());
				stockMovimientoBaja.setUcre(stockMovimiento.getUcre());
				
				entityManager.persist(stockMovimientoBaja);
				
				StockMovimiento stockMovimientoAlta = new StockMovimiento();
				stockMovimientoAlta.setCantidad(Long.valueOf(1) * stockTipoMovimientoAlta.getSigno());
				stockMovimientoAlta.setDocumentoId(Long.valueOf(1));
				stockMovimientoAlta.setEmpresa(empresaDestino);
				stockMovimientoAlta.setFecha(stockMovimiento.getFact());
				stockMovimientoAlta.setMarca(stockMovimiento.getMarca());
				stockMovimientoAlta.setModelo(stockMovimiento.getModelo());
				stockMovimientoAlta.setTipoProducto(stockMovimiento.getTipoProducto());
				stockMovimientoAlta.setProducto(stockMovimiento.getProducto());
				stockMovimientoAlta.setStockTipoMovimiento(stockTipoMovimientoAlta);
				
				stockMovimientoAlta.setFcre(stockMovimiento.getFact());
				stockMovimientoAlta.setFact(stockMovimiento.getFact());
				stockMovimientoAlta.setTerm(stockMovimiento.getTerm());
				stockMovimientoAlta.setUact(stockMovimiento.getUact());
				stockMovimientoAlta.setUcre(stockMovimiento.getUact());
				
				entityManager.persist(stockMovimientoAlta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}