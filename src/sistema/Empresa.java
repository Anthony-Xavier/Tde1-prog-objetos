package sistema;

import java.util.ArrayList;

import categoria.CategoriaConforto;
import categoria.CategoriaCorrida;
import categoria.CategoriaEconomica;
import categoria.CategoriaPremium;
import domain.Corrida;
import domain.EstadoCorrida;
import domain.Motorista;
import domain.Passageiro;
import pagamento.Cartao;
import pagamento.Dinheiro;
import pagamento.FormaPagamento;
import pagamento.Pix;
import veiculo.Veiculo;

public class Empresa {

	private ArrayList<Passageiro> passageiros = new ArrayList<>();
	private ArrayList<Motorista> motoristas = new ArrayList<>();
	private ArrayList<Veiculo> veiculos = new ArrayList<>();
	private ArrayList<Corrida> corridas = new ArrayList<>();
	private ArrayList<CategoriaCorrida> categorias = new ArrayList<>();
	private ArrayList<FormaPagamento> formasPagamento = new ArrayList<>();
	private int proximoIdCorrida = 1;

	public Empresa() {
		categorias.add(new CategoriaEconomica());
		categorias.add(new CategoriaConforto());
		categorias.add(new CategoriaPremium());

		formasPagamento.add(new Pix());
		formasPagamento.add(new Cartao());
		formasPagamento.add(new Dinheiro());
	}

	public void cadastrarPassageiro(Passageiro passageiro) {
		if (buscarPassageiro(passageiro.getCpf()) != null) {
			throw new IllegalArgumentException("Já existe um passageiro com este CPF.");
		}
		passageiros.add(passageiro);
	}

	public void cadastrarMotorista(Motorista motorista) {
		if (buscarMotorista(motorista.getCpf()) != null) {
			throw new IllegalArgumentException("Já existe um motorista com este CPF.");
		}
		for (Motorista outro : motoristas) {
			if (outro.getCnh().equals(motorista.getCnh())) {
				throw new IllegalArgumentException("Já existe um motorista com esta CNH.");
			}
		}
		if (!getVeiculosSemMotorista().contains(motorista.getVeiculo())) {
			throw new IllegalArgumentException("Veículo não cadastrado ou já associado a outro motorista.");
		}
		motoristas.add(motorista);
	}

	public void cadastrarVeiculo(Veiculo veiculo) {
		for (Veiculo outro : veiculos) {
			if (outro.getPlaca().equals(veiculo.getPlaca())) {
				throw new IllegalArgumentException("Já existe um veículo com esta placa.");
			}
		}
		veiculos.add(veiculo);
	}

	public Corrida solicitarCorrida(Passageiro passageiro, String origem, String destino,
			double distanciaEstimada, CategoriaCorrida categoria, FormaPagamento formaPagamento) {
		Corrida corrida = passageiro.solicitarCorrida(proximoIdCorrida, origem, destino,
				distanciaEstimada, categoria, formaPagamento);
		proximoIdCorrida++;
		corridas.add(corrida);
		return corrida;
	}

	public Passageiro buscarPassageiro(String cpf) {
		String cpfLimpo = limparCpf(cpf);
		for (Passageiro passageiro : passageiros) {
			if (passageiro.getCpf().equals(cpfLimpo)) {
				return passageiro;
			}
		}
		return null;
	}

	public Motorista buscarMotorista(String cpf) {
		String cpfLimpo = limparCpf(cpf);
		for (Motorista motorista : motoristas) {
			if (motorista.getCpf().equals(cpfLimpo)) {
				return motorista;
			}
		}
		return null;
	}

	public ArrayList<Veiculo> getVeiculosSemMotorista() {
		ArrayList<Veiculo> livres = new ArrayList<>(veiculos);
		for (Motorista motorista : motoristas) {
			livres.remove(motorista.getVeiculo());
		}
		return livres;
	}

	public ArrayList<Motorista> getMotoristasAptos(Corrida corrida) {
		ArrayList<Motorista> aptos = new ArrayList<>();
		for (Motorista motorista : motoristas) {
			if (corrida.podeSerRealizadaPor(motorista)) {
				aptos.add(motorista);
			}
		}
		return aptos;
	}

	public ArrayList<Corrida> getCorridasPorEstado(EstadoCorrida estado) {
		ArrayList<Corrida> resultado = new ArrayList<>();
		for (Corrida corrida : corridas) {
			if (corrida.getEstado() == estado) {
				resultado.add(corrida);
			}
		}
		return resultado;
	}

	public ArrayList<Corrida> getCorridasCancelaveis() {
		ArrayList<Corrida> resultado = new ArrayList<>();
		for (Corrida corrida : corridas) {
			if (corrida.podeSerCancelada()) {
				resultado.add(corrida);
			}
		}
		return resultado;
	}

	public ArrayList<Corrida> getCorridasAvaliaveis() {
		ArrayList<Corrida> resultado = new ArrayList<>();
		for (Corrida corrida : corridas) {
			if (corrida.podeSerAvaliada()) {
				resultado.add(corrida);
			}
		}
		return resultado;
	}

	public ArrayList<Passageiro> getPassageiros() {
		return new ArrayList<>(passageiros);
	}

	public ArrayList<Corrida> getCorridas() {
		return new ArrayList<>(corridas);
	}

	public ArrayList<CategoriaCorrida> getCategorias() {
		return new ArrayList<>(categorias);
	}

	public ArrayList<FormaPagamento> getFormasPagamento() {
		return new ArrayList<>(formasPagamento);
	}

	private String limparCpf(String cpf) {
		return cpf.replace(".", "").replace("-", "").replace(" ", "");
	}

}
