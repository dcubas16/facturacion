package org.facturacionelectronica;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.Factura;

public final class FacturaMock {

	public static Factura obtenerFactura() {

		Factura factura = new Factura();

		CabeceraFactura cabeceraFactura = obtenerCabeceraFactura();

		List<DetalleFactura> detalleFactura = obtenerDetalleFactura();

		factura.setCabeceraFactura(cabeceraFactura);
		factura.setDetalleFactura(detalleFactura);

		return factura;
	}

	private static List<DetalleFactura> obtenerDetalleFactura() {

		List<DetalleFactura> detalleFactura = new ArrayList<DetalleFactura>();

		DetalleFactura detalleFactura1 = new DetalleFactura(1, "NIU", "GLG199", "Grabadora LG Externo Modelo: GE20LU10", new BigDecimal(2000), new BigDecimal("83.05"), new BigDecimal("98.00"), new BigDecimal("14.95"), new BigDecimal("166100.00"), new BigDecimal("149491.53"), new BigDecimal("26908.47"), new BigDecimal("18.00"));
		
		detalleFactura.add(detalleFactura1);

		return detalleFactura;
	}

	private static CabeceraFactura obtenerCabeceraFactura() {

		CabeceraFactura cabeceraFactura = new CabeceraFactura("F001-4355", "1.0", new Date(), "firmaDigital",
				"", "DOW SA", "", "Av. Primavera Nro. 1416 - Surco", "Urb. Miguel Grau", "Lima", "Lima", "Lima", "", new BigInteger("20381847927"), 6, "01", "F001", "4355", "20587896411", "6", "Servicabinas S.A.", new BigDecimal("348199.15"), null, new BigDecimal("12350.00"), new BigDecimal("30.00"), new BigDecimal("62675.85"), null, new BigDecimal("59230.51"), new BigDecimal("423225.00"), "CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100");
		cabeceraFactura.setMoneda("PEN");
		
		return cabeceraFactura;
	}

}
