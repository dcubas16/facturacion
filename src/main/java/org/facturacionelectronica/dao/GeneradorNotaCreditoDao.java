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

	private NotaCreditoDao obtenerNotaCredito(String idNotaCredito) {
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

//	private boolean eliminarFactura(FacturaDao facturaDao) {
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// Creating Transaction Object
//		Transaction transObj = session.beginTransaction();
//
//		session.delete(facturaDao);
//
//		transObj.commit();
//
//		session.close();
//
//		return true;
//	}
//
//	public boolean guardarDetalleFactura() {
//
//		return false;
//	}
//
//	public FacturaDao obtenerFactura(String idFactura) {
//
//		FacturaDao facturaDao = new FacturaDao();
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		facturaDao = (FacturaDao) session.get(FacturaDao.class, idFactura);
//
//		session.close();
//
//		return facturaDao;
//	}
//
//	public boolean existeFactura(String idFactura) {
//
//		FacturaDao facturaDao = new FacturaDao();
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		facturaDao = (FacturaDao) session.get(FacturaDao.class, idFactura);
//
//		session.close();
//
//		return (facturaDao != null);
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<DetalleFacturaDao> obtenerDetalleFactura(String idFactura) {
//
//		List<DetalleFacturaDao> listaDetalleFacturaDao = new ArrayList<DetalleFacturaDao>();
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		Query query = session.createQuery("FROM DetalleFacturaDao where id_factura = :id_factura");
//		query.setParameter("id_factura", idFactura);
//
//		listaDetalleFacturaDao = (List<DetalleFacturaDao>) query.list();
//
//		session.close();
//
//		return listaDetalleFacturaDao;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<FacturaDao> obtenerFacturasImportadas() {
//
//		List<FacturaDao> listaFacturaDao = new ArrayList<FacturaDao>();
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		Query query = session.createQuery("FROM FacturaDao WHERE ESTADO = :estado");
//		query.setParameter("estado", 5);
//
//		listaFacturaDao = (List<FacturaDao>) query.list();
//
//		session.close();
//
//		return listaFacturaDao;
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	public List<FacturaDao> obtenerFacturasPendientesDeEnvio() {
//
//		List<FacturaDao> listaFacturaDao = new ArrayList<FacturaDao>();
//
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
////		Query query = session.createQuery("FROM FacturaDao WHERE ESTADO = :estado");
////		Query query = session.createQuery("FROM FacturaDao WHERE ESTADO = :estado AND idFactura = :idFactura");
//		Query query = session.createQuery("FROM FacturaDao WHERE ESTADO = :estado AND numeroDocumento = :numeroDocumento");
//		query.setParameter("estado", 6);
////		query.setParameter("idFactura", "20101440355B00210");
//		query.setParameter("numeroDocumento", new BigInteger("20600091370"));
//		
//		
//		
//		listaFacturaDao = (List<FacturaDao>) query.list();
//
//		session.close();
//
//		return listaFacturaDao;
//	}
//
//	public boolean actualizarFactura(FacturaDao facturaDao) {
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// Creating Transaction Object
//		Transaction transObj = session.beginTransaction();
//
//		session.update(facturaDao);
//
//		transObj.commit();
//
//		session.close();
//
//		return true;
//
//	}
//	
//	
//	public boolean actualizarEstadoAceptado(FacturaDao facturaDao, int estado, String mensajeRespuesta) {
//		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();
//
//		// Creating Transaction Object
//		Transaction transObj = session.beginTransaction();
//		
//		facturaDao.setEstado(estado);
//		facturaDao.setMensajeRespuesta(mensajeRespuesta);
//		facturaDao.setFechaRespuesta(new Date());
//
//		session.update(facturaDao);
//
//		transObj.commit();
//
//		session.close();
//
//		return true;
//
//	}
//	
//	
//	
	
	
}
