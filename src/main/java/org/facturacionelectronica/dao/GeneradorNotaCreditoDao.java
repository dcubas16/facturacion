package org.facturacionelectronica.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.DetalleNotaCreditoDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.dao.entidades.NotaCreditoDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.CabeceraNotaCredito;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.facturacionelectronica.entidades.DetalleNotaCredito;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GeneradorNotaCreditoDao {

	SessionFactory sessionFactory;

	public GeneradorNotaCreditoDao() {

	}

	public boolean guardarNotaCredito(CabeceraNotaCredito cabeceraNotaCredito, List<DetalleNotaCredito> listaDetalleNotaCredito) {

		boolean existeNotaCredito = existeNotaCredito(cabeceraNotaCredito.getIdNotaCredito());

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		NotaCreditoDao notaCreditoDao = new NotaCreditoDao(cabeceraNotaCredito);
		notaCreditoDao.setEstado(5);

		if (existeNotaCredito) {
			this.eliminarNotaCredito(obtenerNotaCredito(cabeceraNotaCredito.getIdNotaCredito()));
		}
		
		session.save(notaCreditoDao);

		for (DetalleNotaCredito detalleNotaCredito : listaDetalleNotaCredito) {

			DetalleNotaCreditoDao detalleNotaCreditoDao = new DetalleNotaCreditoDao(detalleNotaCredito);

			detalleNotaCreditoDao.setNotaCreditoDao(notaCreditoDao);
			session.save(detalleNotaCreditoDao);
		}

		transObj.commit();
		session.close();

		return true;
	}

	private boolean eliminarNotaCredito(NotaCreditoDao notaCreditoDao) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		session.delete(notaCreditoDao);

		transObj.commit();

		session.close();

		return true;
		
	}

	public NotaCreditoDao obtenerNotaCredito(String idNotaCredito) {
		NotaCreditoDao notaCreditoDao = new NotaCreditoDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		notaCreditoDao = (NotaCreditoDao) session.get(NotaCreditoDao.class, idNotaCredito);

		session.close();

		return notaCreditoDao;
	}

	private boolean existeNotaCredito(String idNotaCredito) {
		NotaCreditoDao notaCreditoDao = new NotaCreditoDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		notaCreditoDao = (NotaCreditoDao) session.get(NotaCreditoDao.class, idNotaCredito);

		session.close();

		return (notaCreditoDao != null);
	}

	public List<NotaCreditoDao> obtenerNotaCreditoImportadas() {
		List<NotaCreditoDao> listaNotaCreditoDaos = new ArrayList<NotaCreditoDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM NotaCreditoDao WHERE ESTADO = :estado");
		query.setParameter("estado", 5);

		listaNotaCreditoDaos = (List<NotaCreditoDao>) query.list();

		session.close();

		return listaNotaCreditoDaos;
	}

	public List<DetalleNotaCreditoDao> obtenerDetalleNotaCredito(String idNotaCredito) {
		List<DetalleNotaCreditoDao> listaNotaCreditoDaos = new ArrayList<DetalleNotaCreditoDao>();
		
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM DetalleNotaCreditoDao where id_nota_credito = :id_nota_credito");
		query.setParameter("id_nota_credito", idNotaCredito);

		listaNotaCreditoDaos = (List<DetalleNotaCreditoDao>) query.list();

		session.close();
		
		return listaNotaCreditoDaos;
	}

	public boolean actualizarEstadoAceptado(NotaCreditoDao notaCreditoDao, int estado, String mensajeRespuesta) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();
		
		notaCreditoDao.setEstado(estado);
		notaCreditoDao.setMensajeRespuesta(mensajeRespuesta);
		notaCreditoDao.setFechaRespuesta(new Date());

		session.update(notaCreditoDao);

		transObj.commit();

		session.close();

		return true;
		
	}

	public boolean actualizarEstadoNotaCredito(NotaCreditoDao notaCreditoDao) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		session.update(notaCreditoDao);

		transObj.commit();

		session.close();

		return true;
		
	}
	
}
