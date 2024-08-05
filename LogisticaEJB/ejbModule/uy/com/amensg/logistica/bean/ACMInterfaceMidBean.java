package uy.com.amensg.logistica.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import jakarta.ejb.EJB;
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
import jakarta.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceMidBean implements IACMInterfaceMidBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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
			return new QueryBuilder<ACMInterfaceMid>().list(
				entityManager, metadataConsulta, new ACMInterfaceMid()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta) {
		Long result = null;
		
		try {
			result = new QueryBuilder<ACMInterfaceMid>().count(
				entityManager, metadataConsulta, new ACMInterfaceMid()
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
			
			Random random = new Random();
			
			for (ACMInterfaceMid acmInterfaceMid : this.listSubconjunto(metadataConsulta)) {
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceMid.setEstado(estado);
				acmInterfaceMid.setRandom(Long.valueOf(random.nextInt()));
				
				acmInterfaceMid.setUact(Long.valueOf(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(Long.valueOf(1));
				
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
				entityManager.find(ACMInterfaceEstado.class, Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra")));
			
			for (ACMInterfaceMid acmInterfaceMid : query.getResultList()) {
				if (!acmInterfaceMid.getEstado().getId().equals(
						Long.parseLong(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra"))
					)
				) {
					acmInterfaceMid.setEstado(estado);
					
					acmInterfaceMid.setUact(Long.valueOf(1));
					acmInterfaceMid.setFact(hoy);
					acmInterfaceMid.setTerm(Long.valueOf(1));
					
					entityManager.merge(acmInterfaceMid); 
					
					ACMInterfaceListaNegra acmInterfaceListaNegra = new ACMInterfaceListaNegra();
					
					acmInterfaceListaNegra.setMid(acmInterfaceMid.getMid());
					acmInterfaceListaNegra.setObservaciones(
						Configuration.getInstance().getProperty("listaNegra.NoProcesado")
					);
					
					acmInterfaceListaNegra.setTerm(Long.valueOf(1));
					acmInterfaceListaNegra.setFact(hoy);
					acmInterfaceListaNegra.setUact(Long.valueOf(1));
					
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

	private Collection<ACMInterfaceMid> listSubconjunto(MetadataConsulta metadataConsulta) {
		Collection<ACMInterfaceMid> result = new LinkedList<ACMInterfaceMid>();
		
		Collection<MetadataOrdenacion> metadataOrdenaciones = new LinkedList<MetadataOrdenacion>();
		
		MetadataOrdenacion metadataOrdenacion = new MetadataOrdenacion();
		metadataOrdenacion.setAscendente(true);
		metadataOrdenacion.setCampo("random");
		
		metadataOrdenaciones.add(metadataOrdenacion);
		
		metadataConsulta.setMetadataOrdenaciones(metadataOrdenaciones);
		
		TypedQuery<ACMInterfaceMid> query = this.construirQuery(metadataConsulta);
		
		if (metadataConsulta.getTamanoSubconjunto() != null) {
			query.setMaxResults(metadataConsulta.getTamanoSubconjunto().intValue());
		}
		
		result = query.getResultList();
		
		return result;
	}
}