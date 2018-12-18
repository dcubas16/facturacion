package org.facturacionelectronica.servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.facturacionelectronica.entidades.RespuestaCdr;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.helger.commons.state.ESuccess;
import com.helger.ublpe.UBLPEWriter;
//import com.helger.ubl21.UBL21Writer;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;

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
	
	public static ESuccess imprimirFacturaArchivo21(oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType invoiceType, String ruta, String formato) {

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

		final ESuccess eSuccess = com.helger.ubl21.UBL21Writer.invoice().write(invoiceType, new File(ruta));

		return eSuccess;
	}
	
	public static ESuccess imprimirComunicacionBaja(VoidedDocumentsType voidedDocumentsType, String ruta, String formato) {

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

		final ESuccess eSuccess = UBLPEWriter.voidedDocuments().write(voidedDocumentsType, new File(ruta));

		return eSuccess;
	}
	
	
	public static RespuestaCdr obtenerRespuestaCdr(String archivoXml) {
		
		RespuestaCdr respuestaCdr = new RespuestaCdr();
		Document doc = GestorArchivosXML.obtenerArchivoXML(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaRespuesta
				+ Constantes.siglaRespuesta + Constantes.separadorNombreArchivo + archivoXml);

		NodeList nList = doc.getElementsByTagName("cac:DocumentResponse");
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		
		respuestaCdr.setDescripcion(eElement.getElementsByTagName("cac:Response").item(0).getTextContent());
		respuestaCdr.setCodigo(eElement.getElementsByTagName("cbc:ResponseCode").item(0).getTextContent());
		respuestaCdr.setTicket(doc.getDocumentElement().getElementsByTagName("cbc:ID").item(0).getTextContent());
		
		System.out.println(respuestaCdr.getTicket()+ "--" + respuestaCdr.getCodigo() + "--" + respuestaCdr.getDescripcion());
		
		return respuestaCdr;
	}
}
