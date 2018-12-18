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

import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.facturacionelectronica.util.Utilitario;

import com.helger.commons.locale.country.ECountry;
import com.helger.commons.state.ESuccess;
import com.helger.ubl21.UBL21Writer;
import com.helger.ubl21.codelist.ECurrencyCode21;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ExternalReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.InvoiceLineType;
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
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SignatureType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.CompanyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.InvoiceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.InvoicedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.PercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.PriceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.TaxExemptionReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

public class GeneradorFactura21 {

	public ESuccess generarFactura(Factura factura) {

		final InvoiceType invoiceType = new InvoiceType();
		// final CurrencyCodeContentType moneda = CurrencyCodeContentType.PEN;
		ECurrencyCode21 moneda = ECurrencyCode21.PEN;
		final String versionUbl = "2.1";
		final String customizationId = "2.0";
		final ECountry pais = ECountry.PE;

		// Zona firma
		invoiceType.addSignature(generarCabeceraFirma(factura.getCabeceraFactura()));

		// Zona Informacion Basica
		generarDatosBasicosFactura(invoiceType, factura.getCabeceraFactura(), moneda, versionUbl, customizationId);

		// Zona Emisor
		invoiceType.setAccountingSupplierParty(generarZonaInformacionEmisor(factura.getCabeceraFactura(), pais));

		// Zona Cliente
		invoiceType.setAccountingCustomerParty(generarZonaInformacionCliente(factura.getCabeceraFactura()));

		// Zona Impuesto Total
		invoiceType.addTaxTotal(generarImpuestosTotales(factura.getCabeceraFactura(), moneda));

		// Total Monetario
		invoiceType.setLegalMonetaryTotal(generarTotalMonetario(factura.getCabeceraFactura(), moneda));

		// Detalle Factura
		for (int i = 0; i < factura.getDetalleFactura().size(); i++) {
			invoiceType.addInvoiceLine(
					generarDetalleFactura(factura.getCabeceraFactura(), factura.getDetalleFactura().get(i), moneda, i));
		}

		String nombreArchivo = factura.getCabeceraFactura().getNumeroDocumento() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getTipoDocumentoFactura() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getSerie() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getNumeroCorrelativo();

		ESuccess eSuccess = imprimirFacturaArchivo21(invoiceType, ParametrosGlobales.obtenerParametros().getRutaRaiz()
				+ Constantes.rutaSolicitud + nombreArchivo + Constantes.extensionXml, Constantes.estandarXml);

		return eSuccess;

	}

	public static ESuccess imprimirFacturaArchivo21(InvoiceType invoiceType, String ruta, String formato) {

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

		final ESuccess eSuccess = UBL21Writer.invoice().write(invoiceType, new File(ruta));

		return eSuccess;
	}

