package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.DepartamentoBean;
import uy.com.amensg.logistica.bean.IDepartamentoBean;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.DepartamentoTO;

@RemoteProxy
public class DepartamentoDWR {

	private IDepartamentoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = DepartamentoBean.class.getSimpleName();
		String remoteInterfaceName = IDepartamentoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IDepartamentoBean) context.lookup(lookupName);
	}
	
	public Collection<DepartamentoTO> list() {
		Collection<DepartamentoTO> result = new LinkedList<DepartamentoTO>();
		
		try {
			IDepartamentoBean iDepartamentoBean = lookupBean();
			
			for (Departamento departamento : iDepartamentoBean.list()) {
				result.add(transform(departamento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static DepartamentoTO transform(Departamento departamento) {
		DepartamentoTO departamentoTO = new DepartamentoTO();
		
		departamentoTO.setNombre(departamento.getNombre());
		
		departamentoTO.setFact(departamento.getFact());
		departamentoTO.setId(departamento.getId());
		departamentoTO.setUact(departamento.getUact());
		departamentoTO.setTerm(departamento.getTerm());
		
		return departamentoTO;
	}
	
	public static Departamento transform(DepartamentoTO departamentoTO) {
		Departamento departamento = new Departamento();
		
		departamento.setNombre(departamentoTO.getNombre());
		
		departamento.setFact(departamentoTO.getFact());
		departamento.setId(departamentoTO.getId());
		departamento.setUact(departamentoTO.getUact());
		departamento.setTerm(departamentoTO.getTerm());
		
		return departamento;
	}
}