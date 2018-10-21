package org.facturacionelectronica.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.util.Constantes;

public class ManejadorArchivos {

	public List<String> leerArchivo(String ruta) {

		List<String> lineasArchivo = new ArrayList<String>();

		try {

			File f = new File(ruta);

			BufferedReader b = new BufferedReader(new FileReader(f));

			String readLine = "";

			System.out.println("Leyendo archivo: " + ruta);

			while ((readLine = b.readLine()) != null) {
				System.out.println(readLine);
				lineasArchivo.add(readLine);
			}

			b.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lineasArchivo;

	}

	public List<String> leerCarpeta(String rutaCarpeta) {

		File folder = new File(rutaCarpeta);

		List<String> lineasCarpetaImportacion = new ArrayList<String>();

		for (final File fileEntry : folder.listFiles()) {

			if (esArchivoValido(fileEntry.getName())) {
				List<String> lineasArchivo = this
						.leerArchivo(Constantes.rutaCompleta + Constantes.rutaImportar + fileEntry.getName());

				for (String lineaArchivo : lineasArchivo) {
					lineasCarpetaImportacion.add(lineaArchivo);
				}

				eliminarArchivo(fileEntry);
			}
		}
		return lineasCarpetaImportacion;
	}

	public boolean eliminarArchivo(File archivo) {
		try {
			boolean respuesta = archivo.delete();
			System.out.println("Archivo eliminado " + respuesta);
			return respuesta;
		} catch (Exception ex) {
			System.out.println("Ocurrió un problema al intentar borrar archivo");
			return false;
		}
	}

	private boolean esArchivoValido(String name) {

		String pattern = "[0-9]{11}-[0-9]{1}";

		return true;
	}

	public List<CabeceraFactura> generarCabeceraFacturaModificado(List<String> lineasArchivo) throws ParseException {

		String idFacturaAux = "";
		List<CabeceraFactura> listaCabeceraFacturas = new ArrayList<CabeceraFactura>();
		CabeceraFactura cabeceraFactura = new CabeceraFactura();

		String linea = lineasArchivo.get(0);

		String[] arregloFactura = linea.split("\\|");

		idFacturaAux = arregloFactura[0];

		if (!verificarRepiteFactura(listaCabeceraFacturas, idFacturaAux)) {
			cabeceraFactura = new CabeceraFactura();
			cabeceraFactura.setIdFactura(arregloFactura[0]);
			cabeceraFactura.setIdCustomization(arregloFactura[1]);

			String fechaCadena = arregloFactura[2];
			Date fechaEmision = obtenerFecha(fechaCadena);

			cabeceraFactura.setFechaEmision(fechaEmision);
			cabeceraFactura.setFirmaDigital(arregloFactura[3]);
			cabeceraFactura.setRazonSocial(arregloFactura[4]);
			cabeceraFactura.setNombreComercial(arregloFactura[5]);
			cabeceraFactura.setCodigoUbigeo(arregloFactura[6]);
			cabeceraFactura.setDireccionCompleta(arregloFactura[7]);
			cabeceraFactura.setUrbanizacion(arregloFactura[8]);
			cabeceraFactura.setProvincia(arregloFactura[9]);
			cabeceraFactura.setDepartamento(arregloFactura[10]);
			cabeceraFactura.setDistrito(arregloFactura[11]);
			cabeceraFactura.setCodigoPais(arregloFactura[12]);
			cabeceraFactura.setNumeroDocumento(new BigInteger(arregloFactura[13]));
			cabeceraFactura.setTipoDocumento(new Integer(arregloFactura[14]));
			cabeceraFactura.setTipoDocumentoFactura(arregloFactura[15]);
			cabeceraFactura.setSerie(arregloFactura[16]);
			cabeceraFactura.setNumeroCorrelativo(arregloFactura[17]);
			cabeceraFactura.setNumeroDocumentoCliente(arregloFactura[18]);
			cabeceraFactura.setTipoDocumentoCliente(arregloFactura[19]);
			cabeceraFactura.setRazonSocialCliente(arregloFactura[20]);

			if (!arregloFactura[21].isEmpty())
				cabeceraFactura.setTotalValorVentaOpGravadas(new BigDecimal(arregloFactura[21]));

			if (!arregloFactura[22].isEmpty())
				cabeceraFactura.setTotalValorVentaOpInafecta(new BigDecimal(arregloFactura[22]));

			if (!arregloFactura[23].isEmpty())
				cabeceraFactura.setTotalValorVentaOpExoneradas(new BigDecimal(arregloFactura[23]));

			if (!arregloFactura[24].isEmpty())
				cabeceraFactura.setTotalValorVentaOpGratuitas(new BigDecimal(arregloFactura[24]));

			if (!arregloFactura[25].isEmpty())
				cabeceraFactura.setSumatoriaIGV(new BigDecimal(arregloFactura[25]));

			if (!arregloFactura[26].isEmpty())
				cabeceraFactura.setSumatoriaISC(new BigDecimal(arregloFactura[26]));

			if (!arregloFactura[27].isEmpty())
				cabeceraFactura.setTotalDescuentos(new BigDecimal(arregloFactura[27]));

			if (!arregloFactura[28].isEmpty())
				cabeceraFactura.setImporteTotalVenta(new BigDecimal(arregloFactura[28]));

			// cabeceraFactura.setLeyenda(arregloFactura[29]);

			listaCabeceraFacturas.add(cabeceraFactura);
		}

		return listaCabeceraFacturas;
	}

	public List<DetalleFactura> generarDetalleFacturaModificado(List<String> lineasArchivo, String idFactura) {
		// String idFacturaAux = "";
		List<DetalleFactura> listaDetalleFactura = new ArrayList<DetalleFactura>();
		DetalleFactura detalleFactura = new DetalleFactura();

		String linea = "";

		for (int i = 1; i < lineasArchivo.size(); i++) {

			linea = lineasArchivo.get(i);

			String[] arregloFactura = linea.split("\\|");

			// idFacturaAux = arregloFactura[0];
			// if (idFacturaAux.equals(idFactura)) {

			detalleFactura = new DetalleFactura();

			if (!arregloFactura[0].isEmpty())
				detalleFactura.setNumeroOrden(Integer.parseInt(arregloFactura[0]));

			detalleFactura.setUnidadMedida(arregloFactura[1]);
			detalleFactura.setCodigoItem(arregloFactura[2]);
			detalleFactura.setDescripcionItem(arregloFactura[3]);

			if (!arregloFactura[4].isEmpty())
				detalleFactura.setCantidad(new BigDecimal(arregloFactura[4]));

			if (!arregloFactura[5].isEmpty())
				detalleFactura.setValorUnitarioPorItem(new BigDecimal(arregloFactura[5]));

			if (!arregloFactura[6].isEmpty())
				detalleFactura.setPrecioVentaUnitarioPorItem(new BigDecimal(arregloFactura[6]));

			if (!arregloFactura[7].isEmpty())
				detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[7]));// ESTE CAMPO CAMBIA A
																						// DESCUENTO

			if (!arregloFactura[8].isEmpty())
				detalleFactura.setValorVentaBruto(new BigDecimal(arregloFactura[8]));

			if (!arregloFactura[9].isEmpty())
				detalleFactura.setValorVentaPorItem(new BigDecimal(arregloFactura[9]));

			if (!arregloFactura[10].isEmpty())
				detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[10]));

			listaDetalleFactura.add(detalleFactura);

			// }

		}

		return listaDetalleFactura;
	}

	// ----------------------------------------------Estructura de archivos
	// Inicial------------------------------------------------------------

	public List<CabeceraFactura> generarCabeceraFactura(List<String> lineasArchivo) throws ParseException {

		String idFacturaAux = "";
		List<CabeceraFactura> listaCabeceraFacturas = new ArrayList<CabeceraFactura>();
		CabeceraFactura cabeceraFactura = new CabeceraFactura();

		for (String linea : lineasArchivo) {

			String[] arregloFactura = linea.split("\\|");

			idFacturaAux = arregloFactura[0];

			if (!verificarRepiteFactura(listaCabeceraFacturas, idFacturaAux)) {

				// if(esCabeceraValida())

				cabeceraFactura = new CabeceraFactura();
				cabeceraFactura.setIdFactura(arregloFactura[0]);
				cabeceraFactura.setIdCustomization(arregloFactura[1]);

				String fechaCadena = arregloFactura[2];
				Date fechaEmision = obtenerFecha(fechaCadena);

				cabeceraFactura.setFechaEmision(fechaEmision);
				cabeceraFactura.setFirmaDigital(arregloFactura[3]);
				cabeceraFactura.setRazonSocial(arregloFactura[4]);
				cabeceraFactura.setNombreComercial(arregloFactura[5]);
				cabeceraFactura.setCodigoUbigeo(arregloFactura[6]);
				cabeceraFactura.setDireccionCompleta(arregloFactura[7]);
				cabeceraFactura.setUrbanizacion(arregloFactura[8]);
				cabeceraFactura.setProvincia(arregloFactura[9]);
				cabeceraFactura.setDepartamento(arregloFactura[10]);
				cabeceraFactura.setDistrito(arregloFactura[11]);
				cabeceraFactura.setCodigoPais(arregloFactura[12]);
				cabeceraFactura.setNumeroDocumento(new BigInteger(arregloFactura[13]));
				cabeceraFactura.setTipoDocumento(new Integer(arregloFactura[14]));
				cabeceraFactura.setTipoDocumentoFactura(arregloFactura[15]);
				cabeceraFactura.setSerie(arregloFactura[16]);
				cabeceraFactura.setNumeroCorrelativo(arregloFactura[17]);
				cabeceraFactura.setNumeroDocumentoCliente(arregloFactura[18]);
				cabeceraFactura.setTipoDocumentoCliente(arregloFactura[19]);
				cabeceraFactura.setRazonSocialCliente(arregloFactura[20]);

				if (!arregloFactura[21].isEmpty())
					cabeceraFactura.setTotalValorVentaOpGravadas(new BigDecimal(arregloFactura[21]));

				if (!arregloFactura[22].isEmpty())
					cabeceraFactura.setTotalValorVentaOpInafecta(new BigDecimal(arregloFactura[22]));

				if (!arregloFactura[23].isEmpty())
					cabeceraFactura.setTotalValorVentaOpExoneradas(new BigDecimal(arregloFactura[23]));

				if (!arregloFactura[24].isEmpty())
					cabeceraFactura.setTotalValorVentaOpGratuitas(new BigDecimal(arregloFactura[24]));

				if (!arregloFactura[25].isEmpty())
					cabeceraFactura.setSumatoriaIGV(new BigDecimal(arregloFactura[25]));

				if (!arregloFactura[26].isEmpty())
					cabeceraFactura.setSumatoriaISC(new BigDecimal(arregloFactura[26]));

				if (!arregloFactura[27].isEmpty())
					cabeceraFactura.setTotalDescuentos(new BigDecimal(arregloFactura[27]));

				if (!arregloFactura[28].isEmpty())
					cabeceraFactura.setImporteTotalVenta(new BigDecimal(arregloFactura[28]));

				cabeceraFactura.setLeyenda(arregloFactura[29]);

				listaCabeceraFacturas.add(cabeceraFactura);
			}

		}

		return listaCabeceraFacturas;
	}

	public List<DetalleFactura> generarDetalleFactura(List<String> lineasArchivo, String idFactura) {
		String idFacturaAux = "";
		List<DetalleFactura> listaDetalleFactura = new ArrayList<DetalleFactura>();
		DetalleFactura detalleFactura = new DetalleFactura();

		for (String linea : lineasArchivo) {

			String[] arregloFactura = linea.split("\\|");

			idFacturaAux = arregloFactura[0];

			if (idFacturaAux.equals(idFactura)) {

				detalleFactura = new DetalleFactura();

				if (!arregloFactura[30].isEmpty())
					detalleFactura.setNumeroOrden(Integer.parseInt(arregloFactura[30]));

				detalleFactura.setUnidadMedida(arregloFactura[31]);
				detalleFactura.setCodigoItem(arregloFactura[32]);
				detalleFactura.setDescripcionItem(arregloFactura[33]);

				if (!arregloFactura[34].isEmpty())
					detalleFactura.setCantidad(new BigDecimal(arregloFactura[34]));

				if (!arregloFactura[35].isEmpty())
					detalleFactura.setValorUnitarioPorItem(new BigDecimal(arregloFactura[35]));

				if (!arregloFactura[36].isEmpty())
					detalleFactura.setPrecioVentaUnitarioPorItem(new BigDecimal(arregloFactura[36]));

				if (!arregloFactura[37].isEmpty())
					detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[37]));// ESTE CAMPO CAMBIA A
																							// DESCUENTO

				if (!arregloFactura[38].isEmpty())
					detalleFactura.setValorVentaBruto(new BigDecimal(arregloFactura[38]));

				if (!arregloFactura[39].isEmpty())
					detalleFactura.setValorVentaPorItem(new BigDecimal(arregloFactura[39]));

				if (!arregloFactura[40].isEmpty())
					detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[40]));

				listaDetalleFactura.add(detalleFactura);

			}

		}

		return listaDetalleFactura;
	}

	public Date obtenerFecha(String fechaCadena) {
		DateFormat formatter;
		Date fechaEmision = null;

		formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			fechaEmision = formatter.parse(fechaCadena);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fechaEmision;
	}

	public boolean verificarRepiteFactura(List<CabeceraFactura> listaCabeceraFacturas, String idFactura) {

		for (CabeceraFactura cabeceraFactura : listaCabeceraFacturas) {
			if (cabeceraFactura.getIdFactura().equals(idFactura)) {

				////////////// Agregar loger

				return true;
			}

		}
		return false;
	}

	public List<ComunicacionBaja> genearComunicacionBaja(List<String> lineasArchivo) {
		String idComunicacionBaja = "";
		List<ComunicacionBaja> listaComunicacionBaja = new ArrayList<ComunicacionBaja>();
		ComunicacionBaja comunicacionBaja = new ComunicacionBaja();

		for (String linea : lineasArchivo) {

			String[] arregloComunicacionBaja = linea.split("\\|");

			idComunicacionBaja = arregloComunicacionBaja[3] + arregloComunicacionBaja[5] + arregloComunicacionBaja[6];

			if (!verificarRepiteComunicacionBaja(listaComunicacionBaja, idComunicacionBaja)) {

				// if(esCabeceraValida())

				comunicacionBaja = new ComunicacionBaja();
				comunicacionBaja.setIdComunicaionBaja(idComunicacionBaja);
				comunicacionBaja.setRazonSocial(arregloComunicacionBaja[0]);

				if (!arregloComunicacionBaja[3].isEmpty())
					comunicacionBaja.setNumeroRuc(new BigInteger(arregloComunicacionBaja[0]));

				if (!arregloComunicacionBaja[4].isEmpty())
					comunicacionBaja.setTipoDocumento(Integer.parseInt(arregloComunicacionBaja[4]));

				String fechaCadena = arregloComunicacionBaja[2];
				Date fechaEmision = obtenerFecha(fechaCadena);

				comunicacionBaja.setFechaGeneracionDocumento(fechaEmision);

				comunicacionBaja.setIdentificaComunica(arregloComunicacionBaja[1]);

				comunicacionBaja.setFechaGeneraComunica(new Date());

				comunicacionBaja.setFirmaDigital("");

				comunicacionBaja.setVersionUbl("2.0");

				comunicacionBaja.setVersionEstrucDoc("1.0");

				comunicacionBaja.setEstado(3);

				comunicacionBaja.setRespuesta("");

				listaComunicacionBaja.add(comunicacionBaja);
			}

		}

		return listaComunicacionBaja;
	}

	private boolean verificarRepiteComunicacionBaja(List<ComunicacionBaja> listaComunicacionBaja,
			String idComunicacionBaja) {
		for (ComunicacionBaja comunicacionBaja : listaComunicacionBaja) {
			if (comunicacionBaja.getIdComunicaionBaja().equals(idComunicacionBaja)) {

				////////////// Agregar loger

				return true;
			}

		}
		return false;
	}


	public List<DetalleComunicacionBaja> generarDetalleDetalleComunicacionBaja(List<String> lineasArchivo,
			String idComunicaionBaja) {
		String idComunicaionBajaAux = "";
		List<DetalleComunicacionBaja> listaDetalleComunicacionBaja = new ArrayList<DetalleComunicacionBaja>();
		DetalleComunicacionBaja detalleComunicacionBaja = new DetalleComunicacionBaja();

		int contador = 0;
		
		for (String linea : lineasArchivo) {

			String[] arregloComunicacionBaja = linea.split("\\|");

			idComunicaionBajaAux = arregloComunicacionBaja[3] + arregloComunicacionBaja[5] + arregloComunicacionBaja[6];

			if (idComunicaionBajaAux.equals(idComunicaionBaja)) {

				detalleComunicacionBaja = new DetalleComunicacionBaja();
				
				contador = contador + 1;

				detalleComunicacionBaja.setTipoDocumento(arregloComunicacionBaja[4]);
				detalleComunicacionBaja.setSerieDocumento(arregloComunicacionBaja[5]);
				detalleComunicacionBaja.setNumeroCorrelativo(arregloComunicacionBaja[6]);
				detalleComunicacionBaja.setMotivoBaja(arregloComunicacionBaja[7]);
				detalleComunicacionBaja.setNumeroItem(contador);

				listaDetalleComunicacionBaja.add(detalleComunicacionBaja);

			}

		}

		return listaDetalleComunicacionBaja;
	}

}
