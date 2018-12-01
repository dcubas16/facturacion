package org.facturacionelectronica.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.facturacionelectronica.util.Utilitario;
import com.helger.commons.state.ESuccess;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ExternalReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SignatureType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomerAssignedAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferenceDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UBLVersionIDType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.VoidedDocumentsLineType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.IdentifierType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.TextType;

public class GeneradorComunicacionBaja {

	public ESuccess generarComunicacionBaja(ComunicacionBaja comunicacionBaja,
			List<DetalleComunicacionBaja> listaDetalleComunicacionBaja) {

		final VoidedDocumentsType voidedDocumentsType = new VoidedDocumentsType();

		agregarDatosBasicosComunicacionBaja(voidedDocumentsType, comunicacionBaja);

		// Zona firma cabecera
		voidedDocumentsType.addSignature(generarCabeceraFirma(comunicacionBaja));

		// Zona Emisor
		voidedDocumentsType.setAccountingSupplierParty(generarZonaInformacionEmisor(comunicacionBaja));

		// Zona Documentos Anulados
		voidedDocumentsType.setVoidedDocumentsLine(generarZonaDocumentosAnulados(listaDetalleComunicacionBaja));

		// Escribir archivo XML
		ESuccess eSuccess = GestorArchivosXML.imprimirComunicacionBaja(voidedDocumentsType,
				ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaSolicitudComunicaBaja
						+ Utilitario.obtenerNombreArchivoComunicacionBaja(comunicacionBaja) + Constantes.extensionXml,
				Constantes.estandarXml);

		// Firmado

		return eSuccess;

	}

	private List<VoidedDocumentsLineType> generarZonaDocumentosAnulados(
			List<DetalleComunicacionBaja> listaDetalleComunicacionBaja) {

		List<VoidedDocumentsLineType> listaVoidedDocumentsLineTypes = new ArrayList<VoidedDocumentsLineType>();
		DetalleComunicacionBaja detalleComunicacionBaja;

		for (int i = 0; i < listaDetalleComunicacionBaja.size(); i++) {

			detalleComunicacionBaja = listaDetalleComunicacionBaja.get(i);

			VoidedDocumentsLineType voidedDocumentsLineType = new VoidedDocumentsLineType();
			voidedDocumentsLineType.setLineID(new LineIDType(String.valueOf(i + 1)));
			voidedDocumentsLineType.setDocumentTypeCode(
					new DocumentTypeCodeType(String.valueOf(detalleComunicacionBaja.getTipoDocumento())));
			voidedDocumentsLineType
					.setDocumentSerialID(new IdentifierType(detalleComunicacionBaja.getSerieDocumento()));
			voidedDocumentsLineType
					.setDocumentNumberID(new IdentifierType(detalleComunicacionBaja.getNumeroCorrelativo()));

			voidedDocumentsLineType.setVoidReasonDescription(new TextType(detalleComunicacionBaja.getMotivoBaja()));

			listaVoidedDocumentsLineTypes.add(voidedDocumentsLineType);
		}

		return listaVoidedDocumentsLineTypes;
	}

	private SupplierPartyType generarZonaInformacionEmisor(ComunicacionBaja comunicacionBaja) {

		SupplierPartyType supplierPartyType = new SupplierPartyType();
		CustomerAssignedAccountIDType customerAssignedAccountIDType = new CustomerAssignedAccountIDType(
				comunicacionBaja.getNumeroRuc().toString());
		List<AdditionalAccountIDType> listaAdditionalAccountIDTypes = new ArrayList<AdditionalAccountIDType>();
		AdditionalAccountIDType additionalAccountIDType = new AdditionalAccountIDType(
				String.valueOf(comunicacionBaja.getTipoDocumento()));
		listaAdditionalAccountIDTypes.add(additionalAccountIDType);

		PartyType partyType = new PartyType();
		PartyLegalEntityType partyLegalEntityType = new PartyLegalEntityType();
		partyLegalEntityType.setRegistrationName(comunicacionBaja.getRazonSocial());
		partyType.addPartyLegalEntity(partyLegalEntityType);

		supplierPartyType.setCustomerAssignedAccountID(customerAssignedAccountIDType);
		supplierPartyType.setAdditionalAccountID(listaAdditionalAccountIDTypes);
		supplierPartyType.setParty(partyType);

		return supplierPartyType;
	}

	private SignatureType generarCabeceraFirma(ComunicacionBaja comunicacionBaja) {
		SignatureType signatureType = new SignatureType();
		signatureType.setID("SignSUNAT");//

		PartyType partyTypeSignature = new PartyType();
		PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
		partyIdentificationType.setID(comunicacionBaja.getNumeroRuc().toString());

		PartyNameType partyNameType = new PartyNameType();
		partyNameType.setName(comunicacionBaja.getRazonSocial());
		partyTypeSignature.addPartyName(partyNameType);
		partyTypeSignature.addPartyIdentification(partyIdentificationType);

		signatureType.setSignatoryParty(partyTypeSignature);

		AttachmentType attachmentType = new AttachmentType();

		ExternalReferenceType externalReferenceType = new ExternalReferenceType();
		externalReferenceType.setURI(comunicacionBaja.getNumeroRuc().toString());
		attachmentType.setExternalReference(externalReferenceType);

		signatureType.setDigitalSignatureAttachment(attachmentType);

		return signatureType;
	}

	private void agregarDatosBasicosComunicacionBaja(VoidedDocumentsType voidedDocumentsType,
			ComunicacionBaja comunicacionBaja) {

		UBLVersionIDType ublVersionIDType = new UBLVersionIDType("2.0");
		CustomizationIDType customizationIDType = new CustomizationIDType("1.0");
		
		// Constantes.comu
		IDType idType = new IDType(Constantes.siglaIdentComunicacionBaja + Constantes.separadorNombreArchivo
				+ Utilitario.obtenerFechaFormatoComunicaBaja(comunicacionBaja.getFechaGeneraComunica())
				+ Constantes.separadorNombreArchivo + Constantes.numeroUnoComunicacionBaja);
		XMLGregorianCalendar xmlFecha = Utilitario
				.obtenerFechaXMLGregorianCalendar(comunicacionBaja.getFechaGeneracionDocumento());

		ReferenceDateType referenceDateType = new ReferenceDateType(xmlFecha);

		IssueDateType issueDateType = new IssueDateType(
				Utilitario.obtenerFechaXMLGregorianCalendar(comunicacionBaja.getFechaGeneraComunica()));

		voidedDocumentsType.setUBLVersionID(ublVersionIDType);
		voidedDocumentsType.setCustomizationID(customizationIDType);
		voidedDocumentsType.setID(idType);
		voidedDocumentsType.setReferenceDate(referenceDateType);
		voidedDocumentsType.setIssueDate(issueDateType);

	}

}
