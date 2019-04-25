package uy.com.amensg.logistica.dwr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IImportacionArchivoBean;
import uy.com.amensg.logistica.bean.ImportacionArchivoBean;

@RemoteProxy
public class ImportacionArchivoDWR {

	private IImportacionArchivoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ImportacionArchivoBean.class.getSimpleName();
		String remoteInterfaceName = IImportacionArchivoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IImportacionArchivoBean) context.lookup(lookupName);
	}
	
	public Long procesarArchivo(String fileName, Long formatoImportacionArchivoId) {
		Long result = null;
		
		try {
			IImportacionArchivoBean iImportacionArchivoBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = 
				iImportacionArchivoBean.procesarArchivo(
					fileName, formatoImportacionArchivoId, usuarioId
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String consultarProcesamientoArchivo(Long procesoImportacionArchivoId) {
		String result = null;
		
		try {
			IImportacionArchivoBean iImportacionArchivoBean = lookupBean();
			
//			HttpSession httpSession = WebContextFactory.get().getSession(false);
//			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iImportacionArchivoBean.consultarEstadoImportacionArchivo(
				procesoImportacionArchivoId
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}