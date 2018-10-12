package org.facturacionelectronica.servicios;

import java.io.File;
import java.math.BigInteger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;

import org.facturacionelectronica.util.Constantes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;

public class GestorWebService {

	public boolean enviarFacturaSunat(String idDocumento, String rutaComplta, String archivoZip, String archivoXml,
			String usuario, String contrasenia) {

		String responseDescription = "";
		String responseCode = "";
		String ticket = "";

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(rutaComplta + archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			BindingProvider bindingProvider = (BindingProvider) billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, contrasenia);

			byte[] respuesta = billService.sendBill(archivoZip, dataHandler, "");

			Compresor.crearArchivoZip(Constantes.rutaCompleta + Constantes.rutaRespuesta, respuesta);

			// Document doc =
			// GestorArchivosXML.obtenerArchivoXML("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\respuesta\\R-20553510661-01-FF40-46.xml");

			Document doc = GestorArchivosXML.obtenerArchivoXML(Constantes.rutaCompleta + Constantes.rutaRespuesta
					+ Constantes.siglaRespuesta + Constantes.separadorNombreArchivo + archivoXml);

			NodeList nList = doc.getElementsByTagName("cac:DocumentResponse");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;

			responseDescription = eElement.getElementsByTagName("cac:Response").item(0).getTextContent();
			responseCode = eElement.getElementsByTagName("cbc:ResponseCode").item(0).getTextContent();
			ticket = doc.getDocumentElement().getElementsByTagName("cbc:ID").item(0).getTextContent();

			System.out.println(responseDescription + "--" + responseCode + "--" + ticket);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

		}

		return false;
	}

	public String obtenerNombreArchivoFactura(BigInteger numDoc, String tipoDocComprobante, String serie,
			String correlativo) {

		String nombreArchivo = numDoc + Constantes.separadorNombreArchivo + tipoDocComprobante
				+ Constantes.separadorNombreArchivo + serie + Constantes.separadorNombreArchivo
				+ Constantes.separadorNombreArchivo + correlativo;

		return nombreArchivo;

	}

}