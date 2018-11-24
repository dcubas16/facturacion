package org.facturacionelectronica.entidades;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;

public class ComunicacionBaja {

	private String idComunicaionBaja;
	private String razonSocial;
	private BigInteger numeroRuc;
	private int tipoDocumento;
	private Date fechaGeneracionDocumento;
	private Date fechaGeneraComunica;
	private String firmaDigital;
	private String versionUbl;
	private String versionEstrucDoc;
	private int estado;//0:Aceptrado  5:Importado   6:Pendiente envio  Otro:Con error 
	private String respuesta;
	private List<DetalleComunicacionBaja> listaDetalleComunBaja = new ArrayList<DetalleComunicacionBaja>();

	public ComunicacionBaja() {
	}

	public ComunicacionBaja(String idComunicaionBaja, String razonSocial, BigInteger numeroRuc, int tipoDocumento,
			Date fechaGeneracionDocumento, Date fechaGeneraComunica, String firmaDigital,
			String versionUbl, String versionEstrucDoc) {
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
	}

	public ComunicacionBaja(ComunicacionBajaDao comunicacionBajaDao) {
		super();
		this.idComunicaionBaja = comunicacionBajaDao.getIdComunicaionBaja();
		this.razonSocial = comunicacionBajaDao.getRazonSocial();
		this.numeroRuc = comunicacionBajaDao.getNumeroRuc();
		this.tipoDocumento = comunicacionBajaDao.getTipoDocumento();
		this.fechaGeneracionDocumento = comunicacionBajaDao.getFechaGeneracionDocumento();
		this.fechaGeneraComunica = comunicacionBajaDao.getFechaGeneraComunica();
		this.firmaDigital = comunicacionBajaDao.getFirmaDigital();
		this.versionUbl = comunicacionBajaDao.getVersionUbl();
		this.versionEstrucDoc = comunicacionBajaDao.getVersionEstrucDoc();
	}

	public List<DetalleComunicacionBaja> getListaDetalleComunBaja() {
		return listaDetalleComunBaja;
	}

	public void setListaDetalleComunBaja(List<DetalleComunicacionBaja> listaDetalleComunBaja) {
		this.listaDetalleComunBaja = listaDetalleComunBaja;
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
