package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.servicios.Compresor;
import org.facturacionelectronica.servicios.GestorFirma;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.junit.Test;

public class CuandoFirmoXml {
	
	String nombreArchivo = "20381847927-01-0002-52";

	@Test
	public void entoncesDebeFirmarXml() throws Throwable {
		BasicConfigurator.configure();

		GestorFirma gestorFirma = new GestorFirma();
		
		InputStream inputStream = new FileInputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);
		
		Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream, new FacturaDao(FacturaMock.obtenerFactura().getCabeceraFactura()));
		
	    FileOutputStream fout = new FileOutputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);

        ByteArrayOutputStream outDocument = (ByteArrayOutputStream)xmlFirmado.get("signatureFile");
        String digestValue = (String)xmlFirmado.get("digestValue");
	     
	    outDocument.writeTo(fout);
	    fout.close();


		assertTrue(false);

	}
	
	
//	@Test
//	public void entoncesDebeComprimirXml() throws Throwable {
//		BasicConfigurator.configure();
//
//		Compresor.comprimirArchivo(
//				ParametrosGLobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionZip,
//				ParametrosGLobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml,
//				nombreArchivo + Constantes.extensionXml);
//
//
//		assertTrue(false);
//
//	}
}
