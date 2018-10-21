package org.facturacionelectronica.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.facturacionelectronica.util.Constantes;
import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;

public class GestorPdf {

	public void generarPDF(String nombreArchivo) {

		String archivoJrxml = "D:\\proyectos\\Facturacion_Electronica\\SFS_v1.2\\sunat_archivos\\sfs\\FORM\\"
				+ "Plantilla_reporte_factura.jasper";
		String patronXPath = "/Invoice/InvoiceLine";

		String reporteSalida = Constantes.rutaCompleta + Constantes.rutaPdf + nombreArchivo + Constantes.extensionPdf;
		String xmlOrigenDatos = Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo
				+ Constantes.extensionXml;

		try {
			imprimirComprobante(archivoJrxml, reporteSalida, xmlOrigenDatos, patronXPath, nombreArchivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error " + e);
		}

		String retorno = "Se acaba de crear el archivo " + nombreArchivo + ".pdf, para consultarlo vaya a la ruta: "
				+ Constantes.rutaCompleta;

		System.out.println("---> " + retorno);
	}

	public void imprimirComprobante(String nombreReporteJasper, String nombreArchivoSalida, String nombreArchivoXml,
			String rutaBaseXml, String nombreArchivo) throws Exception {

		try {
			/* Se depura el archivo xml de los namespaces */

			String archivoXsl = Constantes.rutaCompleta + "Depura_Xml_Impresion.xsl";
			String archivoOrigen = Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo
					+ Constantes.extensionXml;
			String nombreArchivoAux = this.transform(archivoOrigen, archivoXsl, nombreArchivoXml, nombreArchivo);

			/* Obtener los datos para generar QR */
			GeneradorQr generadorQr = new GeneradorQr();
			String rutaImagenQr = generadorQr.generarCodigoQr(nombreArchivoXml);

			Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(nombreArchivoXml));
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(nombreReporteJasper));

			JRXmlDataSource xmlDataSource = new JRXmlDataSource(nombreArchivoXml, rutaBaseXml);

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("RUTA_IMAGEN_QR", rutaImagenQr);
			parametros.put("XML_DATA_DOCUMENT", document);
			parametros.put("SUBREPORT_DIR", Constantes.rutaCompleta + Constantes.rutaPlantillasReportes);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, xmlDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, nombreArchivoSalida);

		} catch (Exception e) {

			System.out.println("Excepcion " + e);
			// log.error("Error Generado en
			// ReporteDocumentosServiceImpl.imprimirComprobante: " + e.getMessage() + "
			// Causa: " + e.getCause());
			// throw new Exception("Error Generado en
			// ReporteDocumentosServiceImpl.imprimirComprobante: " + e.getMessage() + "
			// Causa: " + e.getCause());
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
