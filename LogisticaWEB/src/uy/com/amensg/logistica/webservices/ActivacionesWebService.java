package uy.com.amensg.logistica.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ActivacionBean;
import uy.com.amensg.logistica.bean.ActivacionConexionBean;
import uy.com.amensg.logistica.bean.IActivacionBean;
import uy.com.amensg.logistica.bean.IActivacionConexionBean;
import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionConexion;

@WebService
public class ActivacionesWebService {

	@WebMethod
	public String getSiguienteMidParaActivar() {
		String result = "";
		
		try {
			IActivacionBean iActivacionBean = lookupActivacionBean();
			IActivacionConexionBean iActivacionConexionBean = lookupActivacionConexionBean();
			
			Activacion activacion = iActivacionBean.getSiguienteMidParaActivar();
			ActivacionConexion activacionConexion = 
				iActivacionConexionBean.getRandomByEmpresaId(activacion.getEmpresa().getId());
			
			if (activacion != null) {
				result = 
					activacion.getMid() + " " 
					+ activacion.getChip() + " "
					+ activacion.getTipoActivacion().getId() + " "
					+ (activacionConexion != null ? activacionConexion.getLogin() : "") + " " 
					+ (activacionConexion != null ? activacionConexion.getPassword() : "") + " "
					+ (activacionConexion != null ? activacionConexion.getAgente() : "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@WebMethod
	public void actualizarDatosActivacion(String mid, String chip, String estadoActivacionId) {
		try {
			IActivacionBean iActivacionBean = lookupActivacionBean();
			
			iActivacionBean.actualizarDatosActivacion(Long.parseLong(mid), chip, Long.parseLong(estadoActivacionId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IActivacionBean lookupActivacionBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IActivacionBean) context.lookup(lookupName);
	}
	
	private IActivacionConexionBean lookupActivacionConexionBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionConexionBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionConexionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IActivacionConexionBean) context.lookup(lookupName);
	}
}