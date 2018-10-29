package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.ParametrosGlobales;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class GeneradorQr {

	public String generarCodigoQr(String nombreArhivo, String archivoSalida) throws Exception {
		// Declaracion de Variables
		String nombreNodo = "", tipoImpuesto = "", montoIgv = "", digest = "", rutaRetorno = "";
		// Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(nombreArhivo);
		try {
			String[] infoArchivo = nombreArhivo.split("\\-");
			String tipDocuArchivo = infoArchivo[1];

			// Se crea el documento a traves del archivo
			Document document = (Document) builder.build(xmlFile);

			// Se obtiene la raiz 'Invoice'
			Element rootNode = document.getRootElement();

			List<Element> elements = rootNode.getChildren();

			// Se obtiene los datos del emisor

			Element accountingSupplier = obtenerElemento(rootNode.getChildren(), "AccountingSupplierParty");

			String tipDocEmisor = obtenerElemento(accountingSupplier.getChildren(), "AdditionalAccountID")
					.getTextTrim();

			String rucEmisor = obtenerElemento(accountingSupplier.getChildren(), "CustomerAssignedAccountID")
					.getTextTrim();

			Element accountingCustomer = obtenerElemento(rootNode.getChildren(), "AccountingCustomerParty");

			String tipDocReceptor = obtenerElemento(accountingCustomer.getChildren(), "AdditionalAccountID")
					.getTextTrim();

			String rucReceptor = obtenerElemento(accountingCustomer.getChildren(), "CustomerAssignedAccountID")
					.getTextTrim();

			String SerieNroDocuCdp = obtenerElemento(rootNode.getChildren(), "ID").getTextTrim();

			String fechaEmision = obtenerElemento(rootNode.getChildren(), "IssueDate").getTextTrim();

			Integer procFirma = -1;
			List<Element> listaTaxTotal = rootNode.getChildren();
			for (Element elemento : listaTaxTotal) {
				nombreNodo = elemento.getName();
				if ("TaxTotal".equals(nombreNodo)) {

					// tipoImpuesto =
					// elemento.getChild("TaxSubtotal").getChild("TaxCategory").getChild("TaxScheme")
					// .getChildText("Name");

					// if (Constantes.CONSTANTE_NOMBRE_TRIBUTO_IGV.equals(tipoImpuesto)) {
					// montoIgv = elemento.getChild("TaxSubtotal").getChildText("TaxAmount");
					// }

					Element elementoTaxSubtotal = obtenerElemento(elemento.getChildren(), "TaxSubtotal");
					Element elementoTaxCategory = obtenerElemento(elementoTaxSubtotal.getChildren(), "TaxCategory");
					Element elementoTaxScheme = obtenerElemento(elementoTaxCategory.getChildren(), "TaxScheme");

					tipoImpuesto = obtenerElemento(elementoTaxScheme.getChildren(), "Name").getTextTrim();

					if (Constantes.CONSTANTE_NOMBRE_TRIBUTO_IGV.equals(tipoImpuesto)) {
						// montoIgv = elemento.getChild("TaxSubtotal").getChildText("TaxAmount");

						montoIgv = obtenerElemento(elementoTaxSubtotal.getChildren(), "TaxAmount").getTextTrim();
					}

				} else {
					if ("UBLExtensions".equals(nombreNodo)) {
						List<Element> listaElementosUbl = elemento.getChildren();
						for (Element elementoUbl : listaElementosUbl) {

							Element elementoExtensionContent = obtenerElemento(elementoUbl.getChildren(),
									"ExtensionContent");

							if ((procFirma < 0) && (elementoExtensionContent != null)) {

								Element elementoSignature = obtenerElemento(elementoExtensionContent.getChildren(),
										"Signature");
								Element elementoSignedInfo = obtenerElemento(elementoSignature.getChildren(),
										"SignedInfo");
								Element elementoReference = obtenerElemento(elementoSignedInfo.getChildren(),
										"Reference");

								digest = obtenerElemento(elementoReference.getChildren(), "DigestValue").getTextTrim();

								procFirma = 1;
							} else {
								continue;
							}
						}

					} else
						continue;
				}
			}

			String montoCdp = "0.00";
			if (Constantes.CONSTANTE_TIPO_DOCUMENTO_NDEBITO.equals(tipDocuArchivo)) {
				Element elementoRequestedMonTot = obtenerElemento(rootNode.getChildren(), "RequestedMonetaryTotal");
				montoCdp = obtenerElemento(elementoRequestedMonTot.getChildren(), "PayableAmount").getTextTrim();
			} else {
				Element elementoLegalMonetaryTotal = obtenerElemento(rootNode.getChildren(), "LegalMonetaryTotal");
				montoCdp = obtenerElemento(elementoLegalMonetaryTotal.getChildren(), "PayableAmount").getTextTrim();
			}

			StringBuilder codigo = new StringBuilder();
			codigo.setLength(0);
			codigo.append(tipDocEmisor).append("|").append(rucEmisor).append("|").append(tipDocReceptor).append("|")
					.append(rucReceptor).append("|").append(tipDocuArchivo).append("|").append(SerieNroDocuCdp)
					.append("|").append(fechaEmision).append("|").append(montoIgv).append("|").append(montoCdp)
					.append("|").append(digest);

			String cadena = codigo.toString();

			ByteArrayOutputStream out = QRCode.from(cadena).to(ImageType.PNG).stream();

			try {

				if (Constantes.CONSTANTE_TIPO_DOCUMENTO_FACTURA.equals(tipDocuArchivo))
					rutaRetorno = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImagenQr + archivoSalida + Constantes.extensionJpg;
				if (Constantes.CONSTANTE_TIPO_DOCUMENTO_BOLETA.equals(tipDocuArchivo))
					rutaRetorno = ParametrosGlobales.obtenerParametros().getRutaRaiz() + Constantes.rutaImagenQr +  archivoSalida + Constantes.extensionJpg;

				FileOutputStream fout = new FileOutputStream(new File(rutaRetorno));

				fout.write(out.toByteArray());

				fout.flush();
				fout.close();

			} catch (FileNotFoundException e) {
				System.out.println("ERROR: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("Código QR generado");

		} catch (IOException io) {
			// log.error(io.getMessage() + " - Causa: " + io.getCause());
			throw new Exception(io.getMessage() + " - Causa: " + io.getCause());
		} catch (JDOMException jdomex) {
			// log.error(jdomex.getMessage() + " - Causa: " + jdomex.getCause());
			throw new Exception(jdomex.getMessage() + " - Causa: " + jdomex.getCause());
		} catch (Exception e) {
			// log.error(e.getMessage() + " - Causa: " + e.getCause());
			System.out.println("Error :" + e.getMessage());
			throw new Exception(e.getMessage() + " - Causa: " + e.getCause());
		}

		return rutaRetorno;

	}

	public Element obtenerElemento(List<Element> listaElementos, String nombreElemento) {

		for (Element element : listaElementos) {
			if (element.getName().equals(nombreElemento)) {
				return element;
			}
		}

		return null;
	}
	
	public boolean existeImagenQr(String nombreArchivo) {
		File f = new File(nombreArchivo);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		
		return false;
	}

}
