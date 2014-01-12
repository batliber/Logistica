package uy.com.amensg.logistica.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import uy.com.amensg.logistica.scheduling.FinProcesosJob;
import uy.com.amensg.logistica.util.Configuration;

public class JobInitializerServlet extends HttpServlet {

	private static final long serialVersionUID = 7929443216577104180L;
	
	public void init() throws ServletException {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	
			if (scheduler.getJobDetail(new JobKey("finProcesosJob", "procesos")) != null) {
				scheduler.deleteJob(new JobKey("finProcesosJob", "procesos"));
			}
			
			JobDetail jobDetail = JobBuilder.newJob(FinProcesosJob.class)
				.withIdentity("finProcesosJob", "procesos")
				.build();
	
			Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("finProcesosTrigger", "procesos")
				.startNow()
				.withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMinutes(
							new Long(
								Configuration.getInstance().getProperty("finProcesosJob.interavaloEjecucion")
							).intValue()
						)
						.repeatForever()
				)
				.build();

			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.init();
	}
}