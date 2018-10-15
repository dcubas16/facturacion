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
import org.facturacionelectronica.entidades.DetalleFactura;

public class ManejadorArchivos {

	@SuppressWarnings("resource")
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

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lineasArchivo;

	}

	public List<CabeceraFactura> generarCabeceraFactura(List<String> lineasArchivo) throws ParseException {

		String idFacturaAux = "";
		List<CabeceraFactura> listaCabeceraFacturas = new ArrayList<CabeceraFactura>();
		CabeceraFactura cabeceraFactura = new CabeceraFactura();

		for (String linea : lineasArchivo) {

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

				if (!arregloFactura[30].isEmpty())

					detalleFactura = new DetalleFactura();

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
					detalleFactura.setImpuestoPorItem(new BigDecimal(arregloFactura[37]));//ESTE CAMPO CAMBIA A DESCUENTO

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
				
				//////////////Agregar loger
				
				return true;
			}
				
		}
		return false;
	}
	


}
