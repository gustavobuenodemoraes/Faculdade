package br.com.ceunsp.projeto1.test.dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ceunsp.projeto1.dao.AmiguinhoDAO;
import br.com.ceunsp.projeto1.dao.EmprestimoDAO;
import br.com.ceunsp.projeto1.dao.RevistaDAO;
import br.com.ceunsp.projeto1.modelo.Amiguinho;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.modelo.Revista;

public class EmprestimoDAOTest {
	@Test
	@Ignore
	public void inserir() {
		Emprestimo emprestimo = new Emprestimo();
		EmprestimoDAO dao = new EmprestimoDAO();

		AmiguinhoDAO amiguinhoDAO = new AmiguinhoDAO();
		Amiguinho amiguinho = amiguinhoDAO.findById(2L);
		emprestimo.setAmiguinho(amiguinho);

		RevistaDAO revistaDAO = new RevistaDAO();
		Revista resvista = revistaDAO.findById(3L);
		emprestimo.setResvista(resvista);

		emprestimo.setDataEmprestimo(new Date());
		emprestimo.setDataDevolucao(new Date());

		dao.merge(emprestimo);
	}
	@Ignore
	@Test
	public void excluir() {
		EmprestimoDAO dao = new EmprestimoDAO();
		Emprestimo emprestimo = dao.findById(1L);
		if (emprestimo != null)
			dao.delete(emprestimo);
	}
}
