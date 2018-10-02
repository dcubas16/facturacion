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

		return detalleFactura;
	}

	private static CabeceraFactura obtenerCabeceraFactura() {

//		return new CabeceraFactura(new Date(20120314), "EjemploFirmaDigital", "Soporte Tecnológicos EIRL", "", "", "Av. Los Precursores # 1245", "Urb. Miguel Grau", "Lima", "Lima", "El Agustino", "", new BigInteger("20100454523") , 6, "01", "F001", "4355", "20587896411", "6", "Servicabinas S.A.");

		CabeceraFactura cabeceraFactura = new CabeceraFactura("F001-4355", "1.0", new Date(20120304), "firmaDigital", "", "Soporte Tecnológicos EIRL", "", "Av. Los Precursores # 1245", "Urb. Miguel Grau", "Lima", "Lima", "Lima", "", new BigInteger("20100454523"), 6, "01", "F001", "4355", "20587896411", "6", "Servicabinas S.A.", new BigDecimal("348199.15"), null, new BigDecimal("12350.00"), new BigDecimal("30.00"), new BigDecimal("62675.85"), null, new BigDecimal("59230.51"), new BigDecimal("423225.00"), "CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100");
		
//		CabeceraFactura cabeceraFactura = new CabeceraFactura();
		
		return cabeceraFactura;
	}

}
