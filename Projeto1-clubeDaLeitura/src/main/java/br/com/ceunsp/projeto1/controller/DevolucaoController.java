package br.com.ceunsp.projeto1.controller;

import java.util.Date;
import java.util.List;

import br.com.ceunsp.projeto1.dao.EmprestimoDAO;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.tabelas.TabelaEmprestimo;
import br.com.ceunsp.projeto1.util.AlertHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	@FXML
	private TextField tfPesquisar;

	private List<Emprestimo> dependencias = getDevolucao();
	private ObservableList<TabelaEmprestimo> list;

	@FXML
	private void initialize() {
		popularTabela();
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
		filtrarDados(tabela);
	}
	
	public void filtrarDados(TableView<TabelaEmprestimo> tabela){
		  // 1. Wrap the ObservableList in a FilteredList (initially display all data).
      FilteredList<TabelaEmprestimo> filteredData = new FilteredList<>(list, p -> true);

      // 2. Set the filter Predicate whenever the filter changes.
      tfPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
          filteredData.setPredicate(emprestimo -> {
              // If filter text is empty, display all persons.
              if (newValue == null || newValue.isEmpty()) {
                  return true;
              }

              // Compare first name and last name of every person with filter text.
              String lowerCaseFilter = newValue.toLowerCase();

              if (emprestimo.getRevista().toLowerCase().contains(lowerCaseFilter)) {
                  return true; // Filter matches first name.
              } else if(emprestimo.getAmiguinho().toLowerCase().contains(lowerCaseFilter)){
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
