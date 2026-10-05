package domain;

import java.util.ArrayList;

import categoria.CategoriaCorrida;
import pagamento.FormaPagamento;

public class Passageiro extends Pessoa {

	private ArrayList<Corrida> historico = new ArrayList<>();

	public Passageiro(String nome, String cpf, String telefone) {
		super(nome, cpf, telefone);
	}

	public Corrida solicitarCorrida(int id, String origem, String destino, double distanciaEstimada,
			CategoriaCorrida categoria, FormaPagamento formaPagamento) {
		Corrida corrida = new Corrida(id, this, origem, destino, distanciaEstimada, categoria, formaPagamento);
		historico.add(corrida);
		return corrida;
	}

	public ArrayList<Corrida> getHistorico() {
		return new ArrayList<>(historico);
	}

}
