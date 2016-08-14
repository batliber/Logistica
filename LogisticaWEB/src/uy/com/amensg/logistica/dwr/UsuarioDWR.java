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

import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MenuTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.MD5Utils;

@RemoteProxy
public class UsuarioDWR {

	private IUsuarioBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}
	
	public Collection<UsuarioTO> list() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuarioLogged = iUsuarioBean.getById(usuarioId);
				
				Collection<Long> adminsEmpresas = new LinkedList<Long>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioLogged.getUsuarioRolEmpresas()) {
					if (usuarioRolEmpresa.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Administrador")))) {
						adminsEmpresas.add(usuarioRolEmpresa.getEmpresa().getId());
					}
				}
				
				for (Usuario usuario : iUsuarioBean.list()) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
						if (adminsEmpresas.contains(usuarioRolEmpresa.getEmpresa().getId())) {
							result.add(transform(usuario, true));
							break;
						}
					}
				}
				
				if (result.size() == 0) {
					result.add(transform(usuarioLogged, true));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UsuarioTO getById(Long id) {
		UsuarioTO result = null;
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			result = transform(iUsuarioBean.getById(id), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UsuarioTO getByLogin(String login) {
		UsuarioTO result = null;
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			result = transform(iUsuarioBean.getByLogin(login), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(UsuarioTO usuarioTO) {
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			iUsuarioBean.save(transform(usuarioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(UsuarioTO usuarioTO) {
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			iUsuarioBean.remove(transform(usuarioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String cambiarContrasena(String actual, String nueva, String confirma) {
		String result = null;
		
		try {
			if (!nueva.equals(confirma)) {
				result = "Las contraseñas no coinciden";
			} else {
				HttpSession httpSession = WebContextFactory.get().getSession(false);
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Usuario usuario = iUsuarioBean.getById(usuarioId);
				
				if (usuario.getContrasena().equals(MD5Utils.stringToMD5(actual))) {
					usuario.setContrasena(MD5Utils.stringToMD5(nueva));
					
					usuario.setFact(GregorianCalendar.getInstance().getTime());
					usuario.setTerm(new Long(1));
					usuario.setUact(usuarioId);
					
					iUsuarioBean.update(usuario);
					
					result = "Operación exitosa.";
				} else {
					result = "Contraseña incorrecta";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			result = "Error en la operación.";
		}
		
		return result;
	}
	
	public void update(UsuarioTO usuarioTO) {
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			iUsuarioBean.update(transform(usuarioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static UsuarioTO transform(Usuario usuario, boolean transformCollections) {
		UsuarioTO usuarioTO = new UsuarioTO();
		
		usuarioTO.setContrasena(usuario.getContrasena());
		usuarioTO.setDocumento(usuario.getDocumento());
		usuarioTO.setLogin(usuario.getLogin());
		usuarioTO.setNombre(usuario.getNombre());

		if (transformCollections) {
			Map<Long, MenuTO> menus = new HashMap<Long, MenuTO>();
			Collection<UsuarioRolEmpresaTO> usuarioRolEmpresas = new LinkedList<UsuarioRolEmpresaTO>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				usuarioRolEmpresas.add(UsuarioRolEmpresaDWR.transform(usuarioRolEmpresa));
				
				for (Menu menu : usuarioRolEmpresa.getRol().getMenus()) {
					if (!menus.containsKey(menu.getId())) {
						menus.put(menu.getId(), MenuDWR.transform(menu));
					}
				}
			}
			usuarioTO.setUsuarioRolEmpresas(usuarioRolEmpresas);
		
			List<MenuTO> menusToOrder = new LinkedList<MenuTO>();
			menusToOrder.addAll(menus.values());
			
			Collections.sort(menusToOrder, new Comparator<MenuTO>() {
				public int compare(MenuTO arg0, MenuTO arg1) {
					return arg0.getOrden().compareTo(arg1.getOrden());
				}
			});
			
			usuarioTO.setMenus(menusToOrder);
		}
		
		usuarioTO.setFact(usuario.getFact());
		usuarioTO.setId(usuario.getId());
		usuarioTO.setTerm(usuario.getTerm());
		usuarioTO.setUact(usuario.getUact());
		
		return usuarioTO;
	}

	public static Usuario transform(UsuarioTO usuarioTO) {
		Usuario usuario = new Usuario();
		
		if (usuarioTO.getContrasena() != null) {
			usuario.setContrasena(MD5Utils.stringToMD5(usuarioTO.getContrasena()));
		}
		
		usuario.setDocumento(usuarioTO.getDocumento());
		usuario.setLogin(usuarioTO.getLogin());
		usuario.setNombre(usuarioTO.getNombre());
		
		if (usuarioTO.getUsuarioRolEmpresas() != null) {
			Collection<UsuarioRolEmpresa> usuarioRolEmpresas = new LinkedList<UsuarioRolEmpresa>();
			for (UsuarioRolEmpresaTO usuarioRolEmpresaTO : usuarioTO.getUsuarioRolEmpresas()) {
				usuarioRolEmpresas.add(UsuarioRolEmpresaDWR.transform(usuarioRolEmpresaTO));
			}
			usuario.setUsuarioRolEmpresas(usuarioRolEmpresas);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		usuario.setFact(date);
		usuario.setId(usuarioTO.getId());
		usuario.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		usuario.setUact(usuarioId);
		
		return usuario;
	}
}