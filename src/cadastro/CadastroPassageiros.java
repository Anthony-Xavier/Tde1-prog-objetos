package cadastro;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import entidade.Passageiro;

public class CadastroPassageiros implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Passageiro> passageiros = new ArrayList<>();

	public Passageiro cadastrar(Passageiro passageiro) {
		passageiros.add(passageiro);
		return passageiro;
	}

	public Passageiro consultarPorCpf(String cpf) {

		for (Passageiro passageiro : passageiros) {

			if (passageiro.getCpf().equals(cpf)) {
				return passageiro;
			}
		}

		return null;
	}

	public List<Passageiro> listarTodos() {
		return passageiros;
	}
}