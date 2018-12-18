package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;

import org.facturacionelectronica.dao.GestorLlavesDao;
import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.dao.entidades.LlaveDao;
import org.facturacionelectronica.dao.entidades.NotaCreditoDao;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class GestorFirma {

	public Map<String, Object> firmarDocumento(InputStream inDocument, FacturaDao facturaDao) {

		Map<String, Object> retorno = new HashMap<String, Object>();

		try {

			GestorLlavesDao gestorLlavesDao = new GestorLlavesDao();
			LlaveDao llaveDao = gestorLlavesDao.obtenerLlave(facturaDao.getNumeroDocumento().toString());

			String alias = llaveDao.getAlias();
			String clavePrivateKey = llaveDao.getPin();

			// Cargamos el almacen de claves
			KeyStore ks = KeyStore.getInstance(Constantes.KEYSTORE_TYPE);

			// Obtenemos la clave privada, pues la necesitaremos para firmar.
			ks.load(new FileInputStream(
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaCertificado
							+ facturaDao.getNumeroDocumento().toString().trim() + Constantes.extensionJks),
					clavePrivateKey.toCharArray());

			// get my private key
			PrivateKey privateKey = (PrivateKey) ks.getKey(alias, clavePrivateKey.toCharArray());

			// Añadimos el KeyInfo del certificado cuya clave privada usamos
			X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
			ByteArrayOutputStream signatureFile = new ByteArrayOutputStream();

			Document doc = buildDocument(inDocument);
			Node parentNode = addExtensionContent(doc, facturaDao);
			doc.normalizeDocument();

			String idReference = "SignSUNAT";

			XMLSignatureFactory fac = XMLSignatureFactory.getInstance();

			Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA256, null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);

			// SignedInfo si = fac.newSignedInfo(
			// fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
			// (C14NMethodParameterSpec) null),
			// fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
			// Collections.singletonList(ref));

			// Inicio Prueba ----- VALIDA
			SignatureMethod signatureMethod = fac
					.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null);
			CanonicalizationMethod canonicalizationMethod = fac
					.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null);
			SignedInfo si = fac.newSignedInfo(canonicalizationMethod, signatureMethod, Collections.singletonList(ref));
			// Fin Prueba

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			List<X509Certificate> x509Content = new ArrayList<X509Certificate>();
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DOMSignContext dsc = new DOMSignContext(privateKey, doc.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);

			if (parentNode != null)
				dsc.setParent(parentNode);
			dsc.setDefaultNamespacePrefix("ds");
			signature.sign(dsc);

			String digestValue = Constantes.separadorNombreArchivo;
			Element elementParent = (Element) dsc.getParent();
			if (idReference != null && elementParent.getElementsByTagName("ds:Signature") != null) {
				Element elementSignature = (Element) elementParent.getElementsByTagName("ds:Signature").item(0);
				elementSignature.setAttribute("Id", idReference);

				NodeList nodeList = elementParent.getElementsByTagName("ds:DigestValue");
				for (int i = 0; i < nodeList.getLength(); i++) {
					digestValue = this.obtenerNodo(nodeList.item(i));
				}
			}

			outputDocToOutputStream(doc, signatureFile);
			signatureFile.close();

			retorno.put("signatureFile", signatureFile);
			retorno.put("digestValue", digestValue);

			return retorno;
		} catch (Exception e) {
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			return retorno;
		}

	}
	
	public Map<String, Object> firmarDocumento(InputStream inDocument, NotaCreditoDao notaCreditoDao) {

		Map<String, Object> retorno = new HashMap<String, Object>();

		try {

			GestorLlavesDao gestorLlavesDao = new GestorLlavesDao();
			LlaveDao llaveDao = gestorLlavesDao.obtenerLlave(notaCreditoDao.getNumeroDocumento().toString());

			String alias = llaveDao.getAlias();
			String clavePrivateKey = llaveDao.getPin();

			// Cargamos el almacen de claves
			KeyStore ks = KeyStore.getInstance(Constantes.KEYSTORE_TYPE);

			// Obtenemos la clave privada, pues la necesitaremos para firmar.
			ks.load(new FileInputStream(
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaCertificado
							+ notaCreditoDao.getNumeroDocumento().toString().trim() + Constantes.extensionJks),
					clavePrivateKey.toCharArray());

			// get my private key
			PrivateKey privateKey = (PrivateKey) ks.getKey(alias, clavePrivateKey.toCharArray());

			// Añadimos el KeyInfo del certificado cuya clave privada usamos
			X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
			ByteArrayOutputStream signatureFile = new ByteArrayOutputStream();

			Document doc = buildDocument(inDocument);
			Node parentNode = addExtensionContent(doc, notaCreditoDao);
			doc.normalizeDocument();

			String idReference = "SignSUNAT";

			XMLSignatureFactory fac = XMLSignatureFactory.getInstance();

			Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA256, null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);

			// SignedInfo si = fac.newSignedInfo(
			// fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
			// (C14NMethodParameterSpec) null),
			// fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
			// Collections.singletonList(ref));

			// Inicio Prueba ----- VALIDA
			SignatureMethod signatureMethod = fac
					.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null);
			CanonicalizationMethod canonicalizationMethod = fac
					.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null);
			SignedInfo si = fac.newSignedInfo(canonicalizationMethod, signatureMethod, Collections.singletonList(ref));
			// Fin Prueba

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			List<X509Certificate> x509Content = new ArrayList<X509Certificate>();
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DOMSignContext dsc = new DOMSignContext(privateKey, doc.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);

			if (parentNode != null)
				dsc.setParent(parentNode);
			dsc.setDefaultNamespacePrefix("ds");
			signature.sign(dsc);

			String digestValue = Constantes.separadorNombreArchivo;
			Element elementParent = (Element) dsc.getParent();
			if (idReference != null && elementParent.getElementsByTagName("ds:Signature") != null) {
				Element elementSignature = (Element) elementParent.getElementsByTagName("ds:Signature").item(0);
				elementSignature.setAttribute("Id", idReference);

				NodeList nodeList = elementParent.getElementsByTagName("ds:DigestValue");
				for (int i = 0; i < nodeList.getLength(); i++) {
					digestValue = this.obtenerNodo(nodeList.item(i));
				}
			}

			outputDocToOutputStream(doc, signatureFile);
			signatureFile.close();

			retorno.put("signatureFile", signatureFile);
			retorno.put("digestValue", digestValue);

			return retorno;
		} catch (Exception e) {
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			return retorno;
		}

	}



	public Map<String, Object> firmarDocumento(InputStream inDocument, ComunicacionBajaDao comunicacionBajaDao) {
		Map<String, Object> retorno = new HashMap<String, Object>();

		try {

			GestorLlavesDao gestorLlavesDao = new GestorLlavesDao();
			LlaveDao llaveDao = gestorLlavesDao.obtenerLlave(comunicacionBajaDao.getNumeroRuc().toString());

			String alias = llaveDao.getAlias();
			String clavePrivateKey = llaveDao.getPin();

			// Cargamos el almacen de claves
			KeyStore ks = KeyStore.getInstance(Constantes.KEYSTORE_TYPE);

			// Obtenemos la clave privada, pues la necesitaremos para firmar.
			ks.load(new FileInputStream(
					ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaCertificado
							+ comunicacionBajaDao.getNumeroRuc().toString().trim() + Constantes.extensionJks),
					clavePrivateKey.toCharArray());

			// get my private key
			PrivateKey privateKey = (PrivateKey) ks.getKey(alias, clavePrivateKey.toCharArray());

			// Añadimos el KeyInfo del certificado cuya clave privada usamos
			X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
			ByteArrayOutputStream signatureFile = new ByteArrayOutputStream();

			Document doc = buildDocument(inDocument);
			Node parentNode = addExtensionContent(doc, comunicacionBajaDao);
			doc.normalizeDocument();

			String idReference = "SignSUNAT";

			XMLSignatureFactory fac = XMLSignatureFactory.getInstance();

			Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA256, null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);

			// SignedInfo si = fac.newSignedInfo(
			// fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
			// (C14NMethodParameterSpec) null),
			// fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
			// Collections.singletonList(ref));

			// Inicio Prueba ----- VALIDA
			SignatureMethod signatureMethod = fac
					.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null);
			CanonicalizationMethod canonicalizationMethod = fac
					.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null);
			SignedInfo si = fac.newSignedInfo(canonicalizationMethod, signatureMethod, Collections.singletonList(ref));
			// Fin Prueba

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			List<X509Certificate> x509Content = new ArrayList<X509Certificate>();
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DOMSignContext dsc = new DOMSignContext(privateKey, doc.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);

			if (parentNode != null)
				dsc.setParent(parentNode);
			dsc.setDefaultNamespacePrefix("ds");
			signature.sign(dsc);

			String digestValue = Constantes.separadorNombreArchivo;
			Element elementParent = (Element) dsc.getParent();
			if (idReference != null && elementParent.getElementsByTagName("ds:Signature") != null) {
				Element elementSignature = (Element) elementParent.getElementsByTagName("ds:Signature").item(0);
				elementSignature.setAttribute("Id", idReference);

				NodeList nodeList = elementParent.getElementsByTagName("ds:DigestValue");
				for (int i = 0; i < nodeList.getLength(); i++) {
					digestValue = this.obtenerNodo(nodeList.item(i));
				}
			}

			outputDocToOutputStream(doc, signatureFile);
			signatureFile.close();

			retorno.put("signatureFile", signatureFile);
			retorno.put("digestValue", digestValue);

			return retorno;
		} catch (Exception e) {
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			return retorno;
		}
	}



	public static void outputDocToOutputStream(Document doc, ByteArrayOutputStream signatureFile)
			throws javax.xml.transform.TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl",
				null);
		javax.xml.transform.Transformer transformer = factory.newTransformer();

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		// "ISO-8859-9"

		// transformer.setOutputProperty(OutputKeys.ENCODING, "UTF8");

		transformer.transform(new javax.xml.transform.dom.DOMSource(doc),
				new javax.xml.transform.stream.StreamResult(signatureFile));
	}

	private String obtenerNodo(Node node) throws Exception {
		StringBuffer valorClave = new StringBuffer();
		valorClave.setLength(0);

		Integer tamano = node.getChildNodes().getLength();

		for (int i = 0; i < tamano; i++) {
			Node c = node.getChildNodes().item(i);
			if (c.getNodeType() == Node.TEXT_NODE) {
				valorClave.append(c.getNodeValue());
			}
		}

		String nodo = valorClave.toString().trim();

		return nodo;
	}

	private Document buildDocument(InputStream inDocument) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Reader reader = new InputStreamReader(inDocument, "ISO8859_1");
		// Reader reader = new InputStreamReader(inDocument, "UTF8");
		Document doc = db.parse(new InputSource(reader));
		return doc;
	}
	
	
	private Node addExtensionContent(Document doc, NotaCreditoDao notaCreditoDao) {

		Element element = doc.getDocumentElement();

		element.setAttribute("xmlns:sac",
				"urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
		element.setAttribute("xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
		element.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
		element.removeAttribute("xmlns:cec");

		Node nodeInvoice = doc.getDocumentElement();
		Node extensions = doc.createElement("ext:UBLExtensions");

		Node extension = doc.createElement("ext:UBLExtension");
		Node content = doc.createElement("ext:ExtensionContent");
		extension.appendChild(content);
		extensions.appendChild(extension);

		Node primerNodo = nodeInvoice.getFirstChild();
		nodeInvoice.insertBefore(extensions, primerNodo);

		return content;
	}

	private Node addExtensionContent(Document doc, FacturaDao facturaDao) {

		Element element = doc.getDocumentElement();

		element.setAttribute("xmlns:sac",
				"urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
		element.setAttribute("xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
		element.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
		element.removeAttribute("xmlns:cec");

		Node nodeInvoice = doc.getDocumentElement();
		Node extensions = doc.createElement("ext:UBLExtensions");

		Node extension = doc.createElement("ext:UBLExtension");
		Node content = doc.createElement("ext:ExtensionContent");
		extension.appendChild(content);
		extensions.appendChild(extension);

		Node extensionAdditionalInformation = doc.createElement("ext:UBLExtension");
		Node contentAdditionalInformation = doc.createElement("ext:ExtensionContent");
		Node additionalInformation = doc.createElement("sac:AdditionalInformation");
		Node additionalMonetaryTotal = doc.createElement("sac:AdditionalMonetaryTotal");

		Node id = doc.createElement("cbc:ID");
		id.setTextContent("1001");

		Node payableAmount = doc.createElement("cbc:PayableAmount");
		((Element) payableAmount).setAttribute("currencyID", facturaDao.getMoneda());
		payableAmount.setTextContent(facturaDao.getTotalValorVentaOpGravadas().toString());

		additionalMonetaryTotal.appendChild(id);
		additionalMonetaryTotal.appendChild(payableAmount);

		additionalInformation.appendChild(additionalMonetaryTotal);
		contentAdditionalInformation.appendChild(additionalInformation);
		extensionAdditionalInformation.appendChild(contentAdditionalInformation);
		extensions.appendChild(extensionAdditionalInformation);

		Node primerNodo = nodeInvoice.getFirstChild();
		nodeInvoice.insertBefore(extensions, primerNodo);

		return content;
	}
	
	private Node addExtensionContent(Document doc, ComunicacionBajaDao comunicacionBajaDao) {
		Element element = doc.getDocumentElement();

		element.setAttribute("xmlns:sac",
				"urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
		element.setAttribute("xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
		element.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
		element.removeAttribute("xmlns:cec");

		Node nodeVoidedDocument = doc.getDocumentElement();
		Node extensions = doc.createElement("ext:UBLExtensions");

		Node extension = doc.createElement("ext:UBLExtension");
		Node content = doc.createElement("ext:ExtensionContent");
		extension.appendChild(content);
		extensions.appendChild(extension);


		Node primerNodo = nodeVoidedDocument.getFirstChild();
		nodeVoidedDocument.insertBefore(extensions, primerNodo);

		return content;
	}

}
