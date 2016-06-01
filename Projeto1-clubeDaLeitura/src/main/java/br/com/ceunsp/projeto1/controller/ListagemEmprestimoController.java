package br.com.ceunsp.projeto1.controller;

import java.util.Date;
import java.util.List;

import br.com.ceunsp.projeto1.dao.EmprestimoDAO;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.tabelas.TabelaEmprestimo;
import br.com.ceunsp.projeto1.util.AlertHelper;
import br.com.ceunsp.projeto1.visao.EmprestimoMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
	@FXML
	private TextField tfPesquisar;

	private List<Emprestimo> emprestimos = getEmprestimos();

	private ObservableList<TabelaEmprestimo> list = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		popularTabela();
	}

	@FXML
	public void novo() {
		EmprestimoMain em = new EmprestimoMain();
		Stage stage = new Stage();
		em.start(stage);
	}

	@FXML
	public void atualizar() {
		list = FXCollections.observableArrayList();
		emprestimos = getEmprestimos();
		popularTabela();
	}

	@FXML
	public void excluir() {
		try {
			TabelaEmprestimo tbEmprestimo = tabela.getSelectionModel().getSelectedItem();

			if (tbEmprestimo == null) {
				AlertHelper.InfoAlert("Selecione um emprestimo", "Por favor selecione um emprestimo para excluir");
				return;
			}

			EmprestimoDAO dao = new EmprestimoDAO();
			Emprestimo emprestimo = dao.findById(tbEmprestimo.getId());

			Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnSim = new ButtonType("Sim");
			ButtonType btnNao = new ButtonType("Não");

			confirmar.setTitle("Confrimar");
			confirmar.setHeaderText("Você tem certeza que deseja excluir o emprestimo do amiguinho "
					+ emprestimo.getAmiguinho() + " ?");
			confirmar.getButtonTypes().setAll(btnSim, btnNao);
			confirmar.showAndWait().ifPresent(b -> {
				if (b == btnSim) {
					dao.delete(emprestimo);
					AlertHelper.InfoAlert("Deletado", "Excluido com sucesso!");
					atualizar();
				} else {
					confirmar.close();
				}
			});
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao tentar excluir emprestimo!");
		}
	}

	@FXML
	public void editar() {
		try {
			TabelaEmprestimo tbEmprestimo = tabela.getSelectionModel().getSelectedItem();
			if (tbEmprestimo == null) {
				AlertHelper.InfoAlert("Selecione um emprestimo", "Por favor selecione um emprestimo para excluir");
				return;
			}

			EmprestimoDAO dao = new EmprestimoDAO();
			Emprestimo emprestimo = dao.findById(tbEmprestimo.getId());

			EmprestimoMain em = new EmprestimoMain();
			em.abrirEmprestimo(emprestimo);
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao tentar editar emprestimo!");
		}
	}

	@FXML
	public void devolver() {

		try {
			TabelaEmprestimo emprestimoTb = tabela.getSelectionModel().getSelectedItem();
			
			if(emprestimoTb == null){
				AlertHelper.ErrorAlert("Não selecionado", "Selecione um emprestimo para devolver! ");
				return;
			}
			
			EmprestimoDAO dao = new EmprestimoDAO();
			Emprestimo emprestimo = dao.findById(emprestimoTb.getId());
			
			if(emprestimo.getEntregue()){
				AlertHelper.InfoAlert("Já entregue", "Essa revistinha já foi entregue!");
				return;
			}
			
			emprestimo.setEntregue(true);

			// data final da devolução
			Date date = new Date();
			date.getTime();
			emprestimo.setDataDevolucaoFinal(date);

			dao.merge(emprestimo);

			atualizar();
			AlertHelper.InfoAlert("Devolvido", "Devolução realizada com sucesso!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao devolver revista");
		}

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

	public void popularTabela() {
		// populando
		for (Emprestimo emprestimo : emprestimos) {
			String status;
			String dtDevolucaoFinal;
			if (emprestimo.getEntregue()) {
				status = "Entrege";
			} else {
				status = "Pendente";
			}

			if (emprestimo.getDataDevolucaoFinal() == null) {
				dtDevolucaoFinal = "pendende";
			} else {
				dtDevolucaoFinal = emprestimo.getDataDevolucaoFinal().toString();
			}
			TabelaEmprestimo tabelaRevistas = new TabelaEmprestimo(emprestimo.getId(),
					emprestimo.getAmiguinho().getNome(), emprestimo.getRevista().getColecao(),
					emprestimo.getDataEmprestimo().toString(), emprestimo.getDataDevolucao().toString(),
					dtDevolucaoFinal, status);
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
		filtrarDados(tabela);
	}

	public void filtrarDados(TableView<TabelaEmprestimo> tabela) {
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<TabelaEmprestimo> filteredData = new FilteredList<>(list, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		tfPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(emprestimo -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter
				// text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (emprestimo.getAmiguinho().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (emprestimo.getRevista().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<TabelaEmprestimo> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tabela.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tabela.setItems(sortedData);
	}

}
