package uy.com.amensg.logistica.web.servlets;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.web.scheduling.FinProcesosJob;
import uy.com.amensg.logistica.web.scheduling.ReprocesarPendientesRiesgoOnLineJob;

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
						
			JobDetail jobDetail = newJob(FinProcesosJob.class)
				.withIdentity("finProcesosJob", "procesos")
				.build();
			
			Long intervaloEjecucion = 
				Long.parseLong(
					Configuration.getInstance().getProperty("finProcesosJob.interavaloEjecucion")
				);
			
			Trigger trigger = newTrigger()
				.withIdentity("finProcesosTrigger", "procesos")
				.startNow()
				.withSchedule(
					simpleSchedule()
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
			
			jobDetail = newJob(ReprocesarPendientesRiesgoOnLineJob.class)
				.withIdentity("reprocesarPendientesRiesgoOnLineJob", "riesgoOnline")
				.build();
	
			intervaloEjecucion =
				Long.parseLong(
					Configuration.getInstance().getProperty(
						"reprocesarPendientesRiesgoOnLineJob.intervaloEjecucion"
					)
				);
			
			trigger = newTrigger()
				.withIdentity("reprocesarPendientesRiesgoOnLineTrigger", "riesgoOnline")
				.startNow()
				.withSchedule(
					simpleSchedule()
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