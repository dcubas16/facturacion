package org.facturacionelectronica.dao;

import java.util.ArrayList;
import java.util.List;

import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GeneradorFacturaDao {

	SessionFactory sessionFactory;

	public GeneradorFacturaDao() {

	}

	public boolean guardarFactura(CabeceraFactura cabeceraFactura, List<DetalleFactura> listaDetalleFacturas) {

		boolean existeFactura = existeFactura(cabeceraFactura.getIdFactura());

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		FacturaDao facturaDao = new FacturaDao(cabeceraFactura);
		facturaDao.setEstado(5);

		if (existeFactura) {
			this.eliminarFactura(obtenerFactura(cabeceraFactura.getIdFactura()));
		}
		
		session.save(facturaDao);

		for (DetalleFactura detalleFactura : listaDetalleFacturas) {

			DetalleFacturaDao detalleFacturaDao = new DetalleFacturaDao(detalleFactura, facturaDao);

			detalleFacturaDao.setFacturaDao(facturaDao);
			session.save(detalleFacturaDao);
		}

		transObj.commit();
		session.close();

		return true;
	}

	private boolean eliminarFactura(FacturaDao facturaDao) {

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		session.delete(facturaDao);

		transObj.commit();

		session.close();

		return true;
	}

	public boolean guardarDetalleFactura() {

		return false;
	}

	public FacturaDao obtenerFactura(String idFactura) {

		FacturaDao facturaDao = new FacturaDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		facturaDao = (FacturaDao) session.get(FacturaDao.class, idFactura);

		session.close();

		return facturaDao;
	}

	public boolean existeFactura(String idFactura) {

		FacturaDao facturaDao = new FacturaDao();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		facturaDao = (FacturaDao) session.get(FacturaDao.class, idFactura);

		session.close();

		return (facturaDao != null);
	}

	@SuppressWarnings("unchecked")
	public List<DetalleFacturaDao> obtenerDetalleFactura(String idFactura) {

		List<DetalleFacturaDao> listaDetalleFacturaDao = new ArrayList<DetalleFacturaDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM DetalleFacturaDao where id_factura = :id_factura");
		query.setParameter("id_factura", idFactura);

		listaDetalleFacturaDao = (List<DetalleFacturaDao>) query.list();

		session.close();

		return listaDetalleFacturaDao;
	}

	@SuppressWarnings("unchecked")
	public List<FacturaDao> obtenerFacturasImportadas() {

		List<FacturaDao> listaFacturaDao = new ArrayList<FacturaDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM FacturaDao WHERE ESTADO = :estado");
		query.setParameter("estado", 5);

		listaFacturaDao = (List<FacturaDao>) query.list();

		session.close();

		return listaFacturaDao;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FacturaDao> obtenerFacturasPendientesDeEnvio() {

		List<FacturaDao> listaFacturaDao = new ArrayList<FacturaDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM FacturaDao WHERE ESTADO = :estado");
		query.setParameter("estado", 6);

		listaFacturaDao = (List<FacturaDao>) query.list();

		session.close();

		return listaFacturaDao;
	}

	public boolean actualizarFactura(FacturaDao facturaDao) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		session.update(facturaDao);

		transObj.commit();

		session.close();

		return true;

	}
	
	
	public boolean actualizarEstadoAceptado(FacturaDao facturaDao, int estado, String mensajeRespuesta) {
		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();
		
		facturaDao.setEstado(estado);
		facturaDao.setMensajeRespuesta(mensajeRespuesta);

		session.update(facturaDao);

		transObj.commit();

		session.close();

		return true;

	}
	
	
	
	
	
}
