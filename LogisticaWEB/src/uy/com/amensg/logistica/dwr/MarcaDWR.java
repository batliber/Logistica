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

import uy.com.amensg.logistica.bean.IMarcaBean;
import uy.com.amensg.logistica.bean.MarcaBean;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MarcaTO;

@RemoteProxy
public class MarcaDWR {

	private IMarcaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MarcaBean.class.getSimpleName();
		String remoteInterfaceName = IMarcaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IMarcaBean) context.lookup(lookupName);
	}
	
	public Collection<MarcaTO> list() {
		Collection<MarcaTO> result = new LinkedList<MarcaTO>();
		
		try {
			IMarcaBean iMarcaBean = lookupBean();
			
			for (Marca marca : iMarcaBean.list()) {
				result.add(transform(marca));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MarcaTO getById(Long id) {
		MarcaTO result = null;
		
		try {
			IMarcaBean iMarcaBean = lookupBean();
			
			result = transform(iMarcaBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(MarcaTO marcaTO) {
		try {
			IMarcaBean iMarcaBean = lookupBean();
			
			iMarcaBean.save(transform(marcaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(MarcaTO marcaTO) {
		try {
			IMarcaBean iMarcaBean = lookupBean();
			
			iMarcaBean.remove(transform(marcaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(MarcaTO marcaTO) {
		try {
			IMarcaBean iMarcaBean = lookupBean();
			
			iMarcaBean.update(transform(marcaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MarcaTO transform(Marca marca) {
		MarcaTO marcaTO = new MarcaTO();
		
		marcaTO.setNombre(marca.getNombre());
		marcaTO.setFechaBaja(marca.getFechaBaja());
		
		marcaTO.setFact(marca.getFact());
		marcaTO.setId(marca.getId());
		marcaTO.setTerm(marca.getTerm());
		marcaTO.setUact(marca.getUact());
		
		return marcaTO;
	}
	
	public static Marca transform(MarcaTO marcaTO) {
		Marca marca = new Marca();
		
		marca.setNombre(marcaTO.getNombre());
		marca.setFechaBaja(marcaTO.getFechaBaja());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		marca.setFact(date);
		marca.setId(marcaTO.getId());
		marca.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		marca.setUact(usuarioId);
		
		return marca;
	}
}