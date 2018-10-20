package org.facturacionelectronica.servicios;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Job;

public class AppEnviarSunatJob implements Job {

	private AppEnviarSunat appEnviarSunat = new AppEnviarSunat();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Ejecutando Segundo Job");
		try {
			appEnviarSunat.execAppEnviarSunat();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
