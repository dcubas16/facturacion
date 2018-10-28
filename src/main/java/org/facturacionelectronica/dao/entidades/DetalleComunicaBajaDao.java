package org.facturacionelectronica.dao.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.facturacionelectronica.entidades.DetalleComunicacionBaja;

@Entity
@Table(name="DETALLE_COMUNICA_BAJA", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_DETALLE_COMUNICA_BAJA")})
@Inheritance(strategy=InheritanceType.JOINED)
public class DetalleComunicaBajaDao {
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_COMUNICA_BAJA", nullable=false)
    private ComunicacionBajaDao comunicacionBajaDao;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "ID_DETALLE_COMUNICA_BAJA", nullable = false)
	private int idDetalleComunicaBaja;
		
	@Column(name = "TIPO_DOCUMENTO", nullable=false, length=2)
	private String tipoDocumento;
	
	@Column(name = "SERIE_DOCUMENTO", nullable=false, length=4)
	private String serieDocumento;
	
	@Column(name = "NUMERO_CORRELATIVO", nullable=false, length=8)
	private String numeroCorrelativo;
	
	@Column(name = "MOTIVO_BAJA", nullable=false, length=100)
	private String motivoBaja;
	
	@Column(name = "NUMERO_ITEM", nullable=false, length=5)
	private int numeroItem;

	public DetalleComunicaBajaDao() {}
	
	public DetalleComunicaBajaDao(ComunicacionBajaDao comunicacionBajaDao, int idDetalleComunicaBaja,
			String tipoDocumento, String serieDocumento, String numeroCorrelativo, String motivoBaja, int numeroItem) {
		super();
		this.comunicacionBajaDao = comunicacionBajaDao;
		this.idDetalleComunicaBaja = idDetalleComunicaBaja;
		this.tipoDocumento = tipoDocumento;
		this.serieDocumento = serieDocumento;
		this.numeroCorrelativo = numeroCorrelativo;
		this.motivoBaja = motivoBaja;
		this.numeroItem = numeroItem;
	}

	public DetalleComunicaBajaDao(DetalleComunicacionBaja detalleComunicacionBaja,
			ComunicacionBajaDao comunicacionBajaDao2) {
		super();
		this.comunicacionBajaDao = comunicacionBajaDao2;
		this.tipoDocumento = detalleComunicacionBaja.getTipoDocumento();
		this.serieDocumento = detalleComunicacionBaja.getSerieDocumento();
		this.numeroCorrelativo = detalleComunicacionBaja.getNumeroCorrelativo();
		this.motivoBaja = detalleComunicacionBaja.getMotivoBaja();
		this.numeroItem = detalleComunicacionBaja.getNumeroItem();
	}

	public ComunicacionBajaDao getComunicacionBajaDao() {
		return comunicacionBajaDao;
	}

	public void setComunicacionBajaDao(ComunicacionBajaDao comunicacionBajaDao) {
		this.comunicacionBajaDao = comunicacionBajaDao;
	}

	public int getIdDetalleComunicaBaja() {
		return idDetalleComunicaBaja;
	}

	public void setIdDetalleComunicaBaja(int idDetalleComunicaBaja) {
		this.idDetalleComunicaBaja = idDetalleComunicaBaja;
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
