package org.facturacionelectronica.entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CabeceraFactura {

	private String idFactura;
	private String idCustomization;
	
	private Date fechaEmision;
	private String firmaDigital;
	private String razonSocial;
	private String nombreComercial;
	private String codigoUbigeo;
	private String direccionCompleta;
	private String urbanizacion;
	private String provincia;
	private String departamento;
	private String distrito;
	private String codigoPais;
	private BigInteger numeroDocumento;
	private int tipoDocumento;
	private String tipoDocumentoFactura;
	private String serie;
	private String numeroCorrelativo;
	
	private String numeroDocumentoCliente;
	private String tipoDocumentoCliente;
	private String razonSocialCliente;
	
	private BigDecimal totalValorVentaOpGravadas;
	private BigDecimal totalValorVentaOpInafecta;
	private BigDecimal totalValorVentaOpExoneradas;
	private BigDecimal totalValorVentaOpGratuitas;
	private BigDecimal sumatoriaIGV;
	private BigDecimal sumatoriaISC;
	private BigDecimal TotalDescuentos;
	private BigDecimal ImporteTotalVenta;
	
	private String leyenda;

	public CabeceraFactura() {}
		
	public CabeceraFactura(String idFactura, String idCustomization, Date fechaEmision, String firmaDigital,
			String razonSocial, String nombreComercial, String codigoUbigeo, String direccionCompleta,
			String urbanizacion, String provincia, String departamento, String distrito, String codigoPais,
			BigInteger numeroDocumento, int tipoDocumento, String tipoDocumentoFactura, String serie,
			String numeroCorrelativo, String numeroDocumentoCliente, String tipoDocumentoCliente,
			String razonSocialCliente, BigDecimal totalValorVentaOpGravadas, BigDecimal totalValorVentaOpInafecta,
			BigDecimal totalValorVentaOpExoneradas, BigDecimal totalValorVentaOpGratuitas, BigDecimal sumatoriaIGV,
			BigDecimal sumatoriaISC, BigDecimal totalDescuentos, BigDecimal importeTotalVenta, String leyenda) {
		super();
		this.idFactura = idFactura;
		this.idCustomization = idCustomization;
		this.fechaEmision = fechaEmision;
		this.firmaDigital = firmaDigital;
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
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
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

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public String getIdCustomization() {
		return idCustomization;
	}

	public void setIdCustomization(String idCustomization) {
		this.idCustomization = idCustomization;
	}
	
}
