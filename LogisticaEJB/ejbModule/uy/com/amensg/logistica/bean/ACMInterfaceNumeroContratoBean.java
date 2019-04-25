package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceNumeroContratoBean implements IACMInterfaceNumeroContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	private CriteriaQuery<ACMInterfaceNumeroContrato> criteriaQuery;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Query para obtener los registros de muestra
			TypedQuery<ACMInterfaceNumeroContrato> queryMuestra = this.construirQuery(metadataConsulta);
			queryMuestra.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfaceNumeroContrato acmInterfaceNumeroContrato : queryMuestra.getResultList()) {
				registrosMuestra.add(acmInterfaceNumeroContrato);
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
			
			Root<ACMInterfaceNumeroContrato> rootCount = criteriaQueryCount.from(ACMInterfaceNumeroContrato.class);
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
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
			
//			if (metadataConsulta.getMetadataCondiciones().size() >= 2) {
//				Iterator<MetadataCondicion> iterator = metadataConsulta.getMetadataCondiciones().iterator();
//				
//				MetadataCondicion metadataCondicion = iterator.next();
//				
//				Long numeroContratoDesde = new Long(metadataCondicion.getValores().iterator().next());
//				
//				metadataCondicion = iterator.next();
//				
//				Long numeroContratoHasta = new Long(metadataCondicion.getValores().iterator().next());
//				
//				for (long i=numeroContratoDesde; i<numeroContratoHasta; i++) {
//					ACMInterfaceNumeroContrato acmInterfaceNumeroContrato = new ACMInterfaceNumeroContrato();
//					acmInterfaceNumeroContrato.setEstado(estado);
//					acmInterfaceNumeroContrato.setNumeroContrato(i);
//					acmInterfaceNumeroContrato.setProcesoId(acmInterfaceProceso.getId());
//					
//					acmInterfaceNumeroContrato.setUact(new Long(1));
//					acmInterfaceNumeroContrato.setUcre(new Long(1));
//					acmInterfaceNumeroContrato.setFact(hoy);
//					acmInterfaceNumeroContrato.setFcre(hoy);
//					acmInterfaceNumeroContrato.setTerm(new Long(1));
//					
//					entityManager.merge(acmInterfaceNumeroContrato);
//				}
//			}
			
			for (ACMInterfaceNumeroContrato acmInterfaceNumeroContrato : this.listSubconjunto(metadataConsulta)) {
				acmInterfaceNumeroContrato.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceNumeroContrato.setEstado(estado);
				
				acmInterfaceNumeroContrato.setUact(new Long(1));
				acmInterfaceNumeroContrato.setFact(hoy);
				acmInterfaceNumeroContrato.setTerm(new Long(1));
				
				entityManager.merge(acmInterfaceNumeroContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Collection<ACMInterfaceNumeroContrato> listSubconjunto(MetadataConsulta metadataConsulta) {
		Collection<ACMInterfaceNumeroContrato> resultList = new LinkedList<ACMInterfaceNumeroContrato>();
		
		TypedQuery<ACMInterfaceNumeroContrato> query = this.construirQuery(metadataConsulta);
		
		if (metadataConsulta.getTamanoSubconjunto() != null) {
			List<ACMInterfaceNumeroContrato> toOrder = query.getResultList();
			
			Collections.shuffle(toOrder);
			
			int i = 0;
			for (ACMInterfaceNumeroContrato acmInterfaceNumeroContrato : toOrder) {
				resultList.add(acmInterfaceNumeroContrato);
				
				i++;
				if (i == metadataConsulta.getTamanoSubconjunto()) {
					break;
				}
			}
		} else {
			resultList = query.getResultList();
		}
		
		return resultList;
	}
	
	private TypedQuery<ACMInterfaceNumeroContrato> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceNumeroContrato.class);
		
		Root<ACMInterfaceNumeroContrato> root = criteriaQuery.from(ACMInterfaceNumeroContrato.class);
		
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
		
		TypedQuery<ACMInterfaceNumeroContrato> query = entityManager.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			for (String valor : metadataCondicion.getValores()) {
				String[] campos = metadataCondicion.getCampo().split("\\.");
				
				Path<ACMInterfaceNumeroContrato> campo = root;
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
		
		// Setear los par�metros seg�n las condiciones del filtro
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