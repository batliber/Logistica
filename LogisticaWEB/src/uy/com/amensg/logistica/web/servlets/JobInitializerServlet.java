package uy.com.amensg.logistica.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import uy.com.amensg.logistica.scheduling.FinProcesosJob;
import uy.com.amensg.logistica.scheduling.ReprocesarPendientesRiesgoOnLineJob;
import uy.com.amensg.logistica.util.Configuration;

public class JobInitializerServlet extends HttpServlet {

	private static final long serialVersionUID = 7929443216577104180L;
	
	public void init() throws ServletException {
		initializeJobs();
		
		super.init();
	}

	protected void doGet(
		HttpServletRequest req, HttpServletResponse resp
	) throws ServletException, IOException {
		initializeJobs();
		
		super.doGet(req, resp);
	}
	
	private void initializeJobs() {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	
			// Fin procesos
			if (scheduler.getJobDetail(new JobKey("finProcesosJob", "procesos")) != null) {
				scheduler.deleteJob(new JobKey("finProcesosJob", "procesos"));
			}
			
			JobDetail jobDetail = JobBuilder.newJob(FinProcesosJob.class)
				.withIdentity("finProcesosJob", "procesos")
				.build();
	
			Long intervaloEjecucion = 
				Long.parseLong(
					Configuration.getInstance().getProperty("finProcesosJob.interavaloEjecucion")
				);
			
			Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("finProcesosTrigger", "procesos")
				.startNow()
				.withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMinutes(
							intervaloEjecucion.intValue()
						)
						.repeatForever()
				)
				.build();

			scheduler.scheduleJob(jobDetail, trigger);
			
			// Reprocesar pendientes riesgo online
			if (scheduler.getJobDetail(new JobKey("reprocesarPendientesRiesgoOnLineJob", "riesgoOnline")) != null) {
				scheduler.deleteJob(new JobKey("reprocesarPendientesRiesgoOnLineJob", "riesgoOnline"));
			}
			
			jobDetail = JobBuilder.newJob(ReprocesarPendientesRiesgoOnLineJob.class)
				.withIdentity("reprocesarPendientesRiesgoOnLineJob", "riesgoOnline")
				.build();
	
			intervaloEjecucion =
				Long.parseLong(
					Configuration.getInstance().getProperty(
						"reprocesarPendientesRiesgoOnLineJob.intervaloEjecucion"
					)
				);
			
			trigger = TriggerBuilder.newTrigger()
				.withIdentity("reprocesarPendientesRiesgoOnLineTrigger", "riesgoOnline")
				.startNow()
				.withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMinutes(intervaloEjecucion.intValue())
						.repeatForever()
				)
				.build();

			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}