package br.com.ceunsp.projeto1.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Emprestimo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataEmprestimo;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataDevolucao;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date dataDevolucaoFinal;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Amiguinho amiguinho;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Revista revista;
	
	@Column
	private Boolean entregue;

	public Emprestimo() {
		entregue = false;
	}
		
	public Amiguinho getAmiguinho() {
		return amiguinho;
	}

	public void setAmiguinho(Amiguinho amiguinho) {
		this.amiguinho = amiguinho;
	}

	public Revista getRevista() {
		return revista;
	}

	public void setRevista(Revista resvista) {
		this.revista = resvista;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emprestimo other = (Emprestimo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return amiguinho.getNome()+ " - " + revista.getColecao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isEntregue() {
		return entregue;
	}

	public void setEntregue(Boolean entregue) {
		this.entregue = entregue;
	}

	public Date getDataDevolucaoFinal() {
		return dataDevolucaoFinal;
	}

	public void setDataDevolucaoFinal(Date dataDevolucaoFinal) {
		this.dataDevolucaoFinal = dataDevolucaoFinal;
	}

	public Boolean getEntregue() {
		return entregue;
	}
	
	
}
