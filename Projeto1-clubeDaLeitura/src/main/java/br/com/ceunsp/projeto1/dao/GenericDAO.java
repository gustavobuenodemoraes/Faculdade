package br.com.ceunsp.projeto1.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.ceunsp.projeto1.util.HibernateUtil;


public class GenericDAO<Entidade> {
	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		//vai pegar o tipo da classe filha atravez da API reflection
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
	
	//listar generico
	@SuppressWarnings("unchecked")
	public List<Entidade> listar(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = session.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	//buscar a partir do codigo
	@SuppressWarnings("unchecked")
	public Entidade findById(Long codigo){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			Criteria consulta = session.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade)consulta.uniqueResult();
			return resultado;
		}catch(RuntimeException e){
			throw e;
		}finally {
			session.close();
		}
	}
	public void delete(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.delete(entidade);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
	
	//O merge serve tanto para salvar quanto para editar
	public void merge(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.merge(entidade);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
}
