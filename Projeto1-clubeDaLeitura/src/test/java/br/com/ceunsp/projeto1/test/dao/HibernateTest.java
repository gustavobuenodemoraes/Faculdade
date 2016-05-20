package br.com.ceunsp.projeto1.test.dao;
import org.hibernate.Session;
import org.junit.Test;

import br.com.ceunsp.projeto1.util.HibernateUtil;

public class HibernateTest {
	@Test
	public void testeHibernate(){
		Session session =  HibernateUtil.getSessionFactory().openSession();
		session.close();
	}
}
