package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MenuTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.RolTO;

public class RolDWR {
		
	public static RolTO transform(Rol rol, boolean transformCollections) {
		RolTO result = new RolTO();
		
		result.setNombre(rol.getNombre());
		
		if (transformCollections) {
			Collection<MenuTO> menus = new LinkedList<MenuTO>();
			if (rol.getMenus() != null) {
				for (Menu menu : rol.getMenus()) {
					menus.add(MenuDWR.transform(menu));
				}
			}
			result.setMenus(menus);
		
			Collection<RolTO> subordinados = new LinkedList<RolTO>();
			if (rol.getSubordinados() != null) {
				for (Rol subordinado : rol.getSubordinados()) {
					subordinados.add(transform(subordinado, false));
				}
			}
			result.setSubordinados(subordinados);
		}
		
		result.setFcre(rol.getFcre());
		result.setFact(rol.getFact());
		result.setId(rol.getId());
		result.setTerm(rol.getTerm());
		result.setUact(rol.getUact());
		result.setUcre(rol.getUcre());
		
		return result;
	}
}