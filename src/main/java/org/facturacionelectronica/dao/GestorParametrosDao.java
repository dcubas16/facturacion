package org.facturacionelectronica.dao;

import java.util.ArrayList;
import java.util.List;
import org.facturacionelectronica.dao.entidades.MovParametrosDao;
import org.hibernate.Query;
import org.hibernate.Session;

public class GestorParametrosDao {
	
	@SuppressWarnings("unchecked")
	public List<MovParametrosDao> obtenerParametros(){
		
		List<MovParametrosDao> listaMovParametros = new ArrayList<MovParametrosDao>();

		Session session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		// session = ConfiguracionBaseDatos.getSessionFactory().openSession();

		Query query = session.createQuery("FROM MovParametrosDao where estado = :estado");
		query.setParameter("estado", 1);

		listaMovParametros = (List<MovParametrosDao>) query.list();

		session.close();

		return listaMovParametros;
		
	}

}