	private InvoiceLineType generarDetalleFactura(CabeceraFactura cabeceraFactura, DetalleFactura lineaDetalleFactura,
			ECurrencyCode21 moneda, int i) {

		i = i + 1;

		InvoiceLineType invoiceLineType = new InvoiceLineType();
		invoiceLineType.setID(String.valueOf(i));

		InvoicedQuantityType invoicedQuantityType = new InvoicedQuantityType();
		invoicedQuantityType.setValue(lineaDetalleFactura.getCantidad());
		invoicedQuantityType.setUnitCode(obtenerUnidadMedida(lineaDetalleFactura.getUnidadMedida()));
		invoicedQuantityType.setUnitCodeListID("UN/ECE rec 20");
		invoicedQuantityType.setUnitCodeListAgencyName("United Nations Economic Commission for Europe");
		invoiceLineType.setLineExtensionAmount(lineaDetalleFactura.getValorVentaPorItem())
				.setCurrencyID(moneda.toString());

		invoiceLineType.setInvoicedQuantity(invoicedQuantityType);

		PricingReferenceType pricingReferenceType = new PricingReferenceType();
		List<PriceType> listaPriceTypes = new ArrayList<PriceType>();

		PriceType priceType = new PriceType();
		priceType.setPriceAmount(lineaDetalleFactura.getPrecioVentaUnitarioPorItem()).setCurrencyID(moneda.toString());

		PriceTypeCodeType priceTypeCodeType = new PriceTypeCodeType();
		
		if (lineaDetalleFactura.getImpuestoPorItem().compareTo(new BigDecimal("0")) == 1) {
			priceTypeCodeType.setValue("01");
		}else {
			priceTypeCodeType.setValue("02");
		}
			
		priceTypeCodeType.setListName("SUNAT:Indicador de Tipo de Precio");
		priceTypeCodeType.setListAgencyName("PE:SUNAT");
		priceTypeCodeType.setListURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16");

		priceType.setPriceTypeCode(priceTypeCodeType);
		listaPriceTypes.add(priceType);
		pricingReferenceType.setAlternativeConditionPrice(listaPriceTypes);
		invoiceLineType.setPricingReference(pricingReferenceType);

		
		if (lineaDetalleFactura.getImpuestoPorItem().compareTo(new BigDecimal("0")) == 1) {

			List<TaxTotalType> listaTaxTotalTypes = new ArrayList<TaxTotalType>();
			TaxTotalType taxTotalType = new TaxTotalType();

			taxTotalType.setTaxAmount(lineaDetalleFactura.getImpuestoPorItem()).setCurrencyID(moneda.toString());

			List<TaxSubtotalType> listaTaxSubtotalType = new ArrayList<TaxSubtotalType>();
			TaxSubtotalType taxSubtotalType = new TaxSubtotalType();

			taxSubtotalType.setTaxableAmount(lineaDetalleFactura.getValorUnitarioPorItem())
					.setCurrencyID(moneda.toString());
			taxSubtotalType.setTaxAmount(lineaDetalleFactura.getImpuestoPorItem()).setCurrencyID(moneda.toString());

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

			invoiceLineType.setTaxTotal(listaTaxTotalTypes);
			
			PriceType priceType2 = new PriceType();
			priceType2.setPriceAmount(lineaDetalleFactura.getValorUnitarioPorItem()).setCurrencyID(moneda.toString());
			invoiceLineType.setPrice(priceType2);
			
		}else {
			invoiceLineType.setLineExtensionAmount(new BigDecimal("0.00"))
			.setCurrencyID(moneda.toString());
			
			List<TaxTotalType> listaTaxTotalTypes = new ArrayList<TaxTotalType>();
			TaxTotalType taxTotalType = new TaxTotalType();

			taxTotalType.setTaxAmount(lineaDetalleFactura.getImpuestoPorItem()).setCurrencyID(moneda.toString());

			List<TaxSubtotalType> listaTaxSubtotalType = new ArrayList<TaxSubtotalType>();
			TaxSubtotalType taxSubtotalType = new TaxSubtotalType();

			taxSubtotalType.setTaxableAmount(lineaDetalleFactura.getValorUnitarioPorItem())
					.setCurrencyID(moneda.toString());
			taxSubtotalType.setTaxAmount(lineaDetalleFactura.getImpuestoPorItem()).setCurrencyID(moneda.toString());

			TaxCategoryType taxCategoryType = new TaxCategoryType();

			IDType idType = taxCategoryType.setID("O");

			idType.setSchemeID("UN/ECE 5305");
			idType.setSchemeName("Tax Category Identifier");
			idType.setSchemeAgencyName("United Nations Economic Commission for Europe");

			PercentType percentType = new PercentType(new BigDecimal("18.0"));

			TaxExemptionReasonCodeType taxExemptionReasonCodeType = new TaxExemptionReasonCodeType();
			taxExemptionReasonCodeType.setValue("37");
			taxExemptionReasonCodeType.setListAgencyName("PE:SUNAT");
			taxExemptionReasonCodeType.setListName("SUNAT:Codigo de Tipo de Afectación del IGV");
			taxExemptionReasonCodeType.setListURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07");

			TaxSchemeType taxSchemeType = new TaxSchemeType();
			taxSchemeType.setID("9996");
			taxSchemeType.setName("GRA");
			taxSchemeType.setTaxTypeCode("FRE");

			taxCategoryType.setTaxScheme(taxSchemeType);
			taxCategoryType.setTaxExemptionReasonCode(taxExemptionReasonCodeType);
			taxCategoryType.setPercent(percentType);
			taxCategoryType.setID(idType);

			taxSubtotalType.setTaxCategory(taxCategoryType);

			listaTaxSubtotalType.add(taxSubtotalType);
			taxTotalType.setTaxSubtotal(listaTaxSubtotalType);

			listaTaxTotalTypes.add(taxTotalType);

			invoiceLineType.setTaxTotal(listaTaxTotalTypes);
			
			PriceType priceType2 = new PriceType();
			priceType2.setPriceAmount(new BigDecimal("0.00")).setCurrencyID(moneda.toString());
			invoiceLineType.setPrice(priceType2);
		}
		
		
		

		ItemType itemType = new ItemType();

		List<DescriptionType> listaDescriptionTypes = new ArrayList<DescriptionType>();
		DescriptionType descriptionType = new DescriptionType();
		descriptionType.setValue(lineaDetalleFactura.getDescripcionItem());

		ItemIdentificationType itemIdentificationType = new ItemIdentificationType();
		itemIdentificationType.setID(lineaDetalleFactura.getCodigoItem());

		itemType.setSellersItemIdentification(itemIdentificationType);

		listaDescriptionTypes.add(descriptionType);
		itemType.setDescription(listaDescriptionTypes);

		invoiceLineType.setItem(itemType);

		

		return invoiceLineType;
	}

