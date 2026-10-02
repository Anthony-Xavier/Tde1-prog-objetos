package entidade;

import java.util.ArrayList;
import java.util.List;

import corrida.Corrida;

public class Passageiro extends Pessoa {

	private List<Corrida> historico = new ArrayList<>();

	public Passageiro(String nome, String cpf, String telefone) {
		super(nome, cpf, telefone);
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ "\nHistórico de corridas: " + historico.size();
	}
}