package org.facturacionelectronica.spike;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;
import org.facturacionelectronica.util.Constantes;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class GeneradorFacturaPrueba {

	public String generarComprobantePagoSunat(Factura factura, String rutaTrabajo) throws Exception {
		String retorno = Constantes.CONSTANTE_SITUACION_POR_GENERAR_XML;
		String tipoDocumento = factura.getCabeceraFactura().getTipoDocumentoFactura();
		String nombreArchivo = factura.getCabeceraFactura().getNumeroDocumento() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getTipoDocumentoFactura() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getSerie() + Constantes.separadorNombreArchivo
				+ factura.getCabeceraFactura().getNumeroCorrelativo();
		
		// formatoPlantillaXml(tipoComprobante, archivos, nombreArchivo, rutaTrabajo);
		formatoPlantillaXml(tipoDocumento, factura, nombreArchivo, rutaTrabajo);

		// validarSchemaXML(tipoComprobante, rutaTrabajo +
		// nombreArchivo + ".xml");
		// generarDocumentosService.validarXML(tipoComprobante,rutaTrabajo,nombreArchivo);

		// generarDocumentosService.firmarComprimirXml(txxxxBean.getNom_arch());

		return retorno;
	}

	// public void formatoPlantillaXml(String tipoDocumento, String[] archivos,
	// String nombreArchivo, String rutaTrabajo)
	// throws Exception {
	public void formatoPlantillaXml(String tipoDocumento, Factura factura, String nombreArchivo, String rutaTrabajo)
			throws Exception {

		Map<String, Object> root = null;
		String plantillaSeleccionada = "";

		// Formato de Facturas
		if (Constantes.CONSTANTE_TIPO_DOCUMENTO_FACTURA.equals(tipoDocumento)
				|| Constantes.CONSTANTE_TIPO_DOCUMENTO_BOLETA.equals(tipoDocumento)) {

			root = formatoFactura(factura, nombreArchivo);
			plantillaSeleccionada = "ConvertirFacturaXML.ftl";
		}

		File archivoFTL = new File(rutaTrabajo, plantillaSeleccionada);

		if (!archivoFTL.exists())
			throw new Exception("No existe la plantilla para el tipo documento a generar XML (Archivo FTL).");

		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(rutaTrabajo));
		cfg.setDefaultEncoding("ISO8859_1");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		Template temp = cfg.getTemplate(plantillaSeleccionada);
		StringBuilder rutaSalida = new StringBuilder();
		rutaSalida.setLength(0);
		rutaSalida.append(rutaTrabajo).append(nombreArchivo).append(".xml");
		OutputStream outputStream = new FileOutputStream(rutaSalida.toString());
		Writer out = new OutputStreamWriter(outputStream);
		temp.process(root, out);
		outputStream.close();

	}

	private Map<String, Object> formatoFactura(Factura facturaObj, String nombreArchivo) throws Exception {

		String[] registro;
		/* Cargando PArametros de Firma */
		String identificadorFirmaSwf = "SIGN";
		Random calcularRnd = new Random();
		Integer codigoFacturadorSwf = (int) (calcularRnd.nextDouble() * 1000000);

		String numRuc = facturaObj.getCabeceraFactura().getNumeroDocumento().toString();
		String razonSocial = facturaObj.getCabeceraFactura().getRazonSocial();
		String nombreComercial = facturaObj.getCabeceraFactura().getNombreComercial();
		String ubigeo = facturaObj.getCabeceraFactura().getCodigoUbigeo();
		String direccion = facturaObj.getCabeceraFactura().getDireccionCompleta();

		/* Leyendo Archivo de Cabecera del Comprobante */
		Map<String, Object> factura = null;

		// Desde Archivo Objeto Factura
		factura = new HashMap<String, Object>();
		factura.put("tipoOperacion", ""); // NUEVO
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String fechaEmisionFormateada = formatter.format(facturaObj.getCabeceraFactura().getFechaEmision());
		
		factura.put("fechaEmision", fechaEmisionFormateada);
		factura.put("fecEmision", fechaEmisionFormateada);
		factura.put("horEmision", "");
		
		factura.put("direccionUsuario", ""); // NUEVO
		factura.put("tipoDocumento", facturaObj.getCabeceraFactura().getTipoDocumentoCliente());
		factura.put("nroDocumento", facturaObj.getCabeceraFactura().getNumeroDocumentoCliente());
		factura.put("razonSocialUsuario", facturaObj.getCabeceraFactura().getRazonSocialCliente());
		factura.put("moneda", facturaObj.getCabeceraFactura().getMoneda());
		factura.put("descuentoGlobal", facturaObj.getCabeceraFactura().getTotalDescuentos()); // CAMBIO
		factura.put("sumaOtrosCargos", "");
		factura.put("totalDescuento", facturaObj.getCabeceraFactura().getTotalDescuentos()); // CAMBIO
		factura.put("montoOperGravadas", facturaObj.getCabeceraFactura().getTotalValorVentaOpGravadas());
		factura.put("montoOperInafectas", facturaObj.getCabeceraFactura().getTotalValorVentaOpInafecta());
		factura.put("montoOperExoneradas", facturaObj.getCabeceraFactura().getTotalValorVentaOpExoneradas());
		factura.put("sumaIgv", facturaObj.getCabeceraFactura().getSumatoriaIGV());
		factura.put("sumaIsc", facturaObj.getCabeceraFactura().getSumatoriaISC());
		factura.put("sumaOtros", "");
		factura.put("sumaImporteVenta", facturaObj.getCabeceraFactura().getImporteTotalVenta());

		// Valores Automaticos
		factura.put("ublVersionIdSwf", Constantes.CONSTANTE_UBL_VERSION);
		factura.put("CustomizationIdSwf", Constantes.CONSTANTE_CUSTOMIZATION_ID);
		factura.put("nroCdpSwf", facturaObj.getCabeceraFactura().getSerie() + Constantes.caracterSeparadorArchivo
				+ facturaObj.getCabeceraFactura().getNumeroCorrelativo()); // NUEVO
		factura.put("tipCdpSwf", "0101"); // NUEVO
		factura.put("tipOperacion", "0101"); // NUEVO
		factura.put("codLocalEmisor", ""); // NUEVO
		
		factura.put("nroRucEmisorSwf", numRuc);
		factura.put("tipDocuEmisorSwf", Constantes.CONSTANTE_TIPO_DOCU_EMISOR);
		factura.put("nombreComercialSwf", nombreComercial);
		factura.put("urbanizaSwf", "");
		
		factura.put("razonSocialSwf", razonSocial);
		factura.put("ubigeoDomFiscalSwf", ubigeo);
		factura.put("direccionDomFiscalSwf", direccion);
		factura.put("paisDomFiscalSwf", Constantes.CONSTANTE_CODIGO_PAIS);
		factura.put("codigoMontoDescuentosSwf", Constantes.CONSTANTE_CODIGO_MONTO_DSCTO);
		factura.put("codigoMontoOperGravadasSwf", Constantes.CONSTANTE_CODIGO_OPER_GRAVADA);
		factura.put("codigoMontoOperInafectasSwf", Constantes.CONSTANTE_CODIGO_OPER_INAFECTA);
		factura.put("codigoMontoOperExoneradasSwf", Constantes.CONSTANTE_CODIGO_OPER_EXONERADA);
		factura.put("idIgv", Constantes.CONSTANTE_ID_IVG);
		factura.put("codIgv", Constantes.CONSTANTE_COD_IVG);
		factura.put("codExtIgv", Constantes.CONSTANTE_COD_EXT_IVG);
		factura.put("idIsc", Constantes.CONSTANTE_ID_ISC);
		factura.put("codIsc", Constantes.CONSTANTE_COD_ISC);
		factura.put("codExtIsc", Constantes.CONSTANTE_COD_EXT_ISC);
		factura.put("idOtr", Constantes.CONSTANTE_ID_OTR);
		factura.put("codOtr", Constantes.CONSTANTE_COD_OTR);
		factura.put("codExtOtr", Constantes.CONSTANTE_COD_EXT_OTR);
		factura.put("tipoCodigoMonedaSwf", Constantes.CONSTANTE_TIPO_CODIGO_MONEDA_ONEROSO);
		factura.put("identificadorFacturadorSwf",
				Constantes.CONSTANTE_INFO_SFS_SUNAT + Constantes.CONSTANTE_VERSION_SFS);
		factura.put("codigoFacturadorSwf", codigoFacturadorSwf.toString());
		factura.put("identificadorFirmaSwf", identificadorFirmaSwf);

		/* Leyendo Archivo de Detalle del Comprobante */
		List<Map<String, Object>> listaDetalle = new ArrayList<Map<String, Object>>();
		Map<String, Object> detalle = null;
		int contador = 0;

		for (DetalleFactura detalleFactura : facturaObj.getDetalleFactura()) {

			detalle = new HashMap<String, Object>();
			contador++;

			detalle.put("unidadMedida", detalleFactura.getUnidadMedida());
			detalle.put("cantItem", detalleFactura.getCantidad());
			detalle.put("codiProducto", detalleFactura.getCodigoItem());
			detalle.put("codiSunat", "");
			detalle.put("desItem", detalleFactura.getDescripcionItem());
			detalle.put("valorUnitario", detalleFactura.getValorUnitarioPorItem());
			detalle.put("descuentoItem", detalleFactura.getImpuestoUnitarioPorItem());
			detalle.put("montoIgvItem", detalleFactura.getImpuestoPorItem());
			detalle.put("afectaIgvItem", "");
			detalle.put("montoIscItem", "");
			detalle.put("tipoSistemaIsc", "");
			detalle.put("precioVentaUnitarioItem", detalleFactura.getPrecioVentaUnitarioPorItem());
			detalle.put("valorVentaItem", detalleFactura.getValorVentaPorItem());
			// Valores Automaticos
			detalle.put("lineaSwf", contador);
			detalle.put("tipoCodiMoneGratiSwf", Constantes.CONSTANTE_TIPO_CODIGO_MONEDA_GRATUITO);
		}

		listaDetalle.add(detalle);

		factura.put("listaDetalle", listaDetalle);

		/* Agregar informacion adicional */
		formatoComunes(factura, facturaObj);

		return factura;

	}

	private void formatoComunes(Map<String, Object> factura, Factura facturaObj) {

		/* Leyendo Archivo de Leyendas del Comprobante */
		List<Map<String, Object>> listaLeyendas = new ArrayList<Map<String, Object>>();
		Map<String, Object> leyendas = null;

		leyendas = new HashMap<String, Object>();
		leyendas.put("codigo", "1000");
		leyendas.put("descripcion", facturaObj.getCabeceraFactura().getLeyenda());

		listaLeyendas.add(leyendas);

		factura.put("listaLeyendas", listaLeyendas);

	}

}
