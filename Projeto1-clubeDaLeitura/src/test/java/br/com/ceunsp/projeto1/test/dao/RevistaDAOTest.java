package br.com.ceunsp.projeto1.test.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ceunsp.projeto1.dao.RevistaDAO;
import br.com.ceunsp.projeto1.modelo.Caixa;
import br.com.ceunsp.projeto1.modelo.Revista;

public class RevistaDAOTest {
	@Ignore
	@Test
	public void inserir() {
		Caixa caixa = new Caixa();
		caixa.setNumero(2L);
		Revista revista = new Revista();
		revista.setCaixa(caixa);
		revista.setAno("2010");
		revista.setColecao("Batman");
		revista.setNumeroEdicao(345L);

		RevistaDAO dao = new RevistaDAO();
		dao.merge(revista);
	}

	@Test
	public void excluir() {
		RevistaDAO revistaDao = new RevistaDAO();

		Revista revista = revistaDao.findById(2L);
		if (revista != null)
			revistaDao.delete(revista);
	}
}
