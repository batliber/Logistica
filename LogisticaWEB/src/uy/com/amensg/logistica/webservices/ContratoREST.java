package uy.com.amensg.logistica.webservices;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.ContratoRoutingHistoryBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;

@Path("/TramiteREST")
public class ContratoREST {

	@GET
	@Path("/listHistoricoByNumeroTramite/{numeroTramite}")
	@Produces("application/json")
	public String listHistoricoByNumeroTramite(@PathParam("numeroTramite") Long numeroTramite) {
		String result = "{";
		
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupContratoRoutingHistoryBean();
			
			Collection<ContratoRoutingHistory> historico = iContratoRoutingHistoryBean.listByNumeroTramite(numeroTramite);
			result += " \"data\": [";
			boolean first = true;
			for (ContratoRoutingHistory contratoRoutingHistory : historico) {
				if (!first) {
					result += ", ";
				} else {
					first = false;
				}
				
				result += 
					"{"
						+ " \"estado\": \"" + (contratoRoutingHistory.getEstado() != null ? contratoRoutingHistory.getEstado().getNombre() : "") + "\","
						+ " \"rol\": \"" + (contratoRoutingHistory.getRol() != null ? contratoRoutingHistory.getRol().getNombre() : "") + "\","
						+ " \"fecha\": \"" + contratoRoutingHistory.getFecha() + "\""
					+ " }";
			}
			result += "] }";
		} catch (Exception e) {
			e.printStackTrace();
			
			result =
				"{"
					+ " \"error\": \"No se puede completar la operación.\""
				+ " }";
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNumeroTramite/{numeroTramite}")
	@Produces("application/json")
	public String getByNumeroTramite(@PathParam("numeroTramite") Long numeroTramite) {
		String result = "{";
		
		try {
			IContratoBean iContratoBean = lookupContratoBean();
			
			Contrato contrato = iContratoBean.getByNumeroTramite(numeroTramite, false);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			result += 
				" \"numeroTramite\": \"" + contrato.getNumeroTramite() + "\","
				+ " \"direccionEntregaCalle\": \"" + (contrato.getDireccionEntregaCalle() != null ? contrato.getDireccionEntregaCalle() : "") + "\","
				+ " \"direccionEntregaNumero\": \"" + (contrato.getDireccionEntregaNumero() != null ? contrato.getDireccionEntregaNumero() : "") + "\","
				+ " \"direccionEntregaDepartamento\": \"" + (contrato.getDireccionEntregaDepartamento() != null ? contrato.getDireccionEntregaDepartamento().getNombre() : "") + "\","
				+ " \"direccionEntregaLocalidad\": \"" + (contrato.getDireccionEntregaLocalidad() != null ? contrato.getDireccionEntregaLocalidad() : "") + "\","
				+ " \"estado\": \"" + (contrato.getEstado() != null ? contrato.getEstado().getNombre() : "") + "\","
				+ " \"fechaEntrega\": \"" + (contrato.getFechaEntrega() != null ? format.format(contrato.getFechaEntrega()) : "") + "\""
				;
			
			result += "}";
		} catch (Exception e) {
			e.printStackTrace();
			
			result =
				"{"
					+ " \"error\": \"No se puede completar la operación.\""
				+ " }";
		}
		
		return result;
	}
	
	private IContratoBean lookupContratoBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IContratoBean) context.lookup(lookupName);
	}
	
	private IContratoRoutingHistoryBean lookupContratoRoutingHistoryBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRoutingHistoryBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IContratoRoutingHistoryBean) context.lookup(lookupName);
	}
}