package org.facturacionelectronica.servicios;

import java.io.File;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;
import org.facturacionelectronica.dao.GeneradorFacturaDao;
import org.facturacionelectronica.entidades.RespuestaCdr;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;

public class GestorWebService {

	public boolean enviarFacturaSunat(String idDocumento, String rutaComplta, String archivoZip, String archivoXml,
			String usuario, String contrasenia) {

		GeneradorFacturaDao generadorFacturaDao = new GeneradorFacturaDao();

		try {

			BillService_Service billService_Service = new BillService_Service();

			BillService billService = billService_Service.getBillServicePort();

			DataSource dataSource = new FileDataSource(new File(rutaComplta + archivoZip));

			DataHandler dataHandler = new DataHandler(dataSource);

			BindingProvider bindingProvider = (BindingProvider) billService;
			bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
			bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, contrasenia);

			byte[] respuesta = billService.sendBill(archivoZip, dataHandler, "");

			Compresor.crearArchivoZip(ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaRespuesta,
					respuesta);

			RespuestaCdr respuestaCdr = GestorArchivosXML.obtenerRespuestaCdr(archivoXml);

			generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
					Integer.parseInt(respuestaCdr.getCodigo()),
					respuestaCdr.getTicket() + "-" + respuestaCdr.getDescripcion());
			
			return true;

		} catch (Exception e) {
			
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			String mensaje = "";
			
			if(e.getMessage().length() > 998)
				mensaje = e.getMessage().substring(0, 998);
			else
				mensaje = e.getMessage();
			
			generadorFacturaDao.actualizarEstadoAceptado(generadorFacturaDao.obtenerFactura(idDocumento),
					-1, mensaje);
			
			return false;
			
		}

	}
}