package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.exceptions.LogisticaException;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.MD5Utils;
import uy.com.amensg.logistica.web.dwr.UsuarioDWR;
import uy.com.amensg.logistica.web.entities.CambioContrasenaUsuarioTO;
import uy.com.amensg.logistica.web.entities.UsuarioTO;

@Path("/UsuarioREST")
public class UsuarioREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<UsuarioTO> list(@Context HttpServletRequest request) {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Usuario usuarioLogged = iUsuarioBean.getById(usuarioId, true);
				
				Collection<Long> adminsEmpresas = new LinkedList<Long>();
				for (UsuarioRolEmpresa usuarioRolEmpresa : usuarioLogged.getUsuarioRolEmpresas()) {
					if (usuarioRolEmpresa.getRol().getId().equals(Long.parseLong(Configuration.getInstance().getProperty("rol.Administrador")))) {
						adminsEmpresas.add(usuarioRolEmpresa.getEmpresa().getId());
					}
				}
				
				for (Usuario usuario : iUsuarioBean.list()) {
					for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
						if (adminsEmpresas.contains(usuarioRolEmpresa.getEmpresa().getId())) {
							result.add(UsuarioDWR.transform(usuario, false));
							break;
						}
					}
				}
				
				if (result.size() == 0) {
					result.add(UsuarioDWR.transform(usuarioLogged, false));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listMinimal")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<Usuario> listMinimal(@Context HttpServletRequest request) {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUsuarioBean iUsuarioBean = lookupBean();
				
				result = iUsuarioBean.listMinimal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listVigentesContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listVigentesContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
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
						
						empresaId = Long.parseLong((String) metadataCondicion.getValores().iterator().next());
					}
				}
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iUsuarioBean.listSubordinados(
						metadataConsulta,
						usuarioId
					);
				
//				MetadataConsultaResultado metadataConsultaResultado = 
//					iUsuarioBean.list(
//						metadataConsulta,
//						usuarioId
//					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
					Usuario usuario = (Usuario) object;
					
					if (empresaId != null) {
						for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
							if (empresaId.equals(usuarioRolEmpresa.getEmpresa().getId())) {
								registrosMuestra.add(UsuarioDWR.transform(usuario, true));
								
								break;
							}
						}
					} else {
						registrosMuestra.add(UsuarioDWR.transform(usuario, true));
					}
				}
				
				result.setRegistrosMuestra(registrosMuestra);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countVigentesContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countVigentesContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				// Vigentes = fechaBaja IS NULL
				MetadataCondicion metadataCondicionVigente = new MetadataCondicion();
				metadataCondicionVigente.setCampo("fechaBaja");
				metadataCondicionVigente.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NULL);
				metadataCondicionVigente.setValores(new LinkedList<String>());
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicionVigente);
				
				// Si hay alguna condición por el campo empresa, se quita de los filtros y se procesa
				// a partir de la lista de usuarios que retorna la consulta. 
				for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
					if (metadataCondicion.getCampo().equals("empresa.id")) {
						metadataConsulta.getMetadataCondiciones().remove(metadataCondicion);
					}
				}
				
//				result = 
//					iUsuarioBean.count(
//						metadataConsulta,
//						usuarioId
//					);
				
				result = 
					iUsuarioBean.countSubordinados(
						metadataConsulta,
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UsuarioTO getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		UsuarioTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUsuarioBean iUsuarioBean = lookupBean();
			
				result = UsuarioDWR.transform(iUsuarioBean.getById(id, true), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByIdMinimal/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Usuario getByIdMinimal(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Usuario result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUsuarioBean iUsuarioBean = lookupBean();
			
				result = iUsuarioBean.getByIdMinimal(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByLogin/{login}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UsuarioTO getByLogin(@PathParam("login") String login, @Context HttpServletRequest request) {
		UsuarioTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Usuario usuario = iUsuarioBean.getByLogin(login, false);
				
				if (usuario != null) {
					result = UsuarioDWR.transform(usuario, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public UsuarioTO add(Usuario usuario, @Context HttpServletRequest request) {
		UsuarioTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				usuario.setFact(hoy);
				usuario.setFcre(hoy);
				usuario.setTerm(Long.valueOf(1));
				usuario.setUact(loggedUsuarioId);
				usuario.setUcre(loggedUsuarioId);
				
				if (usuario.getContrasena() != null) {
					usuario.setContrasena(MD5Utils.stringToMD5(usuario.getContrasena()));
				}
				
				result = UsuarioDWR.transform(iUsuarioBean.save(usuario), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public UsuarioTO update(Usuario usuario, @Context HttpServletRequest request) {
		UsuarioTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				usuario.setFact(hoy);
				usuario.setTerm(Long.valueOf(1));
				usuario.setUact(loggedUsuarioId);
				
				if (usuario.getContrasena() != null) {
					usuario.setContrasena(MD5Utils.stringToMD5(usuario.getContrasena()));
				}
				
				result = UsuarioDWR.transform(iUsuarioBean.update(usuario), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Usuario usuario, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				usuario.setFact(hoy);
				usuario.setTerm(Long.valueOf(1));
				usuario.setUact(loggedUsuarioId);
			
				iUsuarioBean.remove(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/cambiarContrasena")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void cambiarContrasena(
		CambioContrasenaUsuarioTO cambioContrasenaUsuarioTO, @Context HttpServletRequest request
	) throws LogisticaException {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				String nueva = cambioContrasenaUsuarioTO.getContrasenaNueva();
				String confirma = cambioContrasenaUsuarioTO.getContrasenaConfirma();
				String actual = cambioContrasenaUsuarioTO.getContrasenaActual();
				
				if (!nueva.equals(confirma)) {
					throw new LogisticaException("Las contraseñas no coinciden");
				} else if (!nueva.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$")) {
					throw new LogisticaException(
						"La nueva contraseña debe ser de 8 o más caracteres y debe contener mayúsculas, minúsculas y números."
					);
				} else if (nueva.equals(actual)) {
					throw new LogisticaException(
						"La nueva contraseña coincide con la actual"
					);
				} else {
					IUsuarioBean iUsuarioBean = lookupBean();
					
					Usuario usuario = iUsuarioBean.getById(usuarioId, true);
					
					if (usuario.getContrasena().equals(MD5Utils.stringToMD5(actual))) {
						usuario.setContrasena(MD5Utils.stringToMD5(nueva));
						
						usuario.setCambioContrasenaProximoLogin(false);
						
						usuario.setFact(GregorianCalendar.getInstance().getTime());
						usuario.setTerm(Long.valueOf(1));
						usuario.setUact(usuarioId);
						
						iUsuarioBean.update(usuario);
					} else {
						throw new LogisticaException(
							"Contraseña incorrecta"
						);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new LogisticaException(e.getMessage());
		}
	}
	
	private IUsuarioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}
}