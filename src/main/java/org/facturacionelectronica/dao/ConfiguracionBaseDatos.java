package org.facturacionelectronica.dao;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConfiguracionBaseDatos {
	
	
	public static SessionFactory getSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		File f = new File("D:\\Suit_Fael\\hibernate.cfg.xml");
//		configObj.addFile("D:\\Suit_Fael\\hibernate.cfg.xml");
//		configObj.configure("hibernate.cfg.xml");
		
//		SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
		
//		File hibernatePropsFile = new File(hibernatePropsFilePath);

		Configuration configuration = new Configuration(); 
		configuration.configure(f);

		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// Since Hibernate Version 4.x, Service Registry Is Being Used
//		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate Session Factory Instance
//		SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		
		return sessionFactory;
	}

//	public static SessionFactory getSessionFactory() {
//		// Creating Configuration Instance & Passing Hibernate Configuration File
//		Configuration configObj = new Configuration();
//		configObj.configure("D:\\Suit_Fael\\hibernate.cfg.xml");
//
//		// Since Hibernate Version 4.x, Service Registry Is Being Used
//		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 
//
//		// Creating Hibernate Session Factory Instance
//		SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);		
//		return factoryObj;
//	}
}
