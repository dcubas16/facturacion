package org.facturacionelectronica.dao;

import java.util.List;

import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleComunicaBajaDao;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GeneradorComunicacionBajaDao {

	SessionFactory sessionFactory;

	public GeneradorComunicacionBajaDao() {

	}
	
	public boolean guardarComunicacionBajaDao(ComunicacionBaja comunicacionBaja, List<DetalleComunicacionBaja> listaDetalleComunicacionBaja) {

		boolean existeComunicacionBaja = existeComunicacionBaja(comunicacionBaja.getIdComunicaionBaja());

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao(comunicacionBaja);

		if (existeComunicacionBaja) {
			this.eliminarComunicacionBaja(obtenerComunicacionBaja(comunicacionBaja.getIdComunicaionBaja()));
		}
		
		session.save(comunicacionBajaDao);

		for (DetalleComunicacionBaja detalleComunicacionBaja : listaDetalleComunicacionBaja) {

			DetalleComunicaBajaDao detalleComunicaBajaDao = new DetalleComunicaBajaDao(detalleComunicacionBaja, comunicacionBajaDao);

			detalleComunicaBajaDao.setComunicacionBajaDao(comunicacionBajaDao);
			session.save(detalleComunicaBajaDao);
		}

		transObj.commit();
		session.close();

		return true;
	}

	private boolean eliminarComunicacionBaja(ComunicacionBajaDao comunicacionBajaDao) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		session.delete(comunicacionBajaDao);

		transObj.commit();

		session.close();

		return true;
		
	}

	private ComunicacionBajaDao obtenerComunicacionBaja(String idComunicaionBaja) {
		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		comunicacionBajaDao = (ComunicacionBajaDao) session.get(ComunicacionBajaDao.class, idComunicaionBaja);

		session.close();

		return comunicacionBajaDao;
	}

	private boolean existeComunicacionBaja(String idComunicaionBaja) {
		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		comunicacionBajaDao = (ComunicacionBajaDao) session.get(ComunicacionBajaDao.class, idComunicaionBaja);

//		session.flush();
		session.close();

		return (comunicacionBajaDao != null);
	}

}
