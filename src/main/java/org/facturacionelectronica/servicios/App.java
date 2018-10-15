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
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.util.Constantes;

import com.helger.commons.state.ESuccess;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		System.out.println("------------------>Iniciando Importacion Archivos");
		System.out.println("------------------>Leyendo Facturas");
		System.out.println("------------------>Importando Facturas a Base de Datos");

		ExportadorBaseDatos exportadorBaseDatos = new ExportadorBaseDatos();

		try {
			boolean respuesta = exportadorBaseDatos
					.exportarFacturas(Constantes.rutaCompleta + Constantes.rutaImportar + Constantes.archivoImportar);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("------------------>Obteniendo Facturas Importadas de Base de Datos");
		GestorWebService gestorWebService = new GestorWebService();

		
		// AQUI SE DEBE GENERAR EL ZIP

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

			ESuccess eSuccess = generadorFactura.generarFactura(factura);
			
			//////////////////////////////////////////////////////////////////////////////Esto llevar a una clase utils
			String nombreArchivo = gestorWebService.obtenerNombreArchivoFactura(
					factura.getCabeceraFactura().getNumeroDocumento(),
					factura.getCabeceraFactura().getTipoDocumentoFactura(), factura.getCabeceraFactura().getSerie(),
					factura.getCabeceraFactura().getNumeroCorrelativo());

			if (eSuccess.isSuccess()) {
				
				GestorFirma gestorFirma = new GestorFirma();
				InputStream inputStream = new FileInputStream(Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo  + Constantes.extensionXml);
				Map<String, Object> xmlFirmado = gestorFirma.firmarDocumento(inputStream);
				FileOutputStream fout = new FileOutputStream(Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml);
		        ByteArrayOutputStream outDocument = (ByteArrayOutputStream)xmlFirmado.get("signatureFile");
		        String digestValue = (String)xmlFirmado.get("digestValue");
			     
			    outDocument.writeTo(fout);
			    fout.close();
			    
				// Generar archivo ZIP
				Compresor.comprimirArchivo(
						Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionZip,
						Constantes.rutaCompleta + Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml,
						nombreArchivo + Constantes.extensionXml);
				
				gestorWebService.enviarFacturaSunat(factura.getCabeceraFactura().getIdFactura(),
						Constantes.rutaCompleta + Constantes.rutaSolicitud, nombreArchivo + Constantes.extensionZip,
						nombreArchivo + Constantes.extensionXml,
						factura.getCabeceraFactura().getNumeroDocumento() + Constantes.usuarioPruebas,
						Constantes.contraseniaPruebas);
			}

		}
	}

}
