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

import org.facturacionelectronica.entidades.CabeceraFactura;


@Entity
@Table(name = "FACTURA", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_FACTURA")})
@Inheritance(strategy=InheritanceType.JOINED)
public class FacturaDao {
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="facturaDao", cascade=CascadeType.REMOVE)
	private Set<DetalleFacturaDao> detalleFacturaDao;
	
	
	@Id
	@Column(name = "ID_FACTURA", unique = true, nullable = false)
	private String idFactura;
		
	@Column(name = "FECHA_EMISION", nullable=false)
	private Date fechaEmision;
	
//	@Column(name = "ID_CUSTOMIZACION", nullable=true)
//	private String idCustomization;
	
//	@Column(name = "FIRMA_DIGITAL", nullable=true)
//	private String firmaDigital;
	
	@Column(name = "RAZON_SOCIAL", nullable=false, length=100)
	private String razonSocial;
	
	@Column(name = "NOMBRE_COMERCIAL", nullable=false, length=100)
	private String nombreComercial;
	
	@Column(name = "CODIGO_UBIGEO", nullable=true, length=6)
	private String codigoUbigeo;
	
	@Column(name = "DIRECCION_COMPLETA", nullable=true, length=100)
	private String direccionCompleta;

	@Column(name = "URBANIZACION", nullable=true, length=25)
	private String urbanizacion;

	@Column(name = "PROVINCIA", nullable=true, length=30)
	private String provincia;

	@Column(name = "DEPARTAMENTO", nullable=true, length=30)
	private String departamento;

	@Column(name = "DISTRITO", nullable=true, length=30)
	private String distrito;

	@Column(name = "CODIGO_PAIS", nullable=true, length=2)
	private String codigoPais;

	@Column(name = "NUMERO_DOC", nullable=false, precision=11, scale=0)
	private BigInteger numeroDocumento;

	@Column(name = "TIPO_DOCUMENTO", nullable=false, length=1)
	private int tipoDocumento;

	@Column(name = "TIPO_DOC_FACTURA", nullable=false, length=2)
	private String tipoDocumentoFactura;

	@Column(name = "SERIE", nullable=false, length=4)
	private String serie;

	@Column(name = "NUMERO_CORRELATIVO", nullable=false, length=8)
	private String numeroCorrelativo;


	@Column(name = "NUMERO_DOC_CLIENTE", nullable=false, length=15)
	private String numeroDocumentoCliente;

	@Column(name = "TIPO_DOC_CLIENTE", nullable=false, length=1)
	private String tipoDocumentoCliente;

	@Column(name = "RAZON_SOCIAL_CLIENTE", nullable=false, length=100)
	private String razonSocialCliente;


	@Column(name = "TOTAL_VALOR_VENTA_OP_GRAVADA", nullable=false, precision=14, scale=2)
	private BigDecimal totalValorVentaOpGravadas;

	@Column(name = "TOTAL_VALOR_VENTA_OP_INAFEC", nullable=true, precision=14, scale=2)
	private BigDecimal totalValorVentaOpInafecta;

	@Column(name = "TOTAL_VALOR_VENTA_OP_EXONERA", nullable=true, precision=15, scale=2)
	private BigDecimal totalValorVentaOpExoneradas;

	@Column(name = "TOTAL_VALOR_VENTA_OP_GRAT", nullable=true, precision=15, scale=2)
	private BigDecimal totalValorVentaOpGratuitas;

	@Column(name = "SUMATORIA_IGV", nullable=true, precision=14, scale=2)
	private BigDecimal sumatoriaIGV;

	@Column(name = "SUMATORIA_ISC", nullable=true, precision=14, scale=2)
	private BigDecimal sumatoriaISC;

	@Column(name = "TOTAL_DESCUENTOS", nullable=true, precision=14, scale=2)
	private BigDecimal totalDescuentos;

	@Column(name = "IMPORTE_TOTAL_VENTA", nullable=false, precision=14, scale=2)
	private BigDecimal importeTotalVenta;

	@Column(name = "LEYENDA", nullable=true, length=100)
	private String leyenda;

	@Column(name = "PORCENTAJE_IMPUESTO", nullable=true, precision=4, scale=2)
	private double porcentajeImpuesto;

	@Column(name = "MONEDA", nullable=false, length=3)
	private String moneda;
	
	
	
	@Column(name = "ESTADO", nullable=true)
	private int estado;// 0=aceptada ------ 5=Importada a base de datos --- 6=Pendiente Envio ------ otro numero: codigo error
	
	@Column(name = "MENSAJE_ENVIO", nullable=true, length=1000)
	private String mensajeEnvio;
	
	@Column(name = "MENSAJE_RESPUESTA", nullable=true, length=1000)
	private String mensajeRespuesta;
	
	@Column(name = "FECHA_ENVIO", nullable=true)
	private Date fechaEnvio;
	
	@Column(name = "FECHA_RESPUESTA", nullable=true)
	private Date fechaRespuesta;
	
	@Column(name = "TELEFONO_EMISOR", nullable=true)
	private String telefonoEmisor;
	
	@Column(name = "PACIENTE", nullable=true)
	private String paciente;
	
	@Column(name = "DIRECCION_PACIENTE", nullable=true)
	private String direccionPaciente;
	
	@Column(name = "MEDIO_PAGO", nullable=true)
	private String medioPago;
	
	@Column(name = "TIPO_CAMBIO", nullable=true)
	private String tipoCambio;
	
		
	public FacturaDao() {}
	
	public FacturaDao(CabeceraFactura cabeceraFactura) {
		super();
		this.idFactura = cabeceraFactura.getIdFactura();
		this.fechaEmision = cabeceraFactura.getFechaEmision();
		this.razonSocial = cabeceraFactura.getRazonSocial();
		this.nombreComercial = cabeceraFactura.getNombreComercial();
		this.codigoUbigeo = cabeceraFactura.getCodigoUbigeo();
		this.direccionCompleta = cabeceraFactura.getDireccionCompleta();
		this.urbanizacion = cabeceraFactura.getUrbanizacion();
		this.provincia = cabeceraFactura.getProvincia();
		this.departamento = cabeceraFactura.getDepartamento();
		this.distrito = cabeceraFactura.getDistrito();
		this.codigoPais = cabeceraFactura.getCodigoPais();
		this.numeroDocumento = cabeceraFactura.getNumeroDocumento();
		this.tipoDocumento = cabeceraFactura.getTipoDocumento();
		this.tipoDocumentoFactura = cabeceraFactura.getTipoDocumentoFactura();
		this.serie = cabeceraFactura.getSerie();
		this.numeroCorrelativo = cabeceraFactura.getNumeroCorrelativo();
		this.numeroDocumentoCliente = cabeceraFactura.getNumeroDocumentoCliente();
		this.tipoDocumentoCliente = cabeceraFactura.getTipoDocumentoCliente();
		this.razonSocialCliente = cabeceraFactura. getRazonSocialCliente();
		this.totalValorVentaOpGravadas = cabeceraFactura.getTotalValorVentaOpGravadas();
		this.totalValorVentaOpInafecta = cabeceraFactura.getTotalValorVentaOpInafecta();
		this.totalValorVentaOpExoneradas = cabeceraFactura.getTotalValorVentaOpExoneradas();
		this.totalValorVentaOpGratuitas = cabeceraFactura.getTotalValorVentaOpGratuitas();
		this.sumatoriaIGV = cabeceraFactura.getSumatoriaIGV();
		this.sumatoriaISC = cabeceraFactura.getSumatoriaISC();
		this.totalDescuentos = cabeceraFactura.getTotalDescuentos();
		this.importeTotalVenta = cabeceraFactura.getImporteTotalVenta();
		this.leyenda = cabeceraFactura.getLeyenda();
		
		
		this.paciente = cabeceraFactura.getPaciente();
		this.direccionPaciente = cabeceraFactura.getDireccionPaciente();
		this.tipoCambio = cabeceraFactura.getTipoCambio();
		this.medioPago = cabeceraFactura.getMedioPago();
		this.telefonoEmisor = cabeceraFactura.getTelefonoEmisor();
		
//------------->OJO
		this.porcentajeImpuesto = 18.0;
		this.moneda = "";
	}
		
	public FacturaDao(String idFactura, Date fechaEmision, String razonSocial,
			String nombreComercial, String codigoUbigeo, String direccionCompleta, String urbanizacion,
			String provincia, String departamento, String distrito, String codigoPais, BigInteger numeroDocumento,
			int tipoDocumento, String tipoDocumentoFactura, String serie, String numeroCorrelativo,
			String numeroDocumentoCliente, String tipoDocumentoCliente, String razonSocialCliente,
			BigDecimal totalValorVentaOpGravadas, BigDecimal totalValorVentaOpInafecta,
			BigDecimal totalValorVentaOpExoneradas, BigDecimal totalValorVentaOpGratuitas, BigDecimal sumatoriaIGV,
			BigDecimal sumatoriaISC, BigDecimal totalDescuentos, BigDecimal importeTotalVenta, String leyenda,
			double porcentajeImpuesto, String moneda, int estado, String mensajeEnvio, String mensajeRespuesta,
			Date fechaEnvio, Date fechaRespuesta, String telefonoEmisor, String paciente, String direccionPaciente,
			String medioPago, String tipoCambio) {
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
		this.totalDescuentos = totalDescuentos;
		this.importeTotalVenta = importeTotalVenta;
		this.leyenda = leyenda;
		this.porcentajeImpuesto = porcentajeImpuesto;
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

//	public Date getFechaEnvio() {
//		return fechaEnvio;
//	}
//
//	public void setFechaEnvio(Date fechaEnvio) {
//		this.fechaEnvio = fechaEnvio;
//	}
//
//	public Date getFechaRespuesta() {
//		return fechaRespuesta;
//	}
//
//	public void setFechaRespuesta(Date fechaRespuesta) {
//		this.fechaRespuesta = fechaRespuesta;
//	}
	
}
