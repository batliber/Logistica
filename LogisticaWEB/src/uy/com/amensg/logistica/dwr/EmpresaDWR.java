package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;

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
				result.add(transform(empresa, false));
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
			
			result = transform(iEmpresaBean.getById(id, true), true);
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
	
	public Collection<UsuarioTO> listEmpresaUsuarioContratosById(Long id) {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			for (Usuario usuario : iEmpresaBean.listEmpresaUsuarioContratosById(id)) {
				result.add(UsuarioDWR.transform(usuario, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EmpresaTO add(EmpresaTO empresaTO) {
		EmpresaTO result = null;
		
		try {
			IEmpresaBean iEmpresaBean = lookupBean();
			
			result = transform(iEmpresaBean.save(transform(empresaTO)), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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
	
	public static EmpresaTO transform(Empresa empresa, boolean transformCollections) {
		EmpresaTO result = new EmpresaTO();
		
		result.setCodigoPromotor(empresa.getCodigoPromotor());
		result.setDireccion(empresa.getDireccion());
		result.setLogoURL(empresa.getLogoURL());
		result.setNombre(empresa.getNombre());
		result.setNombreContrato(empresa.getNombreContrato());
		result.setNombreSucursal(empresa.getNombreSucursal());
		
		if (transformCollections) {
			if (empresa.getFormaPagos() != null) {
				Collection<FormaPagoTO> formaPagos = new LinkedList<FormaPagoTO>();
				
				for (FormaPago formaPago : empresa.getFormaPagos()) {
					formaPagos.add(FormaPagoDWR.transform(formaPago));
				}
				
				result.setFormaPagos(formaPagos);
			}
			
			if (empresa.getEmpresaUsuarioContratos() != null) {
				Collection<UsuarioTO> empresaUsuarioContratos = new LinkedList<UsuarioTO>();
				
				for (Usuario usuario : empresa.getEmpresaUsuarioContratos()) {
					empresaUsuarioContratos.add(UsuarioDWR.transform(usuario, false));
				}
				
				result.setEmpresaUsuarioContratos(empresaUsuarioContratos);
			}
		}
		
		result.setFcre(empresa.getFcre());
		result.setFact(empresa.getFact());
		result.setId(empresa.getId());
		result.setTerm(empresa.getTerm());
		result.setUact(empresa.getUact());
		result.setUcre(empresa.getUcre());
		
		return result;
	}

	public static Empresa transform(EmpresaTO empresaTO) {
		Empresa result = new Empresa();
		
		result.setCodigoPromotor(empresaTO.getCodigoPromotor());
		result.setDireccion(empresaTO.getDireccion());
		result.setLogoURL(empresaTO.getLogoURL());
		result.setNombre(empresaTO.getNombre());
		result.setNombreContrato(empresaTO.getNombreContrato());
		result.setNombreSucursal(empresaTO.getNombreSucursal());
		
		if (empresaTO.getFormaPagos() != null) {
			Set<FormaPago> formaPagos = new HashSet<FormaPago>();
			
			for (FormaPagoTO formaPagoTO : empresaTO.getFormaPagos()) {
				FormaPago formaPago = new FormaPago();
				
				formaPago.setId(formaPagoTO.getId());
				
				formaPagos.add(formaPago);
			}
			
			result.setFormaPagos(formaPagos);
		}
		
		if (empresaTO.getEmpresaUsuarioContratos() != null) {
			Set<Usuario> empresaUsuarioContratos = new HashSet<Usuario>();
			
			for (UsuarioTO empresaUsuarioContratoTO : empresaTO.getEmpresaUsuarioContratos()) {
				Usuario usuario = new Usuario();
				
				usuario.setId(empresaUsuarioContratoTO.getId());
				
				empresaUsuarioContratos.add(usuario);
			}
			
			result.setEmpresaUsuarioContratos(empresaUsuarioContratos);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(empresaTO.getFcre());
		result.setFact(date);
		result.setId(empresaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(empresaTO.getUcre());
		
		return result;
	}
}