package org.facturacionelectronica.servicios;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.entidades.DetalleNotaCreditoDao;
import org.facturacionelectronica.dao.entidades.NotaCreditoDao;
import org.facturacionelectronica.entidades.DetalleNotaCredito;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;

import com.helger.commons.state.ESuccess;

public class AppNotaCredito {

	public static void main(String[] args) throws Exception {

		try {

		} catch (Exception ex) {

		}

		BasicConfigurator.configure();
		System.out.println("------------------>Iniciando Importacion Archivos");
		System.out.println("------------------>Leyendo Notas de Crédito");
		System.out.println("------------------>Importando Notas de Crédito a Base de Datos");

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		boolean respuesta = exportadorBaseDatos
				.exportarNotasCredito(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImportarNotaCredito);

		List<NotaCreditoDao> listaNotaCreditoDao = exportadorBaseDatos.obtenerNotaCreditoImportadas();

		for (NotaCreditoDao notaCreditoDao : listaNotaCreditoDao) {

//			List<DetalleNotaCreditoDao> listDetalleNotaCreditoDao = exportadorBaseDatos
//					.obtenerDetalleNotaCredito(notaCreditoDao.getIdNotaCredito());
//
//			List<DetalleNotaCredito> listaDetalleNotaCredito = new ArrayList<DetalleNotaCredito>();
//
//			for (DetalleNotaCreditoDao detalleNotaCreditoDao : listDetalleNotaCreditoDao) {
//				listaDetalleNotaCredito.add(new DetalleNotaCredito(detalleNotaCreditoDao));
//			}
//
//			NotaCredito factura = new NotaCredito();
//			factura.setCabeceraNotaCredito(new CabeceraNotaCredito(NotaCreditoDao));
//			factura.setDetalleNotaCredito(listaDetalleNotaCredito);
//
//			GeneradorFactura generadorFactura = new GeneradorFactura();
//
//			ESuccess eSuccess = generadorFactura.generarNotaCredito(factura);
//
//			String nombreArchivo = Utilitario.obtenerNombreArchivoNotaCredito(factura);
//
//			if (eSuccess.isSuccess()) {
//				
//				try {
//					GestorFirma gestorFirma = new GestorFirma();
//					InputStream inputStream = new FileInputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz()
//							+ Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);
//					Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream, facturaDao);
//					FileOutputStream fout = new FileOutputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz()
//							+ Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);
//					ByteArrayOutputStream outDocument = (ByteArrayOutputStream) xmlFirmado.get("signatureFile");
//					String digestValue = (String) xmlFirmado.get("digestValue");
//
//					outDocument.writeTo(fout);
//					fout.close();
//				}catch(Exception ex) {
//					GestorExcepciones.guardarExcepcionPorValidacion(ex, Object.class);
//				}
//
//				
//
//				// Generar archivo ZIP
//				// Compresor.comprimirArchivo(
//				// ParametrosGLobales.obtenerParametros().getRutaRaiz() +
//				// Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionZip,
//				// ParametrosGLobales.obtenerParametros().getRutaRaiz() +
//				// Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml,
//				// nombreArchivo + Constantes.extensionXml);
//
//				// Genero PDF
//				try {
//					GestorPdf gestorPdf = new GestorPdf();
//					gestorPdf.generarPDF(nombreArchivo, facturaDao);
//				} catch (Exception e) {
//
//					GestorExcepciones.guardarExcepcionPorValidacion(e, Object.class);
//
//				}
//
//				GeneradorFacturaDao generadorFacturaDao = new GeneradorFacturaDao();
//				facturaDao.setEstado(6);
//
//				generadorFacturaDao.actualizarFactura(facturaDao);
//
//			}

		}

		System.out.println("Eliminando Proceso Importacion Archivos Factura...");
		System.exit(0);

	}

}