	private String obtenerUnidadMedida(String unidadMedida) {

		if (unidadMedida.equals("01"))
			return "EACH";

		if (unidadMedida.equals("02"))
			return "PAIR";

		return null;
	}

	private MonetaryTotalType generarTotalMonetario(CabeceraFactura cabeceraFactura, ECurrencyCode21 moneda) {

		final MonetaryTotalType monetaryTotalType = new MonetaryTotalType();
		monetaryTotalType.setPayableAmount(cabeceraFactura.getImporteTotalVenta()).setCurrencyID(moneda.toString());

		return monetaryTotalType;
	}

	private TaxTotalType generarImpuestosTotales(CabeceraFactura cabeceraFactura, ECurrencyCode21 moneda) {
		TaxTotalType taxTotalTypeTotal = new TaxTotalType();

		TaxAmountType taxAmountType = new TaxAmountType();
		taxAmountType.setValue(cabeceraFactura.getSumatoriaIGV());
		taxAmountType.setCurrencyID(moneda.toString());

		taxTotalTypeTotal.setTaxAmount(taxAmountType);

		TaxSubtotalType taxSubtotalType = new TaxSubtotalType();
		taxSubtotalType.setTaxableAmount(cabeceraFactura.getTotalValorVentaOpGravadas())
				.setCurrencyID(moneda.toString());
		taxSubtotalType.setTaxAmount(cabeceraFactura.getSumatoriaIGV()).setCurrencyID(moneda.toString());

		TaxCategoryType taxCategoryType = new TaxCategoryType();
		IDType idType = new IDType();
		idType.setSchemeID("UN/ECE 5305");
		idType.setSchemeName("Tax Category Identifier");
		idType.setSchemeAgencyName("United Nations Economic Commission for Europe");
		idType.setValue("S");

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		IDType idTypeTaxSchemeType = taxSchemeType.setID("1000");
		idTypeTaxSchemeType.setSchemeID("UN/ECE 5153");
		idTypeTaxSchemeType.setSchemeAgencyID("6");
		taxSchemeType.setName("IGV");
		taxSchemeType.setTaxTypeCode("VAT");

		taxCategoryType.setTaxScheme(taxSchemeType);
		taxCategoryType.setID(idType);

		taxSubtotalType.setTaxCategory(taxCategoryType);

		taxTotalTypeTotal.addTaxSubtotal(taxSubtotalType);
		
		if(cabeceraFactura.getTotalValorVentaOpGratuitas().compareTo(new BigDecimal("0.00")) == 1) {
			TaxSubtotalType taxSubtotalTypeOperaGratis = new TaxSubtotalType();
			taxSubtotalTypeOperaGratis.setTaxableAmount(cabeceraFactura.getTotalValorVentaOpGratuitas())
					.setCurrencyID(moneda.toString());
			taxSubtotalTypeOperaGratis.setTaxAmount(new BigDecimal("0.00")).setCurrencyID(moneda.toString());

			TaxCategoryType taxCategoryTypeOperaGratis = new TaxCategoryType();
			IDType idTypeOperaGratis = new IDType();
			idTypeOperaGratis.setSchemeID("UN/ECE 5305");
			idTypeOperaGratis.setSchemeName("Tax Category Identifier");
			idTypeOperaGratis.setSchemeAgencyName("United Nations Economic Commission for Europe");
			idTypeOperaGratis.setValue("O");

			TaxSchemeType taxSchemeTypeOperaGratis = new TaxSchemeType();
			IDType idTypeTaxSchemeTypeOperaGratis = taxSchemeTypeOperaGratis.setID("9996");
			idTypeTaxSchemeTypeOperaGratis.setSchemeID("UN/ECE 5153");
			idTypeTaxSchemeTypeOperaGratis.setSchemeAgencyID("6");
			taxSchemeTypeOperaGratis.setName("GRA");
			taxSchemeTypeOperaGratis.setTaxTypeCode("FRE");

			taxCategoryTypeOperaGratis.setTaxScheme(taxSchemeTypeOperaGratis);
			taxCategoryTypeOperaGratis.setID(idTypeOperaGratis);

			taxSubtotalTypeOperaGratis.setTaxCategory(taxCategoryTypeOperaGratis);
			
			taxTotalTypeTotal.addTaxSubtotal(taxSubtotalTypeOperaGratis);
		}

		return taxTotalTypeTotal;
	}

