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

//		final ESuccess eSuccess = generadorFactura.generarFactura(FacturaMock.retornarFactura());
		
//		EUBLPEDocumentType ublpeDocumentTypes = EUBLPEDocumentType.INVOICE;
		
//		EUBLPEDocumentType ublpeDocumentType = EUBLPEDocumentType.INVOICE;
		
//		UBLPEDocumentTypes.getDocumentTypeOfLocalName ("Invoice");
		
//		InvoiceType invoiceType = (InvoiceType) UBLPEDocumentTypes.getImplementationClassOfLocalName("Invoice").cast(InvoiceType.class);
		
//		(InvoiceType.class)
		
//		UBLPEDocumentTypes.getSchemaOfLocalName ("Invoice");
		
//		InvoiceType invoiceType = EUBLPEDocumentType.INVOICE.getClass();
//		
//		ublpeDocumentTypes.getImplementationClass();

//		UBLPEDocumentTypes ublpeDocumentTypes = UBLPEDocumentTypes.getDocumentTypeOfLocalName("Invoice").getClass();
		
//		UBLPEDocumentTypes ublpeDocumentTypes;
//		Class<InvoiceType> invoiceType = (Class<InvoiceType>) UBLPEDocumentTypes.getImplementationClassOfLocalName(EUBLPEDocumentType.INVOICE.getLocalName());
//		= EUBLPEDocumentType.INVOICE.getImplementationClass();
		
		
		
		assertTrue(true);

	}

	
}
