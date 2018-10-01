package org.facturacionelectronica.entidades;

import java.math.BigDecimal;

public class DetalleFactura {
	
	private int numeroOrden;
	private String unidadMedida;
	private String codigoItem;
	private String descripcionItem;
		
	private int cantidad;
	private BigDecimal valorUnitarioPorItem;
	private BigDecimal valorVentaBruto;
	private BigDecimal descuentosPorItem;
	private BigDecimal valorVentaPorItem;
	private BigDecimal impuesto;
	
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
	public BigDecimal getDescuentosPorItem() {
		return descuentosPorItem;
	}
	public void setDescuentosPorItem(BigDecimal descuentosPorItem) {
		this.descuentosPorItem = descuentosPorItem;
	}
	public BigDecimal getValorVentaPorItem() {
		return valorVentaPorItem;
	}
	public void setValorVentaPorItem(BigDecimal valorVentaPorItem) {
		this.valorVentaPorItem = valorVentaPorItem;
	}
	public BigDecimal getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}
	
	
	

}
