package entidade;

import java.util.ArrayList;
import java.util.List;

import corrida.Corrida;

public class Passageiro extends Pessoa {

	private List<Corrida> historico = new ArrayList<>();

	public Passageiro(String nome, String cpf, String telefone) {
		super(nome, cpf, telefone);
	}
	
	public List<Corrida> getHistorico() {
		return historico;
	}

	public void adicionarCorrida(Corrida corrida) {
		historico.add(corrida);
	}

	@Override
	public String toString() {
		return super.toString()
				+ "\nHistórico de corridas: " + historico.size();
	}
}