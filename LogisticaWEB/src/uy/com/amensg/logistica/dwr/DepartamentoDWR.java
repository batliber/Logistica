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

import uy.com.amensg.logistica.bean.DepartamentoBean;
import uy.com.amensg.logistica.bean.IDepartamentoBean;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.DepartamentoTO;

@RemoteProxy
public class DepartamentoDWR {

	private IDepartamentoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = DepartamentoBean.class.getSimpleName();
		String remoteInterfaceName = IDepartamentoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
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
		DepartamentoTO result = new DepartamentoTO();
		
		result.setNombre(departamento.getNombre());
		
		result.setFcre(departamento.getFcre());
		result.setFact(departamento.getFact());
		result.setId(departamento.getId());
		result.setTerm(departamento.getTerm());
		result.setUact(departamento.getUact());
		result.setUcre(departamento.getUcre());
		
		return result;
	}
	
	public static Departamento transform(DepartamentoTO departamentoTO) {
		Departamento result = new Departamento();
		
		result.setNombre(departamentoTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(departamentoTO.getFcre());
		result.setFact(date);
		result.setId(departamentoTO.getId());
		result.setTerm(departamentoTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(departamentoTO.getUcre());
		
		return result;
	}
}