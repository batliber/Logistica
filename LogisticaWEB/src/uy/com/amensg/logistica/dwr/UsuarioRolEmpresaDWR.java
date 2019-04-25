package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioRolEmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioRolEmpresaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
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
	
	public Collection<UsuarioTO> listDistribuidoresByContextMinimal() {
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
				
				for (UsuarioTO usuarioTO : toOrder) {
					UsuarioTO usuarioTOMinimal = new UsuarioTO();
					usuarioTOMinimal.setId(usuarioTO.getId());
					usuarioTOMinimal.setNombre(usuarioTO.getNombre());
					
					result.add(usuarioTOMinimal);
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
	
	public Collection<UsuarioTO> listDistribuidoresChipsByContext() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				Map<Long, UsuarioTO> usuarios = new HashMap<Long, UsuarioTO>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : 
					iUsuarioRolEmpresaBean.listDistribuidoresChipsByUsuario(usuario)
				) {
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
		UsuarioRolEmpresaTO result = new UsuarioRolEmpresaTO();
		
		result.setEmpresa(EmpresaDWR.transform(usuarioRolEmpresa.getEmpresa(), false));
		result.setRol(RolDWR.transform(usuarioRolEmpresa.getRol(), true));
		
		result.setFcre(usuarioRolEmpresa.getFcre());
		result.setFact(usuarioRolEmpresa.getFact());
		result.setId(usuarioRolEmpresa.getId());
		result.setTerm(usuarioRolEmpresa.getTerm());
		result.setUact(usuarioRolEmpresa.getUact());
		result.setUcre(usuarioRolEmpresa.getUcre());
		
		return result;
	}
	
	public static UsuarioRolEmpresa transform(UsuarioRolEmpresaTO usuarioRolEmpresaTO) {
		UsuarioRolEmpresa result = new UsuarioRolEmpresa();
		
		Empresa empresa = new Empresa();
		empresa.setId(usuarioRolEmpresaTO.getEmpresa().getId());
		
		result.setEmpresa(empresa);
		
		Rol rol = new Rol();
		rol.setId(usuarioRolEmpresaTO.getRol().getId());
		
		result.setRol(rol);
		
		if (usuarioRolEmpresaTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(usuarioRolEmpresaTO.getUsuario().getId());
			
			result.setUsuario(usuario);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(usuarioRolEmpresaTO.getFact());
		result.setFact(date);
		result.setId(usuarioRolEmpresaTO.getId());
		result.setTerm(usuarioRolEmpresaTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(usuarioRolEmpresaTO.getUcre());
		
		return result;
	}
}

class ComparatorUsuarioTO implements Comparator<UsuarioTO> {
	
	public ComparatorUsuarioTO() {
		
	}
	
	public int compare(UsuarioTO o1, UsuarioTO o2) {
		return o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase());
	}	
}