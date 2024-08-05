package uy.com.amensg.logistica.web.scheduling;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import uy.com.amensg.logistica.bean.ACMInterfaceProcesoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceProcesoBean;

public class FinProcesosJob implements Job {

	public FinProcesosJob() {
		
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			IACMInterfaceProcesoBean iACMInterfaceProcesoBean = lookupBean();
			
			iACMInterfaceProcesoBean.finalizarProcesos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceProcesoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceProcesoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceProcesoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceProcesoBean) context.lookup(lookupName);
	}
}