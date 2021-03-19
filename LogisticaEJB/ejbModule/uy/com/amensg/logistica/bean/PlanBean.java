package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Plan;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class PlanBean implements IPlanBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Plan> list() {
		Collection<Plan> result = new LinkedList<Plan>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Plan p"
				+ " ORDER BY p.descripcion"
			);
			
			for (Object object : query.getResultList()) {
				result.add((Plan) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Plan> listMinimal() {
		Collection<Plan> result = new LinkedList<Plan>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT p.id, p.descripcion"
					+ " FROM Plan p"
					+ " ORDER BY p.descripcion ASC", 
					Object[].class
				);
			
			for (Object[] plan : query.getResultList()) {
				Plan planMinimal = new Plan();
				planMinimal.setId((Long)plan[0]);
				planMinimal.setDescripcion((String)plan[1]);
				
				result.add(planMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Plan> listVigentes() {
		Collection<Plan> result = new LinkedList<Plan>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Plan p"
				+ " WHERE p.fechaBaja IS NULL"
				+ " ORDER BY p.descripcion"
			);
			
			for (Object object : query.getResultList()) {
				result.add((Plan) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Plan> listVigentesMinimal() {
		Collection<Plan> result = new LinkedList<Plan>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT p.id, p.descripcion"
					+ " FROM Plan p"
					+ " WHERE p.fechaBaja IS NULL"
					+ " ORDER BY p.descripcion ASC", 
					Object[].class
				);
			
			for (Object[] plan : query.getResultList()) {
				Plan planMinimal = new Plan();
				planMinimal.setId((Long)plan[0]);
				planMinimal.setDescripcion((String)plan[1]);
				
				result.add(planMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<Plan>().list(entityManager, metadataConsulta, new Plan());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Plan>().count(entityManager, metadataConsulta, new Plan());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Plan getById(Long id) {
		Plan result = null;
		
		try {
			result = entityManager.find(Plan.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Plan save(Plan plan) {
		Plan result = null;
		
		try {
			plan.setFcre(plan.getFact());
			plan.setUcre(plan.getUact());
			
			entityManager.persist(plan);
			
			result = plan;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(Plan plan) {
		try {
			Plan managedPlan = entityManager.find(Plan.class, plan.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			managedPlan.setFechaBaja(date);
			
			managedPlan.setFact(plan.getFact());
			managedPlan.setTerm(plan.getTerm());
			managedPlan.setUact(plan.getUact());
			
			entityManager.merge(managedPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Plan plan) {
		try {
			Plan planManaged = entityManager.find(Plan.class, plan.getId());
			
			planManaged.setAbreviacion(plan.getAbreviacion());
			planManaged.setBeneficioIncluidoEnLlamadas(plan.getBeneficioIncluidoEnLlamadas());
			planManaged.setCantidadCelularesAntelMinutosGratis(plan.getCantidadCelularesAntelMinutosGratis());
			planManaged.setCantidadCelularesAntelSmsGratis(plan.getCantidadCelularesAntelSmsGratis());
			planManaged.setCantidadFijosAntelMinutosGratis(plan.getCantidadFijosAntelMinutosGratis());
			planManaged.setConsumoMinimo(plan.getConsumoMinimo());
			planManaged.setDescripcion(plan.getDescripcion());
			planManaged.setDestinosGratis(plan.getDestinosGratis());
			planManaged.setDuracion(plan.getDuracion());
			planManaged.setMinutosGratisMesCelularesAntel(plan.getMinutosGratisMesCelularesAntel());
			planManaged.setMinutosGratisMesFijosAntel(plan.getMinutosGratisMesFijosAntel());
			planManaged.setMontoNavegacionCelular(plan.getMontoNavegacionCelular());
			planManaged.setPiePagina(plan.getPiePagina());
			planManaged.setPrecioConsumoFueraBono(plan.getPrecioConsumoFueraBono());
			planManaged.setPrecioMinutoDestinosAntelHorarioNormal(plan.getPrecioMinutoDestinosAntelHorarioNormal());
			planManaged.setPrecioMinutoDestinosAntelHorarioReducido(plan.getPrecioMinutoDestinosAntelHorarioReducido());
			planManaged.setPrecioMinutoNumerosAmigos(plan.getPrecioMinutoNumerosAmigos());
			planManaged.setPrecioMinutoOtrasOperadoras(plan.getPrecioMinutoOtrasOperadoras());
			planManaged.setPrecioSms(plan.getPrecioSms());
			planManaged.setRendimientoMinutosMensualDestinosAntelHorarioNormal(plan.getRendimientoMinutosMensualDestinosAntelHorarioNormal());
			planManaged.setRendimientoMinutosMensualDestinosAntelHorarioReducido(plan.getRendimientoMinutosMensualDestinosAntelHorarioReducido());
			planManaged.setRendimientoMinutosMensualOtrasOperadoras(plan.getRendimientoMinutosMensualOtrasOperadoras());
			planManaged.setSmsGratisMesCelularesAntel(plan.getSmsGratisMesCelularesAntel());
			planManaged.setTopeFacturacionMensualTraficoDatos(plan.getTopeFacturacionMensualTraficoDatos());
			
			planManaged.setTipoPlan(plan.getTipoPlan());
			
			planManaged.setFact(plan.getFact());
			planManaged.setFechaBaja(plan.getFechaBaja());
			planManaged.setTerm(plan.getTerm());
			planManaged.setUact(plan.getUact());
			
			entityManager.merge(planManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}