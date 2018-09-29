package org.facturacionelectronica.servicios;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParserConfigurationException
    {
    	BasicConfigurator.configure();
        System.out.println( "Hello World!" );
        GeneradorFactura generadorFactura = new GeneradorFactura();
        generadorFactura.generarFactura(FacturaMock.retornarFactura());
        
    }
}
