package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.GeneradorFactura;
import org.junit.Test;

import com.helger.commons.state.ESuccess;
import com.helger.ublpe.EUBLPEDocumentType;
import com.helger.ublpe.UBLPEDocumentTypes;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class GeneradorFacturaElectronica {

	GeneradorFactura generadorFactura = new GeneradorFactura();

	@Test
	public void cuandoGeneroFacturaElectronica() throws ParserConfigurationException {
		BasicConfigurator.configure();

		final ESuccess eSuccess = generadorFactura.generarFactura(FacturaMock.retornarFactura());

		assertTrue(eSuccess.isSuccess());

	}

	@Test
	public void cuandoGeneroFacturaElectronicaPeru() {
		BasicConfigurator.configure();
		
		assertTrue(true);

	}

	
}
