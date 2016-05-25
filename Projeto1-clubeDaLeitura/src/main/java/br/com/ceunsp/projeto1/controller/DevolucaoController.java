package br.com.ceunsp.projeto1.controller;

import java.util.Date;
import java.util.List;

import br.com.ceunsp.projeto1.dao.EmprestimoDAO;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import br.com.ceunsp.projeto1.util.AlertHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class DevolucaoController {
	@FXML
	ComboBox<Emprestimo> cbPendencias;
	
	@FXML
	private void initialize(){
		EmprestimoDAO dao = new EmprestimoDAO();
		List<Emprestimo> dependencias = dao.listarDependencias();
		
		cbPendencias.getItems().clear();
		cbPendencias.getItems().addAll(dependencias);
	}
	@FXML
	public void devolver(){
		try{
			Emprestimo emprestimo = cbPendencias.getValue();
			emprestimo.setEntregue(true);
			
			//data final da devolução
			Date date = new Date();
			date.getTime();
			emprestimo.setDataDevolucaoFinal(date);
			
			EmprestimoDAO dao = new EmprestimoDAO();
			dao.merge(emprestimo);
			
			List<Emprestimo> dependencias = dao.listarDependencias();
			cbPendencias.getItems().clear();
			cbPendencias.getItems().addAll(dependencias);
		}catch(RuntimeException e){
			e.printStackTrace();
			AlertHelper.ErrorAlert("Ops! Ocorreu um erro", "Erro ao devolver revista");
		}
	}
	
}
