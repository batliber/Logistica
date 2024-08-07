package uy.com.amensg.logistica.web.listeners;

import java.util.GregorianCalendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import uy.com.amensg.logistica.bean.ISeguridadBean;
import uy.com.amensg.logistica.bean.SeguridadBean;

public class SessionExpirationListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		try {
			HttpSession session = arg0.getSession();
			
			long now = GregorianCalendar.getInstance().getTimeInMillis();
			if ((now - session.getLastAccessedTime()) >= ((long) session.getMaxInactiveInterval() * 1000)) {
				Long usuarioId = (Long) session.getAttribute("sesion");
				
				if (usuarioId != null) {
					ISeguridadBean iSeguridadBean = lookupBean();
					
					iSeguridadBean.sessionTimeout(usuarioId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ISeguridadBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SeguridadBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ISeguridadBean) context.lookup(lookupName);
	}
}