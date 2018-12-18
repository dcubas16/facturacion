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

import javax.xml.datatype.XMLGregorianCalendar;

import org.facturacionelectronica.entidades.CabeceraNotaCredito;
import org.facturacionelectronica.entidades.DetalleNotaCredito;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.facturacionelectronica.util.Utilitario;

import com.helger.commons.locale.country.ECountry;
import com.helger.commons.state.ESuccess;
import com.helger.ubl21.UBL21Writer;
import com.helger.ubl21.codelist.ECurrencyCode21;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.BillingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CreditNoteLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ExternalReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ItemIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyTaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PricingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SignatureType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.CompanyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.CreditedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.PercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.PriceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.TaxExemptionReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.creditnote_21.CreditNoteType;

public class GeneradorNotaCredito21 {

	public ESuccess generarNotaCredito(CabeceraNotaCredito cabeceraNotaCredito, List<DetalleNotaCredito> listaDetalleNotaCredito) {

		final CreditNoteType creditNoteType = new CreditNoteType();
//		final CurrencyCodeContentType moneda = CurrencyCodeContentType.PEN;
		ECurrencyCode21 moneda = ECurrencyCode21.PEN;
		final String versionUbl = "2.1";
		final String customizationId = "2.0";
		final ECountry pais = ECountry.PE;

		// Zona firma
		creditNoteType.addSignature(generarCabeceraFirma(cabeceraNotaCredito));

		// Zona Informacion Basica
		generarDatosBasicosNotaCredito(creditNoteType, cabeceraNotaCredito, moneda, versionUbl, customizationId);

		// Zona Emisor
		creditNoteType.setAccountingSupplierParty(generarZonaInformacionEmisor(cabeceraNotaCredito, pais));

		// Zona Cliente
		creditNoteType.setAccountingCustomerParty(generarZonaInformacionCliente(cabeceraNotaCredito));

		// Zona Impuesto Total
		creditNoteType.addTaxTotal(generarImpuestosTotales(cabeceraNotaCredito, moneda));

		// Total Monetario
		creditNoteType.setLegalMonetaryTotal(generarTotalMonetario(cabeceraNotaCredito, moneda));

		// Detalle Factura
		for (int i = 0; i < listaDetalleNotaCredito.size(); i++) {
			creditNoteType.addCreditNoteLine(
					generarDetalleNotaCredito(cabeceraNotaCredito, listaDetalleNotaCredito.get(i), moneda, i));
		}
		
		String nombreArchivo = cabeceraNotaCredito.getNumeroDocumento() + Constantes.separadorNombreArchivo
				+ cabeceraNotaCredito.getTipoNotaCredito() + Constantes.separadorNombreArchivo
				+ cabeceraNotaCredito.getSerieNotaCredito() + Constantes.separadorNombreArchivo
				+ cabeceraNotaCredito.getNumeroCorrelativoNotaCredito();

		ESuccess eSuccess = imprimirNotaCreditoArchivo21(creditNoteType,
				ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudNotaCredito + nombreArchivo
				+ Constantes.extensionXml,
		Constantes.estandarXml);

		
		return eSuccess;

	}
	
	public static ESuccess imprimirNotaCreditoArchivo21(CreditNoteType creditNoteType, String ruta, String formato) {

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
		
		final ESuccess eSuccess = UBL21Writer.creditNote().write(creditNoteType, new File(ruta));

		return eSuccess;
	}

