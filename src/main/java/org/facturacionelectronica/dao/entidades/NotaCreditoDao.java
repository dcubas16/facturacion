package org.facturacionelectronica.dao.entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.facturacionelectronica.entidades.CabeceraNotaCredito;

@Entity
@Table(name = "NOTA_CREDITO", uniqueConstraints = { @UniqueConstraint(columnNames = "ID_NOTA_CREDITO") })
@Inheritance(strategy = InheritanceType.JOINED)
public class NotaCreditoDao {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notaCreditoDao", cascade = CascadeType.REMOVE)
	private Set<DetalleNotaCreditoDao> detalleNotaCreditoDao;

	@Id
	@Column(name = "ID_NOTA_CREDITO", unique = true, nullable = false)
	private String idNotaCredito;

	@Column(name = "FECHA_EMISION", nullable = true)
	private Date fechaEmision;

	@Column(name = "FIRMA_DIGITAL", nullable = true)
	private String firmaDigital;

	@Column(name = "RAZON_SOCIAL", nullable = true, length = 100)
	private String razonSocial;

	@Column(name = "NOMBRE_COMERCIAL", nullable = true, length = 100)
	private String nombreComercial;

	@Column(name = "CODIGO_UBIGEO", nullable = true, length = 6)
	private String codigoUbigeo;

	@Column(name = "DIRECCION_COMPLETA", nullable = true, length = 100)
	private String direccionCompleta;

	@Column(name = "URBANIZACION", nullable = true, length = 25)
	private String urbanizacion;

	@Column(name = "PROVINCIA", nullable = true, length = 30)
	private String provincia;

	@Column(name = "DEPARTAMENTO", nullable = true, length = 30)
	private String departamento;

	@Column(name = "DISTRITO", nullable = true, length = 30)
	private String distrito;

	@Column(name = "CODIGO_PAIS", nullable = true, length = 2)
	private String codigoPais;

	@Column(name = "NUMERO_DOC", nullable = true, precision = 11, scale = 0)
	private BigInteger numeroDocumento;

	@Column(name = "TIPO_DOCUMENTO", nullable = true, length = 1)
	private int tipoDocumento;

	@Column(name = "SERIE_DOC_AFECTADO", nullable = true, length = 4)
	private String serieDocumentoAfectado;

	@Column(name = "NUMERO_CORRELATIVO_DOC_AFECTADO", nullable = true, length = 8)
	private String numeroCorrelativoDocumentoAfectado;
	
	@Column(name = "TIPO_NOTA_CREDITO", nullable = true, length = 2)
	private String tipoNotaCredito;//El codigo del motivo de la nota de credito del documento afectado

	@Column(name = "SERIE_NOTA_CREDITO", nullable = true, length = 4)
	private String serieNotaCredito;

	@Column(name = "NUMERO_CORRELATIVO_NOTA_CREDITO", nullable = true, length = 8)
	private String numeroCorrelativoNotaCredito;

	@Column(name = "NUMERO_DOC_CLIENTE", nullable = true, length = 15)
	private String numeroDocumentoCliente;

	@Column(name = "TIPO_DOC_CLIENTE", nullable = true, length = 1)
	private String tipoDocumentoCliente;

	@Column(name = "RAZON_SOCIAL_CLIENTE", nullable = true, length = 100)
	private String razonSocialCliente;

	@Column(name = "MOTIVO", nullable = true, length = 250)
	private String motivo;

	@Column(name = "TOTAL_VALOR_VENTA_OP_GRAVADA", nullable = true, precision = 14, scale = 2)
	private BigDecimal totalValorVentaOpGravadas;

	@Column(name = "TOTAL_VALOR_VENTA_OP_INAFEC", nullable = true, precision = 14, scale = 2)
	private BigDecimal totalValorVentaOpInafecta;

	@Column(name = "TOTAL_VALOR_VENTA_OP_EXONERA", nullable = true, precision = 15, scale = 2)
	private BigDecimal totalValorVentaOpExoneradas;

	@Column(name = "TOTAL_VALOR_VENTA_OP_GRAT", nullable = true, precision = 15, scale = 2)
	private BigDecimal totalValorVentaOpGratuitas;

	@Column(name = "SUMATORIA_IGV", nullable = true, precision = 14, scale = 2)
	private BigDecimal sumatoriaIGV;

	@Column(name = "SUMATORIA_ISC", nullable = true, precision = 14, scale = 2)
	private BigDecimal sumatoriaISC;

	@Column(name = "TOTAL_DESCUENTOS", nullable = true, precision = 14, scale = 2)
	private BigDecimal totalDescuentos;

