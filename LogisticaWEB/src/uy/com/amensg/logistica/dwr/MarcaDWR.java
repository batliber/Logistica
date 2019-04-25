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
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

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
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IMarcaBean iMarcaBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iMarcaBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object marca : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(MarcaDWR.transform((Marca) marca));
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
				
				IMarcaBean iMarcaBean = lookupBean();
				
				result = 
					iMarcaBean.count(
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
		MarcaTO result = new MarcaTO();
		
		result.setNombre(marca.getNombre());
		result.setFechaBaja(marca.getFechaBaja());
		
		result.setFcre(marca.getFcre());
		result.setFact(marca.getFact());
		result.setId(marca.getId());
		result.setTerm(marca.getTerm());
		result.setUact(marca.getUact());
		result.setUcre(marca.getUcre());
		
		return result;
	}
	
	public static Marca transform(MarcaTO marcaTO) {
		Marca result = new Marca();
		
		result.setNombre(marcaTO.getNombre());
		result.setFechaBaja(marcaTO.getFechaBaja());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(marcaTO.getFcre());
		result.setFact(date);
		result.setId(marcaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(marcaTO.getUcre());
		
		return result;
	}
}