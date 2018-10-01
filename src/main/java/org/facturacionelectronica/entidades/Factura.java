package org.facturacionelectronica.entidades;

import java.util.ArrayList;
import java.util.List;

public class Factura {

	private CabeceraFactura cabeceraFactura = new CabeceraFactura();
	
	private List<DetalleFactura> detalleFactura = new ArrayList<DetalleFactura>();

	public CabeceraFactura getCabeceraFactura() {
		return cabeceraFactura;
	}

	public void setCabeceraFactura(CabeceraFactura cabeceraFactura) {
		this.cabeceraFactura = cabeceraFactura;
	}

	public List<DetalleFactura> getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(List<DetalleFactura> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}
	
}
