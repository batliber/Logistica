package uy.com.amensg.logistica.web.dwr;

import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.web.entities.MenuTO;

public class MenuDWR {
	
	public static MenuTO transform(Menu menu) {
		MenuTO result = new MenuTO();
		
		result.setOrden(menu.getOrden());
		result.setPadre(menu.getPadre());
		result.setTitulo(menu.getTitulo());
		result.setUrl(menu.getUrl());
		
		result.setFcre(menu.getFcre());
		result.setFact(menu.getFact());
		result.setId(menu.getId());
		result.setTerm(menu.getTerm());
		result.setUact(menu.getUact());
		result.setUcre(menu.getUcre());
		
		return result;
	}
}