	private CustomerPartyType generarZonaInformacionCliente(CabeceraFactura cabeceraFactura) {
		final CustomerPartyType customerPartyType = new CustomerPartyType();

		PartyType partyType = new PartyType();

		List<PartyTaxSchemeType> listaPartyTaxSchemeType = new ArrayList<PartyTaxSchemeType>();
		PartyTaxSchemeType partyTaxSchemeType = new PartyTaxSchemeType();
		partyTaxSchemeType.setRegistrationName(cabeceraFactura.getRazonSocialCliente());

		CompanyIDType companyIDType = new CompanyIDType();
		companyIDType.setValue(cabeceraFactura.getNumeroDocumentoCliente().toString());
		companyIDType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		companyIDType.setSchemeAgencyName("PE:SUNAT");
		companyIDType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
		companyIDType.setSchemeID(String.valueOf(cabeceraFactura.getTipoDocumentoCliente()));

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("-");

		partyTaxSchemeType.setTaxScheme(taxSchemeType);
		partyTaxSchemeType.setCompanyID(companyIDType);

		listaPartyTaxSchemeType.add(partyTaxSchemeType);
		partyType.setPartyTaxScheme(listaPartyTaxSchemeType);

		List<PartyIdentificationType> listaPartyIdentificationTypes = new ArrayList<PartyIdentificationType>();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		IDType idType = new IDType();
		idType.setValue(cabeceraFactura.getNumeroDocumentoCliente().toString());
		idType.setSchemeID(cabeceraFactura.getTipoDocumentoCliente());
		idType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		idType.setSchemeAgencyName("PE:SUNAT");
		idType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");

		partyIdentificationType.setID(idType);

		listaPartyIdentificationTypes.add(partyIdentificationType);
		partyType.setPartyIdentification(listaPartyIdentificationTypes);

		List<PartyLegalEntityType> listaPartyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();

		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(cabeceraFactura.getRazonSocialCliente());

		listaPartyLegalEntityTypes.add(partyLegalEntityType);
		partyType.setPartyLegalEntity(listaPartyLegalEntityTypes);

		customerPartyType.setParty(partyType);

		return customerPartyType;
	}

