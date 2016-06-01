package br.com.ceunsp.projeto1.controller;

import java.util.List;

import br.com.ceunsp.projeto1.dao.CaixaDAO;
import br.com.ceunsp.projeto1.modelo.Caixa;
import br.com.ceunsp.projeto1.tabelas.TabelaCaixa;
import br.com.ceunsp.projeto1.util.AlertHelper;
import br.com.ceunsp.projeto1.visao.CadastroCaixaMain;
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

public class ListagemCaixaController {
	@FXML
	private TableView<TabelaCaixa> tabela;
	@FXML
	private TableColumn<TabelaCaixa, Long> numero;
	@FXML
	private TableColumn<TabelaCaixa, String> cor;
	@FXML
	private TableColumn<TabelaCaixa, String> etiqueta;
	@FXML
	private TextField tfPesquisar;

	private List<Caixa> caixas = getCaixa();

	private ObservableList<TabelaCaixa> list = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		popularTabela();
	}

	@FXML
	public void novo() {
		CadastroCaixaMain ccc = new CadastroCaixaMain();
		Stage stage = new Stage();
		ccc.start(stage);
	}

	@FXML
	public void atualizar() {
		list = FXCollections.observableArrayList();
		caixas = getCaixa();
		popularTabela();
	}

	@FXML
	public void editar() {
		try {
			TabelaCaixa tbCaixa = tabela.getSelectionModel().getSelectedItem();
			if (tbCaixa == null) {
				AlertHelper.InfoAlert("Selecione uma caixa", "Por favor selecione uma caixa para excluir");
				return;
			}

			CaixaDAO dao = new CaixaDAO();
			Caixa revista = dao.findById(tbCaixa.getNumero());

			CadastroCaixaMain crm = new CadastroCaixaMain();
			crm.abrirCadastroRevista(revista);
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao tentar editar caixa!");
		}
	}

	@FXML
	public void excluir() {
		try {
			TabelaCaixa tbCaixa = tabela.getSelectionModel().getSelectedItem();

			if (tbCaixa == null) {
				AlertHelper.InfoAlert("Selecione uma caixa", "Por favor selecione uma caixa para excluir");
				return;
			}

			CaixaDAO dao = new CaixaDAO();
			Caixa caixa = dao.findById(tbCaixa.getNumero());

			Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnSim = new ButtonType("Sim");
			ButtonType btnNao = new ButtonType("Não");

			confirmar.setTitle("Confrimar");
			confirmar.setHeaderText("Você tem certeza que deseja excluir a caixa " + caixa.getEtiqueta() + " ?");
			confirmar.getButtonTypes().setAll(btnSim, btnNao);
			confirmar.showAndWait().ifPresent(b -> {
				if (b == btnSim) {
					dao.delete(caixa);
					AlertHelper.InfoAlert("Deletado", "Excluido com sucesso!");
					atualizar();
				} else {
					confirmar.close();
				}
			});
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro",
					"Erro ao tentar excluir caixa! Verifique se existe revistas na caixa!",
					"Mude as revista dessa caixa para outra antes de tentar excluir");
		}
	}

	public List<Caixa> getCaixa() {
		try {
			CaixaDAO dao = new CaixaDAO();
			return dao.listar();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void popularTabela() {
		// populando
		for (Caixa caixa : caixas) {
			TabelaCaixa tabelaCaixa = new TabelaCaixa(caixa.getNumero(), caixa.getCor(), caixa.getEtiqueta());
			list.add(tabelaCaixa);
		}

		// colunas
		numero.setCellValueFactory(new PropertyValueFactory<TabelaCaixa, Long>("numero"));
		cor.setCellValueFactory(new PropertyValueFactory<TabelaCaixa, String>("cor"));
		etiqueta.setCellValueFactory(new PropertyValueFactory<TabelaCaixa, String>("etiqueta"));

		tabela.setItems(list);
		filtrarDados(tabela);
	}

	public void filtrarDados(TableView<TabelaCaixa> tabela) {
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<TabelaCaixa> filteredData = new FilteredList<>(list, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		tfPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(caixa -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter
				// text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (caixa.getEtiqueta().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (caixa.getNumero().toString().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (caixa.getCor().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<TabelaCaixa> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tabela.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tabela.setItems(sortedData);
	}

}