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

import uy.com.amensg.logistica.bean.ContratoRoutingHistoryBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/ContratoRoutingHistoryREST")
public class ContratoRoutingHistoryREST {

	@GET
	@Path("/listByContratoId/{contratoId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<ContratoRoutingHistory> listByContratoId(
		@PathParam("contratoId") Long contratoId, @Context HttpServletRequest request
	) {
		Collection<ContratoRoutingHistory> result = new LinkedList<ContratoRoutingHistory>();

		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
				result = iContratoRoutingHistoryBean.listByContratoId(contratoId);
				
				for (ContratoRoutingHistory contratoRoutingHistory : result) {
					contratoRoutingHistory.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					contratoRoutingHistory.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					
					if (contratoRoutingHistory.getUsuario() != null) {
						contratoRoutingHistory.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contratoRoutingHistory.getRol() != null) {
						contratoRoutingHistory.getRol().setSubordinados(new HashSet<Rol>());
						contratoRoutingHistory.getRol().setMenus(new HashSet<Menu>());
					}
					
					if (contratoRoutingHistory.getUsuarioAct() != null) {
						contratoRoutingHistory.getUsuarioAct().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					Contrato contrato = contratoRoutingHistory.getContrato();
					
					contrato.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					contrato.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					
					contrato.setArchivosAdjuntos(new HashSet<ContratoArchivoAdjunto>());
					
					if (contrato.getActivador() != null) {
						contrato.getActivador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getBackoffice() != null) {
						contrato.getBackoffice().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getCoordinador() != null) {
						contrato.getCoordinador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getDistribuidor() != null) {
						contrato.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
						
					if (contrato.getVendedor() != null) {
						contrato.getVendedor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getUsuario() != null) {
						contrato.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getRol() != null) {
						contrato.getRol().setSubordinados(new HashSet<Rol>());
						contrato.getRol().setMenus(new HashSet<Menu>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoRoutingHistoryBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRoutingHistoryBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IContratoRoutingHistoryBean) context.lookup(lookupName);
	}
}