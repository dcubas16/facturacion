package org.facturacionelectronica.dao;

import java.io.File;

import org.facturacionelectronica.util.Constantes;
import org.facturacionelectronica.util.GestorExcepciones;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConfiguracionBaseDatos {

	private static SessionFactory sessionFactory = buildSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Optional but can be used to Close caches and connection pools
		getSessionFactory().close();
	}

	private static SessionFactory buildSessionFactory() {
		try {

			File f1 = new File(System.getProperty("java.class.path"));
			File dir = f1.getAbsoluteFile().getParentFile();
			String path = dir.toString();

			System.out.println("Ruta configuración de base de datos : " + path);

			File f = new File(path + Constantes.hibernate_cfg_xml);// --------------------->>> PRODUCCION
//			File f = new File("D://Suit_Fael//hibernate.cfg.xml");//--------------------->>> PRUEBAS

			Configuration configuration = new Configuration();
			configuration.configure(f);

			StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());

			ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			return sessionFactory;

		} catch (Exception ex) {
			GestorExcepciones.guardarExcepcion(ex, Object.class);
			return null;
		}
	}
}
