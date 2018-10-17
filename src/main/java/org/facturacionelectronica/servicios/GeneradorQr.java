package org.facturacionelectronica.servicios;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.facturacionelectronica.util.Constantes;
//import org.facturacionelectronica.util.Constantes;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class GeneradorQr {

	
	public static final String generarCodigoQr(String nombreArhivo) throws Exception {
		// Declaracion de Variables
		String nombreNodo = "", tipoImpuesto = "", montoIgv = "", digest = "", rutaRetorno = "";
		// Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(nombreArhivo);
		try {
			String[] infoArchivo = nombreArhivo.split("\\-");
	    	String tipDocuArchivo = infoArchivo[1];
	    	
	    	//Se crea el documento a traves del archivo
	        Document document = (Document) builder.build( xmlFile );
	 
	        //Se obtiene la raiz 'Invoice'
	        Element rootNode = document.getRootElement();
	        
	        List<Element> elements = rootNode.getChildren();
	 
	        //Se obtiene los datos del emisor
	        
//	        Element accountingSupplier = rootNode.getChild("AccountingSupplierParty");

	        
	        
	        
	        
//	        String  tipDocEmisor = accountingSupplier.getChildText("AdditionalAccountID");
//	        String  rucEmisor = accountingSupplier.getChildText("CustomerAssignedAccountID");
	        
	        String  tipDocEmisor = "";
	        String  rucEmisor = "";
	        
	        for (Element element : elements) {
				if(element.getName().equals("AccountingSupplierParty")) {
//					tipDocEmisor
				}
			}
	        
	        Element accountingCustomer = rootNode.getChild("AccountingCustomerParty");
	        String tipDocReceptor = accountingCustomer.getChildText("AdditionalAccountID");
			String rucReceptor = accountingCustomer.getChildText("CustomerAssignedAccountID");
			String SerieNroDocuCdp = rootNode.getChildText("ID");
			
			String fechaEmision = rootNode.getChildText("IssueDate");

			Integer procFirma = -1;
			List<Element> listaTaxTotal = rootNode.getChildren();
			for (Element elemento : listaTaxTotal) {
				nombreNodo = elemento.getName();
				if ("TaxTotal".equals(nombreNodo)) {
					tipoImpuesto = elemento.getChild("TaxSubtotal").getChild("TaxCategory").getChild("TaxScheme")
							.getChildText("Name");
					if (Constantes.CONSTANTE_NOMBRE_TRIBUTO_IGV.equals(tipoImpuesto)) {
						montoIgv = elemento.getChild("TaxSubtotal").getChildText("TaxAmount");
					}
				} else {
					if ("UBLExtensions".equals(nombreNodo)) {
						List<Element> listaElementosUbl = elemento.getChildren();
						for (Element elementoUbl : listaElementosUbl) {
							if ((procFirma < 0)
									&& (elementoUbl.getChild("ExtensionContent").getChild("Signature") != null)) {
								digest = elementoUbl.getChild("ExtensionContent").getChild("Signature")
										.getChild("SignedInfo").getChild("Reference").getChildText("DigestValue");
								procFirma = 1;
							} else
								continue;
						}

					} else
						continue;
				}
			}

			String montoCdp = "0.00";
			if (Constantes.CONSTANTE_TIPO_DOCUMENTO_NDEBITO.equals(tipDocuArchivo))
				montoCdp = rootNode.getChild("RequestedMonetaryTotal").getChildText("PayableAmount");
			else
				montoCdp = rootNode.getChild("LegalMonetaryTotal").getChildText("PayableAmount");
				

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
					rutaRetorno = Constantes.rutaCompleta +  Constantes.rutaImagenQr + "imagenqr_Factura.jpg";
//				if (Constantes.CONSTANTE_TIPO_DOCUMENTO_BOLETA.equals(tipDocuArchivo))
//					rutaRetorno = comunesService.obtenerRutaTrabajo(CONSTANTE_ORIDAT) + "imagenqr_Boleta.jpg";
//				if (Constantes.CONSTANTE_TIPO_DOCUMENTO_NCREDITO.equals(tipDocuArchivo))
//					rutaRetorno = comunesService.obtenerRutaTrabajo(CONSTANTE_ORIDAT) + "imagenqr_NCredito.jpg";
//				if (Constantes.CONSTANTE_TIPO_DOCUMENTO_NDEBITO.equals(tipDocuArchivo))
//					rutaRetorno = comunesService.obtenerRutaTrabajo(CONSTANTE_ORIDAT) + "imagenqr_NDebito.jpg";

				FileOutputStream fout = new FileOutputStream(new File(rutaRetorno));

				fout.write(out.toByteArray());

				fout.flush();
				fout.close();

			} catch (FileNotFoundException e) {
			} catch (IOException e) {
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
}
