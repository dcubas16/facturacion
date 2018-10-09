package org.facturacionelectronica.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;

import org.facturacionelectronica.util.Constantes;

import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;

public class GestorWebService {

	public boolean enviarFacturaSunat(String rutaComplta, String archivoZip, String usuario, String contrasenia) {

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();
			
			DataSource dataSource = new FileDataSource(new File(rutaComplta+archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			BindingProvider bindingProvider = (BindingProvider) billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, contrasenia);
			
			billService.sendBill(archivoZip, dataHandler, "");
			
			

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

		}

		return false;
	}
	
	public boolean generarZip(String archivo) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Test String");

		File f = new File("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\"+archivo+".zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry e = new ZipEntry("mytext.txt");
		out.putNextEntry(e);

		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();

		out.close();
		
		return false;
	}
	
	public String obtenerNombreArchivoFactura(BigInteger numDoc, String tipoDocComprobante, String serie,
			String correlativo) {

		String nombreArchivo = numDoc + Constantes.separadorNombreArchivo + tipoDocComprobante
				+ Constantes.separadorNombreArchivo + serie + Constantes.separadorNombreArchivo
				+ Constantes.separadorNombreArchivo + correlativo;

		return nombreArchivo;

	}
	
}
