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
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
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
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPlanBean iPlanBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iPlanBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object plan : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(PlanDWR.transform((Plan) plan));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPlanBean iPlanBean = lookupBean();
				
				result = 
					iPlanBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
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
		PlanTO result = new PlanTO();
		
		result.setAbreviacion(plan.getAbreviacion());
		result.setBeneficioIncluidoEnLlamadas(plan.getBeneficioIncluidoEnLlamadas());
		result.setCantidadCelularesAntelMinutosGratis(plan.getCantidadCelularesAntelMinutosGratis());
		result.setCantidadCelularesAntelSmsGratis(plan.getCantidadCelularesAntelSmsGratis());
		result.setCantidadFijosAntelMinutosGratis(plan.getCantidadFijosAntelMinutosGratis());
		result.setConsumoMinimo(plan.getConsumoMinimo());
		result.setDescripcion(plan.getDescripcion());
		result.setDestinosGratis(plan.getDestinosGratis());
		result.setDuracion(plan.getDuracion());
		result.setFechaBaja(plan.getFechaBaja());
		result.setMinutosGratisMesCelularesAntel(plan.getMinutosGratisMesCelularesAntel());
		result.setMinutosGratisMesFijosAntel(plan.getMinutosGratisMesFijosAntel());
		result.setMontoNavegacionCelular(plan.getMontoNavegacionCelular());
		result.setPiePagina(plan.getPiePagina());
		result.setPrecioConsumoFueraBono(plan.getPrecioConsumoFueraBono());
		result.setPrecioMinutoDestinosAntelHorarioNormal(plan.getPrecioMinutoDestinosAntelHorarioNormal());
		result.setPrecioMinutoDestinosAntelHorarioReducido(plan.getPrecioMinutoDestinosAntelHorarioReducido());
		result.setPrecioMinutoNumerosAmigos(plan.getPrecioMinutoNumerosAmigos());
		result.setPrecioMinutoOtrasOperadoras(plan.getPrecioMinutoOtrasOperadoras());
		result.setPrecioSms(plan.getPrecioSms());
		result.setRendimientoMinutosMensualDestinosAntelHorarioNormal(plan.getRendimientoMinutosMensualDestinosAntelHorarioNormal());
		result.setRendimientoMinutosMensualDestinosAntelHorarioReducido(plan.getRendimientoMinutosMensualDestinosAntelHorarioReducido());
		result.setRendimientoMinutosMensualOtrasOperadoras(plan.getRendimientoMinutosMensualOtrasOperadoras());
		result.setSmsGratisMesCelularesAntel(plan.getSmsGratisMesCelularesAntel());
		result.setTopeFacturacionMensualTraficoDatos(plan.getTopeFacturacionMensualTraficoDatos());
		
		if (plan.getTipoPlan() != null) {
			result.setTipoPlan(TipoPlanDWR.transform(plan.getTipoPlan()));
		}
		
		result.setFcre(plan.getFcre());
		result.setFact(plan.getFact());
		result.setId(plan.getId());
		result.setTerm(plan.getTerm());
		result.setUact(plan.getUact());
		result.setUcre(plan.getUcre());
		
		return result;
	}
	
	public static Plan transform(PlanTO planTO) {
		Plan result = new Plan();
		
		result.setAbreviacion(planTO.getAbreviacion());
		result.setBeneficioIncluidoEnLlamadas(planTO.getBeneficioIncluidoEnLlamadas());
		result.setCantidadCelularesAntelMinutosGratis(planTO.getCantidadCelularesAntelMinutosGratis());
		result.setCantidadCelularesAntelSmsGratis(planTO.getCantidadCelularesAntelSmsGratis());
		result.setCantidadFijosAntelMinutosGratis(planTO.getCantidadFijosAntelMinutosGratis());
		result.setConsumoMinimo(planTO.getConsumoMinimo());
		result.setDescripcion(planTO.getDescripcion());
		result.setDestinosGratis(planTO.getDestinosGratis());
		result.setDuracion(planTO.getDuracion());
		result.setFechaBaja(planTO.getFechaBaja());
		result.setMinutosGratisMesCelularesAntel(planTO.getMinutosGratisMesCelularesAntel());
		result.setMinutosGratisMesFijosAntel(planTO.getMinutosGratisMesFijosAntel());
		result.setMontoNavegacionCelular(planTO.getMontoNavegacionCelular());
		result.setPiePagina(planTO.getPiePagina());
		result.setPrecioConsumoFueraBono(planTO.getPrecioConsumoFueraBono());
		result.setPrecioMinutoDestinosAntelHorarioNormal(planTO.getPrecioMinutoDestinosAntelHorarioNormal());
		result.setPrecioMinutoDestinosAntelHorarioReducido(planTO.getPrecioMinutoDestinosAntelHorarioReducido());
		result.setPrecioMinutoNumerosAmigos(planTO.getPrecioMinutoNumerosAmigos());
		result.setPrecioMinutoOtrasOperadoras(planTO.getPrecioMinutoOtrasOperadoras());
		result.setPrecioSms(planTO.getPrecioSms());
		result.setRendimientoMinutosMensualDestinosAntelHorarioNormal(planTO.getRendimientoMinutosMensualDestinosAntelHorarioNormal());
		result.setRendimientoMinutosMensualDestinosAntelHorarioReducido(planTO.getRendimientoMinutosMensualDestinosAntelHorarioReducido());
		result.setRendimientoMinutosMensualOtrasOperadoras(planTO.getRendimientoMinutosMensualOtrasOperadoras());
		result.setSmsGratisMesCelularesAntel(planTO.getSmsGratisMesCelularesAntel());
		result.setTopeFacturacionMensualTraficoDatos(planTO.getTopeFacturacionMensualTraficoDatos());
		
		if (planTO.getTipoPlan() != null) {
			TipoPlan tipoPlan = new TipoPlan();
			tipoPlan.setId(planTO.getTipoPlan().getId());
			
			result.setTipoPlan(tipoPlan);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(planTO.getFcre());
		result.setFact(date);
		result.setId(planTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(planTO.getUcre());
		
		return result;
	}
}