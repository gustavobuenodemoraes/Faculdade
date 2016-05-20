package br.com.ceunsp.projeto1.test.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ceunsp.projeto1.dao.AmiguinhoDAO;
import br.com.ceunsp.projeto1.modelo.Amiguinho;

public class AmiguinhoDAOTest {
	@Ignore
	@Test
	public void inserir() {
		Amiguinho amiguinho = new Amiguinho();
		amiguinho.setLocal('E');
		amiguinho.setNome("Paulo");
		amiguinho.setNomeMae("Cleide");
		amiguinho.setTelefone("11 97248-1996");

		AmiguinhoDAO dao = new AmiguinhoDAO();
		dao.merge(amiguinho);
	}
	@Ignore
	@Test
	public void excluir(){

		AmiguinhoDAO dao = new AmiguinhoDAO();
		Amiguinho amiguinho = dao.findById(1L);
		if(amiguinho != null)
			dao.delete(amiguinho);
	}
}