	private CreditNoteLineType generarDetalleNotaCredito(CabeceraNotaCredito cabeceraNotaCredito, DetalleNotaCredito lineaDetalleNotaCredito,
			ECurrencyCode21 moneda, int i) {

		i = i + 1;
		
		CreditNoteLineType creditNoteLineType = new CreditNoteLineType();
		creditNoteLineType.setID(String.valueOf(i));
		
		CreditedQuantityType creditedQuantityType = new CreditedQuantityType();
		creditedQuantityType.setValue(lineaDetalleNotaCredito.getCantidad());
		creditedQuantityType.setUnitCode(obtenerUnidadMedida(lineaDetalleNotaCredito.getUnidadMedida()));
		creditedQuantityType.setUnitCodeListID("UN/ECE rec 20");
		creditedQuantityType.setUnitCodeListAgencyName("United Nations Economic Commission for Europe");
		creditNoteLineType.setLineExtensionAmount(lineaDetalleNotaCredito.getValorVentaPorItem()).setCurrencyID(moneda.toString());

		creditNoteLineType.setCreditedQuantity(creditedQuantityType);
				
		PricingReferenceType pricingReferenceType = new PricingReferenceType();
		List<PriceType> listaPriceTypes = new ArrayList<PriceType>();
		
		PriceType priceType = new PriceType();
		priceType.setPriceAmount(lineaDetalleNotaCredito.getPrecioVentaUnitarioPorItem()).setCurrencyID(moneda.toString());
		
		PriceTypeCodeType priceTypeCodeType = new PriceTypeCodeType();
		priceTypeCodeType.setValue("01");
		priceTypeCodeType.setListName("SUNAT:Indicador de Tipo de Precio");
		priceTypeCodeType.setListAgencyName("PE:SUNAT");
		priceTypeCodeType.setListURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16");
		
		priceType.setPriceTypeCode(priceTypeCodeType);
		listaPriceTypes.add(priceType);
		pricingReferenceType.setAlternativeConditionPrice(listaPriceTypes);
		creditNoteLineType.setPricingReference(pricingReferenceType);
		
		List<TaxTotalType> listaTaxTotalTypes = new ArrayList<TaxTotalType>();
		TaxTotalType taxTotalType = new TaxTotalType();
		
		taxTotalType.setTaxAmount(lineaDetalleNotaCredito.getImpuestoPorItem()).setCurrencyID(moneda.toString());
		
		List<TaxSubtotalType> listaTaxSubtotalType = new ArrayList<TaxSubtotalType>();
		TaxSubtotalType taxSubtotalType = new TaxSubtotalType();
		
		taxSubtotalType.setTaxableAmount(lineaDetalleNotaCredito.getValorUnitarioPorItem()).setCurrencyID(moneda.toString());
		taxSubtotalType.setTaxAmount(lineaDetalleNotaCredito.getImpuestoPorItem()).setCurrencyID(moneda.toString());

		TaxCategoryType taxCategoryType = new TaxCategoryType();
		
		IDType idType = taxCategoryType.setID("S");
		
		idType.setSchemeID("UN/ECE 5305");
		idType.setSchemeName("Tax Category Identifier");
		idType.setSchemeAgencyName("United Nations Economic Commission for Europe");
		
		PercentType percentType = new PercentType(new BigDecimal("18.0"));
		
		TaxExemptionReasonCodeType taxExemptionReasonCodeType = new TaxExemptionReasonCodeType();
		taxExemptionReasonCodeType.setValue("10");
		taxExemptionReasonCodeType.setListAgencyName("PE:SUNAT");
		taxExemptionReasonCodeType.setListName("SUNAT:Codigo de Tipo de Afectación del IGV");
		taxExemptionReasonCodeType.setListURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07");
		
		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("1000");
		taxSchemeType.setName("IGV");
		taxSchemeType.setTaxTypeCode("VAT");
		
		taxCategoryType.setTaxScheme(taxSchemeType);
		taxCategoryType.setTaxExemptionReasonCode(taxExemptionReasonCodeType);
		taxCategoryType.setPercent(percentType);
		taxCategoryType.setID(idType);

		taxSubtotalType.setTaxCategory(taxCategoryType);
		
		
		listaTaxSubtotalType.add(taxSubtotalType);
		taxTotalType.setTaxSubtotal(listaTaxSubtotalType);
		
		listaTaxTotalTypes.add(taxTotalType);
		
		creditNoteLineType.setTaxTotal(listaTaxTotalTypes);
		
		ItemType itemType = new ItemType();
		
		List<DescriptionType> listaDescriptionTypes = new ArrayList<DescriptionType>();
		DescriptionType descriptionType = new DescriptionType();
		descriptionType.setValue(lineaDetalleNotaCredito.getDescripcionItem());
		
		ItemIdentificationType itemIdentificationType = new ItemIdentificationType();
		itemIdentificationType.setID(lineaDetalleNotaCredito.getCodigoItem());
		
		itemType.setSellersItemIdentification(itemIdentificationType);
		
		listaDescriptionTypes.add(descriptionType);
		itemType.setDescription(listaDescriptionTypes);
		
		creditNoteLineType.setItem(itemType);
		
		PriceType priceType2 = new PriceType();
		priceType2.setPriceAmount(lineaDetalleNotaCredito.getValorUnitarioPorItem()).setCurrencyID(moneda.toString());
		creditNoteLineType.setPrice(priceType2);
		
		return creditNoteLineType;
	}
	
