package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.ConfiguracionBaseDatos;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.servicios.GeneradorFactura;
import org.facturacionelectronica.servicios.ManejadorArchivos;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import com.helger.commons.state.ESuccess;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;
import javax.xml.ws.BindingProvider;

public class GeneradorFacturaElectronica {

	GeneradorFactura generadorFactura = new GeneradorFactura();

	@Test
	public void enviarFacturaSUNAT()  {
		BasicConfigurator.configure();

		try {
			
			BillService_Service billService_Service = new BillService_Service();
			

			BillService billService = billService_Service.getBillServicePort();
			
					
			DataSource dataSource = new FileDataSource(new File("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\20100066603-01-F001-1.ZIP"));
			
			DataHandler dataHandler = new DataHandler(dataSource);
			
			BindingProvider bindingProvider = (BindingProvider)billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "20100066603MODDATOS");
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "MODDATOS");
			
			billService.sendBill("20100066603-01-F001-1.ZIP", dataHandler, null);

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage()); 
			
		}


	}
	
	
	@Test
	public void cuandoGeneroFacturaElectronica() throws ParserConfigurationException {
		BasicConfigurator.configure();

		final ESuccess eSuccess = generadorFactura.generarFactura(FacturaMock.obtenerFactura());

		assertTrue(eSuccess.isSuccess());

	}

	
	@Test
	public void cuandoObtengoFacturasArchivo()  {
		BasicConfigurator.configure();

		ManejadorArchivos manejadorArchivos = new ManejadorArchivos();

		List<String> lineasArchivo = manejadorArchivos.leerArchivo("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\archivo_lectura.txt");

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
		lineasArchivo.add("F001-4355|1.0|04/03/2012|firmaDigital||Soporte Tecnológicos EIRL||Av. Los Precursores # 1245|Urb. Miguel Grau|Lima|Lima|Lima||20100454523|6|01|F001|4355|20587896411|6|Servicabinas S.A.|348199.15||12350.00|30.00|62675.85||59230.51|423225.00|CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100|1|NIU|GLG199|Grabadora LG Externo Modelo: GE20LU10|2000|83.05|98.00|14.95|166100.00|149491.53|26908.47|18.00|");

		DetalleFactura detalleFactura = manejadorArchivos.generarDetalleFactura(lineasArchivo, "F001-4355");
		
		assertTrue(!detalleFactura.getCodigoItem().isEmpty());

	}
	
	
	@Test
	public void cuandoPrueboConexionBaseDatos(){
		
		BasicConfigurator.configure();

		Session sessionObj =  ConfiguracionBaseDatos.getSessionFactory().openSession();

		//Creating Transaction Object  
		Transaction transObj = sessionObj.beginTransaction();
		
		sessionObj.close();
		
		assertTrue(true);

	}
	
}
