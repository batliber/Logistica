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

import uy.com.amensg.logistica.bean.ITasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.bean.TasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;
import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnualTO;

@RemoteProxy
public class TasaInteresEfectivaAnualDWR {
	
	private ITasaInteresEfectivaAnualBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TasaInteresEfectivaAnualBean.class.getSimpleName();
		String remoteInterfaceName = ITasaInteresEfectivaAnualBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITasaInteresEfectivaAnualBean) context.lookup(lookupName);
	}
	
	public Collection<TasaInteresEfectivaAnualTO> list() {
		Collection<TasaInteresEfectivaAnualTO> result = new LinkedList<TasaInteresEfectivaAnualTO>();
		
		try {
			ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
			
			for (TasaInteresEfectivaAnual tasaInteresEfectivaAnual : iTasaInteresEfectivaAnualBean.list()) {
				result.add(transform(tasaInteresEfectivaAnual));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<TasaInteresEfectivaAnualTO> listVigentes() {
		Collection<TasaInteresEfectivaAnualTO> result = new LinkedList<TasaInteresEfectivaAnualTO>();
		
		try {
			ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
			
			for (TasaInteresEfectivaAnual tasaInteresEfectivaAnual : iTasaInteresEfectivaAnualBean.listVigentes()) {
				result.add(transform(tasaInteresEfectivaAnual));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TasaInteresEfectivaAnualTO getById(Long id) {
		TasaInteresEfectivaAnualTO result = null;
		
		try {
			ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
			
			result = transform(iTasaInteresEfectivaAnualBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(TasaInteresEfectivaAnualTO tasaInteresEfectivaAnualTO) {
		try {
			ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
			
			iTasaInteresEfectivaAnualBean.save(transform(tasaInteresEfectivaAnualTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(TasaInteresEfectivaAnualTO tasaInteresEfectivaAnualTO) {
		try {
			ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
			
			iTasaInteresEfectivaAnualBean.remove(transform(tasaInteresEfectivaAnualTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static TasaInteresEfectivaAnualTO transform(TasaInteresEfectivaAnual tasaInteresEfectivaAnual) {
		TasaInteresEfectivaAnualTO result = new TasaInteresEfectivaAnualTO();
		
		result.setCuotasDesde(tasaInteresEfectivaAnual.getCuotasDesde());
		result.setCuotasHasta(tasaInteresEfectivaAnual.getCuotasHasta());
		result.setFechaVigenciaHasta(tasaInteresEfectivaAnual.getFechaVigenciaHasta());
		result.setMontoDesde(tasaInteresEfectivaAnual.getMontoDesde());
		result.setMontoHasta(tasaInteresEfectivaAnual.getMontoHasta());
		result.setValor(tasaInteresEfectivaAnual.getValor());
		
		result.setFact(tasaInteresEfectivaAnual.getFact());
		result.setId(tasaInteresEfectivaAnual.getId());
		result.setTerm(tasaInteresEfectivaAnual.getTerm());
		result.setUact(tasaInteresEfectivaAnual.getUact());
		
		return result;
	}

	public static TasaInteresEfectivaAnual transform(TasaInteresEfectivaAnualTO tasaInteresEfectivaAnualTO) {
		TasaInteresEfectivaAnual result = new TasaInteresEfectivaAnual();
		
		result.setCuotasDesde(tasaInteresEfectivaAnualTO.getCuotasDesde());
		result.setCuotasHasta(tasaInteresEfectivaAnualTO.getCuotasHasta());
		result.setFechaVigenciaHasta(tasaInteresEfectivaAnualTO.getFechaVigenciaHasta());
		result.setMontoDesde(tasaInteresEfectivaAnualTO.getMontoDesde());
		result.setMontoHasta(tasaInteresEfectivaAnualTO.getMontoHasta());
		result.setValor(tasaInteresEfectivaAnualTO.getValor());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(tasaInteresEfectivaAnualTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}