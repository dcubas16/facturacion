package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.servicios.ManejadorArchivos;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.junit.Test;

public class CuandoImportoDatosDeArchivo {

	@Test
	public void entoncesObtengoFacturasArchivo()  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = manejadorArchivos.leerArchivo("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\archivo_lectura.txt");

		assertTrue(lineasArchivo.size()>0);
	}

	
	@Test
	public void entoncesObtengoFacturasDeCarpeta()  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();
		
		List<String> lineasArchivo = manejadorArchivos.leerCarpeta(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImportar);

		assertTrue(lineasArchivo.size()>0);
	}
	
	
	@Test
	public void entoncesGeneroCabeceraFactura() throws ParseException  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = new ArrayList<>();
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|");

		List<CabeceraFactura> listaCabeceraFactura = manejadorArchivos.generarCabeceraFactura(lineasArchivo);
		
		assertTrue(listaCabeceraFactura.size()>0);

	}
	
	@Test
	public void entoncesGeneroDetalleFactura() throws ParseException  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = new ArrayList<>();
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");
		lineasArchivo.add("F001-4356|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");
		lineasArchivo.add("F001-4356|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");
		lineasArchivo.add("F001-4356|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");

		List<DetalleFactura> listaDetalleFactura = manejadorArchivos.generarDetalleFactura(lineasArchivo, "F001-4355");
		
		assertTrue((listaDetalleFactura.size()==4));

	}
	
	
	@Test
	public void siArchivoFacturaExisteEntoncesRetornarVerdadero() {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		
		assertTrue(false);

	}
	
	@Test
	public void siArchivoFacturaTieneFormatoCorrectoRetornarVerdadero() {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		
		assertTrue(false);

	}
	
}
