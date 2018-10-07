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

public class CuandoGeneroFacturaElectronica {

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

	

	
	

	
}
