package org.facturacionelectronica.servicios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;

public class ExportadorBaseDatos {

	private List<String> lineasArchivo = new ArrayList<String>();
	ManejadorArchivos manejadorArchivos = new ManejadorArchivos();
	GeneradorFacturaDao generadorFacturaDao = new GeneradorFacturaDao();

	public ExportadorBaseDatos() {
	}

	public boolean exportarFacturas(String rutaArchivo) throws ParseException {
		// Lee un archivo
		// lineasArchivo = manejadorArchivos.leerArchivo(rutaArchivo);

		// Lee toda la carpeta
		lineasArchivo = manejadorArchivos.leerCarpeta(rutaArchivo);

		List<CabeceraFactura> listaCabeceraFacturas = manejadorArchivos.generarCabeceraFactura(lineasArchivo);

		for (CabeceraFactura cabeceraFactura : listaCabeceraFacturas) {
			List<DetalleFactura> listaDetalleFacturas = manejadorArchivos.generarDetalleFactura(lineasArchivo,
					cabeceraFactura.getIdFactura());

			generadorFacturaDao.guardarFactura(cabeceraFactura, listaDetalleFacturas);

		}

		return true;
	}

	public FacturaDao obtenerFactura(String idFactura) {
		FacturaDao facturaDao = generadorFacturaDao.obtenerFactura(idFactura);
		return facturaDao;
	}

	public List<DetalleFacturaDao> obtenerDetalleFactura(String idFactura) {

		List<DetalleFacturaDao> listDetalleFacturaDaos = generadorFacturaDao.obtenerDetalleFactura(idFactura);

		return listDetalleFacturaDaos;
	}

	public List<FacturaDao> obtenerFacturasImportadas() {
		List<FacturaDao> listaFacturaDaos = generadorFacturaDao.obtenerFacturasImportadas();

		return listaFacturaDaos;
	}

	public List<FacturaDao> obtenerFacturasPendientesDeEnvio() {
		List<FacturaDao> listaFacturaDaos = generadorFacturaDao.obtenerFacturasPendientesDeEnvio();

		return listaFacturaDaos;
	}

}
