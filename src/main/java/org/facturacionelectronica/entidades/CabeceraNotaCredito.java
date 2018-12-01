package org.facturacionelectronica.entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.facturacionelectronica.dao.entidades.NotaCreditoDao;

public class CabeceraNotaCredito {

	private String idNotaCredito;
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
	private String serieDocumentoAfectado;
	private String numeroCorrelativoDocumentoAfectado;
	private String tipoNotaCredito;
	private String serieNotaCredito;
	private String numeroCorrelativoNotaCredito;
	private String numeroDocumentoCliente;
	private String tipoDocumentoCliente;
	private String razonSocialCliente;
	private String motivo;

	private BigDecimal totalValorVentaOpGravadas;
	private BigDecimal totalValorVentaOpInafecta;
	private BigDecimal totalValorVentaOpExoneradas;
	private BigDecimal totalValorVentaOpGratuitas;
	private BigDecimal sumatoriaIGV;
	private BigDecimal sumatoriaISC;
	private BigDecimal totalDescuentos;
	private BigDecimal importeTotalVenta;
	private String moneda;
	private int estado;// 0=aceptada ------ 5=Importada a base de datos --- 6=Pendiente Envio ------ -1
						// o Otro codigo =Error

	private String mensajeEnvio;
	private String mensajeRespuesta;
	private Date fechaEnvio;
	private Date fechaRespuesta;
	private String telefonoEmisor;
	private String paciente;
	private String direccionPaciente;
	private String medioPago;
	private String tipoCambio;
	
	private String idCustomization;
	private String versionUBL;
	
	public CabeceraNotaCredito() {}
	
	public CabeceraNotaCredito(NotaCreditoDao notaCreditoDao) {
		super();
		this.idNotaCredito = notaCreditoDao.getIdNotaCredito();
		this.fechaEmision = notaCreditoDao.getFechaEmision();
		this.firmaDigital = notaCreditoDao.getFirmaDigital();
		this.razonSocial = notaCreditoDao.getRazonSocial();
		this.nombreComercial = notaCreditoDao.getNombreComercial();
		this.codigoUbigeo = notaCreditoDao.getCodigoUbigeo();
		this.direccionCompleta = notaCreditoDao.getDireccionCompleta();
		this.urbanizacion = notaCreditoDao.getUrbanizacion();
		this.provincia = notaCreditoDao.getProvincia();
		this.departamento = notaCreditoDao.getDepartamento();
		this.distrito = notaCreditoDao.getDistrito();
		this.codigoPais = notaCreditoDao.getCodigoPais();
		this.numeroDocumento = notaCreditoDao.getNumeroDocumento();
		this.tipoDocumento = notaCreditoDao.getTipoDocumento();
		this.serieDocumentoAfectado = notaCreditoDao.getSerieDocumentoAfectado();
		this.numeroCorrelativoDocumentoAfectado = notaCreditoDao.getNumeroCorrelativoDocumentoAfectado();
		this.tipoNotaCredito = notaCreditoDao.getTipoNotaCredito();
		this.serieNotaCredito = notaCreditoDao.getSerieNotaCredito();
		this.numeroCorrelativoNotaCredito = notaCreditoDao.getNumeroCorrelativoNotaCredito();
		this.numeroDocumentoCliente = notaCreditoDao.getNumeroDocumentoCliente();
		this.tipoDocumentoCliente = notaCreditoDao.getTipoDocumentoCliente();
		this.razonSocialCliente = notaCreditoDao.getRazonSocialCliente();
		this.motivo = notaCreditoDao.getMotivo();
		this.totalValorVentaOpGravadas = notaCreditoDao.getTotalValorVentaOpGravadas();
		this.totalValorVentaOpInafecta = notaCreditoDao.getTotalValorVentaOpInafecta();
		this.totalValorVentaOpExoneradas = notaCreditoDao.getTotalValorVentaOpExoneradas();
		this.totalValorVentaOpGratuitas = notaCreditoDao.getTotalValorVentaOpGratuitas();
		this.sumatoriaIGV = notaCreditoDao.getSumatoriaIGV();
		this.sumatoriaISC = notaCreditoDao.getSumatoriaISC();
		this.totalDescuentos = notaCreditoDao.getTotalDescuentos();
		this.importeTotalVenta = notaCreditoDao.getImporteTotalVenta();
		this.moneda = notaCreditoDao.getMoneda();
		this.estado = notaCreditoDao.getEstado();
		this.mensajeEnvio = notaCreditoDao.getMensajeEnvio();
		this.mensajeRespuesta = notaCreditoDao.getMensajeRespuesta();
		this.fechaEnvio = notaCreditoDao.getFechaEnvio();
		this.fechaRespuesta = notaCreditoDao.getFechaRespuesta();
		this.telefonoEmisor = notaCreditoDao.getTelefonoEmisor();
		this.paciente = notaCreditoDao.getPaciente();
		this.direccionPaciente = notaCreditoDao.getDireccionPaciente();
		this.medioPago = notaCreditoDao.getMedioPago();
		this.tipoCambio = notaCreditoDao.getTipoCambio();
		this.idCustomization = notaCreditoDao.getIdCustomization();
		this.versionUBL = notaCreditoDao.getVersionUBL();
	}
	
	
	