	@Column(name = "IMPORTE_TOTAL_VENTA", nullable = true, precision = 14, scale = 2)
	private BigDecimal importeTotalVenta;

	@Column(name = "MONEDA", nullable = true, length = 3)
	private String moneda;

	@Column(name = "ESTADO", nullable = true)
	private int estado;// 0=aceptada ------ 5=Importada a base de datos --- 6=Pendiente Envio ------ -1
						// o Otro codigo =Error

	@Column(name = "MENSAJE_ENVIO", nullable = true, length = 1000)
	private String mensajeEnvio;

	@Column(name = "MENSAJE_RESPUESTA", nullable = true, length = 1000)
	private String mensajeRespuesta;

	@Column(name = "FECHA_ENVIO", nullable = true)
	private Date fechaEnvio;

	@Column(name = "FECHA_RESPUESTA", nullable = true)
	private Date fechaRespuesta;

	@Column(name = "TELEFONO_EMISOR", nullable = true)
	private String telefonoEmisor;

	@Column(name = "PACIENTE", nullable = true)
	private String paciente;

	@Column(name = "DIRECCION_PACIENTE", nullable = true)
	private String direccionPaciente;

	@Column(name = "MEDIO_PAGO", nullable = true)
	private String medioPago;

	@Column(name = "TIPO_CAMBIO", nullable = true)
	private String tipoCambio;
	
	@Column(name = "ID_CUSTOMIZATION", nullable = true)
	private String idCustomization;
	
	@Column(name = "VERSION_UBL", nullable = true)
	private String versionUBL;

