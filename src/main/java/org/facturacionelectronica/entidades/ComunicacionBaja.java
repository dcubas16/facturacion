package org.facturacionelectronica.entidades;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComunicacionBaja {

	private String idComunicaionBaja;
	private String razonSocial;
	private BigInteger numeroRuc;
	private int tipoDocumento;
	private Date fechaGeneracionDocumento;
	private String identificaComunica;
	private Date fechaGeneraComunica;
	private String firmaDigital;
	private String versionUbl;
	private String versionEstrucDoc;
	private int estado;//0:Aceptrado   3:Importado  Otro:Con error 
	private String respuesta;
	private List<DetalleComunicacionBaja> listaDetalleComunBaja = new ArrayList<DetalleComunicacionBaja>();

	public ComunicacionBaja() {
	}

	public ComunicacionBaja(String idComunicaionBaja, String razonSocial, BigInteger numeroRuc, int tipoDocumento,
			Date fechaGeneracionDocumento, String identificaComunica, Date fechaGeneraComunica, String firmaDigital,
			String versionUbl, String versionEstrucDoc) {
		super();
		this.idComunicaionBaja = idComunicaionBaja;
		this.razonSocial = razonSocial;
		this.numeroRuc = numeroRuc;
		this.tipoDocumento = tipoDocumento;
		this.fechaGeneracionDocumento = fechaGeneracionDocumento;
		this.identificaComunica = identificaComunica;
		this.fechaGeneraComunica = fechaGeneraComunica;
		this.firmaDigital = firmaDigital;
		this.versionUbl = versionUbl;
		this.versionEstrucDoc = versionEstrucDoc;
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

	public String getIdentificaComunica() {
		return identificaComunica;
	}

	public void setIdentificaComunica(String identificaComunica) {
		this.identificaComunica = identificaComunica;
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
