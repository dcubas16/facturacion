package org.facturacionelectronica.servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.helger.commons.state.ESuccess;
import com.helger.ublpe.UBLPEWriter;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class GestorArchivosXML {

	public static Document obtenerArchivoXML(String rutaArchivo) {

		Document doc = null;
		
		try {
			File fXmlFile = new File(rutaArchivo);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

		}

		return doc;
	}
	
	public static ESuccess imprimirFacturaArchivo(InvoiceType invoiceType, String ruta, String formato) {

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta), formato));
		} catch (IOException ex) {
			// Report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				/* ignore */}
		}

		final ESuccess eSuccess = UBLPEWriter.invoice().write(invoiceType, new File(ruta));

		return eSuccess;
	}
}
