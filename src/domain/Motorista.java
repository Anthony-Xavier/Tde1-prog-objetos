package domain;

import java.util.ArrayList;

import veiculo.Veiculo;

public class Motorista extends Pessoa {
	private String cnh;
	private boolean disponivel;
	private Veiculo veiculo;
	private ArrayList<Avaliacao> avaliacoes = new ArrayList<>();

	public Motorista(String nome, String cpf, String telefone, String cnh, Veiculo veiculo) {
		super(nome, cpf, telefone);
		this.setCnh(cnh);
		if (veiculo == null) {
			throw new IllegalArgumentException("Motorista precisa de um veículo associado.");
		}
		this.veiculo = veiculo;
		this.disponivel = true;
	}

	public String getCnh() {
		return cnh;
	}

	private void setCnh(String cnh) {
		if (cnh == null || cnh.isBlank()) {
			throw new IllegalArgumentException("CNH não pode ser vazia.");
		}
		String cnhLimpa = cnh.replaceAll("[.\\-\\s]", "");
		if (!cnhLimpa.matches("\\d+")) {
			throw new IllegalArgumentException("CNH deve conter apenas números.");
		}
		if (cnhLimpa.length() != 11) {
			throw new IllegalArgumentException("CNH deve conter exatamente 11 dígitos.");
		}
		this.cnh = cnhLimpa;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	void ficarDisponivel() {
		this.disponivel = true;
	}

	void ficarIndisponivel() {
		this.disponivel = false;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public ArrayList<Avaliacao> getAvaliacoes() {
		return new ArrayList<>(avaliacoes);
	}

	void adicionarAvaliacao(Avaliacao avaliacao) {
		avaliacoes.add(avaliacao);
	}

	public double getMediaAvaliacoes() {
		if (avaliacoes.isEmpty()) {
			return 0;
		}
		double soma = 0;
		for (Avaliacao avaliacao : avaliacoes) {
			soma += avaliacao.getNota();
		}
		return soma / avaliacoes.size();
	}
}
