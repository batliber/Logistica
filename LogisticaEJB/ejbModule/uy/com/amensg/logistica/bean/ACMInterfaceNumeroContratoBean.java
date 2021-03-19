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
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;
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
			return new QueryBuilder<ACMInterfaceNumeroContrato>().list(
				entityManager, metadataConsulta, new ACMInterfaceNumeroContrato()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ACMInterfaceNumeroContrato>().count(
				entityManager, metadataConsulta, new ACMInterfaceNumeroContrato()
			);
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
			
			acmInterfaceProceso.setUact(Long.valueOf(1));
			acmInterfaceProceso.setFact(hoy);
			acmInterfaceProceso.setTerm(Long.valueOf(1));
			
			acmInterfaceProceso = iACMInterfaceProcesoBean.save(acmInterfaceProceso);
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario"))
				);
			
//			if (metadataConsulta.getMetadataCondiciones().size() >= 2) {
//				Iterator<MetadataCondicion> iterator = metadataConsulta.getMetadataCondiciones().iterator();
//				
//				MetadataCondicion metadataCondicion = iterator.next();
//				
//				Long numeroContratoDesde = Long.parseLong(metadataCondicion.getValores().iterator().next());
//				
//				metadataCondicion = iterator.next();
//				
//				Long numeroContratoHasta = Long.parseLong(metadataCondicion.getValores().iterator().next());
//				
//				for (long i=numeroContratoDesde; i<numeroContratoHasta; i++) {
//					ACMInterfaceNumeroContrato acmInterfaceNumeroContrato = new ACMInterfaceNumeroContrato();
//					acmInterfaceNumeroContrato.setEstado(estado);
//					acmInterfaceNumeroContrato.setNumeroContrato(i);
//					acmInterfaceNumeroContrato.setProcesoId(acmInterfaceProceso.getId());
//					
//					acmInterfaceNumeroContrato.setUact(Long.valueOf(1));
//					acmInterfaceNumeroContrato.setUcre(Long.valueOf(1));
//					acmInterfaceNumeroContrato.setFact(hoy);
//					acmInterfaceNumeroContrato.setFcre(hoy);
//					acmInterfaceNumeroContrato.setTerm(Long.valueOf(1));
//					
//					entityManager.merge(acmInterfaceNumeroContrato);
//				}
//			}
			
			for (ACMInterfaceNumeroContrato acmInterfaceNumeroContrato : this.listSubconjunto(metadataConsulta)) {
				acmInterfaceNumeroContrato.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceNumeroContrato.setEstado(estado);
				
				acmInterfaceNumeroContrato.setUact(Long.valueOf(1));
				acmInterfaceNumeroContrato.setFact(hoy);
				acmInterfaceNumeroContrato.setTerm(Long.valueOf(1));
				
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
}