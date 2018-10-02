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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.w3c.dom.Document;
import com.helger.commons.state.ESuccess;
import com.helger.datetime.util.PDTXMLConverter;
import com.helger.ublpe.UBLPEWriter;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoicedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;
import un.unece.uncefact.codelist.specification._66411._2001.UnitCodeContentType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalInformationType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalMonetaryTotalType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalPropertyType;
import org.w3._2000._09.xmldsig_.*;

public class GeneradorFactura {

	public ESuccess generarFactura(Factura factura) throws ParserConfigurationException {

		final CurrencyCodeContentType eCurrency = CurrencyCodeContentType.PEN;

		final InvoiceType aInvoice = new InvoiceType();
		aInvoice.setID(factura.getCabeceraFactura().getIdFactura());
		aInvoice.setInvoiceTypeCode(factura.getCabeceraFactura().getTipoDocumentoFactura());
		aInvoice.setCustomizationID(factura.getCabeceraFactura().getIdCustomization());
		aInvoice.setIssueDate(PDTXMLConverter.getXMLCalendarDateNow());

		// Zona Informacion Adicional
		UBLExtensionsType ublExtensionsTypeAdditional = generarInformacionAdicional(factura.getCabeceraFactura());
		aInvoice.setUBLExtensions(ublExtensionsTypeAdditional);

		// Zona firma detalle
		ublExtensionsTypeAdditional.addUBLExtension(generarDetalleFirma(factura.getCabeceraFactura()));

		// Zona firma cabecera
		aInvoice.addSignature(generarCabeceraFirma(factura.getCabeceraFactura()));

		// Zona Cliente
		aInvoice.setAccountingCustomerParty(generarZonaInformacionCliente(factura.getCabeceraFactura()));

		// Zona Emisor
		aInvoice.setAccountingSupplierParty(generarZonaInformacionEmisor(factura.getCabeceraFactura()));

		// Zona Impuesto Total
		aInvoice.addTaxTotal(generarImpuestosTotales(factura.getCabeceraFactura(), eCurrency));

		// Detalle Factura
		aInvoice.addInvoiceLine(generarDetalleFactura(factura.getDetalleFactura(), eCurrency));

		// Total Monetario
		aInvoice.setLegalMonetaryTotal(generarTotalMonetario(factura.getCabeceraFactura(), eCurrency));

		// Escribir archivo
		return imprimirFacturaArchivo(aInvoice, "D:\\proyectos\\Facturacion_Electronica\\facturacionelectronica\\src\\site\\example.xml", "utf-8");

	}

	private SignatureType generarCabeceraFirma(CabeceraFactura cabeceraFactura) {
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

		return signatureType;
	}

	private UBLExtensionType generarDetalleFirma(CabeceraFactura cabeceraFactura) throws ParserConfigurationException {
		org.w3._2000._09.xmldsig_.SignatureType signatureTypeDetail = new org.w3._2000._09.xmldsig_.SignatureType();
		SignedInfoType signedInfoType = new SignedInfoType();

		CanonicalizationMethodType canonicalizationMethodType = new CanonicalizationMethodType();
		canonicalizationMethodType.setAlgorithm("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");

		SignatureMethodType signatureMethodType = new SignatureMethodType();
		signatureMethodType.setAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha1");

		ReferenceType referenceType = new ReferenceType();
		referenceType.setURI("");
		TransformsType transformsType = new TransformsType();
		TransformType transformType = new TransformType();

		transformType.setAlgorithm("http://www.w3.org/2000/09/xmldsig#enveloped-signature");

		transformsType.getTransform().add(transformType);

		referenceType.setTransforms(transformsType);

		DigestMethodType digestMethodType = new DigestMethodType();
		digestMethodType.setAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");

		referenceType.setDigestValue(null);// -------------------------------------Ingresar esto

		referenceType.setTransforms(transformsType);
		referenceType.setDigestMethod(digestMethodType);

		signatureTypeDetail.setId("SignatureSP");
		signedInfoType.setCanonicalizationMethod(canonicalizationMethodType);
		signedInfoType.setSignatureMethod(signatureMethodType);
		signedInfoType.getReference().add(referenceType);

		signatureTypeDetail.setSignedInfo(signedInfoType);

		SignatureValueType signatureValueType = new SignatureValueType();
		signatureValueType.setValue(null);

		signatureTypeDetail.setSignatureValue(signatureValueType);

		KeyInfoType keyInfoType = new KeyInfoType();

		UBLExtensionType ublExtensionTypeSignDetail = new UBLExtensionType();
		ExtensionContentType extensionContentTypeSignDetail = new ExtensionContentType();

		Document documentSignDetail = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		JAXB.marshal(signatureTypeDetail, new DOMResult(documentSignDetail));
		org.w3c.dom.Element elementSignDetail = documentSignDetail.getDocumentElement();

		extensionContentTypeSignDetail.setAny(elementSignDetail);

		ublExtensionTypeSignDetail.setExtensionContent(extensionContentTypeSignDetail);

		return ublExtensionTypeSignDetail;
	}

	private MonetaryTotalType generarTotalMonetario(CabeceraFactura cabeceraFactura,
			CurrencyCodeContentType eCurrency) {

		final MonetaryTotalType aMT = new MonetaryTotalType();
		aMT.setPayableAmount(new BigDecimal("3000.00")).setCurrencyID(eCurrency);

		return aMT;
	}

