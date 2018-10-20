package org.facturacionelectronica.util;

import org.facturacionelectronica.entidades.Factura;

public class Utilitario {

	public static String obtenerNombreArchivoFactura(Factura factura) {

		String nombreArchivo = factura.getCabeceraFactura().getNumeroDocumento() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getTipoDocumentoFactura() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getSerie() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getNumeroCorrelativo();

		return nombreArchivo;
	}

}
