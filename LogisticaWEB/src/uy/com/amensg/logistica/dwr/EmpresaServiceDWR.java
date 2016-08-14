package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EmpresaServiceBean;
import uy.com.amensg.logistica.bean.IEmpresaServiceBean;
import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.EmpresaServiceTO;

@RemoteProxy
public class EmpresaServiceDWR {

	private IEmpresaServiceBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = EmpresaServiceBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaServiceBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
		EmpresaServiceTO empresaServiceTO = new EmpresaServiceTO();
		
		empresaServiceTO.setDireccion(empresaService.getDireccion());
		empresaServiceTO.setFechaBaja(empresaService.getFechaBaja());
		empresaServiceTO.setNombre(empresaService.getNombre());
		empresaServiceTO.setTelefono(empresaService.getTelefono());
		
		empresaServiceTO.setFact(empresaService.getFact());
		empresaServiceTO.setId(empresaService.getId());
		empresaServiceTO.setTerm(empresaService.getTerm());
		empresaServiceTO.setUact(empresaService.getUact());
		
		return empresaServiceTO;
	}

	public static EmpresaService transform(EmpresaServiceTO empresaServiceTO) {
		EmpresaService empresaService = new EmpresaService();
		
		empresaService.setDireccion(empresaServiceTO.getDireccion());
		empresaService.setFechaBaja(empresaServiceTO.getFechaBaja());
		empresaService.setNombre(empresaServiceTO.getNombre());
		empresaService.setTelefono(empresaServiceTO.getTelefono());
		
		empresaService.setFact(empresaServiceTO.getFact());
		empresaService.setId(empresaServiceTO.getId());
		empresaService.setTerm(empresaServiceTO.getTerm());
		empresaService.setUact(empresaServiceTO.getUact());
		
		return empresaService;
	}
}