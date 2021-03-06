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

import org.facturacionelectronica.entidades.DetalleNotaCredito;

@Entity
@Table(name="DETALLE_NOTA_CREDITO", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_DETALLE_NOTA_CREDITO")})
@Inheritance(strategy=InheritanceType.JOINED)
public class DetalleNotaCreditoDao {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_NOTA_CREDITO", nullable =true)
    private NotaCreditoDao notaCreditoDao;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "ID_DETALLE_NOTA_CREDITO", nullable = false)
	private int idDetalleNotaCreditoDao;
	
	@Column(name = "NUMERO_ORDEN", nullable =true, length=3)
	private int numeroOrden;
	
	@Column(name = "UNIDAD_MEDIDA", nullable =true, length=3)
	private String unidadMedida;

	@Column(name = "CANTIDAD", nullable =true, precision=12, scale=3)	
	private BigDecimal cantidad;
	
	@Column(name = "CODIGO_ITEM", nullable=true, length=30 )
	private String codigoItem;
	
	@Column(name = "DESCRIPCION_ITEM", nullable =true, length=250)
	private String descripcionItem;
	
	
	@Column(name = "VALOR_UNITARIO_POR_ITEM", nullable =true, precision=12, scale=2)
	private BigDecimal valorUnitarioPorItem;//83.05 -- precio del item sin IGV

	@Column(name = "PRECIO_VENTA_UNITARIO_POR_ITEM", nullable =true, precision=12, scale=3)
	private BigDecimal precioVentaUnitarioPorItem;//98.00 -- precio de item con IGV y descuentos
	
	@Column(name = "IMPUESTO_UNITARIO_POR_ITEM", nullable=true, precision=12, scale=3)
	private BigDecimal impuestoUnitarioPorItem;//98.00 -- precio de item con IGV

	@Column(name = "VALOR_VENTA_BRUTO", nullable=true)
	private BigDecimal valorVentaBruto;//valorUnitarioPorItem x cantidad

	@Column(name = "VALOR_VENTA_POR_ITEM", nullable =true, precision=14, scale=2)
	private BigDecimal valorVentaPorItem;//149,491.53 -- precio total del item con impuestos y descuentos

	@Column(name = "IMPUESTO_POR_ITEM", nullable=true)
	private BigDecimal impuestoPorItem;//26,908.47 -- 

	@Column(name = "PORCENTAJE_IMPUESTO_ITEM", nullable=true)
	private BigDecimal porcentajeImpuestoItem;//18% -- porcentaje del impuesto del item

	
	
	
	public DetalleNotaCreditoDao(DetalleNotaCredito detalleNotaCredito) {
		super();
		this.numeroOrden = detalleNotaCredito.getNumeroOrden();
		this.unidadMedida = detalleNotaCredito.getUnidadMedida();
		this.cantidad = detalleNotaCredito.getCantidad();
		this.codigoItem = detalleNotaCredito.getCodigoItem();
		this.descripcionItem = detalleNotaCredito.getDescripcionItem();
		this.valorUnitarioPorItem = detalleNotaCredito.getValorUnitarioPorItem();
		this.precioVentaUnitarioPorItem = detalleNotaCredito.getPrecioVentaUnitarioPorItem();
		this.impuestoUnitarioPorItem = detalleNotaCredito.getImpuestoUnitarioPorItem();
		this.valorVentaBruto = detalleNotaCredito.getValorVentaBruto();
		this.valorVentaPorItem = detalleNotaCredito.getValorVentaPorItem();
		this.impuestoPorItem = detalleNotaCredito.getImpuestoPorItem();
		this.porcentajeImpuestoItem = detalleNotaCredito.getPorcentajeImpuestoItem();
	}

	public DetalleNotaCreditoDao() {
		super();
	}

	public NotaCreditoDao getNotaCreditoDao() {
		return notaCreditoDao;
	}

	public void setNotaCreditoDao(NotaCreditoDao notaCreditoDao) {
		this.notaCreditoDao = notaCreditoDao;
	}

	public int getIdDetalleNotaCreditoDao() {
		return idDetalleNotaCreditoDao;
	}

	public void setIdDetalleNotaCreditoDao(int idDetalleNotaCreditoDao) {
		this.idDetalleNotaCreditoDao = idDetalleNotaCreditoDao;
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

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
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
