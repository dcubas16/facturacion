package org.facturacionelectronica.servicios;

import org.apache.log4j.BasicConfigurator;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobEnvio {
	public static void main(String[] args) {
		try {
			
			BasicConfigurator.configure();
			
			JobDetail job2 = JobBuilder.newJob(AppEnviarSunatJob.class).withIdentity("appEnviarSunatJob", "group2").build();
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "group2")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * ? * *")).build();
			Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
			scheduler2.start(); 
			scheduler2.scheduleJob(job2, trigger2); 
						
			}
		catch(Exception e){ 
			e.printStackTrace();
		}
	}
}
