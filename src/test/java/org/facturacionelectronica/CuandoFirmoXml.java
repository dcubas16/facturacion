package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GestorFirma;
import org.facturacionelectronica.util.Constantes;
import org.junit.Test;

public class CuandoFirmoXml {

	@Test
	public void entoncesDebeFirmarXml() throws Throwable {
		BasicConfigurator.configure();

		GestorFirma gestorFirma = new GestorFirma();
		
		InputStream inputStream = new FileInputStream(Constantes.rutaCompleta + Constantes.rutaSolicitud + "20100454523-01-F001-4355" + Constantes.extensionXml);
		
		Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream);
		
//		FileInputStream inDocument = new FileInputStream(rutaNombreEntrada);
	    FileOutputStream fout = new FileOutputStream(Constantes.rutaCompleta + Constantes.rutaSolicitud + "FIR-20100454523-01-F001-4355" + Constantes.extensionXml);

//	    Map<String,Object> firma = this.firmarDocumento(inDocument);
        ByteArrayOutputStream outDocument = (ByteArrayOutputStream)xmlFirmado.get("signatureFile");
        String digestValue = (String)xmlFirmado.get("digestValue");
	     
	    outDocument.writeTo(fout);
	    fout.close();

//		firmarDocumento(inputStream);
//		gestorFirma.generateXMLDigitalSignature(
//				"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\solicitud\\20100454523-01-F001-4355.xml",
//				"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\solicitud\\firma\\20100454523-01-F001-4355.xml",
//				"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\repositorio_cert\\certpri.key",
//				"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\repositorio_cert\\certpub.cer");

		assertTrue(false);

	}
}
