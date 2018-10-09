package org.facturacionelectronica.servicios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.servicios.*;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws ParserConfigurationException {
		BasicConfigurator.configure();
		System.out.println("------------------>Iniciando Importacion Archivos");
		System.out.println("------------------>Leyendo Facturas");
		System.out.println("------------------>Importando Facturas a Base de Datos");

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		try {
			boolean respuesta = exportadorBaseDatos.exportarFacturas(
					"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\factura.txt");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("------------------>Obteniendo Facturas Importadas de Base de Datos");

		List<FacturaDao> listaFacturaDao = exportadorBaseDatos.obtenerFacturasImportadas();

		for (FacturaDao facturaDao : listaFacturaDao) {

			List<DetalleFacturaDao> listDetalleFacturaDao = exportadorBaseDatos
					.obtenerDetalleFactura(facturaDao.getIdFactura());
			List<DetalleFactura> listaDetalleFactura = new ArrayList<DetalleFactura>();

			for (DetalleFacturaDao detalleFacturaDao : listDetalleFacturaDao) {
				listaDetalleFactura.add(new DetalleFactura(detalleFacturaDao));
			}

			Factura factura = new Factura();
			factura.setCabeceraFactura(new CabeceraFactura(facturaDao));
			factura.setDetalleFactura(listaDetalleFactura);

			GeneradorFactura generadorFactura = new GeneradorFactura();
			generadorFactura.generarFactura(factura);
		}

	}
}
