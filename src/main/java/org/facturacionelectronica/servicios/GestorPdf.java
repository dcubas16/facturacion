package org.facturacionelectronica.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.dao.entidades.NotaCreditoDao;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;

public class GestorPdf {

	public void generarPDF(String nombreArchivo, FacturaDao facturaDao) {

		try {

//			String archivoJrxml = "D:\\proyectos\\Facturacion_Electronica\\SFS_v1.2\\sunat_archivos\\sfs\\FORM\\"
//					+ "Plantilla_reporte_factura.jasper";// -------------------->>> Para Pruebas

			 String archivoJrxml = ParametrosGlobales.obtenerParametros().getRutaRaiz()
			 + Constantes.rutaPlantillasReportes + Constantes.reporteFactura;

			String patronXPath = "/Invoice/InvoiceLine";

			String reporteSalida = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaPdf
					+ nombreArchivo + Constantes.extensionPdf;

			String xmlOrigenDatos = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud
					+ nombreArchivo + Constantes.extensionXml;

			imprimirComprobante(archivoJrxml, reporteSalida, xmlOrigenDatos, patronXPath, nombreArchivo, facturaDao);

			System.out.println("---> " + "Se acaba de crear el archivo " + nombreArchivo
					+ ".pdf, para consultarlo vaya a la ruta: " + reporteSalida);

		} catch (Exception e) {
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
		}

	}
	
	
	public void generarPDF(String nombreArchivo, NotaCreditoDao notaCreditoDao) {
		try {

//			String archivoJrxml = "D:\\proyectos\\Facturacion_Electronica\\SFS_v1.2\\sunat_archivos\\sfs\\FORM\\"
//					+ "Plantilla_reporte_nota_credito.jasper";// -------------------->>> Para Pruebas

			 String archivoJrxml = ParametrosGlobales.obtenerParametros().getRutaRaiz()
			 + Constantes.rutaPlantillasReportes + Constantes.reporteNotaCredito;// -------------------->>> Para Produccion

			String patronXPath = "/CreditNote/CreditNoteLine";

			String reporteSalida = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaPdfNotaCredito
					+ nombreArchivo + Constantes.extensionPdf;

			String xmlOrigenDatos = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudNotaCredito
					+ nombreArchivo + Constantes.extensionXml;

			imprimirComprobante(archivoJrxml, reporteSalida, xmlOrigenDatos, patronXPath, nombreArchivo, notaCreditoDao);

			System.out.println("---> " + "Se acaba de crear el archivo " + nombreArchivo
					+ ".pdf, para consultarlo vaya a la ruta: " + reporteSalida);

		} catch (Exception e) {
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
		}
	}
	
	
	public void imprimirComprobante(String nombreReporteJasper, String nombreArchivoSalida, String nombreArchivoXml,
			String rutaBaseXml, String nombreArchivo, NotaCreditoDao notaCreditoDao) {

		try {
			/* Se depura el archivo xml de los namespaces */

			// String archivoXsl = ParametrosGlobales.obtenerParametros().getRutaRaiz() +
			// "Depura_Xml_Impresion.xsl";
			String archivoOrigen = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudNotaCredito
					+ nombreArchivo + Constantes.extensionXml;
			// this.transform(archivoOrigen, archivoXsl, nombreArchivoXml, nombreArchivo);

			/* Obtener los datos para generar QR */
			String rutaImagenQr = "";
			GeneradorQr generadorQr = new GeneradorQr();
			rutaImagenQr = generadorQr.generarCodigoQr(nombreArchivoXml, notaCreditoDao.getNumeroDocumento().toString());

			Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(nombreArchivoXml));
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(nombreReporteJasper));

			JRXmlDataSource xmlDataSource = new JRXmlDataSource(nombreArchivoXml, rutaBaseXml);

