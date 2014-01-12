package uy.com.amensg.logistica.scheduling;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import uy.com.amensg.logistica.bean.ACMInterfaceProcesoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceProcesoBean;

public class FinProcesosJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			IACMInterfaceProcesoBean iACMInterfaceProcesoBean = lookupBean();
			
			iACMInterfaceProcesoBean.finalizarProcesos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceProcesoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ACMInterfaceProcesoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceProcesoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceProcesoBean) context.lookup(lookupName);
	}
}