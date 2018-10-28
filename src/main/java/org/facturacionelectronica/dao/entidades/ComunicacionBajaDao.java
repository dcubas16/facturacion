package org.facturacionelectronica.dao.entidades;

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

import org.facturacionelectronica.entidades.ComunicacionBaja;

@Entity
@Table(name = "COMUNICA_BAJA", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_COMUNICA_BAJA")})
@Inheritance(strategy=InheritanceType.JOINED)
public class ComunicacionBajaDao {
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="comunicacionBajaDao", cascade=CascadeType.REMOVE)
	private Set<DetalleComunicaBajaDao> detalleComunicaBajaDao;
	

	@Id
	@Column(name = "ID_COMUNICA_BAJA", unique = true, nullable = false)
	private String idComunicaionBaja;
	
	@Column(name = "RAZON_SOCIAL", nullable=false, length=100)
	private String razonSocial;
	
	@Column(name = "NUMERO_RUC", nullable=false, length=11, scale = 0)
	private BigInteger numeroRuc;
	
	@Column(name = "TIPO_DOCUMENTO", nullable=false, length=2)
	private int tipoDocumento;
	
	@Column(name = "FECHA_GENERA_DOC", nullable=false)
	private Date fechaGeneracionDocumento;
	
	@Column(name = "FECHA_GENERA_COMUNICA", nullable=false)
	private Date fechaGeneraComunica;
	
	@Column(name = "FIRMA_DIGITAL", nullable=true)
	private String firmaDigital;
	
	@Column(name = "VERSION_UBL", nullable=false, length=10)
	private String versionUbl;
	
	@Column(name = "VERSION_ESTRUC_DOC", nullable=false, length=10)
	private String versionEstrucDoc;
	
	@Column(name = "ESTADO", nullable=false, length=1)
	private int estado;//0:Aceptrado   3:Importado  Otro:Con error
	
	@Column(name = "RESPUESTA", nullable=true, length=100)
	private String respuesta;

	public ComunicacionBajaDao() {}
	
	public ComunicacionBajaDao(ComunicacionBaja comunicacionBaja) {
		super();
		this.idComunicaionBaja = comunicacionBaja.getIdComunicaionBaja();
		this.razonSocial = comunicacionBaja.getRazonSocial();
		this.numeroRuc = comunicacionBaja.getNumeroRuc();
		this.tipoDocumento = comunicacionBaja.getTipoDocumento();
		this.fechaGeneracionDocumento = comunicacionBaja.getFechaGeneracionDocumento();
		this.fechaGeneraComunica = comunicacionBaja.getFechaGeneraComunica();
		this.firmaDigital = comunicacionBaja.getFirmaDigital();
		this.versionUbl = comunicacionBaja.getVersionUbl();
		this.versionEstrucDoc = comunicacionBaja.getVersionEstrucDoc();
		this.estado = comunicacionBaja.getEstado();
		this.respuesta =comunicacionBaja.getRespuesta();
	}
	
	public ComunicacionBajaDao(String idComunicaionBaja, String razonSocial, BigInteger numeroRuc, int tipoDocumento,
			Date fechaGeneracionDocumento, Date fechaGeneraComunica, String firmaDigital,
			String versionUbl, String versionEstrucDoc, int estado, String respuesta) {
		super();
		this.idComunicaionBaja = idComunicaionBaja;
		this.razonSocial = razonSocial;
		this.numeroRuc = numeroRuc;
		this.tipoDocumento = tipoDocumento;
		this.fechaGeneracionDocumento = fechaGeneracionDocumento;
		this.fechaGeneraComunica = fechaGeneraComunica;
		this.firmaDigital = firmaDigital;
		this.versionUbl = versionUbl;
		this.versionEstrucDoc = versionEstrucDoc;
		this.estado = estado;
		this.respuesta = respuesta;
	}

	public String getIdComunicaionBaja() {
		return idComunicaionBaja;
	}

	public void setIdComunicaionBaja(String idComunicaionBaja) {
		this.idComunicaionBaja = idComunicaionBaja;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public BigInteger getNumeroRuc() {
		return numeroRuc;
	}

	public void setNumeroRuc(BigInteger numeroRuc) {
		this.numeroRuc = numeroRuc;
	}

	public int getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getFechaGeneracionDocumento() {
		return fechaGeneracionDocumento;
	}

	public void setFechaGeneracionDocumento(Date fechaGeneracionDocumento) {
		this.fechaGeneracionDocumento = fechaGeneracionDocumento;
	}

	public Date getFechaGeneraComunica() {
		return fechaGeneraComunica;
	}

	public void setFechaGeneraComunica(Date fechaGeneraComunica) {
		this.fechaGeneraComunica = fechaGeneraComunica;
	}

	public String getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
	}

	public String getVersionUbl() {
		return versionUbl;
	}

	public void setVersionUbl(String versionUbl) {
		this.versionUbl = versionUbl;
	}

	public String getVersionEstrucDoc() {
		return versionEstrucDoc;
	}

	public void setVersionEstrucDoc(String versionEstrucDoc) {
		this.versionEstrucDoc = versionEstrucDoc;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
