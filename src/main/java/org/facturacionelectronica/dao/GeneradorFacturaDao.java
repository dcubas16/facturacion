package org.facturacionelectronica.dao;

import java.util.ArrayList;
import java.util.List;

import org.facturacionelectronica.dao.entidades.DetalleFacturaDao;
import org.facturacionelectronica.dao.entidades.FacturaDao;
import org.facturacionelectronica.entidades.CabeceraFactura;
import org.facturacionelectronica.entidades.DetalleFactura;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GeneradorFacturaDao {

	Session session;

	public GeneradorFacturaDao() {

	}

	public boolean guardarFactura(CabeceraFactura cabeceraFactura, List<DetalleFactura> listaDetalleFacturas) {

		session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// Creating Transaction Object
		Transaction transObj = session.beginTransaction();

		FacturaDao facturaDao = new FacturaDao(cabeceraFactura);

		session.save(facturaDao);

		for (DetalleFactura detalleFactura : listaDetalleFacturas) {

			DetalleFacturaDao detalleFacturaDao = new DetalleFacturaDao(detalleFactura, facturaDao);

			detalleFacturaDao.setFacturaDao(facturaDao);
			// facturaDao.getDetalleFacturaDao().add(detalleFacturaDao);

			session.save(detalleFacturaDao);
		}

		transObj.commit();

		session.close();

		return true;
	}

	public boolean guardarDetalleFactura() {

		return false;
	}

	public FacturaDao obtenerFactura(String idFactura) {

		FacturaDao facturaDao = new FacturaDao();
		session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		facturaDao = (FacturaDao) session.get(FacturaDao.class, idFactura);

		session.close();

		return facturaDao;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleFacturaDao> obtenerDetalleFactura(String idFactura) {

		List<DetalleFacturaDao> listaDetalleFacturaDao = new ArrayList<DetalleFacturaDao>();

		session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createSQLQuery("SELECT * FROM DETALLE_FACTURA where id_factura = :id_factura");
		query.setParameter("id_factura", idFactura);

		listaDetalleFacturaDao = query.list();

		session.close();

		return listaDetalleFacturaDao;
	}

	public List<FacturaDao> obtenerFacturasImportadas() {
		
		List<FacturaDao> listaFacturaDao = new ArrayList<FacturaDao>();

		session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createSQLQuery("SELECT * FROM FACTURA WHERE ESTADO = 0");

		listaFacturaDao = query.list();

		session.close();

		return listaFacturaDao;
	}

}
