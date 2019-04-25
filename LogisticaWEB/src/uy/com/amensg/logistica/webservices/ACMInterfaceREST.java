package uy.com.amensg.logistica.webservices;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import uy.com.amensg.logistica.bean.ACMInterfaceBean;
import uy.com.amensg.logistica.bean.IACMInterfaceBean;

@Path("/ACMInterfaceREST")
public class ACMInterfaceREST {

	@GET
	@Path("/getSiguienteMidSinProcesar/")
	@Produces("application/json")
	public String getSiguienteMidSinProcesar() {
		String result = "{";
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			String data = iACMInterfaceBean.getSiguienteMidSinProcesar();
			
			if (data != null) {
				result += 
					" \"data\": \"" + data + "\"";
			} else {
				result += 
					" \"data\": \"\"";
			}
			
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
	
	@GET
	@Path("/getSiguienteNumeroContratoSinProcesar/")
	@Produces("application/json")
	public String getSiguienteNumeroContratoSinProcesar() {
		String result = "{";
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			String data = iACMInterfaceBean.getSiguienteNumeroContratoSinProcesar();
			
			if (data != null) {
				result += 
					" \"data\": \"" + data + "\"";
			} else {
				result += 
					" \"data\": \"\"";
			}
			
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
	
	private IACMInterfaceBean lookupACMInterfaceBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IACMInterfaceBean) context.lookup(lookupName);
	}
}