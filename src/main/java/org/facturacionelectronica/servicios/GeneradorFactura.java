package org.facturacionelectronica.servicios;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXB;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.w3c.dom.Document;
import com.helger.commons.locale.country.ECountry;
import com.helger.commons.state.ESuccess;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ExternalReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemIdentificationType;
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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceAmountType;
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

	public ESuccess generarFactura(Factura factura) throws Exception {

		final CurrencyCodeContentType eCurrency = CurrencyCodeContentType.PEN;

		final ECountry eCountry = ECountry.PE;

		final InvoiceType aInvoice = new InvoiceType();

		// Zona Informacion Basica
		generarDatosBasicosFactura(aInvoice, factura.getCabeceraFactura(), eCurrency, "2.0");

		// Zona firma cabecera
		aInvoice.addSignature(generarCabeceraFirma(factura.getCabeceraFactura()));

		// Zona Cliente
		aInvoice.setAccountingCustomerParty(generarZonaInformacionCliente(factura.getCabeceraFactura()));

		// Zona Emisor
		aInvoice.setAccountingSupplierParty(generarZonaInformacionEmisor(factura.getCabeceraFactura(), eCountry));

		// Zona Impuesto Total
		aInvoice.addTaxTotal(generarImpuestosTotales(factura.getCabeceraFactura(), eCurrency));

		// Detalle Factura
		for (int i = 0; i < factura.getDetalleFactura().size(); i++) {
			aInvoice.addInvoiceLine(generarDetalleFactura(factura.getCabeceraFactura(),
					factura.getDetalleFactura().get(i), eCurrency, i));
		}

		// Total Monetario
		aInvoice.setLegalMonetaryTotal(generarTotalMonetario(factura.getCabeceraFactura(), eCurrency));

		// 20100454523-01-F001-4375
		String nombreArchivo = factura.getCabeceraFactura().getNumeroDocumento() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getTipoDocumentoFactura() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getSerie() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getNumeroCorrelativo();

		// Escribir archivo XML
		ESuccess eSuccess = GestorArchivosXML.imprimirFacturaArchivo(aInvoice,
				ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitud + nombreArchivo
						+ Constantes.extensionXml,
				Constantes.estandarXml);

		// Firmado

		return eSuccess;
	}

	// ----------------------------------------------------------------------------INICIO
	// Generar Conversiones
	private String obtenerCodigoTipoImpuesto(String tipoDocumentoFactura) {
		// TODO Auto-generated method stub
		return "VAT";
	}

	private String obtenerNombreImpuesto(String tipoDocumentoFactura) {
		// TODO Auto-generated method stub
		return "IGV";
	}

	private UnitCodeContentType obtenerUnidadMedida(String unidadMedida) {

		if (unidadMedida.equals("01"))
			return UnitCodeContentType.EACH;

		if (unidadMedida.equals("02"))
			return UnitCodeContentType.PAIR;

		return null;
	}
	// ----------------------------------------------------------------------------FIN
	// Generar Conversiones

	private void generarDatosBasicosFactura(InvoiceType aInvoice, CabeceraFactura cabeceraFactura,
			CurrencyCodeContentType eCurrency, String versionUbl) {

		try {

			aInvoice.setID(cabeceraFactura.getSerie() + Constantes.separadorNombreArchivo
					+ cabeceraFactura.getNumeroCorrelativo());
			aInvoice.setInvoiceTypeCode(cabeceraFactura.getTipoDocumentoFactura());
			aInvoice.setCustomizationID("1.0");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(cabeceraFactura.getFechaEmision());
			XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);

			aInvoice.setIssueDate(xmlCal);
			aInvoice.setDocumentCurrencyCode(eCurrency.value());
			aInvoice.setUBLVersionID(versionUbl);

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
		}
	}

	private SignatureType generarCabeceraFirma(CabeceraFactura cabeceraFactura) {
		SignatureType signatureType = new SignatureType();
		signatureType.setID("SignSUNAT");

		PartyType partyTypeSignature = new PartyType();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		partyIdentificationType.setID(cabeceraFactura.getNumeroDocumento().toString());

		PartyNameType partyNameType = new PartyNameType();
		partyNameType.setName(cabeceraFactura.getNombreComercial());
		partyTypeSignature.addPartyName(partyNameType);
		partyTypeSignature.addPartyIdentification(partyIdentificationType);

		signatureType.setSignatoryParty(partyTypeSignature);

		AttachmentType attachmentType = new AttachmentType();

		ExternalReferenceType externalReferenceType = new ExternalReferenceType();
		externalReferenceType.setURI(cabeceraFactura.getNumeroDocumento().toString());
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

		// KeyInfoType keyInfoType = new KeyInfoType();

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

		final MonetaryTotalType monetaryTotalType = new MonetaryTotalType();
		monetaryTotalType.setPayableAmount(cabeceraFactura.getImporteTotalVenta()).setCurrencyID(eCurrency);

		return monetaryTotalType;
	}

	private InvoiceLineType generarDetalleFactura(CabeceraFactura cabeceraFactura, DetalleFactura lineaDetalleFactura,
			CurrencyCodeContentType eCurrency, int id) {

		id = id + 1;

		InvoiceLineType invoiceLineType = new InvoiceLineType();

		invoiceLineType.setID(Integer.toString(id));
		invoiceLineType.setInvoicedQuantity(lineaDetalleFactura.getCantidad())
				.setUnitCode(obtenerUnidadMedida(lineaDetalleFactura.getUnidadMedida()));
		invoiceLineType.setLineExtensionAmount(lineaDetalleFactura.getValorVentaPorItem()).setCurrencyID(eCurrency);

		ItemType itemType = new ItemType();

		List<DescriptionType> descriptionTypesList = new ArrayList<DescriptionType>();
		DescriptionType descriptionType = new DescriptionType(lineaDetalleFactura.getDescripcionItem());
		descriptionTypesList.add(descriptionType);
		itemType.setDescription(descriptionTypesList);

		ItemIdentificationType itemIdentificationType = new ItemIdentificationType();
		itemIdentificationType.setID(lineaDetalleFactura.getCodigoItem());

		itemType.setSellersItemIdentification(itemIdentificationType);

		invoiceLineType.setItem(itemType);

		PricingReferenceType pricingReferenceType = new PricingReferenceType();

		List<PriceType> priceTypes = new ArrayList<PriceType>();

		PriceAmountType priceAmountType = new PriceAmountType();
		priceAmountType.setValue(lineaDetalleFactura.getPrecioVentaUnitarioPorItem());
		priceAmountType.setCurrencyID(eCurrency);

		PriceType priceTypeCode = new PriceType();
		priceTypeCode.setPriceTypeCode(obtenerCodigoTipoPrecio(cabeceraFactura.getTipoDocumentoFactura()));
		priceTypeCode.setPriceAmount(priceAmountType);

		priceTypes.add(priceTypeCode);

		pricingReferenceType.getAlternativeConditionPrice();

		pricingReferenceType.setAlternativeConditionPrice(priceTypes);

		invoiceLineType.setPricingReference(pricingReferenceType);

		PriceType priceType = new PriceType();
		priceType.setPriceAmount(lineaDetalleFactura.getValorUnitarioPorItem()).setCurrencyID(eCurrency);

		invoiceLineType.setPrice(priceType);

		// Zona Impuesto Items
		TaxTotalType taxTotalType = new TaxTotalType();
		taxTotalType.setTaxAmount(lineaDetalleFactura.getImpuestoPorItem()).setCurrencyID(eCurrency);

		TaxSubtotalType taxSubtotalType = new TaxSubtotalType();

		taxSubtotalType.setTaxableAmount(lineaDetalleFactura.getValorVentaBruto()).setCurrencyID(eCurrency);
		taxSubtotalType.setTaxAmount(lineaDetalleFactura.getImpuestoPorItem()).setCurrencyID(eCurrency);
		taxSubtotalType.setPercent(lineaDetalleFactura.getPorcentajeImpuestoItem());
		PercentType percentType = new PercentType();
		percentType.setValue(new BigDecimal("18.0"));
		taxSubtotalType.setPercent(percentType);

		TaxCategoryType taxCategoryType = new TaxCategoryType();
		taxCategoryType.setTaxExemptionReasonCode("10");

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("1000");
		taxSchemeType.setName(obtenerNombreImpuesto(cabeceraFactura.getTipoDocumentoFactura()));
		taxSchemeType.setTaxTypeCode(obtenerCodigoTipoImpuesto(cabeceraFactura.getTipoDocumentoFactura()));

		taxCategoryType.setTaxScheme(taxSchemeType);

		taxSubtotalType.setTaxCategory(taxCategoryType);

		taxTotalType.addTaxSubtotal(taxSubtotalType);

		invoiceLineType.addTaxTotal(taxTotalType);

		return invoiceLineType;
	}

	private String obtenerCodigoTipoPrecio(String tipoDocumentoFactura) {
		// TODO Auto-generated method stub
		return "01";
	}

	private TaxTotalType generarImpuestosTotales(CabeceraFactura cabeceraFactura, CurrencyCodeContentType eCurrency) {
		TaxTotalType taxTotalTypeTotal = new TaxTotalType();

		taxTotalTypeTotal.setTaxAmount(cabeceraFactura.getSumatoriaIGV()).setCurrencyID(eCurrency);

		TaxSubtotalType taxSubtotalTypeTotal = new TaxSubtotalType();
		taxSubtotalTypeTotal.setTaxAmount(cabeceraFactura.getSumatoriaIGV()).setCurrencyID(eCurrency);

		TaxCategoryType taxCategoryTypeTotal = new TaxCategoryType();

		TaxSchemeType taxSchemeTypeTotal = new TaxSchemeType();
		taxSchemeTypeTotal.setID("1000");
		taxSchemeTypeTotal.setName(obtenerNombreImpuesto(cabeceraFactura.getTipoDocumentoFactura()));
		taxSchemeTypeTotal.setTaxTypeCode(obtenerCodigoTipoImpuesto(cabeceraFactura.getTipoDocumentoFactura()));

		taxCategoryTypeTotal.setTaxScheme(taxSchemeTypeTotal);

		taxSubtotalTypeTotal.setTaxCategory(taxCategoryTypeTotal);

		taxTotalTypeTotal.addTaxSubtotal(taxSubtotalTypeTotal);

		return taxTotalTypeTotal;

	}

	public UBLExtensionsType generarInformacionAdicional(CabeceraFactura cabeceraFactura,
			CurrencyCodeContentType eCurrency) throws ParserConfigurationException {

		UBLExtensionsType ublExtensionsTypeAdditional = new UBLExtensionsType();
		UBLExtensionType ublExtensionTypeAdditional = new UBLExtensionType();
		ExtensionContentType extensionContentType = new ExtensionContentType();

		AdditionalInformationType additionalInformationType = new AdditionalInformationType();
		AdditionalMonetaryTotalType additionalMonetaryTotalType = new AdditionalMonetaryTotalType();
		PayableAmountType payableAmountType = new PayableAmountType();
		AdditionalPropertyType additionalPropertyType = new AdditionalPropertyType();

		additionalMonetaryTotalType.setID(new IDType("1001"));
		payableAmountType.setCurrencyID(eCurrency);
		payableAmountType.setValue(cabeceraFactura.getTotalValorVentaOpGravadas());
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

	public SupplierPartyType generarZonaInformacionEmisor(CabeceraFactura cabeceraFactura, ECountry eCountry) {
		final SupplierPartyType aSupplier = new SupplierPartyType();

		aSupplier.setCustomerAssignedAccountID(
				new CustomerAssignedAccountIDType(cabeceraFactura.getNumeroDocumento().toString()));

		List<AdditionalAccountIDType> additionalAccountIDType = new ArrayList<AdditionalAccountIDType>();
		additionalAccountIDType.add(new AdditionalAccountIDType(String.valueOf(cabeceraFactura.getTipoDocumento())));
		aSupplier.setAdditionalAccountID(additionalAccountIDType);

		PartyType partyType = new PartyType();

		aSupplier.setParty(partyType);

		AddressType addressType = new AddressType();

		addressType.setID(cabeceraFactura.getCodigoUbigeo());
		addressType.setStreetName(cabeceraFactura.getDireccionCompleta());
		addressType.setCitySubdivisionName("");
		addressType.setCityName(cabeceraFactura.getDistrito());
		addressType.setCountrySubentity(cabeceraFactura.getProvincia());
		addressType.setDistrict(cabeceraFactura.getDistrito());
		CountryType countryType = new CountryType();
		countryType.setIdentificationCode(eCountry.toString());
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