package org.facturacionelectronica.servicios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compresor {

	public static void crearArchivoZip(String rutaArchivo, byte[] archivoZip) throws Exception{
		ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(archivoZip));
		ZipEntry entry = null;
		FileOutputStream out = null;
		while ((entry = zipStream.getNextEntry()) != null) {

		    String entryName = rutaArchivo + entry.getName();

		    out = new FileOutputStream(entryName);

		    byte[] byteBuff = new byte[4096];
		    int bytesRead = 0;
		    while ((bytesRead = zipStream.read(byteBuff)) != -1)
		    {
		        out.write(byteBuff, 0, bytesRead);
		    }

		    out.close();
		    zipStream.closeEntry();
		}
		zipStream.close();
		
	}
	
	public static boolean comprimirArchivo(String rutaArchivoZip, String rutaArchivoXml, String archivoXml) {
		try {
			byte[] buffer = new byte[10240];
			
			FileOutputStream fos = new FileOutputStream(rutaArchivoZip);
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(archivoXml);
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(rutaArchivoXml);
   	   
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}

    		in.close();
    		zos.closeEntry();
           
    		//remember close it
    		zos.close();
          
    		System.out.println("Done");
			
			return true;
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static void comprimirArchivo( OutputStream salida, InputStream entrada, String nombre  ) throws Exception {
    	byte[] buffer = new byte[1024];
 
		ZipOutputStream zos = new ZipOutputStream(salida);
		ZipEntry ze= new ZipEntry(nombre);
		zos.putNextEntry(ze);

		int len;
		while ((len = entrada.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		}

		entrada.close();
		zos.closeEntry();

		zos.close();
	}
}
