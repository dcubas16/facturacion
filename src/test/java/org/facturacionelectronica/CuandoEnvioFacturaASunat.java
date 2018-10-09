package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GestorWebService;
import org.junit.Test;


public class CuandoEnvioFacturaASunat {

	@Test
	public void enviarFacturaSUNAT()  {
		BasicConfigurator.configure();

		GestorWebService gestorWebService = new GestorWebService();
		boolean respuesta = gestorWebService.enviarFacturaSunat();
		
		assertTrue(respuesta);

	}
}
