package br.com.ceunsp.projeto1.test.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ceunsp.projeto1.dao.CaixaDAO;
import br.com.ceunsp.projeto1.modelo.Caixa;

public class CaixaDAOTest {

	@Ignore
	@Test
	public void inserir() {
		Caixa caixa = new Caixa();
		caixa.setCor("Vermelha");
		caixa.setEtiqueta("Revistas favoritas");
		CaixaDAO dao = new CaixaDAO();
		dao.merge(caixa);
	}

	@Ignore
	@Test
	public void excluir() {
		CaixaDAO dao = new CaixaDAO();
		Caixa caixa = dao.findById(2L);
		if (caixa != null)
			dao.delete(caixa);
	}
}
