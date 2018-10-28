package org.facturacionelectronica.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleComunicaBajaDao;
import org.facturacionelectronica.util.Constantes;

public class GeneradorComunicacionBaja {

	public Map<String, Object> formatoResumenBajas(ComunicacionBajaDao comunicacionBajaDao,
			List<DetalleComunicaBajaDao> listaDetalleComunicacionBajaDao) throws Exception {

		String idComunicacion = comunicacionBajaDao.getIdComunicaionBaja();

		String identificadorFirmaSwf = "SIGN";
		Random calcularRnd = new Random();
		Integer codigoFacturadorSwf = (int) (calcularRnd.nextDouble() * 1000000);

		Map<String, Object> resumenBajas = null;
		Map<String, Object> resumenGeneral = new HashMap<String, Object>();
		List<Map<String, Object>> listaResumenBajas = new ArrayList<Map<String, Object>>();

		resumenGeneral.put("nombreComercialSwf", comunicacionBajaDao.getRazonSocial());
		resumenGeneral.put("razonSocialSwf", comunicacionBajaDao.getRazonSocial());
		resumenGeneral.put("nroRucEmisorSwf", comunicacionBajaDao.getNumeroRuc().toString());
		resumenGeneral.put("tipDocuEmisorSwf", Constantes.CONSTANTE_TIPO_DOCU_EMISOR);
		resumenGeneral.put("fechaDocumentoBaja", comunicacionBajaDao.getFechaGeneracionDocumento().toString());
		resumenGeneral.put("fechaComunicacioBaja", comunicacionBajaDao.getFechaGeneraComunica().toString());
		// Valores Automaticos
		resumenGeneral.put("ublVersionIdSwf", Constantes.CONSTANTE_UBL_VERSION);
		resumenGeneral.put("idComunicacion", idComunicacion);
		resumenGeneral.put("CustomizationIdSwf", Constantes.CONSTANTE_CUSTOMIZATION_ID);
		resumenGeneral.put("identificadorFacturadorSwf",
				Constantes.CONSTANTE_INFO_SFS_SUNAT + Constantes.CONSTANTE_VERSION_SFS);
		resumenGeneral.put("codigoFacturadorSwf", codigoFacturadorSwf.toString());
		resumenGeneral.put("identificadorFirmaSwf", identificadorFirmaSwf);

		for (DetalleComunicaBajaDao detalleComunicacionBajaDao : listaDetalleComunicacionBajaDao) {
			resumenBajas = new HashMap<String, Object>();

			resumenBajas = new HashMap<String, Object>();
			resumenBajas.put("tipoDocumentoBaja", detalleComunicacionBajaDao.getTipoDocumento());
			resumenBajas.put("serieDocumentoBaja", detalleComunicacionBajaDao.getSerieDocumento());
			resumenBajas.put("nroDocumentoBaja", detalleComunicacionBajaDao.getNumeroCorrelativo());
			resumenBajas.put("motivoBajaDocumento", detalleComunicacionBajaDao.getMotivoBaja());
			resumenBajas.put("linea", detalleComunicacionBajaDao.getIdDetalleComunicaBaja());

			listaResumenBajas.add(resumenBajas);
		}

		resumenGeneral.put("listaResumen", listaResumenBajas);

		return resumenGeneral;

	}
	
	
	
	public void generarXmlComunicaBajas() {
		
	}
	
}
