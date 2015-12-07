package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IUsuarioRolEmpresaBean;
import uy.com.amensg.logistica.bean.UsuarioRolEmpresaBean;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.entities.UsuarioTO;

@RemoteProxy
public class UsuarioRolEmpresaDWR {

	private IUsuarioRolEmpresaBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = UsuarioRolEmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioRolEmpresaBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IUsuarioRolEmpresaBean) context.lookup(lookupName);
	}
	
	public Collection<EmpresaTO> listEmpresasByContext() {
		Collection<EmpresaTO> result = new LinkedList<EmpresaTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				UsuarioTO usuario = new UsuarioDWR().getById(usuarioId);
				
				for (UsuarioRolEmpresaTO usuarioRolEmpresaTO : usuario.getUsuarioRolEmpresas()) {
					result.add(usuarioRolEmpresaTO.getEmpresa());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<UsuarioTO> listVendedoresByContext() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listVendedoresByUsuario(usuario)) {
					result.add(UsuarioDWR.transform(usuarioRolEmpresa.getUsuario()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<UsuarioTO> listBackofficesByContext() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listBackofficesByUsuario(usuario)) {
					result.add(UsuarioDWR.transform(usuarioRolEmpresa.getUsuario()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<UsuarioTO> listDistribuidoresByContext() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listDistribuidoresByUsuario(usuario)) {
					result.add(UsuarioDWR.transform(usuarioRolEmpresa.getUsuario()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<UsuarioTO> listActivadoresByContext() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listActivadoresByUsuario(usuario)) {
					result.add(UsuarioDWR.transform(usuarioRolEmpresa.getUsuario()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static UsuarioRolEmpresaTO transform(UsuarioRolEmpresa usuarioRolEmpresa) {
		UsuarioRolEmpresaTO usuarioRolEmpresaTO = new UsuarioRolEmpresaTO();
		
		usuarioRolEmpresaTO.setEmpresa(EmpresaDWR.transform(usuarioRolEmpresa.getEmpresa()));
		usuarioRolEmpresaTO.setRol(RolDWR.transform(usuarioRolEmpresa.getRol()));
		
		usuarioRolEmpresaTO.setFact(usuarioRolEmpresa.getFact());
		usuarioRolEmpresaTO.setId(usuarioRolEmpresa.getId());
		usuarioRolEmpresaTO.setTerm(usuarioRolEmpresa.getTerm());
		usuarioRolEmpresaTO.setUact(usuarioRolEmpresa.getUact());
		
		return usuarioRolEmpresaTO;
	}
	
	public static UsuarioRolEmpresa transform(UsuarioRolEmpresaTO usuarioRolEmpresaTO) {
		UsuarioRolEmpresa usuarioRolEmpresa = new UsuarioRolEmpresa();
		
		Empresa empresa = new Empresa();
		empresa.setId(usuarioRolEmpresaTO.getEmpresa().getId());
		
		usuarioRolEmpresa.setEmpresa(empresa);
		
		Rol rol = new Rol();
		rol.setId(usuarioRolEmpresaTO.getRol().getId());
		
		usuarioRolEmpresa.setRol(rol);
		
		if (usuarioRolEmpresaTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(usuarioRolEmpresaTO.getUsuario().getId());
			
			usuarioRolEmpresa.setUsuario(usuario);
		}
		
		usuarioRolEmpresa.setFact(usuarioRolEmpresaTO.getFact());
		usuarioRolEmpresa.setId(usuarioRolEmpresaTO.getId());
		usuarioRolEmpresa.setTerm(usuarioRolEmpresaTO.getTerm());
		usuarioRolEmpresa.setUact(usuarioRolEmpresaTO.getUact());
		
		return usuarioRolEmpresa;
	}
}