package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.facturacionelectronica.util.Utilitario;
import com.helger.commons.state.ESuccess;

public class App {

	public static void main(String[] args) throws Exception {

		try {

		} catch (Exception ex) {

		}

		BasicConfigurator.configure();
		System.out.println("------------------>Iniciando Importacion Archivos");
		System.out.println("------------------>Leyendo Facturas");
		System.out.println("------------------>Importando Facturas a Base de Datos");

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		boolean respuesta = exportadorBaseDatos
				.exportarFacturas(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImportar);

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

			ESuccess eSuccess;

			GeneradorFactura21 generadorFactura21 = new GeneradorFactura21();
			eSuccess = generadorFactura21.generarFactura(factura);

			String nombreArchivo = Utilitario.obtenerNombreArchivoFactura(factura);

			if (eSuccess.isSuccess()) {

				try {
					GestorFirma gestorFirma = new GestorFirma();
					InputStream inputStream = new FileInputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz()
							+ Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);
					Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream, facturaDao);
					FileOutputStream fout = new FileOutputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz()
							+ Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);
					ByteArrayOutputStream outDocument = (ByteArrayOutputStream) xmlFirmado.get("signatureFile");
					String digestValue = (String) xmlFirmado.get("digestValue");

					outDocument.writeTo(fout);
					fout.close();
				} catch (Exception ex) {
					GestorExcepciones.guardarExcepcionPorValidacion(ex, Object.class);
				}

				// Generar archivo ZIP
				// Compresor.comprimirArchivo(
				// ParametrosGLobales.obtenerParametros().getRutaRaiz() +
				// Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionZip,
				// ParametrosGLobales.obtenerParametros().getRutaRaiz() +
				// Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml,
				// nombreArchivo + Constantes.extensionXml);

				// Genero PDF
				try {
					GestorPdf gestorPdf = new GestorPdf();
					gestorPdf.generarPDF(nombreArchivo, facturaDao);
				} catch (Exception e) {

					GestorExcepciones.guardarExcepcionPorValidacion(e, Object.class);
				}

				GeneradorFacturaDao generadorFacturaDao = new GeneradorFacturaDao();
				facturaDao.setEstado(6);

				generadorFacturaDao.actualizarFactura(facturaDao);

			}

		}

		System.out.println("Eliminando Proceso Importacion Archivos Factura...");
		System.exit(0);

	}

}
