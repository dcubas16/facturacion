package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.servicios.GestorPdf;
import org.junit.Test;

public class CuandoImprimoComprobante {

	@Test
	public void entoncesDebeGenerarPdf() throws Exception {
		BasicConfigurator.configure();
		
		GestorPdf gestorPdf = new GestorPdf();
		
		FacturaDao facturaDao = new FacturaDao(FacturaMock.obtenerFactura().getCabeceraFactura());

		gestorPdf.generarPDF("20381847927-01-0002-52", facturaDao);
	
		assertTrue(false);
	}
}
