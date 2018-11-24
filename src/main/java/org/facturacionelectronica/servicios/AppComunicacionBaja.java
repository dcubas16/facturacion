package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.facturacionelectronica.dao.GeneradorComuncacionBajaDao;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleComunicaBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.facturacionelectronica.util.Utilitario;

import com.helger.commons.state.ESuccess;

public class AppComunicacionBaja {
	
	static Logger logger = Logger.getLogger(AppComunicacionBaja.class);

	public static void main(String[] args) throws Exception {

		String rutaCompleta = ParametrosGlobales.obtenerParametros().getRutaRaiz();

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		boolean respuesta = exportadorBaseDatos
				.exportarComunicacionBaja(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImportarComunicaBaja);

		System.out.println("------------------>Obteniendo Facturas Importadas de Base de Datos");
		GestorWebService gestorWebService = new GestorWebService();


		List<ComunicacionBajaDao> listaComunicacionBajasDao = exportadorBaseDatos.obtenerComunicacionBajaImportados();
		GeneradorComunicacionBaja generadorComunicacionBaja = new GeneradorComunicacionBaja();
		String nombreArchivo;

		for (ComunicacionBajaDao comunicacionBajaDao : listaComunicacionBajasDao) {
			
			List<DetalleComunicaBajaDao> listDetalleComunicaBajaDao = exportadorBaseDatos
					 .obtenerDetalleComunicaBajaDao(comunicacionBajaDao.getIdComunicaionBaja());
			
			List<DetalleComunicacionBaja> listaDetalleComunicacionBajas = new ArrayList<DetalleComunicacionBaja>();
			
			for (DetalleComunicaBajaDao detalleComunicaBajaDao : listDetalleComunicaBajaDao) {
				listaDetalleComunicacionBajas.add(new DetalleComunicacionBaja(detalleComunicaBajaDao));
			}
			
			ComunicacionBaja comunicacionBaja = new ComunicacionBaja(comunicacionBajaDao);

			ESuccess eSuccess = generadorComunicacionBaja.generarComunicacionBaja(comunicacionBaja, listaDetalleComunicacionBajas);
			
			nombreArchivo = Utilitario.obtenerNombreArchivoComunicacionBaja(comunicacionBaja);

			if (eSuccess.isSuccess()) {

				GestorFirma gestorFirma = new GestorFirma();
				InputStream inputStream = new FileInputStream(
						ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudComunicaBaja + nombreArchivo + Constantes.extensionXml);
				Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream, comunicacionBajaDao);
				
				FileOutputStream fout = new FileOutputStream(
						ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudComunicaBaja + nombreArchivo + Constantes.extensionXml);
				ByteArrayOutputStream outDocument = (ByteArrayOutputStream) xmlFirmado.get("signatureFile");
				String digestValue = (String) xmlFirmado.get("digestValue");

				outDocument.writeTo(fout);
				fout.close();
				
				GeneradorComuncacionBajaDao generadorComuncacionBajaDao = new GeneradorComuncacionBajaDao();
				comunicacionBajaDao.setEstado(6);

				generadorComuncacionBajaDao.actualizarComunicacionBaja(comunicacionBajaDao);

			}
		}
		
		List<ComunicacionBajaDao> listaComunicacionBajasDaoPendiente = exportadorBaseDatos.obtenerComunicacionBajaPendiente();
		
		for(ComunicacionBajaDao comunicacionBajaDao : listaComunicacionBajasDaoPendiente) {
			
			
			nombreArchivo = Utilitario.obtenerNombreArchivoComunicacionBaja(new ComunicacionBaja(comunicacionBajaDao));
//			Generar ZIP
			Compresor.comprimirArchivo(
			ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudComunicaBaja + nombreArchivo + Constantes.extensionZip,
			ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudComunicaBaja + nombreArchivo + Constantes.extensionXml,
			nombreArchivo + Constantes.extensionXml);
			
			// Enviar a Web Service
			gestorWebService.enviarComunicacionBajaSunat(comunicacionBajaDao.getIdComunicaionBaja(),
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudComunicaBaja, nombreArchivo + Constantes.extensionZip,
					nombreArchivo + Constantes.extensionXml,
					comunicacionBajaDao.getNumeroRuc().toString() + Constantes.usuarioPruebas, Constantes.contraseniaPruebas);

		}

		System.out.println("Eliminando Proceso Importacion Archivos Comunicacion Baja...");
		System.exit(0);
	}
}
