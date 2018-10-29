package org.facturacionelectronica.util;

public class ValidadorGenerico {

	public static boolean esCadenaValida(String cadena) {
		
		if(cadena == null)
			return false;
		
		boolean respuesta = !cadena.trim().isEmpty();
		
		return respuesta;
	}
}
