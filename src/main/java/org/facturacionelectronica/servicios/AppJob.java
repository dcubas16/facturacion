package org.facturacionelectronica.servicios;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Job;

public class AppJob implements Job {

	private App app = new App();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("Ejecutando Primer Job");
		try {
			app.execApp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
