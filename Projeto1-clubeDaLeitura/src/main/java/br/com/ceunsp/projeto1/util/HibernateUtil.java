package br.com.ceunsp.projeto1.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory = criarSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Connection getConexao(){
		Session session = sessionFactory.openSession();
		Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});
		
		return connection;
	}
	
	private static SessionFactory criarSessionFactory(){
		try {
			Configuration configuration = new Configuration().configure();
			
			ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			SessionFactory fabrica = configuration.buildSessionFactory(registro);
			return fabrica;
		}  catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}
}
