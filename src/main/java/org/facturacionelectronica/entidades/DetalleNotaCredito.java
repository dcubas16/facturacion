package org.facturacionelectronica.entidades;

import java.math.BigDecimal;

public class DetalleNotaCredito {

	private int numeroOrden ;//33
	private String unidadMedida;//11
	private String codigoItem;//34
	private String descripcionItem;//13
		
	private BigDecimal cantidad = new BigDecimal("0");//12
	private BigDecimal valorUnitarioPorItem= new BigDecimal("0");//14		//83.05 -- precio del item sin IGV
	private BigDecimal precioVentaUnitarioPorItem= new BigDecimal("0");//15	//98.00 -- precio de item con IGV
	private BigDecimal impuestoUnitarioPorItem= new BigDecimal("0");//Reemplazar con descuento			//98.00 -- precio de item con IGV
	private BigDecimal valorVentaBruto= new BigDecimal("0");					//valorUnitarioPorItem x cantidad
	private BigDecimal valorVentaPorItem= new BigDecimal("0");//21			//149,491.53 -- precio total del item con impuestos y descuentos
	private BigDecimal impuestoPorItem= new BigDecimal("0");//16				//26,908.47 -- 
	private BigDecimal porcentajeImpuestoItem= new BigDecimal("0");//BORRAR			//18% -- porcentaje del impuesto del item
	
	
	
	
	public DetalleNotaCredito() {
		super();
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
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getValorUnitarioPorItem() {
		return valorUnitarioPorItem;
	}
	public void setValorUnitarioPorItem(BigDecimal valorUnitarioPorItem) {
		this.valorUnitarioPorItem = valorUnitarioPorItem;
	}
	public BigDecimal getPrecioVentaUnitarioPorItem() {
		return precioVentaUnitarioPorItem;
	}
	public void setPrecioVentaUnitarioPorItem(BigDecimal precioVentaUnitarioPorItem) {
		this.precioVentaUnitarioPorItem = precioVentaUnitarioPorItem;
	}
	public BigDecimal getImpuestoUnitarioPorItem() {
		return impuestoUnitarioPorItem;
	}
	public void setImpuestoUnitarioPorItem(BigDecimal impuestoUnitarioPorItem) {
		this.impuestoUnitarioPorItem = impuestoUnitarioPorItem;
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
	public BigDecimal getImpuestoPorItem() {
		return impuestoPorItem;
	}
	public void setImpuestoPorItem(BigDecimal impuestoPorItem) {
		this.impuestoPorItem = impuestoPorItem;
	}
	public BigDecimal getPorcentajeImpuestoItem() {
		return porcentajeImpuestoItem;
	}
	public void setPorcentajeImpuestoItem(BigDecimal porcentajeImpuestoItem) {
		this.porcentajeImpuestoItem = porcentajeImpuestoItem;
	}
	
	
}
