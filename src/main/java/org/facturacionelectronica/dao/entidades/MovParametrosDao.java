package org.facturacionelectronica.dao.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="MOV_PARAMETROS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_MOV_PARAMETROS")})
public class MovParametrosDao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "ID_MOV_PARAMETROS", nullable = false)
	private int idMovParametros;
	
	@Column(name = "RUTA_RAIZ", nullable=false)
	private String rutaRaiz;
	
	@Column(name = "RUTA_CERTIFICADOS", nullable=false)
	private String rutaCertificados;
	
	@Column(name = "RUTA_IMPORTAR_TXT", nullable=false)
	private String rutaImportarTxt;
	
	@Column(name = "RUTA_PDF", nullable=false)
	private String rutaPdf;
	
	@Column(name = "RUTA_RESPUESTA_SUNAT", nullable=false)
	private String rutaRespuestaSunat;
	
	@Column(name = "RUTA_SOLICITUD", nullable=false)
	private String rutaSolicitud;
	
	@Column(name = "LEER_TXT", nullable=false)
	private int leerTxt;
	
	@Column(name = "ENVIAR_SUNAT", nullable=true)
	private Date enviarSunat;
	
	@Column(name = "ESTADO", nullable=false)
	private int estado;

	public int getIdMovParametros() {
		return idMovParametros;
	}

	public void setIdMovParametros(int idMovParametros) {
		this.idMovParametros = idMovParametros;
	}

	public String getRutaCertificados() {
		return rutaCertificados;
	}

	public void setRutaCertificados(String rutaCertificados) {
		this.rutaCertificados = rutaCertificados;
	}

	public String getRutaImportarTxt() {
		return rutaImportarTxt;
	}

	public void setRutaImportarTxt(String rutaImportarTxt) {
		this.rutaImportarTxt = rutaImportarTxt;
	}

	public String getRutaPdf() {
		return rutaPdf;
	}

	public void setRutaPdf(String rutaPdf) {
		this.rutaPdf = rutaPdf;
	}

	public String getRutaRespuestaSunat() {
		return rutaRespuestaSunat;
	}

	public void setRutaRespuestaSunat(String rutaRespuestaSunat) {
		this.rutaRespuestaSunat = rutaRespuestaSunat;
	}

	public String getRutaSolicitud() {
		return rutaSolicitud;
	}

	public void setRutaSolicitud(String rutaSolicitud) {
		this.rutaSolicitud = rutaSolicitud;
	}

	public int getLeerTxt() {
		return leerTxt;
	}

	public void setLeerTxt(int leerTxt) {
		this.leerTxt = leerTxt;
	}

	public Date getEnviarSunat() {
		return enviarSunat;
	}

	public void setEnviarSunat(Date enviarSunat) {
		this.enviarSunat = enviarSunat;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getRutaRaiz() {
		return rutaRaiz;
	}

	public void setRutaRaiz(String rutaRaiz) {
		this.rutaRaiz = rutaRaiz;
	}
	
	
		
}
