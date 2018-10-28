package org.facturacionelectronica.util;

import org.apache.log4j.Logger;

public class GestorExcepciones {

	static Logger logger;

	public static void guardarExcepcion(Exception e, Object o) {

		logger = Logger.getLogger(o.getClass());

		logger.error(e);
		System.out.println(Constantes.SuitFael + e.getMessage());
				
		//Aqui se debe hacer proceso para guardar error en base de datos
		
		System.out.println(Constantes.SuitFael + "Cerrando Proceso" );
		System.exit(0);

	}

}
