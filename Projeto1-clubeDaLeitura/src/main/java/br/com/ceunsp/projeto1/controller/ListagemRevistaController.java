package br.com.ceunsp.projeto1.controller;

import java.util.List;

import br.com.ceunsp.projeto1.dao.RevistaDAO;
import br.com.ceunsp.projeto1.modelo.Revista;
import br.com.ceunsp.projeto1.tabelas.TabelaRevistas;
import br.com.ceunsp.projeto1.util.AlertHelper;
import br.com.ceunsp.projeto1.visao.CadastroRevistaMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListagemRevistaController {
	@FXML
	private TableView<TabelaRevistas> tabela;
	@FXML
	private TableColumn<TabelaRevistas, String> colecao;
	@FXML
	private TableColumn<TabelaRevistas, Long> edicao;
	@FXML
	private TableColumn<TabelaRevistas, String> ano;
	@FXML
	private TableColumn<TabelaRevistas, String> caixa;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnExcluir;
	@FXML
	private TextField tfPesquisar;

	private List<Revista> revistas = getRevistas();

	private ObservableList<TabelaRevistas> list = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		popularTabela();
	}

	public List<Revista> getRevistas() {
		try {
			RevistaDAO dao = new RevistaDAO();
			return dao.listar();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@FXML
	public void novo() {
		CadastroRevistaMain crm = new CadastroRevistaMain();
		Stage stage = new Stage();
		crm.start(stage);
	}

	@FXML
	public void atualizar() {
		list = FXCollections.observableArrayList();
		revistas = getRevistas();
		popularTabela();
	}

	@FXML
	public void excluir() {
		try {
			TabelaRevistas tbResvista = tabela.getSelectionModel().getSelectedItem();

			if (tbResvista == null) {
				AlertHelper.InfoAlert("Selecione uma revista", "Por favor selecione uma revsta para excluir");
				return;
			}

			RevistaDAO dao = new RevistaDAO();
			Revista revista = dao.findById(tbResvista.getId());

			Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnSim = new ButtonType("Sim");
			ButtonType btnNao = new ButtonType("Não");

			confirmar.setTitle("Confrimar");
			confirmar.setHeaderText("Voçe tem certeza que deseja excluir a revista " + revista.getColecao() + " ?");
			confirmar.getButtonTypes().setAll(btnSim, btnNao);
			confirmar.showAndWait().ifPresent(b -> {
				if (b == btnSim) {
					dao.delete(revista);
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
			TabelaRevistas tbResvista = tabela.getSelectionModel().getSelectedItem();
			if (tbResvista == null) {
				AlertHelper.InfoAlert("Selecione uma revista", "Por favor selecione uma revsta para excluir");
				return;
			}

			RevistaDAO dao = new RevistaDAO();
			Revista revista = dao.findById(tbResvista.getId());

			CadastroRevistaMain crm = new CadastroRevistaMain();
			crm.abrirCadastroRevista(revista);
		} catch (RuntimeException e) {
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao tentar editar revista!");
		}
	}

	public void popularTabela() {
		// populando
		for (Revista revista : revistas) {

			TabelaRevistas tabelaRevistas = new TabelaRevistas(revista.getId(), revista.getColecao(), revista.getAno(),
					revista.getCaixa().getEtiqueta(), revista.getNumeroEdicao());
			list.add(tabelaRevistas);
		}
		// colunas
		colecao.setCellValueFactory(new PropertyValueFactory<TabelaRevistas, String>("Colecao"));
		ano.setCellValueFactory(new PropertyValueFactory<TabelaRevistas, String>("Ano"));
		caixa.setCellValueFactory(new PropertyValueFactory<TabelaRevistas, String>("Caixa"));
		edicao.setCellValueFactory(new PropertyValueFactory<TabelaRevistas, Long>("Edicao"));

		tabela.setItems(list);

		filtrarDados(tabela);
	}
	public void filtrarDados(TableView<TabelaRevistas> tabela){
		  // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<TabelaRevistas> filteredData = new FilteredList<>(list, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        tfPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(revista -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (revista.getColecao().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } 
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<TabelaRevistas> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tabela.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tabela.setItems(sortedData);
	}
}
