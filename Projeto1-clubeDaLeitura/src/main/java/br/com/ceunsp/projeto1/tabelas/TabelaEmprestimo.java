package br.com.ceunsp.projeto1.tabelas;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TabelaEmprestimo {
	private final SimpleLongProperty id;
	private final SimpleStringProperty amiguinho;
	private final SimpleStringProperty revista;
	private final SimpleStringProperty dtEmprestimo;
	private final SimpleStringProperty dtDevolucao;
	private final SimpleStringProperty dtDevolucaoFinal;
	private final SimpleStringProperty status;

	public TabelaEmprestimo(Long id, String amiguinho, String revista, String dtEmprestimo, String dtDevolucao,
			String dtDevolucaoFinal, String status) {
		super();
		this.id = new SimpleLongProperty(id);
		this.amiguinho = new SimpleStringProperty(amiguinho);
		this.revista = new SimpleStringProperty(revista);
		this.dtEmprestimo = new SimpleStringProperty(dtEmprestimo);
		this.dtDevolucao = new SimpleStringProperty(dtDevolucao);
		this.dtDevolucaoFinal = new SimpleStringProperty(dtDevolucaoFinal);
		this.status = new SimpleStringProperty(status);
	}

	public String getAmiguinho() {
		return amiguinho.getValue();
	}

	public String getRevista() {
		return revista.getValue();
	}

	public String getDtEmprestimo() {
		return dtEmprestimo.getValue();
	}

	public String getDtDevolucao() {
		return dtDevolucao.getValue();
	}

	public String getDtDevolucaoFinal() {
		return dtDevolucaoFinal.getValue();
	}

	public String getStatus() {
		return status.getValue();
	}
	public Long getId(){
		return id.getValue();
	}
	

}
