package org.facturacionelectronica.servicios;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.cert.X509Certificate;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.facturacionelectronica.firma.TxxxyBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//import org.apache.commons.codec.binary.Base64;

public class GeneradorFirma {
	
	private TxxxyDAO txxxyDAO;

	private Map<String, Object> firmarDocumento(InputStream inDocument) throws Exception {
		Map<String, Object> retorno = new HashMap<String, Object>();
		// Buscar Parametro de Clave passPrivateKey
		TxxxyBean paramtroBean = new TxxxyBean();
		String param = "", clavePrivateKey = "";
		paramtroBean = new TxxxyBean();
		paramtroBean.setId_para("PARASIST");
		List<TxxxyBean> listaParametros = null;
		listaParametros = txxxyDAO.consultarParametroById(paramtroBean);
		if (listaParametros.size() > 0) {
			for (TxxxyBean parametro : listaParametros) {
				param = parametro.getVal_para();
				if ("PRKCRT".equals(parametro.getCod_para()))
					clavePrivateKey = this.Desencriptar(param);
			}
		}

		// Cargamos el almacen de claves
		KeyStore ks = KeyStore.getInstance(Constantes.KEYSTORE_TYPE);
		// Obtenemos la clave privada, pues la necesitaremos para firmar.
		ks.load(new FileInputStream(obtenerRutaTrabajo(Constantes.CONSTANTE_ALMCERT) + "FacturadorKey.jks"),
				Constantes.KEYSTORE_PASSWORD.toCharArray());

		// get my private key
		PrivateKey privateKey = (PrivateKey) ks.getKey(Constantes.PRIVATE_KEY_ALIAS, clavePrivateKey.toCharArray());

		// Añadimos el KeyInfo del certificado cuya clave privada usamos
		X509Certificate cert = (X509Certificate) ks.getCertificate(Constantes.PRIVATE_KEY_ALIAS);
		ByteArrayOutputStream signatureFile = new ByteArrayOutputStream();

		Document doc = buildDocument(inDocument);
		Node parentNode = addExtensionContent(doc);

		String idReference = "SignSUNAT";

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance();

		Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
				Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null,
				null);

		SignedInfo si = fac.newSignedInfo(
				fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
				fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

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

		String digestValue = "-";
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
	}
	
