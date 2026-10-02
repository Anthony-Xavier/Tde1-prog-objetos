package cadastro;

import java.util.ArrayList;
import java.util.List;

import veiculo.Veiculo;

public class CadastroVeiculos {

	private List<Veiculo> veiculos = new ArrayList<>();

	public Veiculo cadastrar(Veiculo veiculo) {
		veiculos.add(veiculo);
		return veiculo;
	}

	public List<Veiculo> listarTodos() {
		return veiculos;
	}

	public Veiculo consultarPorPlaca(String placa) {

		for (Veiculo veiculo : veiculos) {

			if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
				return veiculo;
			}
		}

		return null;
	}
}