	public NotaCreditoDao() {
		super();
	}

	
	public NotaCreditoDao(CabeceraNotaCredito cabeceraNotaCredito) {
		super();
		this.idNotaCredito = cabeceraNotaCredito.getIdNotaCredito();
		this.fechaEmision = cabeceraNotaCredito.getFechaEmision();
		this.firmaDigital = cabeceraNotaCredito.getFirmaDigital();
		this.razonSocial = cabeceraNotaCredito.getRazonSocial();
		this.nombreComercial = cabeceraNotaCredito.getNombreComercial();
		this.codigoUbigeo = cabeceraNotaCredito.getCodigoUbigeo();
		this.direccionCompleta = cabeceraNotaCredito.getDireccionCompleta();
		this.urbanizacion = cabeceraNotaCredito.getUrbanizacion();
		this.provincia = cabeceraNotaCredito.getProvincia();
		this.departamento = cabeceraNotaCredito.getDepartamento();
		this.distrito = cabeceraNotaCredito.getDistrito();
		this.codigoPais = cabeceraNotaCredito.getCodigoPais();
		this.numeroDocumento = cabeceraNotaCredito.getNumeroDocumento();
		this.tipoDocumento = cabeceraNotaCredito.getTipoDocumento();
		this.serieDocumentoAfectado = cabeceraNotaCredito.getSerieDocumentoAfectado();
		this.numeroCorrelativoDocumentoAfectado = cabeceraNotaCredito.getNumeroCorrelativoDocumentoAfectado();
		this.tipoNotaCredito = cabeceraNotaCredito.getTipoNotaCredito();
		this.serieNotaCredito = cabeceraNotaCredito.getSerieNotaCredito();
		this.numeroCorrelativoNotaCredito = cabeceraNotaCredito.getNumeroCorrelativoNotaCredito();
		this.numeroDocumentoCliente = cabeceraNotaCredito.getNumeroDocumentoCliente();
		this.tipoDocumentoCliente = cabeceraNotaCredito.getTipoDocumentoCliente();
		this.razonSocialCliente = cabeceraNotaCredito.getRazonSocialCliente();
		this.motivo = cabeceraNotaCredito.getMotivo();
		this.totalValorVentaOpGravadas = cabeceraNotaCredito.getTotalValorVentaOpGravadas();
		this.totalValorVentaOpInafecta = cabeceraNotaCredito.getTotalValorVentaOpInafecta();
		this.totalValorVentaOpExoneradas = cabeceraNotaCredito.getTotalValorVentaOpExoneradas();
		this.totalValorVentaOpGratuitas = cabeceraNotaCredito.getTotalValorVentaOpGratuitas();
		this.sumatoriaIGV = cabeceraNotaCredito.getSumatoriaIGV();
		this.sumatoriaISC = cabeceraNotaCredito.getSumatoriaISC();
		this.totalDescuentos = cabeceraNotaCredito.getTotalDescuentos();
		this.importeTotalVenta = cabeceraNotaCredito.getImporteTotalVenta();
		this.moneda = cabeceraNotaCredito.getMoneda();
		this.estado = cabeceraNotaCredito.getEstado();
		this.mensajeEnvio = cabeceraNotaCredito.getMensajeEnvio();
		this.mensajeRespuesta = cabeceraNotaCredito.getMensajeRespuesta();
		this.fechaEnvio = cabeceraNotaCredito.getFechaEnvio();
		this.fechaRespuesta = cabeceraNotaCredito.getFechaRespuesta();
		this.telefonoEmisor = cabeceraNotaCredito.getTelefonoEmisor();
		this.paciente = cabeceraNotaCredito.getPaciente();
		this.direccionPaciente = cabeceraNotaCredito.getDireccionPaciente();
		this.medioPago = cabeceraNotaCredito.getMedioPago();
		this.tipoCambio = cabeceraNotaCredito.getTipoCambio();
		this.idCustomization = cabeceraNotaCredito.getIdCustomization();
		this.versionUBL = cabeceraNotaCredito.getVersionUBL();
	}

	
	public NotaCreditoDao(Set<DetalleNotaCreditoDao> detalleNotaCreditoDao, String idNotaCredito, Date fechaEmision,
			String firmaDigital, String razonSocial, String nombreComercial, String codigoUbigeo,
			String direccionCompleta, String urbanizacion, String provincia, String departamento, String distrito,
			String codigoPais, BigInteger numeroDocumento, int tipoDocumento, String serieDocumentoAfectado,
			String numeroCorrelativoDocumentoAfectado, String tipoNotaCredito, String serieNotaCredito,
			String numeroCorrelativoNotaCredito, String numeroDocumentoCliente, String tipoDocumentoCliente,
			String razonSocialCliente, String motivo, BigDecimal totalValorVentaOpGravadas,
			BigDecimal totalValorVentaOpInafecta, BigDecimal totalValorVentaOpExoneradas,
			BigDecimal totalValorVentaOpGratuitas, BigDecimal sumatoriaIGV, BigDecimal sumatoriaISC,
			BigDecimal totalDescuentos, BigDecimal importeTotalVenta, String moneda, int estado, String mensajeEnvio,
			String mensajeRespuesta, Date fechaEnvio, Date fechaRespuesta, String telefonoEmisor, String paciente,
			String direccionPaciente, String medioPago, String tipoCambio, String idCustomization, String versionUBL) {
		super();
		this.detalleNotaCreditoDao = detalleNotaCreditoDao;
		this.idNotaCredito = idNotaCredito;
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
		this.serieDocumentoAfectado = serieDocumentoAfectado;
		this.numeroCorrelativoDocumentoAfectado = numeroCorrelativoDocumentoAfectado;
		this.tipoNotaCredito = tipoNotaCredito;
		this.serieNotaCredito = serieNotaCredito;
		this.numeroCorrelativoNotaCredito = numeroCorrelativoNotaCredito;
		this.numeroDocumentoCliente = numeroDocumentoCliente;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
		this.razonSocialCliente = razonSocialCliente;
		this.motivo = motivo;
		this.totalValorVentaOpGravadas = totalValorVentaOpGravadas;
		this.totalValorVentaOpInafecta = totalValorVentaOpInafecta;
		this.totalValorVentaOpExoneradas = totalValorVentaOpExoneradas;
		this.totalValorVentaOpGratuitas = totalValorVentaOpGratuitas;
		this.sumatoriaIGV = sumatoriaIGV;
		this.sumatoriaISC = sumatoriaISC;
		this.totalDescuentos = totalDescuentos;
		this.importeTotalVenta = importeTotalVenta;
		this.moneda = moneda;
		this.estado = estado;
		this.mensajeEnvio = mensajeEnvio;
		this.mensajeRespuesta = mensajeRespuesta;
		this.fechaEnvio = fechaEnvio;
		this.fechaRespuesta = fechaRespuesta;
		this.telefonoEmisor = telefonoEmisor;
		this.paciente = paciente;
		this.direccionPaciente = direccionPaciente;
		this.medioPago = medioPago;
		this.tipoCambio = tipoCambio;
		this.idCustomization = idCustomization;
		this.versionUBL = versionUBL;
	}





	public Set<DetalleNotaCreditoDao> getDetalleNotaCreditoDao() {
		return detalleNotaCreditoDao;
	}

	public void setDetalleNotaCreditoDao(Set<DetalleNotaCreditoDao> detalleNotaCreditoDao) {
		this.detalleNotaCreditoDao = detalleNotaCreditoDao;
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
