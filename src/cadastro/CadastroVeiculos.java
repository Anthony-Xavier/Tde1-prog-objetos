package cadastro;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import veiculo.Veiculo;

public class CadastroVeiculos implements Serializable {

	private static final long serialVersionUID = 1L;

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