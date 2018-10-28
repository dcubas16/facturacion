package org.facturacionelectronica.util;

import org.facturacionelectronica.dao.GestorParametrosDao;
import org.facturacionelectronica.dao.entidades.MovParametrosDao;

public class ParametrosGlobales {

	private static MovParametrosDao  movParametrosDao = configurar();
	
	public static MovParametrosDao obtenerParametros() {
		return movParametrosDao;
	}

	private static  MovParametrosDao configurar(){
		GestorParametrosDao gestorParametrosDao = new GestorParametrosDao();
		
		return gestorParametrosDao.obtenerParametros().get(0);
	}
}
