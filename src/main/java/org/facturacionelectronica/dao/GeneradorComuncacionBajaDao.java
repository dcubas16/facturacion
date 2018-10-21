package org.facturacionelectronica.dao;

import java.util.List;

import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GeneradorComuncacionBajaDao {

	SessionFactory sessionFactory;

	public GeneradorComuncacionBajaDao() {

	}

	public boolean guardarComunicacionBaja(ComunicacionBaja comunicacionBaja,
			List<DetalleComunicacionBaja> listaDetalleComunicacionBaja) {
//		boolean existeComunicacionBaja = existeComunicacionBaja(comunicacionBaja.getIdComunicaionBaja());
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// Creating Transaction Object
//		Transaction transObj = session.beginTransaction();
//
//		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao(comunicacionBaja);
//		comunicacionBajaDao.setEstado(5);
//
//		if (existeComunicacionBaja) {
//			this.eliminarComunicacionBaja(obtenerComunicacionBaja(comunicacionBaja.getIdComunicaionBaja()));
//		}
//		
//		session.save(comunicacionBaja);
//
//		for (DetalleComunicacionBaja detalleComunicacionBaja : listaDetalleComunicacionBaja) {
//
//			DetalleComunicacionBajaDao detalleComunicacionBajaDao = new DetalleComunicacionBajaDao(detalleComunicacionBaja, comunicacionBajaDao);
//
//			detalleFacturaDao.setFacturaDao(detalleComunicacionBajaDao);
//			session.save(detalleFacturaDao);
//		}
//
//		transObj.commit();
//		session.close();

		return true;
		
	}

}
