package org.facturacionelectronica.util;

import java.io.File;
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

}
