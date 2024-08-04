package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MenuTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.entities.UsuarioTO;

public class UsuarioDWR {

	private IUsuarioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}
	
	public UsuarioTO getById(Long id) {
		UsuarioTO result = null;
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			result = transform(iUsuarioBean.getById(id, true), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static UsuarioTO transform(Usuario usuario, boolean transformCollections) {
		UsuarioTO result = new UsuarioTO();
		
		result.setBloqueado(usuario.getBloqueado());
		result.setCambioContrasenaProximoLogin(usuario.getCambioContrasenaProximoLogin());
		result.setContrasena(usuario.getContrasena());
		result.setDocumento(usuario.getDocumento());
		result.setIntentosFallidosLogin(usuario.getIntentosFallidosLogin());
		result.setLogin(usuario.getLogin());
		result.setNombre(usuario.getNombre());

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
				
				usuarioRolEmpresa.getRol().setMenus(new HashSet<Menu>());
			}
			result.setUsuarioRolEmpresas(usuarioRolEmpresas);
		
			List<MenuTO> menusToOrder = new LinkedList<MenuTO>();
			menusToOrder.addAll(menus.values());
			
			Collections.sort(menusToOrder, new Comparator<MenuTO>() {
				public int compare(MenuTO arg0, MenuTO arg1) {
					return arg0.getOrden().compareTo(arg1.getOrden());
				}
			});
			
			result.setMenus(menusToOrder);
		}
		
		result.setFcre(usuario.getFcre());
		result.setFact(usuario.getFact());
		result.setId(usuario.getId());
		result.setTerm(usuario.getTerm());
		result.setUact(usuario.getUact());
		result.setUcre(usuario.getUcre());
		
		return result;
	}
}