	private String obtenerUnidadMedida(String unidadMedida) {

		if (unidadMedida.equals("01"))
			return "EACH";

		if (unidadMedida.equals("02"))
			return "PAIR";

		return null;
	}

	private MonetaryTotalType generarTotalMonetario(CabeceraNotaCredito cabeceraNotaCredito, ECurrencyCode21 moneda) {

		final MonetaryTotalType monetaryTotalType = new MonetaryTotalType();
		monetaryTotalType.setPayableAmount(cabeceraNotaCredito.getImporteTotalVenta()).setCurrencyID(moneda.toString());

		return monetaryTotalType;
	}

	private TaxTotalType generarImpuestosTotales(CabeceraNotaCredito cabeceraNotaCredito, ECurrencyCode21 moneda) {
		TaxTotalType taxTotalTypeTotal = new TaxTotalType();

		TaxAmountType taxAmountType = new TaxAmountType();
		taxAmountType.setValue(cabeceraNotaCredito.getSumatoriaIGV());
		taxAmountType.setCurrencyID(moneda.toString());

		taxTotalTypeTotal.setTaxAmount(taxAmountType);

		TaxSubtotalType taxSubtotalType = new TaxSubtotalType();
		taxSubtotalType.setTaxableAmount(cabeceraNotaCredito.getTotalValorVentaOpGravadas()).setCurrencyID(moneda.toString());
		taxSubtotalType.setTaxAmount(cabeceraNotaCredito.getSumatoriaIGV()).setCurrencyID(moneda.toString());

		TaxCategoryType taxCategoryType = new TaxCategoryType();
		IDType idType = new IDType();
//		idType.setSchemeID("UN/ECE 5305");
//		idType.setSchemeName("Tax Category Identifier");
//		idType.setSchemeAgencyName("United Nations Economic Commission for Europe");
		idType.setValue("S");

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		IDType idTypeTaxSchemeType = taxSchemeType.setID("1000");
//		idTypeTaxSchemeType.setSchemeID("UN/ECE 5153");
		idTypeTaxSchemeType.setSchemeAgencyID("6");
		taxSchemeType.setName("IGV");
		taxSchemeType.setTaxTypeCode("VAT");

		taxCategoryType.setTaxScheme(taxSchemeType);
		taxCategoryType.setID(idType);

		taxSubtotalType.setTaxCategory(taxCategoryType);

		taxTotalTypeTotal.addTaxSubtotal(taxSubtotalType);
		
		return taxTotalTypeTotal;
	}

