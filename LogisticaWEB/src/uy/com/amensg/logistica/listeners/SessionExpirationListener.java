package uy.com.amensg.logistica.listeners;

import java.util.GregorianCalendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
				
				ISeguridadBean iSeguridadBean = lookupBean();
				
				iSeguridadBean.sessionTimeout(usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ISeguridadBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = SeguridadBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ISeguridadBean) context.lookup(lookupName);
	}
}