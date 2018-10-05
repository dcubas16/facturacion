package org.facturacionelectronica;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;


import org.junit.Test;
import org.xml.sax.SAXException;

import com.helger.commons.state.ESuccess;
import com.helger.xml.serialize.read.DOMReader;

import com.helger.datetime.util.PDTXMLConverter;
import com.helger.ublpe.EUBLPEDocumentType;
import com.helger.ublpe.UBLPEWriter;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;
import org.apache.log4j.BasicConfigurator;
import org.facturacionelectronica.servicios.UBL20Writer;

public final class CreateInvoiceFromScratchFuncTest {

	@Test
	public void testCreateInvoiceFromScratch() {
		BasicConfigurator.configure();
		final CurrencyCodeContentType eCurrency = CurrencyCodeContentType.PEN;

		final InvoiceType aInvoice = new InvoiceType();
		aInvoice.setID("Dummy Invoice number");
		aInvoice.setIssueDate(PDTXMLConverter.getXMLCalendarDateNow());

		final SupplierPartyType aSupplier = new SupplierPartyType();
		aInvoice.setAccountingSupplierParty(aSupplier);

		final CustomerPartyType aCustomer = new CustomerPartyType();
		aInvoice.setAccountingCustomerParty(aCustomer);

		final MonetaryTotalType aMT = new MonetaryTotalType();
		aMT.setPayableAmount(BigDecimal.TEN).setCurrencyID(eCurrency);
		aInvoice.setLegalMonetaryTotal(aMT);

		final InvoiceLineType aLine = new InvoiceLineType();
		aLine.setID("1");

		final ItemType aItem = new ItemType();
		aLine.setItem(aItem);

		aLine.setLineExtensionAmount(BigDecimal.TEN).setCurrencyID(eCurrency);

		aInvoice.addInvoiceLine(aLine);

		final ESuccess eSuccess = UBL20Writer.invoice().write(aInvoice,
				new File("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"));
		assertTrue(eSuccess.isSuccess());
	}

	@Test
	public void testCreateInvoiceFromScratchWithCustomNamespace() throws SAXException {
		BasicConfigurator.configure();
		final CurrencyCodeContentType eCurrency = CurrencyCodeContentType.EUR;

		final InvoiceType aInvoice = new InvoiceType();

		// UBLExtensionsType ublExtensionType = new UBLExtensionsType();
		aInvoice.setID("Dummy Invoice number");

		aInvoice.setIssueDate(PDTXMLConverter.getXMLCalendarDateNow());

		final SupplierPartyType aSupplier = new SupplierPartyType();
		aInvoice.setAccountingSupplierParty(aSupplier);

		final CustomerPartyType aCustomer = new CustomerPartyType();
		aInvoice.setAccountingCustomerParty(aCustomer);

		final MonetaryTotalType aMT = new MonetaryTotalType();
		aMT.setPayableAmount(BigDecimal.TEN).setCurrencyID(eCurrency);
		aInvoice.setLegalMonetaryTotal(aMT);

		final InvoiceLineType aLine = new InvoiceLineType();
		aLine.setID("1");

		final ItemType aItem = new ItemType();
		aLine.setItem(aItem);

		aLine.setLineExtensionAmount(BigDecimal.TEN).setCurrencyID(eCurrency);

		aInvoice.addInvoiceLine(aLine);

		// Add extension
		final UBLExtensionsType aExtensions = new UBLExtensionsType();
		final UBLExtensionType aExtension = new UBLExtensionType();
		final ExtensionContentType aExtensionContent = new ExtensionContentType();
		aExtensionContent.setAny(DOMReader.readXMLDOM(
				"<root xmlns='urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1'>test</root>")
				.getDocumentElement());

		aExtension.setExtensionContent(aExtensionContent);
		aExtensions.addUBLExtension(aExtension);
		aInvoice.setUBLExtensions(aExtensions);

		final ESuccess eSuccess = UBL20Writer.invoice().write(aInvoice,
				new File("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"));

		assertTrue(eSuccess.isSuccess());
	}

	
	@Test
	  public void testInvoicePeru () throws SAXException
	  {
		  BasicConfigurator.configure();
	    final CurrencyCodeContentType eCurrency = CurrencyCodeContentType.EUR;

	    final InvoiceType aInvoice = new InvoiceType ();
	    
//	    UBLExtensionsType ublExtensionType = new UBLExtensionsType();
	    aInvoice.setID ("Dummy Invoice number");
	    
	    aInvoice.setIssueDate (PDTXMLConverter.getXMLCalendarDateNow ());

	    final SupplierPartyType aSupplier = new SupplierPartyType ();
	    aInvoice.setAccountingSupplierParty (aSupplier);

	    final CustomerPartyType aCustomer = new CustomerPartyType ();
	    aInvoice.setAccountingCustomerParty (aCustomer);

	    final MonetaryTotalType aMT = new MonetaryTotalType ();
	    aMT.setPayableAmount (BigDecimal.TEN).setCurrencyID (eCurrency);
	    aInvoice.setLegalMonetaryTotal (aMT);

	    final InvoiceLineType aLine = new InvoiceLineType ();
	    aLine.setID ("1");

	    final ItemType aItem = new ItemType ();
	    aLine.setItem (aItem);

	    aLine.setLineExtensionAmount (BigDecimal.TEN).setCurrencyID (eCurrency);

	    aInvoice.addInvoiceLine (aLine);

	    // Add extension
	    final UBLExtensionsType aExtensions = new UBLExtensionsType ();
	    final UBLExtensionType aExtension = new UBLExtensionType ();
	    final ExtensionContentType aExtensionContent = new ExtensionContentType ();
	    aExtensionContent.setAny (DOMReader.readXMLDOM ("<root xmlns='urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1'>test</root>")
	                                       .getDocumentElement ());

	    aExtension.setExtensionContent (aExtensionContent);
	    aExtensions.addUBLExtension (aExtension);
	    aInvoice.setUBLExtensions (aExtensions);

//	    final ESuccess eSuccess = UBL20Writer.invoice ().write (aInvoice, new File ("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"));
	    
	    final ESuccess eSuccess = UBLPEWriter.invoice().write (aInvoice, new File ("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"));
	   
	        
	    
//	    UBLPEWriterBuilder<InvoiceType> ublpeWriterBuilder = new UBLPEWriterBuilder<>(InvoiceType.class);
//	    
//	    ublpeWriterBuilder.getJAXBDocumentType().getAllXSDPaths();
	    
	    EUBLPEDocumentType invoiceType = EUBLPEDocumentType.values()[3];
	    
	    
	    final EUBLPEDocumentType e = EUBLPEDocumentType.values()[3];
	    
//	    
//	    UBLExtensionsType ublExtensionsType = new UBLExtensionsType(); 


//	    UBLPEWriter.invoice().getJAXBDocumentType()
	    

	    
	    assertTrue (eSuccess.isSuccess ());
	  }
	
	
	
}