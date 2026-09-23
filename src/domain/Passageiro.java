package domain;

import java.util.ArrayList;
import java.util.List;

public class Passageiro extends Pessoa {

	private List<Corrida> historico = new ArrayList<>();

	public Passageiro(String nome, String cpf, String telefone) {
		super(nome, cpf, telefone);
	}

}