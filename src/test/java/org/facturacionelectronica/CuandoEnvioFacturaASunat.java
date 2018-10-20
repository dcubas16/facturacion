package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GestorWebService;
import org.facturacionelectronica.util.Constantes;
import org.junit.Test;

public class CuandoEnvioFacturaASunat {

	@Test
	public void enviarFacturaSUNAT() {
		BasicConfigurator.configure();
//		D:\Suit_Fael\Solicitud\20381847927-01-F001-4355.zip

		GestorWebService gestorWebService = new GestorWebService();
		 boolean respuesta = gestorWebService.enviarFacturaSunat("F001-4355",
		 "D:\\Suit_Fael\\Solicitud\\",
		 "20381847927-01-F001-4355.zip", "20381847927-01-F001-4355.xml",
		 "20381847927MODDATOS", "MODDATOS");
		//

//		boolean respuesta = gestorWebService.enviarFacturaSunat("FF14-32",
//				Constantes.rutaCompleta + Constantes.rutaSolicitud, "20381847927-01-0002-53.zip",
//				"20381847927-01-0002-53.xml", "20381847927MODDATOS", "MODDATOS");

		assertTrue(respuesta);

	}
}
