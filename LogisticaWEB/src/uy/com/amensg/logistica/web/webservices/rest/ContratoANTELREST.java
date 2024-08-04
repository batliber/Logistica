package uy.com.amensg.logistica.webservices.rest;

import java.util.HashSet;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.ContratoANTELBean;
import uy.com.amensg.logistica.bean.IContratoANTELBean;
import uy.com.amensg.logistica.entities.ContratoANTEL;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/ContratoANTELREST")
public class ContratoANTELREST {

	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoANTELBean iContratoANTELBean = lookupBean();
				
				result = iContratoANTELBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					ContratoANTEL contratoANTEL = (ContratoANTEL) object;
					
					contratoANTEL.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					contratoANTEL.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					
					if (contratoANTEL.getActivador() != null) {
						contratoANTEL.getActivador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoANTEL.getBackoffice() != null) {
						contratoANTEL.getBackoffice().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoANTEL.getCoordinador() != null) {
						contratoANTEL.getCoordinador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoANTEL.getDistribuidor() != null) {
						contratoANTEL.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
						
					if (contratoANTEL.getVendedor() != null) {
						contratoANTEL.getVendedor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoANTEL.getUsuario() != null) {
						contratoANTEL.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoANTEL.getRol() != null) {
						contratoANTEL.getRol().setSubordinados(new HashSet<Rol>());
						contratoANTEL.getRol().setMenus(new HashSet<Menu>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoANTELBean iContratoANTELBean = lookupBean();
				
				result = iContratoANTELBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoANTELBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoANTELBean.class.getSimpleName();
		String remoteInterfaceName = IContratoANTELBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IContratoANTELBean) context.lookup(lookupName);
	}
}