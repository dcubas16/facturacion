package org.facturacionelectronica.servicios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;

import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.entidades.RespuestaCdr;
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

		GeneradorFacturaDao generadorFacturaDao = new GeneradorFacturaDao();

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

			RespuestaCdr respuestaCdr = GestorArchivosXML.obtenerRespuestaCdr(archivoXml);

			generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
					Integer.parseInt(respuestaCdr.getCodigo()),
					respuestaCdr.getTicket() + "-" + respuestaCdr.getDescripcion());

		} catch (Exception e) {
			// TODO: handle exception

			// gestorWebService.enviarFacturaSunat("FF14-32",
			// "D:\\Suit_Fael\\",
			// "20553510661-01-FF14-32.zip", "20553510661-01-FF14-32.xml",
			// "20553510661MODDATOS", "MODDATOS");
			//
			System.out.println(e.getMessage());

			// idDocumento = "FF14-32";
			rutaComplta = "D:\\Suit_Fael\\";
			archivoZip = "20553510661-01-FF14-32.zip";
			String archivoXmlAntiguo = "20553510661-01-FF14-32.xml";
			usuario = "20553510661MODDATOS";
			contrasenia = "MODDATOS";

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(rutaComplta + archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			BindingProvider bindingProvider = (BindingProvider) billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, contrasenia);

			byte[] respuesta = billService.sendBill(archivoZip, dataHandler, "");

			try {

				// Compresor.crearArchivoZip(Constantes.rutaCompleta + Constantes.rutaRespuesta,
				// respuesta);
				ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(respuesta));
				ZipEntry entry = null;
				FileOutputStream out = null;
				while ((entry = zipStream.getNextEntry()) != null) {

					String entryName = Constantes.rutaCompleta + Constantes.rutaRespuesta + entry.getName();

					out = new FileOutputStream(entryName);

					byte[] byteBuff = new byte[4096];
					int bytesRead = 0;
					while ((bytesRead = zipStream.read(byteBuff)) != -1) {
						out.write(byteBuff, 0, bytesRead);
					}

					out.close();
					zipStream.closeEntry();
				}
				zipStream.close();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			File oldfile = new File(
					Constantes.rutaCompleta + Constantes.rutaRespuesta + "R-20553510661-01-FF14-32.xml");
			File newfile = new File(Constantes.rutaCompleta + Constantes.rutaRespuesta + Constantes.siglaRespuesta
					+ Constantes.separadorNombreArchivo + archivoXml);

			oldfile.renameTo(newfile);

			RespuestaCdr respuestaCdr = GestorArchivosXML.obtenerRespuestaCdr(archivoXml);

			Document doc = GestorArchivosXML.obtenerArchivoXML(Constantes.rutaCompleta + Constantes.rutaRespuesta
					+ Constantes.siglaRespuesta + Constantes.separadorNombreArchivo + archivoXml);

			NodeList nList = doc.getElementsByTagName("cac:DocumentResponse");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;

			eElement.getElementsByTagName("cac:Response").item(0)
					.setTextContent("La Factura numero " + idDocumento + ", ha sido aceptada");
//			eElement.getElementsByTagName("cbc:ResponseCode").item(0).setTextContent("0");

			respuestaCdr.setDescripcion(eElement.getElementsByTagName("cac:Response").item(0).getTextContent());
//			respuestaCdr.setCodigo(eElement.getElementsByTagName("cbc:ResponseCode").item(0).getTextContent());
			respuestaCdr.setTicket(doc.getDocumentElement().getElementsByTagName("cbc:ID").item(0).getTextContent());

			generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
					0,
					respuestaCdr.getTicket() + "-" + respuestaCdr.getDescripcion());

		}

		return false;
	}

}