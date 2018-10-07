package org.facturacionelectronica.entidades;

import java.math.BigDecimal;

public class DetalleFactura {
	
	private int numeroOrden;//33
	private String unidadMedida;//11
	private String codigoItem;//34
	private String descripcionItem;//13
		
	private int cantidad;//12
	private BigDecimal valorUnitarioPorItem;//14		//83.05 -- precio del item sin IGV
	private BigDecimal precioVentaUnitarioPorItem;//15	//98.00 -- precio de item con IGV
	private BigDecimal impuestoUnitarioPorItem;			//98.00 -- precio de item con IGV
	private BigDecimal valorVentaBruto;					//valorUnitarioPorItem x cantidad
	private BigDecimal valorVentaPorItem;//21			//149,491.53 -- precio total del item con impuestos y descuentos
	private BigDecimal impuestoPorItem;//16				//26,908.47 -- 
	private BigDecimal porcentajeImpuestoItem;			//18% -- porcentaje del impuesto del item
	
//	private String codigoRazonExencionImpuesto;//16
//	private String codigoTributo;//16
//	private String nombreTributo;//16
//	private String codigoTipoTributo;//16
	
	
	public DetalleFactura() {}
		
	public DetalleFactura(int numeroOrden, String unidadMedida, String codigoItem, String descripcionItem, int cantidad,
			BigDecimal valorUnitarioPorItem, BigDecimal precioVentaUnitarioPorItem, BigDecimal impuestoUnitarioPorItem,
			BigDecimal valorVentaBruto, BigDecimal valorVentaPorItem, BigDecimal impuestoPorItem,
			BigDecimal porcentajeImpuestoItem) {
		super();
		this.numeroOrden = numeroOrden;
		this.unidadMedida = unidadMedida;
		this.codigoItem = codigoItem;
		this.descripcionItem = descripcionItem;
		this.cantidad = cantidad;
		this.valorUnitarioPorItem = valorUnitarioPorItem;
		this.precioVentaUnitarioPorItem = precioVentaUnitarioPorItem;
		this.impuestoUnitarioPorItem = impuestoUnitarioPorItem;
		this.valorVentaBruto = valorVentaBruto;
		this.valorVentaPorItem = valorVentaPorItem;
		this.impuestoPorItem = impuestoPorItem;
		this.porcentajeImpuestoItem = porcentajeImpuestoItem;
	}


	public int getNumeroOrden() {
		return numeroOrden;
	}
	public void setNumeroOrden(int numeroOrden) {
		this.numeroOrden = numeroOrden;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public String getCodigoItem() {
		return codigoItem;
	}
	public void setCodigoItem(String codigoItem) {
		this.codigoItem = codigoItem;
	}
	public String getDescripcionItem() {
		return descripcionItem;
	}
	public void setDescripcionItem(String descripcionItem) {
		this.descripcionItem = descripcionItem;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getValorUnitarioPorItem() {
		return valorUnitarioPorItem;
	}
	public void setValorUnitarioPorItem(BigDecimal valorUnitarioPorItem) {
		this.valorUnitarioPorItem = valorUnitarioPorItem;
	}
	public BigDecimal getValorVentaBruto() {
		return valorVentaBruto;
	}
	public void setValorVentaBruto(BigDecimal valorVentaBruto) {
		this.valorVentaBruto = valorVentaBruto;
	}

	public BigDecimal getValorVentaPorItem() {
		return valorVentaPorItem;
	}
	public void setValorVentaPorItem(BigDecimal valorVentaPorItem) {
		this.valorVentaPorItem = valorVentaPorItem;
	}
	public BigDecimal getPrecioVentaUnitarioPorItem() {
		return precioVentaUnitarioPorItem;
	}
	public void setPrecioVentaUnitarioPorItem(BigDecimal precioVentaUnitarioPorItem) {
		this.precioVentaUnitarioPorItem = precioVentaUnitarioPorItem;
	}
	public BigDecimal getImpuestoPorItem() {
		return impuestoPorItem;
	}
	public void setImpuestoPorItem(BigDecimal impuestoPorItem) {
		this.impuestoPorItem = impuestoPorItem;
	}

	public BigDecimal getImpuestoUnitarioPorItem() {
		return impuestoUnitarioPorItem;
	}

	public void setImpuestoUnitarioPorItem(BigDecimal impuestoUnitarioPorItem) {
		this.impuestoUnitarioPorItem = impuestoUnitarioPorItem;
	}

	public BigDecimal getPorcentajeImpuestoItem() {
		return porcentajeImpuestoItem;
	}

	public void setPorcentajeImpuestoItem(BigDecimal porcentajeImpuestoItem) {
		this.porcentajeImpuestoItem = porcentajeImpuestoItem;
	}

	
	
	

}
