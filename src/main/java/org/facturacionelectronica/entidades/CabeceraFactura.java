package org.facturacionelectronica.entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.facturacionelectronica.dao.entidades.FacturaDao;

public class CabeceraFactura {

	private String idFactura;
	private String idCustomization;//37
	
	private Date fechaEmision;//1
	private String firmaDigital;//2
	private String razonSocial;//3
	private String nombreComercial;//4
	private String codigoUbigeo;//5
	private String direccionCompleta;//5
	private String urbanizacion;//5
	private String provincia;//5
	private String departamento;//5
	private String distrito;//5
	private String codigoPais;//5
	private BigInteger numeroDocumento;//6
	private int tipoDocumento;//7
	private String tipoDocumentoFactura;
	private String serie;//8
	private String numeroCorrelativo;//8
	
	private String numeroDocumentoCliente;//10
	private String tipoDocumentoCliente;//9
	private String razonSocialCliente;
	
	private BigDecimal totalValorVentaOpGravadas;//18
	private BigDecimal totalValorVentaOpInafecta;//19
	private BigDecimal totalValorVentaOpExoneradas;//20
	private BigDecimal totalValorVentaOpGratuitas;//49
	private BigDecimal sumatoriaIGV;
	private BigDecimal sumatoriaISC;
	private BigDecimal totalDescuentos;
	private BigDecimal importeTotalVenta;//27
	
	private String moneda;//28
	private String versionUBL;//36
	
	
	private String leyenda;

	public CabeceraFactura() {}
	
	public CabeceraFactura(FacturaDao facturaDao) {
		super();
		this.idFactura = facturaDao.getIdFactura();
//		this.idCustomization = facturaDao.idCustomization;
		this.fechaEmision = facturaDao.getFechaEmision();
//		this.firmaDigital = facturaDao.firmaDigital;
		this.razonSocial = facturaDao.getRazonSocial();
		this.nombreComercial = facturaDao.getNombreComercial();
		this.codigoUbigeo = facturaDao.getCodigoUbigeo();
		this.direccionCompleta = facturaDao.getDireccionCompleta();
		this.urbanizacion = facturaDao.getUrbanizacion();
		this.provincia = facturaDao.getProvincia();
		this.departamento = facturaDao.getDepartamento();
		this.distrito = facturaDao.getDistrito();
		this.codigoPais = facturaDao.getCodigoPais();
		this.numeroDocumento = facturaDao.getNumeroDocumento();
		this.tipoDocumento = facturaDao.getTipoDocumento();
		this.tipoDocumentoFactura = facturaDao.getTipoDocumentoFactura();
		this.serie = facturaDao.getSerie();
		this.numeroCorrelativo = facturaDao.getNumeroCorrelativo();
		this.numeroDocumentoCliente = facturaDao.getNumeroDocumentoCliente();
		this.tipoDocumentoCliente = facturaDao.getTipoDocumentoCliente();
		this.razonSocialCliente = facturaDao.getRazonSocialCliente();
		this.totalValorVentaOpGravadas = facturaDao.getTotalValorVentaOpGravadas();
		this.totalValorVentaOpInafecta = facturaDao.getTotalValorVentaOpInafecta();
		this.totalValorVentaOpExoneradas = facturaDao.getTotalValorVentaOpExoneradas();
		this.totalValorVentaOpGratuitas = facturaDao.getTotalValorVentaOpGratuitas();
		this.sumatoriaIGV = facturaDao.getSumatoriaIGV();
		this.sumatoriaISC = facturaDao.getSumatoriaISC();
		this.totalDescuentos = facturaDao.getTotalDescuentos();
		this.importeTotalVenta = facturaDao.getImporteTotalVenta();
		this.leyenda = facturaDao.getLeyenda();
	}
		
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
		this.totalDescuentos = totalDescuentos;
		this.importeTotalVenta = importeTotalVenta;
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
		return totalDescuentos;
	}

	public void setTotalDescuentos(BigDecimal totalDescuentos) {
		this.totalDescuentos = totalDescuentos;
	}

	public BigDecimal getImporteTotalVenta() {
		return importeTotalVenta;
	}

	public void setImporteTotalVenta(BigDecimal importeTotalVenta) {
		this.importeTotalVenta = importeTotalVenta;
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

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getVersionUBL() {
		return versionUBL;
	}

	public void setVersionUBL(String versionUBL) {
		this.versionUBL = versionUBL;
	}
	
}
