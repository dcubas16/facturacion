package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.ConfiguracionBaseDatos;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.servicios.ExportadorBaseDatos;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class CuandoExportoFacturaABaseDatos {
	
	GeneradorFacturaDao generador = new GeneradorFacturaDao();
	
	
	@Test
	public void entoncesDebeGuardarFactura() throws ParseException  {
		BasicConfigurator.configure();
		
		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();
		
		
//		boolean respuesta = 

		assertTrue(false);

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
