package org.facturacionelectronica.servicios;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

//	public void generarPDF(String nombreArchivo) {
//		
//		
//		String archivoJrxml = Constantes.rutaCompleta + Constantes.rutaPlantillasReportes  + "Plantilla_reporte_boleta.jasper";
//		String patronXPath = "/Invoice/InvoiceLine";
//		
//		String reporteSalida = Constantes.rutaCompleta + Constantes.rutaPdf +  nombreArchivo + Constantes.extensionPdf;
//		String xmlOrigenDatos = Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo + ".xml"; 
//					
//		reporteDocumentosService.imprimirComprobante(archivoJrxml,reporteSalida,xmlOrigenDatos,patronXPath, nomArch);
//		
//		retorno = "Se acaba de crear el archivo " + nomArch + ".pdf, para consultarlo vaya a la ruta: "+comunesService.obtenerRutaTrabajo(CONSTANTE_REPO);
//	}
//	
//	
//	public void imprimirComprobante(String nombreReporteJasper, String nombreArchivoSalida, String nombreArchivoXml, String rutaBaseXml, String nombreArchivo) throws Exception{
//		
//		
//		try{
//				/* Se depura el archivo xml de los namespaces */
//				String archivoXsl = comunesService.obtenerRutaTrabajo(CONSTANTE_FORMATO) + "Depura_Xml_Impresion.xsl";
//				String archivoOrigen = comunesService.obtenerRutaTrabajo(CONSTANTE_FIRMA) + nombreArchivo + ".xml";
//				this.transform(archivoOrigen, archivoXsl, nombreArchivoXml);
//				
//				/* Obtener los datos para generar QR */
//				String rutaImagenQr = reporteDocumentosAdicionalService.generarCodigoQr(nombreArchivoXml);
//								
//				Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(nombreArchivoXml));
//				JasperReport jasperReport = (JasperReport)JRLoader.loadObject(new File(nombreReporteJasper));
//				
//				JRXmlDataSource xmlDataSource = new JRXmlDataSource(nombreArchivoXml,rutaBaseXml);
//				
//				Map<String, Object> parametros = new HashMap<String, Object>();
//		        parametros.put("RUTA_IMAGEN_QR", rutaImagenQr);
//				parametros.put("XML_DATA_DOCUMENT", document);
//		        parametros.put("SUBREPORT_DIR", comunesService.obtenerRutaTrabajo(CONSTANTE_FORMATO));
//		        
//				
//			    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, xmlDataSource);	
//				JasperExportManager.exportReportToPdfFile(jasperPrint, nombreArchivoSalida);
//
//		}catch(Exception e){
////	    	log.error("Error Generado en ReporteDocumentosServiceImpl.imprimirComprobante: " + e.getMessage() + " Causa: " + e.getCause());
////	    	throw new Exception("Error Generado en ReporteDocumentosServiceImpl.imprimirComprobante:  " + e.getMessage() + " Causa: " + e.getCause());
//	    }
//		 
//	}
}
