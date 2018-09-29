package org.facturacionelectronica.servicios;

import java.math.BigInteger;
import java.util.Date;

import org.facturacionelectronica.entidades.CabeceraFactura;

public final class FacturaMock {

	public static CabeceraFactura retornarFactura() {
		return new CabeceraFactura(new Date(20120314), "EjemploFirmaDigital", "Soporte Tecnológicos EIRL", "", "", "Av. Los Precursores # 1245", "Urb. Miguel Grau", "Lima", "Lima", "El Agustino", "", new BigInteger("20100454523") , 6, "01", "F001", "4355", "20587896411", "6", "Servicabinas S.A.");
	}

}
