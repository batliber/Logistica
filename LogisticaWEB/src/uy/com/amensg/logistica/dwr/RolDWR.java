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

import uy.com.amensg.logistica.bean.IRolBean;
import uy.com.amensg.logistica.bean.RolBean;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MenuTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.RolTO;

@RemoteProxy
public class RolDWR {

	private IRolBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RolBean.class.getSimpleName();
		String remoteInterfaceName = IRolBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IRolBean) context.lookup(lookupName);
	}
	
	public Collection<RolTO> list() {
		Collection<RolTO> result = new LinkedList<RolTO>();
		
		try {
			IRolBean iRolBean = lookupBean();
			
			for (Rol rol : iRolBean.list()) {
				result.add(transform(rol, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRolBean iRolBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iRolBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object rol : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(RolDWR.transform((Rol) rol, false));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRolBean iRolBean = lookupBean();
				
				result = 
					iRolBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RolTO getById(Long id) {
		RolTO result = null;
		
		try {
			IRolBean iRolBean = lookupBean();
			
			result = transform(iRolBean.getById(id, true), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RolTO getByNombre(String nombre) {
		RolTO result = null;
		
		try {
			IRolBean iRolBean = lookupBean();
			
			Rol rol = iRolBean.getByNombre(nombre, true);
			if (rol != null) {
				result = transform(rol, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(RolTO rolTO) {
		try {
			IRolBean iRolBean = lookupBean();
			
			iRolBean.save(transform(rolTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(RolTO rolTO) {
		try {
			IRolBean iRolBean = lookupBean();
			
			Rol rol = new Rol();
			rol.setId(rolTO.getId());
			
			iRolBean.remove(transform(rolTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(RolTO rolTO) {
		try {
			IRolBean iRolBean = lookupBean();
			
			iRolBean.update(transform(rolTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
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
	
	public static Rol transform(RolTO rolTO) {
		Rol result = new Rol();
		
		result.setNombre(rolTO.getNombre());
		
		Set<Menu> menus = new HashSet<Menu>();
		for (MenuTO menuTO : rolTO.getMenus()) {
			Menu menu = new Menu();
			menu.setId(menuTO.getId());
			
			menus.add(menu);
		}
		result.setMenus(menus);
		
		Set<Rol> subordinados = new HashSet<Rol>();
		for (RolTO rolSubordinadoTO : rolTO.getSubordinados()) {
			Rol rol = new Rol();
			rol.setId(rolSubordinadoTO.getId());
			
			subordinados.add(rol);
		}
		result.setSubordinados(subordinados);
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(rolTO.getFcre());
		result.setFact(date);
		result.setId(rolTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(rolTO.getUcre());
		
		return result;
	}
}