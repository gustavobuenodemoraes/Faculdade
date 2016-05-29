package br.com.ceunsp.projeto1.controller;

import java.util.Date;
import java.util.List;

import br.com.ceunsp.projeto1.dao.EmprestimoDAO;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.tabelas.TabelaEmprestimo;
import br.com.ceunsp.projeto1.util.AlertHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DevolucaoController {
	@FXML
	private TableView<TabelaEmprestimo> tabela;
	@FXML
	private TableColumn<TabelaEmprestimo, String> amiguinho;
	@FXML
	private TableColumn<TabelaEmprestimo, String> revista;
	@FXML
	private TableColumn<TabelaEmprestimo, String> status;
	@FXML
	private TableColumn<TabelaEmprestimo, String> dtEmprestimo;
	@FXML
	private TableColumn<TabelaEmprestimo, String> dtDevolucao;

	private List<Emprestimo> dependencias = getDevolucao();
	private ObservableList<TabelaEmprestimo> list;

	@FXML
	private void initialize() {
		popularTabela();
	}

	@FXML
	public void devolver() {

		try {

			EmprestimoDAO dao = new EmprestimoDAO();
			TabelaEmprestimo emprestimoTb = tabela.getSelectionModel().getSelectedItem();
			Emprestimo emprestimo = dao.findById(emprestimoTb.getId());
			emprestimo.setEntregue(true);

			// data final da devolução
			Date date = new Date();
			date.getTime();
			emprestimo.setDataDevolucaoFinal(date);

			dao.merge(emprestimo);

			atualizar();
			AlertHelper.InfoAlert("Devolvido", "Devoleção realizada com sucesso!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao devolver revista");
		}

	}

	public List<Emprestimo> getDevolucao() {
		try {
			EmprestimoDAO dao = new EmprestimoDAO();
			return dao.listarDependencias();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void atualizar() {
		list = FXCollections.observableArrayList();
		dependencias = getDevolucao();
		popularTabela();
	}

	public void popularTabela() {
		// populando
		list = FXCollections.observableArrayList();
		for (Emprestimo emprestimo : dependencias) {
			String status;
			if (emprestimo.getEntregue()) {
				status = "Entrege";
			} else {
				status = "Pendente";
			}

			TabelaEmprestimo tabelaRevistas = new TabelaEmprestimo(emprestimo.getId(),
					emprestimo.getAmiguinho().getNome(), emprestimo.getRevista().getColecao(),
					emprestimo.getDataEmprestimo().toString(), emprestimo.getDataDevolucao().toString(), null, status);
			list.add(tabelaRevistas);
		}

		// colunas
		amiguinho.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("Amiguinho"));
		revista.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("Revista"));
		dtEmprestimo.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("dtEmprestimo"));
		dtDevolucao.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("dtDevolucao"));

		status.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("Status"));

		tabela.setItems(list);
	}
}
