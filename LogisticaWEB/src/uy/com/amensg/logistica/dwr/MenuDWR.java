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

import uy.com.amensg.logistica.bean.IMenuBean;
import uy.com.amensg.logistica.bean.MenuBean;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MenuTO;

@RemoteProxy
public class MenuDWR {

	private IMenuBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MenuBean.class.getSimpleName();
		String remoteInterfaceName = IMenuBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		Context context = new InitialContext();
		
		return (IMenuBean) context.lookup(lookupName);
	}
	
	public Collection<MenuTO> list() {
		Collection<MenuTO> result = new LinkedList<MenuTO>();
		
		try {
			IMenuBean iMenuBean = lookupBean();
			
			for (Menu menu : iMenuBean.list()) {
				result.add(transform(menu));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MenuTO getById(Long id) {
		MenuTO result = null;
		
		try {
			IMenuBean iMenuBean = lookupBean();
			
			result = transform(iMenuBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(MenuTO menuTO) {
		try {
			IMenuBean iMenuBean = lookupBean();
			
			iMenuBean.save(transform(menuTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(MenuTO menuTO) {
		try {
			IMenuBean iMenuBean = lookupBean();
			
			iMenuBean.remove(transform(menuTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(MenuTO menuTO) {
		try {
			IMenuBean iMenuBean = lookupBean();
			
			iMenuBean.update(transform(menuTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MenuTO transform(Menu menu) {
		MenuTO menuTO = new MenuTO();
		
		menuTO.setOrden(menu.getOrden());
		menuTO.setPadre(menu.getPadre());
		menuTO.setTitulo(menu.getTitulo());
		menuTO.setUrl(menu.getUrl());
		
		menuTO.setFact(menu.getFact());
		menuTO.setId(menu.getId());
		menuTO.setTerm(menu.getTerm());
		menuTO.setUact(menu.getUact());
		
		return menuTO;
	}

	public static Menu transform(MenuTO menuTO) {
		Menu result = new Menu();
		
		result.setOrden(menuTO.getOrden());
		result.setPadre(menuTO.getPadre());
		result.setTitulo(menuTO.getTitulo());
		result.setUrl(menuTO.getUrl());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(menuTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}