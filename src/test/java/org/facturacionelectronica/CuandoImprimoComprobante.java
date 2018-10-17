package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GestorPdf;
import org.junit.Test;

public class CuandoImprimoComprobante {

	@Test
	public void entoncesDebeGenerarPdf() throws Exception {
		BasicConfigurator.configure();
		
		GestorPdf gestorPdf = new GestorPdf();

		gestorPdf.generarPDF("20381847927-01-FF40-46");
	
		assertTrue(false);
	}
}
