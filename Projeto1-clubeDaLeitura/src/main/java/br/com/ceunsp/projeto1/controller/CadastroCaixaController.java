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

			Caixa caixa = new Caixa();
			caixa.setCor(tfCorcaixa.getText());
			caixa.setEtiqueta(tfEtiquetaCaixa.getText());
			//converte para long, pois estava vindo como String
			caixa.setNumero(Long.parseLong(tfNumeroCaixa.getText()));
			CaixaDAO dao = new CaixaDAO();
			dao.merge(caixa);
		} catch (RuntimeException e) {
			AlertHelper.ErrorAlert("Ops! ocorreu um erro", "Erro ao tentar salvar!");
			e.printStackTrace();
		}
	}
}