	private CustomerPartyType generarZonaInformacionCliente(CabeceraNotaCredito cabeceraNotaCredito) {
		final CustomerPartyType customerPartyType = new CustomerPartyType();

		PartyType partyType = new PartyType();

		List<PartyTaxSchemeType> listaPartyTaxSchemeType = new ArrayList<PartyTaxSchemeType>();
		PartyTaxSchemeType partyTaxSchemeType = new PartyTaxSchemeType();
		partyTaxSchemeType.setRegistrationName(cabeceraNotaCredito.getRazonSocialCliente());

		CompanyIDType companyIDType = new CompanyIDType();
		companyIDType.setValue(cabeceraNotaCredito.getNumeroDocumentoCliente().toString());
		companyIDType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		companyIDType.setSchemeAgencyName("PE:SUNAT");
		companyIDType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
		companyIDType.setSchemeID(String.valueOf(cabeceraNotaCredito.getTipoDocumentoCliente()));

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("-");
		
		partyTaxSchemeType.setTaxScheme(taxSchemeType);
		partyTaxSchemeType.setCompanyID(companyIDType);

		listaPartyTaxSchemeType.add(partyTaxSchemeType);
		partyType.setPartyTaxScheme(listaPartyTaxSchemeType);
		
		
		List<PartyIdentificationType> listaPartyIdentificationTypes = new ArrayList<PartyIdentificationType>();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		IDType idType = new IDType();
		idType.setValue(cabeceraNotaCredito.getNumeroDocumentoCliente().toString());
		idType.setSchemeID(cabeceraNotaCredito.getTipoDocumentoCliente());
		idType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		idType.setSchemeAgencyName("PE:SUNAT");
		idType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
		
		partyIdentificationType.setID(idType);
		
		listaPartyIdentificationTypes.add(partyIdentificationType);
		partyType.setPartyIdentification(listaPartyIdentificationTypes);
		
		List<PartyLegalEntityType> listaPartyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();
		
		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(cabeceraNotaCredito.getRazonSocialCliente());
		
		listaPartyLegalEntityTypes.add(partyLegalEntityType);
		partyType.setPartyLegalEntity(listaPartyLegalEntityTypes);
		

		customerPartyType.setParty(partyType);

		return customerPartyType;
	}

	private SupplierPartyType generarZonaInformacionEmisor(CabeceraNotaCredito cabeceraNotaCredito, ECountry pais) {
		final SupplierPartyType aSupplier = new SupplierPartyType();

		PartyType partyType = new PartyType();
		List<PartyTaxSchemeType> listaPartyTaxSchemeType = new ArrayList<PartyTaxSchemeType>();
		PartyTaxSchemeType partyTaxSchemeType = new PartyTaxSchemeType();

		partyTaxSchemeType.setRegistrationName(cabeceraNotaCredito.getRazonSocial());

		CompanyIDType companyIDType = new CompanyIDType();
		companyIDType.setValue(cabeceraNotaCredito.getNumeroDocumento().toString());
		companyIDType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		companyIDType.setSchemeAgencyName("PE:SUNAT");
		companyIDType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
		companyIDType.setSchemeID(String.valueOf(cabeceraNotaCredito.getTipoDocumento()));

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("-");
		
		partyTaxSchemeType.setTaxScheme(taxSchemeType);
		partyTaxSchemeType.setCompanyID(companyIDType);

		listaPartyTaxSchemeType.add(partyTaxSchemeType);

		partyType.setPartyTaxScheme(listaPartyTaxSchemeType);
		
		
		List<PartyIdentificationType> listaPartyIdentificationTypes = new ArrayList<PartyIdentificationType>();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		IDType idType = new IDType();
		idType.setValue(cabeceraNotaCredito.getNumeroDocumento().toString());
		idType.setSchemeID("6");
		idType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		idType.setSchemeAgencyName("PE:SUNAT");
		idType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
		
		partyIdentificationType.setID(idType);
		
		listaPartyIdentificationTypes.add(partyIdentificationType);
		partyType.setPartyIdentification(listaPartyIdentificationTypes);
		
		List<PartyLegalEntityType> listaPartyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();
		
		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(cabeceraNotaCredito.getRazonSocial());
		
		listaPartyLegalEntityTypes.add(partyLegalEntityType);
		partyType.setPartyLegalEntity(listaPartyLegalEntityTypes);
		
		
		aSupplier.setParty(partyType);
		
		return aSupplier;
	}