	private InvoiceLineType generarDetalleFactura(List<DetalleFactura> detalleFactura,
			CurrencyCodeContentType eCurrency) {
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

		// Zona Impuesto Items
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

		return invoiceLineType;
	}

	private TaxTotalType generarImpuestosTotales(CabeceraFactura cabeceraFactura, CurrencyCodeContentType eCurrency) {
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

		return taxTotalTypeTotal;

	}

	public UBLExtensionsType generarInformacionAdicional(CabeceraFactura cabeceraFactura)
			throws ParserConfigurationException {

		UBLExtensionsType ublExtensionsTypeAdditional = new UBLExtensionsType();
		UBLExtensionType ublExtensionTypeAdditional = new UBLExtensionType();
		ExtensionContentType extensionContentType = new ExtensionContentType();

		AdditionalInformationType additionalInformationType = new AdditionalInformationType();
		AdditionalMonetaryTotalType additionalMonetaryTotalType = new AdditionalMonetaryTotalType();
		PayableAmountType payableAmountType = new PayableAmountType();
		AdditionalPropertyType additionalPropertyType = new AdditionalPropertyType();

		additionalMonetaryTotalType.setID(new IDType("001"));
		payableAmountType.setCurrencyID(CurrencyCodeContentType.PEN);
		payableAmountType.setValue(new BigDecimal("3000"));
		additionalMonetaryTotalType.setPayableAmount(payableAmountType);
		additionalInformationType.addAdditionalMonetaryTotal(additionalMonetaryTotalType);
		additionalPropertyType.setID(new IDType("1000"));
		additionalPropertyType.setValue(new ValueType(cabeceraFactura.getLeyenda()));
		additionalInformationType.addAdditionalProperty(additionalPropertyType);

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		JAXB.marshal(additionalInformationType, new DOMResult(document));
		org.w3c.dom.Element element = document.getDocumentElement();

		extensionContentType.setAny(element);

		ublExtensionTypeAdditional.setExtensionContent(extensionContentType);

		ublExtensionsTypeAdditional.addUBLExtension(ublExtensionTypeAdditional);

		return ublExtensionsTypeAdditional;

	}

	public ESuccess imprimirFacturaArchivo(InvoiceType invoiceType, String ruta, String formato) {

		Writer writer = null;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta), formato));
		} catch (IOException ex) {
			// Report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				/* ignore */}
		}

		final ESuccess eSuccess = UBLPEWriter.invoice().write(invoiceType, new File(ruta));

		return eSuccess;
	}

	public CustomerPartyType generarZonaInformacionCliente(CabeceraFactura cabeceraFactura) {
		CustomerPartyType customerPartyType = new CustomerPartyType();
		PartyType partyTypeCustomer = new PartyType();

		customerPartyType.setCustomerAssignedAccountID(cabeceraFactura.getNumeroDocumentoCliente());
		List<AdditionalAccountIDType> customerAdditionalAccountIDType = new ArrayList<AdditionalAccountIDType>();
		customerAdditionalAccountIDType
				.add(new AdditionalAccountIDType(String.valueOf(cabeceraFactura.getTipoDocumentoCliente())));

		customerPartyType.setAdditionalAccountID(customerAdditionalAccountIDType);

		List<PartyLegalEntityType> customerPartyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();
		PartyLegalEntityType customerPartyLegalEntityType = new PartyLegalEntityType();
		customerPartyLegalEntityType.setRegistrationName(cabeceraFactura.getRazonSocialCliente());
		customerPartyLegalEntityTypes.add(customerPartyLegalEntityType);

		partyTypeCustomer.setPartyLegalEntity(customerPartyLegalEntityTypes);
		customerPartyType.setParty(partyTypeCustomer);

		return customerPartyType;
	}

	public SupplierPartyType generarZonaInformacionEmisor(CabeceraFactura cabeceraFactura) {
		final SupplierPartyType aSupplier = new SupplierPartyType();

		aSupplier.setCustomerAssignedAccountID(
				new CustomerAssignedAccountIDType(cabeceraFactura.getNumeroDocumento().toString()));

		List<AdditionalAccountIDType> additionalAccountIDType = new ArrayList<AdditionalAccountIDType>();
		additionalAccountIDType.add(new AdditionalAccountIDType(String.valueOf(cabeceraFactura.getTipoDocumento())));
		aSupplier.setAdditionalAccountID(additionalAccountIDType);

		PartyType partyType = new PartyType();
		PartyNameType partyNameType = new PartyNameType();
		partyNameType.setName(cabeceraFactura.getRazonSocial());

		partyType.addPartyName(partyNameType);

		aSupplier.setParty(partyType);

		AddressType addressType = new AddressType();

		addressType.setID(cabeceraFactura.getCodigoUbigeo());
		addressType.setStreetName(cabeceraFactura.getDireccionCompleta());
		addressType.setCitySubdivisionName("");
		addressType.setCityName(cabeceraFactura.getDistrito());
		addressType.setCountrySubentity(cabeceraFactura.getProvincia());
		addressType.setDistrict(cabeceraFactura.getDistrito());
		CountryType countryType = new CountryType();
		countryType.setIdentificationCode("PE");
		addressType.setCountry(countryType);

		partyType.setPostalAddress(addressType);

		List<PartyLegalEntityType> partyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();
		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(cabeceraFactura.getNombreComercial());
		partyLegalEntityTypes.add(partyLegalEntityType);

		partyType.setPartyLegalEntity(partyLegalEntityTypes);

		return aSupplier;
	}

}