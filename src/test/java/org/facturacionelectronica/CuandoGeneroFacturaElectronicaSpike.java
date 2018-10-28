package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GeneradorFactura;
import org.facturacionelectronica.spike.GeneradorFacturaPrueba;
import org.junit.Test;
import com.helger.commons.state.ESuccess;

public class CuandoGeneroFacturaElectronicaSpike {

	GeneradorFacturaPrueba generadorFacturaPrueba = new GeneradorFacturaPrueba();

	@Test
	public void cuandoGeneroFacturaElectronicaPrueba() throws Exception {
		BasicConfigurator.configure();

		generadorFacturaPrueba.generarComprobantePagoSunat(FacturaMock.obtenerFactura(), "D:\\Suit_Fael\\");

		assertTrue(false);

	}

}
