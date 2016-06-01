package br.com.ceunsp.projeto1.controller;

import br.com.ceunsp.projeto1.dao.CaixaDAO;
import br.com.ceunsp.projeto1.modelo.Caixa;
import br.com.ceunsp.projeto1.util.AlertHelper;
import br.com.ceunsp.projeto1.validation.NumberTextFild;
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

				if(verificaSeExisteCaixa(caixa)){
					AlertHelper.ErrorAlert("Já existe", "A caixa com o numero "+ caixa.getNumero()+ " já existe!");
					return;
				}
				CaixaDAO dao = new CaixaDAO();
				dao.merge(caixa);
				AlertHelper.InfoAlert("salvo", "Salvo com sucesso!");
				limparCampos();
			} else {
				caixa.setCor(tfCorcaixa.getText());
				caixa.setEtiqueta(tfEtiquetaCaixa.getText());
				// converte para long, pois estava vindo como String
				caixa.setNumero(Long.parseLong(tfNumeroCaixa.getText()));

				if(verificaSeExisteCaixa(caixa)){
					AlertHelper.ErrorAlert("Já existe", "A caixa com o numero "+ caixa.getNumero()+ " já existe!");
					return;
				}
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
	public boolean verificaSeExisteCaixa(Caixa caixa){
		CaixaDAO dao = new CaixaDAO();
		Caixa caixaResult = dao.findById(caixa.getNumero());
		if(caixaResult  == null){
			return false;
		}else{
			return true;
		}
	}
}