package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GeneradorFactura;
import org.junit.Test;
import com.helger.commons.state.ESuccess;

public class GeneradorFacturaElectronica {

	GeneradorFactura generadorFactura = new GeneradorFactura();

	@Test
	public void cuandoGeneroFacturaElectronica() throws ParserConfigurationException {
		BasicConfigurator.configure();

		final ESuccess eSuccess = generadorFactura.generarFactura(FacturaMock.obtenerFactura());

		assertTrue(eSuccess.isSuccess());

	}

}
