package br.com.ceunsp.projeto1.controller;

import java.util.List;

import br.com.ceunsp.projeto1.dao.AmiguinhoDAO;
import br.com.ceunsp.projeto1.modelo.Amiguinho;
import br.com.ceunsp.projeto1.tabelas.TabelaAmiguinho;
import br.com.ceunsp.projeto1.util.AlertHelper;
import br.com.ceunsp.projeto1.visao.CadastroAmiguinhoMain;
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

public class ListagemAmiguinhoController {
	@FXML
	private TableView<TabelaAmiguinho> tabela;
	@FXML
	private TableColumn<TabelaAmiguinho, String> nome;
	@FXML
	private TableColumn<TabelaAmiguinho, String> nomeMae;
	@FXML
	private TableColumn<TabelaAmiguinho, String> telefone;
	@FXML
	private TableColumn<TabelaAmiguinho, String> local;

	@FXML
	private TextField tfPesquisar;
	private List<Amiguinho> amiguinhos = getAmiguinho();

	private ObservableList<TabelaAmiguinho> list = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		popularTabela();
	}

	@FXML
	public void novo(){
		CadastroAmiguinhoMain cam = new CadastroAmiguinhoMain();
		Stage primaryStage = new Stage();
		cam.start(primaryStage);
	}
	
	public void atualizar(){
		list = FXCollections.observableArrayList();
		amiguinhos = getAmiguinho();
		popularTabela();
	}
	
	@FXML
	public void excluir() {
		try {
			TabelaAmiguinho tbAmiguinho = tabela.getSelectionModel().getSelectedItem();

			if (tbAmiguinho == null) {
				AlertHelper.InfoAlert("Selecione uma revista", "Por favor selecione uma revsta para excluir");
				return;
			}

			AmiguinhoDAO dao = new AmiguinhoDAO();
			Amiguinho amiguinho = dao.findById(tbAmiguinho.getId());

			Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnSim = new ButtonType("Sim");
			ButtonType btnNao = new ButtonType("Não");

			confirmar.setTitle("Confrimar");
			confirmar.setHeaderText("Voçe tem certeza que deseja excluir a revista " + amiguinho.getNome() + " ?");
			confirmar.getButtonTypes().setAll(btnSim, btnNao);
			confirmar.showAndWait().ifPresent(b -> {
				if (b == btnSim) {
					dao.delete(amiguinho);
					atualizar();
					AlertHelper.InfoAlert("Deletado", "Excluido com sucesso!");
				}else{
					confirmar.close();
				}
			});
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao tentar excluir revista!");
		}
	}


	@FXML
	public void editar() {
		try {
			TabelaAmiguinho tbAmiguinho = tabela.getSelectionModel().getSelectedItem();
			if (tbAmiguinho == null) {
				AlertHelper.InfoAlert("Selecione uma revista", "Por favor selecione uma revsta para excluir");
				return;
			}

			AmiguinhoDAO dao = new AmiguinhoDAO();
			Amiguinho amiguinho = dao.findById(tbAmiguinho.getId());

			CadastroAmiguinhoMain cam = new CadastroAmiguinhoMain();
			cam.abrirCadastroAmiguinho(amiguinho);
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao tentar editar revista!");
		}
	}

	
	public List<Amiguinho> getAmiguinho() {
		try {
			AmiguinhoDAO dao = new AmiguinhoDAO();
			return dao.listar();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void popularTabela() {
		// populando
		for (Amiguinho amiguinho : amiguinhos) {
			String local;
			if (amiguinho.getLocal() == 'e') {
				local = "escola";
			} else if (amiguinho.getLocal() == 'p') {
				local = "predio";
			} else {
				local = "Desconhecido";
			}
			TabelaAmiguinho tabelaAmiguinho = new TabelaAmiguinho(amiguinho.getId(), amiguinho.getNome(),
					amiguinho.getNomeMae(), amiguinho.getTelefone(), local);
			list.add(tabelaAmiguinho);
		}

		// colunas
		nome.setCellValueFactory(new PropertyValueFactory<TabelaAmiguinho, String>("Nome"));
		nomeMae.setCellValueFactory(new PropertyValueFactory<TabelaAmiguinho, String>("NomeMae"));
		telefone.setCellValueFactory(new PropertyValueFactory<TabelaAmiguinho, String>("Telefone"));
		local.setCellValueFactory(new PropertyValueFactory<TabelaAmiguinho, String>("Local"));

		tabela.setItems(list);
		filtrarDados(tabela);
	}
	
	public void filtrarDados(TableView<TabelaAmiguinho> tabela){
		  // 1. Wrap the ObservableList in a FilteredList (initially display all data).
      FilteredList<TabelaAmiguinho> filteredData = new FilteredList<>(list, p -> true);

      // 2. Set the filter Predicate whenever the filter changes.
      tfPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
          filteredData.setPredicate(amiguinho -> {
              // If filter text is empty, display all persons.
              if (newValue == null || newValue.isEmpty()) {
                  return true;
              }

              // Compare first name and last name of every person with filter text.
              String lowerCaseFilter = newValue.toLowerCase();

              if (amiguinho.getNome().toLowerCase().contains(lowerCaseFilter)) {
                  return true; // Filter matches first name.
              } 
              return false; // Does not match.
          });
      });

      // 3. Wrap the FilteredList in a SortedList. 
      SortedList<TabelaAmiguinho> sortedData = new SortedList<>(filteredData);

      // 4. Bind the SortedList comparator to the TableView comparator.
      sortedData.comparatorProperty().bind(tabela.comparatorProperty());

      // 5. Add sorted (and filtered) data to the table.
      tabela.setItems(sortedData);
	}

	
}
