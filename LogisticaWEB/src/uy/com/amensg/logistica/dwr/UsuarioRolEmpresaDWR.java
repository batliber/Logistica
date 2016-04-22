package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
				
				Map<Long, EmpresaTO> empresas = new HashMap<Long, EmpresaTO>();
				for (UsuarioRolEmpresaTO usuarioRolEmpresaTO : usuario.getUsuarioRolEmpresas()) {
					empresas.put(
						usuarioRolEmpresaTO.getEmpresa().getId(), 
						usuarioRolEmpresaTO.getEmpresa()
					);
				}
				
				List<EmpresaTO> toOrder = new LinkedList<EmpresaTO>();
				toOrder.addAll(empresas.values());
				
				Collections.sort(toOrder, new Comparator<EmpresaTO>() {
					public int compare(EmpresaTO o1, EmpresaTO o2) {
						return o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase());
					}					
				});
				
				result.addAll(toOrder);
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
				
				Map<Long, UsuarioTO> usuarios = new HashMap<Long, UsuarioTO>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listVendedoresByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							UsuarioDWR.transform(usuarioRolEmpresa.getUsuario(), false)
						);
					}
				}
				
				List<UsuarioTO> toOrder = new LinkedList<UsuarioTO>();
				toOrder.addAll(usuarios.values());
				
				Collections.sort(toOrder, new ComparatorUsuarioTO());
				
				result.addAll(toOrder);
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
				
				Map<Long, UsuarioTO> usuarios = new HashMap<Long, UsuarioTO>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listBackofficesByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							UsuarioDWR.transform(usuarioRolEmpresa.getUsuario(), false)
						);
					}
				}
				
				List<UsuarioTO> toOrder = new LinkedList<UsuarioTO>();
				toOrder.addAll(usuarios.values());
				
				Collections.sort(toOrder, new ComparatorUsuarioTO());
				
				result.addAll(toOrder);
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
				
				Map<Long, UsuarioTO> usuarios = new HashMap<Long, UsuarioTO>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listDistribuidoresByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							UsuarioDWR.transform(usuarioRolEmpresa.getUsuario(), false)
						);
					}
				}
				
				List<UsuarioTO> toOrder = new LinkedList<UsuarioTO>();
				toOrder.addAll(usuarios.values());
				
				Collections.sort(toOrder, new ComparatorUsuarioTO());
				
				result.addAll(toOrder);
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
				
				Map<Long, UsuarioTO> usuarios = new HashMap<Long, UsuarioTO>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : iUsuarioRolEmpresaBean.listActivadoresByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							UsuarioDWR.transform(usuarioRolEmpresa.getUsuario(), false)
						);
					}
				}
				
				List<UsuarioTO> toOrder = new LinkedList<UsuarioTO>();
				toOrder.addAll(usuarios.values());
				
				Collections.sort(toOrder, new ComparatorUsuarioTO());
				
				result.addAll(toOrder);
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

class ComparatorUsuarioTO implements Comparator<UsuarioTO> {
	
	public ComparatorUsuarioTO() {
		
	}
	
	public int compare(UsuarioTO o1, UsuarioTO o2) {
		return o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase());
	}	
}