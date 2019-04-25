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
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.MD5Utils;

@RemoteProxy
public class UsuarioDWR {

	private IUsuarioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
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
				
				Usuario usuarioLogged = iUsuarioBean.getById(usuarioId, true);
				
				Collection<Long> adminsEmpresas = new LinkedList<Long>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioLogged.getUsuarioRolEmpresas()) {
					if (usuarioRolEmpresa.getRol().getId().equals(new Long(Configuration.getInstance().getProperty("rol.Administrador")))) {
						adminsEmpresas.add(usuarioRolEmpresa.getEmpresa().getId());
					}
				}
				
				for (Usuario usuario : iUsuarioBean.list()) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
						if (adminsEmpresas.contains(usuarioRolEmpresa.getEmpresa().getId())) {
							result.add(transform(usuario, false));
							break;
						}
					}
				}
				
				if (result.size() == 0) {
					result.add(transform(usuarioLogged, false));
				}
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
			
			IUsuarioBean iUsuarioBean = lookupBean();
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataConsulta metadataConsulta = 
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					);
				
				Collection<Long> empresaIds = new LinkedList<Long>();
				for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
					if (metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
						metadataConsulta.getMetadataCondiciones().remove(metadataCondicion);
						
						for (Object valor : metadataCondicion.getValores()) {
							empresaIds.add((Long)valor);
						}
					}
				}
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iUsuarioBean.list(
						metadataConsulta,
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
					Usuario usuario = (Usuario) object;
					
					for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
						if (empresaIds.contains(usuarioRolEmpresa.getEmpresa().getId())) {
							registrosMuestra.add(transform((Usuario) object, true));
							break;
						}
					}
				}
				
				result.setRegistrosMuestra(registrosMuestra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listVigentesContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			IUsuarioBean iUsuarioBean = lookupBean();
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataConsulta metadataConsulta = MetadataConsultaDWR.transform(
					metadataConsultaTO
				);
				
				// Vigentes = fechaBaja IS NULL
				MetadataCondicion metadataCondicionVigente = new MetadataCondicion();
				metadataCondicionVigente.setCampo("fechaBaja");
				metadataCondicionVigente.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NULL);
				metadataCondicionVigente.setValores(new LinkedList<String>());
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicionVigente);
				
				// Si hay alguna condición por el campo empresa, se quita de los filtros y se procesa
				// a partir de la lista de usuarios que retorna la consulta. 
				Long empresaId = null;
				for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
					if (metadataCondicion.getCampo().equals("empresa.id")) {
						metadataConsulta.getMetadataCondiciones().remove(metadataCondicion);
						
						empresaId = new Long((String) metadataCondicion.getValores().iterator().next());
					}
				}
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iUsuarioBean.list(
						metadataConsulta,
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
					Usuario usuario = (Usuario) object;
					
					if (empresaId != null) {
						for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
							if (empresaId.equals(usuarioRolEmpresa.getEmpresa().getId())) {
								registrosMuestra.add(transform((Usuario) object, true));
								
								break;
							}
						}
					} else {
						registrosMuestra.add(transform((Usuario) object, true));
					}
				}
				
				result.setRegistrosMuestra(registrosMuestra);
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
			
			IUsuarioBean iUsuarioBean = lookupBean();
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				result = 
					iUsuarioBean.count(
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
	
	public Long countVigentesContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			IUsuarioBean iUsuarioBean = lookupBean();
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataConsulta metadataConsulta = MetadataConsultaDWR.transform(
					metadataConsultaTO
				);
				
				MetadataCondicion metadataCondicionVigente = new MetadataCondicion();
				metadataCondicionVigente.setCampo("fechaBaja");
				metadataCondicionVigente.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NULL);
				metadataCondicionVigente.setValores(new LinkedList<String>());
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicionVigente);
				
				for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
					if (metadataCondicion.getCampo().equals("empresa.id")) {
						metadataConsulta.getMetadataCondiciones().remove(metadataCondicion);
					}
				}
				
				result = 
					iUsuarioBean.count(
						metadataConsulta,
						usuarioId
					);
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
			
			result = transform(iUsuarioBean.getById(id, true), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UsuarioTO getByLogin(String login) {
		UsuarioTO result = null;
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			Usuario usuario = iUsuarioBean.getByLogin(login, true);
			if (usuario != null) {
				result = transform(usuario, true);
			}
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
			} else if (!nueva.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$")) {
				result = "La nueva contraseña debe ser de 8 o más caracteres y debe contener mayúsculas, minúsculas y números.";
			} else if (nueva.equals(actual)) {
				result = "La nueva contraseña coincide con la actual";
			} else {
				HttpSession httpSession = WebContextFactory.get().getSession(false);
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Usuario usuario = iUsuarioBean.getById(usuarioId, true);
				
				if (usuario.getContrasena().equals(MD5Utils.stringToMD5(actual))) {
					usuario.setContrasena(MD5Utils.stringToMD5(nueva));
					
					usuario.setCambioContrasenaProximoLogin(false);
					
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

	public static Usuario transform(UsuarioTO usuarioTO) {
		Usuario result = new Usuario();
		
		result.setBloqueado(usuarioTO.getBloqueado());
		result.setCambioContrasenaProximoLogin(usuarioTO.getCambioContrasenaProximoLogin());
		
		if (usuarioTO.getContrasena() != null) {
			result.setContrasena(MD5Utils.stringToMD5(usuarioTO.getContrasena()));
		}
		
		result.setDocumento(usuarioTO.getDocumento());
		result.setIntentosFallidosLogin(usuarioTO.getIntentosFallidosLogin());
		result.setLogin(usuarioTO.getLogin());
		result.setNombre(usuarioTO.getNombre());
		
		if (usuarioTO.getUsuarioRolEmpresas() != null) {
			Collection<UsuarioRolEmpresa> usuarioRolEmpresas = new LinkedList<UsuarioRolEmpresa>();
			for (UsuarioRolEmpresaTO usuarioRolEmpresaTO : usuarioTO.getUsuarioRolEmpresas()) {
				usuarioRolEmpresas.add(UsuarioRolEmpresaDWR.transform(usuarioRolEmpresaTO));
			}
			result.setUsuarioRolEmpresas(usuarioRolEmpresas);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(usuarioTO.getFcre());
		result.setFact(date);
		result.setId(usuarioTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(usuarioTO.getUcre());
		
		return result;
	}
}