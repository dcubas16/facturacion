package org.facturacionelectronica.dao.entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.Factura;


@Entity
@Table(name = "FACTURA", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_FACTURA")})
@Inheritance(strategy=InheritanceType.JOINED)
public class FacturaDao {
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="facturaDao")
	private Set<DetalleFacturaDao> detalleFacturaDao;
	
	
	@Id
	@Column(name = "ID_FACTURA", unique = true, nullable = false)
	private String idFactura;
		
	@Column(name = "FECHA_EMISION", nullable=true)
	private Date fechaEmision;
	
//	@Column(name = "ID_CUSTOMIZACION", nullable=true)
//	private String idCustomization;
	
//	@Column(name = "FIRMA_DIGITAL", nullable=true)
//	private String firmaDigital;
	
	@Column(name = "RAZON_SOCIAL", nullable=true)
	private String razonSocial;
	
	@Column(name = "NOMBRE_COMERCIAL", nullable=true)
	private String nombreComercial;
	
	@Column(name = "CODIGO_UBIGEO", nullable=true)
	private String codigoUbigeo;
	
	@Column(name = "DIRECCION_COMPLETA", nullable=true)
	private String direccionCompleta;

	@Column(name = "URBANIZACION", nullable=true)
	private String urbanizacion;

	@Column(name = "PROVINCIA", nullable=true)
	private String provincia;

	@Column(name = "DEPARTAMENTO", nullable=true)
	private String departamento;

	@Column(name = "DISTRITO", nullable=true)
	private String distrito;

	@Column(name = "CODIGO_PAIS", nullable=true)
	private String codigoPais;

	@Column(name = "NUMERO_DOC", nullable=true)
	private BigInteger numeroDocumento;

	@Column(name = "TIPO_DOCUMENTO", nullable=true)
	private int tipoDocumento;

	@Column(name = "TIPO_DOC_FACTURA", nullable=true)
	private String tipoDocumentoFactura;

	@Column(name = "SERIE", nullable=true)
	private String serie;

	@Column(name = "NUMERO_CORRELATIVO", nullable=true)
	private String numeroCorrelativo;


	@Column(name = "NUMERO_DOC_CLIENTE", nullable=true)
	private String numeroDocumentoCliente;

	@Column(name = "TIPO_DOC_CLIENTE", nullable=true)
	private String tipoDocumentoCliente;

	@Column(name = "RAZON_SOCIAL_CLIENTE", nullable=true)
	private String razonSocialCliente;


	@Column(name = "TOTAL_VALOR_VENTA_OP_GRAVADA", nullable=true)
	private BigDecimal totalValorVentaOpGravadas;

	@Column(name = "TOTAL_VALOR_VENTA_OP_INAFEC", nullable=true)
	private BigDecimal totalValorVentaOpInafecta;

	@Column(name = "TOTAL_VALOR_VENTA_OP_EXONERA", nullable=true)
	private BigDecimal totalValorVentaOpExoneradas;

	@Column(name = "TOTAL_VALOR_VENTA_OP_GRAT", nullable=true)
	private BigDecimal totalValorVentaOpGratuitas;

	@Column(name = "SUMATORIA_IGV", nullable=true)
	private BigDecimal sumatoriaIGV;

	@Column(name = "SUMATORIA_ISC", nullable=true)
	private BigDecimal sumatoriaISC;

	@Column(name = "TOTAL_DESCUENTOS", nullable=true)
	private BigDecimal TotalDescuentos;

	@Column(name = "IMPORTE_TOTAL_VENTA", nullable=true)
	private BigDecimal ImporteTotalVenta;

	@Column(name = "LEYENDA", nullable=true)
	private String leyenda;

	@Column(name = "PORCENTAJE_IMPUESTO", nullable=true)
	private double porcentajeImpuesto;

	@Column(name = "MONEDA", nullable=true)
	private String moneda;
	
	
	public FacturaDao() {}
	
	public FacturaDao(CabeceraFactura cabeceraFactura) {
		
	}
		
	public FacturaDao(String idFactura, Date fechaEmision, String razonSocial,
			String nombreComercial, String codigoUbigeo, String direccionCompleta, String urbanizacion,
			String provincia, String departamento, String distrito, String codigoPais, BigInteger numeroDocumento,
			int tipoDocumento, String tipoDocumentoFactura, String serie, String numeroCorrelativo,
			String numeroDocumentoCliente, String tipoDocumentoCliente, String razonSocialCliente,
			BigDecimal totalValorVentaOpGravadas, BigDecimal totalValorVentaOpInafecta,
			BigDecimal totalValorVentaOpExoneradas, BigDecimal totalValorVentaOpGratuitas, BigDecimal sumatoriaIGV,
			BigDecimal sumatoriaISC, BigDecimal totalDescuentos, BigDecimal importeTotalVenta, String leyenda,
			double porcentajeImpuesto, String moneda) {
		super();
		this.idFactura = idFactura;
		this.fechaEmision = fechaEmision;
		this.razonSocial = razonSocial;
		this.nombreComercial = nombreComercial;
		this.codigoUbigeo = codigoUbigeo;
		this.direccionCompleta = direccionCompleta;
		this.urbanizacion = urbanizacion;
		this.provincia = provincia;
		this.departamento = departamento;
		this.distrito = distrito;
		this.codigoPais = codigoPais;
		this.numeroDocumento = numeroDocumento;
		this.tipoDocumento = tipoDocumento;
		this.tipoDocumentoFactura = tipoDocumentoFactura;
		this.serie = serie;
		this.numeroCorrelativo = numeroCorrelativo;
		this.numeroDocumentoCliente = numeroDocumentoCliente;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
		this.razonSocialCliente = razonSocialCliente;
		this.totalValorVentaOpGravadas = totalValorVentaOpGravadas;
		this.totalValorVentaOpInafecta = totalValorVentaOpInafecta;
		this.totalValorVentaOpExoneradas = totalValorVentaOpExoneradas;
		this.totalValorVentaOpGratuitas = totalValorVentaOpGratuitas;
		this.sumatoriaIGV = sumatoriaIGV;
		this.sumatoriaISC = sumatoriaISC;
		TotalDescuentos = totalDescuentos;
		ImporteTotalVenta = importeTotalVenta;
		this.leyenda = leyenda;
		this.porcentajeImpuesto = porcentajeImpuesto;
		this.moneda = moneda;
	}

	public Set<DetalleFacturaDao> getDetalleFacturaDao() {
		return detalleFacturaDao;
	}

	public void setDetalleFacturaDao(Set<DetalleFacturaDao> detalleFacturaDao) {
		this.detalleFacturaDao = detalleFacturaDao;
	}

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getCodigoUbigeo() {
		return codigoUbigeo;
	}

	public void setCodigoUbigeo(String codigoUbigeo) {
		this.codigoUbigeo = codigoUbigeo;
	}

	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public String getUrbanizacion() {
		return urbanizacion;
	}

	public void setUrbanizacion(String urbanizacion) {
		this.urbanizacion = urbanizacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public BigInteger getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(BigInteger numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public int getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoDocumentoFactura() {
		return tipoDocumentoFactura;
	}

	public void setTipoDocumentoFactura(String tipoDocumentoFactura) {
		this.tipoDocumentoFactura = tipoDocumentoFactura;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNumeroCorrelativo() {
		return numeroCorrelativo;
	}

	public void setNumeroCorrelativo(String numeroCorrelativo) {
		this.numeroCorrelativo = numeroCorrelativo;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public String getTipoDocumentoCliente() {
		return tipoDocumentoCliente;
	}

	public void setTipoDocumentoCliente(String tipoDocumentoCliente) {
		this.tipoDocumentoCliente = tipoDocumentoCliente;
	}

	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}

	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}

	public BigDecimal getTotalValorVentaOpGravadas() {
		return totalValorVentaOpGravadas;
	}

	public void setTotalValorVentaOpGravadas(BigDecimal totalValorVentaOpGravadas) {
		this.totalValorVentaOpGravadas = totalValorVentaOpGravadas;
	}

	public BigDecimal getTotalValorVentaOpInafecta() {
		return totalValorVentaOpInafecta;
	}

	public void setTotalValorVentaOpInafecta(BigDecimal totalValorVentaOpInafecta) {
		this.totalValorVentaOpInafecta = totalValorVentaOpInafecta;
	}

	public BigDecimal getTotalValorVentaOpExoneradas() {
		return totalValorVentaOpExoneradas;
	}

	public void setTotalValorVentaOpExoneradas(BigDecimal totalValorVentaOpExoneradas) {
		this.totalValorVentaOpExoneradas = totalValorVentaOpExoneradas;
	}

	public BigDecimal getTotalValorVentaOpGratuitas() {
		return totalValorVentaOpGratuitas;
	}

	public void setTotalValorVentaOpGratuitas(BigDecimal totalValorVentaOpGratuitas) {
		this.totalValorVentaOpGratuitas = totalValorVentaOpGratuitas;
	}

	public BigDecimal getSumatoriaIGV() {
		return sumatoriaIGV;
	}

	public void setSumatoriaIGV(BigDecimal sumatoriaIGV) {
		this.sumatoriaIGV = sumatoriaIGV;
	}

	public BigDecimal getSumatoriaISC() {
		return sumatoriaISC;
	}

	public void setSumatoriaISC(BigDecimal sumatoriaISC) {
		this.sumatoriaISC = sumatoriaISC;
	}

	public BigDecimal getTotalDescuentos() {
		return TotalDescuentos;
	}

	public void setTotalDescuentos(BigDecimal totalDescuentos) {
		TotalDescuentos = totalDescuentos;
	}

	public BigDecimal getImporteTotalVenta() {
		return ImporteTotalVenta;
	}

	public void setImporteTotalVenta(BigDecimal importeTotalVenta) {
		ImporteTotalVenta = importeTotalVenta;
	}

	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}

	public double getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}

	public void setPorcentajeImpuesto(double porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
}
