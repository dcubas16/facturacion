package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.ManejadorArchivos;
import org.facturacionelectronica.util.Constantes;
import org.junit.Test;

public class CuandoLeoArchivoComunicacionBaja {

	@Test
	public void entoncesDebeLeerArchivoComunicacionBaja() throws Exception {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = manejadorArchivos.leerArchivo(Constantes.rutaCompleta + Constantes.rutaComunicaBaja );

		assertTrue(lineasArchivo.size()>0);
	}
}
