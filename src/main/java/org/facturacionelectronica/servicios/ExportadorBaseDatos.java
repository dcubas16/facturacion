package org.facturacionelectronica.servicios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.facturacionelectronica.dao.ConfiguracionBaseDatos;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExportadorBaseDatos {

	private List<String> lineasArchivo = new ArrayList<String>();
	ManejadorArchivos manejadorArchivos = new ManejadorArchivos();
	
	public ExportadorBaseDatos() {}
	
	
	public boolean exportarFacturas(String rutaArchivo) throws ParseException {
		
		lineasArchivo = manejadorArchivos.leerArchivo(rutaArchivo);
		
		List<CabeceraFactura> listaCabeceraFacturas = manejadorArchivos.generarCabeceraFactura(lineasArchivo);
				
		for (CabeceraFactura cabeceraFactura : listaCabeceraFacturas) {
			List<DetalleFactura> listaDetalleFacturas = manejadorArchivos.generarDetalleFactura(lineasArchivo, cabeceraFactura.getIdFactura());
//			guardarCabecerasFactura(listaCabeceraFacturas);			
//			guardarDetalleFactura(listaCabeceraFacturas);
			
			
			////////////Guardar Factura
			
			Session session =  ConfiguracionBaseDatos.getSessionFactory().openSession();

			//Creating Transaction Object  
			Transaction transObj = session.beginTransaction();
			
			FacturaDao facturaDao = new FacturaDao(cabeceraFactura);
			
			session.save(facturaDao);
			
			for (DetalleFactura detalleFactura : listaDetalleFacturas) {
				
				DetalleFacturaDao detalleFacturaDao = new DetalleFacturaDao(detalleFactura, facturaDao);
				
				facturaDao.getDetalleFacturaDao().add(detalleFacturaDao);
				
				session.save(detalleFacturaDao);
			}
			
			transObj.commit();
			
			session.close();
			
						
			
		}
		
		
		return true;
	}
	
	
}
