package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceMidBean implements IACMInterfaceMidBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	@EJB
	private IACMInterfaceContratoBean iACMInterfaceContratoBean;
	
	@EJB
	private IACMInterfacePrepagoBean iACMInterfacePrepagoBean;
	
	private CriteriaQuery<ACMInterfaceMid> criteriaQuery;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Query para obtener los registros de muestra
			TypedQuery<ACMInterfaceMid> queryMuestra = this.construirQuery(metadataConsulta);
			queryMuestra.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfaceMid acmInterfaceMid : queryMuestra.getResultList()) {
				registrosMuestra.add(acmInterfaceMid);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			Root<ACMInterfaceMid> rootCount = criteriaQueryCount.from(ACMInterfaceMid.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			this.setQueryParameters(criteriaQueryCount, queryCount, metadataConsulta);
			
			result = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			// Generación de proceso
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFechaInicio(hoy);
			acmInterfaceProceso.setObservaciones(observaciones);
			
			acmInterfaceProceso.setUact(new Long(1));
			acmInterfaceProceso.setFact(hoy);
			acmInterfaceProceso.setTerm(new Long(1));
			
			acmInterfaceProceso = iACMInterfaceProcesoBean.save(acmInterfaceProceso);
			
			// Obtener la cantidad de registros.
			Long cantidadRegistros = this.count(metadataConsulta);
			
			Long cantidadFinal =
				metadataConsulta.getTamanoSubconjunto() != null ? 
					Math.min(cantidadRegistros, metadataConsulta.getTamanoSubconjunto())
					: cantidadRegistros;
			
			Long cantidadRegistrosPagina = new Long(Configuration.getInstance().getProperty("acmInterfaceMid.cantidadRegistrosPagina"));
			Long cantidadPaginas = cantidadRegistros / cantidadRegistrosPagina;
			
			// Query para generar los criterios
			TypedQuery<ACMInterfaceMid> query = this.construirQuery(metadataConsulta);
						
			query.setMaxResults(cantidadRegistrosPagina.intValue());
			
			Map<Long, ACMInterfaceMid> resultMap = new HashMap<Long, ACMInterfaceMid>();
			
			Random random = new Random();
			
			int i = 0;
			while (resultMap.size() < cantidadFinal) {
				query.setFirstResult(cantidadRegistrosPagina.intValue() * i);
				
				for (ACMInterfaceMid acmInterfaceMid : query.getResultList()) {
					if (!resultMap.containsKey(acmInterfaceMid.getMid()) 
						&& resultMap.size() < cantidadFinal
						&& random.nextBoolean()) {
						resultMap.put(acmInterfaceMid.getMid(), acmInterfaceMid);
					}
				}
				
				i = (i + random.nextInt(cantidadPaginas.intValue())) % cantidadPaginas.intValue();
			}
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
			
			for (ACMInterfaceMid acmInterfaceMid : resultMap.values()) {
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceMid.setEstado(estado);
				
				acmInterfaceMid.setUact(new Long(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(new Long(1));
				
				entityManager.merge(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta) {
		try {
			iACMInterfaceContratoBean.agregarAListaNegra(metadataConsulta);
			iACMInterfacePrepagoBean.agregarAListaNegra(metadataConsulta);
			
			TypedQuery<ACMInterfaceMid> query = this.construirQuery(metadataConsulta);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceEstado estado = 
				entityManager.find(ACMInterfaceEstado.class, new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra")));
			
			for (ACMInterfaceMid acmInterfaceMid : query.getResultList()) {
				if (!acmInterfaceMid.getEstado().getId().equals(
						new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra"))
					)
				) {
					acmInterfaceMid.setEstado(estado);
					
					acmInterfaceMid.setUact(new Long(1));
					acmInterfaceMid.setFact(hoy);
					acmInterfaceMid.setTerm(new Long(1));
					
					entityManager.merge(acmInterfaceMid); 
					
					ACMInterfaceListaNegra acmInterfaceListaNegra = new ACMInterfaceListaNegra();
					
					acmInterfaceListaNegra.setMid(acmInterfaceMid.getMid());
					acmInterfaceListaNegra.setObservaciones(
						Configuration.getInstance().getProperty("listaNegra.NoProcesado")
					);
					
					acmInterfaceListaNegra.setTerm(new Long(1));
					acmInterfaceListaNegra.setFact(hoy);
					acmInterfaceListaNegra.setUact(new Long(1));
					
					entityManager.persist(acmInterfaceListaNegra);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfaceMid acmInterfaceMid) {
		try {
			entityManager.merge(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TypedQuery<ACMInterfaceMid> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceMid.class);
		
		Root<ACMInterfaceMid> root = criteriaQuery.from(ACMInterfaceMid.class);
		
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
			.orderBy(orders)
			.where(new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root));
		
		TypedQuery<ACMInterfaceMid> query = entityManager.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			for (String valor : metadataCondicion.getValores()) {
				String[] campos = metadataCondicion.getCampo().split("\\.");
				
				Path<ACMInterfaceMid> campo = root;
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = root.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (join != null) {
					campo = join.get(campos[campos.length - 1]);
				} else {
					campo = root.get(campos[campos.length - 1]);
				}
				
				try {
					if (campo.getJavaType().equals(Date.class)) {
						query.setParameter(
							"p" + i,
							format.parse(valor)
						);
					} else if (campo.getJavaType().equals(Long.class)) {
						query.setParameter(
							"p" + i,
							new Long(valor)
						);
					} else if (campo.getJavaType().equals(String.class)) {
						query.setParameter(
							"p" + i,
							valor
						);
					} else if (campo.getJavaType().equals(Double.class)) {
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
		
		return query;
	}

	private void setQueryParameters(CriteriaQuery<?> criteriaQuery, TypedQuery<?> query, MetadataConsulta metadataConsulta) {
		Root<?> root = criteriaQuery.getRoots().iterator().next();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		// Setear los parámetros según las condiciones del filtro
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
				for (String valor : metadataCondicion.getValores()) {
					String[] campos = metadataCondicion.getCampo().split("\\.");
					
					Path<?> field = root;
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
	}
}