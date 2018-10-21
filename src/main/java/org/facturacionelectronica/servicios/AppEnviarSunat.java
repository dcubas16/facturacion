package org.facturacionelectronica.servicios;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.dao.ConfiguracionBaseDatos;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.util.Constantes;

public class AppEnviarSunat {

//	public static void main(String[] args) {
	public void execAppEnviarSunat() {
		// TODO Auto-generated method stub
		
		BasicConfigurator.configure();

		GestorWebService gestorWebService = new GestorWebService();
		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		List<FacturaDao> listaFacturaDao = exportadorBaseDatos.obtenerFacturasPendientesDeEnvio();

		for (FacturaDao facturaDao : listaFacturaDao) {

			String nombreArchivo = facturaDao.getNumeroDocumento() + Constantes.separadorNombreArchivo
					+ facturaDao.getTipoDocumentoFactura() + Constantes.separadorNombreArchivo + facturaDao.getSerie()
					+ Constantes.separadorNombreArchivo + facturaDao.getNumeroCorrelativo();

			// Enviar a Web Service
			gestorWebService.enviarFacturaSunat(facturaDao.getIdFactura(),
					Constantes.rutaCompleta + Constantes.rutaSolicitud, nombreArchivo + Constantes.extensionZip,
					nombreArchivo + Constantes.extensionXml,
					facturaDao.getNumeroDocumento() + Constantes.usuarioPruebas, Constantes.contraseniaPruebas);
		}
		
//		ConfiguracionBaseDatos.shutdown();
//		System.exit(0);
	}

}
