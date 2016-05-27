package br.com.ceunsp.projeto1.controller;

import java.util.List;

import br.com.ceunsp.projeto1.dao.CaixaDAO;
import br.com.ceunsp.projeto1.dao.RevistaDAO;
import br.com.ceunsp.projeto1.modelo.Caixa;
import br.com.ceunsp.projeto1.modelo.Revista;
import br.com.ceunsp.projeto1.util.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CadastroRevistaController {
	@FXML
	private ComboBox<Caixa> cbCaixa;
	@FXML
	private TextField tfColecao;
	@FXML
	private TextField tfEdicao;
	@FXML
	private TextField tfAno;

	private Revista revista;

	@FXML
	private void initialize() {
		try {
			CaixaDAO dao = new CaixaDAO();
			List<Caixa> caixas = dao.listar();

			cbCaixa.getItems().clear();
			cbCaixa.getItems().addAll(caixas);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}


	@FXML
	public void salvar(ActionEvent event) {
		try {
			if (tfAno.getText().equals("") || tfColecao.getText().equals("") || tfEdicao.getText().equals("")
					|| cbCaixa.getValue() == null) {
				AlertHelper.ErrorAlert("Ops! ocorreu um erro", "Por favor preencha todos os campos");
				return;
			}

			if (tfAno.getText().length() != 4) {
				AlertHelper.InfoAlert("Ops! Ano esta incorreto", "O ano informado esta incorreto use 4 digitos");
				return;
			}
			if (revista == null) {
				revista = new Revista();
				revista.setAno(tfAno.getText());
				revista.setColecao(tfColecao.getText());
				revista.setNumeroEdicao(Long.parseLong(tfEdicao.getText()));
				revista.setCaixa(cbCaixa.getValue());

				RevistaDAO dao = new RevistaDAO();
				dao.merge(revista);

				LimparCampos();
				AlertHelper.InfoAlert("Salvo", "Salvo com sucesso! ");
			}else{
				revista.setAno(tfAno.getText());
				revista.setColecao(tfColecao.getText());
				revista.setNumeroEdicao(Long.parseLong(tfEdicao.getText()));
				revista.setCaixa(cbCaixa.getValue());

				RevistaDAO dao = new RevistaDAO();
				dao.merge(revista);

				LimparCampos();
				AlertHelper.InfoAlert("Salvo", "Salvo com sucesso! ");
				revista = new Revista();
			}


			LimparCampos();
			AlertHelper.InfoAlert("Salvo", "Salvo com sucesso! ");

		} catch (RuntimeException e) {
			AlertHelper.ErrorAlert("Ops! ocorreu um erro", "Erro ao tentar salvar!");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LimparCampos() {
		tfAno.setText("");
		tfColecao.setText("");
		tfEdicao.setText("");
		cbCaixa.setValue(null);
	}

	public void setRevista(Revista revista) {
		this.revista = revista;
		tfAno.setText(revista.getAno());
		tfColecao.setText(revista.getColecao());
		tfEdicao.setText(revista.getNumeroEdicao().toString());
		cbCaixa.setValue(revista.getCaixa());
	}

}
