package org.facturacionelectronica.dao;

import org.facturacionelectronica.dao.entidades.LlaveDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GestorLlavesDao {

	SessionFactory sessionFactory;

	public GestorLlavesDao() {

	}
	
	
	public LlaveDao obtenerLlave(String ruc) {

		LlaveDao llaveDao = new LlaveDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		llaveDao = (LlaveDao) session.get(LlaveDao.class, ruc);

		session.close();

		return llaveDao;
	}
}
