package domain;

import java.util.ArrayList;
import java.util.List;

import veiculo.Veiculo;

public class Motorista extends Pessoa {
	private String cnh;
	private boolean disponivel;
	private Veiculo veiculo;
	private List<Avaliacao> avaliacoes = new ArrayList<>();

	public Motorista(String nome, String cpf, String telefone, String cnh, Veiculo veiculo) {
		super(nome, cpf, telefone);
		this.cnh = cnh;
		this.veiculo = veiculo;
		this.disponivel = true;
	}

	public String getCnh() {
		return cnh;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void ficarDisponivel() {
		this.disponivel = true;
	}

	public void ficarIndisponivel() {
		this.disponivel = false;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void adicionarAvaliacao(Avaliacao avaliacao) {
		avaliacoes.add(avaliacao);
	}
}