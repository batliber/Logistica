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

import uy.com.amensg.logistica.bean.EmpresaBean;
import uy.com.amensg.logistica.bean.IEmpresaBean;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaTO;

@RemoteProxy
public class EmpresaDWR {

	private IEmpresaBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = EmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEmpresaBean) context.lookup(lookupName);
	}
	
	public Collection<EmpresaTO> list() {
		Collection<EmpresaTO> result = new LinkedList<EmpresaTO>();
		
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			for (Empresa empresa : iEmpresaBean.list()) {
				result.add(transform(empresa));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EmpresaTO getById(Long id) {
		EmpresaTO result = null;
		
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			result = transform(iEmpresaBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(EmpresaTO empresaTO) {
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			iEmpresaBean.save(transform(empresaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(EmpresaTO empresaTO) {
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			iEmpresaBean.remove(transform(empresaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(EmpresaTO empresaTO) {
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			iEmpresaBean.update(transform(empresaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EmpresaTO transform(Empresa empresa) {
		EmpresaTO empresaTO = new EmpresaTO();
		
		empresaTO.setNombre(empresa.getNombre());
		
		empresaTO.setFact(empresa.getFact());
		empresaTO.setId(empresa.getId());
		empresaTO.setTerm(empresa.getTerm());
		empresaTO.setUact(empresa.getUact());
		
		return empresaTO;
	}

	public static Empresa transform(EmpresaTO empresaTO) {
		Empresa empresa = new Empresa();
		
		empresa.setNombre(empresaTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		empresa.setFact(date);
		empresa.setId(empresaTO.getId());
		empresa.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		empresa.setUact(usuarioId);
		
		return empresa;
	}
}