package org.facturacionelectronica.servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;

import org.facturacionelectronica.entidades.CabeceraFactura;
import org.w3c.dom.Document;

import com.helger.commons.state.ESuccess;
import com.helger.datetime.util.PDTXMLConverter;
import com.helger.ublpe.EUBLPEDocumentType;
import com.helger.ublpe.UBLPEDocumentTypes;
import com.helger.ublpe.UBLPEWriter;
import com.helger.ublpe.UBLPEWriterBuilder;
import com.sun.xml.bind.v2.model.core.Element;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ExternalReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PricingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SignatureType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomerAssignedAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoicedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;
import un.unece.uncefact.codelist.specification._66411._2001.UnitCodeContentType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalInformationType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalMonetaryTotalType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.*;

public class GeneradorFactura {

	public ESuccess generarFactura(CabeceraFactura factura) throws ParserConfigurationException {

		final CurrencyCodeContentType eCurrency = CurrencyCodeContentType.PEN;

		final InvoiceType aInvoice = new InvoiceType();
		aInvoice.setID("Clave_Primaria_Factura");
		aInvoice.setInvoiceTypeCode("01");
		aInvoice.setIssueDate(PDTXMLConverter.getXMLCalendarDateNow());

		// Zona Informacion Adicional
		UBLExtensionsType ublExtensionsTypeAdditionalInformation = new UBLExtensionsType();
		UBLExtensionType ublExtensionTypeAdditionalInformation = new UBLExtensionType();
		ExtensionContentType extensionContentType = new ExtensionContentType();

		AdditionalInformationType additionalInformationType = new AdditionalInformationType();
		AdditionalMonetaryTotalType additionalMonetaryTotalType = new AdditionalMonetaryTotalType();
		PayableAmountType payableAmountType = new PayableAmountType();

		additionalMonetaryTotalType.setID(new IDType("001"));
		payableAmountType.setCurrencyID(eCurrency);
		payableAmountType.setValue(new BigDecimal("3000"));
		additionalMonetaryTotalType.setPayableAmount(payableAmountType);

		additionalInformationType.addAdditionalMonetaryTotal(additionalMonetaryTotalType);

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		JAXB.marshal(additionalInformationType, new DOMResult(document));
		org.w3c.dom.Element element = document.getDocumentElement();

		extensionContentType.setAny(element);

		ublExtensionTypeAdditionalInformation.setExtensionContent(extensionContentType);

		ublExtensionsTypeAdditionalInformation.addUBLExtension(ublExtensionTypeAdditionalInformation);

		aInvoice.setUBLExtensions(ublExtensionsTypeAdditionalInformation);

		// Zona firma
		SignatureType signatureType = new SignatureType();
		signatureType.setID("IDSignSP");
		
		PartyType partyTypeSignature = new PartyType();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		partyIdentificationType.setID("20100454523");
		
		PartyNameType partyNameType = new PartyNameType();
		partyNameType.setName("SOPORTE TECNOLOGICO EIRL");
		partyTypeSignature.addPartyName(partyNameType);
		partyTypeSignature.addPartyIdentification(partyIdentificationType);
		
		signatureType.setSignatoryParty(partyTypeSignature);
		
		AttachmentType attachmentType = new AttachmentType();
		
		ExternalReferenceType externalReferenceType = new ExternalReferenceType();
		externalReferenceType.setURI("#SignatureSP");
		attachmentType.setExternalReference(externalReferenceType);
		
		signatureType.setDigitalSignatureAttachment(attachmentType);
		
		aInvoice.addSignature(signatureType);
		
		

		// Zona Cliente
		aInvoice.setAccountingCustomerParty(generarZonaInformacionCliente(factura));

		// Zona Emisor
		aInvoice.setAccountingSupplierParty(generarZonaInformacionEmisor(factura));

		// Zona Impuesto Total
		TaxTotalType taxTotalTypeTotal = new TaxTotalType();

		taxTotalTypeTotal.setTaxAmount(new BigDecimal("62675.85")).setCurrencyID(eCurrency);

		TaxSubtotalType taxSubtotalTypeTotal = new TaxSubtotalType();
		taxSubtotalTypeTotal.setTaxAmount(new BigDecimal("62675.85")).setCurrencyID(eCurrency);

		TaxCategoryType taxCategoryTypeTotal = new TaxCategoryType();

		TaxSchemeType taxSchemeTypeTotal = new TaxSchemeType();
		taxSchemeTypeTotal.setID("1000");
		taxSchemeTypeTotal.setName("IGV");
		taxSchemeTypeTotal.setTaxTypeCode("VAT");

		taxCategoryTypeTotal.setTaxScheme(taxSchemeTypeTotal);

		taxSubtotalTypeTotal.setTaxCategory(taxCategoryTypeTotal);

		taxTotalTypeTotal.addTaxSubtotal(taxSubtotalTypeTotal);

		aInvoice.addTaxTotal(taxTotalTypeTotal);

		// Detalle Factura

		InvoicedQuantityType invoicedQuantityType = new InvoicedQuantityType();
		invoicedQuantityType.setValue(new BigDecimal("3000.00"));
		invoicedQuantityType.setUnitCode(UnitCodeContentType.MEGAHERTZ);

		InvoiceLineType invoiceLineType = new InvoiceLineType();
		ItemType itemType = new ItemType();
		DescriptionType descriptionType = new DescriptionType("CAPTOPRIL 25mg X 30");
		List<DescriptionType> descriptionTypesList = new ArrayList<DescriptionType>();
		descriptionTypesList.add(descriptionType);
		itemType.setDescription(descriptionTypesList);
		invoiceLineType.setItem(itemType);
		invoiceLineType.setID("1");
		invoiceLineType.setInvoicedQuantity(new BigDecimal("3000.00"));
		invoiceLineType.setLineExtensionAmount(new BigDecimal("3000.00")).setCurrencyID(eCurrency);

		PricingReferenceType pricingReferenceType = new PricingReferenceType();
		List<PriceType> priceTypes = new ArrayList<PriceType>();

		PriceType priceTypeAmount = new PriceType();
		priceTypeAmount.setPriceAmount(new BigDecimal("98.00")).setCurrencyID(eCurrency);

		PriceType priceTypeCode = new PriceType();
		priceTypeCode.setPriceTypeCode("01");

		priceTypes.add(priceTypeAmount);
		priceTypes.add(priceTypeCode);

		pricingReferenceType.setAlternativeConditionPrice(priceTypes);

		PriceType priceType = new PriceType();
		priceType.setPriceAmount(new BigDecimal("3000.00")).setCurrencyID(eCurrency);

		priceType.setPriceTypeCode("01");

		// ----------------------------Zona Impuesto Items

		TaxTotalType taxTotalType = new TaxTotalType();
		taxTotalType.setTaxAmount(new BigDecimal("26908.47")).setCurrencyID(eCurrency);

		TaxSubtotalType taxSubtotalType = new TaxSubtotalType();

		taxSubtotalType.setTaxableAmount(new BigDecimal("172890.0")).setCurrencyID(eCurrency);
		taxSubtotalType.setTaxAmount(new BigDecimal("32849.10")).setCurrencyID(eCurrency);
		taxSubtotalType.setPercent(new BigDecimal("18.0"));

		TaxCategoryType taxCategoryType = new TaxCategoryType();
		taxCategoryType.setTaxExemptionReasonCode("10");

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("1000");
		taxSchemeType.setName("IGV");
		taxSchemeType.setTaxTypeCode("VAT");

		taxCategoryType.setTaxScheme(taxSchemeType);

		taxSubtotalType.setTaxCategory(taxCategoryType);

		taxTotalType.addTaxSubtotal(taxSubtotalType);

		invoiceLineType.setPrice(priceType);
		invoiceLineType.addTaxTotal(taxTotalType);
		aInvoice.addInvoiceLine(invoiceLineType);

		final MonetaryTotalType aMT = new MonetaryTotalType();
		aMT.setPayableAmount(new BigDecimal("3000.00")).setCurrencyID(eCurrency);
		aInvoice.setLegalMonetaryTotal(aMT);
		aInvoice.setCustomizationID("1.0");
		
//		DocumentCurrencyCodeType documentCurrencyCodeType = new DocumentCurrencyCodeType();
//		documentCurrencyCodeType.setValue(eCurrency.value());
//		aInvoice.setDocumentCurrencyCode(documentCurrencyCodeType);
		

		// ------> Enviar la escritura del archivo a una clase
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(
							"D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"),
					"utf-8"));
		} catch (IOException ex) {
			// Report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				/* ignore */}
		}

		// final ESuccess eSuccess = UBL20Writer.invoice().write(aInvoice, new
		// File("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"));

		final ESuccess eSuccess = UBLPEWriter.invoice().write(aInvoice,
				new File("D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml"));

		return eSuccess;

	}

	public CustomerPartyType generarZonaInformacionCliente(CabeceraFactura factura) {
		CustomerPartyType customerPartyType = new CustomerPartyType();
		PartyType partyTypeCustomer = new PartyType();

		customerPartyType.setCustomerAssignedAccountID(factura.getNumeroDocumentoCliente());
		List<AdditionalAccountIDType> customerAdditionalAccountIDType = new ArrayList<AdditionalAccountIDType>();
		customerAdditionalAccountIDType
				.add(new AdditionalAccountIDType(String.valueOf(factura.getTipoDocumentoCliente())));

		customerPartyType.setAdditionalAccountID(customerAdditionalAccountIDType);

		List<PartyLegalEntityType> customerPartyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();
		PartyLegalEntityType customerPartyLegalEntityType = new PartyLegalEntityType();
		customerPartyLegalEntityType.setRegistrationName(factura.getRazonSocialCliente());
		customerPartyLegalEntityTypes.add(customerPartyLegalEntityType);

		partyTypeCustomer.setPartyLegalEntity(customerPartyLegalEntityTypes);
		customerPartyType.setParty(partyTypeCustomer);

		return customerPartyType;
	}

	public SupplierPartyType generarZonaInformacionEmisor(CabeceraFactura factura) {
		final SupplierPartyType aSupplier = new SupplierPartyType();

		aSupplier.setCustomerAssignedAccountID(
				new CustomerAssignedAccountIDType(factura.getNumeroDocumento().toString()));

		List<AdditionalAccountIDType> additionalAccountIDType = new ArrayList<AdditionalAccountIDType>();
		additionalAccountIDType.add(new AdditionalAccountIDType(String.valueOf(factura.getTipoDocumento())));
		aSupplier.setAdditionalAccountID(additionalAccountIDType);

		PartyType partyType = new PartyType();
		PartyNameType partyNameType = new PartyNameType();
		partyNameType.setName(factura.getRazonSocial());

		partyType.addPartyName(partyNameType);

		aSupplier.setParty(partyType);

		AddressType addressType = new AddressType();

		addressType.setID(factura.getCodigoUbigeo());
		addressType.setStreetName(factura.getDireccionCompleta());
		addressType.setCitySubdivisionName("");
		addressType.setCityName(factura.getDistrito());
		addressType.setCountrySubentity(factura.getProvincia());
		addressType.setDistrict(factura.getDistrito());
		CountryType countryType = new CountryType();
		countryType.setIdentificationCode("PE");
		addressType.setCountry(countryType);

		partyType.setPostalAddress(addressType);

		List<PartyLegalEntityType> partyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();
		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(factura.getNombreComercial());
		partyLegalEntityTypes.add(partyLegalEntityType);

		partyType.setPartyLegalEntity(partyLegalEntityTypes);

		return aSupplier;
	}

}