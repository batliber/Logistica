package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import uy.com.amensg.logistica.bean.DisponibilidadEntregaEmpresaZonaTurnoBean;
import uy.com.amensg.logistica.bean.IDisponibilidadEntregaEmpresaZonaTurnoBean;
import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.Zona;

@Path("/DisponibilidadEntregaEmpresaZonaTurnoREST")
public class DisponibilidadEntregaEmpresaZonaTurnoREST {

	@GET
	@Path("/listByEmpresaZona")
	@Produces("application/json")
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZona(
		@Context UriInfo uriInfo, @Context HttpServletRequest request
	) {
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = 
			new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
			
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = 
					lookupBean();
			
				MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
				
				if (queryParams.size() >= 2) {
					Empresa empresa = new Empresa();
					empresa.setId(Long.parseLong(queryParams.get("eid").get(0)));
					
					Zona zona = new Zona();
					zona.setId(Long.parseLong(queryParams.get("zid").get(0)));
					
					result = 
						iDisponibilidadEntregaEmpresaZonaTurnoBean.listByEmpresaZona(
							empresa, 
							zona
						);
					
					for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : result) {
						disponibilidadEntregaEmpresaZonaTurno.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
						disponibilidadEntregaEmpresaZonaTurno.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listByEmpresaZonaTurno")
	@Produces("application/json")
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZonaTurno(
		@Context UriInfo uriInfo, @Context HttpServletRequest request
	) {
		Collection<DisponibilidadEntregaEmpresaZonaTurno> result = 
			new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
			
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = 
					lookupBean();
			
				MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
				
				if (queryParams.size() >= 3) {
					Empresa empresa = new Empresa();
					empresa.setId(Long.parseLong(queryParams.get("eid").get(0)));
					
					Zona zona = new Zona();
					zona.setId(Long.parseLong(queryParams.get("zid").get(0)));
					
					Turno turno = new Turno();
					turno.setId(Long.parseLong(queryParams.get("tid").get(0)));
					
					result = 
						iDisponibilidadEntregaEmpresaZonaTurnoBean.listByEmpresaZonaTurno(
							empresa, 
							zona,
							turno
						);
					
					for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : result) {
						disponibilidadEntregaEmpresaZonaTurno.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
						disponibilidadEntregaEmpresaZonaTurno.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/updateDisponibilidadByZona/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void updateDisponibilidadByZona(
		@PathParam("id") Long id,
		Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos,
		@Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = 
					lookupBean();
				
				Zona zona = new Zona();
				zona.setId(id);
				
				iDisponibilidadEntregaEmpresaZonaTurnoBean.updateDisponibilidadByZona(
					zona,
					disponibilidadEntregaEmpresaZonaTurnos
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IDisponibilidadEntregaEmpresaZonaTurnoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = DisponibilidadEntregaEmpresaZonaTurnoBean.class.getSimpleName();
		String remoteInterfaceName = IDisponibilidadEntregaEmpresaZonaTurnoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IDisponibilidadEntregaEmpresaZonaTurnoBean) context.lookup(lookupName);
	}
}