			BigDecimal bigDecimalSubTotal = notaCreditoDao.getImporteTotalVenta().subtract(notaCreditoDao.getSumatoriaIGV());

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("RUTA_IMAGEN_QR", rutaImagenQr);
			parametros.put("XML_DATA_DOCUMENT", document);
			parametros.put("PACIENTE", notaCreditoDao.getPaciente());
			parametros.put("DIRECCION_PACIENTE", notaCreditoDao.getDireccionPaciente());
			parametros.put("DIRECCION_EMISOR", notaCreditoDao.getDireccionCompleta());
			parametros.put("TIPO_CAMBIO", notaCreditoDao.getTipoCambio());
			parametros.put("MEDIO_PAGO", notaCreditoDao.getMedioPago());
			parametros.put("TELEFONO_EMISOR", notaCreditoDao.getTelefonoEmisor());
//			parametros.put("LEYENDA", notaCreditoDao.getLeyenda());
			parametros.put("SUBTOTAL", bigDecimalSubTotal.toString());
//			parametros.put("NOMBRE_TIPO_DOCUMENTO", nombreTipoDocumento);
			parametros.put("SUBREPORT_DIR",
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaPlantillasReportes);
//			 parametros.put("SUBREPORT_DIR",
//			 "D:\\proyectos\\Facturacion_Electronica\\SFS_v1.2\\sunat_archivos\\sfs\\FORM\\");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, xmlDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, nombreArchivoSalida);

		} catch (Exception e) {

			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
		}

	}
	
	private Document buildDocument(InputStream inDocument) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Reader reader = new InputStreamReader(inDocument, "ISO8859_1");
		// Reader reader = new InputStreamReader(inDocument, "UTF8");
		Document doc = db.parse(new InputSource(reader));
		return doc;
	}


	public void imprimirComprobante(String nombreReporteJasper, String nombreArchivoSalida, String nombreArchivoXml,
			String rutaBaseXml, String nombreArchivo, FacturaDao facturaDao) {

		try {
			/* Se depura el archivo xml de los namespaces */

			// String archivoXsl = ParametrosGlobales.obtenerParametros().getRutaRaiz() +
			// "Depura_Xml_Impresion.xsl";
			String archivoOrigen = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud
					+ nombreArchivo + Constantes.extensionXml;
			// this.transform(archivoOrigen, archivoXsl, nombreArchivoXml, nombreArchivo);

			/* Obtener los datos para generar QR */
			String rutaImagenQr = "";
			GeneradorQr generadorQr = new GeneradorQr();
			rutaImagenQr = generadorQr.generarCodigoQr(nombreArchivoXml, facturaDao.getNumeroDocumento().toString());

			Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(nombreArchivoXml));
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(nombreReporteJasper));

			JRXmlDataSource xmlDataSource = new JRXmlDataSource(nombreArchivoXml, rutaBaseXml);

			BigDecimal bigDecimalSubTotal = facturaDao.getImporteTotalVenta().subtract(facturaDao.getSumatoriaIGV());
			String nombreTipoDocumento = facturaDao.getTipoDocumentoFactura() == "01" ? "FACTURA ELECTRÓNICA"
					: "BOLETA ELECTRÓNICA";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("RUTA_IMAGEN_QR", rutaImagenQr);
			parametros.put("XML_DATA_DOCUMENT", document);
			parametros.put("RUTA_IMAGEN_QR", rutaImagenQr);
			parametros.put("PACIENTE", facturaDao.getPaciente());
			parametros.put("DIRECCION_PACIENTE", facturaDao.getDireccionPaciente());
			parametros.put("DIRECCION_EMISOR", facturaDao.getDireccionCompleta());
			parametros.put("TIPO_CAMBIO", facturaDao.getTipoCambio());
			parametros.put("MEDIO_PAGO", facturaDao.getMedioPago());
			parametros.put("TELEFONO_EMISOR", facturaDao.getTelefonoEmisor());
			parametros.put("LEYENDA", facturaDao.getLeyenda());
			parametros.put("SUBTOTAL", bigDecimalSubTotal.toString());
			parametros.put("NOMBRE_TIPO_DOCUMENTO", nombreTipoDocumento);
			parametros.put("SUBREPORT_DIR",
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaPlantillasReportes);
//			 parametros.put("SUBREPORT_DIR",
//			 "D:\\proyectos\\Facturacion_Electronica\\SFS_v1.2\\sunat_archivos\\sfs\\FORM\\");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, xmlDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, nombreArchivoSalida);

		} catch (Exception e) {

			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
		}

	}

	private String transform(String dataXML, String inputXSL, String outputHTML, String nombreArchivo)
			throws Exception {
		String retorno = "", mensaje = "";

		File archivoXSL = new File(inputXSL);

		if (!archivoXSL.exists())
			throw new Exception("No existe la plantilla para el tipo documento a validar XML (Archivo XSL).");

		StreamSource xlsStreamSource = new StreamSource(inputXSL);
		StreamSource xmlStreamSource = new StreamSource(dataXML);

		TransformerFactory transformerFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl",
				null);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(nombreArchivo);

			Transformer transformer = transformerFactory.newTransformer(xlsStreamSource);
			transformer.transform(xmlStreamSource, new StreamResult(fos));
			fos.close();
		} catch (Exception e) {
			try {
				String mensajeError = "", lineaArchivo = "", nroObtenido = "";
				Integer numeroLinea = 0;
				mensaje = e.getMessage();
				nroObtenido = obtenerNumeroEnCadena(mensaje);
				if (nroObtenido.length() > 0)
					numeroLinea = new Integer(nroObtenido);
				lineaArchivo = obtenerCodigoError(inputXSL, numeroLinea);
				if (lineaArchivo.length() > 0)
					nroObtenido = obtenerNumeroEnCadena(lineaArchivo);

				if ("".equals(nroObtenido)) {
					retorno = mensaje;
				} else {
					// TxxxzBean txxxzBean = txxxzDAO.consultarErrorById(new Integer(nroObtenido));
					// if (txxxzBean != null)
					// mensajeError = txxxzBean.getNom_error();
					// else
					// mensajeError = mensaje;

					retorno = nroObtenido + " - " + mensajeError;
				}
				fos.close();
			} catch (Exception ex) {
				// log.error("Error Ejecucion de Cierre Archivo: " + ex.getMessage());
				retorno = ex.getMessage();
			}
		}

		return retorno;

	}

	public static String obtenerCodigoError(String rutaArchivo, Integer lineaArchivo) throws Exception {
		String linea = "";
		Integer contador = 1;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(rutaArchivo));
			while ((linea = br.readLine()) != null) {
				if (contador.intValue() == lineaArchivo.intValue())
					break;
				contador++;
			}
			br.close();
		} catch (Exception e) {
			throw new Exception("Error en el utilitario obtenerLineaArchivo: " + e.getMessage());
		}

		if (linea == null)
			linea = "";

		return linea;
	}

	public static String obtenerNumeroEnCadena(String mensaje) throws Exception {
		Integer posicion = Integer.valueOf(mensaje.indexOf("codigo"));
		if (posicion.intValue() > 0)
			mensaje = mensaje.substring(posicion.intValue());

		Integer largo = mensaje.length();
		String numero = "";
		for (int i = 0; i < largo; i++) {
			if (Character.isDigit(mensaje.charAt(i)))
				numero = numero + mensaje.charAt(i);
		}

		return numero;
	}


	

}
