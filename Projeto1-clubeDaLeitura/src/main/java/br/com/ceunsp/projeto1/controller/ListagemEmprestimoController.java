package br.com.ceunsp.projeto1.controller;

import java.util.List;

import br.com.ceunsp.projeto1.dao.EmprestimoDAO;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.tabelas.TabelaEmprestimo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListagemEmprestimoController {
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
	@FXML
	private TableColumn<TabelaEmprestimo, String> dtDevolucaoFinal;

	private List<Emprestimo> emprestimos = getEmprestimos();

	private ObservableList<TabelaEmprestimo> list = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		// populando
		for (Emprestimo emprestimo : emprestimos) {
			String status;
			String dtDevolucaoFinal;
			if(emprestimo.getEntregue()){
				status = "Entrege";
			}else{
				status = "Pendente";
			}
			
			if(emprestimo.getDataDevolucaoFinal() == null){
				dtDevolucaoFinal = "pendende";
			}else{
				dtDevolucaoFinal = emprestimo.getDataDevolucaoFinal().toString();
			}
			TabelaEmprestimo tabelaRevistas = new TabelaEmprestimo(emprestimo.getId(), emprestimo.getAmiguinho().getNome(),
					emprestimo.getRevista().getColecao(), emprestimo.getDataEmprestimo().toString(),
					emprestimo.getDataDevolucao().toString(), dtDevolucaoFinal,status );
			list.add(tabelaRevistas);
		}

		// colunas
		amiguinho.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("Amiguinho"));
		revista.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("Revista"));
		dtEmprestimo.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("dtEmprestimo"));
		dtDevolucao.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("dtDevolucao"));
		dtDevolucaoFinal.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("dtDevolucaoFinal"));
		status.setCellValueFactory(new PropertyValueFactory<TabelaEmprestimo, String>("Status"));

		tabela.setItems(list);
	}

	public List<Emprestimo> getEmprestimos() {
		try {
			EmprestimoDAO dao = new EmprestimoDAO();
			return dao.listar();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
