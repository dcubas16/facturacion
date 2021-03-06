package org.facturacionelectronica.servicios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.dao.GeneradorNotaCreditoDao;
import org.facturacionelectronica.entidades.RespuestaCdr;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pe.gob.sunat.service.StatusResponse;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;

public class GestorWebService {

	public GestorWebService() {
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
	}

	public boolean enviarFacturaSunat(String idDocumento, String rutaComplta, String archivoZip, String archivoXml,
			String usuario, String contrasenia) {

		GeneradorFacturaDao generadorFacturaDao = new GeneradorFacturaDao();

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(rutaComplta + archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			System.out.println("------------------------------->>>USUARIO :" + usuario + " -- PASWORD :" + contrasenia);
			BindingProvider bindingProvider = (BindingProvider) billService;
			// bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
			// usuario);
			// bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
			// contrasenia);

			List<Handler> handlerList = bindingProvider.getBinding().getHandlerChain();
			if (handlerList == null)
				handlerList = new ArrayList<Handler>();

			handlerList.add(new SecurityHandler(usuario, contrasenia));

			byte[] respuesta = billService.sendBill(archivoZip, dataHandler, "");

			Compresor.crearArchivoZip(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaRespuesta,
					respuesta);

			// RespuestaCdr respuestaCdr =
			// GestorArchivosXML.obtenerRespuestaCdr(archivoXml);
			RespuestaCdr respuestaCdr = obtenerRespuestaCdr(archivoXml);

			generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
					Integer.parseInt(respuestaCdr.getCodigo()),
					respuestaCdr.getTicket() + "-" + respuestaCdr.getDescripcion());

			return true;

		} catch (Exception e) {

			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			String mensaje = "";

			if (e.getMessage().length() > 998)
				mensaje = e.getMessage().substring(0, 998);
			else
				mensaje = e.getMessage();

			generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento), -1, mensaje);

			return false;

		}

	}
	
	
	public boolean enviarNotaCreditoSunat(String idDocumento, String rutaComplta, String archivoZip, String archivoXml,
			String usuario, String contrasenia) {

		GeneradorNotaCreditoDao generadorNotaCreditoDao = new GeneradorNotaCreditoDao();

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(rutaComplta + archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			System.out.println("------------------------------->>>USUARIO :" + usuario + " -- PASWORD :" + contrasenia);
			BindingProvider bindingProvider = (BindingProvider) billService;
			// bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
			// usuario);
			// bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
			// contrasenia);

			List<Handler> handlerList = bindingProvider.getBinding().getHandlerChain();
			if (handlerList == null)
				handlerList = new ArrayList<Handler>();

			handlerList.add(new SecurityHandler(usuario, contrasenia));

			byte[] respuesta = billService.sendBill(archivoZip, dataHandler, "");

			Compresor.crearArchivoZip(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaRespuesta,
					respuesta);

			// RespuestaCdr respuestaCdr =
			// GestorArchivosXML.obtenerRespuestaCdr(archivoXml);
			RespuestaCdr respuestaCdr = obtenerRespuestaCdr(archivoXml);

			generadorNotaCreditoDao.actualizarEstadoAceptado(generadorNotaCreditoDao.obtenerNotaCredito(idDocumento),
					Integer.parseInt(respuestaCdr.getCodigo()),
					respuestaCdr.getTicket() + "-" + respuestaCdr.getDescripcion());

			return true;

		} catch (Exception e) {

			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			String mensaje = "";

			if (e.getMessage().length() > 998)
				mensaje = e.getMessage().substring(0, 998);
			else
				mensaje = e.getMessage();

			generadorNotaCreditoDao.actualizarEstadoAceptado(generadorNotaCreditoDao.obtenerNotaCredito(idDocumento), -1, mensaje);

			return false;

		}

	}

	public static RespuestaCdr obtenerRespuestaCdr(String archivoXml) {

		RespuestaCdr respuestaCdr = new RespuestaCdr();
		Document doc = obtenerArchivoXML(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaRespuesta
						+ Constantes.siglaRespuesta + Constantes.separadorNombreArchivo + archivoXml);

		NodeList nList = doc.getElementsByTagName("cac:DocumentResponse");
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;

		respuestaCdr.setDescripcion(eElement.getElementsByTagName("cac:Response").item(0).getTextContent());
		respuestaCdr.setCodigo(eElement.getElementsByTagName("cbc:ResponseCode").item(0).getTextContent());
		respuestaCdr.setTicket(doc.getDocumentElement().getElementsByTagName("cbc:ID").item(0).getTextContent());

		System.out.println(
				respuestaCdr.getTicket() + "--" + respuestaCdr.getCodigo() + "--" + respuestaCdr.getDescripcion());

		return respuestaCdr;
	}

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

	public boolean enviarComunicacionBajaSunat(String idDocumento, String rutaComplta, String archivoZip,
			String archivoXml, String usuario, String contrasenia) {

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(rutaComplta + archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			BindingProvider bindingProvider = (BindingProvider) billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, contrasenia);

			// List<Handler> handlerList = bindingProvider.getBinding().getHandlerChain();
			// if (handlerList == null)
			// handlerList = new ArrayList<Handler>();
			//
			// handlerList.add(new SecurityHandler(usuario, contrasenia));

			String ticket = billService.sendSummary(archivoZip, dataHandler, "");

			System.out.println("Ticket -->>>" + ticket);

			StatusResponse statusResponse = billService.getStatus(ticket);

			byte[] respuesta = statusResponse.getContent();
			statusResponse.getStatusCode();

			System.out.println("Ticket Status Code -->>>" + statusResponse.getStatusCode());

			Compresor.crearArchivoZip(
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaRespuestaComunicacionBaja,
					respuesta);

			// byte[] respuesta = billService.sendBill(archivoZip, dataHandler, "");
			//
			// Compresor.crearArchivoZip(ParametrosGlobales.obtenerParametros().getRutaRaiz()
			// + Constantes.rutaRespuesta,
			// respuesta);
			//
			// RespuestaCdr respuestaCdr =
			// GestorArchivosXML.obtenerRespuestaCdr(archivoXml);
			//
			// generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
			// Integer.parseInt(respuestaCdr.getCodigo()),
			// respuestaCdr.getTicket() + "-" + respuestaCdr.getDescripcion());

			return true;

		} catch (Exception e) {

			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			String mensaje = "";

			System.out.println("Ticket -->>>" + e.getMessage());

			// if(e.getMessage().length() > 998)
			// mensaje = e.getMessage().substring(0, 998);
			// else
			// mensaje = e.getMessage();
			//
			// generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
			// -1, mensaje);

			return false;

		}

	}

}