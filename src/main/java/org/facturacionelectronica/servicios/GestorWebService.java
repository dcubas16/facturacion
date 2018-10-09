package org.facturacionelectronica.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;

import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;

public class GestorWebService {

	public boolean enviarFacturaSunat() {

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(
					"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\20600091370-01-F001-4375.ZIP"));

			DataHandler dataHandler = new DataHandler(dataSource);

			BindingProvider bindingProvider = (BindingProvider) billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "20600091370MODDATOS");
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "MODDATOS");
			
			billService.sendBill("20600091370-01-F001-4375.ZIP", dataHandler, null);
			
			

//			byte[] respuesta = billService.sendBill("20100454523-01-F001-4355.ZIP", dataHandler, null);
			
			String a = "";
//			billService.;

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
	
}
