package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IPlanBean;
import uy.com.amensg.logistica.bean.PlanBean;
import uy.com.amensg.logistica.entities.Plan;
import uy.com.amensg.logistica.entities.PlanTO;
import uy.com.amensg.logistica.entities.TipoPlan;

@RemoteProxy
public class PlanDWR {

	private IPlanBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = PlanBean.class.getSimpleName();
		String remoteInterfaceName = IPlanBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IPlanBean) context.lookup(lookupName);
	}
	
	public Collection<PlanTO> list() {
		Collection<PlanTO> result = new LinkedList<PlanTO>();
		
		try {
			IPlanBean iPlanBean = lookupBean();
			
			for (Plan plan : iPlanBean.list()) {
				result.add(transform(plan));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PlanTO> listVigentes() {
		Collection<PlanTO> result = new LinkedList<PlanTO>();
		
		try {
			IPlanBean iPlanBean = lookupBean();
			
			for (Plan plan : iPlanBean.listVigentes()) {
				result.add(transform(plan));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public PlanTO getById(Long id) {
		PlanTO result = null;
		
		try {
			IPlanBean iPlanBean = lookupBean();
			
			result = transform(iPlanBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(PlanTO planTO) {
		try {
			IPlanBean iPlanBean = lookupBean();
			
			iPlanBean.save(transform(planTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(PlanTO planTO) {
		try {
			IPlanBean iPlanBean = lookupBean();
			
			iPlanBean.remove(transform(planTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(PlanTO planTO) {
		try {
			IPlanBean iPlanBean = lookupBean();
			
			iPlanBean.update(transform(planTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PlanTO transform(Plan plan) {
		PlanTO planTO = new PlanTO();
		
		planTO.setAbreviacion(plan.getAbreviacion());
		planTO.setCantidadCelularesAntelMinutosGratis(plan.getCantidadCelularesAntelMinutosGratis());
		planTO.setCantidadCelularesAntelSmsGratis(plan.getCantidadCelularesAntelSmsGratis());
		planTO.setCantidadFijosAntelMinutosGratis(plan.getCantidadFijosAntelMinutosGratis());
		planTO.setConsumoMinimo(plan.getConsumoMinimo());
		planTO.setDescripcion(plan.getDescripcion());
		planTO.setDestinosGratis(plan.getDestinosGratis());
		planTO.setDuracion(plan.getDuracion());
		planTO.setFechaBaja(plan.getFechaBaja());
		planTO.setMinutosGratisMesCelularesAntel(plan.getMinutosGratisMesCelularesAntel());
		planTO.setMinutosGratisMesFijosAntel(plan.getMinutosGratisMesFijosAntel());
		planTO.setMontoNavegacionCelular(plan.getMontoNavegacionCelular());
		planTO.setPrecioConsumoFueraBono(plan.getPrecioConsumoFueraBono());
		planTO.setPrecioMinutoDestinosAntelHorarioNormal(plan.getPrecioMinutoDestinosAntelHorarioNormal());
		planTO.setPrecioMinutoDestinosAntelHorarioReducido(plan.getPrecioMinutoDestinosAntelHorarioReducido());
		planTO.setPrecioMinutoOtrasOperadoras(plan.getPrecioMinutoOtrasOperadoras());
		planTO.setPrecioSms(plan.getPrecioSms());
		planTO.setRendimientoMinutosMensualDestinosAntelHorarioNormal(plan.getRendimientoMinutosMensualDestinosAntelHorarioNormal());
		planTO.setRendimientoMinutosMensualDestinosAntelHorarioReducido(plan.getRendimientoMinutosMensualDestinosAntelHorarioReducido());
		planTO.setRendimientoMinutosMensualOtrasOperadoras(plan.getRendimientoMinutosMensualOtrasOperadoras());
		planTO.setSmsGratisMesCelularesAntel(plan.getSmsGratisMesCelularesAntel());
		planTO.setTopeFacturacionMensualTraficoDatos(plan.getTopeFacturacionMensualTraficoDatos());
		
		if (plan.getTipoPlan() != null) {
			planTO.setTipoPlan(TipoPlanDWR.transform(plan.getTipoPlan()));
		}
		
		planTO.setFact(plan.getFact());
		planTO.setId(plan.getId());
		planTO.setTerm(plan.getTerm());
		planTO.setUact(plan.getUact());
		
		return planTO;
	}
	
	public static Plan transform(PlanTO planTO) {
		Plan plan = new Plan();
		
		plan.setAbreviacion(planTO.getAbreviacion());
		plan.setCantidadCelularesAntelMinutosGratis(planTO.getCantidadCelularesAntelMinutosGratis());
		plan.setCantidadCelularesAntelSmsGratis(planTO.getCantidadCelularesAntelSmsGratis());
		plan.setCantidadFijosAntelMinutosGratis(planTO.getCantidadFijosAntelMinutosGratis());
		plan.setConsumoMinimo(planTO.getConsumoMinimo());
		plan.setDescripcion(planTO.getDescripcion());
		plan.setDestinosGratis(planTO.getDestinosGratis());
		plan.setDuracion(planTO.getDuracion());
		plan.setFechaBaja(planTO.getFechaBaja());
		plan.setMinutosGratisMesCelularesAntel(planTO.getMinutosGratisMesCelularesAntel());
		plan.setMinutosGratisMesFijosAntel(planTO.getMinutosGratisMesFijosAntel());
		plan.setMontoNavegacionCelular(planTO.getMontoNavegacionCelular());
		plan.setPrecioConsumoFueraBono(planTO.getPrecioConsumoFueraBono());
		plan.setPrecioMinutoDestinosAntelHorarioNormal(planTO.getPrecioMinutoDestinosAntelHorarioNormal());
		plan.setPrecioMinutoDestinosAntelHorarioReducido(planTO.getPrecioMinutoDestinosAntelHorarioReducido());
		plan.setPrecioMinutoOtrasOperadoras(planTO.getPrecioMinutoOtrasOperadoras());
		plan.setPrecioSms(planTO.getPrecioSms());
		plan.setRendimientoMinutosMensualDestinosAntelHorarioNormal(planTO.getRendimientoMinutosMensualDestinosAntelHorarioNormal());
		plan.setRendimientoMinutosMensualDestinosAntelHorarioReducido(planTO.getRendimientoMinutosMensualDestinosAntelHorarioReducido());
		plan.setRendimientoMinutosMensualOtrasOperadoras(planTO.getRendimientoMinutosMensualOtrasOperadoras());
		plan.setSmsGratisMesCelularesAntel(planTO.getSmsGratisMesCelularesAntel());
		plan.setTopeFacturacionMensualTraficoDatos(planTO.getTopeFacturacionMensualTraficoDatos());
		
		if (planTO.getTipoPlan() != null) {
			TipoPlan tipoPlan = new TipoPlan();
			tipoPlan.setId(planTO.getTipoPlan().getId());
			
			plan.setTipoPlan(tipoPlan);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		plan.setFact(date);
		plan.setId(planTO.getId());
		plan.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		plan.setUact(usuarioId);
		
		return plan;
	}
}