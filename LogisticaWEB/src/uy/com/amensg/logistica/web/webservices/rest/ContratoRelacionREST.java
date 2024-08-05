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
import uy.com.amensg.logistica.bean.ContratoRelacionBean;
import uy.com.amensg.logistica.bean.IContratoRelacionBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRelacion;

@Path("/ContratoRelacionREST")
public class ContratoRelacionREST {

	@GET
	@Path("/listByContratoId/{id}")
	@Produces("application/json")
	public Collection<ContratoRelacion> listByContratoId(@PathParam("id") Long id) {
		Collection<ContratoRelacion> result = new LinkedList<ContratoRelacion>();
		
		try {
			IContratoRelacionBean iContratoRelacionBean = lookupBean();
			
			Collection<ContratoRelacion> contratoRelaciones = iContratoRelacionBean.listByContratoId(id);
			
			for (ContratoRelacion contratoRelacion : contratoRelaciones) {
				ContratoRelacion contratoRelacionMinimal = new ContratoRelacion();
				
				Contrato contrato = new Contrato();
				contrato.setId(contratoRelacion.getContrato().getId());
				
				contratoRelacionMinimal.setContrato(contrato);
				
				Contrato contratoRelacionado = new Contrato();
				contratoRelacionado.setId(contratoRelacion.getContratoRelacionado().getId());
				contratoRelacionado.setNumeroTramite(contratoRelacion.getContratoRelacionado().getNumeroTramite());
				contratoRelacionado.setEstado(contratoRelacion.getContratoRelacionado().getEstado());
				
				contratoRelacionMinimal.setContratoRelacionado(contratoRelacionado);
				
				contratoRelacionMinimal.setId(contratoRelacion.getId());
				contratoRelacionMinimal.setFact(contratoRelacion.getFact());
				contratoRelacionMinimal.setFcre(contratoRelacion.getFcre());
				contratoRelacionMinimal.setTerm(contratoRelacion.getTerm());
				contratoRelacionMinimal.setUact(contratoRelacion.getUact());
				contratoRelacionMinimal.setUcre(contratoRelacion.getUcre());
				
				result.add(contratoRelacionMinimal);
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
	public ContratoRelacion add(ContratoRelacion contratoRelacion, @Context HttpServletRequest request) {
		ContratoRelacion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoRelacionBean iContratoRelacionBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contratoRelacion.setFact(hoy);
				contratoRelacion.setFcre(hoy);
				contratoRelacion.setTerm(Long.valueOf(1));
				contratoRelacion.setUact(loggedUsuarioId);
				contratoRelacion.setUcre(loggedUsuarioId);
				
				ContratoRelacion contratoRelacionSaved = iContratoRelacionBean.save(contratoRelacion);
				
				result = new ContratoRelacion();
				
				Contrato contrato = new Contrato();
				contrato.setId(contratoRelacionSaved.getContrato().getId());
				
				result.setContrato(contrato);
				
				Contrato contratoRelacionado = new Contrato();
				contratoRelacionado.setId(contratoRelacionSaved.getContratoRelacionado().getId());
				
				result.setContratoRelacionado(contratoRelacionado);
				
				result.setId(contratoRelacionSaved.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoRelacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRelacionBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRelacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IContratoRelacionBean) context.lookup(lookupName);
	}
}