package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import uy.com.amensg.logistica.bean.IUsuarioRolEmpresaBean;
import uy.com.amensg.logistica.bean.UsuarioRolEmpresaBean;
import uy.com.amensg.logistica.dwr.UsuarioDWR;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.FormaPagoTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.entities.UsuarioTO;

@Path("/UsuarioRolEmpresaREST")
public class UsuarioRolEmpresaREST {

	@GET
	@Path("/listEmpresasByContext")
	@Produces("application/json")
	public Collection<EmpresaTO> listEmpresasByContext(@Context HttpServletRequest request) {
		Collection<EmpresaTO> result = new LinkedList<EmpresaTO>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				UsuarioTO usuario = new UsuarioDWR().getById(usuarioId);
				
				Map<Long, EmpresaTO> empresas = new HashMap<Long, EmpresaTO>();
				for (UsuarioRolEmpresaTO usuarioRolEmpresaTO : usuario.getUsuarioRolEmpresas()) {
					usuarioRolEmpresaTO.getEmpresa().setFormaPagos(new LinkedList<FormaPagoTO>());
					usuarioRolEmpresaTO.getEmpresa().setEmpresaUsuarioContratos(new HashSet<UsuarioTO>());
					
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
	
	@GET
	@Path("/listVendedoresByContextMinimal")
	@Produces("application/json")
	public Collection<Usuario> listVendedoresByContextMinimal(@Context HttpServletRequest request) {
		List<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				Map<Long, Usuario> usuarios = new HashMap<Long, Usuario>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : 
					iUsuarioRolEmpresaBean.listVendedoresByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarioRolEmpresa.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							usuarioRolEmpresa.getUsuario()
						);
					}
				}
				
				result.addAll(usuarios.values());
				
				Collections.sort(result, new ComparatorUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listBackofficesByContextMinimal")
	@Produces("application/json")
	public Collection<Usuario> listBackofficesByContextMinimal(@Context HttpServletRequest request) {
		List<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				Map<Long, Usuario> usuarios = new HashMap<Long, Usuario>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : 
					iUsuarioRolEmpresaBean.listBackofficesByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarioRolEmpresa.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							usuarioRolEmpresa.getUsuario()
						);
					}
				}
				
				result.addAll(usuarios.values());
				
				Collections.sort(result, new ComparatorUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listDistribuidoresByContextMinimal")
	@Produces("application/json")
	public Collection<Usuario> listDistribuidoresByContextMinimal(@Context HttpServletRequest request) {
		List<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				Map<Long, Usuario> usuarios = new HashMap<Long, Usuario>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : 
					iUsuarioRolEmpresaBean.listDistribuidoresByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarioRolEmpresa.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							usuarioRolEmpresa.getUsuario()
						);
					}
				}
				
				result.addAll(usuarios.values());
				
				Collections.sort(result, new ComparatorUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listActivadoresByContextMinimal")
	@Produces("application/json")
	public Collection<Usuario> listActivadoresByContextMinimal(@Context HttpServletRequest request) {
		List<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				Map<Long, Usuario> usuarios = new HashMap<Long, Usuario>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : 
					iUsuarioRolEmpresaBean.listActivadoresByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarioRolEmpresa.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							usuarioRolEmpresa.getUsuario()
						);
					}
				}
				
				result.addAll(usuarios.values());
				
				Collections.sort(result, new ComparatorUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listDistribuidoresChipsByContextMinimal")
	@Produces("application/json")
	public Collection<Usuario> listDistribuidoresChipsByContextMinimal(@Context HttpServletRequest request) {
		List<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IUsuarioRolEmpresaBean iUsuarioRolEmpresaBean = lookupBean();
				
				Map<Long, Usuario> usuarios = new HashMap<Long, Usuario>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : 
					iUsuarioRolEmpresaBean.listDistribuidoresChipsByUsuario(usuario)) {
					if (!usuarios.containsKey(usuarioRolEmpresa.getUsuario().getId())) {
						usuarioRolEmpresa.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
						usuarios.put(
							usuarioRolEmpresa.getUsuario().getId(), 
							usuarioRolEmpresa.getUsuario()
						);
					}
				}
				
				result.addAll(usuarios.values());
				
				Collections.sort(result, new ComparatorUsuario());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IUsuarioRolEmpresaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioRolEmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioRolEmpresaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IUsuarioRolEmpresaBean) context.lookup(lookupName);
	}
}

class ComparatorUsuario implements Comparator<Usuario> {
	
	public ComparatorUsuario() {
		
	}
	
	public int compare(Usuario o1, Usuario o2) {
		return o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase());
	}	
}