	private SupplierPartyType generarZonaInformacionEmisor(CabeceraFactura cabeceraFactura, ECountry pais) {
		final SupplierPartyType aSupplier = new SupplierPartyType();

		PartyType partyType = new PartyType();
		List<PartyTaxSchemeType> listaPartyTaxSchemeType = new ArrayList<PartyTaxSchemeType>();
		PartyTaxSchemeType partyTaxSchemeType = new PartyTaxSchemeType();

		partyTaxSchemeType.setRegistrationName(cabeceraFactura.getRazonSocial());

		CompanyIDType companyIDType = new CompanyIDType();
		companyIDType.setValue(cabeceraFactura.getNumeroDocumento().toString());
		companyIDType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		companyIDType.setSchemeAgencyName("PE:SUNAT");
		companyIDType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
		companyIDType.setSchemeID(String.valueOf(cabeceraFactura.getTipoDocumento()));

		TaxSchemeType taxSchemeType = new TaxSchemeType();
		taxSchemeType.setID("-");

		partyTaxSchemeType.setTaxScheme(taxSchemeType);
		partyTaxSchemeType.setCompanyID(companyIDType);

		listaPartyTaxSchemeType.add(partyTaxSchemeType);

		partyType.setPartyTaxScheme(listaPartyTaxSchemeType);

		List<PartyIdentificationType> listaPartyIdentificationTypes = new ArrayList<PartyIdentificationType>();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		IDType idType = new IDType();
		idType.setValue(cabeceraFactura.getNumeroDocumento().toString());
		idType.setSchemeID("6");
		idType.setSchemeName("SUNAT:Identificador de Documento de Identidad");
		idType.setSchemeAgencyName("PE:SUNAT");
		idType.setSchemeURI("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");

		partyIdentificationType.setID(idType);

		listaPartyIdentificationTypes.add(partyIdentificationType);
		partyType.setPartyIdentification(listaPartyIdentificationTypes);

		List<PartyLegalEntityType> listaPartyLegalEntityTypes = new ArrayList<PartyLegalEntityType>();

		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(cabeceraFactura.getRazonSocial());

		listaPartyLegalEntityTypes.add(partyLegalEntityType);
		partyType.setPartyLegalEntity(listaPartyLegalEntityTypes);

		aSupplier.setParty(partyType);

		return aSupplier;
	}

	private void generarDatosBasicosFactura(InvoiceType invoiceType, CabeceraFactura cabeceraFactura,
			ECurrencyCode21 moneda, String versionUbl, String customizationId) {

		invoiceType.setUBLVersionID(versionUbl);
		invoiceType.setCustomizationID(customizationId);
		invoiceType.setID(cabeceraFactura.getSerie() + Constantes.separadorNombreArchivo
				+ cabeceraFactura.getNumeroCorrelativo());

		XMLGregorianCalendar fechaEmision = Utilitario
				.obtenerFechaXMLGregorianCalendar(cabeceraFactura.getFechaEmision());
		XMLGregorianCalendar horaEmision = Utilitario
				.obtenerHoraXMLGregorianCalendar(cabeceraFactura.getFechaEmision());

		invoiceType.setIssueDate(fechaEmision);
		invoiceType.setIssueTime(horaEmision);

		InvoiceTypeCodeType invoiceTypeCodeType = invoiceType
				.setInvoiceTypeCode(cabeceraFactura.getTipoDocumentoFactura());
		invoiceTypeCodeType.setListID("0101");
		invoiceTypeCodeType.setListAgencyName("PE:SUNAT");
		invoiceTypeCodeType.setListURI("SUNAT:Identifi cador de Tipo de Documento");
		invoiceTypeCodeType.setListName("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01");

		DocumentCurrencyCodeType documentCurrencyCodeType = invoiceType.setDocumentCurrencyCode(moneda.toString());
		documentCurrencyCodeType.setListID("ISO 4217 Alpha");
		documentCurrencyCodeType.setListName("Currency");
		documentCurrencyCodeType.setListAgencyName("United Nations Economic Commission for Europe");
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
}