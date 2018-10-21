package org.facturacionelectronica.entidades;

public class DetalleComunicacionBaja {

	private String tipoDocumento;
	private String serieDocumento;
	private String numeroCorrelativo;
	private String motivoBaja;
	private int numeroItem;
	
	public DetalleComunicacionBaja() {}
	
	public DetalleComunicacionBaja(String tipoDocumento, String serieDocumento, String numeroCorrelativo,
			String motivoBaja, int numeroItem) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.serieDocumento = serieDocumento;
		this.numeroCorrelativo = numeroCorrelativo;
		this.motivoBaja = motivoBaja;
		this.numeroItem = numeroItem;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getSerieDocumento() {
		return serieDocumento;
	}
	public void setSerieDocumento(String serieDocumento) {
		this.serieDocumento = serieDocumento;
	}
	public String getNumeroCorrelativo() {
		return numeroCorrelativo;
	}
	public void setNumeroCorrelativo(String numeroCorrelativo) {
		this.numeroCorrelativo = numeroCorrelativo;
	}
	public String getMotivoBaja() {
		return motivoBaja;
	}
	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}
	public int getNumeroItem() {
		return numeroItem;
	}
	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}
}
