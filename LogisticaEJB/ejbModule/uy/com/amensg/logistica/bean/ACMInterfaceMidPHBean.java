package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceMidPHBean implements IACMInterfaceMidPHBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitPHXA")
	private EntityManager entityManagerPH;
	
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
			CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			Root<ACMInterfaceMid> rootCount = criteriaQueryCount.from(ACMInterfaceMid.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManagerPH.createQuery(criteriaQueryCount);
			
			this.setQueryParameters(criteriaQueryCount, queryCount, metadataConsulta);
			
			result = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private TypedQuery<ACMInterfaceMid> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManagerPH.getCriteriaBuilder();
		
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
		
		TypedQuery<ACMInterfaceMid> query = entityManagerPH.createQuery(criteriaQuery);
		
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
							Long.parseLong(valor)
						);
					} else if (campo.getJavaType().equals(String.class)) {
						query.setParameter(
							"p" + i,
							valor
						);
					} else if (campo.getJavaType().equals(Double.class)) {
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
	}
}