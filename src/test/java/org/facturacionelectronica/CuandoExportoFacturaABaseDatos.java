package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.ConfiguracionBaseDatos;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.servicios.ExportadorBaseDatos;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class CuandoExportoFacturaABaseDatos {

	GeneradorFacturaDao generador = new GeneradorFacturaDao();

	@Test
	public void entoncesDebeGuardarFactura() throws ParseException {
		BasicConfigurator.configure();

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		boolean respuesta = exportadorBaseDatos.exportarFacturas(
				"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\factura.txt");

		assertTrue(respuesta);

	}

	@Test
	public void cuandoPrueboConexionBaseDatos() {

		BasicConfigurator.configure();

		Session sessionObj = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = sessionObj.beginTransaction();

		sessionObj.close();

		assertTrue(true);

	}

	@Test
	public void cuandoObtengoCabeceraFactura() throws ParseException {

		BasicConfigurator.configure();

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		FacturaDao facturaDao = exportadorBaseDatos.obtenerFactura("F001-4355");

		assertTrue(facturaDao.getIdFactura().equals("F001-4355"));

	}

	@Test
	public void cuandoObtengoDetalleFactura() {

		BasicConfigurator.configure();

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		List<DetalleFacturaDao> listaFacturaDao = exportadorBaseDatos.obtenerDetalleFactura("F001-4355");

		assertTrue(listaFacturaDao.size() > 0);

	}
	
	
	@Test
	public void cuandoObtengoFacturasImportadas() {

		BasicConfigurator.configure();

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		List<FacturaDao> listaFacturaDao = exportadorBaseDatos.obtenerFacturasImportadas();

		assertTrue(listaFacturaDao.size() > 0);

	}
	
	
	

}