	public String getIdNotaCredito() {
		return idNotaCredito;
	}
	public void setIdNotaCredito(String idNotaCredito) {
		this.idNotaCredito = idNotaCredito;
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
	public String getSerieDocumentoAfectado() {
		return serieDocumentoAfectado;
	}
	public void setSerieDocumentoAfectado(String serieDocumentoAfectado) {
		this.serieDocumentoAfectado = serieDocumentoAfectado;
	}
	public String getNumeroCorrelativoDocumentoAfectado() {
		return numeroCorrelativoDocumentoAfectado;
	}
	public void setNumeroCorrelativoDocumentoAfectado(String numeroCorrelativoDocumentoAfectado) {
		this.numeroCorrelativoDocumentoAfectado = numeroCorrelativoDocumentoAfectado;
	}
	public String getTipoNotaCredito() {
		return tipoNotaCredito;
	}
	public void setTipoNotaCredito(String tipoNotaCredito) {
		this.tipoNotaCredito = tipoNotaCredito;
	}
	public String getSerieNotaCredito() {
		return serieNotaCredito;
	}
	public void setSerieNotaCredito(String serieNotaCredito) {
		this.serieNotaCredito = serieNotaCredito;
	}
	public String getNumeroCorrelativoNotaCredito() {
		return numeroCorrelativoNotaCredito;
	}
	public void setNumeroCorrelativoNotaCredito(String numeroCorrelativoNotaCredito) {
		this.numeroCorrelativoNotaCredito = numeroCorrelativoNotaCredito;
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
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
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
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getMensajeEnvio() {
		return mensajeEnvio;
	}
	public void setMensajeEnvio(String mensajeEnvio) {
		this.mensajeEnvio = mensajeEnvio;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
	public String getTelefonoEmisor() {
		return telefonoEmisor;
	}
	public void setTelefonoEmisor(String telefonoEmisor) {
		this.telefonoEmisor = telefonoEmisor;
	}
	public String getPaciente() {
		return paciente;
	}
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public String getDireccionPaciente() {
		return direccionPaciente;
	}
	public void setDireccionPaciente(String direccionPaciente) {
		this.direccionPaciente = direccionPaciente;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getIdCustomization() {
		return idCustomization;
	}

	public void setIdCustomization(String idCustomization) {
		this.idCustomization = idCustomization;
	}

	public String getVersionUBL() {
		return versionUBL;
	}

	public void setVersionUBL(String versionUBL) {
		this.versionUBL = versionUBL;
	}
	
	
}
