package br.com.ceunsp.projeto1.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.util.HibernateUtil;

public class EmprestimoDAO extends GenericDAO<Emprestimo> {
	public boolean AmiguinhotemPendincias(Long amiguinhoId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consultaAmiguinho = session.createCriteria(Emprestimo.class);
			consultaAmiguinho.createAlias("amiguinho", "a");
			consultaAmiguinho.add(Restrictions.eq("a.id", amiguinhoId));
			consultaAmiguinho.add(Restrictions.eq("entregue", false));
			@SuppressWarnings("unchecked")
			List<Emprestimo> resultadoAmiguinho = consultaAmiguinho.list();

			if (!resultadoAmiguinho.isEmpty()) {
				return true;
			} else {
				return false;
			}

		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public boolean RevistatemPendincias(Long revistaId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consultaRevista = session.createCriteria(Emprestimo.class);
			consultaRevista.createAlias("revista", "r");
			consultaRevista.add(Restrictions.eq("r.id", revistaId));
			consultaRevista.add(Restrictions.eq("entregue", false));
			@SuppressWarnings("unchecked")
			List<Emprestimo> resultadoRevista = consultaRevista.list();

			if (!resultadoRevista.isEmpty()) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<Emprestimo> listarDependencias(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Emprestimo.class);
			consulta.add(Restrictions.eq("entregue", false));
			@SuppressWarnings("unchecked")
			List<Emprestimo> resultado = consulta.list();

			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}
}


