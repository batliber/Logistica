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

import uy.com.amensg.logistica.bean.IUnidadIndexadaBean;
import uy.com.amensg.logistica.bean.UnidadIndexadaBean;
import uy.com.amensg.logistica.entities.UnidadIndexada;
import uy.com.amensg.logistica.entities.UnidadIndexadaTO;

@RemoteProxy
public class UnidadIndexadaDWR {

	private IUnidadIndexadaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UnidadIndexadaBean.class.getSimpleName();
		String remoteInterfaceName = IUnidadIndexadaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IUnidadIndexadaBean) context.lookup(lookupName);
	}
	
	public Collection<UnidadIndexadaTO> list() {
		Collection<UnidadIndexadaTO> result = new LinkedList<UnidadIndexadaTO>();
		
		try {
			IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
			
			for (UnidadIndexada unidadIndexada : iUnidadIndexadaBean.list()) {
				result.add(transform(unidadIndexada));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UnidadIndexadaTO getById(Long id) {
		UnidadIndexadaTO result = null;
		
		try {
			IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
			
			result = transform(iUnidadIndexadaBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(UnidadIndexadaTO unidadIndexadaTO) {
		try {
			IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
			
			iUnidadIndexadaBean.save(transform(unidadIndexadaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(UnidadIndexadaTO unidadIndexadaTO) {
		try {
			IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
			
			iUnidadIndexadaBean.remove(transform(unidadIndexadaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static UnidadIndexadaTO transform(UnidadIndexada unidadIndexada) {
		UnidadIndexadaTO result = new UnidadIndexadaTO();
		
		result.setValor(unidadIndexada.getValor());
		result.setFechaVigenciaHasta(unidadIndexada.getFechaVigenciaHasta());
		
		result.setFact(unidadIndexada.getFact());
		result.setId(unidadIndexada.getId());
		result.setTerm(unidadIndexada.getTerm());
		result.setUact(unidadIndexada.getUact());
		
		return result;
	}

	public static UnidadIndexada transform(UnidadIndexadaTO unidadIndexadaTO) {
		UnidadIndexada result = new UnidadIndexada();
		
		result.setFechaVigenciaHasta(unidadIndexadaTO.getFechaVigenciaHasta());
		result.setValor(unidadIndexadaTO.getValor());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(unidadIndexadaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}