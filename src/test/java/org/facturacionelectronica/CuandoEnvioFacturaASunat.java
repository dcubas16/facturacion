package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GestorWebService;
import org.junit.Test;

public class CuandoEnvioFacturaASunat {

	@Test
	public void enviarFacturaSUNAT() {
		BasicConfigurator.configure();

		GestorWebService gestorWebService = new GestorWebService();
		boolean respuesta = gestorWebService.enviarFacturaSunat("FF40-46",
				"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\",
				"20381847927-01-FF40-46.zip", "20381847927-01-FF40-46.xml", "20381847927MODDATOS", "MODDATOS");

		assertTrue(respuesta);

	}
}
