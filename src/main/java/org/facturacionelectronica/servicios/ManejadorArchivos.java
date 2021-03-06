package org.facturacionelectronica.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.CabeceraNotaCredito;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.DetalleNotaCredito;
import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.facturacionelectronica.util.ValidadorGenerico;

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

	public List<String> leerCarpeta(String rutaCarpeta, String codigoTipoDocumento) {

		try {

			File folder = new File(rutaCarpeta);
			List<String> lineasCarpetaImportacion = new ArrayList<String>();

			for (final File fileEntry : folder.listFiles()) {

				if (esArchivoValido(fileEntry.getName())) {
					List<String> lineasArchivo = this.leerArchivo(rutaCarpeta + fileEntry.getName());

					for (String lineaArchivo : lineasArchivo) {
						lineasCarpetaImportacion.add(lineaArchivo);
					}

					eliminarArchivo(fileEntry);

				}
			}
			return lineasCarpetaImportacion;
		} catch (Exception ex) {
			// logger.error(ex);
			System.out.println("Suit_fael Error: " + ex.getMessage());
			System.exit(0);
			return null;
		}

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

		// String pattern = "[0-9]{11}-[0-9]{1}";

		return true;
	}

	public List<CabeceraFactura> generarCabeceraFactura(List<String> lineasArchivo) throws ParseException {

		String idFacturaAux = "";
		List<CabeceraFactura> listaCabeceraFacturas = new ArrayList<CabeceraFactura>();
		CabeceraFactura cabeceraFactura = new CabeceraFactura();

		for (String linea : lineasArchivo) {

			String[] arregloFactura = linea.split("\\|");

			idFacturaAux = arregloFactura[13].trim() + arregloFactura[16].trim() + arregloFactura[17].trim();

			if (!verificarRepiteFactura(listaCabeceraFacturas, idFacturaAux)) {

				cabeceraFactura = new CabeceraFactura();
				cabeceraFactura.setIdFactura(
						arregloFactura[13].trim() + arregloFactura[16].trim() + arregloFactura[17].trim());
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
				cabeceraFactura.setIdCustomization("1.0");
				cabeceraFactura.setMoneda("PEN");

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

				cabeceraFactura.setPaciente(arregloFactura[42]);
				cabeceraFactura.setDireccionPaciente(arregloFactura[43]);
				cabeceraFactura.setTipoCambio(arregloFactura[44]);
				cabeceraFactura.setMedioPago(arregloFactura[45]);
				cabeceraFactura.setTelefonoEmisor(arregloFactura[46]);

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

			idFacturaAux = arregloFactura[13].trim() + arregloFactura[16].trim() + arregloFactura[17].trim();
			;

			if (idFacturaAux.equals(idFactura)) {

				detalleFactura = new DetalleFactura();

				if (!arregloFactura[30].isEmpty())
					detalleFactura.setNumeroOrden(Integer.parseInt(arregloFactura[30]));

				detalleFactura.setUnidadMedida(arregloFactura[31]);
				detalleFactura.setCodigoItem(arregloFactura[32]);
				detalleFactura.setDescripcionItem(arregloFactura[33]);

				if (!arregloFactura[34].isEmpty())
					detalleFactura.setCantidad(new BigDecimal(arregloFactura[34]));// OK

				if (!arregloFactura[35].isEmpty()) {
					BigDecimal valorUnitarioPorItemAux = new BigDecimal(arregloFactura[35]);
					valorUnitarioPorItemAux = valorUnitarioPorItemAux.divide(new BigDecimal("1.18"), 2,
							RoundingMode.HALF_UP);

					detalleFactura.setValorUnitarioPorItem(valorUnitarioPorItemAux);// corregir
				}

				if (!arregloFactura[35].isEmpty())
					detalleFactura.setPrecioVentaUnitarioPorItem(new BigDecimal(arregloFactura[35]));

				if (!arregloFactura[37].isEmpty())
					// detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[37]));// ESTE
					// CAMPO CAMBIA A
					// DESCUENTO

					// if (!arregloFactura[38].isEmpty())
					// detalleFactura.setValorVentaBruto(new BigDecimal(arregloFactura[38]));

					if (!arregloFactura[38].isEmpty())
						detalleFactura.setValorVentaPorItem(new BigDecimal(arregloFactura[38]));

				if (!arregloFactura[37].isEmpty())
					detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[37]));

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

	private boolean esCabeceraFacturaValida(List<String> lineasArchivo) {

		boolean esValido = true;

		if (lineasArchivo == null) {
			GestorExcepciones.guardarExcepcionPorValidacion("El archivo está vacio", this);
			return false;
		}

		if (lineasArchivo.size() == 0) {
			GestorExcepciones.guardarExcepcionPorValidacion("El archivo está vacio", this);
			return false;
		}

		for (String linea : lineasArchivo) {

			String[] arregloFactura = linea.split("\\|");

			if (arregloFactura.length < 47) {
				GestorExcepciones.guardarExcepcionPorValidacion("La linea no tiene la camtidad de campos requerida",
						this);
				return false;

			} else {

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[0])) {
					GestorExcepciones
							.guardarExcepcionPorValidacion("El campo ID factura no debe estar vacio (Columna 0)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[2])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Fecha de Emisión no debe estar vacio (Columna 2)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[4])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Razon Social no debe estar vacio (Columna 4)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[5])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Nombre Comercial no debe estar vacio (Columna 5)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[13])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Número de Documento del Emisor no debe estar vacio (Columna 13)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[14])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Tipo de Documento del Emisor no debe estar vacio (Columna 14)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[15])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Tipo de Documento de Comprobante no debe estar vacio (Columna 15)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[16])) {
					GestorExcepciones.guardarExcepcionPorValidacion("El campo Serie no debe estar vacio (Columna 16)",
							this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[17])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Número de Correlativo no debe estar vacio (Columna 17)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[18])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Número de Documento del Cliente no debe estar vacio (Columna 18)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[19])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Tipo de Documento del Cliente no debe estar vacio (Columna 19)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[20])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Razon Social del Cliente no debe estar vacio (Columna 20)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[21])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Total Venta Operaciones Gravadas no debe estar vacio (Columna 21)", this);
					esValido = false;
				}

				if (!ValidadorGenerico.esCadenaValida(arregloFactura[28])) {
					GestorExcepciones.guardarExcepcionPorValidacion(
							"El campo Importe Total de Venta no debe estar vacio (Columna 28)", this);
					esValido = false;
				}

			}

		}

		return esValido;

	}

	// ----------------------------------------------------------------------------------------->>>
	// COMUNICACION BAJA

	public List<ComunicacionBaja> genearComunicacionBaja(List<String> lineasArchivo) {
		String idComunicacionBaja = "";
		BigInteger rucEmisorComunicacionBaja = null;
		List<ComunicacionBaja> listaComunicacionBaja = new ArrayList<ComunicacionBaja>();
		ComunicacionBaja comunicacionBaja = new ComunicacionBaja();

		for (String linea : lineasArchivo) {

			String[] arregloComunicacionBaja = linea.split("\\|");

			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String fechaGeneracionArhcivo = df.format(new Date());

			if (!arregloComunicacionBaja[3].isEmpty())
				rucEmisorComunicacionBaja = new BigInteger(arregloComunicacionBaja[3]);

			idComunicacionBaja = rucEmisorComunicacionBaja + Constantes.separadorNombreArchivo
					+ Constantes.siglaIdentComunicacionBaja + Constantes.separadorNombreArchivo + fechaGeneracionArhcivo
					+ Constantes.separadorNombreArchivo + Constantes.numeroComunicacionBaja;

			if (!verificarRepiteComunicacionBaja(listaComunicacionBaja, idComunicacionBaja)) {

				// if(esCabeceraValida())

				comunicacionBaja = new ComunicacionBaja();
				comunicacionBaja.setIdComunicaionBaja(idComunicacionBaja);
				comunicacionBaja.setRazonSocial(arregloComunicacionBaja[0]);

				if (!arregloComunicacionBaja[3].isEmpty())
					comunicacionBaja.setNumeroRuc(new BigInteger(arregloComunicacionBaja[3]));

				if (!arregloComunicacionBaja[4].isEmpty())
					comunicacionBaja.setTipoDocumento(Integer.parseInt(arregloComunicacionBaja[4]));

				String fechaCadena = arregloComunicacionBaja[2];
				Date fechaEmision = obtenerFecha(fechaCadena);

				comunicacionBaja.setFechaGeneracionDocumento(fechaEmision);

				comunicacionBaja.setFechaGeneraComunica(new Date());

				comunicacionBaja.setFirmaDigital("");

				comunicacionBaja.setVersionUbl("2.0");

				comunicacionBaja.setVersionEstrucDoc("1.0");

				comunicacionBaja.setEstado(5);

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
		String idComunicacionBajaAux = "";
		BigInteger rucEmisorComunicacionBaja = null;
		List<DetalleComunicacionBaja> listaDetalleComunicacionBaja = new ArrayList<DetalleComunicacionBaja>();
		DetalleComunicacionBaja detalleComunicacionBaja = new DetalleComunicacionBaja();

		int contador = 0;

		for (String linea : lineasArchivo) {

			String[] arregloComunicacionBaja = linea.split(Constantes.caracterSeparadorArchivo);
			DateFormat df = new SimpleDateFormat(Constantes.formatoFechaComunicacionBaja);
			String fechaGeneracionArhcivo = df.format(new Date());

			if (!arregloComunicacionBaja[3].isEmpty())
				rucEmisorComunicacionBaja = new BigInteger(arregloComunicacionBaja[3]);

			idComunicacionBajaAux = rucEmisorComunicacionBaja + Constantes.separadorNombreArchivo
					+ Constantes.siglaIdentComunicacionBaja + Constantes.separadorNombreArchivo + fechaGeneracionArhcivo
					+ Constantes.separadorNombreArchivo + Constantes.numeroComunicacionBaja;

			if (idComunicacionBajaAux.equals(idComunicaionBaja)) {

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

	public List<CabeceraNotaCredito> generarCabeceraNotaCredito(List<String> lineasArchivo) {
		String idNotaCreditoAux = "";
		List<CabeceraNotaCredito> listacabeceraNotaCreditos = new ArrayList<CabeceraNotaCredito>();
		CabeceraNotaCredito cabeceraNotaCredito = new CabeceraNotaCredito();

		for (String linea : lineasArchivo) {

			String[] arregloNotaCredito = linea.split("\\|");

			idNotaCreditoAux = arregloNotaCredito[13].trim() + arregloNotaCredito[16].trim() + arregloNotaCredito[17].trim();

			if (!verificarRepiteNotaCredito(listacabeceraNotaCreditos, idNotaCreditoAux)) {

				cabeceraNotaCredito = new CabeceraNotaCredito();
				cabeceraNotaCredito.setIdNotaCredito(
						arregloNotaCredito[13].trim() + arregloNotaCredito[16].trim() + arregloNotaCredito[17].trim());
				cabeceraNotaCredito.setIdCustomization(arregloNotaCredito[1]);

				String fechaCadena = arregloNotaCredito[2];
				Date fechaEmision = obtenerFecha(fechaCadena);

				cabeceraNotaCredito.setFechaEmision(fechaEmision);
				cabeceraNotaCredito.setFirmaDigital(arregloNotaCredito[3]);
				cabeceraNotaCredito.setRazonSocial(arregloNotaCredito[4]);
				cabeceraNotaCredito.setNombreComercial(arregloNotaCredito[5]);
				cabeceraNotaCredito.setCodigoUbigeo(arregloNotaCredito[6]);
				cabeceraNotaCredito.setDireccionCompleta(arregloNotaCredito[7]);
				cabeceraNotaCredito.setUrbanizacion(arregloNotaCredito[8]);
				cabeceraNotaCredito.setProvincia(arregloNotaCredito[9]);
				cabeceraNotaCredito.setDepartamento(arregloNotaCredito[10]);
				cabeceraNotaCredito.setDistrito(arregloNotaCredito[11]);
				cabeceraNotaCredito.setCodigoPais(arregloNotaCredito[12]);
				cabeceraNotaCredito.setNumeroDocumento(new BigInteger(arregloNotaCredito[13]));
				cabeceraNotaCredito.setTipoDocumento(new Integer(arregloNotaCredito[14]));
				cabeceraNotaCredito.setSerieNotaCredito(arregloNotaCredito[16]);
				cabeceraNotaCredito.setNumeroCorrelativoNotaCredito(arregloNotaCredito[17]);
				cabeceraNotaCredito.setNumeroDocumentoCliente(arregloNotaCredito[18]);
				cabeceraNotaCredito.setTipoDocumentoCliente(arregloNotaCredito[19]);
				cabeceraNotaCredito.setRazonSocialCliente(arregloNotaCredito[20]);
				cabeceraNotaCredito.setIdCustomization("1.0");
				cabeceraNotaCredito.setVersionUBL("2.0");
				cabeceraNotaCredito.setMoneda("PEN");

				if (!arregloNotaCredito[21].isEmpty())
					cabeceraNotaCredito.setTotalValorVentaOpGravadas(new BigDecimal(arregloNotaCredito[21]));

				if (!arregloNotaCredito[22].isEmpty())
					cabeceraNotaCredito.setTotalValorVentaOpInafecta(new BigDecimal(arregloNotaCredito[22]));

				if (!arregloNotaCredito[23].isEmpty())
					cabeceraNotaCredito.setTotalValorVentaOpExoneradas(new BigDecimal(arregloNotaCredito[23]));

				if (!arregloNotaCredito[24].isEmpty())
					cabeceraNotaCredito.setTotalValorVentaOpGratuitas(new BigDecimal(arregloNotaCredito[24]));

				if (!arregloNotaCredito[25].isEmpty())
					cabeceraNotaCredito.setSumatoriaIGV(new BigDecimal(arregloNotaCredito[25]));

				if (!arregloNotaCredito[26].isEmpty())
					cabeceraNotaCredito.setSumatoriaISC(new BigDecimal(arregloNotaCredito[26]));

				if (!arregloNotaCredito[27].isEmpty())
					cabeceraNotaCredito.setTotalDescuentos(new BigDecimal(arregloNotaCredito[27]));

				if (!arregloNotaCredito[28].isEmpty())
					cabeceraNotaCredito.setImporteTotalVenta(new BigDecimal(arregloNotaCredito[28]));

				cabeceraNotaCredito.setPaciente(arregloNotaCredito[42]);
				cabeceraNotaCredito.setDireccionPaciente(arregloNotaCredito[43]);
				cabeceraNotaCredito.setTipoCambio(arregloNotaCredito[44]);
				cabeceraNotaCredito.setMedioPago(arregloNotaCredito[45]);
				cabeceraNotaCredito.setTelefonoEmisor(arregloNotaCredito[46]);

				cabeceraNotaCredito.setSerieDocumentoAfectado(arregloNotaCredito[49]);
				cabeceraNotaCredito.setNumeroCorrelativoDocumentoAfectado(arregloNotaCredito[50]);
				
//				cabeceraNotaCredito.setTipoNotaCredito(arregloNotaCredito[51]);
				cabeceraNotaCredito.setTipoNotaCredito("07");

				listacabeceraNotaCreditos.add(cabeceraNotaCredito);
			}

		}

		return listacabeceraNotaCreditos;
	}

	private boolean verificarRepiteNotaCredito(List<CabeceraNotaCredito> listacabeceraNotaCreditos,
			String idNotaCredito) {
		for (CabeceraNotaCredito cabeceraNotaCredito : listacabeceraNotaCreditos) {
			if (cabeceraNotaCredito.getIdNotaCredito().equals(idNotaCredito)) {

				////////////// Agregar loger

				return true;
			}

		}
		return false;
	}

	public List<DetalleNotaCredito> generarDetalleNotaCredito(List<String> lineasArchivo, String idNotaCredito) {
		String idNotaCreditoAux = "";
		List<DetalleNotaCredito> listaDetalleNotaCredito = new ArrayList<DetalleNotaCredito>();
		DetalleNotaCredito detalleNotaCredito = new DetalleNotaCredito();

		for (String linea : lineasArchivo) {

			String[] arregloNotaCredito = linea.split("\\|");

			idNotaCreditoAux = arregloNotaCredito[13].trim() + arregloNotaCredito[16].trim() + arregloNotaCredito[17].trim();
			

			if (idNotaCreditoAux.equals(idNotaCredito)) {

				detalleNotaCredito = new DetalleNotaCredito();

				if (!arregloNotaCredito[30].isEmpty())
					detalleNotaCredito.setNumeroOrden(Integer.parseInt(arregloNotaCredito[30]));

				detalleNotaCredito.setUnidadMedida(arregloNotaCredito[31]);
				detalleNotaCredito.setCodigoItem(arregloNotaCredito[32]);
				detalleNotaCredito.setDescripcionItem(arregloNotaCredito[33]);

				if (!arregloNotaCredito[34].isEmpty())
					detalleNotaCredito.setCantidad(new BigDecimal(arregloNotaCredito[34]));// OK

				if (!arregloNotaCredito[35].isEmpty()) {
					BigDecimal valorUnitarioPorItemAux = new BigDecimal(arregloNotaCredito[35]);
					valorUnitarioPorItemAux = valorUnitarioPorItemAux.divide(new BigDecimal("1.18"), 2,
							RoundingMode.HALF_UP);

					detalleNotaCredito.setValorUnitarioPorItem(valorUnitarioPorItemAux);// corregir
				}

				if (!arregloNotaCredito[35].isEmpty())
					detalleNotaCredito.setPrecioVentaUnitarioPorItem(new BigDecimal(arregloNotaCredito[35]));

				if (!arregloNotaCredito[37].isEmpty())
					// detalleNotaCredito.setImpuestoPorItem(new BigDecimal(arregloNotaCredito[37]));// ESTE
					// CAMPO CAMBIA A
					// DESCUENTO

					// if (!arregloNotaCredito[38].isEmpty())
					// detalleNotaCredito.setValorVentaBruto(new BigDecimal(arregloNotaCredito[38]));

					if (!arregloNotaCredito[38].isEmpty())
						detalleNotaCredito.setValorVentaPorItem(new BigDecimal(arregloNotaCredito[38]));

				if (!arregloNotaCredito[37].isEmpty())
					detalleNotaCredito.setImpuestoPorItem(new BigDecimal(arregloNotaCredito[37]));

				listaDetalleNotaCredito.add(detalleNotaCredito);

			}

		}

		return listaDetalleNotaCredito;
	}

}
