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
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.FormaPagoTO;

@RemoteProxy
public class EmpresaDWR {

	private IEmpresaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
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
	
	public Collection<FormaPagoTO> listFormasPagoById(Long id) {
		Collection<FormaPagoTO> result = new LinkedList<FormaPagoTO>();
		
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			for (FormaPago formaPago : iEmpresaBean.listFormasPagoById(id)) {
				result.add(FormaPagoDWR.transform(formaPago));
			}
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
		
		empresaTO.setCodigoPromotor(empresa.getCodigoPromotor());
		empresaTO.setLogoURL(empresa.getLogoURL());
		empresaTO.setNombre(empresa.getNombre());
		empresaTO.setNombreContrato(empresa.getNombreContrato());
		empresaTO.setNombreSucursal(empresa.getNombreSucursal());
		
		if (empresa.getFormaPagos() != null) {
			Collection<FormaPagoTO> formaPagos = new LinkedList<FormaPagoTO>();
			
			for (FormaPago formaPago : empresa.getFormaPagos()) {
				formaPagos.add(FormaPagoDWR.transform(formaPago));
			}
			
			empresaTO.setFormaPagos(formaPagos);
		}
		
		empresaTO.setFact(empresa.getFact());
		empresaTO.setId(empresa.getId());
		empresaTO.setTerm(empresa.getTerm());
		empresaTO.setUact(empresa.getUact());
		
		return empresaTO;
	}

	public static Empresa transform(EmpresaTO empresaTO) {
		Empresa empresa = new Empresa();
		
		empresa.setCodigoPromotor(empresaTO.getCodigoPromotor());
		empresa.setLogoURL(empresaTO.getLogoURL());
		empresa.setNombre(empresaTO.getNombre());
		empresa.setNombreContrato(empresaTO.getNombreContrato());
		empresa.setNombreSucursal(empresaTO.getNombreSucursal());
		
		if (empresaTO.getFormaPagos() != null) {
			Collection<FormaPago> formaPagos = new LinkedList<FormaPago>();
			
			for (FormaPagoTO formaPagoTO : empresaTO.getFormaPagos()) {
				FormaPago formaPago = new FormaPago();
				
				formaPago.setId(formaPagoTO.getId());
				
				formaPagos.add(formaPago);
			}
			
			empresa.setFormaPagos(formaPagos);
		}
		
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