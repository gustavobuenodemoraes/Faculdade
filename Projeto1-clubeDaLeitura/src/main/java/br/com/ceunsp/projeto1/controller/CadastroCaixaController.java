package br.com.ceunsp.projeto1.controller;

import br.com.ceunsp.projeto1.validation.NumberTextFild;

import br.com.ceunsp.projeto1.dao.CaixaDAO;
import br.com.ceunsp.projeto1.modelo.Caixa;
import br.com.ceunsp.projeto1.util.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CadastroCaixaController {
	@FXML
	private NumberTextFild tfNumeroCaixa;
	@FXML
	private TextField tfCorcaixa;
	@FXML
	private TextField tfEtiquetaCaixa;

	@FXML
	private Caixa caixa;

	@FXML
	public void salvar(ActionEvent event) {
		try {
			if (tfNumeroCaixa.getText().equals("") || tfCorcaixa.getText().equals("")
					|| tfEtiquetaCaixa.getText().equals("")) {
				AlertHelper.ErrorAlert("Ops! ocorreu um erro", "Por favor preencha todos os campos");
				return;
			}

			if (caixa == null) {
				Caixa caixa = new Caixa();
				caixa.setCor(tfCorcaixa.getText());
				caixa.setEtiqueta(tfEtiquetaCaixa.getText());
				// converte para long, pois estava vindo como String
				caixa.setNumero(Long.parseLong(tfNumeroCaixa.getText()));
				CaixaDAO dao = new CaixaDAO();
				dao.merge(caixa);
				AlertHelper.InfoAlert("salvo", "Salvo com sucesso!");
				limparCampos();
			} else {
				caixa.setCor(tfCorcaixa.getText());
				caixa.setEtiqueta(tfEtiquetaCaixa.getText());
				// converte para long, pois estava vindo como String
				caixa.setNumero(Long.parseLong(tfNumeroCaixa.getText()));
				CaixaDAO dao = new CaixaDAO();
				dao.merge(caixa);
				AlertHelper.InfoAlert("salvo", "Salvo com sucesso!");
				limparCampos();
			}

		} catch (RuntimeException e) {
			AlertHelper.ErrorAlert("Ops! ocorreu um erro", "Erro ao tentar salvar!");
			e.printStackTrace();
		}
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
		tfCorcaixa.setText(caixa.getCor());
		tfEtiquetaCaixa.setText(caixa.getEtiqueta());
		tfNumeroCaixa.setText(caixa.getNumero().toString());

	}

	public void limparCampos() {
		tfCorcaixa.setText("");
		tfEtiquetaCaixa.setText("");
		tfNumeroCaixa.setText("");
	}
}