package entidade;

import java.util.ArrayList;
import java.util.List;

import corrida.Avaliacao;
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

	public double calcularMediaAvaliacoes() {

		if (avaliacoes.isEmpty()) {
			return 0;
		}

		double soma = 0;

		for (Avaliacao avaliacao : avaliacoes) {
			soma += avaliacao.getNota();
		}

		return soma / avaliacoes.size();
	}

	@Override
	public String toString() {

		String media;

		if (avaliacoes.isEmpty()) {
			media = "Sem avaliações";
		}
		else {
			media = String.format("%.1f", calcularMediaAvaliacoes())
					+ " (" + avaliacoes.size() + " avaliações)";
		}

		return super.toString()
				+ "\nCNH: " + cnh
				+ "\nDisponível: " + (disponivel ? "Sim" : "Não")
				+ "\nVeículo: " + veiculo
				+ "\nMédia de avaliações: " + media;
	}
}