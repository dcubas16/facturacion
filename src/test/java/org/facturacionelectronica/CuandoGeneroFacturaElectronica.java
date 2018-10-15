package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GeneradorFactura;
import org.junit.Test;
import com.helger.commons.state.ESuccess;

public class CuandoGeneroFacturaElectronica {

	GeneradorFactura generadorFactura = new GeneradorFactura();

	@Test
	public void cuandoGeneroFacturaElectronica() throws Exception {
		BasicConfigurator.configure();

		final ESuccess eSuccess = generadorFactura.generarFactura(FacturaMock.obtenerFactura());

		assertTrue(eSuccess.isSuccess());

	}

}
