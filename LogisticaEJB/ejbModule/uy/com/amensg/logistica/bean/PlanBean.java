package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import uy.com.amensg.logistica.entities.Plan;

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

	public Plan getById(Long id) {
		Plan result = null;
		
		try {
			result = entityManager.find(Plan.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Plan plan) {
		try {
			entityManager.persist(plan);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			planManaged.setPrecioConsumoFueraBono(plan.getPrecioConsumoFueraBono());
			planManaged.setPrecioMinutoDestinosAntelHorarioNormal(plan.getPrecioMinutoDestinosAntelHorarioNormal());
			planManaged.setPrecioMinutoDestinosAntelHorarioReducido(plan.getPrecioMinutoDestinosAntelHorarioReducido());
			planManaged.setPrecioMinutoOtrasOperadoras(plan.getPrecioMinutoOtrasOperadoras());
			planManaged.setPrecioSms(plan.getPrecioSms());
			planManaged.setRendimientoMinutosMensualDestinosAntelHorarioNormal(plan.getRendimientoMinutosMensualDestinosAntelHorarioNormal());
			planManaged.setRendimientoMinutosMensualDestinosAntelHorarioReducido(plan.getRendimientoMinutosMensualDestinosAntelHorarioReducido());
			planManaged.setRendimientoMinutosMensualOtrasOperadoras(plan.getRendimientoMinutosMensualOtrasOperadoras());
			planManaged.setSmsGratisMesCelularesAntel(plan.getSmsGratisMesCelularesAntel());
			planManaged.setTopeFacturacionMensualTraficoDatos(plan.getTopeFacturacionMensualTraficoDatos());
			
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