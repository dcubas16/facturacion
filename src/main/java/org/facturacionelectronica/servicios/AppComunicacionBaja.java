package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.Utilitario;

import com.helger.commons.state.ESuccess;

public class AppComunicacionBaja {

	public void execAppComunicacionBaja() throws Exception {
		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		boolean respuesta = exportadorBaseDatos
				.exportarComunicacionBaja(Constantes.rutaCompleta + Constantes.rutaImportar);

		System.out.println("------------------>Obteniendo Facturas Importadas de Base de Datos");
		GestorWebService gestorWebService = new GestorWebService();

		// AQUI SE DEBE GENERAR EL ZIP

		List<ComunicacionBajaDao> listaComunicacionBajas = exportadorBaseDatos.obtenerComunicacionBajaDao();

//		for (FacturaDao facturaDao : listaFacturaDao) {
//
//			List<DetalleFacturaDao> listDetalleFacturaDao = exportadorBaseDatos
//					.obtenerDetalleFactura(facturaDao.getIdFactura());
//
//			List<DetalleFactura> listaDetalleFactura = new ArrayList<DetalleFactura>();
//
//			for (DetalleFacturaDao detalleFacturaDao : listDetalleFacturaDao) {
//				listaDetalleFactura.add(new DetalleFactura(detalleFacturaDao));
//			}
//
//			Factura factura = new Factura();
//			factura.setCabeceraFactura(new CabeceraFactura(facturaDao));
//			factura.setDetalleFactura(listaDetalleFactura);
//
//			GeneradorFactura generadorFactura = new GeneradorFactura();
//
//			ESuccess eSuccess = generadorFactura.generarFactura(factura);
//
//			String nombreArchivo = Utilitario.obtenerNombreArchivoFactura(factura);
//
//			if (eSuccess.isSuccess()) {
//
//			}
//
//		}
	}
}
