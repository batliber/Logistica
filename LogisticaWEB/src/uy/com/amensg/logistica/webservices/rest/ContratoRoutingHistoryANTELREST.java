package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.ContratoRoutingHistoryANTELBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryANTELBean;
import uy.com.amensg.logistica.entities.ContratoANTEL;
import uy.com.amensg.logistica.entities.ContratoRoutingHistoryANTEL;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/ContratoRoutingHistoryANTELREST")
public class ContratoRoutingHistoryANTELREST {

	@GET
	@Path("/listByContratoId/{contratoId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<ContratoRoutingHistoryANTEL> listByContratoId(
		@PathParam("contratoId") Long contratoId, @Context HttpServletRequest request
	) {
		Collection<ContratoRoutingHistoryANTEL> result = new LinkedList<ContratoRoutingHistoryANTEL>();

		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoRoutingHistoryANTELBean iContratoRoutingHistoryANTELBean = lookupBean();
			
				result = iContratoRoutingHistoryANTELBean.listByContratoId(contratoId);
				
				for (ContratoRoutingHistoryANTEL contratoRoutingHistoryANTEL : result) {
					contratoRoutingHistoryANTEL.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					contratoRoutingHistoryANTEL.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					
					if (contratoRoutingHistoryANTEL.getUsuario() != null) {
						contratoRoutingHistoryANTEL.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoRoutingHistoryANTEL.getRol() != null) {
						contratoRoutingHistoryANTEL.getRol().setSubordinados(new HashSet<Rol>());
						contratoRoutingHistoryANTEL.getRol().setMenus(new HashSet<Menu>());
					}
					
					if (contratoRoutingHistoryANTEL.getUsuarioAct() != null) {
						contratoRoutingHistoryANTEL.getUsuarioAct().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					ContratoANTEL contratoANTEL = contratoRoutingHistoryANTEL.getContrato();
					
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
	
	private IContratoRoutingHistoryANTELBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRoutingHistoryANTELBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryANTELBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IContratoRoutingHistoryANTELBean) context.lookup(lookupName);
	}
}