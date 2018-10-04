package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.servicios.GeneradorFactura;
import org.facturacionelectronica.servicios.ManejadorArchivos;
import org.junit.Test;
import com.helger.commons.state.ESuccess;

public class GeneradorFacturaElectronica {

	GeneradorFactura generadorFactura = new GeneradorFactura();

//	@Test
//	public void cuandoGeneroFacturaElectronica() throws ParserConfigurationException {
//		BasicConfigurator.configure();
//
//		final ESuccess eSuccess = generadorFactura.generarFactura(FacturaMock.obtenerFactura());
//
//		assertTrue(eSuccess.isSuccess());
//
//	}

	
	@Test
	public void cuandoObtengoFacturasArchivo()  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = manejadorArchivos.leerArchivo("C:\\DANC\\Spring\\proyectos\\facturacionelectronica\\src\\site\\archivo_lectura.txt");

		assertTrue(lineasArchivo.size()>0);

	}
	
	@Test
	public void cuandoGeneroCabeceraFactura() throws ParseException  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = new ArrayList<>();
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|");

		CabeceraFactura cabeceraFactura = manejadorArchivos.generarCabeceraFactura(lineasArchivo);
		
		assertTrue(!cabeceraFactura.getIdFactura().isEmpty());

	}
	
	@Test
	public void cuandoGeneroDetalleFactura() throws ParseException  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = new ArrayList<>();
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|");

		DetalleFactura detalleFactura = manejadorArchivos.generarDetalleFactura(lineasArchivo, "F001-4355");
		
		assertTrue(!detalleFactura.getCodigoItem().isEmpty());

	}
	
	
	
}