	public String obtenerRutaTrabajo(String rutaTrabajoBusqueda) throws Exception{
//		log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Inicio");
		StringBuilder rutaDirectorio = new StringBuilder();
		rutaDirectorio.setLength(0);
		
		TxxxyBean txxxyBean = new TxxxyBean();
		txxxyBean.setId_para("PARASIST");
		txxxyBean.setCod_para("RUTSOL");
		List<TxxxyBean> parametroRuta =  txxxyDAO.consultarParametro(txxxyBean);
		
		if (parametroRuta.size() > 0)
			rutaDirectorio.append(parametroRuta.get(0).getVal_para());
		else
			rutaDirectorio.append("");
		
//		log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Ruta Base: " + rutaDirectorio.toString());
				
		if(Constantes.CONSTANTE_TEMP.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/TEMP/");
		if(Constantes.CONSTANTE_PARSE.equals(rutaTrabajoBusqueda))	
			rutaDirectorio.append( "/sunat_archivos/sfs/PARSE/");
		if(Constantes.CONSTANTE_DATA.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/DATA/");				
		if(Constantes.CONSTANTE_CERT.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/CERT/");					
		if(Constantes.CONSTANTE_ENVIO.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/ENVIO/");						
		if(Constantes.CONSTANTE_RPTA.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/RPTA/");			
		if(Constantes.CONSTANTE_FIRMA.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/FIRMA/");			
		if(Constantes.CONSTANTE_REPO.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/REPO/");			
		if(Constantes.CONSTANTE_FORMATO.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/VALI/");			
		if(Constantes.CONSTANTE_ORIDAT.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/ORIDAT/");			
		if(Constantes.CONSTANTE_ALMCERT.equals(rutaTrabajoBusqueda))
			rutaDirectorio.append("/sunat_archivos/sfs/ALMCERT/");
		
//		log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Ruta Completa: " + rutaDirectorio.toString());
		
		File farchivo = new File(rutaDirectorio.toString());
		if(!farchivo.exists()||!farchivo.isDirectory())
			throw new Exception("No se encuentra la ruta para los archivos y directorios del facturador"); 
						
//		log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Final");
		return rutaDirectorio.toString();
		
	}
	
	public static void outputDocToOutputStream(Document doc, ByteArrayOutputStream signatureFile) throws TransformerException {
        TransformerFactory factory	   	   = TransformerFactory.newInstance();
        Transformer		   transformer	   = factory.newTransformer();
        
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        //"ISO-8859-9"
        transformer.transform(new DOMSource(doc), new StreamResult(signatureFile));		
	}
	
	private String obtenerNodo(Node node) throws Exception {
	    StringBuffer valorClave = new StringBuffer();
	    valorClave.setLength(0);
	    
	    Integer tamano = node.getChildNodes().getLength();
	    
	    for (int i = 0; i < tamano; i ++) {
	      Node c = node.getChildNodes().item(i);
	      if (c.getNodeType() == Node.TEXT_NODE) {
	    	  valorClave.append(c.getNodeValue());
	      }
	    }
	    
	    String nodo = valorClave.toString().trim(); 
	    
	    return nodo;
	  }

	public String Desencriptar(String textoEncriptado) throws Exception {
		String secretKey = "qualityinfosolutions"; // llave para desenciptar datos
		String base64EncryptedString = "";

		byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		SecretKey key = new SecretKeySpec(keyBytes, "DESede");

		Cipher decipher = Cipher.getInstance("DESede");
		decipher.init(Cipher.DECRYPT_MODE, key);

		byte[] plainText = decipher.doFinal(message);

		base64EncryptedString = new String(plainText, "UTF-8");

		return base64EncryptedString;
	}
	
	private Node addExtensionContent(Document doc) {
		NodeList nodeList = doc.getDocumentElement().getElementsByTagNameNS("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2", "UBLExtensions");
		Node extensions = nodeList.item(0);
		extensions.appendChild(doc.createTextNode("\n\t\t"));
		Node extension = doc.createElement("ext:UBLExtension");
		extension.appendChild(doc.createTextNode("\n\t\t\t"));
		Node content = doc.createElement("ext:ExtensionContent");
		extension.appendChild(content);
		extension.appendChild(doc.createTextNode("\n\t\t"));
		extensions.appendChild(extension);
		extensions.appendChild(doc.createTextNode("\n\t"));
		return content;
    }  
	
	
	  private Document buildDocument(InputStream inDocument) throws Exception {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
		    dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Reader reader = new InputStreamReader(inDocument,"ISO8859_1");
		    Document doc = db.parse(new InputSource(reader));
		    return doc;
	    }

	public interface TxxxyDAO {

		public List<TxxxyBean> consultarParametroById(TxxxyBean txxxyBean);

		public List<TxxxyBean> consultarParametro(TxxxyBean txxxyBean);

		public void insertarParametro(TxxxyBean txxxyBean);

		public void actualizarParametro(TxxxyBean txxxyBean);

	}
	
		
	
	public final class Constantes {

		public static final String CONSTANTE_TEMP    = "TEMP";
		public static final String CONSTANTE_PARSE   = "PARS";
		public static final String CONSTANTE_DATA    = "DATA";
		public static final String CONSTANTE_CERT    = "CERT";
		public static final String CONSTANTE_ENVIO   = "ENVI";
		public static final String CONSTANTE_RPTA    = "RPTA";
		public static final String CONSTANTE_FIRMA   = "FIRM";
		public static final String CONSTANTE_REPO    = "REPO";
		public static final String CONSTANTE_FORMATO = "VALI";
		public static final String CONSTANTE_ORIDAT  = "ORID";
		public static final String CONSTANTE_ALMCERT = "ALMC";	
		public static final String CONSTANTE_SITUACION_POR_GENERAR_XML = "01";	
		public static final String CONSTANTE_SITUACION_XML_GENERADO = "02";
		public static final String CONSTANTE_SITUACION_ENVIADO_ACEPTADO = "03";
		public static final String CONSTANTE_SITUACION_ENVIADO_ACEPTADO_OBSERVACIONES = "04";
		public static final String CONSTANTE_SITUACION_ENVIADO_ANULADO = "05";
		public static final String CONSTANTE_SITUACION_CON_ERRORES = "06";
		public static final String CONSTANTE_SITUACION_XML_VALIDAR = "07";
		public static final String CONSTANTE_SITUACION_ENVIADO_POR_PROCESAR = "08";
		public static final String CONSTANTE_SITUACION_ENVIADO_PROCESANDO = "09";
		public static final String CONSTANTE_SITUACION_ENVIADO_RECHAZADO = "10";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_BAJA = ".CBA";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_CABE = ".CAB";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_DETA = ".DET";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_RELA = ".REL";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_ACAB = ".ACA";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_ADET = ".ADE";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_LEYE = ".LEY";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_NOTA = ".NOT";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_JSON = ".JSON";
		public static final String CONSTANTE_SUFIJO_ARCHIVO_XML = ".XML";
		public static final String CONSTANTE_TIPO_DOCUMENTO_FACTURA  = "01";
		public static final String CONSTANTE_TIPO_DOCUMENTO_BOLETA   = "03";
		public static final String CONSTANTE_TIPO_DOCUMENTO_NCREDITO = "07";
		public static final String CONSTANTE_TIPO_DOCUMENTO_NDEBITO  = "08";
		public static final String CONSTANTE_TIPO_DOCUMENTO_RBAJAS  = "RA";
		public static final String CONSTANTE_TIP_ARCH_TEXT = "TXT";
		public static final String CONSTANTE_TIP_ARCH_JSON = "JSON";
		public static final String CONSTANTE_TIP_ARCH_XML = "XML";
		public static final String KEYSTORE_TYPE		  = "JKS";
		public static final String PRIVATE_KEY_ALIAS	  = "certContribuyente";
		public static final String KEYSTORE_PASSWORD	  = "SuN@TF4CT";
		public static final String CONSTANTE_CODIGO_MONTO_DSCTO = "2005";
		public static final String CONSTANTE_CODIGO_OPER_GRATUITA = "1004";
		public static final String CONSTANTE_CODIGO_OPER_EXONERADA = "1003";
		public static final String CONSTANTE_CODIGO_OPER_INAFECTA = "1002";
		public static final String CONSTANTE_CODIGO_OPER_GRAVADA = "1001";
		public static final String CONSTANTE_TIPO_DOCU_EMISOR = "6";
		public static final String CONSTANTE_UBL_VERSION = "2.0";
		public static final String CONSTANTE_CUSTOMIZATION_ID = "1.0";
		public static final String CONSTANTE_TIPO_PRECIO = "01";
		public static final String CONSTANTE_AFECTACION_IGV = "10";	
		public static final String CONSTANTE_CODIGO_TRIBUTO_IGV = "1000";
		public static final String CONSTANTE_NOMBRE_TRIBUTO_IGV = "IGV";
		public static final String CONSTANTE_CODIGO_INTER_TRIBUTO = "VAT";
		public static final String CONSTANTE_CODIGO_PAIS = "PER";
		public static final String CONSTANTE_TIPO_FUNCION_AUTO = "01";
		public static final String CONSTANTE_ID_IVG = "1000";
		public static final String CONSTANTE_COD_IVG = "IGV";
		public static final String CONSTANTE_COD_EXT_IVG = "VAT";
		public static final String CONSTANTE_ID_ISC = "2000";
		public static final String CONSTANTE_COD_ISC = "ISC";
		public static final String CONSTANTE_COD_EXT_ISC = "EXC";
		public static final String CONSTANTE_ID_OTR = "9999";
		public static final String CONSTANTE_COD_OTR = "OTROS";
		public static final String CONSTANTE_COD_EXT_OTR = "OTH";
		public static final String CONSTANTE_ID_PER = "2001";
		public static final String CONSTANTE_TIPO_CODIGO_MONEDA_ONEROSO = "01";
		public static final String CONSTANTE_TIPO_CODIGO_MONEDA_GRATUITO = "02";
		public static final String CONSTANTE_TIPO_CODIGO_PLACA = "7000";
		public static final String CONSTANTE_COD_MONEDA_SOLES = "PEN";
		public static final String CONSTANTE_EXTENSION_BAJAS = "CBA";
		public static final String CONSTANTE_EXTENSION_NOTAS = "NOT";
		public static final String CONSTANTE_EXTENSION_CDP = "CAB";
		public static final String CONSTANTE_EXTENSION_JSON = "JSON";
		public static final String CONSTANTE_EXTENSION_XML = "XML";
		public static final String CONSTANTE_EXTENSION_COMPL_DETA = "DET";
		public static final String CONSTANTE_EXTENSION_COMPL_RELA = "REL";
		public static final String CONSTANTE_EXTENSION_COMPL_ACAB = "ACA";
		public static final String CONSTANTE_EXTENSION_COMPL_ADET = "ADE";
		public static final String CONSTANTE_EXTENSION_COMPL_LEYE = "LEY";
		public static final String CONSTANTE_VERSION_SFS = "1.0.5";
		public static final String CONSTANTE_INFO_SFS_SUNAT = "Elaborado por Sistema de Emision Electronica Facturador SUNAT (SEE-SFS) ";
		public static final String CONSTANTE_ELEMENTO_LIBRERIAS = "filesLibraries";
		public static final String CONSTANTE_ELEMENTO_COMPONENTE = "filesWebComponents";
		public static final String CONSTANTE_ELEMENTO_FUENTES = "filesSources";
		public static final String CONSTANTE_URL_PRUEBA = "www.sunat.gob.pe";
		public static final String CONSTANTE_CODIGO_EXITO_CONSULTA_CDR = "0004";
//		public static final Integer CONSTANTE_CODIGO_ENVIO_PREVIO = 1033;
	}
	
	
}
