package cadastro;

import java.util.ArrayList;
import java.util.List;

import entidade.Motorista;
import veiculo.Veiculo;

public class CadastroMotoristas {

	private List<Motorista> motoristas = new ArrayList<>();

	public Motorista cadastrar(Motorista motorista) {
		motoristas.add(motorista);
		return motorista;
	}

	public Motorista consultarPorCpf(String cpf) {

		for (Motorista motorista : motoristas) {

			if (motorista.getCpf().equals(cpf)) {
				return motorista;
			}
		}

		return null;
	}

	public boolean veiculoJaVinculado(Veiculo veiculo) {

		for (Motorista motorista : motoristas) {

			if (motorista.getVeiculo() != null
					&& motorista.getVeiculo().getPlaca()
							.equalsIgnoreCase(veiculo.getPlaca())) {

				return true;
			}
		}

		return false;
	}	
	

	public List<Motorista> listarDisponiveis() {

		List<Motorista> disponiveis = new ArrayList<>();

		for (Motorista motorista : motoristas) {

			if (motorista.isDisponivel()) {
				disponiveis.add(motorista);
			}
		}

		return disponiveis;
	}
	
	
}