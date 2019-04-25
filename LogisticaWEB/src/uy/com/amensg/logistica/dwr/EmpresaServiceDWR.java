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

import uy.com.amensg.logistica.bean.EmpresaServiceBean;
import uy.com.amensg.logistica.bean.IEmpresaServiceBean;
import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.EmpresaServiceTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class EmpresaServiceDWR {

	private IEmpresaServiceBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EmpresaServiceBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaServiceBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEmpresaServiceBean) context.lookup(lookupName);
	}
	
	public Collection<EmpresaServiceTO> list() {
		Collection<EmpresaServiceTO> result = new LinkedList<EmpresaServiceTO>();
		
		try {
			IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
			
			for (EmpresaService empresaService : iEmpresaServiceBean.list()) {
				result.add(transform(empresaService));
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
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iEmpresaServiceBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object empresaService : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(EmpresaServiceDWR.transform((EmpresaService) empresaService));
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
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				result = 
					iEmpresaServiceBean.count(
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
	
	public EmpresaServiceTO getById(Long id) {
		EmpresaServiceTO result = null;
		
		try {
			IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
			
			result = transform(iEmpresaServiceBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(EmpresaServiceTO empresaServiceTO) {
		try {
			IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
			
			iEmpresaServiceBean.save(transform(empresaServiceTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(EmpresaServiceTO empresaServiceTO) {
		try {
			IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
			
			iEmpresaServiceBean.remove(transform(empresaServiceTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(EmpresaServiceTO empresaServiceTO) {
		try {
			IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
			
			iEmpresaServiceBean.update(transform(empresaServiceTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EmpresaServiceTO transform(EmpresaService empresaService) {
		EmpresaServiceTO result = new EmpresaServiceTO();
		
		result.setDireccion(empresaService.getDireccion());
		result.setFechaBaja(empresaService.getFechaBaja());
		result.setNombre(empresaService.getNombre());
		result.setTelefono(empresaService.getTelefono());
		
		result.setFcre(empresaService.getFcre());
		result.setFact(empresaService.getFact());
		result.setId(empresaService.getId());
		result.setTerm(empresaService.getTerm());
		result.setUact(empresaService.getUact());
		result.setUcre(empresaService.getUcre());
		
		return result;
	}

	public static EmpresaService transform(EmpresaServiceTO empresaServiceTO) {
		EmpresaService result = new EmpresaService();
		
		result.setDireccion(empresaServiceTO.getDireccion());
		result.setFechaBaja(empresaServiceTO.getFechaBaja());
		result.setNombre(empresaServiceTO.getNombre());
		result.setTelefono(empresaServiceTO.getTelefono());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(empresaServiceTO.getFcre());
		result.setFact(date);
		result.setId(empresaServiceTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(empresaServiceTO.getUcre());
		
		return result;
	}
}