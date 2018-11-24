package org.facturacionelectronica.dao;

import java.util.ArrayList;
import java.util.List;

import org.facturacionelectronica.dao.entidades.ComunicacionBajaDao;
import org.facturacionelectronica.dao.entidades.DetalleComunicaBajaDao;
import org.facturacionelectronica.entidades.ComunicacionBaja;
import org.facturacionelectronica.entidades.DetalleComunicacionBaja;
import org.facturacionelectronica.util.GestorExcepciones;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GeneradorComuncacionBajaDao {

	SessionFactory sessionFactory;

	public GeneradorComuncacionBajaDao() {

	}

	public boolean guardarComunicacionBaja(ComunicacionBaja comunicacionBaja,
			List<DetalleComunicacionBaja> listaDetalleComunicacionBaja) {
		boolean existeComunicacionBaja = existeComunicacionBaja(comunicacionBaja.getIdComunicaionBaja());

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao(comunicacionBaja);
		comunicacionBajaDao.setEstado(5);

		if (existeComunicacionBaja) {
			this.eliminarComunicacionBaja(obtenerComunicacionBaja(comunicacionBaja.getIdComunicaionBaja()));
		}

		session.save(comunicacionBajaDao);

		for (DetalleComunicacionBaja detalleComunicacionBaja : listaDetalleComunicacionBaja) {

			DetalleComunicaBajaDao detalleComunicacionBajaDao = new DetalleComunicaBajaDao(detalleComunicacionBaja,
					comunicacionBajaDao);

			detalleComunicacionBajaDao.setComunicacionBajaDao(comunicacionBajaDao);
			session.save(detalleComunicacionBajaDao);
		}

		transObj.commit();
		session.close();

		return true;

	}

	private boolean eliminarComunicacionBaja(ComunicacionBajaDao comunicacionBajaDao) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		session.delete(comunicacionBajaDao);

		session.close();

		return true;

	}

	private ComunicacionBajaDao obtenerComunicacionBaja(String idComunicaionBaja) {
		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		comunicacionBajaDao = (ComunicacionBajaDao) session.get(ComunicacionBajaDao.class, idComunicaionBaja);

		session.close();

		return comunicacionBajaDao;
	}

	private boolean existeComunicacionBaja(String idComunicaionBaja) {
		ComunicacionBajaDao comunicacionBajaDao = new ComunicacionBajaDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		comunicacionBajaDao = (ComunicacionBajaDao) session.get(ComunicacionBajaDao.class, idComunicaionBaja);

		session.close();

		return (comunicacionBajaDao != null);
	}

	@SuppressWarnings("unchecked")
	public List<ComunicacionBajaDao> obtenerComunicacionBajaImportados() {
		List<ComunicacionBajaDao> listaComunicacionBajaDao = new ArrayList<ComunicacionBajaDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM ComunicacionBajaDao WHERE ESTADO = :estado");
		query.setParameter("estado", 5);

		listaComunicacionBajaDao = (List<ComunicacionBajaDao>) query.list();

		session.close();

		return listaComunicacionBajaDao;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleComunicaBajaDao> obtenerDetalleComunicaBajaDao(String idComunicaionBaja) {
		List<DetalleComunicaBajaDao> listaDetalleComunicaBajaDao = new ArrayList<DetalleComunicaBajaDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM DetalleComunicaBajaDao where ID_COMUNICA_BAJA = :ID_COMUNICA_BAJA");
		query.setParameter("ID_COMUNICA_BAJA", idComunicaionBaja);

		listaDetalleComunicaBajaDao = (List<DetalleComunicaBajaDao>) query.list();

		session.close();

		return listaDetalleComunicaBajaDao;
	}

	public boolean actualizarComunicacionBaja(ComunicacionBajaDao comunicacionBajaDao) {
		try {
			Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

			// Creating Transaction Object
			Transaction transObj = session.beginTransaction();

			session.update(comunicacionBajaDao);
			
			transObj.commit();

			session.close();

			return true;
		}catch (Exception e) {
			GestorExcepciones.guardarExcepcionPorValidacion(e, this);
			return false;
		}
		
	}

	public List<ComunicacionBajaDao> obtenerComunicacionBajaPendientes() {
		List<ComunicacionBajaDao> listaComunicacionBajaDao = new ArrayList<ComunicacionBajaDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM ComunicacionBajaDao WHERE ESTADO = :estado");
		query.setParameter("estado", 6);

		listaComunicacionBajaDao = (List<ComunicacionBajaDao>) query.list();

		session.close();

		return listaComunicacionBajaDao;
	}

}
