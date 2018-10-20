package org.facturacionelectronica.dao;

import java.io.File;

import org.facturacionelectronica.util.Constantes;
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
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configObj = new Configuration();
			File f = new File(Constantes.rutaCompleta+"hibernate.cfg.xml");

			Configuration configuration = new Configuration();
			configuration.configure(f);

			StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());

			ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			return sessionFactory;

		} catch (Throwable ex) {
			// Make sure you log the exception to track it
			System.err.println("SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
