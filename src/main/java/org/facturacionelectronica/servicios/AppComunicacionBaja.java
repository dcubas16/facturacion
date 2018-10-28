package org.facturacionelectronica.servicios;

import java.util.List;

import org.apache.log4j.Logger;
import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleComunicaBajaDao;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;

public class AppComunicacionBaja {
	
	static Logger logger = Logger.getLogger(AppComunicacionBaja.class);

	public static void main(String[] args) throws Exception {

		String rutaCompleta = ParametrosGlobales.obtenerParametros().getRutaRaiz();

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		boolean respuesta = exportadorBaseDatos
				.exportarComunicacionBaja(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImportarComunicaBaja);

		System.out.println("------------------>Obteniendo Facturas Importadas de Base de Datos");
		GestorWebService gestorWebService = new GestorWebService();

		// AQUI SE DEBE GENERAR EL ZIP

		List<ComunicacionBajaDao> listaComunicacionBajas = exportadorBaseDatos.obtenerComunicacionBajaImportados();
		GeneradorComunicacionBaja generadorComunicacionBaja = new GeneradorComunicacionBaja();

		for (ComunicacionBajaDao comunicacionBajaDao : listaComunicacionBajas) {
			
			List<DetalleComunicaBajaDao> listDetalleComunicaBajaDao = exportadorBaseDatos
					 .obtenerDetalleComunicaBajaDao(comunicacionBajaDao.getIdComunicaionBaja());
			
			generadorComunicacionBaja.formatoResumenBajas(comunicacionBajaDao, listDetalleComunicaBajaDao);
			
		}
//		 for (FacturaDao facturaDao : listaFacturaDao) {
//		
//		 List<DetalleFacturaDao> listDetalleFacturaDao = exportadorBaseDatos
//		 .obtenerDetalleFactura(facturaDao.getIdFactura());
//		
//		 List<DetalleFactura> listaDetalleFactura = new ArrayList<DetalleFactura>();
//		
//		 for (DetalleFacturaDao detalleFacturaDao : listDetalleFacturaDao) {
//		 listaDetalleFactura.add(new DetalleFactura(detalleFacturaDao));
//		 }
//		
//		 Factura factura = new Factura();
//		 factura.setCabeceraFactura(new CabeceraFactura(facturaDao));
//		 factura.setDetalleFactura(listaDetalleFactura);
//		
//		 GeneradorFactura generadorFactura = new GeneradorFactura();
//		
//		 ESuccess eSuccess = generadorFactura.generarFactura(factura);
//		
//		 String nombreArchivo = Utilitario.obtenerNombreArchivoFactura(factura);
//		
//		 if (eSuccess.isSuccess()) {
//		
//		 }
//		
//		 }

		System.out.println("Eliminando Proceso Importacion Archivos...");
		System.exit(0);
	}
}
