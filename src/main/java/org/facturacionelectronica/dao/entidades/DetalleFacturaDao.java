package org.facturacionelectronica.dao.entidades;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.facturacionelectronica.entidades.DetalleFactura;

@Entity
@Table(name="DETALLE_FACTURA", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_DETALLE_FACTURA")})
@Inheritance(strategy=InheritanceType.JOINED)
public class DetalleFacturaDao {
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_FACTURA", nullable=false)
    private FacturaDao facturaDao;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "ID_DETALLE_FACTURA", nullable = false)
	private int idDetalleFactura;
	
	@Column(name = "NUMERO_ORDEN", nullable=false, length=3)
	private int numeroOrden;

	@Column(name = "UNIDAD_MEDIDA", nullable=false, length=3)
	private String unidadMedida;

	@Column(name = "CODIGO_ITEM", nullable=true, length=30 )
	private String codigoItem;

	@Column(name = "DESCRIPCION_ITEM", nullable=false, length=250)
	private String descripcionItem;

	@Column(name = "CANTIDAD", nullable=false, precision=12, scale=3)	
	private int cantidad;

	@Column(name = "VALOR_UNITARIO_POR_ITEM", nullable=false, precision=12, scale=2)
	private BigDecimal valorUnitarioPorItem;//83.05 -- precio del item sin IGV

	@Column(name = "PRECIO_VENTA_UNITARIO_POR_ITEM", nullable=false, precision=12, scale=3)
	private BigDecimal precioVentaUnitarioPorItem;//98.00 -- precio de item con IGV

	@Column(name = "IMPUESTO_UNITARIO_POR_ITEM", nullable=false, precision=12, scale=3)
	private BigDecimal impuestoUnitarioPorItem;//98.00 -- precio de item con IGV

	@Column(name = "VALOR_VENTA_BRUTO", nullable=true)
	private BigDecimal valorVentaBruto;//valorUnitarioPorItem x cantidad

	@Column(name = "VALOR_VENTA_POR_ITEM", nullable=false, precision=14, scale=2)
	private BigDecimal valorVentaPorItem;//149,491.53 -- precio total del item con impuestos y descuentos

	@Column(name = "IMPUESTO_POR_ITEM", nullable=true)
	private BigDecimal impuestoPorItem;//26,908.47 -- 

	@Column(name = "PORCENTAJE_IMPUESTO_ITEM", nullable=true)
	private BigDecimal porcentajeImpuestoItem;//18% -- porcentaje del impuesto del item
	
	
	public DetalleFacturaDao() {}

	public DetalleFacturaDao(int idDetalleFactura, int numeroOrden, String unidadMedida,
			String codigoItem, String descripcionItem, int cantidad, BigDecimal valorUnitarioPorItem,
			BigDecimal precioVentaUnitarioPorItem, BigDecimal impuestoUnitarioPorItem, BigDecimal valorVentaBruto,
			BigDecimal valorVentaPorItem, BigDecimal impuestoPorItem, BigDecimal porcentajeImpuestoItem) {
		super();
		this.idDetalleFactura = idDetalleFactura;
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

	public DetalleFacturaDao(DetalleFactura detalleFactura, FacturaDao facturaDao) {
		super();
		
		this.numeroOrden = detalleFactura.getNumeroOrden();
		this.unidadMedida = detalleFactura.getUnidadMedida();
		this.codigoItem = detalleFactura.getCodigoItem();
		this.descripcionItem = detalleFactura.getDescripcionItem();
		this.cantidad = detalleFactura.getCantidad();
		this.valorUnitarioPorItem = detalleFactura.getValorUnitarioPorItem();
		this.precioVentaUnitarioPorItem = detalleFactura.getPrecioVentaUnitarioPorItem();
		this.impuestoUnitarioPorItem = detalleFactura.getImpuestoUnitarioPorItem();
		this.valorVentaBruto = detalleFactura.getValorVentaBruto();
		this.valorVentaPorItem = detalleFactura.getValorVentaPorItem();
		this.impuestoPorItem = detalleFactura.getImpuestoPorItem();
		this.porcentajeImpuestoItem = detalleFactura.getPorcentajeImpuestoItem();
	}

	public FacturaDao getFacturaDao() {
		return facturaDao;
	}

	public void setFacturaDao(FacturaDao facturaDao) {
		this.facturaDao = facturaDao;
	}

	public int getIdDetalleFactura() {
		return idDetalleFactura;
	}

	public void setIdDetalleFactura(int idDetalleFactura) {
		this.idDetalleFactura = idDetalleFactura;
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
