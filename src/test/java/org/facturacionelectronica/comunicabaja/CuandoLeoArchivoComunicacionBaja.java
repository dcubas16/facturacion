package org.facturacionelectronica.comunicabaja;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.ManejadorArchivos;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.junit.Test;

public class CuandoLeoArchivoComunicacionBaja {

	@Test
	public void entoncesDebeLeerArchivoComunicacionBaja() throws Exception {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = manejadorArchivos.leerArchivo(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaComunicaBaja + "A-2038184792704BT050000000201.txt");

		assertTrue(lineasArchivo.size()>0);
	}
}
