package org.facturacionelectronica.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.Factura;

public class Utilitario {

	public static String obtenerNombreArchivoFactura(Factura factura) {

		String nombreArchivo = factura.getCabeceraFactura().getNumeroDocumento() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getTipoDocumentoFactura() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getSerie() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getNumeroCorrelativo();

		return nombreArchivo;
	}

	public static String obtenerRutaEjecutable() {
		File f1 = new File(System.getProperty("java.class.path"));
		File dir = f1.getAbsoluteFile().getParentFile();
		String path = dir.toString() + Constantes.SimboloDobleEslash;

		return path;
	}

	public static XMLGregorianCalendar obtenerFechaXMLGregorianCalendar(Date fecha) {

		SimpleDateFormat formatoFechaXml = new SimpleDateFormat("yyyy-MM-dd");
		String cadenaFecha = formatoFechaXml.format(fecha);
		XMLGregorianCalendar xmlFecha;

		try {
			xmlFecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(cadenaFecha);
		} catch (DatatypeConfigurationException e) {
			xmlFecha = null;
			GestorExcepciones.guardarExcepcionPorValidacion(e, Utilitario.class);
		}
		return xmlFecha;
	}

	public static String obtenerFechaFormatoComunicaBaja(Date fecha) {

		SimpleDateFormat formatoFechaXml = new SimpleDateFormat("yyyyMMdd");
		String cadenaFecha = formatoFechaXml.format(fecha);

		return cadenaFecha;
	}

	public static String obtenerNombreArchivoComunicacionBaja(ComunicacionBaja comunicacionBaja) {

		// Nombre del archivo ZIP: 20100066603-RA-20110522.ZIP
		// Nombre del archivo XML: 20100066603-RA-20110522.XML

		String nombreArchivo = comunicacionBaja.getNumeroRuc().toString() + Constantes.separadorNombreArchivo
				+ Constantes.siglaIdentComunicacionBaja + Constantes.separadorNombreArchivo
				+ obtenerFechaFormatoComunicaBaja(comunicacionBaja.getFechaGeneraComunica())
				+ Constantes.separadorNombreArchivo + Constantes.numeroComunicacionBaja;

		return nombreArchivo;
	}

}
