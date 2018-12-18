package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.GeneradorNotaCreditoDao;
import org.facturacionelectronica.dao.entidades.DetalleNotaCreditoDao;
import org.facturacionelectronica.dao.entidades.NotaCreditoDao;
import org.facturacionelectronica.entidades.CabeceraNotaCredito;
import org.facturacionelectronica.entidades.DetalleNotaCredito;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.facturacionelectronica.util.Utilitario;

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

			List<DetalleNotaCreditoDao> listDetalleNotaCreditoDao = exportadorBaseDatos
					.obtenerDetalleNotaCredito(notaCreditoDao.getIdNotaCredito());

			List<DetalleNotaCredito> listaDetalleNotaCredito = new ArrayList<DetalleNotaCredito>();

			for (DetalleNotaCreditoDao detalleNotaCreditoDao : listDetalleNotaCreditoDao) {
				listaDetalleNotaCredito.add(new DetalleNotaCredito(detalleNotaCreditoDao));
			}
			
			CabeceraNotaCredito cabeceraNotaCredito = new CabeceraNotaCredito(notaCreditoDao);


			GeneradorNotaCredito21 generadorNotaCredito21 = new GeneradorNotaCredito21();

			ESuccess eSuccess = generadorNotaCredito21.generarNotaCredito(cabeceraNotaCredito, listaDetalleNotaCredito);

			String nombreArchivo = Utilitario.obtenerNombreArchivoNotaCredito(cabeceraNotaCredito);
			
			

			if (eSuccess.isSuccess()) {
				
				try {
					GestorFirma gestorFirma = new GestorFirma();
					
					InputStream inputStream = new FileInputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz()
							+ Constantes.rutaSolicitudNotaCredito + nombreArchivo + Constantes.extensionXml);
					
					Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream, notaCreditoDao);
					
					FileOutputStream fout = new FileOutputStream(ParametrosGlobales.obtenerParametros().getRutaRaiz()
							+ Constantes.rutaSolicitudNotaCredito + nombreArchivo + Constantes.extensionXml);
					
					ByteArrayOutputStream outDocument = (ByteArrayOutputStream) xmlFirmado.get("signatureFile");
					
					outDocument.writeTo(fout);
					fout.close();
				}catch(Exception ex) {
					GestorExcepciones.guardarExcepcionPorValidacion(ex, Object.class);
				}

				

				// Generar archivo ZIP
				 Compresor.comprimirArchivo(
				 ParametrosGlobales.obtenerParametros().getRutaRaiz() +
				 Constantes.rutaSolicitudNotaCredito + nombreArchivo + Constantes.extensionZip,
				 ParametrosGlobales.obtenerParametros().getRutaRaiz() +
				 Constantes.rutaSolicitudNotaCredito + nombreArchivo + Constantes.extensionXml,
				 nombreArchivo + Constantes.extensionXml);

				// Genero PDF
				try {
					GestorPdf gestorPdf = new GestorPdf();
					gestorPdf.generarPDF(nombreArchivo, notaCreditoDao);
				} catch (Exception e) {

					GestorExcepciones.guardarExcepcionPorValidacion(e, Object.class);

				}

				GeneradorNotaCreditoDao generadorNotaCreditoDao = new GeneradorNotaCreditoDao();
				
				notaCreditoDao.setEstado(6);
				generadorNotaCreditoDao.actualizarEstadoNotaCredito(notaCreditoDao);

			}

		}

		System.out.println("Eliminando Proceso Importacion Archivos Factura...");
		System.exit(0);

	}

}