	private void generarDatosBasicosNotaCredito(CreditNoteType creditNoteType, CabeceraNotaCredito cabeceraNotaCredito,
			ECurrencyCode21 moneda, String versionUbl, String customizationId) {

		creditNoteType.setUBLVersionID(versionUbl);
		creditNoteType.setCustomizationID(customizationId);
		creditNoteType.setID(cabeceraNotaCredito.getSerieNotaCredito() + Constantes.separadorNombreArchivo
				+ cabeceraNotaCredito.getNumeroCorrelativoNotaCredito());

		XMLGregorianCalendar fechaEmision = Utilitario
				.obtenerFechaXMLGregorianCalendar(cabeceraNotaCredito.getFechaEmision());
		
		creditNoteType.setIssueDate(fechaEmision);
		creditNoteType.setDocumentCurrencyCode(moneda.toString());
		
		
		List<ResponseType> listaResponseTypes = new ArrayList<ResponseType>();
		
		ResponseType responseType = new ResponseType();
		responseType.setReferenceID(cabeceraNotaCredito.getSerieDocumentoAfectado() + Constantes.separadorNombreArchivo
				+ cabeceraNotaCredito.getNumeroCorrelativoDocumentoAfectado());
		responseType.setResponseCode(cabeceraNotaCredito.getTipoNotaCredito());
		
		List<DescriptionType> listaDescriptionType = new ArrayList<DescriptionType>();
		DescriptionType descriptionType = new DescriptionType();
		descriptionType.setValue("Anulación de la operación");
		listaDescriptionType.add(descriptionType);
		
		responseType.setDescription(listaDescriptionType);
		
		listaResponseTypes.add(responseType);
				
		creditNoteType.setDiscrepancyResponse(listaResponseTypes);
		
		List<BillingReferenceType> listaBillingReferenceTypes = new ArrayList<BillingReferenceType>();
		
		BillingReferenceType billingReferenceType = new BillingReferenceType();
		
		DocumentReferenceType documentReferenceType = new DocumentReferenceType();
		
		documentReferenceType.setID(cabeceraNotaCredito.getSerieDocumentoAfectado() + Constantes.separadorNombreArchivo
				+ cabeceraNotaCredito.getNumeroCorrelativoDocumentoAfectado());
		
		String codigoTipoDocumentoAfectado = cabeceraNotaCredito.getSerieDocumentoAfectado().contains("F")?"01":"03";
		
		documentReferenceType.setDocumentTypeCode(codigoTipoDocumentoAfectado);
		
		billingReferenceType.setInvoiceDocumentReference(documentReferenceType);
		
		creditNoteType.setBillingReference(listaBillingReferenceTypes);
	}

	private SignatureType generarCabeceraFirma(CabeceraNotaCredito cabeceraNotaCredito) {
		SignatureType signatureType = new SignatureType();
		signatureType.setID("SignSUNAT");

		PartyType partyTypeSignature = new PartyType();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		partyIdentificationType.setID(cabeceraNotaCredito.getNumeroDocumento().toString());

		PartyNameType partyNameType = new PartyNameType();
		partyNameType.setName(cabeceraNotaCredito.getNombreComercial());
		partyTypeSignature.addPartyName(partyNameType);
		partyTypeSignature.addPartyIdentification(partyIdentificationType);

		signatureType.setSignatoryParty(partyTypeSignature);

		AttachmentType attachmentType = new AttachmentType();

		ExternalReferenceType externalReferenceType = new ExternalReferenceType();
		externalReferenceType.setURI(cabeceraNotaCredito.getNumeroDocumento().toString());
		attachmentType.setExternalReference(externalReferenceType);

		signatureType.setDigitalSignatureAttachment(attachmentType);

		return signatureType;
	}
}